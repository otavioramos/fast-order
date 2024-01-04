package br.com.otavio.fastorder.service;

import br.com.otavio.fastorder.model.dto.CreateOrderDTO;
import br.com.otavio.fastorder.model.dto.OrderItemRecordDTO;
import br.com.otavio.fastorder.model.dto.OrderPaymentRecordDTO;
import br.com.otavio.fastorder.model.dto.UpdateOrderDTO;
import br.com.otavio.fastorder.model.entity.Order;
import br.com.otavio.fastorder.model.entity.OrderProduct;
import br.com.otavio.fastorder.model.entity.enums.OrderPaymentMethod;
import br.com.otavio.fastorder.model.entity.enums.OrderStatusDescription;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private OrderService orderService;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    private static CreateOrderDTO getCreateOrderDTO(Integer productId1, Integer productId2) {
        OrderPaymentRecordDTO paymentRecordDTO = new OrderPaymentRecordDTO("CREDIT_CARD", 1);
        OrderItemRecordDTO orderItemRecordDTO1 = new OrderItemRecordDTO(productId1, 1);
        OrderItemRecordDTO orderItemRecordDTO2 = new OrderItemRecordDTO(productId2, 1);
        return new CreateOrderDTO(paymentRecordDTO, Arrays.asList(orderItemRecordDTO1, orderItemRecordDTO2));
    }

    private static OrderProduct getProduct() {
        return new OrderProduct(null, UUID.randomUUID().toString(), BigDecimal.TEN, 15);
    }

    @Test
    @Transactional
    void deve_salvar_pedido() {
        OrderProduct product1 = getProduct();
        Integer productId1 = orderProductService.save(product1).getId();
        OrderProduct product2 = getProduct();
        Integer productId2 = orderProductService.save(product2).getId();
        CreateOrderDTO dto = getCreateOrderDTO(productId1, productId2);

        Integer orderId = orderService.save(dto).getId();
        Order order = orderService.getById(orderId);

        assertThat(order.getPayment().getPaymentMethod(), equalTo(OrderPaymentMethod.CREDIT_CARD));
        assertThat(order.getItems().size(), is(2));
        assertTrue(order.getItems().stream().anyMatch(o -> o.getProduct().getId().equals(productId1)));
        assertTrue(order.getItems().stream().anyMatch(o -> o.getProduct().getId().equals(productId2)));

        orderService.deleteById(orderId);
        assertThat(orderProductService.getAll().size(), is(dto.items().size()));
        orderProductService.deleteById(productId1);
        orderProductService.deleteById(productId2);
        assertThat(orderProductService.getAll().size(), is(0));
    }

    @Test
    @Transactional
    void deve_obter_todos_pedidos() {
        assertThat(orderService.getAll().size(), is(0));

        OrderProduct product1 = getProduct();
        Integer productId1 = orderProductService.save(product1).getId();
        OrderProduct product2 = getProduct();
        Integer productId2 = orderProductService.save(product2).getId();
        CreateOrderDTO dto1 = getCreateOrderDTO(productId1, productId2);
        CreateOrderDTO dto2 = getCreateOrderDTO(productId1, productId2);

        Integer orderId1 = orderService.save(dto1).getId();
        Integer orderId2 = orderService.save(dto2).getId();

        assertThat(orderService.getAll().size(), is(2));
        orderService.deleteById(orderId1);
        orderService.deleteById(orderId2);
        orderProductService.deleteById(productId1);
        orderProductService.deleteById(productId2);
        assertThat(orderProductService.getAll().size(), is(0));
        assertThat(orderService.getAll().size(), is(0));
    }

    @Test
    @Transactional
    void deve_obter_pedido_pelo_ticket() {
        assertThat(orderService.getAll().size(), is(0));

        OrderProduct product1 = getProduct();
        Integer productId1 = orderProductService.save(product1).getId();
        OrderProduct product2 = getProduct();
        Integer productId2 = orderProductService.save(product2).getId();
        CreateOrderDTO dto = getCreateOrderDTO(productId1, productId2);

        Order order = orderService.save(dto);
        Order byTicket = orderService.getByTicket(order.getOrderTicket().getId());

        assertThat(order, equalTo(byTicket));
        orderService.deleteById(order.getId());
        orderProductService.deleteById(productId1);
        orderProductService.deleteById(productId2);
        assertThat(orderProductService.getAll().size(), is(0));
        assertThat(orderService.getAll().size(), is(0));
    }

    @Test
    @Transactional
    void deve_atualizar_pedido() {
        assertThat(orderService.getAll().size(), is(0));

        OrderProduct product1 = getProduct();
        Integer productId1 = orderProductService.save(product1).getId();
        OrderProduct product2 = getProduct();
        Integer productId2 = orderProductService.save(product2).getId();
        CreateOrderDTO dto = getCreateOrderDTO(productId1, productId2);

        Order order = orderService.save(dto);
        assertThat(order.getOrderStatus().getCurrentStatus(), equalTo(OrderStatusDescription.WAITING_FOR_PREPARATION));

        orderService.update(order.getId(), new UpdateOrderDTO("FINISHED"));
        Order savedOrder = orderService.getById(order.getId());

        assertThat(order.getOrderStatus().getCurrentStatus(), equalTo(OrderStatusDescription.FINISHED));
        orderService.deleteById(savedOrder.getId());
        orderProductService.deleteById(productId1);
        orderProductService.deleteById(productId2);
        assertThat(orderProductService.getAll().size(), is(0));
        assertThat(orderService.getAll().size(), is(0));
    }
}
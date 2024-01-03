package br.com.otavio.fastorder.service;

import br.com.otavio.fastorder.model.dto.OrderItemRecordDTO;
import br.com.otavio.fastorder.model.entity.OrderItem;
import br.com.otavio.fastorder.model.entity.OrderProduct;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class OrderItemServiceTest {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderProductService orderProductService;

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

    @Test
    void deve_carregar_items_pelo_dto() {
        Integer productId = 1;
        Integer productQuantity = 3;
        OrderProduct product = new OrderProduct(productId, "TESTE", BigDecimal.TEN, 15);
        List<OrderItemRecordDTO> items = Collections.singletonList(new OrderItemRecordDTO(productId, productQuantity));
        orderProductService.save(product);

        List<OrderItem> orderItems = orderItemService.loadItems(items);

        assertThat(orderItems.size(), equalTo(items.size()));
        for (OrderItem orderItem : orderItems) {
            assertThat(orderItem.getProduct().getId(), equalTo(productId));
        }

        orderProductService.deleteById(productId);
    }

    @Test
    void deve_obter_total_dos_items() {
        OrderProduct product1 = new OrderProduct(1, "TESTE", BigDecimal.TEN, 15);
        int quantity1 = 2;

        OrderProduct product2 = new OrderProduct(2, "TESTE 2", BigDecimal.ONE, 15);
        int quantity2 = 5;

        OrderItem item1 = new OrderItem(1, product1, quantity1);
        OrderItem item2 = new OrderItem(1, product2, quantity2);
        List<OrderItem> items = Arrays.asList(item1, item2);
        BigDecimal expectedTotal = product1.getPrice().multiply(BigDecimal.valueOf(quantity1))
                .add(product2.getPrice().multiply(BigDecimal.valueOf(quantity2)));

        BigDecimal total = orderItemService.getTotal(items);

        assertThat(total.compareTo(expectedTotal), is(0));
    }
}
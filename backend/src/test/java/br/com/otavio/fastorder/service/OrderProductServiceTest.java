package br.com.otavio.fastorder.service;

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
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OrderProductServiceTest {

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
    void deve_salvar_produto() {
        String productName = UUID.randomUUID().toString();
        OrderProduct product = new OrderProduct(null, productName, BigDecimal.TEN, 15);
        orderProductService.save(product);

        OrderProduct savedProduct = orderProductService.getByName(productName);

        assertThat(product, equalTo(savedProduct));

        orderProductService.deleteById(savedProduct.getId());
    }

    @Test
    void deve_obter_todos_produtos() {
        String productName1 = UUID.randomUUID().toString();
        OrderProduct product1 = new OrderProduct(null, productName1, BigDecimal.TEN, 15);
        String productName2 = UUID.randomUUID().toString();
        OrderProduct product2 = new OrderProduct(null, productName2, BigDecimal.ONE, 15);
        orderProductService.save(product1);
        orderProductService.save(product2);

        List<OrderProduct> products = orderProductService.getAll();

        assertThat(products.size(), is(2));
        assertTrue(products.stream().anyMatch(p -> p.getName().equals(productName1)));
        assertTrue(products.stream().anyMatch(p -> p.getName().equals(productName2)));

        for (OrderProduct product : products) {
            orderProductService.deleteById(product.getId());
        }
    }
}
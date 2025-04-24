package com.systemlabs.catalog_service.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class ProductRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Test
    void shouldFindProductById() {
        // Arrange
        ProductEntity product = new ProductEntity();
        product.setName("Find Product");
        product.setPrice(BigDecimal.valueOf(10));
        ProductEntity savedProduct = productRepository.save(product);

        // Act
        ProductEntity foundProduct = productRepository
                .findById(savedProduct.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Assert
        assertEquals(savedProduct.getId(), foundProduct.getId());
        assertEquals("Find Product", foundProduct.getName());
    }

    @Test
    void shouldGetProductByCode() {
        // Arrange
        ProductEntity product = new ProductEntity();
        product.setCode("ABC");
        product.setName("Find Product");
        product.setPrice(BigDecimal.valueOf(10));
        ProductEntity savedProduct = productRepository.save(product);

        // Act
        ProductEntity foundProduct = productRepository
                .findByCode(savedProduct.getCode())
                .orElseThrow();

        // Assert
        assertEquals(savedProduct.getCode(), foundProduct.getCode());
        assertEquals("Find Product", foundProduct.getName());
    }
}

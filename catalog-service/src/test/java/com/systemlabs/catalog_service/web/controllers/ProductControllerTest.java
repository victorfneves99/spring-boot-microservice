package com.systemlabs.catalog_service.web.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import com.systemlabs.catalog_service.AbstractIT;
import com.systemlabs.catalog_service.domain.Product;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@Sql("/test-data.sql")
public class ProductControllerTest extends AbstractIT {
    @Test
    void shouldReturnProducts() {

        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("data", hasSize(10))
                .body("totalElements", is(15))
                .body("pageNumber", is(1))
                .body("totalPages", is(2))
                .body("isFirst", is(true))
                .body("isLast", is(false))
                .body("hasNext", is(true))
                .body("hasPrevious", is(false));
    }

    @Test
    void shouldGetProductByCode() {
        var response = given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", "P100")
                .then()
                .statusCode(200);

        var product = response.extract().as(Product.class);
        assertEquals("The Hunger Games", product.name());
    }

    @Test
    void notFoundWhenProductCodeNotExists() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", "NON_EXISTENT_CODE")
                .then()
                .statusCode(404)
                .body("title", is("Product Not Found"));
    }
}

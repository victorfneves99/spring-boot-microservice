package com.systemlabs.catalog_service.domain;

public class ProductMapper {

    static Product toProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getCode(),
                productEntity.getName(),
                productEntity.getImageUrl(),
                productEntity.getDescription(),
                productEntity.getPrice());
    }
}

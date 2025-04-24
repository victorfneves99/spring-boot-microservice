package com.systemlabs.catalog_service.domain;

import com.systemlabs.catalog_service.ApplicationProperties;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    private final ApplicationProperties applicationProperties;

    private final ProductRepository productRespository;

    public ProductService(ApplicationProperties applicationProperties, ProductRepository productRespository) {
        this.applicationProperties = applicationProperties;
        this.productRespository = productRespository;
    }

    public PageResult<Product> getProducts(int pageNo) {

        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, applicationProperties.pagesize(), sort);
        var result = productRespository.findAll(pageable).map(ProductMapper::toProduct);

        return new PageResult<>(
                result.getContent(),
                result.getTotalElements(),
                result.getNumber() + 1,
                result.getTotalPages(),
                result.isFirst(),
                result.isLast(),
                result.hasNext(),
                result.hasPrevious());
    }

    public Optional<Product> getProductByCode(String code) {
        return productRespository.findByCode(code).map(ProductMapper::toProduct);
    }
}

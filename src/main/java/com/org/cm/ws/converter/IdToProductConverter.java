package com.org.cm.ws.converter;

import com.org.cm.infrastructure.entity.Product;
import com.org.cm.infrastructure.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class IdToProductConverter implements Converter<Long, Product> {
    @Autowired
    ProductRepository productRepository;
    @Override
    public Product convert(Long source) {
        return productRepository.findProductById((source));
    }
}
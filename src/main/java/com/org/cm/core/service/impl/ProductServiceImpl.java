package com.org.cm.core.service.impl;

import com.org.cm.core.model.ProductModel;
import com.org.cm.core.service.ProductService;
import com.org.cm.core.service.exception.ProductNotFound;
import com.org.cm.infrastructure.entity.Product;
import com.org.cm.infrastructure.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final
    ProductRepository productRepository;
    private final
    ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductModel> getProducts() {
        log.info("method getProducts is invoked");
        List<ProductModel> productModels = (productRepository.findAll())
                .stream()
                .map(x -> modelMapper.map(x, ProductModel.class))
                .collect(Collectors.toList());
        Assert.notEmpty(productModels, "No product found");
        return productModels;

    }

    @Override
    @Transactional(readOnly = true)
    public ProductModel getProductByID(long id) throws ProductNotFound {
        log.info("method getProductById is invoked");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("product not found for this id :: " + id));
        ProductModel productModel = modelMapper.map(product, ProductModel.class);
        Assert.notNull(productModel, "productModel null");
        return productModel;
    }

    @Override
    @Transactional
    public Product saveProduct(ProductModel productModel) {
        log.info("method saveProduct is invoked");
        Assert.notNull(productModel, "product model not valid");
        Product product = modelMapper.map(productModel, Product.class);
        product.setCreated(LocalDateTime.now());
        product.setUpdated(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProductByID(long id, ProductModel productModel) throws ProductNotFound {
        log.info("method updateProductById is invoked");
        Assert.notNull(productModel, "product model not valid");
        Product productToBeUpdated = productRepository.findById(id).orElseThrow(() ->
                new ProductNotFound("product not found for this id :: " + id));
        Product product = modelMapper.map(productModel, Product.class);
        product.setCreated(productToBeUpdated.getCreated());
        product.setUpdated(LocalDateTime.now());
        productRepository.delete(productToBeUpdated);
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProductByID(long id) throws ProductNotFound {
        log.info("method deleteProductById is invoked");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("product not found for this id :: " + id));
        product.setRemoved(LocalDateTime.now());
        productRepository.save(product);

    }
}

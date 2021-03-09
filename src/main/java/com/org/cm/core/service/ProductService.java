package com.org.cm.core.service;

import com.org.cm.core.model.ProductModel;
import com.org.cm.core.service.exception.ProductNotFound;
import com.org.cm.infrastructure.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductModel> getProducts();
    ProductModel getProductByID(long id) throws ProductNotFound;
    Product saveProduct(ProductModel productModel) ;
    Product updateProductByID(long id, ProductModel productModel) throws ProductNotFound;
    void deleteProductByID(long id) throws ProductNotFound;
}

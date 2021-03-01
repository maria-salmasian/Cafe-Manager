package com.org.cm.core.service;

import com.org.cm.core.model.ProductModel;
import com.org.cm.core.model.UserModel;
import com.org.cm.infrastructure.entity.Product;
import com.org.cm.infrastructure.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductModel> getProducts();
    ProductModel getProductByID(int id) ;
    Product saveProduct(ProductModel productModel) ;
    Product updateProductByID(int id, ProductModel productModel) ;
    void deleteProductByID(int id) ;
}

package com.org.cm.ws.controller;

import com.org.cm.core.model.ProductModel;
import com.org.cm.core.service.ProductService;
import com.org.cm.core.service.exception.ProductNotFound;
import com.org.cm.ws.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    private final
    ProductService productService;

    private final
    ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") long id) throws ProductNotFound {
        log.info("method getProductById in Controller is invoked");
        ProductModel productModel = productService.getProductByID(id);
        ProductDTO productDTO = modelMapper.map(productModel, ProductDTO.class);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        log.info("method getProducts in Controller is invoked");

        List<ProductModel> productModels = productService.getProducts();
        List<ProductDTO> productDTOS = productModels.stream().map(x -> modelMapper.map(x, ProductDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct( @RequestBody ProductDTO productDTO)  {
        log.info("method createProduct in Controller is invoked");

        ProductModel productModel = modelMapper.map(productDTO, ProductModel.class);
        productService.saveProduct(productModel);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") long id,
                                              @RequestBody ProductDTO productDTO) throws ProductNotFound {
        log.info("method updateProduct in Controller is invoked");

        ProductModel updatedProductModel = modelMapper.map(productDTO, ProductModel.class);
        productService.updateProductByID(id, updatedProductModel);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id) throws ProductNotFound {
        log.info("method deleteProduct in Controller is invoked");

        productService.deleteProductByID(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }



}

package com.product.product_service.service;

import com.product.product_service.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product addProduct(Product product);

    Product getProductByName(String name);

    List<Product> getAllProducts();

    Product updateProductByName(String name, Product updatedProduct);

    void deleteProduct(String id);

    Product getProductById(String id);
}

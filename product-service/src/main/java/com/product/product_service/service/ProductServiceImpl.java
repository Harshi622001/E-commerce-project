package com.product.product_service.service;

import com.product.product_service.entity.Product;
import com.product.product_service.exception.ProductNotFoundException;
import com.product.product_service.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Product addProduct(Product product) {

        return productRepo.save(product);
    }


    public Product getProductByName(String name)  {
        return (Product) productRepo.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("Product with name " + name + " not found"));
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }


    public Product updateProductByName(String name, Product updatedProduct) {
        Product existingProduct = (Product) productRepo.findByName(name).orElseThrow(() -> new ProductNotFoundException("Product with name " + name + " not found"));

        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setImage(updatedProduct.getImage());
        existingProduct.setTag(updatedProduct.getTag());

        return productRepo.save(existingProduct);
    }


    public void deleteProduct(String id) {

        productRepo.deleteById(id);
    }

    @Override
    public Product getProductById(String id) {
        return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found for id: " + id));
    }
}


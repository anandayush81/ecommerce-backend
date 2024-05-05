package dev.ayush.productservice.services;

import dev.ayush.productservice.exceptions.ProductNotFoundException;
import dev.ayush.productservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long id) throws ProductNotFoundException;

    Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy, String order);

    Product addNewProduct(Product product);

    Product updateProduct(Long id, Product product) throws ProductNotFoundException;

    Product replaceProduct(Long id, Product product);

    void deleteProduct(Long id);
}

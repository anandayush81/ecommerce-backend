package dev.ayush.productservice.services;

import dev.ayush.productservice.models.Category;
import dev.ayush.productservice.models.Product;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    List<Product> getProductsInCategory(String name);
}

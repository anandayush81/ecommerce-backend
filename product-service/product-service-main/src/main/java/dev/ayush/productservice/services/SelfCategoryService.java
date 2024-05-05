package dev.ayush.productservice.services;

import dev.ayush.productservice.models.Category;
import dev.ayush.productservice.models.Product;
import dev.ayush.productservice.repositories.CategoryRepository;
import dev.ayush.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfCategoryService")
public class SelfCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SelfCategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Product> getProductsInCategory(String name) {
        return productRepository.findAllByCategory_name(name);
    }
}

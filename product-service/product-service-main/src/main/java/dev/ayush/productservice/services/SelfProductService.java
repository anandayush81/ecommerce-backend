package dev.ayush.productservice.services;

import dev.ayush.productservice.exceptions.ProductNotFoundException;
import dev.ayush.productservice.models.Category;
import dev.ayush.productservice.models.Product;
import dev.ayush.productservice.repositories.CategoryRepository;
import dev.ayush.productservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy, String order) {
        Sort sort = Sort.by(Sort.Direction.fromString(order), sortBy);
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
    }

    @Override
    public Product addNewProduct(Product product) {
        Optional<Category> category = categoryRepository.findByName(product.getCategory().getName());
        category.ifPresent(product::setCategory);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotFoundException {
        Product savedProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        if (product.getTitle() != null) {
            savedProduct.setTitle(product.getTitle());
        }
        if (product.getDescription() != null) {
            savedProduct.setDescription(product.getDescription());
        }
        if (product.getPrice() != null) {
            savedProduct.setPrice(product.getPrice());
        }
        if (product.getCategory() != null) {
            Optional<Category> category = categoryRepository.findByName(product.getCategory().getName());
            savedProduct.setCategory(
                    category.orElseGet(() -> categoryRepository.saveAndFlush(product.getCategory()))
            );
        }
        if (product.getImageUrl() != null) {
            savedProduct.setImageUrl(product.getImageUrl());
        }
        return productRepository.save(savedProduct);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

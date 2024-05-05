package dev.ayush.productservice.services;

import dev.ayush.productservice.dtos.FakeStoreProductDto;
import dev.ayush.productservice.models.Category;
import dev.ayush.productservice.models.Product;
import lombok.val;

public class ProductConverter {

    static FakeStoreProductDto convertProductToFakeStoreProduct(Product product) {
        FakeStoreProductDto.FakeStoreProductDtoBuilder fakeStoreProductBuilder = FakeStoreProductDto.builder();
        if (product.getId() != null)
            fakeStoreProductBuilder.id(product.getId());
        if (product.getTitle() != null)
            fakeStoreProductBuilder.title(product.getTitle());
        if (product.getPrice() != null)
            fakeStoreProductBuilder.price(product.getPrice());
        if (product.getDescription() != null)
            fakeStoreProductBuilder.description(product.getDescription());
        if (product.getImageUrl() != null)
            fakeStoreProductBuilder.image(product.getImageUrl());
        Category category = product.getCategory();
        if (category != null)
            fakeStoreProductBuilder.category(category.getName());
        return fakeStoreProductBuilder.build();
    }

    static Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProduct) {
        Product product = new Product();
        product.setTitle(fakeStoreProduct.getTitle());
        product.setId(fakeStoreProduct.getId());
        product.setPrice(fakeStoreProduct.getPrice());
        product.setDescription(fakeStoreProduct.getDescription());
        product.setImageUrl(fakeStoreProduct.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProduct.getCategory());

        return product;
    }
}

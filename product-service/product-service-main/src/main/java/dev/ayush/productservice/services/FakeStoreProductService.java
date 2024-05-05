package dev.ayush.productservice.services;

import dev.ayush.productservice.dtos.FakeStoreProductDto;
import dev.ayush.productservice.exceptions.ProductNotFoundException;
import dev.ayush.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static dev.ayush.productservice.services.ProductConverter.convertFakeStoreProductToProduct;
import static dev.ayush.productservice.services.ProductConverter.convertProductToFakeStoreProduct;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    public static final String FAKE_STORE_PRODUCT_ENDPOINT = "https://fakestoreapi.com/products/";
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Product cachedProduct = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + id);
        if (cachedProduct != null) {
            return cachedProduct;
        }

        FakeStoreProductDto productDto = restTemplate.getForObject(
                FAKE_STORE_PRODUCT_ENDPOINT + id,
                FakeStoreProductDto.class
        );
        if (productDto == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        Product product = convertFakeStoreProductToProduct(productDto);
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + id, product);
        return product;
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy, String order) {
        FakeStoreProductDto[] productDtos = restTemplate.getForObject(
                FAKE_STORE_PRODUCT_ENDPOINT,
                FakeStoreProductDto[].class
        );

        List<Product> products = productDtos != null ? Arrays.stream(productDtos)
                .map(ProductConverter::convertFakeStoreProductToProduct)
                .toList() : Collections.emptyList();
        return new PageImpl<>(products);
    }

    @Override
    public Product addNewProduct(Product product) {
        FakeStoreProductDto fakeStoreProduct = convertProductToFakeStoreProduct(product);
        FakeStoreProductDto response = restTemplate.postForObject(
                FAKE_STORE_PRODUCT_ENDPOINT,
                fakeStoreProduct,
                FakeStoreProductDto.class
        );
        return response != null ? convertFakeStoreProductToProduct(response) : null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProduct = convertProductToFakeStoreProduct(product);
        FakeStoreProductDto response = restTemplate.patchForObject(
                FAKE_STORE_PRODUCT_ENDPOINT + id,
                fakeStoreProduct,
                FakeStoreProductDto.class
        );
        return response != null ? convertFakeStoreProductToProduct(response) : null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = convertProductToFakeStoreProduct(product);
        RequestEntity<FakeStoreProductDto> fakeStoreProductDtoHttpEntity = new RequestEntity<>(
                fakeStoreProductDto,
                HttpMethod.PUT,
                URI.create(FAKE_STORE_PRODUCT_ENDPOINT + id));
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                fakeStoreProductDtoHttpEntity,
                FakeStoreProductDto.class
        );
        return response.getBody() != null ? convertFakeStoreProductToProduct(response.getBody()) : null;
    }

    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete(FAKE_STORE_PRODUCT_ENDPOINT + id);
    }
}

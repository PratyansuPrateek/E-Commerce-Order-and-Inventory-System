package com.pratyansu.ecommercebackend.service;

import com.pratyansu.ecommercebackend.dto.ProductRequest;
import com.pratyansu.ecommercebackend.dto.ProductResponse;
import com.pratyansu.ecommercebackend.entity.Category;
import com.pratyansu.ecommercebackend.entity.Product;
import com.pratyansu.ecommercebackend.exception.ResourceNotFoundException;
import com.pratyansu.ecommercebackend.repository.CategoryRepository;
import com.pratyansu.ecommercebackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request){
        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);

        Product save = productRepository.save(product);
        return mapToResponse(save);
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategoryName(product.getCategory().getName());
        return response;
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        return mapToResponse(product);
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(this::mapToResponse);
    }

}

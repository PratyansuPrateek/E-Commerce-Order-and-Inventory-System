package com.pratyansu.ecommercebackend.service;

import com.pratyansu.ecommercebackend.dto.CategoryRequest;
import com.pratyansu.ecommercebackend.dto.CategoryResponse;
import com.pratyansu.ecommercebackend.entity.Category;
import com.pratyansu.ecommercebackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository repository;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request){
        Category category = new Category();

        category.setName(request.getName());

        Category save = repository.save(category);

        return mapToResponse(save);

    }

    private CategoryResponse mapToResponse(Category category){
        CategoryResponse response = new CategoryResponse();

        response.setId(category.getId());
        response.setName(category.getName());

        return response;
    }

    public List<CategoryResponse> getAllCategories(){
        List<Category> categories = repository.findAll();

        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }
}

package com.zhang.springbootmall.service;

import com.zhang.springbootmall.dto.ProductRequest;
import com.zhang.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}

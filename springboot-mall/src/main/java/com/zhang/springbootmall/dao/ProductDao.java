package com.zhang.springbootmall.dao;

import com.zhang.springbootmall.dto.ProductRequest;
import com.zhang.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

}

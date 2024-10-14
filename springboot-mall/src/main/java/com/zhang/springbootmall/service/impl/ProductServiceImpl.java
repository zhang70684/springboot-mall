package com.zhang.springbootmall.service.impl;

import com.zhang.springbootmall.dao.ProductDao;
import com.zhang.springbootmall.model.Product;
import com.zhang.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}

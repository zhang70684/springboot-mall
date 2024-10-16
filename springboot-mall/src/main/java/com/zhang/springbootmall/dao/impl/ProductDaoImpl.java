package com.zhang.springbootmall.dao.impl;

import com.zhang.springbootmall.dao.ProductDao;
import com.zhang.springbootmall.dto.ProductRequest;
import com.zhang.springbootmall.model.Product;
import com.zhang.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT " +
                "product_id," +
                "product_name," +
                " category," +
                " image_url," +
                " price, stock," +
                " description," +
                " created_date," +
                " last_modified_date" +
                " FROM product " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId",productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size() > 0){
            return productList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        // 檢查 productName 是否已存在
        String checkSql = "SELECT COUNT(*) FROM product WHERE product_name = :productName";
        Map<String, Object> checkMap = new HashMap<>();
        checkMap.put("productName", productRequest.getProductName());

        // 查詢結果
        int count = namedParameterJdbcTemplate.queryForObject(checkSql, checkMap, Integer.class);

        // 如果 count 大於 0，則表示 productName 已存在，拋出異常
        if (count > 0) {
            throw new IllegalArgumentException("商品重複(productName): " + productRequest.getProductName());
        }else{
            String sql = "INSERT INTO product (" +
                    "product_name," +
                    " category," +
                    " image_url," +
                    " price," +
                    " stock," +
                    " description," +
                    " created_date," +
                    " last_modified_date)" +
                    " VALUES (" +
                    " :productName," +
                    " :category," +
                    " :imageUrl," +
                    " :price," +
                    " :stock," +
                    " :description," +
                    " :createdDate," +
                    " :lastModifiedDate)";

            Map<String,Object> map = new HashMap<>();
            map.put("productName",productRequest.getProductName());
            map.put("category",productRequest.getCategory().toString());
            map.put("imageUrl",productRequest.getImageUrl());
            map.put("price",productRequest.getPrice());
            map.put("stock",productRequest.getStock());
            map.put("description",productRequest.getDescription());

            Date now = new Date();
            map.put("createdDate",now);
            map.put("lastModifiedDate",now);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

            int productId = keyHolder.getKey().intValue();

            return productId;
        }
    }


}

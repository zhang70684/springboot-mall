package com.zhang.springbootmall.dao.impl;

import com.zhang.springbootmall.dao.ProductDao;
import com.zhang.springbootmall.dto.ProductQueryParams;
import com.zhang.springbootmall.dto.ProductRequest;
import com.zhang.springbootmall.model.Product;
import com.zhang.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {

        String sql = "SELECT COUNT(*) FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql,map,productQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {

        String sql = "SELECT " +
                "product_id," +
                "product_name," +
                " category," +
                " image_url," +
                " price, stock," +
                " description," +
                " created_date," +
                " last_modified_date" +
                " FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql,map,productQueryParams);

        //排序
        sql += " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        //分頁 LIMIT OFFSET
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        //sql語句
        System.out.println("sql = " + sql);

        return productList;
    }

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

            //當前時間
            Date now = new Date();
            map.put("createdDate",now);
            map.put("lastModifiedDate",now);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

            int productId = keyHolder.getKey().intValue();

            return productId;
        }
    }


    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product " +
                "SET product_name = :productName," +
                " category = :category," +
                " image_url = :imageUrl," +
                " price = :price," +
                " stock = :stock," +
                " description = :description," +
                " last_modified_date = :lastModifiedDate" +
                " WHERE product_id = :productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        map.put("lastModifiedDate",new Date());

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);

    }

    //查詢條件
    private String addFilteringSql(String sql,Map<String,Object> map,ProductQueryParams productQueryParams){

        if(productQueryParams.getCategory() != null){
            sql += " AND category = :category";
            map.put("category",productQueryParams.getCategory().name()); //Enum to String
        }

        if(productQueryParams.getSearch() != null){
            sql += " AND product_name LIKE :search";
            map.put("search","%" + productQueryParams.getSearch() + "%");
        }

        return sql;
    }

}

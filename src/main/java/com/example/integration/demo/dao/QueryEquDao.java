package com.example.integration.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class QueryEquDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String,Object>> queryEqu(){
        return jdbcTemplate.queryForList("SELECT * FROM equipment");
    }
}

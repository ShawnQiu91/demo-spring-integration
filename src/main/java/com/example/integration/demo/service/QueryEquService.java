package com.example.integration.demo.service;

import com.example.integration.demo.dao.QueryEquDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "queryEquService")
public class QueryEquService {
    @Autowired
    private QueryEquDao queryEquDao;

    public List<Map<String,Object>> queryEqu() {
        return queryEquDao.queryEqu();
    }
}

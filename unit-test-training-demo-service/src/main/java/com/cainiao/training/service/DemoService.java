package com.cainiao.training.service;

import com.cainiao.training.infra.DemoDBMapper;
import com.cainiao.training.infra.DemoTairClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Component
public class DemoService {
    @Autowired
    DemoTairClient tairClient;
    @Autowired
    DemoDBMapper dbMapper;

    public String getResult(String request) throws IOException {
        String cacheDate = tairClient.getCache(request);

        if (cacheDate == null) {
            throw new IOException("Client Timeout");
        }
        if (!StringUtils.isEmpty(cacheDate)) {
            return cacheDate;
        } else {
            return dbMapper.queryData(request);
        }
    }

    public List<String> getResults(List<String> requests) {
        return tairClient.batchGet(requests, () -> {
            List<String> dbResult = dbMapper.queryData(requests);
            if (dbResult.isEmpty()) {
                throw new RuntimeException("Empty DB");
            }
            return dbResult;
        });

    }

    public void deleteByKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        String sql = generateSQL(key);
        System.out.println(sql);
        dbMapper.deleteData(sql);
    }

    private String generateSQL(String key) {
        return String.format("Delete from table_name where key = %s", key);
    }


}

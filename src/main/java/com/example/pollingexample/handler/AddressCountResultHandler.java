package com.example.pollingexample.handler;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.util.Map;

public class AddressCountResultHandler implements ResultHandler {
    private Map<String, Long> resultMap;

    public AddressCountResultHandler(Map<String, Long> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void handleResult(ResultContext resultContext) {
        // for each row, we are obtaining a map, mapping column names to their respective value
        Map<String, Object> row = (Map<String, Object>) resultContext.getResultObject();
        String address = (String) row.get("address");
        Long count = (Long) row.get("count");
        resultMap.put(address, count);
    }
}

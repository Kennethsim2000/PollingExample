package com.example.pollingexample.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pollingexample.model.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.ResultHandler;


import java.util.Map;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
//    @Select("SELECT address, COUNT(*) AS count FROM account GROUP BY address")
//    @MapKey("address")
//    Map<String, Map<String, Integer>> getCountByAddress();

/* Without @MapKey, we should be getting a list of Map<String, Integer> where each row correspond to a Map<String, Integer>, and the map maps
column names to its values
* {
*    "address": "tamp",
*    "count": 3
* }
Each row returns a address and count(*), basically a map, so when we use @MapKey("Address"), we will map each Map<String, Integer> using address
* and return a Map<String, Map<String, Integer>>
*/

    @Select("SELECT address, COUNT(*) AS count FROM account GROUP BY address")
    @ResultType(Map.class)
    void getCountByAddress(ResultHandler<Map<String, Long>> resultHandler);

}

/*
By setting ResultType = Map.class, we can ensure that each row, the column name will be the key, and the value will be the value. We can pass in this
* to a resultHandler, which will then parse each row, and return a Map<String, Long>
*/

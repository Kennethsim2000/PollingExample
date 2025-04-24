package com.example.pollingexample.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pollingexample.handler.AddressCountResultHandler;
import com.example.pollingexample.mapper.AccountMapper;
import com.example.pollingexample.model.Account;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    private static final Integer PAGE = 0;
    private static final Integer PAGE_SIZE = 10;

    @Override
    public Account addAccount(Account acc) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("name", acc.getName());
        Account existingAcc = baseMapper.selectOne(wrapper);
        if(existingAcc != null) {
            return null;
        }
        int res = baseMapper.insert(acc);
        return acc;
    }

    @Override
    public Page<Account> getAccounts() {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        Page<Account> page = new Page<>(PAGE, PAGE_SIZE);
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public Map<String, Long> partitionByAddress() {
        Map<String, Long> resultMap = new HashMap<>();
        ResultHandler<Map<String, Long>> resultHandler = new AddressCountResultHandler(resultMap);
        baseMapper.getCountByAddress(resultHandler);
         return resultMap;
    }
}

package com.example.pollingexample.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pollingexample.model.Account;

import java.util.Map;

public interface AccountService extends IService<Account> {
    Account addAccount(Account acc);
    Page<Account> getAccounts();
    Map<String, Long> partitionByAddress();
}

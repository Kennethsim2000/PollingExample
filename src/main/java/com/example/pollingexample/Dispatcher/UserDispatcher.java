package com.example.pollingexample.Dispatcher;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pollingexample.enums.RequestEnum;
import com.example.pollingexample.model.Account;
import com.example.pollingexample.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class UserDispatcher implements Dispatcher{

    @Autowired
    AccountService accountService;

    @Override
    public Object process(Object requestBody) throws InterruptedException {
        Page<Account> page = accountService.getAccounts();
        TimeUnit.SECONDS.sleep(5);
        return page;
    }

    @Override
    public String getRequestType() {
        return RequestEnum.OBTAIN_USER.getTaskName();
    }
}

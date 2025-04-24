package com.example.pollingexample.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pollingexample.Request.AccountRequest;
import com.example.pollingexample.config.CommonResult;
import com.example.pollingexample.model.Account;
import com.example.pollingexample.response.AccountResponse;
import com.example.pollingexample.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping()
    @Operation(summary = "Add a new account", description = "Creates a new account")
    public CommonResult<AccountResponse> addTask(@RequestBody AccountRequest request) {
        Account acc = new Account();
        BeanUtils.copyProperties(request, acc);
        Account addedAcc = accountService.addAccount(acc);
        if(addedAcc == null) {
            return CommonResult.fail("Account already exist", 400);
        }
        AccountResponse response = AccountResponse.builder()
                .id(addedAcc.getId())
                .address(addedAcc.getAddress())
                .name(addedAcc.getName())
                .build();
        return CommonResult.success(response, "Account successfully added");
    }

    @GetMapping()
    @Operation(summary = "Get all accounts", description = "Retrieves all accounts")
    public CommonResult<Page<AccountResponse>> getAllAccounts() {
        Page<Account> page = accountService.getAccounts();
        Page<AccountResponse> response = new Page<>();
        BeanUtils.copyProperties(page, response);
        return CommonResult.success(response, "Accounts successfully retrieved");
    }

    @GetMapping("stat")
    @Operation(summary = "Get count at each address", description = "Retrieves all address and the number of accounts belonging")
    public CommonResult<Map<String, Long>> partitionByAddress() {
        Map<String, Long> response = accountService.partitionByAddress();
        return CommonResult.success(response, "Accounts successfully retrieved");
    }

}

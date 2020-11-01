package com.anz.accounts.controller;

import com.anz.accounts.repository.entity.Account;
import com.anz.accounts.repository.entity.Transaction;
import com.anz.accounts.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @GetMapping(path ="/accounts/{user}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Account>> getAccounts(@PathVariable String user) {
        //TODO : GET User from JWT Token
        List<Account> accounts = accountsService.getAccountsForUserId(user);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(path ="/transactions/{account}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String account) {
        List<Transaction> transactions = accountsService.getTransactionForAccount(account);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}

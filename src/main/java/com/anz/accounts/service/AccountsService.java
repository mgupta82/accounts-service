package com.anz.accounts.service;

import com.anz.accounts.repository.AccountRepository;
import com.anz.accounts.repository.TransactionRepository;
import com.anz.accounts.repository.entity.Account;
import com.anz.accounts.repository.entity.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class AccountsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Account> getAccountsForUserId(String userId) {
        log.info("Finding accounts by user id = {}",userId);
        return accountRepository.findByUserId(userId);
    }

    public List<Transaction> getTransactionForAccount(String accountNumber) {
        log.info("Finding transactions by account number = {}",accountNumber);
        return transactionRepository.findByAccountNumber(accountNumber);
    }
}

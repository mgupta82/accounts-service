package com.anz.accounts.service;

import com.anz.accounts.repository.entity.Account;
import com.anz.accounts.repository.entity.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.AssertionErrors;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts= {"/scripts/test-accounts.sql"})
public class AccountsServiceTest {
    @Autowired
    private AccountsService accountsService;

    @Test
    public void testGetAccountsForUserId() {
        List<Account> accounts = accountsService.getAccountsForUserId("testuser");
        Assertions.assertNotNull(accounts);
        AssertionErrors.assertEquals("Invalid number of accounts",1,accounts.size());
    }

    @Test
    public void testGetTransactionForAccount() {
        List<Transaction> transactions = accountsService.getTransactionForAccount("585309209");
        Assertions.assertNotNull(transactions);
        AssertionErrors.assertEquals("Invalid number of transactions",1,transactions.size());
    }

}

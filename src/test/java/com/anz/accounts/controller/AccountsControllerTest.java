package com.anz.accounts.controller;

import com.anz.accounts.dto.ErrorResponse;
import com.anz.accounts.repository.entity.Account;
import com.anz.accounts.repository.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.AssertionErrors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts= {"/scripts/test-accounts.sql"})
public class AccountsControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testGetAccounts() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity request = new HttpEntity<>(null);
        ResponseEntity<Account[]> responseEntity = restTemplate.exchange("/api/accounts/testuser", HttpMethod.GET,request, Account[].class);
        AssertionErrors.assertEquals("Invalid Http Status",HttpStatus.OK,responseEntity.getStatusCode());
        AssertionErrors.assertEquals("Incorrect search result",2,responseEntity.getBody().length);
    }

    @Test
    public void testGetTransactions() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity request = new HttpEntity<>(null);
        ResponseEntity<Transaction[]> responseEntity = restTemplate.exchange("/api/transactions/585309209", HttpMethod.GET,request, Transaction[].class);
        AssertionErrors.assertEquals("Invalid Http Status",HttpStatus.OK,responseEntity.getStatusCode());
        AssertionErrors.assertEquals("Incorrect search result",3,responseEntity.getBody().length);
    }

    @Test
    public void testErrorScenario() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity request = new HttpEntity<>(null);
        ResponseEntity<ErrorResponse> responseEntity = restTemplate.exchange("/account/testuser", HttpMethod.POST,request, ErrorResponse.class);
        AssertionErrors.assertEquals("Invalid Http Status",HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }

}

package com.anz.accounts.controller;

import com.anz.accounts.JwtUtil;
import com.anz.accounts.dto.ErrorResponse;
import com.anz.accounts.repository.entity.Account;
import com.anz.accounts.repository.entity.Transaction;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

    private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8089));

    @BeforeAll
    public static void setup() {
        WireMock.configureFor("localhost", 8089);
        wireMockServer.start();
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/auth/realms/test/protocol/openid-connect/certs"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("jwks_response.json")));
    }

    @AfterAll
    public static void finish() {
        wireMockServer.stop();
    }

    @Test
    public void testGetAccounts() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Authorization", "Bearer "+ JwtUtil.generateJwtToken());
        HttpEntity request = new HttpEntity<>(httpHeaders);
        ResponseEntity<Account[]> responseEntity = restTemplate.exchange("/api/accounts/testuser", HttpMethod.GET,request, Account[].class);
        AssertionErrors.assertEquals("Invalid Http Status",HttpStatus.OK,responseEntity.getStatusCode());
        AssertionErrors.assertEquals("Incorrect search result",2,responseEntity.getBody().length);
    }

    @Test
    public void testGetTransactions() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Authorization", "Bearer "+ JwtUtil.generateJwtToken());
        HttpEntity request = new HttpEntity<>(httpHeaders);
        ResponseEntity<Transaction[]> responseEntity = restTemplate.exchange("/api/transactions/585309209", HttpMethod.GET,request, Transaction[].class);
        AssertionErrors.assertEquals("Invalid Http Status",HttpStatus.OK,responseEntity.getStatusCode());
        AssertionErrors.assertEquals("Incorrect search result",3,responseEntity.getBody().length);
    }

    @Test
    public void testErrorScenario() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Authorization", "Bearer "+ JwtUtil.generateJwtToken());
        HttpEntity request = new HttpEntity<>(httpHeaders);
        ResponseEntity<ErrorResponse> responseEntity = restTemplate.exchange("/account/testuser", HttpMethod.POST,request, ErrorResponse.class);
        AssertionErrors.assertEquals("Invalid Http Status",HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }

}

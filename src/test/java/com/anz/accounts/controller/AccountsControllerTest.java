package com.anz.accounts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts= {"/scripts/test-accounts.sql"})
public class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAccounts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts/testuser"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].accountNumber").value("585309209"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userId").value("testuser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].accountName").value("SGSavings726"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].accountType").value("Savings"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].currency").value("AUD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].balanceDate").value("2018-11-08"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].availableBalance").value("84327.51"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].accountNumber").value("485309209"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userId").value("testuser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].accountName").value("SGSavings726"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].accountType").value("Savings"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].currency").value("AUD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].balanceDate").value("2018-11-08"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].availableBalance").value("84327.51"));
    }

   @Test
    public void testGetTransactions() throws Exception {
       mockMvc.perform(MockMvcRequestBuilders.get("/api/transactions/585309209"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].transId").value("12345"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].accountNumber").value("585309209"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].valueDate").value("2012-01-12"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].currency").value("SGD"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].amount").value("9540.98"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].transId").value("22345"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].accountNumber").value("585309209"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].valueDate").value("2012-01-12"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].currency").value("SGD"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].amount").value("-234.56"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].transNarrative").value("moving out"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[2].transId").value("32345"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[2].accountNumber").value("585309209"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[2].valueDate").value("2012-01-12"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[2].currency").value("SGD"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[2].amount").value("54.56"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[2].transNarrative").value("moving in"));
    }

    @Test
    public void testErrorScenario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/account/testuser"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}

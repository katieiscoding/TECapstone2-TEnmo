package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import io.cucumber.core.resource.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private String authToken = null;
    private RestTemplate restTemplate = new RestTemplate();

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public BigDecimal getBalance(int userId, int accountId) {

        BigDecimal balance = null;
        Account account = null;

        //Let's test this part once the database is hooked up

        try {
            ResponseEntity<BigDecimal> response =
                    restTemplate.exchange(API_BASE_URL + "users/" + userId + "/accounts/" + accountId, HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            balance = response.getBody();

        } catch (ResourceAccessException e) {
            System.out.println("There was an issue retrieving the balance. Please try again.");
        }

        System.out.println("Your current balance is $" + balance);
        return balance;

    }
        private HttpEntity<Void> makeAuthEntity() {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            return new HttpEntity<>(headers);
        }

    }


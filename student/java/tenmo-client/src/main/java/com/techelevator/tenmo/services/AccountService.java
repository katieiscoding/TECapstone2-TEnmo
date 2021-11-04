package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import io.cucumber.core.resource.Resource;
import okhttp3.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private String authToken = "";
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser = new AuthenticatedUser();

    public AccountService() {
    }


    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public void setAuthenticatedUser(AuthenticatedUser user) {
        this.currentUser = user;
    }

    public BigDecimal getAccount() throws AccountServiceException {

        String path = API_BASE_URL + "accounts/users/";
        ResponseEntity<BigDecimal> response = restTemplate.exchange(path, HttpMethod.GET, makeAuthEntity(),
                BigDecimal.class);
        return response.getBody();
    }

    public User[] getAllUsers() {
        String path = API_BASE_URL + "accounts";
        ResponseEntity<User[]> response = restTemplate.exchange(path, HttpMethod.GET, makeAuthEntity(), User[].class);
        return response.getBody();
    }

    public BigDecimal getBalance() throws AccountServiceException {
    if (this.currentUser == null) {
        throw new AccountServiceException("User is not logged in");
    }
    String path = API_BASE_URL + "accounts/users/";
    ResponseEntity<BigDecimal> response = restTemplate.exchange(path, HttpMethod.GET, makeAuthEntity(),
            BigDecimal.class);
        return response.getBody();
}

        private HttpEntity<Void> makeAuthEntity() {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            return new HttpEntity<>(headers);
        }


        public String printErrorMessage() {
        return "Error occurred";
        }

    }


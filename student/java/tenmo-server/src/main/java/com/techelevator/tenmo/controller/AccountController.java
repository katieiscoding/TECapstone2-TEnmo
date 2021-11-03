package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/users")

//This @ may need to change - one person can't access just anyone's account

@PreAuthorize("isAuthenticated()")

public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(value="/{user_id}/accounts/{account_id}", method = RequestMethod.GET)

    public BigDecimal getBalance(Principal principal, @PathVariable int user_id, @PathVariable int account_id) {
        return accountDao.getBalance(user_id, account_id);
    }
}

package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping(path="/accounts")

@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public BigDecimal getAccountByUserId(Principal principal)
            throws IllegalAccessException {
        int currentUserId = userDao.findIdByUsername(principal.getName());
        return accountDao.getAccountByUserId(currentUserId).getBalance();
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Account> getAllUsers() {
        return accountDao.getAllUsers();
    }
}



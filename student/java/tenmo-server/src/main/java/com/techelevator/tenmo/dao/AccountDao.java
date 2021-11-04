package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface AccountDao {
//    BigDecimal getBalance(Principal principal);
      Account getAccountByUserId(int userId);
      List<Account> getAllUsers();
      String getUsernameById(int userId);
}

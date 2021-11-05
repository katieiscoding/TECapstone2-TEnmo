package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getUsernameById(int userId) throws UsernameNotFoundException {
        String sql = "SELECT username FROM users WHERE user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (rowSet.next()) {
            return rowSet.getString("username");
        }
        throw new UsernameNotFoundException("No users were found.");
    }

    @Override
    public Account getAccountByUserId(int userId) {
        Account account = null;
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        while (result.next()) {
            account = mapRowToAccount(result);
        }
        return account;
    }

    public List<Account> getAllUsers() {
        List<Account> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
            allUsers.add(mapRowToAccount(result));
        }
        return allUsers;
    }


    public void addToBalance(@org.jetbrains.annotations.NotNull Transfer transfer) {
        String sql = "UPDATE accounts SET balance = (balance + ?) WHERE user_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccount_to());
    }

    public void subtractFromBalance(Transfer transfer) {
        String sql = "UPDATE accounts SET balance = (balance - ?) WHERE user_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccount_from());
    }


    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setUsername(getUsernameById(rs.getInt("user_id")));
        return account;
    }

}

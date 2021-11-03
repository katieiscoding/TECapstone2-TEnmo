package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.math.BigDecimal;

public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public BigDecimal getBalance(int user_id, int account_id) {
        BigDecimal balance = null;
        Account account = null;
        String sql = "SELECT * FROM accounts WHERE user_id = ? AND account_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, user_id, account_id);
        if (result.next()) {
            account = mapRowToAccount(result);
            balance = account.getBalance();
        }
        return balance;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }

}

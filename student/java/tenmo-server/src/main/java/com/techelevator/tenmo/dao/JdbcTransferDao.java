package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {




    // TODO - we need to:
    // DONE! remove money from user account (UPDATE balance IN accounts WHERE user_id = ?)
    // DONE! add money to other user account (UPDATE balance IN accounts WHERE user_id = ?)
    // DONE! create new row in the transfer table (INSERT INTO transfers VALUES ...)
    // DONE! update transfer status (UPDATE transfer_status_id IN transfers WHERE transfer_id = ?)

    @Override
    public Transfer sendBucks() {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE user_id = ?; " +
                "UPDATE accounts SET balance = balance + ? WHERE user_id = ?'; " +
                "INSERT INTO transfers VALUES ('DEFAULT', ?, ?, ?, ?);";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, sendBucks().getAmount(), sendBucks().getAccount_from(),
                sendBucks().getAmount(), sendBucks().getAccount_to());
        Transfer transfer = mapRowToTransfer(results);

    return transfer;
    }

// #7 - optional use case

//    @Override
//    public Transfer requestBucks() {
//        Transfer transfer = new Transfer();
////        String sql = "SELECT "
//
//        return transfer;
//    }

    @Override
    public List<Transfer> getTransfersByUserId() {
        List<Transfer> transfersByUserId = new ArrayList<>();
//        String sql = "SELECT "

        return transfersByUserId;
    }

    @Override
    public List<Transfer> getTransfersByTransferId() {
        List<Transfer> transfersByTransferId = new ArrayList<>();
//        String sql = "SELECT "

        return transfersByTransferId;
    }



    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rs.getInt("transfer_id"));
        transfer.setTransfer_type_id(rs.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rs.getInt("transfer_status_id"));
        transfer.setAccount_from(rs.getInt("account_from"));
        transfer.setAccount_to(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}



//for reference ---------
//    @Override
//    public Account getAccountByUserId(int userId) {
//        Account account = null;
//        String sql = "SELECT * FROM accounts WHERE user_id = ?;";
//        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
//        while (result.next()) {
//            account = mapRowToAccount(result);
//        }
//        return account;
//    }


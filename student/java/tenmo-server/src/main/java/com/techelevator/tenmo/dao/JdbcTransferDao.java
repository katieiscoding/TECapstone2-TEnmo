package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        @Override
        public void sendBucks(Transfer transfer) {
            String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, transfer.getTransfer_type_id(), transfer.getTransfer_status_id(),
                    transfer.getAccount_from(), transfer.getAccount_to(), transfer.getAmount());
        }

        @Override
        public List<Transfer> getTransfersByUserId(int userId) {
            List<Transfer> transfersByUserId = new ArrayList<>();
            String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfers JOIN accounts ON accounts.account_id = transfers.account_from WHERE user_id = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Transfer transfer = mapRowToTransfer(results);
                transfersByUserId.add(transfer);
            }
            return transfersByUserId;
        }

        @Override
        public Transfer getTransfersByTransferId(int transferId) {
            Transfer transfer = new Transfer();
            String sql = "SELECT * FROM transfers WHERE transfer_id = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            if (results.next()) {
                transfer = mapRowToTransfer(results);
            }
            return transfer;
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



package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    public void sendBucks(Transfer transfer);
    public List<Transfer> getTransfersByUserId(int userId);
    public Transfer getTransfersByTransferId(int transferId);

}

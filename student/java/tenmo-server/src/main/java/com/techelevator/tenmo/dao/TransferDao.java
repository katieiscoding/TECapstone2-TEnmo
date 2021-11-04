package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    public Transfer sendBucks();
//  public Transfer requestBucks();
    public List<Transfer> getTransfersByUserId();
    public List<Transfer> getTransfersByTransferId();

}

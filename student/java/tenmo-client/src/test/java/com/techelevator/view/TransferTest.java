package com.techelevator.view;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.services.AccountService;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TransferTest {

    private static final Transfer TRANSFER_1 = new Transfer(3001, 2, 2, 2001, 2002, BigDecimal.valueOf(0.50));
    private static final Transfer TRANSFER_2 = new Transfer(3002, 2, 2, 2001, 2001, BigDecimal.valueOf(400.50));
    private static final Transfer TRANSFER_3 = new Transfer(3003, 2, 2, 2001, 2002, BigDecimal.valueOf(1500.00));
    private static final Transfer TRANSFER_4 = new Transfer(3004, 2, 2, 2001, 2002, BigDecimal.valueOf(0.00));
    //    private JdbcTransferDao sut;
    @Test
    public void view_transfer_by_transferId_returns_transfer_info() {
        AccountService accountService = new AccountService();
        Transfer transfer = accountService.getTransferByTransferId(3001);
        Assert.assertEquals(2, transfer.getTransfer_type_id());
        Assert.assertEquals(2, transfer.getTransfer_status_id());
        Assert.assertEquals(2001, transfer.getAccount_from());
        Assert.assertEquals(2002, transfer.getAccount_to());
        Assert.assertEquals(BigDecimal.valueOf(0.50), transfer.getAmount());
    }

    @Test
    public void view_transfer_by_transferId_invalid_transferId_returns_nothing() {
        AccountService accountService = new AccountService();
        Transfer transfer = accountService.getTransferByTransferId(9999);
        Assert.assertNull(transfer);

    }

    @Test
    public void view_transfer_by_transferId_returns_transfer_infox() {
        AccountService accountService = new AccountService();
        Transfer transfer = accountService.getTransferByTransferId(3001);
        Assert.assertEquals(2, transfer.getTransfer_type_id());
        Assert.assertEquals(2, transfer.getTransfer_status_id());
        Assert.assertEquals(2001, transfer.getAccount_from());
        Assert.assertEquals(2002, transfer.getAccount_to());
        Assert.assertEquals(BigDecimal.valueOf(0.50), transfer.getAmount());
    }
}

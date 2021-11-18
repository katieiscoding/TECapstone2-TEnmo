package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path="/transfers")

public class TransferController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;


    public TransferController(AccountDao accountDao, UserDao userDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void sendBucks(Principal principal, @RequestBody Transfer transfer)
            throws IllegalTransactionStateException {
        transferDao.sendBucks(transfer);
        accountDao.addToBalance(transfer);
        accountDao.subtractFromBalance(transfer);
    }




    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public List<Transfer> listOfTransfersByUserId(@RequestBody @PathVariable int userId) {
        return transferDao.getTransfersByUserId(userId);
    }

    @RequestMapping(path = "/{transferId}", method = RequestMethod.GET)
    public Transfer getTransfersByTransferId(@PathVariable int transferId) {
        return transferDao.getTransfersByTransferId(transferId);
    }



}

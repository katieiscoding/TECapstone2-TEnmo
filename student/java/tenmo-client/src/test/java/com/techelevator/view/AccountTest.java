package com.techelevator.view;
import com.techelevator.tenmo.model.Account;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountTest {

    private static final Account ACCOUNT_1 = new Account(1000, 2002,
            "Katie", BigDecimal.valueOf(1000.00));
    private static final Account ACCOUNT_2 = new Account(1001, 2003,
            "Garrett", BigDecimal.valueOf(1000.00));
    private static final Account ACCOUNT_3 = new Account(1002, 2004,
            "Jason", BigDecimal.valueOf(1000.00));

//    private JdbcAccountDao sut;

    @Test
    public void view_current_balance_returns_balance() {

    }

    @Test
    public void sendBucks_sends_bucks() {

    }


}

package com.techelevator.tenmo;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AccountServiceException;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;
import java.math.BigDecimal;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_VIEW_TRANSFERS_BY_TRANSFER_ID = "View information about a specific transfer";
//	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
//	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_VIEW_TRANSFERS_BY_TRANSFER_ID, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
//  removed " MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_REQUEST_BUCKS," from above array
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
	private AccountService accountService;


    public static void main(String[] args) throws Exception {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new AccountService());
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, AccountService accountService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService = accountService;
	}

	public void run() throws Exception {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() throws Exception {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_TRANSFERS_BY_TRANSFER_ID.equals(choice)) {
				viewTransferByTransferId();
//			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
//				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
//			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
//				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() throws AccountServiceException {
    	//TODO prompt for user input
		BigDecimal balance = accountService.getBalance();
		System.out.println("Your current balance is: $" + balance);
	}

	private void viewTransferHistory() {
    	int userId = console.getUserInputInteger("Enter your user ID");
		Transfer[] transfers = accountService.getListOfTransfersByUserID(userId);
		for (Transfer transfer : transfers) {
			System.out.println(transfer.toString());
		}
	}



	private void viewTransferByTransferId() {
		int transfer_id = console.getUserInputInteger("Enter the desired transfer ID");

		System.out.println(accountService.getTransferByTransferId(transfer_id).toString());
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() throws AccountServiceException, Exception {
		viewAllAccounts();
		int senderAccountId = console.getUserInputInteger("Enter your account ID");
		int recipientAccountId = console.getUserInputInteger("Enter the desired recipient's account ID");
		String amountToTransfer = console.getUserInput("Enter the amount of money you want to send");
		Double amountToTransferAsDouble = Double.valueOf(amountToTransfer);
		Transfer transfer = new Transfer();

		transfer.setAccount_from(senderAccountId);
		BigDecimal amountToTransferAsBigDecimal = BigDecimal.valueOf(amountToTransferAsDouble);
		transfer.setAmount(amountToTransferAsBigDecimal);
		transfer.setTransfer_status_id(2);
		transfer.setTransfer_type_id(2);
		transfer.setAccount_to(recipientAccountId);
		if (transfer.getAmount().compareTo(accountService.getBalance()) > 0) {
			System.out.println("You can't overdraw your account.");
			return;
		}
		if (transfer.getAmount().compareTo(BigDecimal.ZERO) < 0) {
			System.out.println("Please enter an amount greater than zero.");
			return;
		}
		accountService.sendBucks(transfer);

		System.out.println("Transfer complete.");
	}

		private void viewAllAccounts() {
    	Account[] allUsers = accountService.getAllAccounts();

			System.out.println("Available users:\n");
    	for(Account a : allUsers) {

//    		if (a.getUsername().equals(currentUser.getUser().getUsername())) {
//				break;
//			}
			System.out.println("\b");

			// just commenting this out to check

			System.out.println(a.getUsername() + ":  User ID #" + a.getUserId() + "  |  Account ID #" + a.getAccountId());
		}
			System.out.println("\b");

	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now log in.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
				accountService.setAuthToken(currentUser.getToken());
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}

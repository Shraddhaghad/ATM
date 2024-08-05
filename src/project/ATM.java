package project;
import java.util.Scanner;
public class ATM {

	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		Bank  theBank = new Bank("Bank of Maharastra");
		//add user ,which also create a Savings Account
		User aUser = theBank.addUser("Shraddha", "Ghadge", "1234");

		//add a cheacking Accounts for our user
		Account newAccount = new Account("Checking",aUser,theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);

		User curUser;
		while(true) {
			//Stay in the login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank ,sc);
			//stay in main manu until User quits
			ATM.printUserMenu(curUser , sc);

		}
	}
	public static User mainMenuPrompt(Bank theBank ,Scanner sc) {
		String userID;
		String pin;
		User authUser;
		//prompt the user for user ID/pin combo umtil a correct one is reached
		do {
			System.out.printf("\n\nWelcome to %s\n\n",theBank.getName());
			System.out.print("Enter user ID:");
			userID = sc.nextLine();
			System.out.printf("Enter Pin : ");
			pin = sc.nextLine();

			//try to get the user object corresponding to the ID and pin combo
			authUser = theBank.userLogin(userID , pin);
			if(authUser == null) {
				System.out.println("Incorrect user ID/pin combination," + "Please try again.");
			}
		}	while(authUser == null);//continue looping until  successful login

		return authUser;

	}

	public static void printUserMenu(User theUser, Scanner sc) {
		//print a summary of tne user's accounts
		theUser.printAccountSummary();
		//init
		int  choice;
		//user menu
		do {
			System.out.printf("Welcom %s,what would you like to do?\n",
					theUser.getfirstName());
			System.out.println("  1)Show account transaction history");
			System.out.println("  2)Withdrawl");
			System.out.println("  3)Deposit");
			System.out.println("  4)Tranfer");
			System.out.println("  5)Quit");
			System.out.println();
			System.out.print("Enter Choice: ");
			choice = sc.nextInt();
			if(choice<1 || choice>5) {
				System.out.println();
			}

		}while(choice < 1 ||choice > 5);
		//process the choice
		switch(choice) {
		case 1:
			ATM.showTransHistory(theUser,sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser,sc);
			break;
		case 3:
			ATM.depositeFunds(theUser,sc);
			break;
		case 4:
			ATM.transferFunds(theUser ,sc);
			break;
		}
		//redisplay this menu unless the user wants to quit
		if(choice != 5) {
			ATM.printUserMenu(theUser,sc);

		}
	}
	/**
	 * 
	 * @param theUser
	 * @param sc
	 */
	public static void showTransHistory(User theUser , Scanner sc) {
		int theAcct;
		//get Account whoes transaction history to look at
		do {
			System.out.printf("Enter the number (1-%d0)) of the account" +
					"whoes transactions you want to see :",
					theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if(theAcct <0||theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account.Please try again.");
			}
		}while(theAcct <0 || theAcct >= theUser.numAccounts());
		//print the transaction history
		theUser.printAcctTransHistory(theAcct);
	}

	/**
	 * 
	 * @param theUser
	 * @param sc
	 */
	public static void transferFunds(User theUser , Scanner sc) {
		//inits
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		//get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d)of the account\n"+"to transfer from: ",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct<0||fromAcct>=theUser.numAccounts())
			{
				System.out.println("Invalid account.Please try again.");
			}
		}while(fromAcct <0 || fromAcct >= theUser.numAccounts());

		acctBal = theUser.getAcctBalance(fromAcct);

		//get the account to transfer to
		do {
			System.out.printf("Enter the number (1-%d)of the account\n"+"to transfer from: ",theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct<0||toAcct>=theUser.numAccounts())
			{
				System.out.println("Invalid account.Please try again.");
			}
		}while(toAcct <0 || toAcct >= theUser.numAccounts());
		//get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer(max $%.02f):$",acctBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than Zero ");
			}else if(amount > acctBal) {
				System.out.printf("Amount must not be greater than\n"+"balance od $%.02f\n",acctBal);
			}
		}while(amount < 0 || amount > acctBal);
		//finally do the transfer
		theUser.addAcctTransaction(fromAcct,-1*amount,String.format("Transfer to account %s",theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct,-1*amount,String.format("Transfer to account %s",theUser.getAcctUUID(fromAcct)));
	}

	/**
	 * 
	 * @param theUser
	 * @param sc
	 */
	public static void withdrawFunds(User theUser , Scanner sc) {
		//inits
		int fromAcct;
		String memo;
		double amount;
		double acctBal;
		//get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d)of the account\n"+"to Withdraw from: ",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if(fromAcct<0||fromAcct>=theUser.numAccounts())
			{
				System.out.println("Invalid account.Please try again.");
			}
		}while(fromAcct <0 || fromAcct >= theUser.numAccounts());

		acctBal = theUser.getAcctBalance(fromAcct);
		do {
			System.out.printf("Enter the amount to transfer(max $%.02f):$",acctBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than Zero ");
			}else if(amount > acctBal) {
				System.out.printf("Amount must not be greater than\n"+"balance od $%.02f\n",acctBal);
			}
		}while(amount < 0 || amount > acctBal);
		//gobble up rest of previous input
		sc.nextLine();

		//get the memo
		System.out.println("Enter a memo : ");
		memo= sc.nextLine();

		//do the withdrawl
		theUser.addAcctTransaction(fromAcct,-1*amount,memo);
	}


	/**
	 * 
	 * @param theUser
	 * @param sc
	 */
	public static void depositeFunds(User theUser , Scanner sc) {
		//inits
		int toAcct;
		String memo;
		double amount;
		double acctBal;
		//get the account to transfer from
		do {
			System.out.printf("Enter the number (1-%d)of the account\n"+"to diposit in: ",theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if(toAcct<0||toAcct>=theUser.numAccounts())
			{
				System.out.println("Invalid account.Please try again.");
			}
		}while(toAcct <0 || toAcct >= theUser.numAccounts());

		acctBal = theUser.getAcctBalance(toAcct);
		do {
			System.out.printf("Enter the amount to transfer(max $%.02f):$",acctBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than Zero ");
			}
		}while(amount < 0);
		//gobble up rest of previous input
		sc.nextLine();

		//get the memo
		System.out.println("Enter a memo : ");
		memo= sc.nextLine();

		//do the withdrawl
		theUser.addAcctTransaction(toAcct,amount,memo);
	}
}

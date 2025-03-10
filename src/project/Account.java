package project;

import java.util.ArrayList;

public class Account {

	private String name;

	private String uuid;
	private User holder;
	private ArrayList<Transaction>transactions;
	public Account(String name,User holder,Bank theBank) {
		//set the account name and holder
		this.name = name;
		this.holder = holder;

		//get new account UUID
		this.uuid = theBank.getNewAccountUUID();
		this .transactions = new ArrayList<Transaction>();

	}
	public String getUUID() {
		return this.uuid;
	}
	public String getSummaryLine() {
		//get account's balance
		double balance = this.getBalance();

		//format the summary line,depending on the whether the balance is
		//negitive
		if(balance >=0) {

			return String.format("%S : $%.02f : %s" , this.uuid,balance, this.name );
		}else {
			return String.format("%S : $(%.02f) : %s" , this.uuid,balance, this.name );

		}
	}
	public double getBalance() {

		double balance =0;
		for(Transaction t: this.transactions) {
			balance +=t.getAmount();

		}
		return balance;
	}
	/**
	 * 
	 */
	public void printTransHistory() {
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for(int t=this.transactions.size()-1; t>=0;t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}
	/**
	 * 
	 * @param amount
	 * @param memo
	 */
	public void addTransaction(double amount,String memo) {
		//create new transaction object and add it to our list
		Transaction newTrans = new Transaction(amount,memo,this);
		this.transactions.add(newTrans);
	}
}


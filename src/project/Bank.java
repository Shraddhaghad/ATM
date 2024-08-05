package project;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String name;
	private ArrayList<User>users;
	private ArrayList<Account>accounts;
	public Bank(String name) {
		this.name = name;
		this.users=new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	public String getNewUserUUID() {
		//inits
		String uuid;
		Random rng = new Random();
		int len=6;
		boolean nonunique;
		do {
			uuid = "";
			for(int c=0;c<len;c++) {
				uuid +=((Integer)rng.nextInt(10)).toString();
			}
			//check to make sure it's unique
			nonunique=false;
			for(User u : this.users) {
				if (uuid.compareTo(u.getUUID())==0){
					nonunique = true;
					break;
				}
			}

		}while(nonunique);

		return uuid;

	}
	public String getNewAccountUUID() {
		//inits
		String uuid;
		Random rng = new Random();
		int len=6;
		boolean nonunique;
		do {
			uuid = "";
			for(int c=0;c<len;c++) {
				uuid +=((Integer)rng.nextInt(10)).toString();
			}
			//check to make sure it's unique
			nonunique=false;
			for(Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID())==0){
					nonunique = true;
					break;
				}
			}

		}while(nonunique);

		return uuid;

	}
public void addAccount(Account anAcct) {
	this.accounts.add(anAcct);
	
}

	public User addUser(String firstName , String lastName, String pin) {

		//Create a new User Object and add it to our list
		User newUser = new User(firstName , lastName, pin,this);
		this.users.add(newUser);
		//create a saving account for the user
		Account newAccount = new Account("Saving",newUser,this);
		newUser.addAccount(newAccount);

		this.addAccount(newAccount);

		return newUser;

	}
	public User userLogin(String userID , String pin) {
		//Search Through list of users
		for(User u:this.users) {

			//check user ID is correct
			if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		return null;
	}
	public String getName() {
		return this.name;
	}
}
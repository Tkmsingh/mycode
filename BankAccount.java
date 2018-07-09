package bankaccountapp;

public abstract class Account implements IRate{
	//list  common property for both account
	private String name;
	private String SSN;
	private static int uniqueID = 10000;
	protected String accountnumber;
	protected double balance;
	protected double rate;
	
	// Constructor to set basic property and intialization
	public Account(String name, String SSN, double intideposite) {
		this.name=name;
		this.SSN = SSN;
		balance = intideposite;
		uniqueID++;
		
		// set accountnumber
		this.accountnumber = SetAccountnumber();
		//System.out.println("Account no: "+this.accountnumber);
		setrate();
	}
	public abstract void setrate();
	
	private String SetAccountnumber() {
		String lasttwodigit = SSN.substring(SSN.length()-2, SSN.length());
		int index = this.uniqueID;
		int randomNUM = (int)(Math.random()*Math.pow(10,3));
		return lasttwodigit+index+randomNUM;
	}
	//public abstract double Setrate();
	// list common methods
	public void compound() {
		double accruedInterest = balance * (rate/100);
		balance += accruedInterest;
		System.out.println("AcuredInterest is: "+accruedInterest);
		printbalance();
	}
	public void deposite(double amount) {
		balance += amount;
		printbalance();
		
	}
	public void withdraw(double amount) {
		balance -= amount;
		printbalance();
	}
	public void transfer(String where, double amount) {
		balance -= amount;
		System.out.println("Transfering amount $"+amount+" to "+where);
	}
	public void printbalance() {
		System.out.println("Updated balance is: "+balance);
	}
	public void showinfo() {
		System.out.println(
				"NAME: "+name+
				"\nAccountNumber: "+accountnumber+
				"\nBalance: "+balance);
		
	}
}


package bankaccountapp;

public interface IRate {
	// WAM to return base rate
	default double getbaserate() {
		return 2.5;
	}
}


package bankaccountapp;

import java.util.LinkedList;
import java.util.List;

public class BankAccountApp {
	
	public static void main(String[] args) {

		List<Account> accounts = new LinkedList<Account>();
		String file = new String("C:\\Users\\Dev.computers\\Downloads\\NewBankAccounts.csv");
		
		List<String[]> data = CSV.read(file);
		for(String[] AccountHolder : data) {
			String name =AccountHolder[0];
			String SSN=AccountHolder[1];
			String Accounttype = AccountHolder[2];
			Double intideposite = Double.parseDouble(AccountHolder[3]);
			
			if(Accounttype.equals("Savings")) 
				accounts.add(new SavingAccount(name,SSN,intideposite));
			else if(Accounttype.equals("Checking"))
				accounts.add(new CheckingAccount(name,SSN,intideposite));
			else
				System.out.println("None");		
		
		}
		for(Account acc : accounts) {
			System.out.println("\n***************");
			acc.showinfo();
		}		
	}
		
}


package bankaccountapp;

public class CheckingAccount extends Account {
	// list specific property for checking account
	int debitcardNumber;
	int debitcardPIN;
	//Constructor to intialize property for checking account
	public CheckingAccount(String name,String SSN, double intideposite) {
		super(name,SSN,intideposite);
		accountnumber = "C"+ accountnumber;		
		SetDebitCardDetails();
		
	}
	@Override
	public void setrate() {		
		rate = getbaserate()*.15;
	}
	private void SetDebitCardDetails() {
		this.debitcardNumber = (int)(Math.random()*Math.pow(10, 12));
		this.debitcardPIN = (int)(Math.random()*Math.pow(10, 4));
	}
	public void showinfo(){
		super.showinfo();
		System.out.println("Checking Account features: "+
							"\nDebit card No: "+debitcardNumber+
							"\nDebit Card PIN: "+debitcardPIN+
							"\nRate: "+rate+"%");
	}	
	
}


package bankaccountapp;

public class SavingAccount extends Account {
	// list specific property for Saving account
	private int safetyboxkeyID;
	private int safetyboxPIN;
	
		//Constructor to intialize property for Saving account
	public SavingAccount(String name,String SSN, double intideposite) {
		super(name,SSN,intideposite);
		this.accountnumber = "S"+ accountnumber;
		SetspecificFeature();
		
	}
	@Override
	public void setrate() {		
		rate = getbaserate()-.25;
	}
	private void SetspecificFeature() {
		this.safetyboxkeyID = (int)(Math.random()*Math.pow(10,3));
		this.safetyboxPIN = (int)(Math.random()*Math.pow(10,4));
	}
	public void showinfo(){
		super.showinfo();
		System.out.println("Saving Accout Feature:"+
							"\nID :"+safetyboxkeyID+
							"\nPIN :"+safetyboxPIN+
							"\nRate: "+rate);
	}
	
}

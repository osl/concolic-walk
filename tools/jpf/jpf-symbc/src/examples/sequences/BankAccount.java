package sequences;

import gov.nasa.jpf.symbc.Preconditions;



/**
 *
 * @author Mithun Acharya
 * Taken from Inkumsah, Xie's ASE08 paper
 */
public class BankAccount {
	private int balance;
	private int numberOfWithdrawals;


	public BankAccount(int amount){
		balance = amount;
	}

    //@Preconditions("amount<1000&&amount>-1000")
	public void deposit(int amount){
		if (amount>0)
			//System.out.println("I am easily reachable in deposit");
			balance = balance + amount;
	}

    //@Preconditions("amount<1000&&amount>-1000")
	public void withdraw(int amount){
		if(amount>balance){
			//System.out.println("I am easily reachable in withdraw");
			return;
		}
		if (numberOfWithdrawals>=5){// was 10
			assert(false);
			//System.out.println("I am very hard to reach in withdraw");
			return;
		}
		balance = balance - amount;
		numberOfWithdrawals++;
	}
}

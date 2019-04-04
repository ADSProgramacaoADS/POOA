package flist;

import java.util.function.Function;

class Operations {
	public final int balance;
	public final int value;
	public Operations (int balance, int value) {
		this.balance = balance;
		this.value = value;
	}
	public String toString(){
		return "(" + balance + "," + value + ")";
	}
}

class Account{
	
	public final String name;
	public final int id;
	public final FList<Operations> history;
	private Account(String name, int id) {
		this.name = name;
		this.id = id;
		history = FList.<Operations>empty();
	
	}
	
	private Account(String name, int id,  FList<Operations> history) {
		this.name = name; 
		this.id = id;
		this.history = history;
	}
	
	public Account movement(int value) {
		int bal = history.isEmpty() ? 0 : history.head().balance;
		Operations op = new Operations(bal + value,value);
		FList<Operations> list = FList.<Operations>cons(op, history);
		return new Account(name, id, list);
	}
	
	public String toString() {
		return "( "+ name + ", " + id +")"+ history +"\n";
	}
	
	private FList<Integer> valDeps(FList<Operations> deps){
		if(deps.isEmpty()) return FList.<Integer>empty();
		FList<Integer> rest = valDeps(deps.tail());
		Integer val = deps.head().value;
		return FList.<Integer>cons(val, rest);
	}
	
	public FList<Integer> valDeps(){
		return deposits().map(op -> op.value);
	}
	
	public FList<Operations> deposits(){
		return history.filter((Operations op)-> op.value > 0);
	}
	
	public FList<Operations> withdraws(){
		return history.filter((Operations op)-> op.value < 0);
	}
	
	public FList<Operations> higherDeps(int val){
		return history.filter((Operations op)-> op.value > val);
	}
	
	private int totalDep(FList<Operations> xs) {
		if(xs.isEmpty()) {
			return 0;
		}
		int sumRest = totalDep(xs.tail());
		if(xs.head().value > 0) {
			return xs.head().value + sumRest;
		}
		return sumRest;
	}
	
	public int totalDep() {
		return valDeps().foldRight(0, (x, y) -> x + y);
	}
		
	
	public int maxDep(){
		Flist<Interger> deps = valDeps();
		if(deps.isEmpty()) throw new NoSuchAttributeException();
		return deps.foldLeft(deps.head(), (x,y) -> x>=y ? x:y);
	}
	
	public static Account newAccount(String name) {
		return new Account(name);
	}
}
public class Bank{
	
	
	public final FList<Account> accounts;
	
	private Bank(){accounts = FList.<Account>empty();}
	
	private Bank(FList<Account> accounts){
		this.accounts = accounts};
		
	
	public Bank newAccount (String name){
		Account acc = Account.newAccount(name, accounts.size +1);
		return new Bank(FList.<Account>cons(acc, accounts));
		
	}
	public Account getAccount(int id){
	FList<Account> list = accounts.find(acc -> acc.id== id); 
	if(list.isEmpty()) throw new NoSuchElementException();
	return list.head();
	}
	
	publlic Account getAccount(String name){
		FList<Account> list = accounts.find(acc -> name.equals(acc.name)); 
	if(list.isEmpty()) throw new NoSuchElementException();
	return list.head();
		
	}
	public static Bank newBank() {return new Bank();}
	
	
	public static void main(String[] args) {
		Bank b = newBank().newAccount ("Jose")
						.newAccount ("Maria")
						.newAccount ("Adriana")
						.newAccount ("Joao");
		
		/*Account acc = Account.newAccount("Franklin")
				.movement(80)
				.movement(90)
				.movement(10)
				.movement(50)
				.movement(-4)
				.movement(70)
				.movement(-20);
	System.out.println(acc);
	System.out.println(acc.deposits());
	System.out.println(acc.valDeps().sort((x,y) -> x.compareTo(y)));
	System.out.println(acc.totalDep());
	System.out.println(acc.maxDep());*/
	}
}

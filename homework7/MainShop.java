package homework7;

public class MainShop {

	public static void main(String[] args) {
		Cashier cashier = new Cashier();
		Buyer b1 = new Buyer(cashier, "A");
		Buyer b2 = new Buyer(cashier, "B");
		Buyer b3 = new Buyer(cashier, "C");
		Buyer b4 = new Buyer(cashier, "D");
		Buyer b5 = new Buyer(cashier, "E");
		
	}
}

package homework7;


public class Buyer implements Runnable {
	Cashier cashier;
	String name;

	public Buyer(Cashier cashier, String name) {
		this.cashier = cashier;
		this.name = name;
		new Thread(this).start();
	}

	@Override
	public void run() {
		
		while(true) {
		synchronized (this) {
			cashier.rand = (int)(Math.random()*10);
		synchronized (cashier) {
			
			if(cashier.rand >3) {
			if(!cashier.switchBool) {
				System.out.println("The cash is free");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cashier.switchBool = true;
			}else {
				
				System.out.println(name + " is staying at the cash");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cashier.switchBool = false;
				break;
				}
			} else {
				System.out.println("The cash is closed!!!");
				break;
			}
			
		}
		}
		
		}
	}


}

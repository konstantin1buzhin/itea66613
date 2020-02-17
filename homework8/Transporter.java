package homework8;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class Transporter implements Runnable {
	private Exchanger<Integer> exLoad;
	private Exchanger<Integer> exOffLoad;
	Cart cart;
	private int mat;
	private int met;

	public Transporter(Exchanger<Integer> exLoad, Exchanger<Integer> exOffLoad, Cart cart) {
		this.exLoad = exLoad;
		this.exOffLoad = exOffLoad;
		this.cart = cart;
		this.mat = new Integer(mat);
		this.met = new Integer(met);
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (mat >= 0) {
			try {
				mat = exLoad.exchange(mat);
				if (mat == 0) {
					met = exOffLoad.exchange(mat);
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Transporter is going with " + mat + " kg");
			try {
				met = exOffLoad.exchange(mat);
				TimeUnit.MILLISECONDS.sleep(500);
				if(cart.capacity==0) {
					System.out.println("Transporter is going back");
					cart.capacity = exLoad.exchange(0);
					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

package homework8;

import java.util.concurrent.Exchanger;

public class OffLoader implements Runnable {
	private Exchanger<Integer> exOffLoad;
	private int met;
	Cart cart;

	public OffLoader(Exchanger<Integer> exOffLoad, Cart cart) {
		this.exOffLoad = exOffLoad;
		this.met = new Integer(met);
		this.cart = cart;
		new Thread(this).start();
	}

	@Override
	public void run() {
		do {
			try {
				met = exOffLoad.exchange(met);
				if (met == 0) {
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("OffLoader uploaded " + met + " kg");
			cart.capacity = 0;

		} while (met>=0);

	}

}

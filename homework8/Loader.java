package homework8;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class Loader implements Runnable {
	private Exchanger<Integer> exLoad;
	Cart cart;
	private int mat;
	int materials = 100;

	public Loader(Exchanger<Integer> exLoad, Cart cart) {
		this.exLoad = exLoad;
		this.mat = new Integer(mat);
		this.cart = cart;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (materials >= 0) {
			if (materials == 0) {
				mat = 0;
				try {
					mat = exLoad.exchange(cart.capacity);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Store is empty");
				break;
			} else {
				for(int i=0;i<3;i++) {
					if(materials ==1) {
						cart.capacity=1;
						materials -= 1;
						break;
					}
				cart.capacity += 1;
				materials -= 1;
				}
			}
			System.out.println("There are "+materials+" materials in the store");
			System.out.println();
			
			try {
				System.out.println("Loader loaded " + cart.capacity + " kg");
				mat = exLoad.exchange(cart.capacity);
				TimeUnit.SECONDS.sleep(2);
				if(cart.capacity==0) {
					System.out.println("Transporter has gone back");
					mat = exLoad.exchange(0);
					TimeUnit.SECONDS.sleep(1);
					System.out.println();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}

package homework8;

import java.util.concurrent.Exchanger;

public class MainStore {

	public static void main(String[] args) {
		Exchanger<Integer> exLoad = new Exchanger<Integer>();
		Exchanger<Integer> exOffLoad = new Exchanger<Integer>();
		Cart cart = new Cart();
		new Loader(exLoad, cart);
		new Transporter(exLoad, exOffLoad, cart);
		new OffLoader(exOffLoad, cart);
	}
}

package ukr.newRandom;

import java.util.ArrayList;
import java.util.Iterator;

import ownLibrary.myThread.NewThread;


public class MainApp {
	public static void main(String[] args) {
		System.out.println(Thread.activeCount());
		NewThread thread1 = new NewThread("A");
		NewThread thread2 = new NewThread("B");
		thread1.start();
		thread2.start();
		System.out.println(Thread.activeCount());
		while (thread1.isAlive() && thread2.isAlive()) {

		}
		int sumFirst = sum(thread1.getCollectionInt());
		int sumSecond = sum(thread2.getCollectionInt());
		System.out.println(thread1.getNameThread() + ": " + thread1.getCollectionInt() + sumFirst);

		System.out.println(thread2.getNameThread() + ": " + thread2.getCollectionInt().toString() + sumSecond);

		if (sumFirst > sumSecond) {
			System.out.println(thread1.getNameThread() + " is bigger");
		} else {
			System.out.println(thread2.getNameThread() + " is bigger");
		}
	}

	public static int sum(ArrayList<Integer> list) {
		// iterator for accessing the elements
		Iterator<Integer> it = list.iterator();

		int res = 0;
		while (it.hasNext()) {
			int num = it.next();

			res += num;
		}

		return res;
	}
}

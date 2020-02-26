package application;

import java.lang.ref.WeakReference;

import javafx.scene.control.TextField;

public class DogCache {

	WeakReference<Dog> cache;

	public Dog getDog(String name, int age, Owner owner, TextField textField) {
		if (cache == null || cache.get() == null) {
			cache = new WeakReference<Dog>(new Dog(name, age, owner));
//			System.out.println("New Dog");
			textField.setText("New Dog is created");
		} else {
			textField.setText("Old Dog is created");
		}
		return cache.get();
	}

}

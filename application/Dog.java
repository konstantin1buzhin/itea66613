package application;

import java.io.Serializable;

public class Dog extends Animal implements Serializable{

	private Owner owner;

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	

	public Dog(String name, int age, Owner owner) {
		super(name, age);
		this.owner = owner;
	}

	public Dog() {
	}

	@Override
	public String toString() {
		return "Dog [owner=" + owner + ", getName()=" + getName() + ", getAge()=" + getAge() + "]";
	}
	
	
	
}

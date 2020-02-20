package homework9;

public class Cat extends Animal{

	@LuckyCat(luckyAnimal = false)
	private int ageAtHome;
	
	@Paw(countRaws = 4)
	@Override
	public String toString() {
		return "Cat [getName()=" + getName() + ", getColor()=" + getColor() + ", getAge()=" + getAge() + "]";
	}
	
}

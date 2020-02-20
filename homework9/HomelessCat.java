package homework9;

@Blochoble
public class HomelessCat extends Animal{
	
	@LuckyCat(luckyAnimal = false)
	private int code;

	@Paw(countRaws = 3)
	public String toString() {
		return "HomelessCat [getName()=" + getName() + ", getColor()=" + getColor() + ", getAge()=" + getAge() + "]";
	}

	
}

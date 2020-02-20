package homework9;

public class FatCat extends Animal {
	@LuckyCat(luckyAnimal = true)
	private int weight;
	
	@Paw(countRaws = 4)
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return "FatCat [getName()=" + getName() + ", getColor()=" + getColor() + ", getAge()=" + getAge()
				+ ", getWeight()=" + getWeight() + "]";
	}

}

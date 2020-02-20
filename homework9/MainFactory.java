package homework9;

import java.util.ArrayList;

public class MainFactory {
	
	public static void main(String[] args) {

	 FactoryCat factoryCat = new FactoryCat();

     Animal animal1 = factoryCat.getAnimal("CAT");
     animal1.setName("Hey1");
     animal1.setColor("Green");
     animal1.setAge(4);
     System.out.println(animal1.toString());
     
     Animal animal4 = factoryCat.getAnimal("CAT");
     animal4.setName("Hey4");
     animal4.setColor("Blue");
     animal4.setAge(7);
     System.out.println(animal4.toString());

     Animal animal2 = factoryCat.getAnimal("FATCAT");
     animal2.setName("Hey2");
     animal2.setColor("Grey");
     animal2.setAge(2);
     System.out.println(animal2.toString());
     
     Animal animal5 = factoryCat.getAnimal("FATCAT");
     animal5.setName("Hey5");
     animal5.setColor("Black");
     animal5.setAge(5);
     System.out.println(animal5.toString());

     Animal animal3 = factoryCat.getAnimal("HOMELESSCAT");
     animal3.setName("Hey3");
     animal3.setColor("Red");
     animal3.setAge(3);
     System.out.println(animal3.toString());
     
     Animal animal6 = factoryCat.getAnimal("HOMELESSCAT");
     animal6.setName("Hey6");
     animal6.setColor("Pink");
     animal6.setAge(6);
     System.out.println(animal6.toString());
     
     Animal[] classes = {animal1, animal2, animal3, animal4, animal5, animal6};
     
     ArrayList<Animal> farsh = new ArrayList<Animal>();
 
     factoryCat.search(classes, farsh);
     System.out.println("All of the:\n"+farsh+"\nwill become farsh!!!");
	}

}

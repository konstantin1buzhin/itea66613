package application;

import java.io.Serializable;

public class Owner implements Serializable {
	
	private String nameOwner;

	public Owner(String nameOwner) {
		this.nameOwner = nameOwner;
	}

	public Owner() {
	}

	public String getNameOwner() {
		return nameOwner;
	}

	public void setNameOwner(String nameOwner) {
		this.nameOwner = nameOwner;
	}

	@Override
	public String toString() {
		return "Owner [nameOwner=" + nameOwner + "]";
	}
	
	

}

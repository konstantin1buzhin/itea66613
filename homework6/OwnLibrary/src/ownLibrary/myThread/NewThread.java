package ownLibrary.myThread;

import java.util.ArrayList;

public class NewThread extends Thread{

	private String nameThread;
	private int currentNum;
	private ArrayList<Integer> collectionInt = new ArrayList<Integer>();
	
	public String getNameThread() {
		return nameThread;
	}

	public void setNameThread(String nameThread) {
		this.nameThread = nameThread;
	}

	public int getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(int currentNum) {
		this.currentNum = currentNum;
	}

	public ArrayList<Integer> getCollectionInt() {
		return collectionInt;
	}

	public void setCollectionInt(ArrayList<Integer> collectionInt) {
		this.collectionInt = collectionInt;
	}

	public NewThread() {
	}

	public NewThread(String nameThread){
		this.nameThread = nameThread;
	}
    
    @Override
    public void run() {
    	for(int i = 0; i<5; i++) {
    	currentNum = (int)(Math.random()*101);
    	collectionInt.add(currentNum);
    	try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    }
}

package logic;

public class LProduct {

	String name; 
	int ID;
	int ETA;
	boolean available;

	public LProduct(String name, int id2, int ETA, boolean available) {
		this.name = name;
		this.ID = id2;
		this.ETA = ETA;
		this.available = available;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getETA() {
		return ETA;
	}

	public void setETA(int eTA) {
		ETA = eTA;
	}

	public boolean isAvailable() {
		return available;
	}

	@Override
	public String toString() {
		return name + "\n" + ID + "\n" + ETA + "\n" + available + "\n";
	}

}

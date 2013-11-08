package radisLogic;

public class Product {

	String name, ID;
	int ETA;
	boolean available;

	public Product(String name, String ID, int ETA, boolean available) {
		this.name = name;
		this.ID = ID;
		this.ETA = ETA;
		this.available = available;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
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

package logic;

public class tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LxmlWriter write = new LxmlWriter();
		LProduct prod = new LProduct("radis", "lol", 6, false);
		LOrder order = new LOrder("Radis", 6, "to begin", null, prod );
		write.writeXML(order);
	}

}

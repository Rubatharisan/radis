package logic;

import java.io.IOException;

import javax.jms.JMSException;

public class tester {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JMSException 
	 */
	public static void main(String[] args) throws IOException, JMSException {
		// TODO Auto-generated method stub
		LxmlWriter write = new LxmlWriter();
		LProduct prod = new LProduct("radis", "lol", 6, false);
		LOrder order = new LOrder("Radis", 6, "to begin", null, prod );
		write.writeXML(order);
	}

}

package logic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LxmlReceiver {
	 public void ReceiveXML(String xmlFile) throws NamingException, JMSException, IOException{
		 Properties env = new Properties( );
	       
	        // ... specify the JNDI properties specific to the vendor
	        InitialContext jndi = new InitialContext(env);
	    	QueueConnectionFactory queueConnectionFactory =
	    			(QueueConnectionFactory) jndi.lookup("jms/__defaultConnectionFactory");
	    			
	    	// Look up a JMS Queue
	        Queue queue = (Queue) jndi.lookup("testqueue");
	        
	        
	        // Create a JMS connection
	     		QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
	     		Session session =  queueConnection.createSession(false,
	     				Session.AUTO_ACKNOWLEDGE);
	     		
	     		MessageConsumer messageConsumer = session.createConsumer(queue);
	     		
	     		queueConnection.start();
	     		
	     		System.out.println("Receiving message.");
	     		
	     		BytesMessage messageReceived = (BytesMessage) messageConsumer.receive();
	     		
	     		System.out.println("Received message with: " + messageReceived.getBodyLength() + 
	     				" bytes. Now streaming to file on disk.");
	     		
	     		File file = new File(xmlFile);
	     		FileOutputStream fos = new FileOutputStream(file);
	     		BufferedOutputStream outBuf = new BufferedOutputStream(fos);
	     		int i;
	     		while((i=messageReceived.readInt())!=-1){
	     		   outBuf.write(i);
	     		}
	     		outBuf.close();
	     		fos.close();
	     		
	     		System.out.println("File streamed to disk. Size of received file on disk is " + file.length());
	 }
}

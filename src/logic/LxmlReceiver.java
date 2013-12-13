package logic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LxmlReceiver extends Observable implements Runnable {
	
	 LfileObserver ob;
	 public static void main(String[] args) throws NamingException, IOException, JMSException{
			
			
			new LxmlReceiver().run();
		}
	
	@Override
	public void run() {
		
	              
		try {
			
			// TODO Auto-generated method stub
			Properties env = new Properties( );
			
			 // ... specify the JNDI properties specific to the vendor
	        InitialContext jndi;
	        
	        
			jndi = new InitialContext(env);
			
			QueueConnectionFactory queueConnectionFactory =
	    			(QueueConnectionFactory) jndi.lookup("jms/__defaultConnectionFactory");
	    			
	    	// Look up a JMS Queue
	        Queue queue = (Queue) jndi.lookup("textqueue");
	        
	        
	        // Create a JMS connection
	     		QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
	     		Session session =  queueConnection.createSession(false,
	     				Session.AUTO_ACKNOWLEDGE);
	     		
	     		MessageConsumer messageConsumer = session.createConsumer(queue);
	     		
	     		queueConnection.start();
	     		
	     		System.out.println("Receiving message.");
//	     		
//     		TextMessage textReceived = (TextMessage) messageConsumer.receive();
//	     		
	     		
	     		
	     		BytesMessage messageReceived = (BytesMessage) messageConsumer.receive();
	     		     		
	     		
	     		System.out.println("Received message with: " + messageReceived.getBodyLength() + 
	     				" bytes. Now streaming to file on disk.");
	     		
	     		
	     		
//	     		String s = textReceived.getText();
//	     		
//	     		System.out.println("TextMessage: " + s);
	     		
	     		File file = new File("Random.xml");
	     		FileOutputStream fos = new FileOutputStream(".git/src\\mes\\" + file);
	     		BufferedOutputStream outBuf = new BufferedOutputStream(fos);
	     		
	     	
	     		
//	     		int i;
//	     		while((i=messageReceived.readInt())!=-1){
//	     		   outBuf.write(i);
//	     		   
//	     		  	     		 	     		  	     		 	     		  
//	     		}
//	     		
	     		 
	     		outBuf.close();
	     		fos.close();
	     		
	     		
	     		
//	     		System.out.println("File streamed to disk. Size of received file on disk is " + file.length());
//	     		
//	     	
//	     			
//		     	    
//		     		
//	     		
//		     System.out.println(file.length());

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		}
}

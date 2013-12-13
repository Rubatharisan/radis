package logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class LxmlSender {
	
	private final static long FILE_SIZE = 1000024; 

	public static void main(String[] args) throws NamingException, IOException, JMSException{
		LtextSender sendText = new LtextSender();
		sendText.produceMessages("Random.xml");
		
		new LxmlSender().SendXML("8OQ0979.xml");
	}

    public void SendXML(String file) throws JMSException, FileNotFoundException, IOException, NamingException {

    	
    	 // Obtain a JNDI connection
        Properties env = new Properties( );
       
        // ... specify the JNDI properties specific to the vendor
        InitialContext jndi = new InitialContext(env);
    	QueueConnectionFactory queueConnectionFactory =
    			(QueueConnectionFactory) jndi.lookup("jms/__defaultConnectionFactory");
    			
    	// Look up a JMS Queue
        Queue queue = (Queue) jndi.lookup("bytequeue");
        
        
        
        // Create a JMS connection
     		QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
     		Session session =  queueConnection.createSession(false,
     				Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(queue);
        
    
        File fileInput = new File(file);



        BytesMessage message1 = session.createBytesMessage();
        

        FileInputStream fileInputStream = new FileInputStream(fileInput);
        BufferedInputStream bufferedInput = new BufferedInputStream(fileInputStream);
       	
       	
        int i;
        
        while((i = bufferedInput.read()) != -1)
        {
        	message1.writeInt(i);
        }
        message1.writeInt(-1);
        
        System.out.println("Sending the file.");
        bufferedInput.close();
     // Step 9. Send the Message
      
        producer.send(message1);
        
        System.out.println("File sent");

        System.out.println("Stopping server.");
     
        queueConnection.close();
        
        System.out.println("Server stopped");
    }
    

}
package logic;


import javax.jms.*;
import javax.naming.*;

import java.io.*;
import java.net.ServerSocket;
import java.util.Properties;
 
















import javax.annotation.Resource;
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

import mes.JMSConnection;

public class LtextSender implements Serializable
{
	 
	  
	  

  
  public void produceMessages(String text) throws NamingException, IOException
  {
    MessageProducer messageProducer;
    TextMessage textMessage;
    try
    {
    	// Obtain a JNDI connection
        Properties env = new Properties( );
       
        // ... specify the JNDI properties specific to the vendor
        InitialContext jndi = new InitialContext(env);
        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) jndi.lookup("jms/__defaultConnectionFactory");
    			
    	// Look up a JMS Queuez
        Queue queue = (Queue) jndi.lookup("textqueue");
        
        // Create a JMS connection
     		QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
      Session session =  queueConnection.createSession(false,
        Session.AUTO_ACKNOWLEDGE);
    	
    
    	
      messageProducer = session.createProducer(queue);
      

      textMessage = session.createTextMessage();


      textMessage.setText(text);
      System.out.println("The text content: " + textMessage.getText());
      messageProducer.send(textMessage);


      messageProducer.close();
      session.close();
      queueConnection.close();
    }
    catch (JMSException e)
    {
      e.printStackTrace();
    }
  }
 
}
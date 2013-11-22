package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class LxmlSender {
	private String everything;
	
	public void sendFile(File file, String[] args){
		String                  queueName = null;
        Context                 jndiContext = null;
        QueueConnectionFactory  queueConnectionFactory = null;
        QueueConnection         queueConnection = null;
        QueueSession            queueSession = null;
        Queue                   queue = null;
        QueueSender             queueSender = null;
        TextMessage             message = null;
        final int               NUM_MSGS;
        

     
        
        if ( (args.length < 1) || (args.length > 2) ) {
            System.out.println("Usage: java SimpleQueueSender " +
                "<queue-name> [<number-of-messages>]");
            System.exit(1);
        }
        queueName = new String(args[0]);
        System.out.println("Queue name is " + queueName);
        if (args.length == 2){
            NUM_MSGS = (new Integer(args[1])).intValue();
        } else {
            NUM_MSGS = 1;
        }
        
        /* 
         * Create a JNDI API InitialContext object if none exists
         * yet.
         */
        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            System.out.println("Could not create JNDI API " +
                "context: " + e.toString());
            System.exit(1);
        }
        
        /* 
         * Look up connection factory and queue.  If either does
         * not exist, exit.
         */
        try {
            queueConnectionFactory = (QueueConnectionFactory)
                jndiContext.lookup("glassfishConnectionFactory");
            queue = (Queue) jndiContext.lookup(queueName);
        } catch (NamingException e) {
            System.out.println("JNDI API lookup failed: " + 
                e.toString());
            System.exit(1);
        }

        /*
         * Create connection.
         * Create session from connection; false means session is
         * not transacted.
         * Create sender and text message.
         * Send messages, varying text slightly.
         * Send end-of-messages message.
         * Finally, close connection.
         */
        try {
            queueConnection = 
                queueConnectionFactory.createQueueConnection();
            queueSession = 
                queueConnection.createQueueSession(false, 
                    Session.AUTO_ACKNOWLEDGE);
            queueSender = queueSession.createSender(queue);
            message = queueSession.createTextMessage();
            for (int i = 0; i < NUM_MSGS; i++) {
            
            		message.setText(readFile(file));
           
            
                System.out.println("Sending message: " + 
                    message.getText());
                queueSender.send(message);
            }

            /* 
             * Send a non-text control message indicating end of
             * messages.
             */
            queueSender.send(queueSession.createMessage());
        } catch (JMSException e) {
            System.out.println("Exception occurred: " + 
                e.toString());
        } finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                } catch (JMSException e) {}
            }
        }
	}
	public String readFile(File file)
	{
		try {
			//filen indlæses
			BufferedReader buffer = new BufferedReader(new FileReader(file));
			
			//vi bruger en Stringbuilder til at få teksten til at være 100% som den står
			//i filen.
			StringBuilder sb = new StringBuilder();
			String line = buffer.readLine();
			
			while(line != null)
			{
				sb.append(line);
				sb.append("\n");
				
				line = buffer.readLine();
			}
			//her sættes alt hvad der er i XML dokumentet ind i én string, som gør det nemt
			//at sende via JMS.
			everything = sb.toString();
			
			
			buffer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return everything;
		
	}
}

package mes;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSConnection {
	
	// TODO Auto-generated method stub
			// Obtain a JNDI connection
	        private Properties env = new Properties( );
	       
	        // ... specify the JNDI properties specific to the vendor
	        private InitialContext jndi;
	        
	        private QueueConnectionFactory queueConnectionFactory;
	        
	        private Queue queue1;
	        
	       public static void main(String[] args) {
	      	new JMSConnection().ConnectionFactory("jms/__defaultConnectionFactory", "testqueue");
	      
	      	  
	        }



	public void ConnectionFactory(String ConFac, String queueu){
		try {
			jndi = new InitialContext(env);
			queueConnectionFactory = (QueueConnectionFactory) jndi.lookup(ConFac);
			queue1 = (Queue) jndi.lookup(queueu);
				// Create a JMS connection
		 	QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
		 		
		 		Session session =  queueConnection.createSession(false,
		 				Session.AUTO_ACKNOWLEDGE);
		} catch (NamingException | JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

package logic;

import java.io.IOException;

import javax.jms.JMSException;
import javax.naming.NamingException;

public class Observertester {
	public static void main(String[] args) throws NamingException, JMSException, IOException {
        
        
      
 
        // create an event source - reads from stdin
        final LxmlReceiver eventSource = new LxmlReceiver();
 
        // create an observer
        final LfileObserver responseHandler = new LfileObserver();
 
        // subscribe the observer to the event source
        eventSource.addObserver(responseHandler);
 
        // starts the event thread
        Thread thread = new Thread(eventSource);
        thread.start();
    }
}

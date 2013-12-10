package jms;

import javax.jms.*;
import javax.naming.*;

import java.io.*;
import java.util.Properties;

public class Server implements javax.jms.MessageListener {

    private TopicSession pubSession;
    private TopicSession subSession;
    private TopicPublisher publisher;
    private TopicConnection connection;
    private String username;
 
    /* Constructor. Establish JMS publisher and subscriber */
    public Server(String topicName, String username, String password)
    throws Exception {
        // Obtain a JNDI connection
        Properties env = new Properties( );
        // ... specify the JNDI properties specific to the vendor
 
        InitialContext jndi = new InitialContext(env);
 
        // Look up a JMS connection factory
        TopicConnectionFactory conFactory =
        (TopicConnectionFactory)jndi.lookup("GlassFishConnectionfactory");
 
        // Create a JMS connection
        TopicConnection connection =
        conFactory.createTopicConnection(username,password);
 
        // Create two JMS session objects
        TopicSession pubSession =
        connection.createTopicSession(false,
                                      Session.AUTO_ACKNOWLEDGE);
        TopicSession subSession =
        connection.createTopicSession(false,
                                      Session.AUTO_ACKNOWLEDGE);
 
        // Look up a JMS topic
        Topic serverTopic = (Topic)jndi.lookup(topicName);
 
        // Create a JMS publisher and subscriber
        TopicPublisher publisher = 
            pubSession.createPublisher(serverTopic);
        TopicSubscriber subscriber = 
            subSession.createSubscriber(serverTopic);
 
        // Set a JMS message listener
        subscriber.setMessageListener(this);
 
        // Intialize the Chat application
        set(connection, pubSession, subSession, publisher, username);
 
        // Start the JMS connection; allows messages to be delivered
        connection.start( );
 
    }
    /* Initialize the instance variables */
    public void set(TopicConnection con, TopicSession pubSess,
                    TopicSession subSess, TopicPublisher pub, 
                    String username) {
        this.connection = con;
        this.pubSession = pubSess;
        this.subSession = subSess;
        this.publisher = pub;
        this.username = username;
    }
    /* Receive message from topic subscriber */
    public void onMessage(Message message) {
        try {
        	BytesMessage bm = (BytesMessage) message;
        	File file = new File("C:/Users/Zaghi/Documents/Workspace/radis/.git/src/jms/184562_10200754215756784_1374214022_n.jpg");
        	FileOutputStream fos = new FileOutputStream(file);
        	BufferedOutputStream outBuf = new BufferedOutputStream(fos);
        	int i;
        	while((i=bm.readInt())!=-1){
        	   fos.write(i);
        	}
        	outBuf.close();
        	fos.close();
        } catch (JMSException | IOException jmse){ jmse.printStackTrace( ); }
    }
    /* Create and send message using topic publisher */
    public void writeMessage(String file) throws JMSException, IOException {
        
        File fileToPublish=new File(file);
        BytesMessage bm = pubSession.createBytesMessage();
        InputStream in= new FileInputStream(fileToPublish);
        BufferedInputStream inBuf= new BufferedInputStream(in);
        int i;
        while((i=inBuf.read())!=-1){
           bm.writeInt(i);
        }
        //adding an eof
        bm.writeInt(-1);
        inBuf.close();
    }
    /* Close the JMS connection */
    public void close( ) throws JMSException {
        connection.close( );
    }
    
}

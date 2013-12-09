package jms;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Client1 {
	
	public Client1(String topicname, String username, String password){
		
		try {
			Server server = new Server(topicname, username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}

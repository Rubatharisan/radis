package jms;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jms.Server;



public class Client {

	 public static void main(String [] args){
	        try{
	            if (args.length!=3)
	                System.out.println("Topic or username missing");
	 
	            // args[0]=topicName; args[1]=username; args[2]=password
	            Server chat = new Server("testtopic","Zaghi","Saad");
	 
	           
	            chat.writeMessage("C:/Users/Zaghi/Pictures/mig/184562_10200754215756784_1374214022_n.jpg");
	            System.out.println("File sent");
	        } catch (Exception e){ e.printStackTrace( ); }
	    }
	
}

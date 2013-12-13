package logic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Observable;
import java.util.Observer;

import javax.jms.BytesMessage;

public class LfileObserver implements Observer {
	private String files;
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		if(arg instanceof String){
			files = ((String) arg);
			System.out.println("Message received: " + files);
		}
	}

}

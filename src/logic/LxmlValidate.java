package logic;





import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.FactoryConfigurationError; 
import javax.xml.parsers.ParserConfigurationException; 

import org.xml.sax.SAXException; 
import org.xml.sax.SAXParseException; 
import org.w3c.dom.Document;
import org.w3c.dom.DOMException;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.transform.stream.StreamSource;

import java.io.*;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;


public class LxmlValidate {
	
	public LxmlValidate(String xmlFile, String ValidationFile) throws SAXException, IOException{
		
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
   	 SchemaFactory factory = SchemaFactory.newInstance(language);
   	((factory.newSchema(new File(ValidationFile))).newValidator()).validate(new StreamSource(new File(xmlFile)));
		
	}

	

	 public static void main(String [] args){
		 boolean flag = true;
		 try{
	        	        	
	            try{new LxmlValidate("8OQ14.xml", ".git/src\\b2mml\\B2MML-V0600-Material.xsd");}
	            catch (SAXException e){ e.printStackTrace();} 
	            catch (IOException e) {	flag = false; }
	            System.out.println("XML fil er valid : " + flag);
	            
	 }
		 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}
}

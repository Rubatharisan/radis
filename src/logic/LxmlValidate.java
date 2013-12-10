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

	 public boolean Xml2String(String xmlFile){
	    	// Danner JAVA DOM XML Parser
		 boolean flag = true;
	        
	        DocumentBuilderFactory builderFactory =
	               DocumentBuilderFactory.newInstance();
	       DocumentBuilder builder = null;
	       try {
	           builder = builderFactory.newDocumentBuilder();
	        } catch (ParserConfigurationException e) {
	            e.printStackTrace();  
	        }
	        
	       // Omdanner XML filen til XML DOM standard
	       
	       try {
	           Document document = builder.parse(
	                   new FileInputStream(new File(xmlFile)));
	           Transformer transformer = TransformerFactory.newInstance().newTransformer();
	           transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	           // G�r DOM XML filen l�sevenligt
	           StreamResult result = new StreamResult(new StringWriter());
	          DOMSource source = new DOMSource(document);
	          transformer.transform(source, result);
	           String xmlString = result.getWriter().toString();
	           System.out.println(xmlString);
	       } catch (SAXException e) {
	           flag = false;
	       } catch (IOException e) {
	    	   flag = false;
	       } catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	     
	   }
	 
	 public static void main(String [] args){
		 boolean flag = true;
		 try{
	        	        	
	            try{new LxmlValidate("RAD40OQ12.xml", ".git/src\\B2mml\\B2MML-V0600-Material.xsd");}
	            catch (SAXException e){ flag = false; } 
	            catch (IOException e) {	flag = false; }
	            System.out.println("XML fil er valid : " + flag);
	            
	 }
		 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}
}

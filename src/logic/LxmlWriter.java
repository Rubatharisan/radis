package logic;



import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;


public class LxmlWriter {
	
	public void writeXML(LOrder order) throws JMSException{
		try{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("b2mml:MaterialInformation");
		rootElement.setAttribute("xmlns:b2mml", "http://www.mesa.org/xml/B2MML-V0600");
		rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		rootElement.setAttribute("xsi:schemaLocation", "http://www.mesa.org/xml/B2MML-V0600 B2MML-V0600-Material.xsd");
		doc.appendChild(rootElement);
 
		// staff elements
		Element top_id = doc.createElement("b2mml:ID");
		rootElement.appendChild(top_id);
		top_id.appendChild(doc.createTextNode(order.orderID));
		
 
		Element description = doc.createElement("b2mml:Description");
		rootElement.appendChild(description);
		description.appendChild(doc.createTextNode(order.getProduct().name));

		Element date = doc.createElement("b2mml:PublishedDate");
		rootElement.appendChild(date);
		String timeStamp_1 = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String timeStamp_2 = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		date.appendChild(doc.createTextNode(timeStamp_1+"T"+timeStamp_2+"Z"));
 
		Element MaterialLot = doc.createElement("b2mml:MaterialLot");
		rootElement.appendChild(MaterialLot);
		
		
		Element Material_ID = doc.createElement("b2mml:ID");
		Material_ID.appendChild(doc.createTextNode(String.valueOf(order.getOrderID())+"LOT"));
		MaterialLot.appendChild(Material_ID);
		
		Element Material_Status = doc.createElement("b2mml:Status");
		Material_Status.appendChild(doc.createTextNode(order.getOrderStatus()));
		MaterialLot.appendChild(Material_Status);
		
		Element Material_Quantity = doc.createElement("b2mml:Quantity");
		MaterialLot.appendChild(Material_Quantity);
		
		Element Quantity_String = doc.createElement("b2mml:QuantityString");
		Quantity_String.appendChild(doc.createTextNode(String.valueOf(order.getOrderQuantity())));
		Material_Quantity.appendChild(Quantity_String);
		
		Element Quantity_Data = doc.createElement("b2mml:DataType");
		Quantity_Data.appendChild(doc.createTextNode("double"));
		Material_Quantity.appendChild(Quantity_Data);
		
		Element Quantity_unit = doc.createElement("b2mml:UnitOfMeasure");
		Quantity_unit.appendChild(doc.createTextNode("units"));
		Material_Quantity.appendChild(Quantity_unit);
		
		Element Quantity_key = doc.createElement("b2mml:Key");
		Quantity_key.appendChild(doc.createTextNode(order.getOrderID()+"key"));
		Material_Quantity.appendChild(Quantity_key);
		
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		File writtenFile = new File(order.getOrderID()+".xml");
		StreamResult result = new StreamResult(writtenFile);
		transformer.transform(source, result);
		System.out.println("File saved! " + writtenFile.getName());
		
		//Validating the saved xml against XSD B2MML 
		LxmlValidate validate = new LxmlValidate(writtenFile.getName(), "B2mml/B2MML-V0600-Material.xsd");
		System.out.println("The xml file: " + writtenFile.getName() + " " + "is VALID against: " + "B2MML-V0600-Material.xsd");
		

		// write the xml content into console
		OutputFormat format    = new OutputFormat (doc); 
		StringWriter stringOut = new StringWriter (); 
		XMLSerializer serial   = new XMLSerializer (stringOut, 
                format);
		serial.serialize(doc);
		
		System.out.println("========Sending the xml file via JMS==========");

 
		LxmlSender send = new LxmlSender();
		send.SendXML(writtenFile.getName());
		
		System.out.println("========The XML file is sent========");
		
		System.out.println("========Receiving the message in MES=========");
		
		
		
		System.out.println("========The XML file is received in MES=========");
		
		
		}catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

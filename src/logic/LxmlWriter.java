package logic;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

public class LxmlWriter {
	
	public void writeXML(LOrder order){
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
		Material_ID.appendChild(doc.createTextNode(order.getOrderID()+"LOT"));
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
		Quantity_unit.appendChild(doc.createTextNode("regular")); //SPØRG JENS! :(
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
		

		
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
		LxmlSender send = new LxmlSender();
		System.out.println("File saved!");
		System.out.println(send.readFile(writtenFile));
		String[] args = {"glassfishDestination"};
		send.sendFile(writtenFile, args);
		
		}catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
	}
}

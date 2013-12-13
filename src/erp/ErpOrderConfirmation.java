package erp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.jms.JMSException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.LOrder;
import logic.LxmlWriter;

public class ErpOrderConfirmation extends Application{
	
	
	Stage primaryStage;
	/**
	 * @param args
	 */
	TextField itemField, orderField, quantityField, ETAField,orderStatusField;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	LOrder order = ErpNewOrder.orderList.get(0);
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		
		// TODO Auto-generated method stub
		primaryStage.setTitle("Vertical Farming Inc.");

		
		VBox cont = new VBox();
		HBox main = new HBox(10);
		cont.getChildren().add(main);
		main.setPadding(new Insets(25,25,25,25));
		
		//H��jre VBOX
		VBox rightLabels = new VBox(13);
		main.getChildren().add(rightLabels);
		
		Label item = new Label("ordre:  ");
		rightLabels.getChildren().add(item);
		
		Label orderID = new Label("orderID");
		rightLabels.getChildren().add(orderID);
		
		Label quantityLabel = new Label("mængde");
		rightLabels.getChildren().add(quantityLabel);
		
		Label statusLabel = new Label("Status: ");
		rightLabels.getChildren().add(statusLabel);
		
		Label ETALabel = new Label("ca. antal dage");
		rightLabels.getChildren().add(ETALabel);
		//H��jre VBOX END
		//Venstre tekstfeldter
		VBox leftTextfield = new VBox(5);
		main.getChildren().add(leftTextfield);
		
		itemField = new TextField(order.getProductName());
		itemField.setEditable(false);
		leftTextfield.getChildren().add(itemField);
		
		orderField = new TextField(order.getOrderID());
		orderField.setEditable(false);
		leftTextfield.getChildren().add(orderField);
		
		quantityField = new TextField(String.valueOf(order.getOrderQuantity()));
		quantityField.setEditable(false);
		leftTextfield.getChildren().add(quantityField);
		
		orderStatusField = new TextField(order.getOrderStatus());
		orderStatusField.setEditable(false);
		leftTextfield.getChildren().add(orderStatusField);
		
		ETAField = new TextField(String.valueOf(order.getHours())+" dage");
		ETAField.setEditable(false);
		leftTextfield.getChildren().add(ETAField);
		//Venstre tekstfeldter END
		
		
		
		HBox buttons = new HBox(10);
		buttons.setPadding(new Insets(0,0,0,30));
		cont.getChildren().add(buttons);
		
		Button finalSubmit = new Button("Indsend Ordre");
		buttons.getChildren().add(finalSubmit);
		finalSubmit.setOnAction(new newConfirmationListener());
		
				
		Button finalSubmit1 = new Button("JMS Configuration");
		buttons.getChildren().add(finalSubmit1);
		finalSubmit1.setOnAction(new newConfigurationListener());
		
				
		Scene scene = new Scene(cont,300,300);
		scene.getStylesheets().add(ErpMain.class.getResource("main.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		init();
		
	}
	private class newConfirmationListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
						
			LxmlWriter write = new LxmlWriter();
			try {				
				write.writeXML(order);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
				
		}
		
	}
	
	private class newConfigurationListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
						
		
			Stage SecondStage = new Stage();
			PopupExample pop = new PopupExample();
			try {
				pop.start(SecondStage);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		
	}

}

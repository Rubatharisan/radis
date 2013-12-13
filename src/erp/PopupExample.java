package erp;

import javax.jms.JMSException;




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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import logic.LxmlWriter;
import mes.JMSConnection;
 
public class PopupExample extends Application {
	Stage primaryStage;
	TextField itemField;
	TextField itemField1;
	Label responseLabel;
  public static void main(String[] args) {
	  launch(args); 
	  
  }
 
  @Override public void start(final Stage primaryStage) throws Exception { 
	  
	  this.primaryStage = primaryStage;
	  
	  primaryStage.setTitle("JMS Options.");
		
		VBox cont1 = new VBox();
		HBox main1 = new HBox(10);
		cont1.getChildren().add(main1);
		main1.setPadding(new Insets(25,25,25,25));
		
		//H��jre VBOX
		VBox rightLabels = new VBox(13);
		main1.getChildren().add(rightLabels);
		
		Label item = new Label("JMS QueueConnectionFactory:  ");
		rightLabels.getChildren().add(item);
		
		Label item1 = new Label("JMS Queue Destination Resources:  ");
		rightLabels.getChildren().add(item1);
		
		VBox leftTextfield = new VBox(5);
		main1.getChildren().add(leftTextfield);
		
		itemField = new TextField();
		itemField.setEditable(true);
		leftTextfield.getChildren().add(itemField);
		
		itemField1 = new TextField();
		itemField.setEditable(true);
		leftTextfield.getChildren().add(itemField1);
		
		HBox buttons = new HBox();
		buttons.setPadding(new Insets(0,0,0,200));
		cont1.getChildren().add(buttons);
		Button finalSubmit = new Button("Finish");
		buttons.getChildren().add(finalSubmit);
		finalSubmit.setOnAction(new newConfirmationListener());
		
		responseLabel = new Label();
		main1.getChildren().add(responseLabel);
		responseLabel.setId("error");
		
		Scene scene = new Scene(cont1,450,150);
		scene.getStylesheets().add(ErpMain.class.getResource("main.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		init();
  }
  

	
  private class newConfirmationListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			
			if(itemField.getText().isEmpty() && itemField1.getText().isEmpty()){
				responseLabel.setText("Angiv alle JMS configurationer");
			}else if(itemField.getText().isEmpty() || itemField1.getText().isEmpty()){
				responseLabel.setText("Configuration mangler");
			}else if(itemField.getText().isEmpty() == false && itemField1.getText().isEmpty() == false){
				
				try{
					JMSConnection con = new JMSConnection();
					con.ConnectionFactory(itemField.getText(), itemField1.getText());
					System.out.println("Connection OK");
				}finally{
					primaryStage.close();
				}
				
				
				
			}			
		}
		
	}
  

	
}
package erp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErpMain extends Application {
	Label buttonstat;
	Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Vertical Farming Inc.");

		// HOVEDBOX
		VBox main = new VBox(15);
		main.setPadding(new Insets(75, 0, 0, 45));

		// SEKUND��R BOKS
		// Indeholder overskrifterne
		VBox sec = new VBox(5);
		main.getChildren().add(sec);
		Label header = new Label("Vertical Farming!");
		header.setId("header_first");
		sec.getChildren().add(header);
		Label underHeader = new Label("Hvad vil du?");
		underHeader.setId("header_second");
		sec.getChildren().add(underHeader);

		// BUTTONS BOX, indeholder knapperne
		VBox buttons = new VBox(25);
		main.getChildren().add(buttons);
		Button newOrder = new Button("Opret Odre");
		buttons.getChildren().add(newOrder);
		newOrder.setOnAction(new newOrderListener());

		Button cancelOrder = new Button("Annuler Ordre");
		buttons.getChildren().add(cancelOrder);

		Button seeStatus = new Button("Se Status");
		buttons.getChildren().add(seeStatus);

		buttonstat = new Label();
		buttons.getChildren().add(buttonstat);

		// INIT
		Scene scene = new Scene(main, 258, 300);
		primaryStage.setScene(scene);
	    scene.getStylesheets().add(ErpMain.class.getResource("main.css").toExternalForm());
		
		primaryStage.show();

	}

	private class newOrderListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			Stage SecondStage = new Stage();
			ErpNewOrder newOrder = new ErpNewOrder();
			try {
				newOrder.start(SecondStage);
				primaryStage.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}

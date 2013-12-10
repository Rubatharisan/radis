package erp;

import java.util.ArrayList;
import java.util.Date;

import data.RconnectDB;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.LOrder;
import logic.LProduct;


public class ErpNewOrder extends Application {

	Stage primaryStage;
	TableView<LProduct> table;
	Label ETAres, IDres, nameRes, availabilityRes, selectedProduct,responseLabel;
	TextField quantityField;
	static ArrayList<LOrder> orderList;

	public static void main(String[] args) {

		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Vertical Farming Inc.");

		VBox main = new VBox(15);
		main.setPadding(new Insets(25, 25, 25, 25));

		HBox secondaryHbox = new HBox(15);
		main.getChildren().add(secondaryHbox);
		secondaryHbox.setPadding(new Insets(25, 25, 25, 25));

		table = new TableView<LProduct>();
		secondaryHbox.getChildren().add(table);
		table.setMaxHeight(250);
		table.setMaxWidth(150);

		@SuppressWarnings("rawtypes")
		TableColumn products = new TableColumn("Produkt");
		products.setMinWidth(150);
		products.setCellValueFactory(new PropertyValueFactory<LProduct, String>(
				"name"));

		table.getColumns().add(products);
		RconnectDB read = new RconnectDB();
		read.readDataBase();
		table.setItems(read.getProducts());
		table.getSelectionModel().selectedIndexProperty()
				.addListener(new TableSelector());

		VBox rightLabels = new VBox(10);
		secondaryHbox.getChildren().add(rightLabels);

		Label name = new Label("navn:");
		rightLabels.getChildren().add(name);

		nameRes = new Label("--");
		rightLabels.getChildren().add(nameRes);

		Label id = new Label("Produkt ID:");
		rightLabels.getChildren().add(id);

		IDres = new Label("--");
		rightLabels.getChildren().add(IDres);

		Label ETA = new Label("Antal dyrknings timer: ");
		rightLabels.getChildren().add(ETA);

		ETAres = new Label("--");
		rightLabels.getChildren().add(ETAres);

		Label availability = new Label("Tilgængelig: ");
		rightLabels.getChildren().add(availability);

		availabilityRes = new Label("--");
		rightLabels.getChildren().add(availabilityRes);

		HBox lowerBox = new HBox(10);
		main.getChildren().add(lowerBox);

		Label chosenProduct = new Label("Valgte produkt: ");
		lowerBox.getChildren().add(chosenProduct);
		chosenProduct.setMinWidth(110);
		chosenProduct.setMaxWidth(110);

		selectedProduct = new Label("-------");
		lowerBox.getChildren().add(selectedProduct);
		selectedProduct.setMinWidth(70);
		selectedProduct.setMaxWidth(70);

		Label antal = new Label("Antal: ");
		lowerBox.getChildren().add(antal);

		quantityField = new TextField();
		lowerBox.getChildren().add(quantityField);


		HBox buttonOptions = new HBox(35);
		main.getChildren().add(buttonOptions);

		Button backButton = new Button("Tilbage");
		backButton.setOnAction(new backAction());
		buttonOptions.getChildren().add(backButton);

		Button sendOrder = new Button("Opret Odre");
		buttonOptions.getChildren().add(sendOrder);
		sendOrder.setOnAction(new submitOrder());
		
		responseLabel = new Label();
		main.getChildren().add(responseLabel);
		responseLabel.setId("error");

		Scene scene = new Scene(main, 450, 450);
		scene.getStylesheets().add(ErpMain.class.getResource("main.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private class TableSelector implements ChangeListener<Object> {

		@Override
		public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
			// TODO Auto-generated method stub
			String index = table.getSelectionModel().getSelectedItem()
					.getName();
			if (index.equals(table.getSelectionModel().getSelectedItem()
					.getName())) {
				selectedProduct.setText(table.getSelectionModel()
						.getSelectedItem().getName());
				nameRes.setText(table.getSelectionModel().getSelectedItem()
						.getName());
				IDres.setText(String.valueOf(table.getSelectionModel().getSelectedItem()
						.getID()));
				ETAres.setText(String.valueOf(table.getSelectionModel()
						.getSelectedItem().getETA()));
				if (table.getSelectionModel().getSelectedItem().isAvailable() == true) {
					availabilityRes.setText("Ja");
				} else {
					availabilityRes.setText("Nej");
				}
			}

		}

	}

	private class backAction implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			Stage secondStage = new Stage();
			ErpMain backwindow = new ErpMain();
			primaryStage.close();
			try {
				backwindow.start(secondStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	private class submitOrder implements EventHandler<ActionEvent>{
		

		@Override
		public void handle(ActionEvent arg0) {
			
			if(quantityField.getText().isEmpty()){
				responseLabel.setText("Du skal angive en mængde.");
			}
			else if(quantityField.getText().isEmpty() == false){
				
				responseLabel.setText(" ");
			ArrayList<LOrder> orderIn = new ArrayList<>();
			
			int quantity = Integer.valueOf(quantityField.getText());
			String orderID = table.getSelectionModel().getSelectedItem().getID()+"OQ"+quantityField.getText();
			String orderStatus = "To Begin";
			Date getDate = new Date();
			@SuppressWarnings("deprecation")
			String date = getDate.getDay()+"-"+getDate.getMonth()+"-"+getDate.getYear();
			LProduct product= table.getSelectionModel().getSelectedItem();

			
			orderIn.add(new LOrder(orderID, quantity, orderStatus, date, product));
			
			
			ErpNewOrder.orderList = orderIn;
			Stage confirmOrder = new Stage();
			ErpOrderConfirmation confirm = new ErpOrderConfirmation();
			try {
				confirm.start(confirmOrder);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			}
		}
		
		
	}
	public static ArrayList<LOrder> getOrderList(){
		
		return ErpNewOrder.orderList;
	}
}

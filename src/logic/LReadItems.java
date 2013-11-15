package logic;

import java.io.File;

import data.RconnectDB;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LReadItems {
	File file = new File(".//items.txt");

	public ObservableList<LProduct> getProducts() {
		ObservableList<LProduct> products = FXCollections.observableArrayList();
		
		
		products.add(new LProduct("Radis", "RAD40", 40, true));
		products.add(new LProduct("Gulerod", "GUL65", 65, false));
		products.add(new LProduct("Agurk", "AGU76", 76, false));
		products.add(new LProduct("Bananer", "BAN30", 30, true));

		return products;
	}

}

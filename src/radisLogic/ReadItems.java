package radisLogic;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReadItems {
	File file = new File(".//items.txt");

	public ObservableList<Product> getProducts() {
		ObservableList<Product> products = FXCollections.observableArrayList();

		products.add(new Product("Radis", "RAD40", 40, true));
		products.add(new Product("Gullerod", "GUL65", 65, false));
		products.add(new Product("Agurk", "AGU76", 76, false));

		return products;
	}

}

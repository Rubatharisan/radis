package radisData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import radisLogic.Product;

public class connectDB {
  private Connection connect = null;
  private Statement statement = null;
  private ResultSet resultSet = null;
  ObservableList<Product> products = FXCollections.observableArrayList();


  public void readDataBase() throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://95.47.119.185/foo?"
              + "user=root&password=password");

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from foo.products");
      writeResultSet(resultSet);
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set

    while (resultSet.next()) {
      // It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
     String name = resultSet.getString("name");
     String id = resultSet.getString("id");
     int eta = resultSet.getInt("eta");
     int available = resultSet.getInt("available");
     boolean availablef;
     if(available == 1){
    	 availablef = true;
     } else {
    	 availablef = false;
     }
         
     products.add(new Product(name, id, eta, availablef));
//     
//      System.out.println("Name: " + name);
//      System.out.println("ID: " + id);
//      System.out.println("ETA: " + eta);
    }
  }

  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }
	public ObservableList<Product> getProducts() {
		return products;
		
	}
} 
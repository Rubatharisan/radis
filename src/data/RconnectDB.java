package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.LProduct;

public class RconnectDB {
  private Connection connect = null;
  private Statement statement = null;
  private ResultSet resultSet = null;
  ObservableList<LProduct> products = FXCollections.observableArrayList();


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
          .executeQuery("select * from foo.Products");
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
     int id = resultSet.getInt("id");
     int eta = resultSet.getInt("eta");
     int available = resultSet.getInt("available");
     boolean availablef;
     if(available == 1){
    	 availablef = true;
     } else {
    	 availablef = false;
     }
         
     products.add(new LProduct(name, id, eta, availablef));
//     
//      System.out.println("Name: " + name);
//      System.out.println("ID: " + id);
//      System.out.println("ETA: " + eta);
    }
  }
  
  private void insertOrder(){
	  
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
	public ObservableList<LProduct> getProducts() {
		return products;
		
	}
} 
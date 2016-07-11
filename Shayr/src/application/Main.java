package application;
	
/* For the most part, this is mainly for testing purposes */ 

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//launch(args);
		
//		Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/Shayr","root","okay123");
//		
//		Statement stmt = (Statement) con.createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT * FROM Shayr.login");
//		
//		while(rs.next()) {
//			System.out.println(rs.getString("id") + " " + rs.getString("username") + " " + rs.getString("password"));
//		}
		
		String pass = null;
		Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/Shayr?autoReconnect=true&useSSL=false","root","okay123");
		
		Statement stmt = (Statement) con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT password FROM Shayr.login where username = \"test\";");
		
		if(rs.next()) {
			pass = rs.getString("password");
		}
		
		if(pass.equals("test")) {
			System.out.println("great");
		}
		
	}
}

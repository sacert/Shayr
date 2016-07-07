package login;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Login extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root);
			String css = this.getClass().getResource("login.css").toExternalForm();
			scene.getStylesheets().add(css);

			primaryStage.setTitle("Shayr");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// close application on termination
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
			}
		});
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		launch(args);
	}
}

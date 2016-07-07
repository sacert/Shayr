package client;

import java.io.IOException;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Client extends Application {
	
	// Windows view
	public void start(Stage primaryStage) {
		try {
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("Client.fxml"));
			Scene scene = new Scene(root);
			String css = this.getClass().getResource("client.css").toExternalForm();
			scene.getStylesheets().add(css);

			primaryStage.setTitle("Client");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		
			// close application on termination
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
				}
			});
						
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		launch(args);	
		
	}
}


	



package login;

import java.io.IOException;
import client.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

	@FXML private TextField username;
	@FXML private PasswordField password;
	
	ClientController client = new ClientController();
	
	@FXML void handleLoginButton(ActionEvent event) throws IOException {
		Boolean check;
		// check if login is true or false and then move to corresponding window page
		check = client.login(username.getText(), password.getText());
		
		// * NEED TO DO STUFF HERE
		if(check) {
			System.out.println("it works");
		} else {
			System.out.println("damn");
		}
	}
}

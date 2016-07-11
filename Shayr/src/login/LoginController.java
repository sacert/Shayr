package login;

import java.io.IOException;
import client.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private Button signupBtn;
	@FXML private Button loginBtn;
	
	@FXML private TextField signupUsername;
	@FXML private TextField signupPassword;
	@FXML private TextField signupVerifyPassword;
	
	ClientController client;
	
	@FXML void handleLoginButton(ActionEvent event) throws IOException {
		
		client = new ClientController();
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
	
	@FXML void handleSignupButton(ActionEvent event) throws IOException {
		
		client = new ClientController();
		String response = "";
		
		// check if login is true or false and then move to corresponding window page
		Boolean usernameValidCheck = (signupUsername.getText() != null) && (signupUsername.getText().matches("^[A-Za-z_][A-Za-z0-9_]{5,29}$"));
		Boolean passwordValidCheck = (signupPassword.getText() != null) && (signupPassword.getText().matches("^[A-Za-z_][A-Za-z0-9_]{5,29}$"));
		
		
		if(!usernameValidCheck) {
			System.out.println("Username must be between 6-30 characters without extra characters");
			// bad username (6-30 characeters) display error
		} else if (!passwordValidCheck) {
			System.out.println("Password must be between 6-30 characters without extra characters");
			// bad password (6-30 characeters) display error
		} else if(signupPassword.getText().equals(signupVerifyPassword.getText())) {
			response = client.signup(signupUsername.getText(), signupPassword.getText());
		} else {
			// bad password: passwords arent the same
			response = "Passwords are not the same";
			System.out.println("Passwords are not the same");
		}
		
		// display error messages if there is a fault
		if(response.equals("ACCEPTED")) {
			System.out.println("it works");
		} else {
			System.out.println("bad credtials");
		}
		
		// * NEED TO DO STUFF HERE
		/*if(check) {
			System.out.println("it works");
		} else {
			System.out.println("damn");
		}*/
	}
	
	/* Change scene from login to signup  */
	@FXML void handleSignupScene(ActionEvent event) throws IOException {
		Stage stage = (Stage) signupBtn.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	/* Change scene from signup to login */
	@FXML void handleLoginScene(ActionEvent event) throws IOException {
		Stage stage = (Stage) loginBtn.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}

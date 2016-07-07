package client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ClientController implements Initializable {
	
	public static TextArea cl;
	@FXML private TextArea chatLog;
	@FXML private TextField userInput;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cl = chatLog;
		
		// start the listener here
		// NOTE* should probably move to when the program begins
		Listener listener = new Listener();
		Thread x = new Thread(listener);
		x.start();

		// get user's text input
		userInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent enter) {
				if(enter.getCode().equals(KeyCode.ENTER)) {
					Listener.setOutput(userInput.getText());
					userInput.setText("");
				}
			}
		});
	}
	
}

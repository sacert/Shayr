package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ClientController implements Initializable {
	
	public static TextArea cl;
	@FXML private TextArea chatLog;
	@FXML private TextField userInput;
	
	String screenName = "";
	String serverIp = "192.168.0.15";
	final int portNumber = 9001;
	BufferedReader in;
	static PrintWriter out;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cl = chatLog;
		
		// start the listener here
		// NOTE* should probably move to when the program begins
		Listener listener = new Listener(serverIp, portNumber, in, out);
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
	
	/* attempt to log into the server and get return result */
	/* Needs to add try/catch to safely fail 				*/
	/* Need to revamp password to be more secure			*/
	public boolean login(String username, String password) throws IOException {
		
		// delete the following lines to make a class for it
		Socket socket;
		socket = new Socket(serverIp, 9001);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		out.println("LOGIN: " + username + "," + password);
		out.flush();
		
		String getResponse = null;
		
		try {
			getResponse = in.readLine();
			System.out.println(getResponse);
		} catch(IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		if(getResponse.equals("ACCEPTED")) {
			return true;
		} else {
			return false;
		}
	}
}

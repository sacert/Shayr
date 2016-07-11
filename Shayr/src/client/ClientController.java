package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import networkSettings.NetworkSettings;

public class ClientController implements Initializable {
	
	public static TextArea cl;
	@FXML private TextArea chatLog;
	@FXML private TextField userInput;
	
	String screenName = "";
	BufferedReader in;
	static PrintWriter out;
	Socket socket;
	
	NetworkSettings ns;
	
	public ClientController() throws UnknownHostException, IOException {
		ns = new NetworkSettings();
		in = ns.getBufferedReader();
		out = ns.getPrinterWriter();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		cl = chatLog;
		
		// start the listener here
		// NOTE* should probably move to when the program begins
		Listener listener = new Listener(ns.getServerIp(), ns.getPortNumber(), in, out);
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
		
		out.println("LOGIN: " + username + "," + password);
		out.flush();
		
		String getResponse = null;
		
		try {
			getResponse = in.readLine();
			System.out.println("LOGIN RESPONSE: " + getResponse);
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
	
	/* attempt to log into the server and get return result */
	/* Needs to add try/catch to safely fail 				*/
	/* Need to revamp password to be more secure			*/
	public String signup(String username, String password) throws IOException {
		
		out.println("SIGNUP: " + username + "," + password);
		out.flush();
		
		String getResponse = null;
		
		try {
			getResponse = in.readLine();
			System.out.println("SIGNUP RESPONSE: " + getResponse);
		} catch(IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
		
		return getResponse;
		
	}
}

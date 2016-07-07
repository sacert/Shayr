package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

public class Listener implements Runnable {
	
	String screenName = "";
	String serverIp = "192.168.0.15";
	final int portNumber = 9001;
	BufferedReader in;
	static PrintWriter out;
	static String testString;

	public Listener() {
		// need to set the local variables in here
		System.out.println("Client started...");
	}
	
	// Start the client
	public void run() {

		Socket socket;
		try {
			socket = new Socket(serverIp, 9001);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("Connection accepted");
			
			while (true) {
				String line = in.readLine();
				
				ClientController.cl.appendText(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// set the output message
	public static void setOutput(String msg) {
		out.println(msg);
	}
}

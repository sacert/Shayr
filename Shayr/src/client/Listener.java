

package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Listener implements Runnable {
	
	private String serverIp;
	private int portNumber;
	private BufferedReader in;
	private static PrintWriter out;
	
	public Listener(String serverIp, int portNumber, BufferedReader in, PrintWriter out) {

		System.out.println("Client started...");
		
		this.serverIp = serverIp;
		this.portNumber = portNumber;
		this.in = in;
		this.out = out;

	}

	// Start the client
	public void run() {
		
		// delete the following lines to make a class for it
		Socket socket;
		try {

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


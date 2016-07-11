package networkSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkSettings {
	
	final static String SERVERIP = "127.0.0.1";
	final static int PORTNUMBER = 1337;
	BufferedReader in;
	static PrintWriter out;
	Socket socket;
	
	public NetworkSettings () throws UnknownHostException, IOException {
		
		socket = new Socket(SERVERIP, PORTNUMBER);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

	}
	
	public String getServerIp() {
		return SERVERIP;
	}
	
	public int getPortNumber() {
		return PORTNUMBER;
	}
	
	public PrintWriter getPrinterWriter() {
		return out;
	}
	
	public BufferedReader getBufferedReader() {
		return in;
	}
}

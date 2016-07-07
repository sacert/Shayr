package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {
	
	static final int PORT = 9001;
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>(); 
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("There server is now running");
		ServerSocket listener = new ServerSocket(PORT);
		while(true) {
			new Handler(listener.accept()).start();
		}
	}
	
	private static class Handler extends Thread {
		
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;
		
		public Handler(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				writers.add(out);
				while (true) {
					String input = in.readLine();
					if(input == null) {
						return;
					}
					for(PrintWriter writer : writers) {
						writer.println(input);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				
				if(out != null) {
					writers.remove(out);
				}
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
	}

}

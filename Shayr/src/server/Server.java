package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Server {
	
	static final int PORT = 1337;
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
				
				String firstMessage = null;
				Boolean connected = false;
				
				/* Checks before entering the client */
				firstMessage = in.readLine();
				System.out.println("Incomming message: " + firstMessage);
				
				if(firstMessage.startsWith("LOGIN: ")) {
					connected = authenticate(firstMessage);
				} else if (firstMessage.startsWith("SIGNUP: ")) {
					addUser(firstMessage);
				}
				
				
				/* This is the main chat loop, where the server reads and sends messages */
				while (true) {
					String input = in.readLine();
					if(input == null) {
						return;
					}
					for(PrintWriter writer : writers) {
						writer.println(input);
					}
				}
			} catch (IOException | SQLException e) {
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
		
		private Boolean authenticate(String msg) throws SQLException {
			
			String username = msg.split(" ")[1].split(",")[0];
			String password = msg.split(",")[1];

			String pass = getPass(username);
			
			if(password.equals(pass)) {
				System.out.println("Accepted user: " + username);
				out.println("ACCEPTED");
				out.flush();
				return true;
			} else {
				System.out.println("Denied attempt for user: " + username);
				out.println("DENIED");
				out.flush();
				return false;
			}
		
		}
		
		/* Helper function for authenticate()				*
		 * Connects to database to determine is username 	*
		 * and password are accepted					 	*/
		private String getPass(String username) throws SQLException {
			String pass = null;
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/Shayr?autoReconnect=true&useSSL=false","root","okay123");
			
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT password FROM Shayr.login where username = \"" + username + "\";");
			
			if(rs.next()) {
				pass = rs.getString("password");
			}
			
			return pass;
		}
		
		
		private Boolean addUser(String msg) throws SQLException {
			
			String username = msg.split(" ")[1].split(",")[0];
			String password = msg.split(",")[1];

			String usernameCount = checkAndAdd(username, password);
			
			if(usernameCount.equals("ACCEPTED")) {
				out.println("ACCEPTED");
				out.flush();
				return true;
			} else {
				out.println("DENIED");
				out.flush();
				return false;
			}
		}
		
		/* Helper function for authenticate()				*
		 * Connects to database to determine is username 	*
		 * and password are accepted					 	*/
		private String checkAndAdd(String username, String password) throws SQLException {
			
			String pass = null;
			Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/Shayr?autoReconnect=true&useSSL=false","root","okay123");
			
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(username) FROM Shayr.login where username = \"" + username + "\";");
			
			if(rs.next()) {
				pass = rs.getString("COUNT(username)");
			}
			
			if(pass.equals("0")) {
				System.out.println("User successfully attempted to create an account with username: " + username);
				stmt.executeUpdate("INSERT INTO `Shayr`.`login` (`username`, `password`) VALUES (\"" + username + "\", \"" + password + "\");");
				return "ACCEPTED";
			} else {
				System.out.println("User failed at attempted to create an account with username: " + username);
				return "DENIED";
			}
			
		}
	}

}

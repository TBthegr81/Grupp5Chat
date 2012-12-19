package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private Socket connectionToClient;
	private ServerSocket openForConnections;
	private PrintWriter outStream;
	private BufferedReader inStream;
	String message;
	String servername;
	ArrayList<String> settings;
	int port;
	
	Server()
	{
		
	}
	
	public void start() throws IOException
	{
		message = "Starting up server...";
		System.out.println(message);
		Lib.log(message);
		settings = Lib.settings;
		servername = settings.get(0);
		port = Integer.parseInt(settings.get(1));
				
		openForConnections = new ServerSocket(port);
	}
	
	public String listen() throws IOException
	{
		// Start listening to things
		message = "Starts listening to things...";
		System.out.println(message);
		Lib.log(message);
		connectionToClient = openForConnections.accept();
		message = "Client Connected";
		System.out.println(message);
		Lib.log(message);
		// Opens stream top out socket for sending and receiving
		outStream = new PrintWriter(connectionToClient.getOutputStream());
		inStream = new BufferedReader(new InputStreamReader(connectionToClient.getInputStream()));
				
		return("Client connected");
	}
	
	public String receive()
	{
		String text = "";
		try {
			text = inStream.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public void send(String text)
	{
		message = text;
		System.out.println(message);
		Lib.log(message);
		outStream.println(text);
		outStream.flush();
	}
	
	public void exit()
	{
		try{
			inStream.close();
			outStream.close();
			connectionToClient.close();
			connectionToClient.close();
		} catch(IOException e) {
			System.err.println("Error on close.");
		}
		message = "Server shutting down!";
		System.out.println(message);
		Lib.log(message);
	}
}

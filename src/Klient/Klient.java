package Klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Klient {
	
	private Socket connectionToServer;
	private PrintWriter outStream;
	private BufferedReader inStream;
	
	//connect to server
	public void connectToServer(String address, int port) throws IOException {
		connectionToServer = new Socket(address, port);
		
		//setup streams
		outStream = new PrintWriter(connectionToServer.getOutputStream());
		inStream = new BufferedReader(new InputStreamReader(connectionToServer.getInputStream()));
	}
	
	//send message to server
	public void send() {
		
	}
	
	//receive message from server
	public void receive() {
		
	}
	
	//show message in text area
	public void showMessage(final String m) {
		
	}
	
	//close streams and connections
	public void close() {
		System.out.println("connection closed");
		try {
			inStream.close();
			outStream.close();
			connectionToServer.close();			
		}catch(IOException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}

}
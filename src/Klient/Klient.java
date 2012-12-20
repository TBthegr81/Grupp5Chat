package Klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingUtilities;

public class Klient {
	
	private Socket connectionToServer;
	private PrintWriter outStream;
	private BufferedReader inStream;
	private String message = "";
	
	//connect to server
	public void connectToServer(String address, int port) throws IOException {

		//skicka lite text till servern som frågar om att få connecta
		//if sats för att ta emot svar från servern
		connectionToServer = new Socket(address, port);
		
		//setup streams
		outStream = new PrintWriter(connectionToServer.getOutputStream());
		inStream = new BufferedReader(new InputStreamReader(connectionToServer.getInputStream()));
	}
	
	//login to server
	public void loginToServer() {
		connect();
		receive();
	}
	
	public void connect() {
		try {
			inStream = new BufferedReader(new InputStreamReader(connectionToServer.getInputStream()));
			outStream = new PrintWriter(connectionToServer.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//send message to server
	public void send(String m) {
		outStream.write("" + m);
		outStream.flush();
		showMessage("" + m);
	}
	
	//receive message from server
	public void receive() {
		while(!message.equals("SERVER - END")){
			message = (String) inStream.toString();
			showMessage("\n" + message);	
		}
	}
	
	//show message in text area
	public void showMessage(final String m) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//append text to chat window
			}
		});
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
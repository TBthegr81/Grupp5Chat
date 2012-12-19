package Klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingUtilities;

public class Klient {
	
	private Socket connection;
	private PrintWriter outStream;
	private BufferedReader inStream;
	private String message = "";
	private String address;
	private String port;
	
	//konstruktor
	Klient() {
	}
	
//	//connect to server
//	public void connectToServer(String address, int port) throws IOException {
//
//		//skicka lite text till servern som frågar om att få connecta
//		//if sats för att ta emot svar från servern
//		connection = new Socket(address, port);
//		
//		//setup streams
//		outStream = new PrintWriter(connection.getOutputStream());
//		inStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//	}
	
	public void logInToServer() {
	//	connect();
	//	receive();
	}
	
	public void connect(String address, int port) {
		showMessage("connecting to: " + address + "...");
		try {
			connection = new Socket(address, port);
			inStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			outStream = new PrintWriter(connection.getOutputStream());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
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
		while(!message.equals("SERVER - END")){		//annan lösning här såklart
			try {
				message = inStream.readLine();
			} catch (IOException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
			showMessage("\n" + message);
		}
	}
	
	//show message in text area
	public void showMessage(final String m) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				/*append text to chat window*/
				
			}
		});
	}
	
	//close streams and connections
	public void close() {
		System.out.println("connection closed");
		try {
			inStream.close();
			outStream.close();
			connection.close();			
		}catch(IOException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}

}
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
	private String user;
	private String address;
	private int port;
	
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
	
	public void startRunning() {
		address = "127.0.0.1";
		port = 54602;
		connect(address, port);
		receive();
	}
	
	//connect to server and setup streams
	public void connect(String address, int port) {
		System.out.println("connecting to: " + address + "...");
		try {
			connection = new Socket(address, port);
			inStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			outStream = new PrintWriter(connection.getOutputStream());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		System.out.println("Connected to: " + connection.getInetAddress().getHostName());
	}
	
	//send message to server
	public void send(String m) {
		outStream.write("" + m);
		outStream.flush();
	}
	
	//receive message from server
	public void receive() {
		try {
			if(inStream.readLine() != null) {
				System.out.println("asldhlksdkvn");
				for(int i = 0; i < 100; i++) {
					outStream.println("SUPerUser" + i);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				user = inStream.readLine();
				message = inStream.readLine();
				System.out.println(user + message);
				Main.gui.showReceivedMessage(message, user);
				outStream.println("SuerUser");
				outStream.flush();
			}			
		}catch(IOException e){
			System.out.println("Error: " + e.getMessage());
		}
		
		
		//while message is not SERVER - END, keep receiving
		do {
			try {
				user = inStream.readLine();
				message = inStream.readLine();
			} catch (IOException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
			Main.gui.showReceivedMessage(message, user);				
		}while(!message.equals("SERVER - END")); //annan lösning här såklart
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

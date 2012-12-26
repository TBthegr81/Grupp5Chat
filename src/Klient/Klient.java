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
	private int answer;
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
		address = "10.0.0.1";
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
		//while message is not SERVER - END, keep receiving
		do {
			try {
				String fromServer[] = inStream.readLine().split("\\s+",3);
				//answer takes the int that decides what state is to be used in answerCase()
				answer = Integer.parseInt(fromServer[0]);
				user = fromServer[1];
				message = fromServer[2];
				answerCase(answer, user, message);
				Main.gui.showReceivedMessage(message, user);
			}catch (IOException e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}while(!message.equals("SERVER - END")); //annan lösning här såklart
	}
	
	
	public void answerCase(int answer, String user, String message) {
		switch(answer){
		case 1:			//connected to server, got welcome message
			outStream.println("SuperUser");
			outStream.flush();
			break;
		case 2:			//username accepted
			outStream.println("OK");
			outStream.flush();
			break;
		case 3:			//username not accepted... get new from GUI
			outStream.println("SuperUser2");
			outStream.flush();
			break;
		case 4:			//got MOT
			outStream.println("OK");
			outStream.flush();
			break;
		case 5:			//list of users
			outStream.println("OK");
			outStream.flush();
			break;
		case 6:			//list of rooms
			outStream.println("OK");
			outStream.flush();
			break;
		case 7:
			//TODO get input from GUI
			break;
		case 8:
			//lalalala
			break;
		}
	}
	
	//close streams and connections
	public void close() {
		System.out.println("closing connections");
		try {
			inStream.close();
			outStream.close();
			connection.close();			
		}catch(IOException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		System.out.println("connections closed");
	}
}
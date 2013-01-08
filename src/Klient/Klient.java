package Klient;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingUtilities;

public class Klient {

	private Socket connection;
//	private PrintWriter outStream;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
//	private BufferedReader inStream;
//	private FileWriter output;
	private String message = "";
	private String user;
	private String address;
	private String userName;
	private int answer;
	private int port;

	//konstruktor
	Klient() {
	}

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
//			inStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			inStream = new ObjectInputStream((connection.getInputStream()));
//			outStream = new PrintWriter(connection.getOutputStream());
			outStream = new ObjectOutputStream(connection.getOutputStream());
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
		System.out.println("Connected to: " + connection.getInetAddress().getHostName());
	}

	//checks the output from the gui input field then sends if appropriate
	public void checkMessage(String m) {
		switch(m) {
		case "/dc":
			send(answer + " " + "klient" + " " + m);
			close();
		default:
			send(answer + " " + "klient" + " " + m);
		}
	}
	
	//chose username for current session
	public void choseUsername() {
		userName = Main.gui.messageInputField.getText();
	}
	
//	public void writeFile(String fileName, String textToWrite) throws IOException {
//		//open output stream
//		output = new FileWriter(fileName);
//		
//		try{
//			output.write(textToWrite);
//		}catch(IOException e) {
//			System.err.println("ERROR: " + e.getMessage());
//		}
//		
//		//close output stream
//		try{
//			output.close();
//		}catch(IOException e) {
//			System.err.println("ERROR: " + fileName + e.getMessage());
//		}
//	}

	//send message to server
	public void send(String m) {
		outStream.println(m);
		outStream.flush();
	}

	//receive message from server
	public void receive() {
		//while message is not SERVER - END, keep receiving
		do {
			try {
//				System.out.println(inStream.readLine() + "\n");
				String fromServer[] = inStream.readLine().split("\\s+",3);
				//answer takes the int that decides what state is to be used in answerCase()
				answer = Integer.parseInt(fromServer[0]);
				user = fromServer[1];
				message = fromServer[2];
				System.out.println(answer + " " + user + " " + message);
				answerCase(answer, user, message);
				Main.gui.showReceivedMessage(message, user);
			}catch (IOException e) {
				System.err.println("ERROR: " + e.getMessage());
			}
		}while(!message.equals("SERVER - END")); //annan lösning här såklart, eller?
		close();
	}


	public void answerCase(int answer, String user, String message) {
		switch(answer){
		case 1:			//connected to server, got welcome message
			send(answer + " " + "klient" + " " + "Ted");
			break;
		case 2:			//username accepted
			send(answer + " " + "klient" + " " + "OK");
			break;
		case 3:			//username not accepted... get new from GUI
			send(answer + " " + "klient" + " " + "FlowDan");
			break;
		case 4:			//got MOT
			send(answer + " " + "klient" + " " + "OK");
			break;
		case 5:			//list of users
			chopStrings(message);
//			fillUsers(message);
			send(answer + " " + "klient" + " " + "OK");
			break;
		case 6:			//list of rooms
			Main.gui.messageInputField.setEditable(true);
			chopStrings(message);
			System.out.println("choose a room");
			break;
		case 7:			//send messages
			System.out.println("Ready to type.");
			break;
		case 8:			//get messages
			System.out.println("");
			break;
		}
	}

	public String[] chopStrings(String m) {
		String serverInfo[] = m.split("\\s+");
		for(int i = 0; i < serverInfo.length; i++) {
			System.out.println(serverInfo[i] + " ");
		}
		return serverInfo;
	}

	public void fillUsers(String m) {
		/*Main.gui.usersWindow*/chopStrings(m);
	}

	//close streams and connections
	public void close() {
		System.out.println("closing connections");
		try {
			inStream.close();
			outStream.close();
			connection.close();
		}catch(IOException e){
			System.err.println("ERROR: " + e.getMessage());
		}
		System.out.println("connections closed");
	}
}
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
	private String userName = "klient";
	private int answer;
	private int port;
	private int id;

	//konstruktor
	Klient()
	{
	}

	public void startRunning(String address, int port)
	{
		connect(address, port);
		try {
			receive();
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}

	//connect to server and setup streams
	public void connect(String address, int port)
	{
		System.out.println("connecting to: " + address + "...");
		try {
			connection = new Socket(address, port);
			//			inStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//			outStream = new PrintWriter(connection.getOutputStream());
			inStream = new ObjectInputStream((connection.getInputStream()));
			outStream = new ObjectOutputStream(connection.getOutputStream());
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
		System.out.println("Connected to: " + connection.getInetAddress().getHostName());
	}

	//checks the output from the gui input field then sends if appropriate
	public void checkMessage(String m)
	{
		String arr[] = m.split(" ", 2);
		String firstWord = arr[0];
		String rest = arr[1];
		
		switch(firstWord) {
		case "/dc":
			send(answer, userName, m);
			close();
			break;
		case "/nick":
			send(answer, userName, rest);
			break;
		default:
			send(answer, userName, m);
			break;
		}
	}

	//chose username for current session
	public void choseUsername()
	{
		userName = Main.gui.messageInputField.getText();
	}

	//	public void writeFile(String fileName, String textToWrite) throws IOException
	//	{
	//		//open output stream
	//		output = new FileWriter("msglog.txt");
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
	//			System.err.println("ERROR: " + e.getMessage());
	//		}
	//	}

	//send message to server
	public void send(int id, String userName, String message)
	{
		Message outMessage = new Message(id, userName, message);
		try {
			outStream.writeObject(outMessage);
			outStream.flush();
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}

	//receive message from server
	public void receive() throws ClassNotFoundException, IOException
	{
		//while message is not SERVER - END, keep receiving
		Message inputMessage = null;
		do {
			inputMessage = (Message) inStream.readObject();
			//answer takes the int that decides what state is to be used in answerCase()
			answer = inputMessage.getId();
			user = inputMessage.getUsername();
			message = inputMessage.getMessage();
			System.out.println(answer + " " + user + " " + message);
			answerCase(answer, message);
			Main.gui.showReceivedMessage(message, user);
		}while((inputMessage = (Message) inStream.readObject()) != null);
		close();
	}

	public void answerCase(int answer, String user)
	{
		switch(answer){
		case 1:			//connected to server, got welcome message
			send(answer, "Ted", "hej");
			break;
		case 2:			//username accepted
			send(answer, userName, "OK");
			break;
		case 3:			//username not accepted... get new from GUI
			send(answer, "FlowDaN", "lala");
			break;
		case 4:			//got MOT
			send(answer, userName, "OK");
			break;
		case 5:			//list of users
			chopStrings(message);
			//			fillUsers(message);
			send(answer, userName, "OK");
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

	//chops a string att every blankspace
	public String[] chopStrings(String m)
	{
		String serverInfo[] = m.split("\\s+");
		for(int i = 0; i < serverInfo.length; i++) {
			System.out.println(serverInfo[i] + " ");
		}
		return serverInfo;
	}

	public void fillUsers(String m)
	{
		/*Main.gui.usersWindow*/chopStrings(m);
		//use method in gui?
	}

	//close streams and connections
	public void close()
	{
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
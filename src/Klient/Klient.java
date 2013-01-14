package Klient;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import Delat.Message;

public class Klient {

	private Socket connection;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	private Message inputMessage;
	private String message = "";
	private String user;
	private String userName = "klient";
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
		String firstWord = null;
		String rest = null;
		
		String arr[] = m.split(" ", 2);
		firstWord = arr[0];
		rest = arr[1];
		
		if(firstWord != null || rest != null) {
			switch(firstWord) {
			case "/dc":
				send(id, userName, m);
				close();
				break;
			case "/nick":
				send(id, userName, rest);
				break;
			default:
				send(id, userName, m);
				break;
			}
		}
	}

	//send message to server
	public void send(int id, String userName, String message)
	{
		Message outMessage = new Message(id, userName, message);
		try {
			outStream.writeObject(outMessage);
			outStream.flush();
			String chunk = Integer.toString(id) + userName + message;
			log(chunk);
			System.out.println(Integer.toString(id) + userName + message);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}

	//receive message from server
	public void receive() throws ClassNotFoundException, IOException
	{
		while((inputMessage = (Message) inStream.readObject()) != null) {
			//answer takes the int that decides what state is to be used in answerCase()
			id = inputMessage.getId();
			user = inputMessage.getUsername();
			message = inputMessage.getMessage();
			Main.gui.showReceivedMessage(message, user);
			System.out.println(id + " " + user + " " + message);
			String chunk = Integer.toString(id) + user + message;
			log(chunk);
			answerCase(id, message);
			//			Main.gui.showReceivedMessage(message, user);			
		}
		close();
	}

	public void answerCase(int id, String user)
	{
		switch(id){
		case 1:			//connected to server, got welcome message
			userName = "Ted";
			send(id, userName, userName);
			break;
		case 2:			//username accepted
			send(id, userName, "OK");
			break;
		case 3:			//username not accepted... get new from GUI
			System.out.println("röv");
			Main.gui.messageInputField.setEditable(true);
//			userName = write();
//			send(id, userName, userName);
//			Main.gui.messageInputField.setEditable(false);
			break;
		case 4:			//got MOT
			send(id, userName, "OK");
			break;
		case 5:			//list of users
			chopStrings(message);
			//			fillUsers(message);
			send(id, userName, "OK");
			break;
		case 6:			//list of rooms
			Main.gui.messageInputField.setEditable(true);
			chopStrings(message);
			System.out.println("choose a room");
//			String room = write();
//			send(id, userName, room);
			break;
		case 7:			//send messages
			System.out.println("Ready to type.");
//			String message = write();
//			send(id, userName, message);
			break;
		}
	}
	
	public String write()
	{
		Scanner input = new Scanner(System.in);
		String message = input.nextLine();
		return message;
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

	public void log(String m)
	{
		//open output stream and create writer
		FileWriter output = null;
		try {
			output = new FileWriter("log.txt", true);
		} catch (IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}

		//write to file
		try {
			output.write(m + System.lineSeparator());
		}catch(IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}

		//close stream
		try {
			output.close();
		}catch(IOException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
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
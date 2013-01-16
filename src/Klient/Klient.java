package Klient;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Delat.Message;

public class Klient {
	//This is the backend of the chat client.

	private Socket connection;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	private Message inputMessage;
	private String message = "";
	private String room;
	private String user;
	private String userName = null;
	private int id;
	public ArrayList<String> listUsers = new ArrayList<String>();
	public ArrayList<String> listRooms = new ArrayList<String>();


	//konstruktor
	Klient()
	{
	}
	
	//initiates the klient
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
		String arr[] = m.split(" ", 2);		//separates the string at the first whitespace
		
		try {
			firstWord = arr[0];
			rest = arr[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("ERROR: Only sent one word in message, resulting in ArrayIndexOutOfBoundsException when trying to acces arr[1]");
		}
		
			switch(firstWord) {				//checks if the first word in the message is a command
			case "/dc":
				send(id, userName, null, "has disconnected from the server...");
				Main.gui.showReceivedMessage("Disconnected...", "Hal9000");
				close();
				break;
			case "/nick":
				send(id, userName, null, rest);
				break;
			default:
				send(id, userName, null, m);
				break;
			}
	}

	//send message to server and logs message in log file
	public void send(int id, String userName, String room, String message)
	{
		Message outMessage = new Message(id, userName, room, message);
		try {
			outStream.writeObject(outMessage);
			outStream.flush();
			String chunk = Integer.toString(id) + " " + userName + " " + room + " " + message;
			log(chunk);
			System.out.println(chunk);
		} catch (IOException e) {
			Main.gui.showReceivedMessage("You are not connected to a server...", "Hal9000");
			System.err.println("ERROR: " + e.getMessage());
		}
	}

	//receive message from server
	public void receive() throws ClassNotFoundException, IOException
	{	//loop runs while inStream is open
		while((inputMessage = (Message) inStream.readObject()) != null) {																			
			id = inputMessage.getId();										//id takes the int that decides what state to called used in answerCase()
			user = inputMessage.getUsername();
			room = inputMessage.getRoom();
			message = inputMessage.getMessage();
			if(id != 0) {
				Main.gui.showReceivedMessage(message, user);				//sends the message and user data to gui				
			}
			String chunk = Integer.toString(id) + user + room + message;	//saves the entire message in a string
			System.out.println(chunk);										//prints the entire message in console
			log(chunk);														//logs entire message in log file
			answerCase(id, message);			
		}
		close();
	}

	public void answerCase(int id, String user)
	{
		switch(id){
		case 1:			//connected to server, got welcome message
			send(id, userName, null, Main.mainData.userName);
			break;
		case 2:			//username accepted
			userName = message;
			Main.gui.serverMessage("Username: " + message + " accepted!");
			send(id, userName, null, "OK");
			break;
		case 3:			//username not accepted... get new from GUI
			GUI.messageInputField.setEditable(true);
			UserNameFailWindow fail = new UserNameFailWindow();
			fail.setVisible(true);
			break;
		case 4:			//got MOT
			send(id, userName, null, "OK");
			break;
		case 5:			//list of rooms
			listRooms = chopStrings(message);
			GUI.messageInputField.setEditable(true);
			send(id, userName, null, "OK");
			break;
		case 6:			//welcome to room
			send(id, userName, null, "OK");
			break;
		case 7:			//list of users
			send(id, userName, null, "OK");
			GUI.userList = chopStrings(message);
			GUI.setMemberList();
			break;
		case 8:			//send messages
			System.out.println("ready to type!");
			break;
		}
	}
	
	//method used to send strings from the console used sometimes while debugging
	public String write()
	{
		Scanner input = new Scanner(System.in);
		String message = input.nextLine();
		input.close();
		return message;		
	}

	//chops a string att every blankspace
	public ArrayList<String> chopStrings(String m)
	{
		m = m.replace("<", ".").replace(">", "");
		
		ArrayList<String> serverInfo = new ArrayList<String>(Arrays.asList(m.split("\\s+")));
		for(int i = 0; i < serverInfo.size(); i++) {
			System.out.println(serverInfo.get(i) + " ");
		}
		return serverInfo;
	}
	
	//id getter used by gui
	public int idGetter(){
		return id;
	}
	
	//writes to log file
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

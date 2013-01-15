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
		String arr[] = m.split(" ", 2);
		
		try {
			firstWord = arr[0];
			rest = arr[1];			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
			switch(firstWord) {
			case "/dc":
				send(id, userName, null, m);
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

	//send message to server
	public void send(int id, String userName, String room, String message)
	{
		Message outMessage = new Message(id, userName, room, message);
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
			room = inputMessage.getRoom();
			message = inputMessage.getMessage();
			Main.gui.showReceivedMessage(message, user);
			System.out.println(id + " " + user + " " + room + " " + message);
			String chunk = Integer.toString(id) + user + room + message;
			log(chunk);
			answerCase(id, message);			
		}
		close();
	}

	public void answerCase(int id, String user)
	{
		switch(id){
		case 1:			//connected to server, got welcome message
			Main.gui.messageInputField.setEditable(true);
			break;
		case 2:			//username accepted
			userName = message;
			Main.gui.serverMessage("Username: " + message + " accepted!");
			send(id, userName, null, "OK");
			break;
		case 3:			//username not accepted... get new from GUI
			break;
		case 4:			//got MOT
			send(id, userName, null, "OK");
			break;
		case 5:			//list of users
			listUsers = chopStrings(message);
//			fillUsers(message);
			send(id, userName, null, "OK");
			break;
		case 6:			//list of rooms
			listRooms = chopStrings(message);
//			send(id, userName, room);
			break;
		case 7:			//send messages
			System.out.println("Ready to type.");
//			send(id, userName, message);
			break;
		}
	}
	
	//method used to send strings from the console used sometimes while debugging
	public String write()
	{
		Scanner input = new Scanner(System.in);
		String message = input.nextLine();
		return message;
	}

	//chops a string att every blankspace
	public ArrayList<String> chopStrings(String m)
	{
		ArrayList<String> serverInfo = new ArrayList<String>(Arrays.asList(m.split("\\s+")));
//		for(int i = 0; i < serverInfo.length; i++) {
//			System.out.println(serverInfo[i] + " ");
//		}
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
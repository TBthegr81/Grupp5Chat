package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private Socket socket = null;
    private Socket connectionToClient;
	private PrintWriter outStream;
	private BufferedReader inStream;
	String message;
	String servername;
	ArrayList<String> settings;
	int port;

    public ServerThread(Socket socket) {
	super("ServerThread");
	this.socket = socket;
    }

    public void run() {
		try {
			// Opens stream top out socket for sending and receiving
			try {
				outStream = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				message = "Error: " + e.getMessage();
				System.err.println(message);
				Lib.log(message);
			}
			try {
				inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				message = "Error: " + e.getMessage();
				System.err.println(message);
				Lib.log(message);
			}
			
		    String inputLine = "";
		    String outputLine = "";
		    ChatProtocol cpc = new ChatProtocol();
		    outputLine = cpc.read(null);
		    outStream.println(outputLine);
		    
		    do{
				/*
		    	if (inputLine == null || inputLine.equals("END"))
			    {
			    	break;
			    }*/
				outputLine = cpc.read(inputLine);
				outStream.println(outputLine);
				outStream.flush();
			    
			}
			while ((inputLine = inStream.readLine()) != null);
			close();
	
		} catch (IOException e) {
			message = "Error: " + e.getMessage();
			System.err.println(message);
			Lib.log(message);
		}
    }
    
	public void close()
	{
		try{
			inStream.close();
			outStream.close();
			connectionToClient.close();
		} catch(IOException e) {
			System.err.println("Error on close.");
		}
		message = "Server shutting down!";
		System.out.println(message);
		Lib.log(message);
	}
	
	public static int createUser()
	{
		User thisUser = new User();
		Main.users.add(thisUser);
		return Main.users.indexOf(thisUser);
	}
	
	public static int createRoom()
	{
		Room thisRoom = new Room();
		Main.rooms.add(thisRoom);
		return Main.rooms.indexOf(thisRoom);
	}
	
	public static String getUsers()
	{
		String text = "";
		for(int i = 0; i < Main.users.size(); i++)
		{
			text = text + "<" + Main.users.get(i).getNickname() + "> ";
		}
		return text;
	}
	
	public static String getRooms()
	{
		String text = "";
		for(int i = 0; i < Main.rooms.size(); i++)
		{
			text = text + Main.rooms.get(i).roomName + " ";
		}
		return text;
	}
	
	public static int getRoomIndex(String input)
	{
		int roomid = 0;
		for(int i = 0; i < Main.rooms.size(); i++)
		{
			if(Main.rooms.get(i).getRoomName().equalsIgnoreCase(input))
			{
				roomid = i;
			}
		}
		return roomid;
	}
	
	public static boolean userOK(String input)
	{
		boolean svar = false;
		for(int i = 0; i < Main.users.size(); i++)
		{
			if(input.equalsIgnoreCase(Main.users.get(i).getNickname()) || input.equals("") || input.equalsIgnoreCase("server") || input.equalsIgnoreCase("null"))
			{
				svar = true;
			}
					
		}
		return svar;
	}
}
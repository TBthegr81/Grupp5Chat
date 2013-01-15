package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import Delat.Message;

public class ServerThread extends Thread {
    private Socket socket = null;
    private Socket connectionToClient;
	//private PrintWriter outStream;
	private ObjectOutputStream outStream;
	//private BufferedReader inStream;
	private ObjectInputStream inStream;
	String message;
	String servername;
	private InetAddress ip = null;
	private User user = null;
	ArrayList<String> settings;
	private String text;
	int port;
	int i = 0;

    public ServerThread(User thisUser, Socket socket) {
	//super("ServerThread");
	text = "Thread Created";
	Lib.print(text);
	Lib.log(text);
	this.socket = socket;
	this.user = thisUser;
	ip = socket.getInetAddress();
	Lib.print(ip.getHostName().toString());
    }

    public void run() {
    	text = "Thread Started";
    	Lib.print(text);
    	Lib.log(text);
		try {
			// Opens stream top out socket for sending and receiving
			try {
				//outStream = new PrintWriter(socket.getOutputStream());
				outStream = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				message = "Error: " + e.getMessage();
				System.err.println(message);
				Lib.log(message);
			}
			try {
				//inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				inStream =  new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				message = "Error: " + e.getMessage();
				System.err.println(message);
				Lib.log(message);
			}
			
		    //String inputLine = "";
		    //String outputLine = "";
			Message inputMessage = null;
		    Message outputMessage = null;
		    ChatProtocol cpc = new ChatProtocol(this.user);
		    //outputLine = cpc.read(null);
		    //outputMessage = cpc.read(null);
		    //outStream.writeObject(outputMessage);
		    try {
		    	do{
					//System.out.println("Doing somthing " + i);
					if (inputMessage != null && inputMessage.getMessage().equals("/dc"))
				    {
				    	break;
				    }
					//outputLine = cpc.read(inputLine);
					outputMessage = cpc.read(inputMessage,ip);
					outStream.writeObject(outputMessage);
					outStream.flush();
				}
		    	while ((inputMessage = (Message) inStream.readObject()) != null);
			} catch (ClassNotFoundException e) {
				System.err.println(e);
			}
		    close();
		    
	
		} catch (IOException e) {
			message = "Error Bajs: " + e.getMessage();
			System.err.println(message);
			Lib.log(message);
			close();
		}
    }
    
	public void close()
	{
		try{
			inStream.close();
			outStream.close();
			//connectionToClient.close();
		} catch(IOException e) {
			System.err.println("Error on close.");
		}
		message = "Thread shutting down!";
		System.out.println(message);
		Lib.log(message);
	}
	
	
	public static int createRoom(String roomname)
	{
		Room thisRoom = new Room();
		Main.rooms.add(thisRoom);
		int rum = Main.rooms.indexOf(thisRoom);
		Main.rooms.get(rum).setRoomName(roomname);
		System.out.println("Room Created: " + Main.rooms.get(rum).getRoomName());
		return rum;
	}
	
	public static String getRooms()
	{
		String text = "";
		for(int i = 0; i < Main.rooms.size(); i++)
		{
			text = text + Main.rooms.get(i).getRoomName() + " ";
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
			if(input.equalsIgnoreCase(Main.users.get(i).getNickname()) || input.equals("") || input.equalsIgnoreCase("server") || input.equalsIgnoreCase("null") ||input.equalsIgnoreCase("tb"))
			{
				svar = true;
			}
					
		}
		return svar;
	}
	
	public void sendMessage(Message input)
	{
		try {
			outStream.writeObject(input);
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
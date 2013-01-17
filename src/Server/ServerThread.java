package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import Delat.Message;

public class ServerThread extends Thread {
	
	/*
	 * ServerThread är den ena av de två köttiga delarna som bygger upp server-programmet
	 * Skapas en ServerThread för varje user och den håller koll på komunikationen med user.
	 * När user skriver något skickas det vidare till ChatProtocol för hantering av den inputen
	 * 
	 * Som vanligt börjar vi deklarera ett gäng variabler.
	 */
    private Socket socket = null;
	private ObjectOutputStream outStream;
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
	/*
	 * Constructorn startar lite sockets och printar ut status.
	 */
	text = "Thread Created";
	Lib.print(text);
	Lib.log(text);
	this.socket = socket;
	this.user = thisUser;
	ip = socket.getInetAddress();
	Lib.print(ip.getHostName().toString());
    }

    public void run() {
    	/*
    	 * Run() funktionen startar en konversation med en klient, snackar och stänger sedan
    	 */
    	text = "Thread Started";
    	Lib.print(text);
    	Lib.log(text);
		try {
			// Opens stream top out socket for sending and receiving
			try {
				outStream = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				message = "Error: " + e.getMessage();
				System.err.println(message);
				Lib.log(message);
			}
			try {
				inStream =  new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				message = "Error: " + e.getMessage();
				System.err.println(message);
				Lib.log(message);
			}
			// Om Input och Output-streams funkade bra så skapa ett ChatProtocol...
			Message inputMessage = null;
		    Message outputMessage = null;
		    ChatProtocol cpc = new ChatProtocol(this.user);
		    try {
		    	/*
		    	 * Här kommer det roliga. Varje gång en user skriver något så plockas det upp i denna while
		    	 * Skickas till ChatProtocollet som ska fundera ut vad att göra med inputen.
		    	 * Det är en do/while eftersom Servern ska säga den första saken vid en connect.
		    	 * (Nämligen att skicka "välkommen till servern, var vänlig skicka mig ett username du vill ha")
		    	 */
		    	do{
					if (inputMessage != null && inputMessage.getMessage().equals("/dc"))
				    {
				    	break;
				    }
					// Output blir det ChatProtocol tycker det ska vara, skicka output och flusha.
					outputMessage = cpc.read(inputMessage,ip);
					outStream.writeObject(outputMessage);
					outStream.flush();
				}
		    	while ((inputMessage = (Message) inStream.readObject()) != null);
		    	// Sålänge user fortsätter skriva saker, kör denna while.
			} catch (ClassNotFoundException e) {
				System.err.println(e);
			}
		    // När allt är sagt och gjort, kalla på stäng-ner funktionen.
		    close();
		    
	
		} catch (IOException e) {
			message = "Error: " + e.getMessage();
			System.err.println(message);
			Lib.log(message);
			close();
		}
    }
    
	public void close()
	{
		/*
		 * Funktion för att stänga av allt, ta bort usern och rapportera
		 */
		try{
			inStream.close();
			outStream.close();
		} catch(IOException e) {
			System.err.println("Error on close.");
		}
		Main.rooms.get(0).removeUser(user);
		Main.users.remove(user);
		Main.rooms.get(0).say(null,user.getNickname() + " has left the room.");
		message = "Thread shutting down!";
		Lib.print(message);
		Lib.log(message);
	}
	
	public static boolean userOK(String input)
	{
		/*
		 * Funktion som kollar om nickname som är angivet är ok.
		 * Null, Server eller något som redan finns är dåligt.
		 */
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
	
	public void sendMessage(Message input)
	{
		/*
		 * Funktion för att skicka meddelande till users
		 */
		try {
			outStream.writeObject(input);
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
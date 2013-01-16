package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;

public class Main extends Thread
{
	// Börjar med att deklarera ett gäng variabler som ska användas.
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<Room> rooms = new ArrayList<Room>();
	private static String message;
	private static int port;
	private static ServerSocket openForConnections;
	
	public static void main(String[] args) throws IOException
	{
		// En variabel sätts, sålänge den är true kommer servern köra.
		boolean listening = true;
		
		// Öppnar loggen och skriver dit datum och tid som servern startade.
		Date date = new Date();
		Lib.log("\n\nDate: " + date.toString());
		
		// Här börjar servern faktiskt starta upp
		message = "Starting up server...";
		Lib.print(message);
		Lib.log(message);
		
		// Settings laddas
		Lib.loadSettings();
		port = Integer.parseInt( Lib.getSettings().get(1));
		
		// Skapa huvudrummet där alla slängs in när de joinar servern
		Lib.createRoom("Main");
		
		// Nu startas några sockets som den ska övervaka för klienter, misslyckas det stängs programmet av
		try {
			openForConnections = new ServerSocket(port);
		} catch (IOException e) {
		message = "Could not listen on port: " + port;
		System.err.println(message);
		Lib.log(message);
		System.exit(1);
		}
		message = "Server started on port " + port;
		Lib.print(message);
		Lib.log(message);
		
		// Om socketen skapades korrekt ska den börja lyssna efter klienter
		message = "Starts listening to things...";
		Lib.print(message);
		Lib.log(message);
		while (listening)
		{
			// Om någon connectar ska det skapas en ny user.
			User u = Lib.createUser(openForConnections.accept());
			u.start();
		}
		
	}

}
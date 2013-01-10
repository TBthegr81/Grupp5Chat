package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;

public class Main extends Thread
{
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<Room> rooms = new ArrayList<Room>();
	private static String message;
	private static ArrayList<String> settings;
	@SuppressWarnings("unused")
	private static String servername = null;
	private static int port;
	private static ServerSocket openForConnections;
	
	public static void main(String[] args) throws IOException
	{
		Lib.test();
		        boolean listening = true;
		        Date date = new Date();
		Lib.log("\n\nDate: " + date.toString());
		Lib.loadSettings();
		
		message = "Starting up server...";
		System.out.println(message);
		Lib.log(message);
		settings = Lib.settings;
		servername = settings.get(0);
		port = Integer.parseInt(settings.get(1));
		try {
		openForConnections = new ServerSocket(port);
		} catch (IOException e) {
		message = "Could not listen on port: " + port;
		System.err.println(message);
		Lib.log(message);
		System.exit(1);
		}
		message = "Server started on port " + port;
		System.out.println(message);
		Lib.log(message);
		
		message = "Starts listening to things...";
		System.out.println(message);
		Lib.log(message);
		while (listening)
		{
			new ServerThread(openForConnections.accept()).start();
			System.out.println("Client Connected");
		}
	}

}
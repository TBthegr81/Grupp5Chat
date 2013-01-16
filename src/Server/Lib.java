package Server;

import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Innehåller randomfunktioner som kan användas lite wherever
 */
public class Lib {
	final static Charset ENCODING = StandardCharsets.UTF_8;
	public static ArrayList<String> settings = new ArrayList<String>();

	public static void loadSettings()
	{
		/*
		 * Laddar settings ifrån configfil på servern
		 */
		Path path = Paths.get("Grupp5Chat.cfg");
		List<String> list;
		
		// Börjar försöka ladda settings
		String message;
		message = "Trying to load settings...";
		Lib.log(message);
		Lib.print(message);
		try {
			// Ladda in filen till en list
			list = Files.readAllLines(path, ENCODING);
			for(int i = 0; i < list.size(); i++)
			{
				/*
				 * Gå igenom listan och för varje rad, splitta på whitespace
				 * Den ska dock bara splitta på de första 2 whitespaces för att behålla
				 * Server-MOT och annat som kan vara flera ord långa.
				 * De addas sen till ArrayList med settings för att man lätt ska kunna komma åt dem.
				 */
				Lib.log(list.get(i));
				String Stuff[] = list.get(i).split("\\s+",2);
				settings.add(Stuff[1]);
			}
			// Printa ut settings
			for(String line : settings)
			{
				Lib.print(line);
				Lib.log(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			message = "Cant load settings check file";
			Lib.log(message);
			Lib.print(message);
			System.exit(1);
		} finally {
		message = "Settings loaded\n";
		Lib.log(message);
		Lib.print(message);
		}
	}
	
	// Funktion för att ge settings till det som vill ha dem
	public static ArrayList<String> getSettings()
	{
		return settings;
	}

	public static void print(String text)
	{
		/*
		 * Printar till konsol
		 */
		Date date = new Date();
		text = date.toString() + " " + text;
		System.out.println(text);
	}

	public static void log(String text)
	{
		/*
		 * Loggar det man vill logga till en .log fil
		 * Kallas på lite överallt i programmet för att spara debuginfo som kan va rolig att ha senare.
		 */
		FileWriter logging = null;
		try {
			logging = new FileWriter("Grupp5Chat.log", true);
			logging.write(text + System.lineSeparator());
		} catch (IOException e) {
			Lib.print("Error-logging failed, check error-log for details");
			Lib.print(e.getMessage());
		} finally {
			// Close FileOutputStream
			try
			{
				if(logging != null)
				{
					logging.close();
				}
			} catch (IOException e){
				Lib.print("Error on closing file");
			}
		}
	}
	
	/* En gammal testfunktion för att slänga in lite fake-users på servern
	public static void test()
	{
		int room1 = ServerThread.createRoom("Animetalk!");
		Main.rooms.get(room1).setTopic("No topic");
		User user1 = Main.createUser(null);
		user1.setNickname("Ted");
		Main.rooms.get(room1).addUser(user1);
		//Main.rooms.get(room1).setUserLevel(Main.users.get(user1).getNickname(), 2);
		System.out.println("User nr: " + Main.users.indexOf(user1) + " with username: " + user1.getNickname() + " created in room " + Main.rooms.get(room1).getRoomName());

		User user2 = Main.createUser(null);
		user2.setNickname("Fred");
		Main.rooms.get(room1).addUser(user2);
		//Main.rooms.get(room1).setUserLevel(Main.users.get(user2).getNickname(), 2);
		System.out.println("User nr: " + Main.users.indexOf(user2) + " with username: " + user2.getNickname() + " created in room " + Main.rooms.get(room1).getRoomName());


		int room2 = ServerThread.createRoom("Gametalk");
		Main.rooms.get(room2).setTopic("Here we speak about games");
		User user3 = Main.createUser(null);
		user3.setNickname("Tom");
		Main.rooms.get(room1).addUser(user3);
		//Main.rooms.get(room2).setUserLevel(Main.users.get(user3).getNickname(), 1);
		System.out.println("User nr: " + Main.users.indexOf(user3) + " with username: " + user3.getNickname() + " created in room " + Main.rooms.get(room2).getRoomName());

		User user4 = Main.createUser(null);
		user4.setNickname("James");
		Main.rooms.get(room1).addUser(user4);
		//Main.rooms.get(room2).setUserLevel(Main.users.get(user4).getNickname(), 2);
		System.out.println("User nr: " + Main.users.indexOf(user4) + " with username: " + user4.getNickname() + " created in room " + Main.rooms.get(room2).getRoomName());
		
		//System.out.println(ServerThread.getUsers());
		//System.out.println(ServerThread.getRooms());
	}*/
	
	public static Room createRoom(String roomname)
	{
		/*
		 * Funktion för att skapa nytt rum
		 */
		Room thisRoom = new Room();
		Main.rooms.add(thisRoom);
		// Skapa rummet och adda till ArrayListen med rum i Main
		thisRoom.setRoomName(roomname);
		// Sätt ett namn på det rummet
		String text = "Room Created: " + thisRoom.getRoomName();
		Lib.print(text);
		Lib.log(text);
		return thisRoom;
	}
	
	public static User createUser(Socket socket)
	{
		/*
		 * Funktion för att skapa users
		 */
		User thisUser = new User();
		Main.users.add(thisUser);
		// Adda user till ArrayListen i Main
		thisUser.setSocket(socket);
		// Adda en socket till Usern
		return thisUser;
	}
	
	public static String getRooms()
	{
		/*
		 * Funktion som returnar en Sträng med alla rum
		 */
		String text = "";
		for(int i = 0; i < Main.rooms.size(); i++)
		{
			text = text + Main.rooms.get(i).getRoomName() + " ";
		}
		return text;
	}
}
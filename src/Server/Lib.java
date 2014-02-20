package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

/*
 * Innehåller randomfunktioner som kan användas lite wherever
 */
public class Lib {
	//final static Charset ENCODING = StandardCharsets.UTF_8;
	public static ArrayList<String> settings = new ArrayList<String>();

	public static void loadSettings()
	{
		/*
		 * Laddar settings ifrån configfil på servern
		 */
		String initPath = System.getProperty("user.dir");
		//System.out.println(initPath);
		Path path = Paths.get(initPath + File.separator + "Grupp5Chat.cfg");
		
		// Börjar försöka ladda settings
		String message;
		InputStream    inputstream;
		BufferedReader buffreader;
		String line;
		message = "Trying to load settings...";
		Lib.log(message);
		Lib.print(message);
		try {
			// Ladda in filen
			//System.out.println(path.toString());
			inputstream = new FileInputStream(path.toString());
			buffreader = new BufferedReader(new InputStreamReader(inputstream, Charset.forName("UTF-8")));
			while((line = buffreader.readLine()) != null)
			{
				/*
				 * Gå igenom listan och för varje rad, splitta på whitespace
				 * Den ska dock bara splitta på de första 2 whitespaces för att behålla
				 * Server-MOT och annat som kan vara flera ord långa.
				 * De addas sen till ArrayList med settings för att man lätt ska kunna komma åt dem.
				 */
				Lib.log(line);
				String Stuff[] = line.split("\\s+",2);
				settings.add(Stuff[1]);
			}
			// Printa ut settings
			for(String line2 : settings)
			{
				Lib.print(line2);
				Lib.log(line2);
			}
		} catch (IOException e) {
			e.printStackTrace();
			message = "Cant load settings check file";
			Lib.log(message);
			Lib.print(message);
			Lib.CreateSettingsFile();
			Lib.loadSettings();
			System.exit(1);
		} finally {
		message = "Settings loaded\n";
		Lib.log(message);
		Lib.print(message);
		}
	}
	
	public static void CreateSettingsFile()
	{
		String message = "Creating Settingsfile...";
		Lib.log(message);
		Lib.print(message);
		String initPath = System.getProperty("user.dir");
		String settings = "Servername: MyServer\nListenPort: 54602\nMOT: Welcome to this awesome server, dont say stupid things!";
		writeToFile(initPath + File.separator + "Grupp5Chat.cfg", settings);
		message = "Settingsfile created at " + initPath + File.separator;
		Lib.log(message);
		Lib.print(message);
	}
	
	public static void writeToFile(String file, String text)
	{
		/*
		 * Method to write to any file specified. Primarly used by the Log-method.
		 */
		String message;
		FileWriter writer = null;
		
			try {
				writer = new FileWriter(file, true);
			} catch (IOException e) {
				message = "Could not create filewriter." + e.toString();
				Lib.log(message);
				Lib.print(message);
			}
			
			/*
			 * Writes whatever was specified when calling the method and also a lineSeperator.
			 */
			try {
				writer.write(text + System.lineSeparator());
			} catch (IOException e) {
				message = "Could not write to file"+ e.toString();
				Lib.log(message);
				Lib.print(message);
			}
			
			try {
				writer.close();
			} catch (IOException e) {
				message = "Could not close file."+ e.toString();
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
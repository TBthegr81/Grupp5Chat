package Server;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * Innehåller randomfunktioner som kan användas lite wherever
 */
public class Lib {
	final static Charset ENCODING = StandardCharsets.UTF_8;
	public static ArrayList<String> settings;
	
	public static void loadSettings()
	{
		/*
		 * Laddar settings ifrån userfile
		 */
			Path path = Paths.get("./bin/Server/Grupp5Chat.cfg");
			settings = new ArrayList<String>();
		    List<String> list;
		    String message;
		    message = "Trying to load settings...";
		    Lib.log(message);
		    System.out.println(message);
			try {
				list = Files.readAllLines(path, ENCODING);
				for(int i = 0; i < list.size(); i++)
			    {
					Lib.log(list.get(i));
					String Stuff[] = list.get(i).split("\\s+",2);
					settings.add(Stuff[1]);
			    }
				for(String line : settings)
				{
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
				message = "Cant load settings check file";
				Lib.log(message);
				System.out.println(message);
			}
			message = "Settings loaded\n";
			Lib.log(message);
			System.out.println(message);
	}
	
	public static void print(String text)
	{
		/*
		 * Printar till konsol
		 */
		System.out.println(text);
	}
	
	public static void log(String text)
	{
		/*
		 * Ska logga allt som händer på servern
		 */
		FileWriter logging = null;
		try {
			logging = new FileWriter("./bin/Server/Grupp5Chat.log", true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		try {
			logging.write(text + System.lineSeparator());
		} catch (IOException e) {
			System.out.println("Error-logging failed, check error-log for details");
		}
		
		// Close FileOutputStream
			try
			{
				logging.close();
			} catch (IOException e){
				System.out.println("Error on closing file");
			}
	}
	
	public static void test()
	{
		int room1 = ServerThread.createRoom();
 		Main.rooms.get(room1).setRoomName("Animetalk!");
 		Main.rooms.get(room1).setTopic("No topic");
 		int user1 = ServerThread.createUser();
 		Main.users.get(user1).setNickname("Ted");
 		Main.rooms.get(room1).addUser(Main.users.get(user1));
 		Main.rooms.get(room1).setUserLevel(Main.users.get(user1).getNickname(), 2);
 		System.out.println("User nr: " + user1 + " with username: " + Main.users.get(user1).getNickname() + " created in room " + Main.rooms.get(room1).roomName);
 		
 		int user2 = ServerThread.createUser();
 		Main.users.get(user2).setNickname("Fred");
 		Main.rooms.get(room1).addUser(Main.users.get(user2));
 		Main.rooms.get(room1).setUserLevel(Main.users.get(user2).getNickname(), 2);
 		System.out.println("User nr: " + user2 + " with username: " + Main.users.get(user2).getNickname() + " created in room " + Main.rooms.get(room1).roomName);
 		
 		
 		int room2 = ServerThread.createRoom();
 		Main.rooms.get(room2).setRoomName("Gametalk");
 		Main.rooms.get(room2).setTopic("Here we speak about games");
 		int user3 = ServerThread.createUser();
 		Main.users.get(user3).setNickname("Tom");
 		Main.rooms.get(room2).addUser(Main.users.get(user3));
 		Main.rooms.get(room2).setUserLevel(Main.users.get(user3).getNickname(), 1);
 		System.out.println("User nr: " + user3 + " with username: " + Main.users.get(user3).getNickname() + " created in room " + Main.rooms.get(room2).roomName);
 		
 		int user4 = ServerThread.createUser();
 		Main.users.get(user4).setNickname("James");
 		Main.rooms.get(room2).addUser(Main.users.get(user4));
 		Main.rooms.get(room2).setUserLevel(Main.users.get(user4).getNickname(), 2);
 		System.out.println("User nr: " + user4 + " with username: " + Main.users.get(user4).getNickname() + " created in room " + Main.rooms.get(room2).roomName);
 		
 		for(int i = 0; i < Main.users.size(); i++)
 		{
 			System.out.println(Main.users.get(i).getNickname());
 		}
 		
 		System.out.println(ServerThread.getUsers());
 		System.out.println(ServerThread.getRooms());
	}
}

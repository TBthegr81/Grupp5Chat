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
import java.util.List;

/*
 * Inneh�ller randomfunktioner som kan anv�ndas lite wherever
 */
public class Lib {
	final static Charset ENCODING = StandardCharsets.UTF_8;
	public static ArrayList<String> settings;

	public static void loadSettings()
	{
		/*
		 * Laddar settings ifr�n userfile
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
	}
}
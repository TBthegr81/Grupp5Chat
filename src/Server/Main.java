package Server;

import java.util.ArrayList;

public class Main {
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<Room> rooms = new ArrayList<Room>();
	
	public static void main(String[] args) {
		Lib.log("\n\n");
		Lib.loadSettings();
		Server awesomeserver = null;
		try {
			awesomeserver = new Server();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		awesomeserver.start();
		Lib.test();
		awesomeserver.listenForClients();
		
		awesomeserver.close();
	}

}

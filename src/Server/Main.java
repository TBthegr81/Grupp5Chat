package Server;

import java.io.IOException;

public class Main {
	
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
		
		awesomeserver.listenForClients();
		
		awesomeserver.exit();
	}

}

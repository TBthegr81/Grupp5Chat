package Klient;

import java.io.IOException;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) {
		Klient klient = new Klient();
		
		try {
			klient.connectToServer("localhost", 54602);
		}catch(UnknownHostException e) {
			System.out.println("ERROr: " + e.getMessage());
		}catch(IOException e) {
			System.out.println("ERROr: " + e.getMessage());
		}
	}
}
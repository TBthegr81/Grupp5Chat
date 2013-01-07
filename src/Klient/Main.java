package Klient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {

	//Temp solution for storing messages. Joakim
	public static ArrayList<String> messageArray = new ArrayList<String>();
	public static GUI gui = new GUI();
	public static Klient klient = new Klient();
	
	public static void main(String[] args) {
		klient.startRunning();

		gui.setVisible(true);
	}
}
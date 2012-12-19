package Klient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {

	//Temp solution for storing messages. Joakim
	static ArrayList<String> messageArray = new ArrayList<String>();
	static GUI gui = new GUI();
	
	public static void main(String[] args) {

		Klient klient = new Klient();		
		gui.setVisible(true);
	}
}
package Klient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {

	public static GUI gui;
	public static Klient klient;

	public static void main(String[] args) {

		Klient klient = new Klient();
//		GUI gui = new GUI();

		klient.startRunning("10.0.0.1", 54602);
//		gui.setVisible(true);
	}
}

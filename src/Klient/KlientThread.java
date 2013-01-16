package Klient;

public class KlientThread implements Runnable {
	//Mainly used for deciding what should be contained in a thread.

	@Override
	public void run() {
		Main.klient.startRunning(Main.gui.connectMenu.BookmarkGetIp(), Main.gui.connectMenu.BookmarkGetPort());
	}

}

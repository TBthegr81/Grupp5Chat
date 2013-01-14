package Klient;

public class KlientThread implements Runnable {

	@Override
	public void run() {
		Main.klient.startRunning(Main.gui.connectMenu.BookmarkGetIp(), Main.gui.connectMenu.BookmarkGetPort());
	}

}

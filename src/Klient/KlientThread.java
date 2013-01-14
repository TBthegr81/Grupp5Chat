package Klient;

public class KlientThread implements Runnable {

//  private int port;
//	private String address;
//	
//	KlientThread() 
//	{
//		int port = 54602;
//		String address = "10.0.0.1";
//	}

	@Override
	public void run() {
		Main.klient.startRunning("10.0.0.1", 54602);
	}

}

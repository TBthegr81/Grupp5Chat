package Server;

import java.net.InetAddress;
import java.net.Socket;

import Delat.Message;

/*
* Objektklass får users på servern
* Håller variabler om nicks och metoder för när de vill göra saker
*/
public class User
{
	private String nickname;
	private InetAddress ip = null;
	private ServerThread myThread = null;
	private Socket openForConnections;
	private String text;
	
	User()
	{
		text = "Client Connected!";
		Lib.print(text);
		Lib.log(text);
	}
	
	public void setSocket(Socket mySocket)
	{
		ip = mySocket.getInetAddress();
		openForConnections = mySocket;
	}
	
	public void setNickname(String name)
	{
	nickname = name;
	}
	
	public String getNickname()
	{
	return nickname;
	}
	
	public void setIP(InetAddress newIp)
	{
		ip = newIp;
	}
	
	public InetAddress getIP()
	{
		return ip;
	}
	
	// Startfunktionen skapar en ny ServerThread och startar den så det kan hända något
	public void start()
	{
		myThread = new ServerThread(this,openForConnections);
		myThread.start();
	}
	
	// Alternativet mot att ha en funktion som bara kallar på en annan funktion var att ha ServerThread publik
	// Detta lät bättre
	public void send(Message message)
	{
		myThread.sendMessage(message);
	}
}
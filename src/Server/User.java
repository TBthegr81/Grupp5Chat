package Server;

import java.net.InetAddress;
import java.net.Socket;

import Delat.Message;
import Delat.Spell;

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
	private int mana = 0;
	private int hp = 0;
	
	User()
	{
		text = "Client Connected!";
		Lib.print(text);
		Lib.log(text);
		mana = 10;
		hp = 30;
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
	
	public void start()
	{
		myThread = new ServerThread(this,openForConnections);
		myThread.start();
	}
	
	public void send(Message message)
	{
		myThread.sendMessage(message);
	}
	
	public String cast(Spell mySpell, User target)
	{
		mana =- mySpell.getCost();
		target.looseHP(mySpell.getDamage());
		String text = this.getNickname() + " attacked " + target.getNickname() +  " who was left with " + target.getHP() + " HP";
		return text;
	}
	
	public void looseHP(int damage)
	{
		hp = hp - damage;
	}
	
	public int getHP()
	{
		return hp;
	}
}
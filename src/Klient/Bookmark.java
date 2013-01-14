package Klient;

import java.io.Serializable;
import java.util.ArrayList;

public class Bookmark implements Serializable{
  
	private static final long serialVersionUID = 1L;
	public ArrayList<String> nickname;
	public ArrayList<String> ipAdress;
	public ArrayList<Integer> port;
	public String userName;
	
	public Bookmark(){
		nickname = new ArrayList<String>();
		ipAdress = new ArrayList<String>(); 
		port = new ArrayList<Integer>();
		userName = "Default";
	}
	
	public void addBookmark(String nick, String ip, int po){
		nickname.add(nick);
		ipAdress.add(ip);
		port.add(po);
		System.out.println(nick + " created.");
	}
	public void listAllBookmarks(){
		
		if (nickname.isEmpty() == false){
		System.out.println("There are " + nickname.size() + " bookmarks saved.");
		
		for (int i = 0; i<nickname.size();i++){
			System.out.println(nickname.get(i));
			System.out.println(ipAdress.get(i));
			System.out.println(port.get(i));
		}
		
		}
		else {
			System.out.println("There are no bookmarks saved.");
		}
	}
	
}


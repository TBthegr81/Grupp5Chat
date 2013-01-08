package Server;

import java.util.ArrayList;

public class Message {
	private int id;
	private String username;
	private String message;
	
	Message(int newId, String newUsername, String newMessage)
	{
		id = newId;
		username = newUsername;
		message = newMessage;
	}
	
	public ArrayList<String> getData()
	{
		ArrayList<String> data = new ArrayList<String>();
		data.add(id + "");
		data.add(username);
		data.add(message);
		return data;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String message()
	{
		return message;
	}
}

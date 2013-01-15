package Delat;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String room;
	private String message;

	//the object we send 
	public Message(int id, String username, String room, String message)
	{
		this.id = id;
		this.username = username;
		this.room = room;
		this.message = message;
	}

	public ArrayList<String> getData()
	{
		ArrayList<String> data = new ArrayList<String>();
		data.add(id + "");
		data.add(username);
		data.add(room);
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

	public String getRoom()
	{
		return room;
	}

	public String getMessage()
	{
		return message;
	}
}
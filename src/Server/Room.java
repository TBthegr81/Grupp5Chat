package Server;

import java.util.ArrayList;

import Delat.Message;

/*
* Objektklass får channels/rum på servern
* Håller rummens variabler och de funktioner rummen ska kunna kalla på
*/
public class Room {
	private String roomName;
	private String topic;
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Integer> userLevels = new ArrayList<Integer>();
	
	/*
	 * Alla dessa funktioner med set/get behöver väll inte förklaras närmare
	 * De sätter och hämtar värden till rummet
	 */
	public void setRoomName(String newName)
	{
		roomName = newName;
	}

	public String getRoomName()
	{
		return roomName;
	}

	public void setTopic(String newTopic)
	{
		topic = newTopic;
	}

	public String getTopic()
	{
		return topic;
	}

	public void addUser(User user)
	{
		users.add(user);
		userLevels.add(0);
	}

	public void setUserLevel(String username, int level)
	{
		userLevels.set(users.indexOf(username), level);
	}
	
	// Skickar tillbaka sträng med alla users
	public String getUsersString()
	{
		String users = "";
		 for(int i = 0; i < this.users.size(); i++)
		{
			 users += this.users.indexOf(this.users.get(i)) + "<" + this.users.get(i).getNickname() + "> ";
		}
		return users;
		
	}

	// Funktion som loopar rummets users och kallar på deras respektive "send" funktion.
	// Så alla users i rummet får det meddelande som skickas in med denna funktion
	public void say(User user, String message)
	{
		String text = "<" + user.getNickname() + "> " + message;
		//messages.add(text);
		Lib.log(text);
		
		for(int i = 0; i < users.size(); i++)
		{
			users.get(i).send(new Message(8, user.getNickname(), this.getRoomName(), message));
		}
	}
}
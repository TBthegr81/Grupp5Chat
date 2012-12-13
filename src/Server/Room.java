package Server;
/*
 * Objektklass för channels/rum på servern
 * Håller rummens variabler och de funktioner rummen ska kunna kalla på
 */
public class Room {

	String roomName;
	
	Room()
	{
		
	}
	
	public void setRoomName(String newName)
	{
		roomName = newName;
	}
	
	public String getRoomName()
	{
		return roomName;
	}
}

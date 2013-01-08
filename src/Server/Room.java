package Server;

import java.util.ArrayList;

/*
* Objektklass för channels/rum på servern
* Håller rummens variabler och de funktioner rummen ska kunna kalla på
*/
public class Room {

String roomName;
String topic;
ArrayList<String> users = new ArrayList<String>();
ArrayList<Integer> userLevels = new ArrayList<Integer>();
ArrayList<String> messages = new ArrayList<String>();

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
users.add(user.getNickname());
userLevels.add(0);
}

public void setUserLevel(String username, int level)
{
userLevels.set(users.indexOf(username), level);
}

public ArrayList<String> getUsers()
{
ArrayList<String> returnList = new ArrayList<String>();
for(int i = 0; i < users.size(); i++)
{
returnList.add(userLevels.get(i) + " " + users.get(i));
}
return returnList;
}

public String getUsersString()
{
String tillbaka = "";
ArrayList<String> listan = getUsers();
for(String i : listan)
{
tillbaka = tillbaka + " " + i;
}
return tillbaka;
}

public void Say(User user, String message)
{
String text = "<" + user.getNickname() + "> " + message;
messages.add(text);
Lib.log(text);
}
}
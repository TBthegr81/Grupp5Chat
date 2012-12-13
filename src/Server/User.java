package Server;
/*
 * Objektklass för users på servern
 * Håller variabler om nicks och metoder för när de vill göra saker
 */
public class User {
	private String nickname;
	
	User()
	{
		
	}
	
	public void setNickname(String name)
	{
		nickname = name;
	}
	
	public String getNickname()
	{
		return nickname;
	}
}

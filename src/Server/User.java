package Server;
/*
* Objektklass f�r users p� servern
* H�ller variabler om nicks och metoder f�r n�r de vill g�ra saker
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
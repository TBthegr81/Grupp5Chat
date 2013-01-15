package Delat;

public class Spell {
	private String name;
	private int cost;
	private int type;
	private int damage;
	
	public Spell(String name, int cost, int type, int damage)
	{
		this.name = name;
		this.cost = cost;
		this.type = type;
		this.damage = damage;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getDamage()
	{
		return damage;
	}
}

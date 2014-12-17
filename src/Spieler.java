/*
 * This class represents a player
 */

public class Spieler 
{
	protected String sName;
	protected int iGeld;
	protected int iPosition;
	protected boolean bAussetzen;

	// -> Standard-Konstruktor
	Spieler(String sName)
	{
		this.sName = sName;
		this.iGeld = 2;
		this.iPosition = 0;
		this.bAussetzen = false;
	}	
	
	// -> Name
	public void SetName(String sName)
	{
		this.sName = sName;
	}
	
	public String GetName()
	{
		return sName;
	}
	
	// -> Geld
	public void SetGeld(int iGeld)
	{
		this.iGeld = iGeld;
	}
	
	public int GetGeld()
	{
		return iGeld;
	}	
	
	// -> Position
	public void SetPosition(int iPosition)
	{
		this.iPosition = iPosition;
	}
	
	public int GetPosition()
	{
		return iPosition;
	}	
	
	// -> Aussetzen
	public void SetAussetzen(boolean bAussetzen)
	{
		this.bAussetzen = bAussetzen;
	}
	
	public boolean GetAussetzen()
	{
		return bAussetzen;
	}
}

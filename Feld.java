public class Feld 
{
	protected String sName;
	protected int iKosten;
	protected int iEssen;
	protected boolean bBelegt;
	protected int iBesitzer;
	
	// -> Standard-Konstruktor
	Feld(String sName, int iKosten, int iEssen)
	{
		this.sName = sName;
		this.iKosten = iKosten;
		this.iEssen = iEssen;
		this.bBelegt = false;
		this.iBesitzer = -1;
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
	
	// -> Kosten
	public void SetKosten(int iKosten)
	{
		this.iKosten = iKosten;
	}
	
	public int GetKosten()
	{
		return iKosten;
	}
	
	// -> Essen
	public void SetEssen(int iEssen)
	{
		this.iEssen = iEssen;
	}
	
	public int GetEssen()
	{
		return iEssen;
	}
	
	// -> Frei
	public void SetBelegung(boolean bStatus)
	{
		this.bBelegt = bStatus;
	}
	
	public boolean GetBelegung()
	{
		return bBelegt;
	}
	
	// -> Besitzer
	public void SetBesitzer(int iBesitzer)
	{
		this.iBesitzer = iBesitzer;
	}
	
	public int GetBesitzer()
	{
		return iBesitzer;
	}
}

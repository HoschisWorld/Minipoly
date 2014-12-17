import java.util.Scanner;

public class Game 
{
	Scanner input = new Scanner(System.in);
	
	private Spieler Spieler[] = new Spieler[4];
	private Feld Feld[] = new Feld[8];
	
	private int iWuerfel = 0;
	private int iJackpot = 0;
	private int iSpieler = 0;
	private int iAkteur = 0;
	private int iOldPosition = 0;	
	private int iUpgrade = 0;	
	private String sValue;
	private boolean bGameOver = false;
	
	
	// Init-Part
	public void InitGame()
	{		
		System.out.println("\nWillkommen bei Minipoly");
		
		// -> Abfrage zur Spieleranzahl
		do 
		{
			System.out.println("\n\nWie viele Spieler?");
			iSpieler = input.nextInt();
				
		} while (iSpieler < 2 || iSpieler > 4);

		// -> Initialisierung der einzlenen Spieler
		for (int i = 0; i < iSpieler; i++)
		{
			System.out.println("\nName des >> " + i + " << Spielers?");			
			Spieler[i] = new Spieler(input.next());
		}

		// -> Initialisierung der einzelnen Felder
		Feld[0] = new Feld("Los", 0, 0); 
		Feld[1] = new Feld("Cafeteria", 2, 1);
		Feld[2] = new Feld("Party", 0, -1);
		Feld[3] = new Feld("Restaurant", 4, 2);
		Feld[4] = new Feld("Jackpot", 0, 0);
		Feld[5] = new Feld("Casino", 6, 3);
		Feld[6] = new Feld("Zur Party", 0, 0);
		Feld[7] = new Feld("Schloss", 8, 4);
		
		System.out.println("\n\nZum starten ENTER drücken!");
		input.nextLine();
	}
	
	// Loop-Part
	public void RunGame()
	{
		do
		{
			// Der Würfel Abschnitt
			if (!(Spieler[iAkteur].GetAussetzen()))
			{
				System.out.println("\n\nSpieler >> " + Spieler[iAkteur].GetName() + "<< bitte wuerfeln (ENTER)");
				input.nextLine();
				iWuerfel = Wuerfel(1, 7);
				System.out.println("\nSie haben eine >> " + iWuerfel + " << gewuerfelt");
			}

			else
			{
				System.out.println("\n\nDer Spieler >> " + Spieler[iAkteur].GetName() + " << muss leider diese Runde aussetzen");
				Spieler[iAkteur].SetAussetzen(false);
				
				iAkteur++;

				if (iAkteur >= iSpieler)
				{
					iAkteur = 0;
				}

				System.out.println("\nWeiter mit ENTER");
				continue;
			}
			
			// Der Spielerpositionierungsabschnitt
			iOldPosition = Spieler[iAkteur].GetPosition();
			Spieler[iAkteur].SetPosition(Spieler[iAkteur].GetPosition() + iWuerfel);
			
			if (Spieler[iAkteur].GetPosition() > 7)
			{
				Spieler[iAkteur].SetPosition(Spieler[iAkteur].GetPosition() - 8);
			}

			// Der Feldabschnitt
			switch (Spieler[iAkteur].GetPosition())
			{
				case 0:
					System.out.println("\n\nSie stehen auf dem Feld >> " + Feld[0].GetName());
					System.out.println("\nSie bekommen 2 Euro auf Ihrem Konto gutgeschrieben");
					Spieler[iAkteur].SetGeld(Spieler[iAkteur].GetGeld() + 2);
					System.out.println("\n-> Ihr Aktueller Kontostand betraegt nun: >> " + Spieler[iAkteur].GetGeld() + " << Euro");
					break;

				case 1:
					ManageFeld(1);
					break;					

				case 2:
					System.out.println("\n\nSie stehen auf dem Feld >> " + Feld[2].GetName());
					System.out.println("\nSie muessen 1 Euro in den Jackpot zahlen");

					if (ViaLos(iOldPosition, Spieler[iAkteur].GetPosition()))
					{
						System.out.println("\n\nSie gingen ueber Los und bekommen zudem 1 Euro auf Ihrem Konto gutgeschrieben");
						Spieler[iAkteur].SetGeld(Spieler[iAkteur].GetGeld() + 1);
					}
					
					Spieler[iAkteur].SetGeld(Spieler[iAkteur].GetGeld() - 1);
					iJackpot++;
					System.out.println("\n-> Ihr Aktueller Kontostand betraegt nun: >> " + Spieler[iAkteur].GetGeld() + " << Euro");
					break;					

				case 3:
					ManageFeld(3);
					break;
					
				case 4:
					System.out.println("\n\nSie stehen auf dem Feld >> " + Feld[4].GetName());
					System.out.println("\nSie bekommen den Inhalt des Jackpots >> " + iJackpot + " << Euro auf Ihrem Konto gutgeschrieben");

					if (ViaLos(iOldPosition, Spieler[iAkteur].GetPosition()))
					{
						System.out.println("\nSie gingen ueber Los und bekommen zudem 1 Euro auf Ihrem Konto gutgeschrieben");
						Spieler[iAkteur].SetGeld(Spieler[iAkteur].GetGeld() + 1);
					}

					Spieler[iAkteur].SetGeld(Spieler[iAkteur].GetGeld() + iJackpot);
					iJackpot = 0;
					System.out.println("\n-> Ihr Aktueller Kontostand betraegt nun: >> " + Spieler[iAkteur].GetGeld() + " << Euro");
					break;

				case 5:
					ManageFeld(5);
					break;
					
				case 6:
					System.out.println("\n\nSie stehen auf dem Feld >> " + Feld[6].GetName());
					System.out.println("\nSie gehen direkt und nicht ueber Los zur Party, ohne 1 Euro einzuziehen");					
					Spieler[iAkteur].SetPosition(2);
					Spieler[iAkteur].SetAussetzen(true);					
					break;

				case 7:
					ManageFeld(7);
					break;
			}

			System.out.println("\n\n--> Ihr Zug ist beendet");
			System.out.println("\n-------------------------------------------------------------------------------");

			// -> Der Endbedingungsabschnitt (Kontostand -10€)
			for (int i = 0; i < iSpieler; i++)
			{
				if (Spieler[i].GetGeld() < -10)
				{
					bGameOver = true;
				}
			}

			// -> Nächster Spieler
			iAkteur++;

			if (iAkteur >= iSpieler)
			{
				iAkteur = 0;
			}	
			
		} while (!bGameOver);
	}
	
	// Clean-Part
	public void CleanUp()
	{
		// -> Ausgabe des Gewinners
		int iIndex = 0;
		int iGewinner = 0;
		int iEndSumme = 0;

		do 
		{
			if (Spieler[iIndex].GetGeld() > iEndSumme)
			{
				iEndSumme = Spieler[iIndex].GetGeld();
				iGewinner = iIndex;
			}
			
			iIndex++;
			
		} while (iIndex < iSpieler);

		System.out.println("\nDer Gewinner ist >> " + Spieler[iGewinner].GetName() + " << mit >> " + Spieler[iGewinner].GetGeld() + "<< Euro auf dem Konto - GLUECKWUNSCH");
	}

	
	// -> Funktion: Würfel (Random)
	private int Wuerfel(int low, int high) 
	{
		return (int) (Math.random() * (high - low) + low);
	}
	
	
	// -> Funktion: Überprüfe ob Spieler über 'Los' ging
	private boolean ViaLos (int vorher, int nachher)
	{
		if (vorher > nachher)
		{
			return true;
		}
	
		else
		{
			return false;
		}
	}
	
	// -> Funktion: Feld-Management
	private void ManageFeld(int iFeldID)
	{
		System.out.println("\n\nSie stehen auf dem Feld >> " + Feld[iFeldID].GetName());

		if (ViaLos(iOldPosition, Spieler[iAkteur].GetPosition()))
		{
			System.out.println("\n\nSie gingen ueber Los und bekommen zudem 1 Euro auf Ihrem Konto gutgeschrieben");
			Spieler[iAkteur].SetGeld(Spieler[iAkteur].GetGeld() + 1);
		}

		System.out.println("\n-> Ihr Aktueller Kontostand betraegt nun: >> " + Spieler[iAkteur].GetGeld() + " << Euro");

		if (!Feld[iFeldID].GetBelegung())
		{
			do
			{
				System.out.println("\n\nMoechten Sie das Feld >> " + Feld[iFeldID].GetName() + " kaufen [J] / [N]");
				System.out.println("\nDer Kaufpreis betraegt >> " + Feld[iFeldID].GetKosten() + " Euro");
				sValue = input.next();
			} while (!(sValue.equals("j") || sValue.equals("J") || sValue.equals("n") || sValue.equals("N")));

			switch (sValue)
			{
				case "j": case "J":
					
					if (Spieler[iAkteur].GetGeld() >= Feld[iFeldID].GetKosten())
					{
						System.out.println("\nSie kauften das Feld >> " + Feld[iFeldID].GetName() + " << GLUECKWUNSCH");
						Spieler[iAkteur].SetGeld(Spieler[iAkteur].GetGeld() - Feld[iFeldID].GetKosten());
						Feld[iFeldID].SetBelegung(true);
						Feld[iFeldID].SetBesitzer(iAkteur);
						System.out.println("\nIhr neuer Kontostand betraegt >> " + Spieler[iAkteur].GetGeld() + " << Euro");
					}

					else
					{
						System.out.println("\nSie koennen sich das Feld nicht leisten - PECH");
					}

					break;

				case "n": case "N":
					System.out.println("\nSie wollen das Feld nicht kaufen - Pech gehabt");
					break;
			}
		}

		else if (Feld[iFeldID].GetBesitzer() == iAkteur)
		{
			System.out.println("\n\nSie besitzen bereits dieses Feld");
			System.out.println("\nSie koennen das Feld nun aufstocken, indem Sie die Essenskosten erhoehen");

			do 
			{
				System.out.println("\nMochten Sie nun einen bestimmten Betrag finanzieren, um das Feld aufzustocken");
				System.out.println("\nJa [J] oder Nein [N] - (Enter) - ");
				sValue = input.next();
			} while (!(sValue.equals("j") || sValue.equals("J") || sValue.equals("n") || sValue.equals("N")));

			switch (sValue)
			{
				case "j": case "J":
					System.out.println("\nGeben Sie bitte den gewuenschten Betrag an: ");
					iUpgrade = input.nextInt();

					if (Spieler[iAkteur].GetGeld() >= iUpgrade)
					{
						Feld[iFeldID].SetKosten((Feld[iFeldID].GetKosten() + iUpgrade));
						Spieler[iAkteur].SetGeld(Spieler[iAkteur].GetGeld() - iUpgrade);
						System.out.println("\nDie Essenkosten des Feldes wurden auf >> " + Feld[iFeldID].GetEssen() + " << Euro erhoeht");
					}

					else
					{
						System.out.println("\nSie haben nicht genuegend Geld hierfuer");
					}

					break;

				case "n": case "N":
					System.out.println("\nDas Feld wird nicht aufgestockt - Pech gehabt");
					break;
			}
		}

		else
		{
			System.out.println("\n\nDieses Feld gehoert bereits Spieler >> " + Spieler[Feld[iFeldID].GetBesitzer()].GetName());
			System.out.println("\nSie muessen diesem Spieler >> " + Feld[iFeldID].GetEssen() + " << Euro bezahlen");
			Spieler[iAkteur].SetGeld(Spieler[iAkteur].GetGeld() - Feld[iFeldID].GetEssen());
			Spieler[Feld[iFeldID].GetBesitzer()].SetGeld(Spieler[Feld[iFeldID].GetBesitzer()].GetGeld() + Feld[iFeldID].GetEssen());
			System.out.println("\n-> Ihr Aktueller Kontostand betraegt nun: >> " + Spieler[iAkteur].GetGeld() + " << Euro");
		}	
	}
}

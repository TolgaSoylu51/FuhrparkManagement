package Code;

public class Fahrer {
	private int ID; 
	private int Personalnummer;
	private String AktivKZ;
	private String Name;
	private String Vorname;
	private String FirmaNr;
	private String NL_Nr;
	private String Fahrerlaubnis;
	private String Erstpr�fung;
	private String Pr�fungszeitpunkt1;
	private String Kommentar1;
	private String Zweitpr�fung;
	private String Pr�fungszeitpunkt2;
	private String Kommentar2;
	
	public Fahrer(int iD, int personalnummer, String aktivKZ, String name, String vorname, String firmaNr, String nL_Nr,
			String fahrerlaubnis, String erstpr�fung, String pr�fungszeitpunkt1, String kommentar1, String zweitpr�fung,
			String pr�fungszeitpunkt2, String kommentar2) {
		super();
		ID = iD;
		Personalnummer = personalnummer;
		AktivKZ = aktivKZ;
		Name = name;
		Vorname = vorname;
		FirmaNr = firmaNr;
		NL_Nr = nL_Nr;
		Fahrerlaubnis = fahrerlaubnis;
		Erstpr�fung = erstpr�fung;
		Pr�fungszeitpunkt1 = pr�fungszeitpunkt1;
		Kommentar1 = kommentar1;
		Zweitpr�fung = zweitpr�fung;
		Pr�fungszeitpunkt2 = pr�fungszeitpunkt2;
		Kommentar2 = kommentar2;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getPersonalnummer() {
		return Personalnummer;
	}

	public void setPersonalnummer(int personalnummer) {
		Personalnummer = personalnummer;
	}

	public String getAktivKZ() {
		return AktivKZ;
	}

	public void setAktivKZ(String aktivKZ) {
		AktivKZ = aktivKZ;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getVorname() {
		return Vorname;
	}

	public void setVorname(String vorname) {
		Vorname = vorname;
	}

	public String getFirmaNr() {
		return FirmaNr;
	}

	public void setFirmaNr(String firmaNr) {
		FirmaNr = firmaNr;
	}

	public String getNL_Nr() {
		return NL_Nr;
	}

	public void setNL_Nr(String nL_Nr) {
		NL_Nr = nL_Nr;
	}

	public String getFahrerlaubnis() {
		return Fahrerlaubnis;
	}

	public void setFahrerlaubnis(String fahrerlaubnis) {
		Fahrerlaubnis = fahrerlaubnis;
	}

	public String getErstpr�fung() {
		return Erstpr�fung;
	}

	public void setErstpr�fung(String erstpr�fung) {
		Erstpr�fung = erstpr�fung;
	}

	public String getPr�fungszeitpunkt1() {
		return Pr�fungszeitpunkt1;
	}

	public void setPr�fungszeitpunkt1(String pr�fungszeitpunkt1) {
		Pr�fungszeitpunkt1 = pr�fungszeitpunkt1;
	}

	public String getKommentar1() {
		return Kommentar1;
	}

	public void setKommentar1(String kommentar1) {
		Kommentar1 = kommentar1;
	}

	public String getZweitpr�fung() {
		return Zweitpr�fung;
	}

	public void setZweitpr�fung(String zweitpr�fung) {
		Zweitpr�fung = zweitpr�fung;
	}

	public String getPr�fungszeitpunkt2() {
		return Pr�fungszeitpunkt2;
	}

	public void setPr�fungszeitpunkt2(String pr�fungszeitpunkt2) {
		Pr�fungszeitpunkt2 = pr�fungszeitpunkt2;
	}

	public String getKommentar2() {
		return Kommentar2;
	}

	public void setKommentar2(String kommentar2) {
		Kommentar2 = kommentar2;
	}
	
}

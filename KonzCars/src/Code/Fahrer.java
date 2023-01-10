package Code;

public class Fahrer {
	private int ID; 
	private String Personalnummer;
	private int AktivKZ;
	private String Name;
	private String Vorname;
	private String FirmaNr;
	private int NL_Nr;
	private String Fahrerlaubnis;
	private String Erstpruefung;
	private String Pruefungszeitpunkt1;
	private String Kommentar1;
	private String Zweitpruefung;
	private String Pruefungszeitpunkt2;
	private String Kommentar2;
	private int Bearbeitet;
	private String FahrzeugID;
	
	public Fahrer(int iD, String personalnummer, int aktivkz, String name, String vorname, String firmaNr, int nlnr,
			String fahrerlaubnis, String erstpruefung, String pruefungszeitpunkt1, String kommentar1, String zweitpruefung,
			String pruefungszeitpunkt2, String kommentar2, int bearbeitet, String fahrzeugID
			) {
		super();
		ID = iD;
		Personalnummer = personalnummer;
		AktivKZ = aktivkz;
		Name = name;
		Vorname = vorname;
		FirmaNr = firmaNr;
		NL_Nr = nlnr;
		Fahrerlaubnis = fahrerlaubnis;
		Erstpruefung = erstpruefung;
		Pruefungszeitpunkt1 = pruefungszeitpunkt1;
		Kommentar1 = kommentar1;
		Zweitpruefung = zweitpruefung;
		Pruefungszeitpunkt2 = pruefungszeitpunkt2;
		Kommentar2 = kommentar2;
		Bearbeitet = bearbeitet;
		FahrzeugID = fahrzeugID;
	}

	public String getPruefungszeitpunkt1() {
		return Pruefungszeitpunkt1;
	}

	public void setPruefungszeitpunkt1(String pruefungszeitpunkt1) {
		Pruefungszeitpunkt1 = pruefungszeitpunkt1;
	}

	public String getPruefungszeitpunkt2() {
		return Pruefungszeitpunkt2;
	}

	public void setPruefungszeitpunkt2(String pruefungszeitpunkt2) {
		Pruefungszeitpunkt2 = pruefungszeitpunkt2;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getPersonalnummer() {
		return Personalnummer;
	}

	public void setPersonalnummer(String personalnummer) {
		Personalnummer = personalnummer;
	}

	public int getAktivKZ() {
		return AktivKZ;
	}

	public void setAktivKZ(int aktivKZ) {
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

	public int getNL_Nr() {
		return NL_Nr;
	}

	public void setNL_Nr(int nL_Nr) {
		NL_Nr = nL_Nr;
	}

	public String getFahrerlaubnis() {
		return Fahrerlaubnis;
	}

	public void setFahrerlaubnis(String fahrerlaubnis) {
		Fahrerlaubnis = fahrerlaubnis;
	}

	public String getErstpruefung() {
		return Erstpruefung;
	}

	public void setErstpruefung(String erstpruefung) {
		Erstpruefung = erstpruefung;
	}

	public String getKommentar1() {
		return Kommentar1;
	}

	public void setKommentar1(String kommentar1) {
		Kommentar1 = kommentar1;
	}

	public String getZweitpruefung() {
		return Zweitpruefung;
	}

	public void setZweitpruefung(String zweitpruefung) {
		Zweitpruefung = zweitpruefung;
	}

	public String getKommentar2() {
		return Kommentar2;
	}

	public void setKommentar2(String kommentar2) {
		Kommentar2 = kommentar2;
	}

	public int getBearbeitet() {
		return Bearbeitet;
	}

	public void setBearbeitet(int bearbeitet) {
		Bearbeitet = bearbeitet;
	}

	public String getFahrzeugID() {
		return FahrzeugID;
	}

	public void setFahrzeugID(String fahrzeugID) {
		FahrzeugID = fahrzeugID;
	}
}

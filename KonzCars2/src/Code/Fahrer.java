package Code;

public class Fahrer {
	private int ID; 
	private int Personalnummer;
	private int AktivKZ;
	private String Name;
	private String Vorname;
	private String FirmaNr;
	private int NL_Nr;
	private String Fahrerlaubnis;
	private String Erstprüfung;
	private String Prüfungszeitpunkt1;
	private String Kommentar1;
	private String Zweitprüfung;
	private String Prüfungszeitpunkt2;
	private String Kommentar2;
	
	public Fahrer(int iD, int personalnummer, int aktivkz, String name, String vorname, String firmaNr, int nlnr,
			String fahrerlaubnis, String erstprüfung, String prüfungszeitpunkt1, String kommentar1, String zweitprüfung,
			String prüfungszeitpunkt2, String kommentar2) {
		super();
		ID = iD;
		Personalnummer = personalnummer;
		AktivKZ = aktivkz;
		Name = name;
		Vorname = vorname;
		FirmaNr = firmaNr;
		NL_Nr = nlnr;
		Fahrerlaubnis = fahrerlaubnis;
		Erstprüfung = erstprüfung;
		Prüfungszeitpunkt1 = prüfungszeitpunkt1;
		Kommentar1 = kommentar1;
		Zweitprüfung = zweitprüfung;
		Prüfungszeitpunkt2 = prüfungszeitpunkt2;
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

	public String getErstprüfung() {
		return Erstprüfung;
	}

	public void setErstprüfung(String erstprüfung) {
		Erstprüfung = erstprüfung;
	}

	public String getPrüfungszeitpunkt1() {
		return Prüfungszeitpunkt1;
	}

	public void setPrüfungszeitpunkt1(String prüfungszeitpunkt1) {
		Prüfungszeitpunkt1 = prüfungszeitpunkt1;
	}

	public String getKommentar1() {
		return Kommentar1;
	}

	public void setKommentar1(String kommentar1) {
		Kommentar1 = kommentar1;
	}

	public String getZweitprüfung() {
		return Zweitprüfung;
	}

	public void setZweitprüfung(String zweitprüfung) {
		Zweitprüfung = zweitprüfung;
	}

	public String getPrüfungszeitpunkt2() {
		return Prüfungszeitpunkt2;
	}

	public void setPrüfungszeitpunkt2(String prüfungszeitpunkt2) {
		Prüfungszeitpunkt2 = prüfungszeitpunkt2;
	}

	public String getKommentar2() {
		return Kommentar2;
	}

	public void setKommentar2(String kommentar2) {
		Kommentar2 = kommentar2;
	}
	
}

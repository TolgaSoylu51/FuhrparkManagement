package Code;

import java.util.Comparator;

public class Fahrzeug {

	private int ID;
	private String IdentNr;
	private String FirmaNr;
	private String NL;
	private String FZG_Marke;
	private String FZG_Typ;
	private String FZG_Bezeichnung;
	private String amtl_Kennzeichen;
	private String Erstzulassung;
	private String Abmeldedatum;
	private String Fahrer;
	private String Fahrer2;
	private String Finanzstatus;
	private String Bank_Leasinggesellschaft;
	private String VertragsNr;
	private String Leasingdauer_Monate;
	private String Verlaengerung_Monate;
	private String Leasingrate_zzgl_MwSt_Fahrzeug;
	private String Vertragsende;
	private String Bemerkung;
	private String Restwert_Leasingende;
	private String Soll_Laufleistung_Km;
	private String km_Stand;
	private String Datum_Erfassung_km_Stand;
	private String Anschaffungswert_Netto;
	private String Finanzierungsrate;
	private String Wartung;
	private String Zulassungsart;
	private String Motorleistung_KW_P_2;
	private String Sommerreifen;
	private String Winterreifen;
	private String Kostenstelle;
	private int Foliert;
	private String Typ;
	private int UVV;
	private int Fahrerunterweisung;
	private int Werkstatteinrichtung;
	private int Belueftung;
	private int Pruefung1;
	private int Pruefung2;
	private int Fahrerlaubnis;
	private int Bearbeitet;
	
	public Fahrzeug(int iD, String identNr, String FirmaNr, String NL, String FZG_Marke, String FZG_Typ, String FZG_Bezeichnung,
			String amtl_Kennzeichen, String Erstzulassung, String Abmeldedatum, String Fahrer,
			String Fahrer2, String Finanzstatus, String Bank_Leasinggesellschaft, String VertragsNr,
			String Leasingdauer_Monate, String Verlaengerung_Monate, String Leasingrate_zzgl_MwSt_Fahrzeug,
			String Vertragsende, String Bemerkung, String Restwert_Leasingende, String Soll_Laufleistung_Km,
			String km_Stand, String Datum_Erfassung_km_Stand, String Anschaffungswert_Netto,
			String Finanzierungsrate, String Wartung, String Zulassungsart,
			String Motorleistung_KW_P_2, String Sommerreifen, String Winterreifen, String Kostenstelle, int Foliert, String Typ, 
			int UVV, int Fahrerunterweisung, int Werkstatteinrichtung, int Belueftung, int Pruefung1, int Pruefung2, int Fahrerlaubnis, int Bearbeitet) {
		super();
		this.ID = iD;
		this.IdentNr = identNr;
		this.FirmaNr = FirmaNr;
		this.NL = NL;
		this.FZG_Marke = FZG_Marke;
		this.FZG_Typ = FZG_Typ;
		this.FZG_Bezeichnung = FZG_Bezeichnung;
		this.amtl_Kennzeichen = amtl_Kennzeichen;
		this.Erstzulassung = Erstzulassung;
		this.Abmeldedatum = Abmeldedatum;
		this.Fahrer = Fahrer;
		this.Fahrer2 = Fahrer2;
		this.Finanzstatus = Finanzstatus;
		this.Bank_Leasinggesellschaft = Bank_Leasinggesellschaft;
		this.VertragsNr = VertragsNr;
		this.Leasingdauer_Monate = Leasingdauer_Monate;
		this.Verlaengerung_Monate = Verlaengerung_Monate;
		this.Leasingrate_zzgl_MwSt_Fahrzeug = Leasingrate_zzgl_MwSt_Fahrzeug;
		this.Vertragsende = Vertragsende;
		this.Bemerkung = Bemerkung;
		this.Restwert_Leasingende = Restwert_Leasingende;
		this.Soll_Laufleistung_Km = Soll_Laufleistung_Km;
		this.km_Stand = km_Stand;
		this.Datum_Erfassung_km_Stand = Datum_Erfassung_km_Stand;
		this.Anschaffungswert_Netto = Anschaffungswert_Netto;
		this.Finanzierungsrate = Finanzierungsrate;
		this.Wartung = Wartung;
		this.Zulassungsart = Zulassungsart;
		this.Motorleistung_KW_P_2 = Motorleistung_KW_P_2;
		this.Sommerreifen = Sommerreifen;
		this.Winterreifen = Winterreifen;
		this.Kostenstelle = Kostenstelle;
		this.Foliert = Foliert;
		this.Typ = Typ;
		this.UVV = UVV;
		this.Fahrerunterweisung = Fahrerunterweisung;
		this.Werkstatteinrichtung = Werkstatteinrichtung;
		this.Belueftung = Belueftung;
		this.Pruefung1 = Pruefung1;
		this.Pruefung2 = Pruefung2;
		this.Fahrerlaubnis = Fahrerlaubnis;
		this.Bearbeitet = Bearbeitet;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public String getIdentNr() {
		return IdentNr;
	}

	public void setIdentNr(String identNr) {
		IdentNr = identNr;
	}
	
	public String getFirmaNr() {
		return FirmaNr;
	}

	public void setFirmaNr(String firmaNr) {
		FirmaNr = firmaNr;
	}

	public String getNL() {
		return NL;
	}

	public void setNL(String nL) {
		NL = nL;
	}

	public String getFZG_Marke() {
		return FZG_Marke;
	}

	public void setFZG_Marke(String fZG_Marke) {
		FZG_Marke = fZG_Marke;
	}

	public String getFZG_Typ() {
		return FZG_Typ;
	}

	public void setFZG_Typ(String fZG_Typ) {
		FZG_Typ = fZG_Typ;
	}

	public String getFZG_Bezeichnung() {
		return FZG_Bezeichnung;
	}

	public void setFZG_Bezeichnung(String fZG_Bezeichnung) {
		FZG_Bezeichnung = fZG_Bezeichnung;
	}

	public String getAmtl_Kennzeichen() {
		return amtl_Kennzeichen;
	}

	public void setAmtl_Kennzeichen(String amtl_Kennzeichen) {
		this.amtl_Kennzeichen = amtl_Kennzeichen;
	}

	public String getErstzulassung() {
		return Erstzulassung;
	}

	public void setErstzulassung(String erstzulassung) {
		Erstzulassung = erstzulassung;
	}

	public String getAbmeldedatum() {
		return Abmeldedatum;
	}

	public void setAbmeldedatum(String abmeldedatum) {
		Abmeldedatum = abmeldedatum;
	}

	public String getFahrer() {
		return Fahrer;
	}

	public void setFahrer(String fahrer) {
		Fahrer = fahrer;
	}

	public String getFahrer2() {
		return Fahrer2;
	}

	public void setFahrer2(String fahrer2) {
		Fahrer2 = fahrer2;
	}

	public String getFinanzstatus() {
		return Finanzstatus;
	}

	public void setFinanzstatus(String finanzstatus) {
		Finanzstatus = finanzstatus;
	}

	public String getBank_Leasinggesellschaft() {
		return Bank_Leasinggesellschaft;
	}

	public void setBank_Leasinggesellschaft(String bank_Leasinggesellschaft) {
		Bank_Leasinggesellschaft = bank_Leasinggesellschaft;
	}

	public String getVertragsNr() {
		return VertragsNr;
	}

	public void setVertragsNr(String vertragsNr) {
		VertragsNr = vertragsNr;
	}

	public String getLeasingdauer_Monate() {
		return Leasingdauer_Monate;
	}

	public void setLeasingdauer_Monate(String leasingdauer_Monate) {
		Leasingdauer_Monate = leasingdauer_Monate;
	}

	public String getVerlaengerung_Monate() {
		return Verlaengerung_Monate;
	}

	public void setVerlaengerung_Monate(String verlaengerung_Monate) {
		Verlaengerung_Monate = verlaengerung_Monate;
	}

	public String getLeasingrate_zzgl_MwSt_Fahrzeug() {
		return Leasingrate_zzgl_MwSt_Fahrzeug;
	}

	public void setLeasingrate_zzgl_MwSt_Fahrzeug(String leasingrate_zzgl_MwSt_Fahrzeug) {
		Leasingrate_zzgl_MwSt_Fahrzeug = leasingrate_zzgl_MwSt_Fahrzeug;
	}

	public String getVertragsende() {
		return Vertragsende;
	}

	public void setVertragsende(String vertragsende) {
		Vertragsende = vertragsende;
	}

	public String getBemerkung() {
		return Bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		Bemerkung = bemerkung;
	}

	public String getRestwert_Leasingende() {
		return Restwert_Leasingende;
	}

	public void setRestwert_Leasingende(String restwert_Leasingende) {
		Restwert_Leasingende = restwert_Leasingende;
	}

	public String getSoll_Laufleistung_Km() {
		return Soll_Laufleistung_Km;
	}

	public void setSoll_Laufleistung_Km(String soll_Laufleistung_Km) {
		Soll_Laufleistung_Km = soll_Laufleistung_Km;
	}

	public String getKm_Stand() {
		return km_Stand;
	}

	public void setKm_Stand(String km_Stand) {
		this.km_Stand = km_Stand;
	}

	public String getDatum_Erfassung_km_Stand() {
		return Datum_Erfassung_km_Stand;
	}

	public void setDatum_Erfassung_km_Stand(String datum_Erfassung_km_Stand) {
		Datum_Erfassung_km_Stand = datum_Erfassung_km_Stand;
	}

	public String getAnschaffungswert_Netto() {
		return Anschaffungswert_Netto;
	}

	public void setAnschaffungswert_Netto(String anschaffungswert_Netto) {
		Anschaffungswert_Netto = anschaffungswert_Netto;
	}

	public String getFinanzierungsrate() {
		return Finanzierungsrate;
	}

	public void setFinanzierungsrate(String finanzierungsrate) {
		Finanzierungsrate = finanzierungsrate;
	}

	public String getWartung() {
		return Wartung;
	}

	public void setWartung(String wartung) {
		Wartung = wartung;
	}

	public String getZulassungsart() {
		return Zulassungsart;
	}

	public void setZulassungsart(String zulassungsart) {
		Zulassungsart = zulassungsart;
	}

	public String getMotorleistung_KW_P_2() {
		return Motorleistung_KW_P_2;
	}

	public void setMotorleistung_KW_P_2(String motorleistung_KW_P_2) {
		Motorleistung_KW_P_2 = motorleistung_KW_P_2;
	}

	public String getSommerreifen() {
		return Sommerreifen;
	}

	public void setSommerreifen(String sommerreifen) {
		Sommerreifen = sommerreifen;
	}

	public String getWinterreifen() {
		return Winterreifen;
	}

	public void setWinterreifen(String winterreifen) {
		Winterreifen = winterreifen;
	}

	public String getKostenstelle() {
		return Kostenstelle;
	}

	public void setKostenstelle(String kostenstelle) {
		Kostenstelle = kostenstelle;
	}

	public int getFoliert() {
		return Foliert;
	}

	public void setFoliert(int foliert) {
		Foliert = foliert;
	}

	public String getTyp() {
		return Typ;
	}

	public void setTyp(String typ) {
		Typ = typ;
	}
	
	public int getUVV() {
		return UVV;
	}

	public void setUVV(int uvv) {
		UVV = uvv;
	}
	
	public int getFahrerunterweisung() {
		return Fahrerunterweisung;
	}

	public void setFahrerunterweisung(int fahrerunterweisung) {
		Fahrerunterweisung = fahrerunterweisung;
	}
	
	public int getWerkstatteinrichtung() {
		return Werkstatteinrichtung;
	}

	public void setWerkstatteinrichtung(int werkstatteinrichtung) {
		Werkstatteinrichtung = werkstatteinrichtung;
	}
	
	public int getBelueftung() {
		return Belueftung;
	}

	public void setBelueftung(int belueftung) {
		Belueftung = belueftung;
	}
	
	public int getPruefung1() {
		return Pruefung1;
	}

	public void setPruefung1(int pruefung1) {
		Pruefung1 = pruefung1;
	}
	
	public int getPruefung2() {
		return Pruefung2;
	}

	public void setPruefung2(int fahrerlaubnis) {
		Fahrerlaubnis = fahrerlaubnis;
	}
	
	public int getFahrerlaubnis() {
		return Fahrerlaubnis;
	}

	public void setFahrerlaubnis(int fahrerlaubnis) {
		Fahrerlaubnis = fahrerlaubnis;
	}
	
	public int getBearbeitet() {
		return Bearbeitet;
	}

	public void setBearbeitet(int bearbeitet) {
		Bearbeitet = bearbeitet;
	}
}
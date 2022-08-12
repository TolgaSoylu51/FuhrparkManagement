package Code;

public class Fahrzeug {

	private int ID; 
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
	private String zulaessiges_Gesamtgew_F_1;
	private String Motorleistung_KW_P_2;
	private String Sommerreifen;
	private String Sommer_T_Typ;
	private String Winterreifen;
	private String Winter_T_Typ;
	private String Kostenstelle;
	private String km_Stand_Jan;
	private String km_Stand_Jan_Vorjahr;
	private String km_Stand_Jan_VorVorjahr;
	private String Haftpflicht;
	private String Kasko;
	private String Quartal;
	private String Steuer;
	private String Farbe_Auto;
	private String Foliert;
	private String Folieren_Planung;
	private String Folieren_Farbe;
	private String Regale_Geleast_Gekauft;
	private String Typ;
	private String Belueftung_wegen_Gas;
	
	public Fahrzeug(int ID, String FirmaNr, String NL, String FZG_Marke, String FZG_Typ, String FZG_Bezeichnung,
			String amtl_Kennzeichen, String Erstzulassung, String Abmeldedatum, String Fahrer,
			String Fahrer2, String Finanzstatus, String Bank_Leasinggesellschaft, String VertragsNr,
			String Leasingdauer_Monate, String Verlaengerung_Monate, String Leasingrate_zzgl_MwSt_Fahrzeug,
			String Vertragsende, String Bemerkung, String Restwert_Leasingende, String Soll_Laufleistung_Km,
			String km_Stand, String Datum_Erfassung_km_Stand, String Anschaffungswert_Netto,
			String Finanzierungsrate, String Wartung, String Zulassungsart,
			String zulaessiges_Gesamtgew_F_1, String Motorleistung_KW_P_2, String Sommerreifen,
			String Sommer_T_Typ, String Winterreifen, String Winter_T_Typ, String Kostenstelle,
			String km_Stand_Jan, String km_Stand_Jan_Vorjahr, String km_Stand_Jan_VorVorjahr, String Haftpflicht, 
			String Kasko, String Quartal, String Steuer, String Farbe_Auto, String Foliert, 
			String Folieren_Planung, String Folieren_Farbe, String Regale_Geleast_Gekauft, String Typ, 
			String Belueftung_wegen_Gas) {
		super();
		this.ID = ID;
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
		this.zulaessiges_Gesamtgew_F_1 = zulaessiges_Gesamtgew_F_1;
		this.Motorleistung_KW_P_2 = Motorleistung_KW_P_2;
		this.Sommerreifen = Sommerreifen;
		this.Sommer_T_Typ = Sommer_T_Typ;
		this.Winterreifen = Winterreifen;
		this.Winter_T_Typ = Winter_T_Typ;
		this.Kostenstelle = Kostenstelle;
		this.km_Stand_Jan = km_Stand_Jan;
		this.km_Stand_Jan_Vorjahr = km_Stand_Jan_Vorjahr;
		this.km_Stand_Jan_VorVorjahr = km_Stand_Jan_VorVorjahr;
		this.Haftpflicht = Haftpflicht;
		this.Kasko = Kasko;
		this.Quartal = Quartal;
		this.Steuer = Steuer;
		this.Farbe_Auto = Farbe_Auto;
		this.Foliert = Foliert;
		this.Folieren_Planung = Folieren_Planung;
		this.Folieren_Farbe = Folieren_Farbe;
		this.Regale_Geleast_Gekauft = Regale_Geleast_Gekauft;
		this.Typ = Typ;
		this.Belueftung_wegen_Gas = Belueftung_wegen_Gas;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public String getZulaessiges_Gesamtgew_F_1() {
		return zulaessiges_Gesamtgew_F_1;
	}

	public void setZulaessiges_Gesamtgew_F_1(String zulaessiges_Gesamtgew_F_1) {
		this.zulaessiges_Gesamtgew_F_1 = zulaessiges_Gesamtgew_F_1;
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

	public String getSommer_T_Typ() {
		return Sommer_T_Typ;
	}

	public void setSommer_T_Typ(String sommer_T_Typ) {
		Sommer_T_Typ = sommer_T_Typ;
	}

	public String getWinterreifen() {
		return Winterreifen;
	}

	public void setWinterreifen(String winterreifen) {
		Winterreifen = winterreifen;
	}

	public String getWinter_T_Typ() {
		return Winter_T_Typ;
	}

	public void setWinter_T_Typ(String winter_T_Typ) {
		Winter_T_Typ = winter_T_Typ;
	}

	public String getKostenstelle() {
		return Kostenstelle;
	}

	public void setKostenstelle(String kostenstelle) {
		Kostenstelle = kostenstelle;
	}

	public String getKm_Stand_Jan() {
		return km_Stand_Jan;
	}

	public void setKm_Stand_Jan(String km_Stand_Jan) {
		this.km_Stand_Jan = km_Stand_Jan;
	}
	
	public String getKm_Stand_Jan_Vorjahr() {
		return km_Stand_Jan_Vorjahr;
	}

	public void setKm_Stand_Jan_Vorjahr(String km_Stand_Jan_Vorjahr) {
		this.km_Stand_Jan_Vorjahr = km_Stand_Jan_Vorjahr;
	}
	
	public String getKm_Stand_Jan_VorVorjahr() {
		return km_Stand_Jan_VorVorjahr;
	}

	public void setKm_Stand_Jan_VorVorjahr(String km_Stand_Jan_VorVorjahr) {
		this.km_Stand_Jan_VorVorjahr = km_Stand_Jan_VorVorjahr;
	}

	public String getHaftpflicht() {
		return Haftpflicht;
	}

	public void setHaftpflicht(String haftpflicht) {
		Haftpflicht = haftpflicht;
	}

	public String getKasko() {
		return Kasko;
	}

	public void setKasko(String kasko) {
		Kasko = kasko;
	}

	public String getQuartal() {
		return Quartal;
	}

	public void setQuartal(String quartal) {
		Quartal = quartal;
	}

	public String getSteuer() {
		return Steuer;
	}

	public void setSteuer(String steuer) {
		Steuer = steuer;
	}

	public String getFarbe_Auto() {
		return Farbe_Auto;
	}

	public void setFarbe_Auto(String farbe_Auto) {
		Farbe_Auto = farbe_Auto;
	}

	public String getFoliert() {
		return Foliert;
	}

	public void setFoliert(String foliert) {
		Foliert = foliert;
	}

	public String getFolieren_Planung() {
		return Folieren_Planung;
	}

	public void setFolieren_Planung(String folieren_Planung) {
		Folieren_Planung = folieren_Planung;
	}

	public String getFolieren_Farbe() {
		return Folieren_Farbe;
	}

	public void setFolieren_Farbe(String folieren_Farbe) {
		Folieren_Farbe = folieren_Farbe;
	}

	public String getRegale_Geleast_Gekauft() {
		return Regale_Geleast_Gekauft;
	}

	public void setRegale_Geleast_Gekauft(String regale_Geleast_Gekauft) {
		Regale_Geleast_Gekauft = regale_Geleast_Gekauft;
	}

	public String getTyp() {
		return Typ;
	}

	public void setTyp(String typ) {
		Typ = typ;
	}

	public String getBelueftung_wegen_Gas() {
		return Belueftung_wegen_Gas;
	}

	public void setBelueftung_wegen_Gas(String belueftung_wegen_Gas) {
		Belueftung_wegen_Gas = belueftung_wegen_Gas;
	}
}


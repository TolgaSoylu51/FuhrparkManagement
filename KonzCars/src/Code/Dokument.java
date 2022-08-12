package Code;

public class Dokument {
	
	int ID;
	String DokumentName;
	String Pfad;
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getPfad() {
		return Pfad;
	}

	public void setPfad(String pfad) {
		Pfad = pfad;
	}

	byte[] Dokument;
	String Extension;

	public Dokument(int id, String dokumentName, String pfad , byte[] bs, String extension) {
		super();
		ID = id;
		DokumentName = dokumentName;
		Pfad = pfad;
		Dokument = bs;
		Extension = extension;
	}

	public String getDokumentName() {
		return DokumentName;
	}

	public void setDokumentName(String dokumentName) {
		DokumentName = dokumentName;
	}

	public byte[] getDokument() {
		return Dokument;
	}

	public void setDokument(byte[] dokument) {
		Dokument = dokument;
	}

	public String getExtension() {
		return Extension;
	}

	public void setExtension(String extension) {
		Extension = extension;
	}
	
	public int getId() {
		return ID;
	}

	public void setId(int id) {
		this.ID = id;
	}

}


package Code;

public class Login {

	String UserName;
	String Passwort;
	String LE;
	int PWAendern;
	int ID;
	
	public Login(String userName, String passwort, String le, int id) {
		super();
		UserName = userName;
		Passwort = passwort;
		LE = le;
		ID = id;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPasswort() {
		return Passwort;
	}
	public void setPasswort(String passwort) {
		Passwort = passwort;
	}
	public String getLE() {
		return LE;
	}
	public void setLE(String le) {
		LE = le;
	}
	public int getPWAendern() {
		return PWAendern;
	}
	public void setPWAendern(int pWAendern) {
		PWAendern = pWAendern;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
}
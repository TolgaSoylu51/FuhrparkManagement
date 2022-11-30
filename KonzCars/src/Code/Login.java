package Code;

public class Login {

	String UserName;
	String Passwort;
	int Rolle;
	
	public Login(String userName, String passwort, int rolle) {
		super();
		UserName = userName;
		Passwort = passwort;
		Rolle = rolle;
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
	public int getRolle() {
		return Rolle;
	}
	public void setRolle(int rolle) {
		Rolle = rolle;
	}
	
}

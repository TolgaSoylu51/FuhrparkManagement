package Code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginMaske extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final JLabel UserName = null;
	protected static final JLabel Passwort = null;
	private JTextField tfUserName;
	private JTextField tfPasswort;
	private JLabel lblPasswort;

	static Connection conn = null;
	static ResultSet rs = null;
	static PreparedStatement pst = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginMaske frame = new LoginMaske();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return 
	 */

	public static void establishConnection(String query) {
		String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
		try {
			System.out.println("connection was established");
			conn = DriverManager.getConnection(url);
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
		}
		catch(Exception err) {
			JOptionPane.showMessageDialog(null,	err);
		}
	}
	
	public LoginMaske() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 240);
		getContentPane().setLayout(null);

		tfUserName = new JTextField();
		tfUserName.setBounds(230, 37, 168, 20);
		getContentPane().add(tfUserName);
		tfUserName.setColumns(10);

		tfPasswort = new JTextField();
		tfPasswort.setBounds(230, 80, 168, 20);
		getContentPane().add(tfPasswort);
		tfPasswort.setColumns(10);

		JLabel lblUserName = new JLabel("Benutzername");
		lblUserName.setBounds(47, 34, 173, 27);
		getContentPane().add(lblUserName);

		lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(47, 80, 173, 20);
		getContentPane().add(lblPasswort);

		JButton btnAnmelden = new JButton("Anmelden");
		btnAnmelden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
				String sql = "Select * from Login where UserName=? and Passwort=?";
				conn = DriverManager.getConnection("jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;","KonzCars","KonzCars");
                pst = conn.prepareStatement(sql);
                pst.setString(1, tfUserName.getText());
                pst.setString(2, tfPasswort.getText());
                rs = pst.executeQuery();
                if (rs.next()) {
                    Hauptmenue s = new Hauptmenue();
                    s.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Username und Passwort stimmen nicht überein");
                }
			}
           catch (Exception e1) {
                JOptionPane.showMessageDialog(null,    e1);
            }
			}
		});
		btnAnmelden.setBounds(47, 145, 123, 25);

		getContentPane().add(btnAnmelden);
	}
}

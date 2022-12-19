package Code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.*;
import java.util.*;
import java.sql.*;

public class LoginMaske extends JFrame { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final JLabel UserName = null;
	protected static final JLabel Passwort = null;
	private JTextField tfUserName;
	private JLabel lblPasswort;

	static Connection conn = null;
	static ResultSet rs = null;
	static PreparedStatement pst = null;
	private JPasswordField tfPasswort;
	private JLabel lblBackground;
	public Login login;
	public static String LE_Sichtbarkeit_Uebergabe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginMaske frame = new LoginMaske();
					frame.setResizable(false);
					ImageIcon icon = new ImageIcon(
							"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
					frame.setIconImage(icon.getImage());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public LoginMaske() {
		getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		setTitle("KFM Login");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(360, 260);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		tfUserName = new JTextField();
		tfUserName.setBounds(120, 50, 187, 20);
		getContentPane().add(tfUserName);
		tfUserName.setColumns(10);

		JLabel lblUserName = new JLabel("Benutzername");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUserName.setBounds(23, 53, 101, 13);
		getContentPane().add(lblUserName);

		lblPasswort = new JLabel("Passwort");
		lblPasswort.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPasswort.setBounds(23, 78, 101, 13);
		getContentPane().add(lblPasswort);

		tfPasswort = new JPasswordField();
		tfPasswort.setBounds(120, 75, 187, 20);
		getContentPane().add(tfPasswort);

		JButton btnAnmelden = new JButton("Anmelden");
		btnAnmelden.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAnmelden.setBackground(SystemColor.inactiveCaption);

		btnAnmelden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		getRootPane().setDefaultButton(btnAnmelden);
		btnAnmelden.setBounds(31, 160, 281, 40);
		getContentPane().add(btnAnmelden);

		lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground.setBounds(0, 0, 344, 221);
		getContentPane().add(lblBackground);
	}

	@SuppressWarnings("deprecation")
	public void login() {
		try {
			String sql = "Select * from Login where UserName=? and Passwort=?";
//			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			pst = conn.prepareStatement(sql);
			pst.setString(1, tfUserName.getText());
			pst.setString(2, tfPasswort.getText());

			rs = pst.executeQuery();

			if (rs.next()) {
				if (rs.getInt("PWAendern") == 1) {
					JFrame frame = new JFrame("InputDialog");
					String s = JOptionPane.showInputDialog(frame, "Neues Passwort eingeben", "Passwort festlegen",
							JOptionPane.WARNING_MESSAGE);
					// if the user presses Cancel, this will be null
					if (s == null) {
						System.exit(0);
					} else {
						int id = rs.getInt("ID");
						String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
						conn = DriverManager.getConnection(url);
						String query = "UPDATE Login SET Passwort=?, PWAendern=? WHERE ID="
								+ id;
						
						PreparedStatement pst = conn.prepareStatement(query);

						pst.setString(1, s);
						pst.setInt(2,0);
						
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Das neue Passwort wurde gespeichert!");
					}

				}
				LE_Sichtbarkeit_Uebergabe = rs.getString("LE");
				Hauptmenue s = new Hauptmenue();
				s.setResizable(false);
				ImageIcon icon = new ImageIcon(
						"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
				s.setIconImage(icon.getImage());
				s.setVisible(true);
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Der Benutzername und das Passwort stimmen nicht überein!");
			}
		} catch (

		Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
	}
}
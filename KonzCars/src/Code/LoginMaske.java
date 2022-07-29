package Code;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginMaske extends JFrame {
	protected static final JLabel UserName = null;
	protected static final JLabel Passwort = null;
	private JTextField tfUserName;
	private JTextField tfPasswort;
	private JLabel lblPasswort;

	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

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
	 */
	public LoginMaske() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 215);
		getContentPane().setLayout(null);

		tfUserName = new JTextField();
		tfUserName.setBounds(171, 38, 168, 20);
		getContentPane().add(tfUserName);
		tfUserName.setColumns(10);

		tfPasswort = new JTextField();
		tfPasswort.setBounds(171, 81, 168, 20);
		getContentPane().add(tfPasswort);
		tfPasswort.setColumns(10);

		JLabel lblUserName = new JLabel("Benutzername");
		lblUserName.setBounds(67, 35, 70, 27);
		getContentPane().add(lblUserName);

		lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(67, 84, 46, 14);
		getContentPane().add(lblPasswort);

		JButton btnAnmelden = new JButton("Anmelden");
		btnAnmelden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "Select * from Login where UserName=? and Passwort=?";
				try {
					con = DriverManager.getConnection("jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;","KonzCars","KonzCars");
					pst = con.prepareStatement(sql);
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
					JOptionPane.showMessageDialog(null,	e1);
				}
			}
		});
		btnAnmelden.setBounds(171, 127, 89, 23);

		getContentPane().add(btnAnmelden);
	}
}

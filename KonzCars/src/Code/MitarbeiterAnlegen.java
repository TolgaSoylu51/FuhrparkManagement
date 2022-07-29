package Code;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.SwingConstants;

public class MitarbeiterAnlegen extends JFrame {

	private JPanel contentPane;
	private JTextField tfPersonalnummer;
	private JTextField tfVorname;
	private JTextField tfNachname;
	private JTextField tfAktivKz;
	private JTextField tfFirmaNr;
	private JTextField tfNlNr;
	private JTextField tfKommentar1;
	private JTextField tfKommentar2;

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
					MitarbeiterAnlegen frame = new MitarbeiterAnlegen();
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
	public MitarbeiterAnlegen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 944, 576);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSave = new JButton("Anlegen");

		btnSave.setBounds(72, 466, 122, 23);
		contentPane.add(btnSave);

		tfPersonalnummer = new JTextField();
		tfPersonalnummer.setBounds(233, 69, 169, 20);
		contentPane.add(tfPersonalnummer);
		tfPersonalnummer.setColumns(10);

		tfVorname = new JTextField();
		tfVorname.setColumns(10);
		tfVorname.setBounds(233, 100, 169, 20);
		contentPane.add(tfVorname);

		tfNachname = new JTextField();
		tfNachname.setColumns(10);
		tfNachname.setBounds(233, 131, 169, 20);
		contentPane.add(tfNachname);

		tfAktivKz = new JTextField();
		tfAktivKz.setColumns(10);
		tfAktivKz.setBounds(233, 162, 169, 20);
		contentPane.add(tfAktivKz);

		tfFirmaNr = new JTextField();
		tfFirmaNr.setColumns(10);
		tfFirmaNr.setBounds(233, 193, 169, 20);
		contentPane.add(tfFirmaNr);

		tfNlNr = new JTextField();
		tfNlNr.setColumns(10);
		tfNlNr.setBounds(233, 224, 169, 20);
		contentPane.add(tfNlNr);

		JLabel lblPersonalnummer = new JLabel("Personalnummer");
		lblPersonalnummer.setBounds(72, 72, 110, 14);
		contentPane.add(lblPersonalnummer);

		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setBounds(72, 103, 89, 14);
		contentPane.add(lblVorname);

		JLabel lblNachname = new JLabel("Nachname");
		lblNachname.setBounds(72, 135, 89, 14);
		contentPane.add(lblNachname);

		JLabel lblAktivKZ = new JLabel("Aktiv KZ");
		lblAktivKZ.setBounds(72, 165, 89, 14);
		contentPane.add(lblAktivKZ);

		JLabel lblFirmaNr = new JLabel("FirmaNr");
		lblFirmaNr.setBounds(72, 196, 89, 14);
		contentPane.add(lblFirmaNr);

		JLabel lblNLNr = new JLabel("Niederlassungsnummer");
		lblNLNr.setBounds(72, 227, 155, 14);
		contentPane.add(lblNLNr);

		JCheckBox chckbxPruefung1 = new JCheckBox("erste Prüfung");
		chckbxPruefung1.setBounds(72, 321, 174, 23);
		contentPane.add(chckbxPruefung1);

		JCheckBox chckbxPruefung2 = new JCheckBox("zweite Prüfung");
		chckbxPruefung2.setBounds(72, 381, 174, 23);
		contentPane.add(chckbxPruefung2);

		tfKommentar1 = new JTextField();
		tfKommentar1.setBounds(72, 351, 314, 23);
		contentPane.add(tfKommentar1);
		tfKommentar1.setColumns(10);

		tfKommentar2 = new JTextField();
		tfKommentar2.setBounds(72, 411, 314, 23);
		contentPane.add(tfKommentar2);
		tfKommentar2.setColumns(10);

		JCheckBox chckbxFahrerlaubnis = new JCheckBox("Fahrerlaubnis");
		chckbxFahrerlaubnis.setBounds(72, 272, 174, 23);
		contentPane.add(chckbxFahrerlaubnis);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(440, 0, 2, 537);
		contentPane.add(separator);

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
					con = DriverManager.getConnection(
							"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
							"KonzCars", "KonzCars");
					String query = "insert into MitarbeiterTest (ID, Personalnummer,AktivKZ,Name,Vorname,FirmaNr,NL_Nr,Fahrerlaubnis,Erstprüfung,Prüfungszeitpunkt1,Kommentar1,Zweitprüfung,Prüfungszeitpunkt2,Kommentar2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, "");
					pst.setString(2, tfPersonalnummer.getText());
					pst.setString(3, tfAktivKz.getText());
					pst.setString(4, tfNachname.getText());
					pst.setString(5, tfVorname.getText());
					pst.setString(6, tfFirmaNr.getText());
					pst.setString(7, tfNlNr.getText());

					boolean fahrerlaubnis = false;
					if (chckbxFahrerlaubnis.isSelected()) {
						fahrerlaubnis = true;
					}
					if (fahrerlaubnis) {
						pst.setString(8, "1");
					} else {
						pst.setString(8, "0");
					}

					boolean pruefung1 = false;
					if (chckbxPruefung1.isSelected()) {
						pruefung1 = true;
					}	
					if (pruefung1) {	
						pst.setString(9, "1");;
					} else {
						pst.setString(9, "0");
					}
					
					pst.setString(10, "");
					pst.setString(11, tfKommentar1.getText());

					boolean pruefung2 = false;
					if (chckbxPruefung2.isSelected()) {
						pruefung2 = true;
					}	
					if (pruefung2) {	
						pst.setString(12, "1");;
					} else {
						pst.setString(12, "0");
					}
				
					pst.setString(13, "");
					pst.setString(14, tfKommentar2.getText());

					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
				}

				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		
		JButton btnReset = new JButton("Zurücksetzen");
		btnReset.setBounds(265, 466, 122, 23);
		contentPane.add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfPersonalnummer.setText("");
				tfVorname.setText("");
				tfNachname.setText("");
				tfAktivKz.setText("");
				tfFirmaNr.setText("");
				tfNlNr.setText("");
				tfKommentar1.setText("");
				tfKommentar2.setText("");
				chckbxFahrerlaubnis .setSelected(false);
				chckbxPruefung1 .setSelected(false);
				chckbxPruefung2 .setSelected(false);
				tfPersonalnummer.setText("");
				tfPersonalnummer.setText("");
			}
		});
	}
}

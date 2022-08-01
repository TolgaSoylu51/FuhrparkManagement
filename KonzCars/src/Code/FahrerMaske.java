package Code;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;

public class FahrerMaske extends JFrame {

	private JPanel contentPane;
	private JTextField tfPersonalnummer;
	private JTextField tfVorname;
	private JTextField tfNachname;
	private JTextField tfAktivKz;
	private JTextField tfFirmaNr;
	private JTextField tfNlNr;
	private JTextField tfKommentar1;
	private JTextField tfKommentar2;

	static Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private static JTable tableFahrer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FahrerMaske frame = new FahrerMaske();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		show_fahrer();
	}

	/**
	 * Create the frame.
	 */
	public FahrerMaske() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1286, 767);
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

		JCheckBox chckbxPruefung1 = new JCheckBox("erste Pr�fung");
		chckbxPruefung1.setBounds(72, 321, 174, 23);
		contentPane.add(chckbxPruefung1);

		JCheckBox chckbxPruefung2 = new JCheckBox("zweite Pr�fung");
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
					String query = "insert into MitarbeiterTest (ID, Personalnummer,AktivKZ,Name,Vorname,FirmaNr,NL_Nr,Fahrerlaubnis,Erstpr�fung,Pr�fungszeitpunkt1,Kommentar1,Zweitpr�fung,Pr�fungszeitpunkt2,Kommentar2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
						pst.setString(9, "1");
						;
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
						pst.setString(12, "1");
						;
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

		JButton btnReset = new JButton("Zur�cksetzen");
		btnReset.setBounds(265, 466, 122, 23);
		contentPane.add(btnReset);

		JButton btnZurück = new JButton("");
		btnZurück.setBackground(Color.WHITE);
		btnZurück.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hauptmenue frame = new Hauptmenue();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnZurück.setIcon(
				new ImageIcon("C:\\Users\\Tolga.Soylu\\OneDrive - KHW Konzmann GmbH\\Desktop\\back-icon (1).png"));
		btnZurück.setBounds(0, 0, 38, 23);
		contentPane.add(btnZurück);

		tableFahrer = new JTable();
		tableFahrer.setColumnSelectionAllowed(true);
		tableFahrer.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		tableFahrer.setBounds(448, 49, 822, 395);
		contentPane.add(tableFahrer);

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
				chckbxFahrerlaubnis.setSelected(false);
				chckbxPruefung1.setSelected(false);
				chckbxPruefung2.setSelected(false);
				tfPersonalnummer.setText("");
				tfPersonalnummer.setText("");
			}
		});
	}

	public static ArrayList<Fahrer> fahrer() {
		ArrayList<Fahrer> fahrerliste = new ArrayList<>();

		try {
			con = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1 = "Select * from MitarbeiterTest";
			java.sql.Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);
			Fahrer fahrer;

			while (rs.next()) {
				fahrer = new Fahrer(rs.getInt("ID"), rs.getInt("Personalnummer"), rs.getString("AktivKZ"),
						rs.getString("Name"), rs.getString("Vorname"), rs.getString("FirmaNr"), rs.getString("NL_Nr"),
						rs.getString("Fahrerlaubnis"), rs.getString("Erstpr�fung"), rs.getString("Pr�fungszeitpunkt1"),
						rs.getString("Kommentar1"), rs.getString("Zweitpr�fung"), rs.getString("Pr�fungszeitpunkt2"),
						rs.getString("Kommentar2"));
				fahrerliste.add(fahrer);
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

		return fahrerliste;
	}
	
	public static void show_fahrer() {
		ArrayList<Fahrer> fahrer = fahrer();
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		Object[] row = new Object[14];
		for (int i = 0; i < fahrer.size(); i++) {
			row[0]=fahrer.get(i).getID();
			row[1]=fahrer.get(i).getPersonalnummer();
			row[2]=fahrer.get(i).getAktivKZ();
			row[3]=fahrer.get(i).getName();
			row[4]=fahrer.get(i).getVorname();
			row[5]=fahrer.get(i).getFirmaNr();
			row[6]=fahrer.get(i).getNL_Nr();
			row[7]=fahrer.get(i).getFahrerlaubnis();
			row[8]=fahrer.get(i).getErstprüfung();
			row[9]=fahrer.get(i).getPrüfungszeitpunkt1();
			row[10]=fahrer.get(i).getKommentar1();
			row[11]=fahrer.get(i).getZweitprüfung();
			row[12]=fahrer.get(i).getPrüfungszeitpunkt2();
			row[13]=fahrer.get(i).getKommentar2();
			model.addRow(row);
		}
	}
}

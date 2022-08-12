package Code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.ListSelectionModel;

public class FahrerAnlegenMaske extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
					FahrerAnlegenMaske frame = new FahrerAnlegenMaske();
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
	public FahrerAnlegenMaske() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1710, 681);
		contentPane = new JPanel();
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSave = new JButton("Anlegen");

		btnSave.setBounds(20, 466, 180, 22);
		contentPane.add(btnSave);
		
		tfPersonalnummer = new JTextField();
		tfPersonalnummer.setBounds(181, 69, 209, 20);
		contentPane.add(tfPersonalnummer);
		tfPersonalnummer.setColumns(10);

		tfVorname = new JTextField();
		tfVorname.setColumns(10);
		tfVorname.setBounds(181, 100, 209, 20);
		contentPane.add(tfVorname);

		tfNachname = new JTextField();
		tfNachname.setColumns(10);
		tfNachname.setBounds(181, 131, 209, 20);
		contentPane.add(tfNachname);

		tfAktivKz = new JTextField();
		tfAktivKz.setColumns(10);
		tfAktivKz.setBounds(181, 162, 209, 20);
		contentPane.add(tfAktivKz);

		tfFirmaNr = new JTextField();
		tfFirmaNr.setColumns(10);
		tfFirmaNr.setBounds(181, 193, 209, 20);
		contentPane.add(tfFirmaNr);

		tfNlNr = new JTextField();
		tfNlNr.setColumns(10);
		tfNlNr.setBounds(181, 224, 209, 20);
		contentPane.add(tfNlNr);

		JLabel lblPersonalnummer = new JLabel("Personalnummer");
		lblPersonalnummer.setBounds(20, 72, 422151, 14);
		contentPane.add(lblPersonalnummer);

		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setBounds(20, 103, 151, 14);
		contentPane.add(lblVorname);

		JLabel lblNachname = new JLabel("Nachname");
		lblNachname.setBounds(20, 135, 151, 14);
		contentPane.add(lblNachname);

		JLabel lblAktivKZ = new JLabel("Aktiv KZ");
		lblAktivKZ.setBounds(20, 165, 151, 14);
		contentPane.add(lblAktivKZ);

		JLabel lblFirmaNr = new JLabel("FirmaNr");
		lblFirmaNr.setBounds(20, 196, 151, 14);
		contentPane.add(lblFirmaNr);

		JLabel lblNLNr = new JLabel("Niederlassungsnummer");
		lblNLNr.setBounds(20, 227, 151, 20);
		contentPane.add(lblNLNr);

		JCheckBox chckbxPruefung1 = new JCheckBox("erste Prüfung");
		chckbxPruefung1.setBounds(20, 324, 20, 20);
		contentPane.add(chckbxPruefung1);

		JCheckBox chckbxPruefung2 = new JCheckBox("zweite Prüfung");
		chckbxPruefung2.setBounds(20, 384, 20, 20);
		contentPane.add(chckbxPruefung2);

		tfKommentar1 = new JTextField();
		tfKommentar1.setBounds(20, 351, 370, 23);
		contentPane.add(tfKommentar1);
		tfKommentar1.setColumns(10);

		tfKommentar2 = new JTextField();
		tfKommentar2.setBounds(20, 411, 370, 23);
		contentPane.add(tfKommentar2);
		tfKommentar2.setColumns(10);

		JCheckBox chckbxFahrerlaubnis = new JCheckBox("Fahrerlaubnis");
		chckbxFahrerlaubnis.setBounds(20, 280, 20, 20);
		contentPane.add(chckbxFahrerlaubnis);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.GRAY);
		separator.setBackground(Color.WHITE);
		separator.setBounds(420, 20, 2, 537);
		contentPane.add(separator);

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
					con = DriverManager.getConnection(url);
					String query = "insert into MitarbeiterTest (Personalnummer,AktivKZ,Name,Vorname,FirmaNr,NL_Nr,Fahrerlaubnis,Erstprüfung,Prüfungszeitpunkt1,Kommentar1,Zweitprüfung,Prüfungszeitpunkt2,Kommentar2) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement pst = con.prepareStatement(query);
					//pst.setString(1, "");
					pst.setString(1, tfPersonalnummer.getText());
					pst.setString(2, tfAktivKz.getText());
					pst.setString(3, tfNachname.getText());
					pst.setString(4, tfVorname.getText());
					pst.setString(5, tfFirmaNr.getText());
					pst.setString(6, tfNlNr.getText());

					boolean fahrerlaubnis = false;
					if (chckbxFahrerlaubnis.isSelected()) {
						fahrerlaubnis = true;
					}
					if (fahrerlaubnis) {
						pst.setString(7, "1");
					} else {
						pst.setString(7, "0");
					}

					boolean pruefung1 = false;
					if (chckbxPruefung1.isSelected()) {
						pruefung1 = true;
					}
					if (pruefung1) {
						pst.setString(8, "1");
						;
					} else {
						pst.setString(8, "0");
					}

					pst.setString(9, "");
					pst.setString(10, tfKommentar1.getText());

					boolean pruefung2 = false;
					if (chckbxPruefung2.isSelected()) {
						pruefung2 = true;
					}
					if (pruefung2) {
						pst.setString(11, "1");
						;
					} else {
						pst.setString(11, "0");
					}

					pst.setString(12, "");
					pst.setString(13, tfKommentar2.getText());

					pst.executeUpdate();

					show_hinzugefuegten_fahrer();

					JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
				}

				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}

			}
		});

		JButton btnReset = new JButton("Zurücksetzen");
		btnReset.setBounds(210, 466, 180, 23);
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
		btnZurück.setIcon(new ImageIcon("C:\\Users\\Tolga.Soylu\\OneDrive - KHW Konzmann GmbH\\Desktop\\back-icon (1).png"));
		btnZurück.setBounds(0, 0, 40, 20);
		contentPane.add(btnZurück);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(450, 30, 1220, 510);
		contentPane.add(scrollPane);
//		ab hier
		tableFahrer = new JTable();

		tableFahrer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableFahrer);
		tableFahrer.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Personalnummer", "AktivKZ", "Name", "Vorname", "FirmaNr", "NL_Nr",
						"Fahrerlaubnis", "Erstpr\u00FCfung", "Pr\u00FCfungszeitpunkt1", "Kommentar1",
						"Zweitpr\u00FCfung", "Pr\u00FCfungszeitpunkt2", "Kommentar2" }));

		JLabel lblFahrerlaubnis = new JLabel("Fahrerlaubnis");
		lblFahrerlaubnis.setBounds(60, 284, 151, 14);
		contentPane.add(lblFahrerlaubnis);

		JLabel lblErstePrfung = new JLabel("erste Prüfung");
		lblErstePrfung.setBounds(60, 328, 151, 14);
		contentPane.add(lblErstePrfung);

		JLabel lblZweitePrfung = new JLabel("zweite Prüfung");
		lblZweitePrfung.setBounds(60, 388, 151, 14);
		contentPane.add(lblZweitePrfung);

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
		
		show_fahrer();
	}

	public static ArrayList<Fahrer> fahrer() {
		ArrayList<Fahrer> fahrerliste = new ArrayList<>();

		try {
			con = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1 = "Select * from MitarbeiterTest";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);
			Fahrer fahrer;
			while (rs.next()) {
				fahrer = new Fahrer(rs.getInt("ID"), rs.getInt("Personalnummer"), rs.getString("AktivKZ"),
						rs.getString("Name"), rs.getString("Vorname"), rs.getString("FirmaNr"), rs.getString("NL_Nr"),
						rs.getString("Fahrerlaubnis"), rs.getString("Erstprüfung"), rs.getString("Prüfungszeitpunkt1"),
						rs.getString("Kommentar1"), rs.getString("Zweitprüfung"), rs.getString("Prüfungszeitpunkt2"),
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
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		ArrayList<Fahrer> fahrer = fahrer();
		Object[] row = new Object[14];
		for (int i = 0; i < fahrer.size(); i++) {
			row[0] = fahrer.get(i).getID();
			row[1] = fahrer.get(i).getPersonalnummer();
			row[2] = fahrer.get(i).getAktivKZ();
			row[3] = fahrer.get(i).getName();
			row[4] = fahrer.get(i).getVorname();
			row[5] = fahrer.get(i).getFirmaNr();
			row[6] = fahrer.get(i).getNL_Nr();
			row[7] = fahrer.get(i).getFahrerlaubnis();
			row[8] = fahrer.get(i).getErstprüfung();
			row[9] = fahrer.get(i).getPrüfungszeitpunkt1();
			row[10] = fahrer.get(i).getKommentar1();
			row[11] = fahrer.get(i).getZweitprüfung();
			row[12] = fahrer.get(i).getPrüfungszeitpunkt2();
			row[13] = fahrer.get(i).getKommentar2();
			model.addRow(row);
		}
	}

	public static void show_hinzugefuegten_fahrer() {
		try {
			DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
			model.setRowCount(0);
			show_fahrer();
		} catch (IndexOutOfBoundsException e) {
			//JOptionPane.showMessageDialog(null, e);
		}
		;
	}
}

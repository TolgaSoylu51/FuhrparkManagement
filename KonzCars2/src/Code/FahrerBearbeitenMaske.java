package Code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.RowFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.*;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.UIManager;

public class FahrerBearbeitenMaske extends JFrame {

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
	ArrayList<Fahrer> vergleichsliste = new ArrayList<>();
	private static JTable tableFahrer;
	private JTextField tfSuche;
	private String id = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FahrerBearbeitenMaske frame = new FahrerBearbeitenMaske();
					frame.setResizable(false);
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
	public FahrerBearbeitenMaske() {
		setTitle("KFM Fahrer Bearbeiten");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1278, 674);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfSuche = new JTextField();
		tfSuche.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				filter(tfSuche.getText());
			}
		});
		tfSuche.setBackground(UIManager.getColor("CheckBox.background"));
		tfSuche.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfSuche.setBounds(10, 26, 964, 19);
		contentPane.add(tfSuche);
		tfSuche.setColumns(10);

		JButton btnClear = new JButton("X");
		btnClear.setFont(new Font("Arial", Font.PLAIN, 10));
		btnClear.setFocusPainted(false);
		btnClear.setBackground(SystemColor.inactiveCaption);
		btnClear.setBounds(974, 26, 19, 18);
		btnClear.setMargin(new Insets(0, 0, 0, 0));
		contentPane.add(btnClear);

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				tfSuche.setText("");
				filter(tfSuche.getText());
			}
		});

		JLabel lblZweitePrfung = new JLabel("Zweite Prüfung");
		lblZweitePrfung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblZweitePrfung.setBounds(50, 397, 75, 14);
		contentPane.add(lblZweitePrfung);

		JLabel lblFahrerlaubnis = new JLabel("Fahrerlaubnis");
		lblFahrerlaubnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFahrerlaubnis.setBounds(50, 284, 75, 14);
		contentPane.add(lblFahrerlaubnis);

		JLabel lblErstePrfung = new JLabel("Erste Prüfung");
		lblErstePrfung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErstePrfung.setBounds(50, 328, 75, 14);
		contentPane.add(lblErstePrfung);

		JButton btnSave = new JButton("Speichern");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setFocusPainted(false);
		btnSave.setBackground(SystemColor.inactiveCaption);

		btnSave.setBounds(10, 605, 180, 23);
		contentPane.add(btnSave);

		tfPersonalnummer = new JTextField();
		tfPersonalnummer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfPersonalnummer.setBounds(137, 69, 243, 20);
		contentPane.add(tfPersonalnummer);
		tfPersonalnummer.setColumns(10);

		tfVorname = new JTextField();
		tfVorname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfVorname.setColumns(10);
		tfVorname.setBounds(137, 100, 243, 20);
		contentPane.add(tfVorname);

		tfNachname = new JTextField();
		tfNachname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfNachname.setColumns(10);
		tfNachname.setBounds(137, 131, 243, 20);
		contentPane.add(tfNachname);

		tfAktivKz = new JTextField();
		tfAktivKz.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfAktivKz.setColumns(10);
		tfAktivKz.setBounds(137, 162, 243, 20);
		contentPane.add(tfAktivKz);

		tfFirmaNr = new JTextField();
		tfFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFirmaNr.setColumns(10);
		tfFirmaNr.setBounds(137, 193, 243, 20);
		contentPane.add(tfFirmaNr);

		tfNlNr = new JTextField();
		tfNlNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfNlNr.setColumns(10);
		tfNlNr.setBounds(137, 224, 243, 20);
		contentPane.add(tfNlNr);

		JLabel lblPersonalnummer = new JLabel("Personalnummer");
		lblPersonalnummer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPersonalnummer.setBounds(10, 69, 100, 14);
		contentPane.add(lblPersonalnummer);

		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVorname.setBounds(10, 100, 115, 14);
		contentPane.add(lblVorname);

		JLabel lblNachname = new JLabel("Nachname");
		lblNachname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNachname.setBounds(10, 132, 115, 14);
		contentPane.add(lblNachname);

		JLabel lblAktivKZ = new JLabel("Aktiv KZ");
		lblAktivKZ.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAktivKZ.setBounds(10, 162, 115, 14);
		contentPane.add(lblAktivKZ);

		JLabel lblFirmaNr = new JLabel("FirmaNr");
		lblFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFirmaNr.setBounds(10, 193, 115, 14);
		contentPane.add(lblFirmaNr);

		JLabel lblNLNr = new JLabel("Niederlassungsnummer");
		lblNLNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNLNr.setBounds(10, 224, 115, 14);
		contentPane.add(lblNLNr);

		JCheckBox chckbxPruefung1 = new JCheckBox("erste Prüfung");
		chckbxPruefung1.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxPruefung1.setOpaque(false);
		chckbxPruefung1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxPruefung1.setBounds(10, 324, 20, 20);
		contentPane.add(chckbxPruefung1);

		JCheckBox chckbxPruefung2 = new JCheckBox("zweite Prüfung");
		chckbxPruefung2.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxPruefung2.setOpaque(false);
		chckbxPruefung2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxPruefung2.setBounds(10, 393, 20, 20);
		contentPane.add(chckbxPruefung2);

		tfKommentar1 = new JTextField();
		tfKommentar1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfKommentar1.setBounds(10, 351, 370, 23);
		contentPane.add(tfKommentar1);
		tfKommentar1.setColumns(10);

		tfKommentar2 = new JTextField();
		tfKommentar2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfKommentar2.setBounds(10, 420, 370, 23);
		contentPane.add(tfKommentar2);
		tfKommentar2.setColumns(10);

		JCheckBox chckbxFahrerlaubnis = new JCheckBox("Fahrerlaubnis");
		chckbxFahrerlaubnis.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxFahrerlaubnis.setOpaque(false);
		chckbxFahrerlaubnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxFahrerlaubnis.setBounds(10, 280, 20, 20);
		contentPane.add(chckbxFahrerlaubnis);

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow());
				int aktualisiert;
				aktualisiert = vergleichsliste.get(i).getBearbeitet();
				ArrayList<Fahrer> fahrerliste = fahrer();
				if (aktualisiert == fahrerliste.get(i).getBearbeitet()) {
					try {
						emptyTf(tfPersonalnummer);
						emptyTf(tfNachname);
						emptyTf(tfAktivKz);
						emptyTf(tfFirmaNr);
						emptyTf(tfNlNr);

						String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
						con = DriverManager.getConnection(url);
						String query = "UPDATE MitarbeiterTest SET Personalnummer=?,AktivKZ=?,Name=?,Vorname=?,FirmaNr=?,NL_Nr=?,Fahrerlaubnis=?,Erstprüfung=?,Prüfungszeitpunkt1=?,Kommentar1=?,Zweitprüfung=?,Prüfungszeitpunkt2=?,Kommentar2=?, Bearbeitet=? WHERE ID="
								+ id;
						PreparedStatement pst = con.prepareStatement(query);
						pst.setString(1, tfPersonalnummer.getText());
						pst.setString(2, "2");
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

						pst.setString(13, tfKommentar2.getText());

						try {
							fahrerliste = fahrer();
							tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow());
							pst.setInt(14, fahrerliste.get(i).getBearbeitet() + 1); 
						} catch (IndexOutOfBoundsException e1) {

						}

						pst.executeUpdate();

						show_aktualisierte_fahrerliste();

						// JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
					}
					catch (IndexOutOfBoundsException e2) {
						//JOptionPane.showMessageDialog(null, e);
					}

					catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Ihre Änderungen wurden nicht gespeichert, bitte überarbeiten Sie die Daten gegebenfalls noch einmal.");
				}
				
				vergleichsliste = fahrer();
				show_aktualisierte_fahrerliste();
				//tableFahrer.setEditingRow(i);
			}
		});

		JButton btnZurück = new JButton("");
		btnZurück.setFocusable(false);
		btnZurück.setBackground(Color.WHITE);
		btnZurück.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnZurück.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\icons\\pfeil-zurück.png"));
		btnZurück.setBounds(10, 2, 28, 23);
		contentPane.add(btnZurück);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(390, 50, 862, 574);
		contentPane.add(scrollPane);

		tableFahrer = new JTable();

		scrollPane.setViewportView(tableFahrer);
		tableFahrer.setBorder(null);
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Personalnummer", "AktivKZ", "Name", "Vorname", "FirmaNr", "NL_Nr",
						"Fahrerlaubnis", "Erstpruefung", "Pruefungszeitpunkt1", "Kommentar1", "Zweitpruefung",
						"Pruefungszeitpunkt2", "Kommentar2" }));

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground.setBounds(0, 0, 1262, 647);
		contentPane.add(lblBackground);

		JLabel lblBackground_1 = new JLabel("");
		lblBackground_1.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground_1.setBounds(1260, 0, 433, 651);
		contentPane.add(lblBackground_1);

		wichtigTf(tfPersonalnummer);
		wichtigTf(tfNachname);
		wichtigTf(tfAktivKz);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNlNr);

		tableFahrer.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int i = tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow());
				TableModel model = tableFahrer.getModel();
				id = model.getValueAt(i, 0).toString();
				tfPersonalnummer.setText(model.getValueAt(i, 1).toString());
				tfVorname.setText(model.getValueAt(i, 4).toString());
				tfFirmaNr.setText(model.getValueAt(i, 5).toString());
				tfAktivKz.setText(model.getValueAt(i, 2).toString());
				tfNlNr.setText(model.getValueAt(i, 6).toString());
				tfNachname.setText(model.getValueAt(i, 3).toString());
				tfKommentar1.setText(model.getValueAt(i, 10).toString());
				tfKommentar2.setText(model.getValueAt(i, 13).toString());
				String fahrerlaubnis = model.getValueAt(i, 7).toString();
				switch (fahrerlaubnis) {
				case "1":
					chckbxFahrerlaubnis.setSelected(true);
					break;
				case "0":
					chckbxFahrerlaubnis.setSelected(false);
					break;
				}
				String erstpruefung = model.getValueAt(i, 8).toString();
				switch (erstpruefung) {
				case "1":
					chckbxPruefung1.setSelected(true);
					break;
				case "0":
					chckbxPruefung1.setSelected(false);
					break;
				}
				String zweitpruefung = model.getValueAt(i, 11).toString();
				switch (zweitpruefung) {
				case "1":
					chckbxPruefung2.setSelected(true);
					break;
				case "0":
					chckbxPruefung2.setSelected(false);
					break;
				}

			}
		});
		vergleichsliste = fahrer();

		show_fahrer();
	}

	public static ArrayList<Fahrer> fahrer() {
		ArrayList<Fahrer> fahrerliste = new ArrayList<>();

		try {
			con = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1 = "Select * from MitarbeiterTest where AktivKZ <> 4";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);
			Fahrer fahrer;
			while (rs.next()) {
				fahrer = new Fahrer(rs.getInt("ID"), rs.getInt("Personalnummer"), rs.getInt("AktivKZ"),
						rs.getString("Name"), rs.getString("Vorname"), rs.getString("FirmaNr"), rs.getInt("NL_Nr"),
						rs.getString("Fahrerlaubnis"), rs.getString("Erstprüfung"), rs.getString("Prüfungszeitpunkt1"),
						rs.getString("Kommentar1"), rs.getString("Zweitprüfung"), rs.getString("Prüfungszeitpunkt2"),
						rs.getString("Kommentar2"), rs.getInt("Bearbeitet"));
				fahrerliste.add(fahrer);
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

		return fahrerliste;
	}

	public void filter(String str) {
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		TableRowSorter<DefaultTableModel> rowFilter = new TableRowSorter<DefaultTableModel>(model);
		tableFahrer.setRowSorter(rowFilter);

		rowFilter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
	}

	public static void show_fahrer() {
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		ArrayList<Fahrer> fahrer = fahrer();
		Object[] row = new Object[15];
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
			row[14] = fahrer.get(i).getBearbeitet();
			model.addRow(row);
		}
	}

	public static void show_aktualisierte_fahrerliste() {
		try {
			DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
			model.setRowCount(0);
			show_fahrer();
		} catch (IndexOutOfBoundsException e) {
			// JOptionPane.showMessageDialog(null, e);
		}
		;
	}

	public void wichtigTf(JTextField tf) {
		tf.setText("!");
		tf.setForeground(Color.red);
		tf.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evtfg) {
				if (tf.getText().equals("!")) {
					tf.setText("");
					tf.setForeground(Color.black);
				}
			}

			public void focusLost(FocusEvent evtfl) {
				if (tf.getText().equals("")) {
					tf.setText("!");
					tf.setForeground(Color.red);
				}
			}
		});
	}

	public void emptyTf(JTextField tf) throws Exception {
		if (tf.getText().equals("!")) {
			throw new Exception("Füllen sie bitte alle Felder aus!");
		}
	}
}
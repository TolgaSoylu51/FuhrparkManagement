package Code;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.SystemColor;

public class FahrerDatenMaske extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	static Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	ArrayList<Fahrer> vergleichsliste = new ArrayList<>();
	private static JTable tableFahrer;
	
	private String id = null;
	
	private JTextField tfPersonalnummer;
	private JTextField tfVorname;
	private JTextField tfNachname;
	private JTextField tfAktivKz;
	private JTextField tfFirmaNr;
	private JTextField tfNlNr;
	private JTextField tfKommentar1;
	private JTextField tfKommentar2;
	private String modus;
	private JCheckBox chckbxFahrerlaubnis;
	private JCheckBox chckbxPruefung1;
	private JCheckBox chckbxPruefung2;
	private JTextField tfSuche;
	public static String id_Uebergabe;
	// static 
	public static String LE_Sichtbarkeit;
	public static String id_Uebergabe_fahrzeug;
	public static String id_Uebergabe_fahrer;
	public static boolean herkunft_ueber_fahrer;
	static LoginMaske loginMaske;
	public JButton btn_Abbrechen;
	public JButton btn_Save;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FahrerDatenMaske frame = new FahrerDatenMaske();
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
	public FahrerDatenMaske() {
		setTitle("KFM Fahrer");
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
		tfSuche.setBounds(10, 26, 1002, 19);
		contentPane.add(tfSuche);
		tfSuche.setColumns(10);
		
		btn_Save = new JButton("Speichern");
		btn_Save.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn_Save.setFocusPainted(false);
		btn_Save.setBackground(SystemColor.inactiveCaption);

		btn_Save.setBounds(10, 605, 180, 23);
		contentPane.add(btn_Save);

		JButton btnClear = new JButton("X");
		btnClear.setFont(new Font("Arial", Font.PLAIN, 10));
		btnClear.setFocusPainted(false);
		btnClear.setBackground(SystemColor.inactiveCaption);
		btnClear.setBounds(1012, 26, 19, 18);
		btnClear.setMargin(new Insets(0, 0, 0, 0));
		contentPane.add(btnClear);

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				tfSuche.setText("");
				filter(tfSuche.getText());
			}
		});

		JLabel lblZweitePrfung = new JLabel("Zweite Pruefung");
		lblZweitePrfung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblZweitePrfung.setBounds(50, 397, 75, 14);
		contentPane.add(lblZweitePrfung);

		JLabel lblFahrerlaubnis = new JLabel("Fahrerlaubnis");
		lblFahrerlaubnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFahrerlaubnis.setBounds(50, 284, 75, 14);
		contentPane.add(lblFahrerlaubnis);

		JLabel lblErstePrfung = new JLabel("Erste Pruefung");
		lblErstePrfung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErstePrfung.setBounds(50, 328, 75, 14);
		contentPane.add(lblErstePrfung);

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

		chckbxPruefung1 = new JCheckBox("erste Pruefung");
		chckbxPruefung1.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxPruefung1.setOpaque(false);
		chckbxPruefung1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxPruefung1.setBounds(10, 324, 20, 20);
		contentPane.add(chckbxPruefung1);

		chckbxPruefung2 = new JCheckBox("zweite Pruefung");
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

		chckbxFahrerlaubnis = new JCheckBox("Fahrerlaubnis");
		chckbxFahrerlaubnis.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxFahrerlaubnis.setOpaque(false);
		chckbxFahrerlaubnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxFahrerlaubnis.setBounds(10, 280, 20, 20);
		contentPane.add(chckbxFahrerlaubnis);
		
		btn_Abbrechen = new JButton("Abbrechen");
		btn_Abbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAllFields();
				setAllFields(false);
			}
		});
		btn_Abbrechen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn_Abbrechen.setFocusPainted(false);
		btn_Abbrechen.setBackground(SystemColor.inactiveCaption);
		btn_Abbrechen.setBounds(200, 605, 180, 23);
		contentPane.add(btn_Abbrechen);
		
		setAllFields(false);
		
		JButton btnZurueck = new JButton("");
		btnZurueck.setFocusable(false);
		btnZurueck.setBackground(Color.WHITE);
		btnZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hauptmenue frame = new Hauptmenue();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnZurueck.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\icons\\pfeil-zurueck.png"));
		btnZurueck.setBounds(10, 2, 28, 23);
		contentPane.add(btnZurueck);
		
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBorder(null);
//		scrollPane.setBounds(390, 50, 862, 574);
//		contentPane.add(scrollPane);

		tableFahrer = new JTable();
		tableFahrer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		scrollPane.setViewportView(tableFahrer);
		tableFahrer.setBorder(null);
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Personalnummer", "AktivKZ", "Name", "Vorname", "FirmaNr", "NL_Nr",
						"Fahrerlaubnis", "Erstpruefung", "Pruefungszeitpunkt1", "Kommentar1", "Zweitpruefung",
						"Pruefungszeitpunkt2", "Kommentar2", "Bearbeitet" , "FahrzeugID" }));
		
		tableFahrer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		wichtigTf(tfPersonalnummer);
		wichtigTf(tfNachname);
		wichtigTf(tfAktivKz);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNlNr);

		JButton btn_Anlegen = new JButton("Anlegen");
		btn_Anlegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modus = "anlegen";
				setAllFields(true);
				wichtigTf(tfPersonalnummer);
				wichtigTf(tfNachname);
				wichtigTf(tfAktivKz);
				wichtigTf(tfFirmaNr);
				wichtigTf(tfNlNr);
				tfVorname.setText("");
				tfKommentar1.setText("");
				tfKommentar2.setText("");
				chckbxFahrerlaubnis.setSelected(false);
				chckbxPruefung1.setSelected(false);
				chckbxPruefung2.setSelected(false);
			}
		});
		btn_Anlegen.setBounds(1040, 2, 45, 43);
		contentPane.add(btn_Anlegen);

		JButton btn_Bearbeiten = new JButton("Bearbeiten");
		btn_Bearbeiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modus = "bearbeiten";
				setAllFields(true);
			}
		});
		btn_Bearbeiten.setBounds(1095, 2, 45, 43);
		contentPane.add(btn_Bearbeiten);

		JButton btn_Loeschen = new JButton("New button");
		btn_Loeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Object[] options = { "Ja", "Nein" };
				int n = JOptionPane.showOptionDialog(null, "Sind Sie sich sicher?", "Datensatz löschen", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.DEFAULT_OPTION, null, options, options[1]);
				if (n == 0) {
					try {
						int i = tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow());
						TableModel model = tableFahrer.getModel();
						id = model.getValueAt(i, 0).toString();

						String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
						con = DriverManager.getConnection(url);
						String query = "DELETE FROM MitarbeiterTest WHERE ID=" + id;
						PreparedStatement pst = con.prepareStatement(query);

						pst.executeUpdate();

						show_aktualisierte_fahrerliste();
						vergleichsliste = fahrer();
						JOptionPane.showMessageDialog(null, "Datensatz wurde gelöscht!");
					}

					catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});

		JButton btn_Dokumente = new JButton("Dokumente");
		btn_Dokumente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id_Uebergabe_fahrzeug = tableFahrer.getModel()
						.getValueAt(tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow()), 0)
						.toString();
				id_Uebergabe_fahrer = tableFahrer.getModel()
						.getValueAt(tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow()), 15)
						.toString();
				herkunft_ueber_fahrer = true;
				DokumentAnsehenMaske frame = new DokumentAnsehenMaske();
				frame.setVisible(true);
			}
		});
		btn_Dokumente.setBounds(1204, 2, 45, 43);
		contentPane.add(btn_Dokumente);
		btn_Loeschen.setBounds(1149, 2, 45, 43);
		contentPane.add(btn_Loeschen);
		
		scrollpane(btn_Save, btnClear, btn_Abbrechen, btn_Anlegen, btn_Bearbeiten, btn_Loeschen, btn_Dokumente);

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
		

		btn_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modus == "bearbeiten") {
					int i = tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow());
					int aktualisiert;
					aktualisiert = vergleichsliste.get(i).getBearbeitet();
					ArrayList<Fahrer> fahrerliste = fahrer();
					if (aktualisiert == fahrerliste.get(i).getBearbeitet()) {
						try {
							
							emptyTf(tfPersonalnummer);
							emptyTf(tfVorname);
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
							
							JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
						}
						catch (IndexOutOfBoundsException e2) {
							//JOptionPane.showMessageDialog(null, e);
						}

						catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Ihre Änderungen wurden nicht gespeichert, bitte ueberarbeiten Sie die Daten gegebenfalls noch einmal.");
					}
					vergleichsliste = fahrer();
					show_aktualisierte_fahrerliste();
					setAllFields(false);
					modus = "";
				}
				else if (modus == "anlegen") {
					try {
						emptyTf(tfPersonalnummer);
						emptyTf(tfVorname);
						emptyTf(tfNachname);
						emptyTf(tfAktivKz);
						emptyTf(tfFirmaNr);
						emptyTf(tfNlNr);

						String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
						con = DriverManager.getConnection(url);
						String query = "insert into MitarbeiterTest (Personalnummer,AktivKZ,Name,Vorname,FirmaNr,NL_Nr,Fahrerlaubnis,Erstprüfung,Prüfungszeitpunkt1,Kommentar1,Zweitprüfung,Prüfungszeitpunkt2,Kommentar2, Bearbeitet, FahrzeugID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement pst = con.prepareStatement(query);

						TableModel model = tableFahrer.getModel();
						int numOfRows = model.getRowCount();

						for (int k = 1; k < numOfRows; k++) {
							String checkRow = model.getValueAt(k, 1).toString();
							if (tfPersonalnummer.getText().equals(checkRow)) {
								throw new Exception("Dieser Fahrer exisitiert bereits!");
							}
						}

						pst.setString(1, tfPersonalnummer.getText());
						pst.setString(2, "1");
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
						pst.setInt(14, 0);
						pst.setString(15, "");

						pst.executeUpdate();
						
						sendEmail();
						show_hinzugefuegten_fahrer();
						
						vergleichsliste = fahrer();
						show_aktualisierte_fahrerliste();
						setAllFields(false);
						modus = "";
						JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
					}

					catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					}

				}
			}
		});
		
	}

	public static ArrayList<Fahrer> fahrer() {
		ArrayList<Fahrer> fahrerliste = new ArrayList<>();

		try {
			LE_Sichtbarkeit = LoginMaske.LE_Sichtbarkeit_Uebergabe;
			con = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1;
			if (LE_Sichtbarkeit.equals("Admin")) {
				query1 = "Select * from MitarbeiterTest where AktivKZ <> 4";
			} else {
				query1 = "Select * from MitarbeiterTest where AktivKZ <> 4 and FirmaNr=" + LE_Sichtbarkeit;
			}
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);
			Fahrer fahrer;
			while (rs.next()) {
				fahrer = new Fahrer(rs.getInt("ID"), rs.getInt("Personalnummer"), rs.getInt("AktivKZ"),
						rs.getString("Name"), rs.getString("Vorname"), rs.getString("FirmaNr"), rs.getInt("NL_Nr"),
						rs.getString("Fahrerlaubnis"), rs.getString("Erstprüfung"), rs.getString("Prüfungszeitpunkt1"),
						rs.getString("Kommentar1"), rs.getString("Zweitprüfung"), rs.getString("Prüfungszeitpunkt2"),
						rs.getString("Kommentar2"), rs.getInt("Bearbeitet"), rs.getString("FahrzeugID"));
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
		Object[] row = new Object[16];
		for (int i = 0; i < fahrer.size(); i++) {
			row[0] = fahrer.get(i).getID();
			row[1] = fahrer.get(i).getPersonalnummer();
			row[2] = fahrer.get(i).getAktivKZ();
			row[3] = fahrer.get(i).getName();
			row[4] = fahrer.get(i).getVorname();
			row[5] = fahrer.get(i).getFirmaNr();
			row[6] = fahrer.get(i).getNL_Nr();
			row[7] = fahrer.get(i).getFahrerlaubnis();
			row[8] = fahrer.get(i).getErstpruefung();
			row[9] = fahrer.get(i).getPruefungszeitpunkt1();
			row[10] = fahrer.get(i).getKommentar1();
			row[11] = fahrer.get(i).getZweitpruefung();
			row[12] = fahrer.get(i).getPruefungszeitpunkt2();
			row[13] = fahrer.get(i).getKommentar2();
			row[14] = fahrer.get(i).getBearbeitet();
			row[15] = fahrer.get(i).getFahrzeugID();
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
	
	public void scrollpane(JButton btnSave, JButton btnClear, JButton btn_Abbrechen, JButton btn_Anlegen, JButton btn_Bearbeiten, JButton btn_Loeschen, JButton btn_Dokumente) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(390, 50, 860, 574);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		scrollPane.setViewportView(tableFahrer);

		JLabel lblBackground_2 = new JLabel("");
		lblBackground_2.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));

		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(final WindowEvent evt) {
				if (evt.getNewState() == MAXIMIZED_BOTH) {
					scrollPane.setBounds(390, 50, 1518, 956);
					scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
					tableFahrer.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					btnSave.setBounds(10, 986, 180, 23);
					btn_Abbrechen.setBounds(200, 986, 180, 23);
					btn_Anlegen.setBounds(1698, 2, 45, 43);
					btn_Bearbeiten.setBounds(1753, 2, 45, 43);
					btn_Loeschen.setBounds(1807, 2, 45, 43);
					btn_Dokumente.setBounds(1862, 2, 45, 43);
					tfSuche.setBounds(10, 26, 1660, 19);
					btnClear.setBounds(1670, 26, 19, 18);
				} else {
					scrollPane.setBounds(391, 50, 860, 574);
					scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					tableFahrer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					btnSave.setBounds(10, 605, 180, 23);
					btn_Abbrechen.setBounds(200, 605, 180, 23);
					btn_Anlegen.setBounds(1040, 2, 45, 43);
					btn_Bearbeiten.setBounds(1095, 2, 45, 43);
					btn_Loeschen.setBounds(1149, 2, 45, 43);
					btn_Dokumente.setBounds(1204, 2, 45, 43);
					tfSuche.setBounds(10, 26, 1002, 19);
					btnClear.setBounds(1012, 26, 19, 18);
				}
			}
		});
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
	
	public void setAllFields(boolean wert) {
		tfPersonalnummer.setEnabled(wert);
		tfVorname.setEnabled(wert);
		tfFirmaNr.setEnabled(wert);
		tfAktivKz.setEnabled(wert);
		tfNlNr.setEnabled(wert);
		tfNachname.setEnabled(wert);
		tfKommentar1.setEnabled(wert);
		tfKommentar2.setEnabled(wert);
		chckbxFahrerlaubnis.setEnabled(wert);
		chckbxPruefung1.setEnabled(wert);
		chckbxPruefung2.setEnabled(wert);
	}
	
	public void clearAllFields() {
		tfPersonalnummer.setText("");
		tfVorname.setText("");
		tfFirmaNr.setText("");
		tfAktivKz.setText("");
		tfNlNr.setText("");
		tfNachname.setText("");
		tfKommentar1.setText("");
		tfKommentar2.setText("");
		chckbxFahrerlaubnis.setText("");
		chckbxPruefung1.setText("");
		chckbxPruefung2.setText("");
		wichtigTf(tfPersonalnummer);
		wichtigTf(tfNachname);
		wichtigTf(tfAktivKz);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNlNr);
	}
	
	public static void show_hinzugefuegten_fahrer() {
		try {
			DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
			model.setRowCount(0);
			show_fahrer();
		} catch (IndexOutOfBoundsException e) {
			// JOptionPane.showMessageDialog(null, e);
		}
		;
	}
	
	private static Message prepareMessage(Session session, String myAccount, String empfaenger) throws Exception {
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(myAccount));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(empfaenger));
		message.setSubject("Fuhrpark Management System");

		// Multipart-Message ("Wrapper") erstellen
		Multipart multipart = new MimeMultipart();
		// Body-Part setzen:
		BodyPart messageBodyPart = new MimeBodyPart();
		// Textteil des Body-Parts
		messageBodyPart.setText("Hallo");
		// Body-Part dem Multipart-Wrapper hinzufügen
		multipart.addBodyPart(messageBodyPart);
		// Message fertigstellen, indem sie mit dem Multipart-Content ausgestattet wird
		message.setContent(multipart);

		return message;
	}

	public void sendEmail() {
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp-mail.outlook.com");
		properties.put("mail.smtp.port", "587");

		String myAccount = "tolga.soylu@konzmann.de";
		String myPassword = "Tolga@Konz";
		String empfaenger = "tolga.soylu@konzmann.de";

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccount, myPassword);
			}
		});

		// Message-Objekt erzeugen und senden!
		try {
			Message message = prepareMessage(session, myAccount, empfaenger);
			Transport.send(message); // E-Mail senden!
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
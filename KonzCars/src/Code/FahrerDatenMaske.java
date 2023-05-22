package Code;

import java.awt.*;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class FahrerDatenMaske extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	static Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	Dimension screenSize;
	
	ArrayList<Fahrer> vergleichsliste = new ArrayList<>();

	private static JTable tableFahrer;
	private static int id;
	
	int f_width, f_height;
	
	private static ArrayList<String> arrFirmenname = new ArrayList<String>();
	private static ArrayList<String> arrNLName = new ArrayList<String>();
	private static ArrayList<String> maxID_array = new ArrayList<String>();

	boolean erweitern = false;

	public static JTextField tfPersonalnummer;
	private static JTextField tfAktivKz;
	private static JTextField tfNachname;
	private static JTextField tfVorname;
	
	private static JTextField tfFirmaNr;
	private JComboBox<?> cBoxFirmenname;
	
	private static JTextField tfNLNr;
	private JComboBox<?> cBoxNLName;
	
	private static JCheckBox chckbxFahrerlaubnis;
	private static JCheckBox chckbxPruefung1;
	// Pruefungszeitpunkt1
	private static JTextField tfKommentar1;
	private static JCheckBox chckbxPruefung2;
	// Pruefungszeitpunkt2
	private static JTextField tfKommentar2;

	private JTextField tfSuche;

	private String modus;

	public static boolean herkunft_ueber_fahrzeug;
	public static String id_Uebergabe_fahrzeug;
	public static String id_Uebergabe_fahrer;
	public static int Uebergabe_Pruefung1;
	public static int Uebergabe_Pruefung2;
	private static String LE_Sichtbarkeit;
	static LoginMaske loginMaske;

	public JButton btnAbbrechen;
	public JButton btnSave;
	
	public static JLabel lblZeilenAnzahl;
	public static int zeilenAnzahl;
	
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
		
		//screensize
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		f_width = getWidth();
		f_height = getHeight();
		
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
		contentPane.add(tfSuche);
		tfSuche.setColumns(10);
		
		JButton btn_Anlegen = new JButton("Anlegen");
		JButton btn_Bearbeiten = new JButton("Bearbeiten");
		JButton btn_Loeschen = new JButton("L\u00F6schen");
		JButton btn_Dokumente = new JButton("Dokumente");

		JButton btnClear = new JButton("X");
		btnClear.setFont(new Font("Arial", Font.PLAIN, 10));
		btnClear.setFocusPainted(false);
		btnClear.setBackground(SystemColor.inactiveCaption);
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
		lblZweitePrfung.setBounds(50, 447, 75, 14);
		contentPane.add(lblZweitePrfung);

		JLabel lblFahrerlaubnis = new JLabel("Fahrerlaubnis");
		lblFahrerlaubnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFahrerlaubnis.setBounds(50, 334, 75, 14);
		contentPane.add(lblFahrerlaubnis);

		JLabel lblErstePrfung = new JLabel("Erste Pruefung");
		lblErstePrfung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErstePrfung.setBounds(50, 378, 75, 14);
		contentPane.add(lblErstePrfung);

		btnSave = new JButton("Speichern");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setFocusPainted(false);
		btnSave.setBackground(SystemColor.inactiveCaption);

		btnSave.setBounds(10, f_height -100, 180, 23);
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
//		contentPane.add(tfAktivKz);

		tfFirmaNr = new JTextField();
		tfFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFirmaNr.setColumns(10);
		tfFirmaNr.setBounds(137, 193, 60, 20);
		contentPane.add(tfFirmaNr);
		
//		tfFirmenname = new JTextField();
//		tfFirmenname.setFont(new Font("Tahoma", Font.PLAIN, 11));
//		tfFirmenname.setColumns(10);
//		contentPane.add(tfFirmenname);

		tfNLNr = new JTextField();
		tfNLNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfNLNr.setColumns(10);
		tfNLNr.setBounds(137, 244, 60, 20);
		contentPane.add(tfNLNr);
		
//		tfNLName = new JTextField();
//		tfNLName.setFont(new Font("Tahoma", Font.PLAIN, 11));
//		tfNLName.setColumns(10);
//		contentPane.add(tfNLName);

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
//		contentPane.add(lblAktivKZ);

		JLabel lblFirmaNr = new JLabel("FirmaNr");
		lblFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFirmaNr.setBounds(10, 193, 115, 14);
		contentPane.add(lblFirmaNr);
		
		JLabel lblFirmenname = new JLabel("Firmenname");
		lblFirmenname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFirmenname.setBounds(10, 214, 115, 14);
		contentPane.add(lblFirmenname);

		JLabel lblNLNr = new JLabel("NLNr");
		lblNLNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNLNr.setBounds(10, 244, 115, 14);
		contentPane.add(lblNLNr);
		
		JLabel lblNLName = new JLabel("Niederlassung");
		lblNLName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNLName.setBounds(10, 265, 115, 14);
		contentPane.add(lblNLName);

		chckbxPruefung1 = new JCheckBox("erste Pruefung");
		chckbxPruefung1.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxPruefung1.setOpaque(false);
		chckbxPruefung1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxPruefung1.setBounds(10, 374, 20, 20);
		contentPane.add(chckbxPruefung1);

		chckbxPruefung2 = new JCheckBox("zweite Pruefung");
		chckbxPruefung2.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxPruefung2.setOpaque(false);
		chckbxPruefung2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxPruefung2.setBounds(10, 443, 20, 20);
		contentPane.add(chckbxPruefung2);

		tfKommentar1 = new JTextField();
		tfKommentar1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfKommentar1.setBounds(10, 401, 370, 23);
		contentPane.add(tfKommentar1);
		tfKommentar1.setColumns(10);

		tfKommentar2 = new JTextField();
		tfKommentar2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfKommentar2.setBounds(10, 470, 370, 23);
		contentPane.add(tfKommentar2);
		tfKommentar2.setColumns(10);

		chckbxFahrerlaubnis = new JCheckBox("Fahrerlaubnis");
		chckbxFahrerlaubnis.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxFahrerlaubnis.setOpaque(false);
		chckbxFahrerlaubnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxFahrerlaubnis.setBounds(10, 330, 20, 20);
		contentPane.add(chckbxFahrerlaubnis);
		
		lblZeilenAnzahl = new JLabel("Zeilenanzahl: ");
		lblZeilenAnzahl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblZeilenAnzahl.setBounds(390, f_height -100, f_width -400, 23);
		contentPane.add(lblZeilenAnzahl);

		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_Anlegen.setBorder(BorderFactory.createLineBorder(Color.gray,1));
				btn_Bearbeiten.setBorder(BorderFactory.createLineBorder(Color.gray,1));
				clearAllFields();
				setAllFields(false);
			}
		});
		btnAbbrechen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAbbrechen.setFocusPainted(false);
		btnAbbrechen.setBackground(SystemColor.inactiveCaption);
		btnAbbrechen.setBounds(200, f_height -100, 180, 23);
		contentPane.add(btnAbbrechen);
		
		//combobox Firmenname
		fuelleArrFirmennameList(arrFirmenname);
		String[] a1 = new String[arrFirmenname.size() + 1];
				
		a1[0] = "";

		for (int i = 1; i < a1.length; i++) {
			a1[i] = arrFirmenname.get(i - 1);
		}
		cBoxFirmenname = new JComboBox<Object>(a1);
		cBoxFirmenname.setBounds(137, 216, 243, 19);
		contentPane.add(cBoxFirmenname);
						
		//combobox Niederlassungsname
		fuelleArrNLNameList(arrNLName);
		String[] a2 = new String[arrNLName.size() + 1];

		a2[0] = "";

		for (int j = 1; j < a2.length; j++) {
			a2[j] = arrNLName.get(j - 1);
		}
		cBoxNLName = new JComboBox<Object>(a2);
		cBoxNLName.setBounds(137, 267, 243, 19);
		contentPane.add(cBoxNLName);

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
		try {
			btnZurueck.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/res/pfeil-zurueck.png"))));
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		btnZurueck.setBounds(10, 2, 28, 23);
		contentPane.add(btnZurueck);

		tableFahrer = new JTable();
		tableFahrer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFahrer.setBorder(null);
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Personalnummer", "AktivKZ", "Name", "Vorname", "FirmaNr", "Firmenname", "NLNr",
						"NLName", "Fahrerlaubnis", "Erstpruefung", "Pruefungszeitpunkt1", "Kommentar1", "Zweitpruefung",
						"Pruefungszeitpunkt2", "Kommentar2", "FahrzeugID", "Bearbeitet" }));
		
		tableFahrer.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = tableFahrer.getColumnModel();
		for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
			if(columnIndex == 6) {
				tableFahrer.getColumnModel().getColumn(columnIndex).setPreferredWidth(220);
			}
			else {
				tableFahrer.getColumnModel().getColumn(columnIndex).setPreferredWidth(100);
			}
		}
		
		filter("");

		wichtigTf(tfPersonalnummer);
		wichtigTf(tfNachname);
//		wichtigTf(tfAktivKz);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNLNr);
		
		btn_Anlegen.setIcon(null);
		btn_Anlegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modus = "anlegen";
				activeMode(btn_Anlegen, btn_Bearbeiten);
				setAllFields(true);
				
				wichtigTf(tfPersonalnummer);
				wichtigTf(tfNachname);
//				wichtigTf(tfAktivKz);
				wichtigTf(tfFirmaNr);
				wichtigTf(tfNLNr);
				
				cBoxFirmenname.setSelectedIndex(0);
				cBoxNLName.setSelectedIndex(0);
				
				tfVorname.setText("");
				tfKommentar1.setText("");
				tfKommentar2.setText("");
				tfSuche.setText("");
				
				chckbxFahrerlaubnis.setSelected(false);
				chckbxPruefung1.setSelected(false);
				chckbxPruefung2.setSelected(false);
			}
		});
		
		btn_Bearbeiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modus = "bearbeiten";
				activeMode(btn_Bearbeiten, btn_Anlegen);
				setAllFields(true);
				tfPersonalnummer.setEnabled(false);
			}
		});
		
		btn_Loeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btn_Anlegen.setBorder(BorderFactory.createLineBorder(Color.gray,1));
				btn_Bearbeiten.setBorder(BorderFactory.createLineBorder(Color.gray,1));
				Object[] options = { "Ja", "Nein" };
				int n = JOptionPane.showOptionDialog(null, "Sind Sie sich sicher?", "Datensatz löschen",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[1]);
				if (n == 0) {
					try {
						int i = tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow());
						TableModel model = tableFahrer.getModel();
						id = (int) model.getValueAt(i, 0);

						String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
						conn = DriverManager.getConnection(url);
						String query = "DELETE FROM Fahrer WHERE ID=" + id;
						PreparedStatement pst = conn.prepareStatement(query);

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
		
		btn_Dokumente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_Anlegen.setBorder(BorderFactory.createLineBorder(Color.gray,1));
				btn_Bearbeiten.setBorder(BorderFactory.createLineBorder(Color.gray,1));
				if (tableFahrer.getModel().getValueAt(tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow()),
						17) != null) {
					id_Uebergabe_fahrzeug = tableFahrer.getModel()
							.getValueAt(tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow()), 17)
							.toString();
				}
				id_Uebergabe_fahrer = tableFahrer.getModel()
						.getValueAt(tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow()), 0).toString();
				herkunft_ueber_fahrzeug = true;
				DokumenteMaske frame = new DokumenteMaske();
				frame.setResizable(false);
				frame.setVisible(true);
			}
		});
		
		// Navbar
		tfSuche.setBounds(10, 26, f_width -453, 19);		
		btnClear.setBounds(f_width -443, 26, 19, 18);		
		btn_Anlegen.setBounds(f_width -420, 25, 93, 19);
		btn_Anlegen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn_Anlegen.setFocusPainted(false);
		btn_Anlegen.setBackground(SystemColor.inactiveCaption);
		contentPane.add(btn_Anlegen);
		btn_Bearbeiten.setBounds(f_width -317, 25, 93, 19);
		btn_Bearbeiten.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn_Bearbeiten.setFocusPainted(false);
		btn_Bearbeiten.setBackground(SystemColor.inactiveCaption);
		contentPane.add(btn_Bearbeiten);
		btn_Loeschen.setBounds(f_width -214, 25, 93, 19);
		btn_Loeschen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn_Loeschen.setFocusPainted(false);
		btn_Loeschen.setBackground(SystemColor.inactiveCaption);
		contentPane.add(btn_Loeschen);
		btn_Dokumente.setBounds(f_width -111, 25, 100, 19);
		btn_Dokumente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn_Dokumente.setFocusPainted(false);
		btn_Dokumente.setBackground(SystemColor.inactiveCaption);
		contentPane.add(btn_Dokumente);

		JButton btnExport = new JButton("Exportieren");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnExport) {
					JFileChooser fchoose = new JFileChooser();
					int option = fchoose.showSaveDialog(FahrerDatenMaske.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						String name = fchoose.getSelectedFile().getName();
						String path = fchoose.getSelectedFile().getParentFile().getPath();
						String file = path + "\\" + name + ".xls";
						export(tableFahrer, new File(file));
					}
				}
			}
		});
		btnExport.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnExport.setFocusPainted(false);
		btnExport.setEnabled(true);
		btnExport.setBackground(SystemColor.inactiveCaption);
		btnExport.setBounds(f_width -192, f_height -100, 180, 23);
		contentPane.add(btnExport);
		
		scrollpane(btnSave, btnClear, btnAbbrechen, btn_Anlegen, btn_Bearbeiten, btn_Loeschen, btn_Dokumente, btnExport);

		tableFahrer.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int i = tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow());
				TableModel model = tableFahrer.getModel();
				
//				clearAllFields();
				
				id = (int) model.getValueAt(i, 0);
				
				if (model.getValueAt(i, 1) != null) {
					tfPersonalnummer.setText(model.getValueAt(i, 1).toString());
				}
				
				if (model.getValueAt(i, 2) != null) {
					tfAktivKz.setText(model.getValueAt(i, 2).toString());
				}
				
				if (model.getValueAt(i, 3) != null) {
					tfNachname.setText(model.getValueAt(i, 3).toString());
				}
				
				if (model.getValueAt(i, 4) != null) {
					tfVorname.setText(model.getValueAt(i, 4).toString());
				}
				
				if (model.getValueAt(i, 5) != null) {
					tfFirmaNr.setText(model.getValueAt(i, 5).toString());
				}
				
				if (model.getValueAt(i, 6) != null) {
					String s1;
					String s2 = model.getValueAt(i, 6).toString();
					for (int j = 1; j < cBoxFirmenname.getItemCount(); j++) {
						s1 = cBoxFirmenname.getItemAt(j).toString();
						if (s1.equals(s2)) {
							cBoxFirmenname.setSelectedIndex(j);
						}
					}
					if (model.getValueAt(i, 6).toString().equals("")) {
						cBoxFirmenname.setSelectedIndex(0);
					}
				}
				
				if (model.getValueAt(i, 7) != null) {
					tfNLNr.setText(model.getValueAt(i, 7).toString());
				}
				
				if (model.getValueAt(i, 8) != null) {
					String s1;
					String s2 = model.getValueAt(i, 8).toString();
					for (int j = 1; j < cBoxNLName.getItemCount(); j++) {
						s1 = cBoxNLName.getItemAt(j).toString();
						if (s1.equals(s2)) {
							cBoxNLName.setSelectedIndex(j);
						}
					}
					if (model.getValueAt(i, 8).toString().equals("")) {
						cBoxNLName.setSelectedIndex(0);
					}
				}
				
				if (model.getValueAt(i, 9) != null) {
					String fahrerlaubnis = model.getValueAt(i, 9).toString();
					switch (fahrerlaubnis) {
					case "1":
						chckbxFahrerlaubnis.setSelected(true);
						break;
					case "0":
						chckbxFahrerlaubnis.setSelected(false);
						break;
					}
				}
				
				if (model.getValueAt(i, 10) != null) {
					String erstpruefung = model.getValueAt(i, 10).toString();
					switch (erstpruefung) {
					case "1":
						chckbxPruefung1.setSelected(true);
						break;
					case "0":
						chckbxPruefung1.setSelected(false);
						break;
					}
				}
				
				if (model.getValueAt(i, 12) != null) {
					tfKommentar1.setText(model.getValueAt(i, 12).toString());
				}
				
				if (model.getValueAt(i, 13) != null) {
					String zweitpruefung = model.getValueAt(i, 13).toString();
					switch (zweitpruefung) {
					case "1":
						chckbxPruefung2.setSelected(true);
						break;
					case "0":
						chckbxPruefung2.setSelected(false);
						break;
					}
				}
				
				if (model.getValueAt(i, 15) != null) {
					tfKommentar2.setText(model.getValueAt(i, 15).toString());
				}
			}
		});

		vergleichsliste = fahrer();
		show_fahrer();

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modus == "bearbeiten") {
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
							emptyTf(tfNLNr);

							String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
							conn = DriverManager.getConnection(url);
							String query = "UPDATE Fahrer SET Personalnummer=?,AktivKZ=?,Name=?,Vorname=?,FirmaNr=?,Firmenname=?,NLNr=?,NLName=?,Fahrerlaubnis=?,Erstpruefung=?,Pruefungszeitpunkt1=?,Kommentar1=?,Zweitpruefung=?,Pruefungszeitpunkt2=?,Kommentar2=?, Bearbeitet=? WHERE ID="
									+ id;
							
							Object object1 = cBoxFirmenname.getSelectedItem();
							String selectedItem1 = object1.toString();
							String item1[] = new String[1];
							
							Object object2 = cBoxNLName.getSelectedItem();
							String selectedItem2 = object2.toString();
							String item2[] = new String[1];
							
							if (!selectedItem1.equals("")) {
								StringTokenizer strings = new StringTokenizer(selectedItem1, ",");

								item1[0] = strings.nextElement().toString();
							}
							
							if (!selectedItem2.equals("")) {
								StringTokenizer strings = new StringTokenizer(selectedItem2, ",");

								item2[0] = strings.nextElement().toString();
							}

							PreparedStatement pst = conn.prepareStatement(query);
							
							pst.setString(1, tfPersonalnummer.getText());
							pst.setString(2, "2");
							pst.setString(3, tfNachname.getText());
							pst.setString(4, tfVorname.getText());
							pst.setString(5, tfFirmaNr.getText());
							try {
								if (selectedItem1.equals("")) {
									pst.setString(6, "");
								} else {
									pst.setString(6, item1[0]);
								}
							} catch (Exception e2) {
							}
							
							pst.setString(7, tfNLNr.getText());
							
							try {
								if (selectedItem2.equals("")) {
									pst.setString(8, "");
								} else {
									pst.setString(8, item2[0]);
								}
							} catch (Exception e2) {
							}
							
							boolean fahrerlaubnis = false;
							if (chckbxFahrerlaubnis.isSelected()) {
								fahrerlaubnis = true;
							}
							if (fahrerlaubnis) {
								pst.setString(9, "1");
							} else {
								pst.setString(9, "0");
							}

							boolean pruefung1 = false;
							if (chckbxPruefung1.isSelected()) {
								pruefung1 = true;
							}
							if (pruefung1) {
								pst.setString(10, "1");
								;
							} else {
								pst.setString(10, "0");
							}

							pst.setString(11, "");
							pst.setString(12, tfKommentar1.getText());

							boolean pruefung2 = false;
							if (chckbxPruefung2.isSelected()) {
								pruefung2 = true;
							}
							if (pruefung2) {
								pst.setString(13, "1");
								;
							} else {
								pst.setString(13, "0");
							}

							pst.setString(14, "");
							pst.setString(15, tfKommentar2.getText());

							try {
								fahrerliste = fahrer();
								tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow());
								pst.setInt(16, fahrerliste.get(i).getBearbeitet() + 1);
							} catch (IndexOutOfBoundsException e1) {

							}

							pst.executeUpdate();

							show_aktualisierte_fahrerliste();

							JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
						} 
						
						catch (IndexOutOfBoundsException e2) {
							// JOptionPane.showMessageDialog(null, e);
						}

						catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1);
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Ihre Aenderungen wurden nicht gespeichert, bitte Ueberarbeiten Sie die Daten gegebenfalls noch einmal.");
					}
					
					vergleichsliste = fahrer();
					show_aktualisierte_fahrerliste();
					setAllFields(false);
					modus = "";

				} else if (modus == "anlegen") {
					try {
						emptyTf(tfPersonalnummer);
						emptyTf(tfNachname);
//						emptyTf(tfAktivKz);
						emptyTf(tfFirmaNr);
						emptyTf(tfNLNr);

						TableModel model = tableFahrer.getModel();
						int numOfRows = model.getRowCount();

						String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
						conn = DriverManager.getConnection(url);
						String query = "insert into Fahrer (Personalnummer,AktivKZ,Name,Vorname,FirmaNr,Firmenname,NLNr,NLName,Fahrerlaubnis,Erstpruefung,Pruefungszeitpunkt1,Kommentar1,Zweitpruefung,Pruefungszeitpunkt2,Kommentar2, Bearbeitet) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

						Object object1 = cBoxFirmenname.getSelectedItem();
						String selectedItem1 = object1.toString();
						String item1[] = new String[1];
						
						Object object2 = cBoxNLName.getSelectedItem();
						String selectedItem2 = object2.toString();
						String item2[] = new String[1];
						
						if (!selectedItem1.equals("")) {
							StringTokenizer strings = new StringTokenizer(selectedItem1, ",");

							item1[0] = strings.nextElement().toString();
						}
						if (!selectedItem2.equals("")) {
							StringTokenizer strings = new StringTokenizer(selectedItem2, ",");

							item2[0] = strings.nextElement().toString();
						}
						
						fuelleArrayMaxIDList(maxID_array);

						PreparedStatement pst = conn.prepareStatement(query);

						for (int i = 1; i < numOfRows; i++) {
							String checkRow = model.getValueAt(i, 1).toString();
							if (tfPersonalnummer.getText().equals(checkRow)) {
								throw new Exception("Dieser Fahrer exisitiert bereits!");
							}
						}

						pst.setString(1, tfPersonalnummer.getText());
						pst.setString(2, "1");
						pst.setString(3, tfNachname.getText());
						pst.setString(4, tfVorname.getText());
						pst.setString(5, tfFirmaNr.getText());
						try {
							if (selectedItem1.equals("")) {
								pst.setString(6, "");
							} else {
								pst.setString(6, item1[0]);
							}
						} catch (Exception e2) {
						}
						pst.setString(7, tfNLNr.getText());
						try {
							if (selectedItem2.equals("")) {
								pst.setString(8, "");
							} else {
								pst.setString(8, item2[0]);
							}
						} catch (Exception e2) {
						}
						boolean fahrerlaubnis = false;
						if (chckbxFahrerlaubnis.isSelected()) {
							fahrerlaubnis = true;
						}
						if (fahrerlaubnis) {
							pst.setString(9, "1");
//							checkFahrerlaubnis = 1;
						} else {
							pst.setString(9, "0");
						}

						boolean pruefung1 = false;
						if (chckbxPruefung1.isSelected()) {
							pruefung1 = true;
						}
						if (pruefung1) {
							pst.setString(10, "1");
//							checkPruefung1 = 1;
						} else {
							pst.setString(10, "0");
						}

						pst.setString(11, "");
						pst.setString(12, tfKommentar1.getText());

						boolean pruefung2 = false;
						if (chckbxPruefung2.isSelected()) {
							pruefung2 = true;
						}
						if (pruefung2) {
							pst.setString(13, "1");
//							checkPruefung2 = 1;
						} else {
							pst.setString(13, "0");
						}

						pst.setString(14, "");
						pst.setString(15, tfKommentar2.getText());
						pst.setInt(16, 0);

						pst.executeUpdate();

//						String query2 = "UPDATE Fuhrpark SET Pruefung1=" + checkPruefung1 + ", Pruefung2=" + checkPruefung2 + ", Fahrerlaubnis=" + checkFahrerlaubnis + " WHERE ID= FahrzeugID";
//						PreparedStatement pst2 = conn.prepareStatement(query2);
//						pst2.executeUpdate();

						vergleichsliste = fahrer();
						show_aktualisierte_fahrerliste();
						setAllFields(false);
						modus = "";
						JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");

//						sendEmail();
						show_hinzugefuegten_fahrer();

						// JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
					}

					catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBorder(null);
//		scrollPane.setBounds(390, 50, 862, 574);
//		contentPane.add(scrollPane);	
	}

	public static ArrayList<Fahrer> fahrer() {
		ArrayList<Fahrer> fahrerliste = new ArrayList<>();
		try {
			LE_Sichtbarkeit = LoginMaske.LE_Sichtbarkeit_Uebergabe;
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1;
			if (LE_Sichtbarkeit.equals("Admin")) {
				query1 = "Select * from Fahrer";
			} else {
				query1 = "Select * from Fahrer where FirmaNr=" + LE_Sichtbarkeit;
			}
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query1);
			Fahrer fahrer;
			while (rs.next()) {
				fahrer = new Fahrer(rs.getInt("ID"), rs.getString("Personalnummer"), rs.getInt("AktivKZ"),
						rs.getString("Name"), rs.getString("Vorname"), rs.getString("FirmaNr"), rs.getString("Firmenname"),
						rs.getString("NLNr"), rs.getString("NLName"), rs.getString("Fahrerlaubnis"), rs.getString("Erstpruefung"),
						rs.getString("Pruefungszeitpunkt1"), rs.getString("Kommentar1"), rs.getString("Zweitpruefung"),
						rs.getString("Pruefungszeitpunkt2"), rs.getString("Kommentar2"), rs.getInt("Bearbeitet"),
						rs.getString("FahrzeugID"));
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
		rowCount();
	}

	public static void show_fahrer() {
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		ArrayList<Fahrer> fahrer = fahrer();
		Object[] row = new Object[18];
		for (int i = 0; i < fahrer.size(); i++) {
			row[0] = fahrer.get(i).getID();
			row[1] = fahrer.get(i).getPersonalnummer();
			row[2] = fahrer.get(i).getAktivKZ();
			row[3] = fahrer.get(i).getName();
			row[4] = fahrer.get(i).getVorname();
			row[5] = fahrer.get(i).getFirmaNr();
			row[6] = fahrer.get(i).getFirmenname();
			row[7] = fahrer.get(i).getNLNr();
			row[8] = fahrer.get(i).getNLName();
			row[9] = fahrer.get(i).getFahrerlaubnis();
			row[10] = fahrer.get(i).getErstpruefung();
			row[11] = fahrer.get(i).getPruefungszeitpunkt1();
			row[12] = fahrer.get(i).getKommentar1();
			row[13] = fahrer.get(i).getZweitpruefung();
			row[14] = fahrer.get(i).getPruefungszeitpunkt2();
			row[15] = fahrer.get(i).getKommentar2();
			row[16] = fahrer.get(i).getFahrzeugID();
			row[17] = fahrer.get(i).getBearbeitet();
			model.addRow(row);
		}
		rowCount();
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

	public void scrollpane(JButton btnSave, JButton btnClear, JButton btn_Abbrechen, JButton btn_Anlegen,
			JButton btn_Bearbeiten, JButton btn_Loeschen, JButton btn_Dokumente, JButton btnExport) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(390, 50, f_width -400, f_height -158);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		scrollPane.setViewportView(tableFahrer);

		JLabel lblBackground_2 = new JLabel("");
		try {
			lblBackground_2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/res/Vorschlag1.jpg"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		addWindowStateListener(new WindowStateListener() {
//			public void windowStateChanged(final WindowEvent evt) {
//				if (evt.getNewState() == MAXIMIZED_BOTH) {
//					scrollPane.setBounds(390, 50, 1518, 926);
//					btnSave.setBounds(10, 986, 180, 23);
//					btnExport.setBounds(1727, 986, 180, 23);
//					btn_Abbrechen.setBounds(200, 986, 180, 23);
//					btn_Anlegen.setBounds(1498, 25, 93, 19);
//					btn_Bearbeiten.setBounds(1603, 25, 93, 19);
//					btn_Loeschen.setBounds(1707, 25, 93, 19);
//					btn_Dokumente.setBounds(1810, 25, 93, 19);
//					btnClear.setBounds(1470, 26, 19, 18);
//					tfSuche.setBounds(10, 26, 1460, 19);
//
//				} else {
//					
//					btnExport.setBounds(1132, 605, 180, 23);
//					btn_Anlegen.setBounds(904, 25, 93, 19);
//					btn_Bearbeiten.setBounds(1007, 25, 93, 19);
//					btn_Loeschen.setBounds(1110, 25, 93, 19);
//					btn_Dokumente.setBounds(1212, 25, 100, 19);				
//					scrollPane.setBounds(390, 50, 924, 544);
//					btnSave.setBounds(10, 605, 180, 23);
//					btn_Abbrechen.setBounds(200, 605, 180, 23);
//					btnClear.setBounds(876, 26, 19, 18);
//					tfSuche.setBounds(10, 26, 866, 19);
//				}
//			}
//		});
	}
	
	public static void rowCount() {
		zeilenAnzahl = tableFahrer.getRowCount();
		lblZeilenAnzahl.setText("Zeilenanzahl: " + zeilenAnzahl);
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
			throw new Exception("Fuellen sie bitte alle Felder aus!");
		}
	}

//	private static Message prepareMessage(Session session, String myAccount, String empfaenger) throws Exception {
//		Message message = new MimeMessage(session);
//
//		message.setFrom(new InternetAddress(myAccount));
//		message.setRecipient(Message.RecipientType.TO, new InternetAddress(empfaenger));
//		message.setSubject("Fuhrpark Management System");
//
//		// Multipart-Message ("Wrapper") erstellen
//		Multipart multipart = new MimeMultipart();
//		// Body-Part setzen:
//		BodyPart messageBodyPart = new MimeBodyPart();
//		// Textteil des Body-Parts
//		messageBodyPart.setText("Der Fahrer\nPersonalnummer: " + tfPersonalnummer.getText() + "\nVorname: "
//				+ tfVorname.getText() + "\nNachname: " + tfNachname.getText() + "\nFirmaNr: " + tfFirmaNr.getText()
//				+ "\nNiederlassungsnummer: " + tfNlNr.getText() + "\nwurde angelegt");
//		// Body-Part dem Multipart-Wrapper hinzufuegen
//		multipart.addBodyPart(messageBodyPart);
//		// Message fertigstellen, indem sie mit dem Multipart-Content ausgestattet wird
//		message.setContent(multipart);
//
//		return message;
//	}
//
//	public void sendEmail() {
//		Properties properties = new Properties();
//		properties.put("mail.smtp.ssl.trust", "true");
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.host", "smtp-mail.outlook.com");
//		properties.put("mail.smtp.port", "587");
//
//		String myAccount = "konzcars@konzmann.de";
//		String myPassword = "KnzCars#2022";
//		String empfaenger = "tolga.soylu@konzmann.de";
//		
//		PasswordAuthentication pw = new PasswordAuthentication(myAccount, myPassword);
//		
//		Authenticator myAuth = new Authenticator() 
//		{
//		    @Override
//		    protected PasswordAuthentication getPasswordAuthentication()
//		    {
//		        return new PasswordAuthentication("german", "german");
//		    }
//		};
//		
//		Authenticator.setDefault(myAuth);
//		
//		Session session = Session.getInstance(properties, new Authenticator() {
//			@Override
//			protected java.net.PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(myAccount, myPassword);
//			}
//		});
//		
//		
//
//		// Message-Objekt erzeugen und senden!
//		try {
//			Message message = prepareMessage(session, myAccount, empfaenger);
//			Transport.send(message); // E-Mail senden!
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}

	public void setAllFields(boolean wert) {
		tfPersonalnummer.setEnabled(wert);
		tfVorname.setEnabled(wert);
		tfNachname.setEnabled(wert);
		tfAktivKz.setEnabled(wert);
		tfFirmaNr.setEnabled(wert);
		cBoxFirmenname.setEnabled(wert);
		tfNLNr.setEnabled(wert);
		cBoxNLName.setEnabled(wert);
		chckbxFahrerlaubnis.setEnabled(wert);
		chckbxPruefung1.setEnabled(wert);
		tfKommentar1.setEnabled(wert);
		chckbxPruefung2.setEnabled(wert);
		tfKommentar2.setEnabled(wert);
		btnSave.setEnabled(wert);
		btnAbbrechen.setEnabled(wert);
	}

	public void clearAllFields() {
		tfSuche.setText("");
		tfPersonalnummer.setText("");
		tfVorname.setText("");
		tfNachname.setText("");
		tfAktivKz.setText("");
		tfFirmaNr.setText("");
		cBoxFirmenname.setSelectedIndex(0);
		tfNLNr.setText("");
		cBoxNLName.setSelectedIndex(0);
		chckbxFahrerlaubnis.setText("");
		chckbxPruefung1.setText("");
		tfKommentar1.setText("");
		chckbxPruefung2.setText("");
		tfKommentar2.setText("");
		wichtigTf(tfPersonalnummer);
		wichtigTf(tfNachname);
		wichtigTf(tfAktivKz);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNLNr);
	};

	public static ArrayList<String> fuelleArrFirmennameList(ArrayList<String> arr1List) {
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1 = "Select FirmaBez from Firma";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query1);
			while (rs.next()) {
				arrFirmenname.add(rs.getString("FirmaBez"));
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
		return arrFirmenname;
	}
	
	public static ArrayList<String> fuelleArrNLNameList(ArrayList<String> arr2List) {
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1 = "Select Ort from Niederlassung";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query1);
			while (rs.next()) {
				arrNLName.add(rs.getString("Ort"));
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
		return arrNLName;
	}
	
	public static ArrayList<String> fuelleArrayMaxIDList(ArrayList<String> arrayList) {
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query = "Select * from Fuhrpark";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				maxID_array.add(rs.getString("ID"));
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
		return arrayList;
	}

	public void export(JTable table, File file) {
		try {
			TableModel m = table.getModel();
			FileWriter fw = new FileWriter(file);
			for (int i = 0; i < m.getColumnCount(); i++) {
				fw.write(m.getColumnName(i) + "\t");
			}
			fw.write("\n");
			for (int i = 0; i < m.getRowCount(); i++) {
				for (int j = 0; j < m.getColumnCount(); j++) {
					fw.write(m.getValueAt(i, j).toString() + "\t");
				}
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void activeMode(JButton activeBtn, JButton deactiveBtn) {
		activeBtn.setBorder(BorderFactory.createLineBorder(Color.red,1));
		deactiveBtn.setBorder(BorderFactory.createLineBorder(Color.gray,1));
	}
}
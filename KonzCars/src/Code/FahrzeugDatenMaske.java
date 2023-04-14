
package Code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class FahrzeugDatenMaske extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	static Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	Dimension screenSize;

	ArrayList<Fahrzeug> vergleichsliste = new ArrayList<>();

	private static JTable tableFahrzeuge;
	private static int id;
	
	int f_width, f_height;

	private static ArrayList<String> arr = new ArrayList<String>();
	private static ArrayList<String> arrFirmenname = new ArrayList<String>();
	private static ArrayList<String> arrNLName = new ArrayList<String>();
	private static ArrayList<String> maxID_array = new ArrayList<String>();

	boolean erweitern = false;

	private static JTextField tfFIN;
	private static JTextField tfFirmaNr;
	private JComboBox<?> cBoxFirmenname;	
	private static JTextField tfNLNr;
	private JComboBox<?> cBoxNLName;
	private static JTextField tfFZG_Marke;
	private static JTextField tfFZG_Bezeichnung;
	private static JTextField tfamtl_Kennzeichen;
	private static JTextField tfFZG_Typ;
	private static JTextField tfDatum_Erfassung_km_Stand;
	private static JTextField tfAbmeldedatum;
	private static JComboBox<?> comboBox;
	private static JTextField tfFahrer2;
	private static JTextField tfFinanzstatus;
	private static JTextField tfBank_Leasinggesellschaft;
	private static JTextField tfVertragsNr;
	private static JTextField tfLeasingdauer_Monate;
	private static JTextField tfVerlaengerung_Monate;
	private static JTextField tfVertragsende;
	private static JTextField tfLeasingrate_zzgl_MwSt_Fahrzeug;
	private static JTextField tfBemerkung;
	private static JTextField tfRestwert_Leasingende;
	private static JTextField tfSoll_Laufleistung_Km;
	private static JTextField tfkm_Stand;
	private static JTextField tfAnschaffungswert__Netto;
	private static JTextField tfFinanzierungsrate;
	private static JTextField tfZulassungsart;
	private static JTextField tfMotorleistung_KW_P_2;
	private static JTextField tfSommerreifen;
	private static JTextField tfWinterreifen;
	private static JTextField tfKostenstelle;
	private static JTextField tfTyp;
	private static JTextField tfErstzulassung;
	private JTextField tfSuche;

	private static JCheckBox chkbxBelueftung;
	private static JCheckBox chkbxFahrerunterweisung;
	private static JCheckBox chkbxFoliert;
	private static JCheckBox chkbxUVV;
	private static JCheckBox chkbxWartung;
	private static JCheckBox chkbxWerkstatteinrichtung;
	private static JCheckBox chkbxPruefung1;
	private static JCheckBox chkbxPruefung2;
	private static JCheckBox chkbxFahrerlaubnis;
	
	JButton btnExport;

	private int checkPruefung1, checkPruefung2, checkFahrerlaubnis;

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
	
	public static JLabel lblGewaehlteZeile;
	public static int gewaehlteZeile;

	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FahrzeugDatenMaske frame = new FahrzeugDatenMaske();
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
	public FahrzeugDatenMaske() {
		setTitle("KFM Fahrzeuge");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//screensize
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		f_width = getWidth();
		f_height = getHeight();
		
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		f_width = getWidth();
		f_height = getHeight();

		tfSuche = new JTextField();
		tfSuche.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				filter(tfSuche.getText());
			}
		});

		tfSuche.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfSuche.setColumns(10);
		tfSuche.setBackground(SystemColor.menu);
		contentPane.add(tfSuche);

		btnSave = new JButton("Speichern");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setFocusPainted(false);
		btnSave.setBackground(SystemColor.inactiveCaption);

		btnSave.setBounds(10, f_height -100, 180, 23);
		contentPane.add(btnSave);
		
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

		JLabel lblFIN = new JLabel("FIN");
		lblFIN.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFIN.setForeground(Color.BLACK);
		lblFIN.setBounds(10, 72, 45, 13);
		contentPane.add(lblFIN);

		tfFIN = new JTextField();
		tfFIN.setForeground(Color.BLACK);
		tfFIN.setBackground(Color.WHITE);
		tfFIN.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFIN.setBounds(64, 69, 248, 19);
		contentPane.add(tfFIN);
		tfFIN.setColumns(10);

		JLabel lblFirmaNr = new JLabel("FirmaNr");
		lblFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFirmaNr.setForeground(Color.BLACK);
		lblFirmaNr.setBounds(10, 92, 64, 13);
		contentPane.add(lblFirmaNr);

		tfFirmaNr = new JTextField();
		tfFirmaNr.setForeground(Color.BLACK);
		tfFirmaNr.setBackground(Color.WHITE);
		tfFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFirmaNr.setBounds(64, 89, 40, 19);
		contentPane.add(tfFirmaNr);
		tfFirmaNr.setColumns(10);
		
		JLabel lblFirma = new JLabel("Firma");
		lblFirma.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFirma.setForeground(Color.BLACK);
		lblFirma.setBounds(114, 92, 64, 13);
		contentPane.add(lblFirma);

//		tfFirma = new JTextField();
//		tfFirma.setForeground(Color.BLACK);
//		tfFirma.setBackground(Color.WHITE);
//		tfFirma.setFont(new Font("Tahoma", Font.PLAIN, 11));
//		tfFirma.setBounds(154, 89, 158, 19);
//		contentPane.add(tfFirma);
//		tfFirma.setColumns(10);

		JLabel lblNLNr = new JLabel("NLNr");
		lblNLNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNLNr.setForeground(Color.BLACK);
		lblNLNr.setBounds(10, 112, 64, 13);
		contentPane.add(lblNLNr);

		tfNLNr = new JTextField();
		tfNLNr.setForeground(Color.BLACK);
		tfNLNr.setBackground(Color.WHITE);
		tfNLNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfNLNr.setColumns(10);
		tfNLNr.setBounds(64, 109, 40, 19);
		contentPane.add(tfNLNr);
		
		JLabel lblNL = new JLabel("NL");
		lblNL.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNL.setForeground(Color.BLACK);
		lblNL.setBounds(114, 112, 64, 13);
		contentPane.add(lblNL);

//		tfNL = new JTextField();
//		tfNL.setForeground(Color.BLACK);
//		tfNL.setBackground(Color.WHITE);
//		tfNL.setFont(new Font("Tahoma", Font.PLAIN, 11));
//		tfNL.setColumns(10);
//		tfNL.setBounds(154, 109, 158, 19);
//		contentPane.add(tfNL);

		JLabel lblFZG_Marke = new JLabel("Marke");
		lblFZG_Marke.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFZG_Marke.setForeground(Color.BLACK);
		lblFZG_Marke.setBounds(10, 132, 45, 13);
		contentPane.add(lblFZG_Marke);

		tfFZG_Marke = new JTextField();
		tfFZG_Marke.setForeground(Color.BLACK);
		tfFZG_Marke.setBackground(Color.WHITE);
		tfFZG_Marke.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFZG_Marke.setColumns(10);
		tfFZG_Marke.setBounds(64, 129, 102, 19);
		contentPane.add(tfFZG_Marke);

		JLabel lblFZG_Typ = new JLabel("Typ");
		lblFZG_Typ.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFZG_Typ.setForeground(Color.BLACK);
		lblFZG_Typ.setBounds(176, 132, 45, 13);
		contentPane.add(lblFZG_Typ);

		tfFZG_Bezeichnung = new JTextField();
		tfFZG_Bezeichnung.setForeground(Color.BLACK);
		tfFZG_Bezeichnung.setBackground(Color.WHITE);
		tfFZG_Bezeichnung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFZG_Bezeichnung.setColumns(10);
		tfFZG_Bezeichnung.setBounds(92, 154, 220, 19);
		contentPane.add(tfFZG_Bezeichnung);

		JLabel lblFZG_Bezeichnung = new JLabel("Bezeichnung");
		lblFZG_Bezeichnung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFZG_Bezeichnung.setForeground(Color.BLACK);
		lblFZG_Bezeichnung.setBounds(10, 157, 64, 13);
		contentPane.add(lblFZG_Bezeichnung);

		JLabel lblamtl_Kennzeichen = new JLabel("Kennzeichen");
		lblamtl_Kennzeichen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblamtl_Kennzeichen.setForeground(Color.BLACK);
		lblamtl_Kennzeichen.setBounds(10, 182, 64, 13);
		contentPane.add(lblamtl_Kennzeichen);

		tfamtl_Kennzeichen = new JTextField();
		tfamtl_Kennzeichen.setForeground(Color.BLACK);
		tfamtl_Kennzeichen.setBackground(Color.WHITE);
		tfamtl_Kennzeichen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfamtl_Kennzeichen.setColumns(10);
		tfamtl_Kennzeichen.setBounds(92, 179, 220, 19);
		contentPane.add(tfamtl_Kennzeichen);

		tfFZG_Typ = new JTextField();
		tfFZG_Typ.setForeground(Color.BLACK);
		tfFZG_Typ.setBackground(Color.WHITE);
		tfFZG_Typ.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFZG_Typ.setColumns(10);
		tfFZG_Typ.setBounds(210, 129, 102, 19);
		contentPane.add(tfFZG_Typ);

		JLabel lblErstzulassung = new JLabel("Erstzulassung");
		lblErstzulassung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErstzulassung.setForeground(Color.BLACK);
		lblErstzulassung.setBounds(10, 207, 80, 13);
		contentPane.add(lblErstzulassung);

		tfErstzulassung = new JTextField();
		tfErstzulassung.setBackground(Color.WHITE);
		tfErstzulassung.setForeground(Color.BLACK);
		tfErstzulassung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfErstzulassung.setColumns(10);
		tfErstzulassung.setBounds(92, 204, 120, 19);
		contentPane.add(tfErstzulassung);

		JLabel lblDatum_Erfassung_km_Stand = new JLabel("Kilometerstand erfasst am");
		lblDatum_Erfassung_km_Stand.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDatum_Erfassung_km_Stand.setForeground(Color.BLACK);
		lblDatum_Erfassung_km_Stand.setBounds(10, 532, 120, 13);
		contentPane.add(lblDatum_Erfassung_km_Stand);

		tfDatum_Erfassung_km_Stand = new JTextField();
		tfDatum_Erfassung_km_Stand.setBackground(Color.WHITE);
		tfDatum_Erfassung_km_Stand.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfDatum_Erfassung_km_Stand.setForeground(Color.BLACK);
		tfDatum_Erfassung_km_Stand.setColumns(10);
		tfDatum_Erfassung_km_Stand.setBounds(192, 529, 120, 19);
		contentPane.add(tfDatum_Erfassung_km_Stand);

		JLabel lblAbmeldedatum = new JLabel("Abmeldedatum");
		lblAbmeldedatum.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAbmeldedatum.setForeground(Color.BLACK);
		lblAbmeldedatum.setBounds(10, 232, 80, 13);
		contentPane.add(lblAbmeldedatum);

		tfAbmeldedatum = new JTextField();
		tfAbmeldedatum.setBackground(Color.WHITE);
		tfAbmeldedatum.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfAbmeldedatum.setForeground(Color.BLACK);
		tfAbmeldedatum.setColumns(10);
		tfAbmeldedatum.setBounds(92, 229, 120, 19);
		contentPane.add(tfAbmeldedatum);

		JLabel lblFahrer = new JLabel("Fahrer");
		lblFahrer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFahrer.setForeground(Color.BLACK);
		lblFahrer.setBounds(10, 257, 64, 13);
		contentPane.add(lblFahrer);

		JLabel lblFahrer2 = new JLabel("Fahrer2");
		lblFahrer2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFahrer2.setForeground(Color.BLACK);
		lblFahrer2.setBounds(10, 282, 64, 13);
		contentPane.add(lblFahrer2);

		tfFahrer2 = new JTextField();
		tfFahrer2.setBackground(Color.WHITE);
		tfFahrer2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFahrer2.setForeground(Color.BLACK);
		tfFahrer2.setColumns(10);
		tfFahrer2.setBounds(92, 279, 220, 19);
		contentPane.add(tfFahrer2);

		JLabel lblFinanzstatus = new JLabel("Finanzstatus");
		lblFinanzstatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFinanzstatus.setForeground(Color.BLACK);
		lblFinanzstatus.setBounds(10, 307, 64, 13);
		contentPane.add(lblFinanzstatus);

		tfFinanzstatus = new JTextField();
		tfFinanzstatus.setForeground(Color.BLACK);
		tfFinanzstatus.setBackground(Color.WHITE);
		tfFinanzstatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFinanzstatus.setColumns(10);
		tfFinanzstatus.setBounds(92, 304, 220, 19);
		contentPane.add(tfFinanzstatus);

		JLabel lblBank_Leasinggesellschaft = new JLabel("Bank/Leasinggesellschaft");
		lblBank_Leasinggesellschaft.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBank_Leasinggesellschaft.setForeground(Color.BLACK);
		lblBank_Leasinggesellschaft.setBounds(10, 332, 64, 13);
		contentPane.add(lblBank_Leasinggesellschaft);

		tfBank_Leasinggesellschaft = new JTextField();
		tfBank_Leasinggesellschaft.setForeground(Color.BLACK);
		tfBank_Leasinggesellschaft.setBackground(Color.WHITE);
		tfBank_Leasinggesellschaft.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfBank_Leasinggesellschaft.setColumns(10);
		tfBank_Leasinggesellschaft.setBounds(92, 329, 220, 19);
		contentPane.add(tfBank_Leasinggesellschaft);

		JLabel lblVertragsNr = new JLabel("VertragsNr");
		lblVertragsNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVertragsNr.setForeground(Color.BLACK);
		lblVertragsNr.setBounds(10, 357, 64, 13);
		contentPane.add(lblVertragsNr);

		tfVertragsNr = new JTextField();
		tfVertragsNr.setForeground(Color.BLACK);
		tfVertragsNr.setBackground(Color.WHITE);
		tfVertragsNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfVertragsNr.setColumns(10);
		tfVertragsNr.setBounds(92, 354, 220, 19);
		contentPane.add(tfVertragsNr);

		JLabel lblLeasingdauer_Monate = new JLabel("Leasingdauer");
		lblLeasingdauer_Monate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLeasingdauer_Monate.setForeground(Color.BLACK);
		lblLeasingdauer_Monate.setBounds(10, 382, 64, 13);
		contentPane.add(lblLeasingdauer_Monate);

		JLabel lblVerlaengerung_Monate = new JLabel("Verl√§ngerung");
		lblVerlaengerung_Monate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVerlaengerung_Monate.setForeground(Color.BLACK);
		lblVerlaengerung_Monate.setBounds(168, 382, 80, 13);
		contentPane.add(lblVerlaengerung_Monate);

		JLabel lblVertragsende = new JLabel("Vertragsende");
		lblVertragsende.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVertragsende.setForeground(Color.BLACK);
		lblVertragsende.setBounds(10, 407, 64, 13);
		contentPane.add(lblVertragsende);

		JLabel lblLeasingrate_zzgl_MwSt_Fahrzeug = new JLabel("Leasingrate zzgl. MwSt");
		lblLeasingrate_zzgl_MwSt_Fahrzeug.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLeasingrate_zzgl_MwSt_Fahrzeug.setForeground(Color.BLACK);
		lblLeasingrate_zzgl_MwSt_Fahrzeug.setBounds(10, 432, 120, 13);
		contentPane.add(lblLeasingrate_zzgl_MwSt_Fahrzeug);

		JLabel lblBemerkung = new JLabel("Bemerkung");
		lblBemerkung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBemerkung.setForeground(Color.BLACK);
		lblBemerkung.setBounds(333, 272, 64, 13);
		contentPane.add(lblBemerkung);

		tfLeasingdauer_Monate = new JTextField();
		tfLeasingdauer_Monate.setBackground(Color.WHITE);
		tfLeasingdauer_Monate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfLeasingdauer_Monate.setForeground(Color.BLACK);
		tfLeasingdauer_Monate.setColumns(10);
		tfLeasingdauer_Monate.setBounds(92, 379, 64, 19);
		contentPane.add(tfLeasingdauer_Monate);

		tfVerlaengerung_Monate = new JTextField();
		tfVerlaengerung_Monate.setBackground(Color.WHITE);
		tfVerlaengerung_Monate.setForeground(Color.BLACK);
		tfVerlaengerung_Monate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfVerlaengerung_Monate.setColumns(10);
		tfVerlaengerung_Monate.setBounds(248, 379, 64, 19);
		contentPane.add(tfVerlaengerung_Monate);

		tfVertragsende = new JTextField();
		tfVertragsende.setBackground(Color.WHITE);
		tfVertragsende.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfVertragsende.setForeground(Color.BLACK);
		tfVertragsende.setColumns(10);
		tfVertragsende.setBounds(92, 404, 220, 19);
		contentPane.add(tfVertragsende);

		tfLeasingrate_zzgl_MwSt_Fahrzeug = new JTextField();
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setForeground(Color.BLACK);
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setBackground(Color.WHITE);
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setColumns(10);
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setBounds(129, 429, 183, 19);
		contentPane.add(tfLeasingrate_zzgl_MwSt_Fahrzeug);

		tfBemerkung = new JTextField();
		tfBemerkung.setForeground(Color.BLACK);
		tfBemerkung.setBackground(Color.WHITE);
		tfBemerkung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfBemerkung.setColumns(10);
		tfBemerkung.setBounds(392, 269, 220, 19);
		contentPane.add(tfBemerkung);

		JLabel lblRestwert_Leasingende = new JLabel("Restwert Leasingende");
		lblRestwert_Leasingende.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRestwert_Leasingende.setForeground(Color.BLACK);
		lblRestwert_Leasingende.setBounds(10, 457, 102, 13);
		contentPane.add(lblRestwert_Leasingende);

		tfRestwert_Leasingende = new JTextField();
		tfRestwert_Leasingende.setBackground(Color.WHITE);
		tfRestwert_Leasingende.setForeground(Color.BLACK);
		tfRestwert_Leasingende.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfRestwert_Leasingende.setColumns(10);
		tfRestwert_Leasingende.setBounds(152, 454, 160, 19);
		contentPane.add(tfRestwert_Leasingende);

		tfSoll_Laufleistung_Km = new JTextField();
		tfSoll_Laufleistung_Km.setBackground(Color.WHITE);
		tfSoll_Laufleistung_Km.setForeground(Color.BLACK);
		tfSoll_Laufleistung_Km.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfSoll_Laufleistung_Km.setColumns(10);
		tfSoll_Laufleistung_Km.setBounds(152, 479, 160, 19);
		contentPane.add(tfSoll_Laufleistung_Km);

		JLabel lblSoll_Laufleistung_Km = new JLabel("Soll Laufleistung (km)");
		lblSoll_Laufleistung_Km.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSoll_Laufleistung_Km.setForeground(Color.BLACK);
		lblSoll_Laufleistung_Km.setBounds(10, 482, 102, 13);
		contentPane.add(lblSoll_Laufleistung_Km);

		JLabel lblkm_Stand = new JLabel("Kilometerstand");
		lblkm_Stand.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblkm_Stand.setForeground(Color.BLACK);
		lblkm_Stand.setBounds(10, 507, 102, 13);
		contentPane.add(lblkm_Stand);

		tfkm_Stand = new JTextField();
		tfkm_Stand.setBackground(Color.WHITE);
		tfkm_Stand.setForeground(Color.BLACK);
		tfkm_Stand.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfkm_Stand.setColumns(10);
		tfkm_Stand.setBounds(152, 504, 160, 19);
		contentPane.add(tfkm_Stand);

		JLabel lblAnschaffungswert__Netto = new JLabel("Anschaffungswert");
		lblAnschaffungswert__Netto.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAnschaffungswert__Netto.setForeground(Color.BLACK);
		lblAnschaffungswert__Netto.setBounds(333, 72, 120, 13);
		contentPane.add(lblAnschaffungswert__Netto);

		tfAnschaffungswert__Netto = new JTextField();
		tfAnschaffungswert__Netto.setBackground(Color.WHITE);
		tfAnschaffungswert__Netto.setForeground(Color.BLACK);
		tfAnschaffungswert__Netto.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfAnschaffungswert__Netto.setColumns(10);
		tfAnschaffungswert__Netto.setBounds(458, 69, 154, 19);
		contentPane.add(tfAnschaffungswert__Netto);

		JLabel lblFinanzierungsrate = new JLabel("Finanzierungsrate");
		lblFinanzierungsrate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFinanzierungsrate.setForeground(Color.BLACK);
		lblFinanzierungsrate.setBounds(333, 97, 120, 13);
		contentPane.add(lblFinanzierungsrate);

		tfFinanzierungsrate = new JTextField();
		tfFinanzierungsrate.setBackground(Color.WHITE);
		tfFinanzierungsrate.setForeground(Color.BLACK);
		tfFinanzierungsrate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFinanzierungsrate.setColumns(10);
		tfFinanzierungsrate.setBounds(458, 94, 154, 19);
		contentPane.add(tfFinanzierungsrate);

		JLabel lblWartung = new JLabel("Wartung");
		lblWartung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblWartung.setForeground(Color.BLACK);
		lblWartung.setBounds(10, 567, 45, 13);
		contentPane.add(lblWartung);

		chkbxWartung = new JCheckBox("");
		chkbxWartung.setOpaque(false);
		chkbxWartung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkbxWartung.setForeground(Color.BLACK);
		chkbxWartung.setBounds(60, 564, 20, 20);
		contentPane.add(chkbxWartung);

		tfZulassungsart = new JTextField();
		tfZulassungsart.setBackground(Color.WHITE);
		tfZulassungsart.setForeground(Color.BLACK);
		tfZulassungsart.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfZulassungsart.setColumns(10);
		tfZulassungsart.setBounds(458, 119, 154, 19);
		contentPane.add(tfZulassungsart);

		JLabel lblZulassungsart = new JLabel("Zulassungsart");
		lblZulassungsart.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblZulassungsart.setForeground(Color.BLACK);
		lblZulassungsart.setBounds(333, 122, 120, 13);
		contentPane.add(lblZulassungsart);

		JLabel lblMotorleistung_KW_P_2 = new JLabel("Motorleistung (ps)");
		lblMotorleistung_KW_P_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMotorleistung_KW_P_2.setForeground(Color.BLACK);
		lblMotorleistung_KW_P_2.setBounds(333, 147, 120, 13);
		contentPane.add(lblMotorleistung_KW_P_2);

		tfMotorleistung_KW_P_2 = new JTextField();
		tfMotorleistung_KW_P_2.setBackground(Color.WHITE);
		tfMotorleistung_KW_P_2.setForeground(Color.BLACK);
		tfMotorleistung_KW_P_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfMotorleistung_KW_P_2.setColumns(10);
		tfMotorleistung_KW_P_2.setBounds(458, 144, 64, 19);
		contentPane.add(tfMotorleistung_KW_P_2);

		JLabel lblSommerreifen = new JLabel("Sommerreifen");
		lblSommerreifen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSommerreifen.setForeground(Color.BLACK);
		lblSommerreifen.setBounds(333, 172, 120, 13);
		contentPane.add(lblSommerreifen);

		tfSommerreifen = new JTextField();
		tfSommerreifen.setBackground(Color.WHITE);
		tfSommerreifen.setForeground(Color.BLACK);
		tfSommerreifen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfSommerreifen.setColumns(10);
		tfSommerreifen.setBounds(458, 169, 154, 19);
		contentPane.add(tfSommerreifen);

		JLabel lblWinterreifen = new JLabel("Winterreifen");
		lblWinterreifen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblWinterreifen.setForeground(Color.BLACK);
		lblWinterreifen.setBounds(333, 197, 120, 13);
		contentPane.add(lblWinterreifen);

		tfWinterreifen = new JTextField();
		tfWinterreifen.setBackground(Color.WHITE);
		tfWinterreifen.setForeground(Color.BLACK);
		tfWinterreifen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfWinterreifen.setColumns(10);
		tfWinterreifen.setBounds(458, 194, 154, 19);
		contentPane.add(tfWinterreifen);

		JLabel lblKostenstelle = new JLabel("Kostenstelle");
		lblKostenstelle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblKostenstelle.setForeground(Color.BLACK);
		lblKostenstelle.setBounds(333, 222, 120, 13);
		contentPane.add(lblKostenstelle);

		tfKostenstelle = new JTextField();
		tfKostenstelle.setBackground(Color.WHITE);
		tfKostenstelle.setForeground(Color.BLACK);
		tfKostenstelle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfKostenstelle.setColumns(10);
		tfKostenstelle.setBounds(458, 219, 154, 19);
		contentPane.add(tfKostenstelle);

		chkbxFoliert = new JCheckBox("");
		chkbxFoliert.setOpaque(false);
		chkbxFoliert.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkbxFoliert.setForeground(Color.BLACK);
		chkbxFoliert.setBounds(135, 564, 20, 20);
		contentPane.add(chkbxFoliert);

		JLabel lblFoliert = new JLabel("Foliert");
		lblFoliert.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFoliert.setForeground(Color.BLACK);
		lblFoliert.setBounds(95, 567, 35, 13);
		contentPane.add(lblFoliert);

		JLabel lblTyp = new JLabel("Regaltyp");
		lblTyp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTyp.setForeground(Color.BLACK);
		lblTyp.setBounds(333, 247, 120, 13);
		contentPane.add(lblTyp);

		tfTyp = new JTextField();
		tfTyp.setBackground(Color.WHITE);
		tfTyp.setForeground(Color.BLACK);
		tfTyp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfTyp.setColumns(10);
		tfTyp.setBounds(458, 244, 154, 19);
		contentPane.add(tfTyp);

		chkbxUVV = new JCheckBox("");
		chkbxUVV.setOpaque(false);
		chkbxUVV.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkbxUVV.setForeground(Color.BLACK);
		chkbxUVV.setBounds(195, 564, 20, 20);
		contentPane.add(chkbxUVV);

		JLabel lblUVV = new JLabel("UVV");
		lblUVV.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUVV.setForeground(Color.BLACK);
		lblUVV.setBounds(170, 567, 20, 13);
		contentPane.add(lblUVV);

		chkbxFahrerunterweisung = new JCheckBox("");
		chkbxFahrerunterweisung.setOpaque(false);
		chkbxFahrerunterweisung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkbxFahrerunterweisung.setForeground(Color.BLACK);
		chkbxFahrerunterweisung.setBounds(305, 564, 20, 20);
		contentPane.add(chkbxFahrerunterweisung);

		JLabel lblFahrerunterweisung = new JLabel("Fahrerunterw.");
		lblFahrerunterweisung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFahrerunterweisung.setForeground(Color.BLACK);
		lblFahrerunterweisung.setBounds(230, 567, 70, 13);
		contentPane.add(lblFahrerunterweisung);

		chkbxWerkstatteinrichtung = new JCheckBox("");
		chkbxWerkstatteinrichtung.setOpaque(false);
		chkbxWerkstatteinrichtung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkbxWerkstatteinrichtung.setForeground(Color.BLACK);
		chkbxWerkstatteinrichtung.setBounds(415, 564, 20, 20);
		contentPane.add(chkbxWerkstatteinrichtung);

		JLabel lblWerkstatteinrichtung = new JLabel("Werkstatteinr.");
		lblWerkstatteinrichtung.setBackground(new Color(240, 240, 240));
		lblWerkstatteinrichtung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblWerkstatteinrichtung.setForeground(Color.BLACK);
		lblWerkstatteinrichtung.setBounds(340, 567, 70, 13);
		contentPane.add(lblWerkstatteinrichtung);

		chkbxBelueftung = new JCheckBox("");
		chkbxBelueftung.setOpaque(false);
		chkbxBelueftung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkbxBelueftung.setForeground(Color.BLACK);
		chkbxBelueftung.setBounds(505, 564, 20, 20);
		contentPane.add(chkbxBelueftung);

		JLabel lblBelueftung = new JLabel("Belueftung");
		lblBelueftung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBelueftung.setForeground(Color.BLACK);
		lblBelueftung.setBounds(450, 567, 50, 13);
		contentPane.add(lblBelueftung);

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

		JLabel lblPruefung1 = new JLabel("Pruefung1");
		lblPruefung1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPruefung1.setForeground(Color.BLACK);
		lblPruefung1.setBounds(343, 300, 50, 13);
		contentPane.add(lblPruefung1);

		chkbxPruefung1 = new JCheckBox("");
		chkbxPruefung1.setOpaque(false);
		chkbxPruefung1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkbxPruefung1.setForeground(Color.BLACK);
		chkbxPruefung1.setBounds(410, 297, 20, 20);
		contentPane.add(chkbxPruefung1);

		JLabel lblPruefung2 = new JLabel("Pruefung2");
		lblPruefung2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPruefung2.setForeground(Color.BLACK);
		lblPruefung2.setBounds(343, 320, 50, 13);
		contentPane.add(lblPruefung2);

		chkbxPruefung2 = new JCheckBox("");
		chkbxPruefung2.setOpaque(false);
		chkbxPruefung2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkbxPruefung2.setForeground(Color.BLACK);
		chkbxPruefung2.setBounds(410, 317, 20, 20);
		contentPane.add(chkbxPruefung2);

		JLabel lblFahrerlaubnis = new JLabel("Fahrerlaubnis");
		lblFahrerlaubnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFahrerlaubnis.setForeground(Color.BLACK);
		lblFahrerlaubnis.setBounds(503, 300, 80, 13);
		contentPane.add(lblFahrerlaubnis);

		chkbxFahrerlaubnis = new JCheckBox("");
		chkbxFahrerlaubnis.setOpaque(false);
		chkbxFahrerlaubnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkbxFahrerlaubnis.setForeground(Color.BLACK);
		chkbxFahrerlaubnis.setBounds(590, 297, 20, 20);
		contentPane.add(chkbxFahrerlaubnis);
		
		lblZeilenAnzahl = new JLabel("Zeilenanzahl: ");
		lblZeilenAnzahl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblZeilenAnzahl.setBounds(620, f_height -100, f_width -400, 23);
		contentPane.add(lblZeilenAnzahl);
		
		lblGewaehlteZeile = new JLabel("Ausgew‰hlte Zeile: ");
		lblGewaehlteZeile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGewaehlteZeile.setBounds(660, f_height -100, f_width -400, 23);
		contentPane.add(lblGewaehlteZeile);
		
		fuelleArrayList(arr);
		String[] a = new String[arr.size() + 2];

		a[0] = "";
		a[1] = "Poolfahrzeug";

		for (int i = 2; i < a.length; i++) {
			a[i] = arr.get(i - 2);
		}
		comboBox = new JComboBox<Object>(a);
		comboBox.setBounds(92, 254, 220, 19);
		contentPane.add(comboBox);
		
		//combobox Firmenname
		fuelleArrFirmennameList(arrFirmenname);
		String[] a1 = new String[arrFirmenname.size() + 1];
		
		a1[0] = "";

		for (int i = 1; i < a1.length; i++) {
			a1[i] = arrFirmenname.get(i - 1);
		}
		cBoxFirmenname = new JComboBox<Object>(a1);
		cBoxFirmenname.setBounds(154, 89, 158, 19);
		contentPane.add(cBoxFirmenname);
				
		//combobox Niederlassungsname
		fuelleArrNLNameList(arrNLName);
		String[] a2 = new String[arrNLName.size() + 1];

		a2[0] = "";

		for (int j = 1; j < a2.length; j++) {
			a2[j] = arrNLName.get(j - 1);
		}
		cBoxNLName = new JComboBox<Object>(a2);
		cBoxNLName.setBounds(154, 109, 158, 19);
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

		tableFahrzeuge = new JTable();
		tableFahrzeuge.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFahrzeuge.setBorder(null);
		tableFahrzeuge.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "FIN", "FirmaNr", "Firmenname",
				"NLNr", "NLName", "FZG_Marke", "FZG_Typ", "FZG_Bezeichnung", "amtl_Kennzeichen", "Erstzulassung", "Abmeldedatum",
				"Fahrer", "Fahrer2", "Finanzstatus", "Bank_Leasinggesellschaft", "VertragsNr", "Leasingdauer_Monate",
				"Verlaengerung_Monate", "Leasingrate_zzgl_MwSt_Fahrzeug", "Vertragsende", "Bemerkung",
				"Restwert_Leasingende", "Soll_Laufleistung_Km", "km_Stand", "Datum_Erfassung_km_Stand",
				"Anschaffungswert__Netto", "Finanzierungsrate", "Wartung", "Zulassungsart", "Motorleistung_KW_P_2",
				"Sommerreifen", "Winterreifen", "Kostenstelle", "Foliert", "Typ", "UVV", "Fahrerunterweisung",
				"Werkstatteinrichtung", "Belueftung", "Pruefung1", "Pruefung2", "Fahrerlaubnis", "Bearbeitet" }));

		tableFahrzeuge.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = tableFahrzeuge.getColumnModel();
		for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
			if(columnIndex == 10 | columnIndex == 20 | columnIndex == 25 | columnIndex == 30) {
				tableFahrzeuge.getColumnModel().getColumn(columnIndex).setPreferredWidth(130);
			}
			else if(columnIndex == 3) {
				tableFahrzeuge.getColumnModel().getColumn(columnIndex).setPreferredWidth(220);
			}
			else {
				tableFahrzeuge.getColumnModel().getColumn(columnIndex).setPreferredWidth(100);
			}
		}
		tableFahrzeuge.setAutoCreateRowSorter(true);
		
		filter("");

		wichtigTf(tfFIN);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNLNr);
		wichtigTf(tfFZG_Marke);
		wichtigTf(tfFZG_Typ);
		wichtigTf(tfFZG_Bezeichnung);
		wichtigTf(tfamtl_Kennzeichen);
		wichtigTf(tfErstzulassung);
		wichtigTf(tfFinanzstatus);
		wichtigTf(tfkm_Stand);
		wichtigTf(tfDatum_Erfassung_km_Stand);
		wichtigTf(tfZulassungsart);
		wichtigTf(tfMotorleistung_KW_P_2);
		wichtigTf(tfKostenstelle);
		
		btn_Anlegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modus = "anlegen";
				activeMode(btn_Anlegen, btn_Bearbeiten);
				setAllFields(true);
				
				wichtigTf(tfFIN);
				wichtigTf(tfFirmaNr);
				wichtigTf(tfNLNr);
				wichtigTf(tfFZG_Marke);
				wichtigTf(tfFZG_Typ);
				wichtigTf(tfFZG_Bezeichnung);
				wichtigTf(tfamtl_Kennzeichen);
				wichtigTf(tfErstzulassung);
				wichtigTf(tfFinanzstatus);
				wichtigTf(tfkm_Stand);
				wichtigTf(tfDatum_Erfassung_km_Stand);
				wichtigTf(tfZulassungsart);
				wichtigTf(tfMotorleistung_KW_P_2);
				wichtigTf(tfKostenstelle);
				
				cBoxFirmenname.setSelectedIndex(0);
				cBoxNLName.setSelectedIndex(0);
				comboBox.setSelectedIndex(0);
				
				tfFahrer2.setText("");
				tfBank_Leasinggesellschaft.setText("");
				tfVertragsNr.setText("");
				tfLeasingdauer_Monate.setText("");
				tfVerlaengerung_Monate.setText("");
				tfLeasingrate_zzgl_MwSt_Fahrzeug.setText("");
				tfVertragsende.setText("");
				tfBemerkung.setText("");
				tfAbmeldedatum.setText("");
				tfRestwert_Leasingende.setText("");
				tfSoll_Laufleistung_Km.setText("");
				tfAnschaffungswert__Netto.setText("");
				tfFinanzierungsrate.setText("");
				tfSommerreifen.setText("");
				tfWinterreifen.setText("");
				tfTyp.setText("");
				
				chkbxWartung.setSelected(false);
				chkbxFoliert.setSelected(false);
				chkbxUVV.setSelected(false);
				chkbxFahrerunterweisung.setSelected(false);
				chkbxWerkstatteinrichtung.setSelected(false);
				chkbxBelueftung.setSelected(false);
				chkbxPruefung1.setSelected(false);
				chkbxPruefung2.setSelected(false);
				chkbxFahrerlaubnis.setSelected(false);
			}
		});

		btn_Bearbeiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modus = "bearbeiten";
				activeMode(btn_Bearbeiten, btn_Anlegen);
				setAllFields(true);
				tfFIN.setEnabled(false);
			}
		});

		btn_Loeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_Anlegen.setBorder(BorderFactory.createLineBorder(Color.gray,1));
				btn_Bearbeiten.setBorder(BorderFactory.createLineBorder(Color.gray,1));
				Object[] options = { "Ja", "Nein" };
				int n = JOptionPane.showOptionDialog(null, "Sind Sie sich sicher?", "Datensatz lˆschen",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[1]);
				if (n == 0) {
					try {
						int i = tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
						TableModel model = tableFahrzeuge.getModel();
						id = (int) model.getValueAt(i, 0);

						String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
						conn = DriverManager.getConnection(url);
						String query = "DELETE FROM Fuhrpark WHERE ID=" + id;
						String query2 = "UPDATE Fahrer SET FahrzeugID = '' where FahrzeugID =" + id;
						PreparedStatement pst = conn.prepareStatement(query);
						PreparedStatement pst2 = conn.prepareStatement(query2);

						pst.executeUpdate();
						pst2.executeUpdate();

						show_aktualisiertes_fahrzeug();
						vergleichsliste = fahrzeug();
						JOptionPane.showMessageDialog(null, "Datensatz wurde gelˆscht!");
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
				id_Uebergabe_fahrzeug = tableFahrzeuge.getModel()
						.getValueAt(tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow()), 0)
						.toString();
				if (tableFahrzeuge.getModel().getValueAt(
						tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow()), 12) != null) {
					id_Uebergabe_fahrer = tableFahrzeuge.getModel()
							.getValueAt(tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow()), 12)
							.toString();
				}
				herkunft_ueber_fahrzeug = true;
				DokumenteMaske frame = new DokumenteMaske();
				frame.setResizable(false);
				frame.setVisible(true);
			}
		});
		
		addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	setExtendedState(JFrame.MAXIMIZED_BOTH);
                f_width = getWidth();
                f_height = getHeight();
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

		btnExport = new JButton("Exportieren");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnExport) {
					JFileChooser fchoose = new JFileChooser();
					int option = fchoose.showSaveDialog(FahrzeugDatenMaske.this);
					if (option == JFileChooser.APPROVE_OPTION) {
						String name = fchoose.getSelectedFile().getName();
						String path = fchoose.getSelectedFile().getParentFile().getPath();
						String file = path + "\\" + name + ".xls";
						export(tableFahrzeuge, new File(file));
					}
				}
			}
		});
		btnExport.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnExport.setFocusPainted(false);
		btnExport.setBackground(SystemColor.inactiveCaption);
		btnExport.setBounds(f_width -192, f_height -100, 180, 23);
		contentPane.add(btnExport);

		scrollpane(btnSave, btnClear, btnAbbrechen, btn_Anlegen, btn_Bearbeiten, btn_Loeschen, btn_Dokumente,
				btnExport /*
							 * ,lblWartung, lblFoliert, lblUVV, lblFahrerunterweisung,
							 * lblWerkstatteinrichtung, lblBelueftung
							 */);

		tableFahrzeuge.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int i = tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
				TableModel model = tableFahrzeuge.getModel();
				
//				clearAllFields();
				
				id = (int) model.getValueAt(i, 0);
				
				if (model.getValueAt(i, 1) != null) {
					tfFIN.setText(model.getValueAt(i, 1).toString());
				}
				if (model.getValueAt(i, 2) != null) {
					tfFirmaNr.setText(model.getValueAt(i, 2).toString());
				}
				if (model.getValueAt(i, 3) != null) {
					String s1;
					String s2 = model.getValueAt(i, 3).toString();
					for (int j = 1; j < cBoxFirmenname.getItemCount(); j++) {
						s1 = cBoxFirmenname.getItemAt(j).toString();
						if (s1.equals(s2)) {
							cBoxFirmenname.setSelectedIndex(j);
						}
					}
					if (model.getValueAt(i, 3).toString().equals("")) {
						cBoxFirmenname.setSelectedIndex(0);
					}
				}
				if (model.getValueAt(i, 4) != null) {
					tfNLNr.setText(model.getValueAt(i, 4).toString());
				}
				if (model.getValueAt(i, 5) != null) {
					String s1;
					String s2 = model.getValueAt(i, 5).toString();
					for (int j = 1; j < cBoxNLName.getItemCount(); j++) {
						s1 = cBoxNLName.getItemAt(j).toString();
						if (s1.equals(s2)) {
							cBoxNLName.setSelectedIndex(j);
						}
					}
					if (model.getValueAt(i, 5).toString().equals("")) {
						cBoxNLName.setSelectedIndex(0);
					}
				}
				if (model.getValueAt(i, 6) != null) {
					tfFZG_Marke.setText(model.getValueAt(i, 6).toString());
				}
				if (model.getValueAt(i, 7) != null) {
					tfFZG_Typ.setText(model.getValueAt(i, 7).toString());
				}
				if (model.getValueAt(i, 8) != null) {
					tfFZG_Bezeichnung.setText(model.getValueAt(i, 8).toString());
				}
				if (model.getValueAt(i, 9) != null) {
					tfamtl_Kennzeichen.setText(model.getValueAt(i, 9).toString());
				}
				if (model.getValueAt(i, 10) != null) {
					tfErstzulassung.setText(model.getValueAt(i, 10).toString());
				}
				if (model.getValueAt(i, 11) != null) {
					tfAbmeldedatum.setText(model.getValueAt(i, 11).toString());
				}
				if (model.getValueAt(i, 12) != null) {
					String item[] = new String[1];
					String s1;
					String s2 = model.getValueAt(i, 12).toString();
					for (int j = 1; j < comboBox.getItemCount(); j++) {
						s1 = comboBox.getItemAt(j).toString();
						StringTokenizer strings = new StringTokenizer(s1, ",");
						item[0] = strings.nextElement().toString();
						if (item[0].equals(s2)) {
							comboBox.setSelectedIndex(j);
						}
					}
					if (model.getValueAt(i, 12).toString().equals("")) {
						comboBox.setSelectedIndex(0);
					}
				}
				if (model.getValueAt(i, 13) != null) {
					tfFahrer2.setText(model.getValueAt(i, 13).toString());
				}
				if (model.getValueAt(i, 14) != null) {
					tfFinanzstatus.setText(model.getValueAt(i, 14).toString());
				}
				if (model.getValueAt(i, 15) != null) {
					tfBank_Leasinggesellschaft.setText(model.getValueAt(i, 15).toString());
				}
				if (model.getValueAt(i, 16) != null) {
					tfVertragsNr.setText(model.getValueAt(i, 16).toString());
				}
				if (model.getValueAt(i, 17) != null) {
					tfLeasingdauer_Monate.setText(model.getValueAt(i, 17).toString());
				}
				if (model.getValueAt(i, 18) != null) {
					tfVerlaengerung_Monate.setText(model.getValueAt(i, 18).toString());
				}
				if (model.getValueAt(i, 19) != null) {
					tfLeasingrate_zzgl_MwSt_Fahrzeug.setText(model.getValueAt(i, 19).toString());
				}
				if (model.getValueAt(i, 20) != null) {
					tfVertragsende.setText(model.getValueAt(i, 20).toString());
				}
				if (model.getValueAt(i, 21) != null) {
					tfBemerkung.setText(model.getValueAt(i, 21).toString());
				}
				if (model.getValueAt(i, 22) != null) {
					tfRestwert_Leasingende.setText(model.getValueAt(i, 22).toString());
				}
				if (model.getValueAt(i, 23) != null) {
					tfSoll_Laufleistung_Km.setText(model.getValueAt(i, 23).toString());
				}
				if (model.getValueAt(i, 24) != null) {
					tfkm_Stand.setText(model.getValueAt(i, 24).toString());
				}
				if (model.getValueAt(i, 25) != null) {
					tfDatum_Erfassung_km_Stand.setText(model.getValueAt(i, 25).toString());
				}
				if (model.getValueAt(i, 26) != null) {
					tfAnschaffungswert__Netto.setText(model.getValueAt(i, 26).toString());
				}
				if (model.getValueAt(i, 27) != null) {
					tfFinanzierungsrate.setText(model.getValueAt(i, 27).toString());
				}
				if (model.getValueAt(i, 28) != null) {
					String wartung = model.getValueAt(i, 28).toString();
					switch (wartung) {
					case "1":
						chkbxWartung.setSelected(true);
						break;
					case "0":
						chkbxWartung.setSelected(false);
						break;
					}
				}
				if (model.getValueAt(i, 29) != null) {
					tfZulassungsart.setText(model.getValueAt(i, 29).toString());
				}
				if (model.getValueAt(i, 30) != null) {
					tfMotorleistung_KW_P_2.setText(model.getValueAt(i, 30).toString());
				}
				if (model.getValueAt(i, 31) != null) {
					tfSommerreifen.setText(model.getValueAt(i, 31).toString());
				}
				if (model.getValueAt(i, 32) != null) {
					tfWinterreifen.setText(model.getValueAt(i, 32).toString());
				}
				if (model.getValueAt(i, 33) != null) {
					tfKostenstelle.setText(model.getValueAt(i, 33).toString());
				}
				if (model.getValueAt(i, 34) != null) {
					String foliert = model.getValueAt(i, 34).toString();
					switch (foliert) {
					case "1":
						chkbxFoliert.setSelected(true);
						break;
					case "0":
						chkbxFoliert.setSelected(false);
						break;
					}
				}
				if (model.getValueAt(i, 35) != null) {
					tfTyp.setText(model.getValueAt(i, 35).toString());
				}
				if (model.getValueAt(i, 36) != null) {
					String uvv = model.getValueAt(i, 36).toString();
					switch (uvv) {
					case "1":
						chkbxUVV.setSelected(true);
						break;
					case "0":
						chkbxUVV.setSelected(false);
						break;
					}
				}
				if (model.getValueAt(i, 37) != null) {
					String fahrerunterweisung = model.getValueAt(i, 37).toString();
					switch (fahrerunterweisung) {
					case "1":
						chkbxFahrerunterweisung.setSelected(true);
						break;
					case "0":
						chkbxFahrerunterweisung.setSelected(false);
						break;
					}
				}
				if (model.getValueAt(i, 38) != null) {
					String werkstatteinrichtung = model.getValueAt(i, 38).toString();
					switch (werkstatteinrichtung) {
					case "1":
						chkbxWerkstatteinrichtung.setSelected(true);
						break;
					case "0":
						chkbxWerkstatteinrichtung.setSelected(false);
						break;
					}
				}
				if (model.getValueAt(i, 39) != null) {
					String belueftung = model.getValueAt(i, 39).toString();
					switch (belueftung) {
					case "1":
						chkbxBelueftung.setSelected(true);
						break;
					case "0":
						chkbxBelueftung.setSelected(false);
						break;
					}
				}
				if (model.getValueAt(i, 40) != null) {
					String pruefung1 = model.getValueAt(i, 40).toString();
					switch (pruefung1) {
					case "1":
						chkbxPruefung1.setSelected(true);
						break;
					case "0":
						chkbxPruefung1.setSelected(false);
						break;
					}
				}
				if (model.getValueAt(i, 41) != null) {
					String pruefung2 = model.getValueAt(i, 41).toString();
					switch (pruefung2) {
					case "1":
						chkbxPruefung2.setSelected(true);
						break;
					case "0":
						chkbxPruefung2.setSelected(false);
						break;
					}
				}
				if (model.getValueAt(i, 42) != null) {
					String fahrerlaubnis = model.getValueAt(i, 42).toString();
					switch (fahrerlaubnis) {
					case "1":
						chkbxFahrerlaubnis.setSelected(true);
						break;
					case "0":
						chkbxFahrerlaubnis.setSelected(false);
						break;
					}
				}
			}
		});

		vergleichsliste = fahrzeug();
		show_fahrzeug();

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_Anlegen.setBorder(BorderFactory.createLineBorder(Color.gray,1));
				btn_Bearbeiten.setBorder(BorderFactory.createLineBorder(Color.gray,1));

//BEARBEITEN
				if (modus == "bearbeiten") {
					int i = tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
					int aktualisiert;
					aktualisiert = vergleichsliste.get(i).getBearbeitet();
					ArrayList<Fahrzeug> fahrzeugliste = fahrzeug();
					if (aktualisiert == fahrzeugliste.get(i).getBearbeitet()) {
						try {

							emptyTf(tfFIN);
							emptyTf(tfFirmaNr);
							emptyTf(tfNLNr);
							emptyTf(tfFZG_Marke);
							emptyTf(tfFZG_Typ);
							emptyTf(tfFZG_Bezeichnung);
							emptyTf(tfamtl_Kennzeichen);
							emptyTf(tfErstzulassung);
							emptyTf(tfAbmeldedatum);
							emptyTf(tfFinanzstatus);
							emptyTf(tfkm_Stand);
							emptyTf(tfDatum_Erfassung_km_Stand);
							emptyTf(tfZulassungsart);
							emptyTf(tfMotorleistung_KW_P_2);
							emptyTf(tfKostenstelle);

							String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
							conn = DriverManager.getConnection(url);
							String qry = "UPDATE Fuhrpark SET FIN=?, FirmaNr=?, Firmenname=?, NLNr=?, NLName=?, FZG_Marke=?, FZG_Typ=?, FZG_Bezeichnung=?, amtl_Kennzeichen=?, Erstzulassung=?, Abmeldedatum=?,"
									+ "Fahrer=?, Fahrer2=?, Finanzstatus=?, Bank_Leasinggesellschaft=?, VertragsNr=?, Leasingdauer_Monate=?, Verlaengerung_Monate=?, Leasingrate_zzgl_MwSt_Fahrzeug=?,"
									+ "Vertragsende=?, Bemerkung=?, Restwert_Leasingende=?, Soll_Laufleistung_Km=?, km_Stand=?, Datum_Erfassung_km_Stand=?, Anschaffungswert__Netto=?, "
									+ "Finanzierungsrate=?, Wartung=?, Zulassungsart=?, Motorleistung_KW_P_2=?, Sommerreifen=?, Winterreifen=?, Kostenstelle=?,"
									+ "Foliert=?, Typ=?, UVV=?, Fahrerunterweisung=?, Werkstatteinrichtung=?, Belueftung=?, Pruefung1=?, Pruefung2=?, Fahrerlaubnis=?, Bearbeitet=? WHERE ID="
									+ id;

							Object object = comboBox.getSelectedItem();
							String selectedItem = object.toString();
							String item[] = new String[1];
							
							Object object1 = cBoxFirmenname.getSelectedItem();
							String selectedItem1 = object1.toString();
							String item1[] = new String[1];
							
							Object object2 = cBoxNLName.getSelectedItem();
							String selectedItem2 = object2.toString();
							String item2[] = new String[1];
							
							if (!selectedItem.equals("") && !selectedItem.equals("Poolfahrzeug")) {
								StringTokenizer strings = new StringTokenizer(selectedItem, ",");

								item[0] = strings.nextElement().toString();

//								String qry2 = "UPDATE Fuhrpark SET Fahrer = '' where Fahrer = " + item[0];
								String qry3 = "UPDATE Fahrer set FahrzeugID = " + id + " where ID =" + item[0];
								String qry4 = "UPDATE Fuhrpark SET Fahrer = " + item[0] + " where ID = " + id;

//								PreparedStatement pst2 = conn.prepareStatement(qry2);
								PreparedStatement pst3 = conn.prepareStatement(qry3);
								PreparedStatement pst4 = conn.prepareStatement(qry4);

//								pst2.executeUpdate();
								pst3.executeUpdate();
								pst4.executeUpdate();
							}
							
							if (!selectedItem1.equals("")) {
								StringTokenizer strings = new StringTokenizer(selectedItem1, ",");

								item1[0] = strings.nextElement().toString();
							}
							
							if (!selectedItem2.equals("")) {
								StringTokenizer strings = new StringTokenizer(selectedItem2, ",");

								item2[0] = strings.nextElement().toString();
							}
							
							PreparedStatement pst = conn.prepareStatement(qry);

							pst.setString(1, tfFIN.getText());
							pst.setString(2, tfFirmaNr.getText());
							
							try {
								if (selectedItem1.equals("")) {
									pst.setString(3, "");
								} else {
									pst.setString(3, item1[0]);
								}
							} catch (Exception e2) {
							}
							
							pst.setString(4, tfNLNr.getText());
							
							try {
								if (selectedItem2.equals("")) {
									pst.setString(5, "");
								} else {
									pst.setString(5, item2[0]);
								}
							} catch (Exception e2) {
							}
							
							pst.setString(6, tfFZG_Marke.getText());
							pst.setString(7, tfFZG_Typ.getText());
							pst.setString(8, tfFZG_Bezeichnung.getText());
							pst.setString(9, tfamtl_Kennzeichen.getText());
							pst.setString(10, tfErstzulassung.getText());
							pst.setString(11, tfAbmeldedatum.getText());
							
							try {
								if (selectedItem.equals("")) {
									pst.setString(12, "");
								} else {
									pst.setString(12, item[0]);
								}
								if (selectedItem.equals("Poolfahrzeug")) {
									pst.setString(12, "Poolfahrzeug");
								} else {
									pst.setString(12, item[0]);
								}
							} catch (Exception e2) {
							}
							
							pst.setString(13, tfFahrer2.getText());
							pst.setString(14, tfFinanzstatus.getText());
							pst.setString(15, tfBank_Leasinggesellschaft.getText());
							pst.setString(16, tfVertragsNr.getText());
							pst.setString(17, tfLeasingdauer_Monate.getText());
							pst.setString(18, tfVerlaengerung_Monate.getText());
							pst.setString(19, tfLeasingrate_zzgl_MwSt_Fahrzeug.getText());
							pst.setString(20, tfVertragsende.getText());
							pst.setString(21, tfBemerkung.getText());
							pst.setString(22, tfRestwert_Leasingende.getText());
							pst.setString(23, tfSoll_Laufleistung_Km.getText());
							pst.setString(24, tfkm_Stand.getText());
							pst.setString(25, tfDatum_Erfassung_km_Stand.getText());
							pst.setString(26, tfAnschaffungswert__Netto.getText());
							pst.setString(27, tfFinanzierungsrate.getText());
							boolean wartung = false;
							if (chkbxWartung.isSelected()) {
								wartung = true;
							}
							if (wartung) {
								pst.setString(28, "1");
							} else {
								pst.setString(28, "0");
							}
							pst.setString(29, tfZulassungsart.getText());
							pst.setString(30, tfMotorleistung_KW_P_2.getText());
							pst.setString(31, tfSommerreifen.getText());
							pst.setString(32, tfWinterreifen.getText());
							pst.setString(33, tfKostenstelle.getText());
							boolean foliert = false;
							if (chkbxFoliert.isSelected()) {
								foliert = true;
							}
							if (foliert) {
								pst.setString(34, "1");
							} else {
								pst.setString(34, "0");
							}
							pst.setString(35, tfTyp.getText());
							boolean uvv = false;
							if (chkbxUVV.isSelected()) {
								uvv = true;
							}
							if (uvv) {
								pst.setString(36, "1");
							} else {
								pst.setString(36, "0");
							}
							boolean fahrerunterweisung = false;
							if (chkbxFahrerunterweisung.isSelected()) {
								fahrerunterweisung = true;
							}
							if (fahrerunterweisung) {
								pst.setString(37, "1");
							} else {
								pst.setString(37, "0");
							}
							boolean werkstatteinrichtung = false;
							if (chkbxWerkstatteinrichtung.isSelected()) {
								werkstatteinrichtung = true;
							}
							if (werkstatteinrichtung) {
								pst.setString(38, "1");
							} else {
								pst.setString(38, "0");
							}
							boolean belueftung = false;
							if (chkbxBelueftung.isSelected()) {
								belueftung = true;
							}
							if (belueftung) {
								pst.setString(39, "1");
							} else {
								pst.setString(39, "0");
							}
							boolean pruefung1 = false;
							if (chkbxPruefung1.isSelected()) {
								pruefung1 = true;
							}
							if (pruefung1) {
								pst.setString(40, "1");
								checkPruefung1 = 1;
							} else {
								pst.setString(40, "0");
								checkPruefung1 = 0;
							}
							boolean pruefung2 = false;
							if (chkbxPruefung2.isSelected()) {
								pruefung2 = true;
							}
							if (pruefung2) {
								pst.setString(41, "1");
								checkPruefung2 = 1;
							} else {
								pst.setString(41, "0");
								checkPruefung2 = 0;
							}
							boolean fahrerlaubnis = false;
							if (chkbxFahrerlaubnis.isSelected()) {
								fahrerlaubnis = true;
							}
							if (fahrerlaubnis) {
								pst.setString(42, "1");
								checkFahrerlaubnis = 1;
							} else {
								pst.setString(42, "0");
								checkFahrerlaubnis = 0;
							}

							try {
								fahrzeugliste = fahrzeug();
								// tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
								pst.setInt(43, fahrzeugliste.get(i).getBearbeitet() + 1);
							} catch (IndexOutOfBoundsException e1) {
								//
							}

							String query5 = "UPDATE Fahrer SET Erstpruefung=" + checkPruefung1 + ", Zweitpruefung="
									+ checkPruefung2 + ", Fahrerlaubnis=" + checkFahrerlaubnis + " WHERE ID=" + item[0];
							PreparedStatement pst5 = conn.prepareStatement(query5);
							
							pst5.executeUpdate();
							
							pst.executeUpdate();
							
							show_aktualisiertes_fahrzeug();
							
							JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
						}

						catch (IndexOutOfBoundsException e2) {
							// JOptionPane.showMessageDialog(null, e);
						} 
						
						catch (Exception e2) {
							// e2.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Ihre Aenderungen wurden nicht gespeichert, bitte Ueberarbeiten Sie die Daten gegebenfalls noch einmal.");
					}
					
					show_aktualisiertes_fahrzeug();
					vergleichsliste = fahrzeug();
					setAllFields(false);
					modus = "";
					
				} else if (modus == "anlegen") {
					try {

						emptyTf(tfFIN);
						emptyTf(tfFirmaNr);
						emptyTf(tfNLNr);
						emptyTf(tfFZG_Marke);
						emptyTf(tfFZG_Typ);
						emptyTf(tfFZG_Bezeichnung);
						emptyTf(tfamtl_Kennzeichen);
						emptyTf(tfErstzulassung);
						emptyTf(tfFinanzstatus);
						emptyTf(tfkm_Stand);
						emptyTf(tfDatum_Erfassung_km_Stand);
						emptyTf(tfZulassungsart);
						emptyTf(tfMotorleistung_KW_P_2);
						emptyTf(tfKostenstelle);

						TableModel model = tableFahrzeuge.getModel();
						int numOfRows = model.getRowCount();

						String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
						conn = DriverManager.getConnection(url);
						String qry1 = "insert into Fuhrpark (FIN, FirmaNr, Firmenname, NLNr, NLName, FZG_Marke, FZG_Typ, FZG_Bezeichnung, amtl_Kennzeichen, Erstzulassung, Abmeldedatum, "
								+ "Fahrer, Fahrer2, Finanzstatus, Bank_Leasinggesellschaft, VertragsNr, Leasingdauer_Monate, Verlaengerung_Monate, Leasingrate_zzgl_MwSt_Fahrzeug, "
								+ "Vertragsende, Bemerkung, Restwert_Leasingende, Soll_Laufleistung_Km, km_Stand, Datum_Erfassung_km_Stand, Anschaffungswert__Netto, Finanzierungsrate, "
								+ "Wartung, Zulassungsart, Motorleistung_KW_P_2, Sommerreifen, Winterreifen, Kostenstelle, "
								+ "Foliert, Typ, UVV, Fahrerunterweisung, Werkstatteinrichtung, Belueftung, Pruefung1, Pruefung2, Fahrerlaubnis,"
								+ "Bearbeitet) values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)";

						fuelleArrayMaxIDList(maxID_array);

						int maxID = 0;

						for (int i = 0; i < maxID_array.size(); i++) {
							if (maxID <= Integer.parseInt(maxID_array.get(i))) {
								maxID = Integer.parseInt(maxID_array.get(i));
							}
						}
						id = maxID + 1;

//						id = (int) model.getValueAt(numOfRows - 1, 0) + 1;
						
						Object object = comboBox.getSelectedItem();
						String selectedItem = object.toString();
						String item[] = new String[1];

						if (!selectedItem.equals("") && !selectedItem.equals("Poolfahrzeug")) {
							StringTokenizer strings2 = new StringTokenizer(selectedItem, ",");

							item[0] = strings2.nextElement().toString();
							
//							String qry2 = "UPDATE Fuhrpark SET Fahrer = '' where Fahrer = " + item[0];
							String qry3 = "UPDATE Fuhrpark SET Fahrer = " + item[0] + " where ID = " + id;							
							String qry5 = "UPDATE Fahrer SET FahrzeugID = " + id + " where ID =" + item[0];
							
//							PreparedStatement pst2 = conn.prepareStatement(qry2);
							PreparedStatement pst3 = conn.prepareStatement(qry3);
							PreparedStatement pst5 = conn.prepareStatement(qry5);
							
//							pst2.executeUpdate();
							pst3.executeUpdate();
							pst5.executeUpdate();
						}
						
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

						String qry4 = "UPDATE Fahrer SET FahrzeugID = '' where FahrzeugID = " + id;

						PreparedStatement pst1 = conn.prepareStatement(qry1);
						PreparedStatement pst4 = conn.prepareStatement(qry4);

						for (int k = 1; k < numOfRows; k++) {
							String checkRow = model.getValueAt(k, 1).toString();
							if (tfFIN.getText().equals(checkRow)) {
								throw new Exception("Dieses Fahrzeug exisitiert bereits!");
							}
						}

						pst1.setString(1, tfFIN.getText());
						pst1.setString(2, tfFirmaNr.getText());
						try {
							if (selectedItem1.equals("")) {
								pst1.setString(3, "");
							} else {
								pst1.setString(3, item1[0]);
							}
						} catch (Exception e2) {
						}
						pst1.setString(4, tfNLNr.getText());
						try {
							if (selectedItem2.equals("")) {
								pst1.setString(5, "");
							} else {
								pst1.setString(5, item2[0]);
							}
						} catch (Exception e2) {
						}
						pst1.setString(6, tfFZG_Marke.getText());
						pst1.setString(7, tfFZG_Typ.getText());
						pst1.setString(8, tfFZG_Bezeichnung.getText());
						pst1.setString(9, tfamtl_Kennzeichen.getText());
						pst1.setString(10, tfErstzulassung.getText());
						pst1.setString(11, tfAbmeldedatum.getText());
						if (selectedItem.equals("")) {
							pst1.setString(12, "");
						} else {
							pst1.setString(12, item[0]);
						}
						if (selectedItem.equals("Poolfahrzeug")) {
							pst1.setString(12, "Poolfahrzeug");
						} else {
							pst1.setString(12, item[0]);
						}
						pst1.setString(13, tfFahrer2.getText());
						pst1.setString(14, tfFinanzstatus.getText());
						pst1.setString(15, tfBank_Leasinggesellschaft.getText());
						pst1.setString(16, tfVertragsNr.getText());
						pst1.setString(17, tfLeasingdauer_Monate.getText());
						pst1.setString(18, tfVerlaengerung_Monate.getText());
						pst1.setString(19, tfLeasingrate_zzgl_MwSt_Fahrzeug.getText());
						pst1.setString(20, tfVertragsende.getText());
						pst1.setString(21, tfBemerkung.getText());
						pst1.setString(22, tfRestwert_Leasingende.getText());
						pst1.setString(23, tfSoll_Laufleistung_Km.getText());
						pst1.setString(24, tfkm_Stand.getText());
						pst1.setString(25, tfDatum_Erfassung_km_Stand.getText());
						pst1.setString(26, tfAnschaffungswert__Netto.getText());
						pst1.setString(27, tfFinanzierungsrate.getText());
						boolean wartung = false;
						if (chkbxWartung.isSelected()) {
							wartung = true;
						}
						if (wartung) {
							pst1.setString(28, "1");
						} else {
							pst1.setString(28, "0");
						}
						pst1.setString(29, tfZulassungsart.getText());
						pst1.setString(30, tfMotorleistung_KW_P_2.getText());
						pst1.setString(31, tfSommerreifen.getText());
						pst1.setString(32, tfWinterreifen.getText());
						pst1.setString(33, tfKostenstelle.getText());
						boolean foliert = false;
						if (chkbxFoliert.isSelected()) {
							foliert = true;
						}
						if (foliert) {
							pst1.setString(34, "1");
						} else {
							pst1.setString(34, "0");
						}
						pst1.setString(35, tfTyp.getText());
						boolean uvv = false;
						if (chkbxUVV.isSelected()) {
							uvv = true;
						}
						if (uvv) {
							pst1.setString(36, "1");
						} else {
							pst1.setString(36, "0");
						}
						boolean fahrerunterweisung = false;
						if (chkbxFahrerunterweisung.isSelected()) {
							fahrerunterweisung = true;
						}
						if (fahrerunterweisung) {
							pst1.setString(37, "1");
						} else {
							pst1.setString(37, "0");
						}
						boolean werkstatteinrichtung = false;
						if (chkbxWerkstatteinrichtung.isSelected()) {
							werkstatteinrichtung = true;
						}
						if (werkstatteinrichtung) {
							pst1.setString(38, "1");
						} else {
							pst1.setString(38, "0");
						}
						boolean belueftung = false;
						if (chkbxBelueftung.isSelected()) {
							belueftung = true;
						}
						if (belueftung) {
							pst1.setString(39, "1");
						} else {
							pst1.setString(39, "0");
						}
						boolean pruefung1 = false;
						if (chkbxPruefung1.isSelected()) {
							pruefung1 = true;
						}
						if (pruefung1) {
							pst1.setString(40, "1");
							checkPruefung1 = 1;
						} else {
							pst1.setString(40, "0");
							checkPruefung1 = 0;
						}
						boolean pruefung2 = false;
						if (chkbxPruefung2.isSelected()) {
							pruefung2 = true;
						}
						if (pruefung2) {
							pst1.setString(41, "1");
							checkPruefung2 = 1;
						} else {
							pst1.setString(41, "0");
							checkPruefung2 = 0;
						}
						boolean fahrerlaubnis = false;
						if (chkbxFahrerlaubnis.isSelected()) {
							fahrerlaubnis = true;
						}
						if (fahrerlaubnis) {
							pst1.setString(42, "1");
							checkFahrerlaubnis = 1;
						} else {
							pst1.setString(42, "0");
							checkFahrerlaubnis = 0;
						}
						pst1.setInt(43, 0);
						
						try {
							pst1.executeUpdate();
							pst4.executeUpdate();
						} catch (Exception e4) {

						}

						String qry6 = "UPDATE Fahrer SET Erstpruefung=" + checkPruefung1 + ", Zweitpruefung="
								+ checkPruefung2 + ", Fahrerlaubnis=" + checkFahrerlaubnis + " WHERE ID=" + item[0];
						PreparedStatement pst6 = conn.prepareStatement(qry6);
						pst6.executeUpdate();

						vergleichsliste = fahrzeug();
						show_aktualisiertes_fahrzeug();
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

	public static ArrayList<Fahrzeug> fahrzeug() {
		ArrayList<Fahrzeug> fahrzeugliste = new ArrayList<>();
		try {
			LE_Sichtbarkeit = LoginMaske.LE_Sichtbarkeit_Uebergabe;
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");

			String qry1, qry2;

			if (LE_Sichtbarkeit.equals("Admin")) {
				qry1 = "UPDATE Fuhrpark SET Pruefung1 = Fahrer.Erstpruefung, Pruefung2 = Fahrer.Zweitpruefung, Fahrerlaubnis = Fahrer.Fahrerlaubnis FROM Fuhrpark INNER JOIN Fahrer ON Fuhrpark.ID = Fahrer.FahrzeugID";
				qry2 = "SELECT * FROM Fuhrpark order by id";

			} else {
				qry1 = "UPDATE Fuhrpark SET Pruefung1 = Fahrer.Erstpruefung, Pruefung2 = Fahrer.Zweitpruefung, Fahrerlaubnis = Fahrer.Fahrerlaubnis FROM Fuhrpark INNER JOIN Fahrer ON Fuhrpark.ID = Fahrer.FahrzeugID";
				qry2 = "SELECT * FROM Fuhrpark WHERE FirmaNr=" + LE_Sichtbarkeit + " order by id";
			}

			PreparedStatement pst = conn.prepareStatement(qry1);
			pst.executeUpdate();

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(qry2);

			Fahrzeug fahrzeug;

			while (rs.next()) {

				fahrzeug = new Fahrzeug(rs.getInt("ID"), rs.getString("FIN"), rs.getString("FirmaNr"), rs.getString("Firmenname"),
						rs.getString("NLNr"), rs.getString("NLName"), rs.getString("FZG_Marke"), rs.getString("FZG_Typ"),
						rs.getString("FZG_Bezeichnung"), rs.getString("amtl_Kennzeichen"),
						rs.getString("Erstzulassung"), rs.getString("Abmeldedatum"), rs.getString("Fahrer"),
						rs.getString("Fahrer2"), rs.getString("Finanzstatus"), rs.getString("Bank_Leasinggesellschaft"),
						rs.getString("VertragsNr"), rs.getString("Leasingdauer_Monate"),
						rs.getString("Verlaengerung_Monate"), rs.getString("Leasingrate_zzgl_MwSt_Fahrzeug"),
						rs.getString("Vertragsende"), rs.getString("Bemerkung"), rs.getString("Restwert_Leasingende"),
						rs.getString("Soll_Laufleistung_Km"), rs.getString("km_Stand"),
						rs.getString("Datum_Erfassung_km_Stand"), rs.getString("Anschaffungswert__Netto"),
						rs.getString("Finanzierungsrate"), rs.getString("Wartung"), rs.getString("Zulassungsart"),
						rs.getString("Motorleistung_KW_P_2"), rs.getString("Sommerreifen"),
						rs.getString("Winterreifen"), rs.getString("Kostenstelle"), rs.getInt("Foliert"),
						rs.getString("Typ"), rs.getInt("UVV"), rs.getInt("Fahrerunterweisung"),
						rs.getInt("Werkstatteinrichtung"), rs.getInt("Belueftung"), rs.getInt("Pruefung1"),
						rs.getInt("Pruefung2"), rs.getInt("Fahrerlaubnis"), rs.getInt("Bearbeitet"));

				fahrzeugliste.add(fahrzeug);
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

		return fahrzeugliste;
	}

	public void filter(String str) {
		DefaultTableModel model = (DefaultTableModel) tableFahrzeuge.getModel();
		TableRowSorter<DefaultTableModel> rowFilter = new TableRowSorter<DefaultTableModel>(model);
		tableFahrzeuge.setRowSorter(rowFilter);

		rowFilter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
		rowCount();
	}

	public static void show_fahrzeug() {
		DefaultTableModel model = (DefaultTableModel) tableFahrzeuge.getModel();
		ArrayList<Fahrzeug> fahrzeug = fahrzeug();
		Object[] row = new Object[44];
		for (int i = 0; i < fahrzeug.size(); i++) {
			row[0] = fahrzeug.get(i).getID();
			row[1] = fahrzeug.get(i).getFIN();
			row[2] = fahrzeug.get(i).getFirmaNr();
			row[3] = fahrzeug.get(i).getFirmenname();
			row[4] = fahrzeug.get(i).getNLNr();
			row[5] = fahrzeug.get(i).getNLName();
			row[6] = fahrzeug.get(i).getFZG_Marke();
			row[7] = fahrzeug.get(i).getFZG_Typ();
			row[8] = fahrzeug.get(i).getFZG_Bezeichnung();
			row[9] = fahrzeug.get(i).getAmtl_Kennzeichen();
			row[10] = fahrzeug.get(i).getErstzulassung();
			row[11] = fahrzeug.get(i).getAbmeldedatum();
			row[12] = fahrzeug.get(i).getFahrer();
			row[13] = fahrzeug.get(i).getFahrer2();
			row[14] = fahrzeug.get(i).getFinanzstatus();
			row[15] = fahrzeug.get(i).getBank_Leasinggesellschaft();
			row[16] = fahrzeug.get(i).getVertragsNr();
			row[17] = fahrzeug.get(i).getLeasingdauer_Monate();
			row[18] = fahrzeug.get(i).getVerlaengerung_Monate();
			row[19] = fahrzeug.get(i).getLeasingrate_zzgl_MwSt_Fahrzeug();
			row[20] = fahrzeug.get(i).getVertragsende();
			row[21] = fahrzeug.get(i).getBemerkung();
			row[22] = fahrzeug.get(i).getRestwert_Leasingende();
			row[23] = fahrzeug.get(i).getSoll_Laufleistung_Km();
			row[24] = fahrzeug.get(i).getKm_Stand();
			row[25] = fahrzeug.get(i).getDatum_Erfassung_km_Stand();
			row[26] = fahrzeug.get(i).getAnschaffungswert_Netto();
			row[27] = fahrzeug.get(i).getFinanzierungsrate();
			row[28] = fahrzeug.get(i).getWartung();
			row[29] = fahrzeug.get(i).getZulassungsart();
			row[30] = fahrzeug.get(i).getMotorleistung_KW_P_2();
			row[31] = fahrzeug.get(i).getSommerreifen();
			row[32] = fahrzeug.get(i).getWinterreifen();
			row[33] = fahrzeug.get(i).getKostenstelle();
			row[34] = fahrzeug.get(i).getFoliert();
			row[35] = fahrzeug.get(i).getTyp();
			row[36] = fahrzeug.get(i).getUVV();
			row[37] = fahrzeug.get(i).getFahrerunterweisung();
			row[38] = fahrzeug.get(i).getWerkstatteinrichtung();
			row[39] = fahrzeug.get(i).getBelueftung();
			row[40] = fahrzeug.get(i).getPruefung1();
			row[41] = fahrzeug.get(i).getPruefung2();
			row[42] = fahrzeug.get(i).getFahrerlaubnis();
			row[43] = fahrzeug.get(i).getBearbeitet();
			model.addRow(row);
		}
		rowCount();
	}

	public static void show_aktualisiertes_fahrzeug() {
		try {
			DefaultTableModel model = (DefaultTableModel) tableFahrzeuge.getModel();
			model.setRowCount(0);
			show_fahrzeug();
		} catch (IndexOutOfBoundsException e) {
			// JOptionPane.showMessageDialog(null, e);
		}
		;
	}

	public void scrollpane(JButton btnSave, JButton btnClear, JButton btn_Abbrechen, JButton btn_Anlegen,
			JButton btn_Bearbeiten, JButton btn_Loeschen, JButton btn_Dokumente,
			JButton btnExport /*
								 * , JLabel lblWartung, JLabel lblFoliert, JLabel lblUVV, JLabel
								 * lblFahrerunterweisung, JLabel lblWerkstatteinrichtung, JLabel lblBelueftung
								 */) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(620, 50, f_width -630, f_height -158);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		scrollPane.setViewportView(tableFahrzeuge);

		JLabel lblBackground_2 = new JLabel("");
		try {
			lblBackground_2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/res/Vorschlag1.jpg"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void rowCount() {		
		zeilenAnzahl = tableFahrzeuge.getRowCount();
		lblZeilenAnzahl.setText("Zeilenanzahl: " + zeilenAnzahl);
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

	public void setAllFields(boolean wert) {
		tfFIN.setEnabled(wert);
		tfFirmaNr.setEnabled(wert);
		cBoxFirmenname.setEnabled(wert);
		tfNLNr.setEnabled(wert);
		cBoxNLName.setEnabled(wert);
		tfFZG_Marke.setEnabled(wert);
		tfFZG_Bezeichnung.setEnabled(wert);
		tfamtl_Kennzeichen.setEnabled(wert);
		tfFZG_Typ.setEnabled(wert);
		tfDatum_Erfassung_km_Stand.setEnabled(wert);
		tfAbmeldedatum.setEnabled(wert);
		comboBox.setEnabled(wert);
		tfFahrer2.setEnabled(wert);
		tfFinanzstatus.setEnabled(wert);
		tfBank_Leasinggesellschaft.setEnabled(wert);
		tfVertragsNr.setEnabled(wert);
		tfLeasingdauer_Monate.setEnabled(wert);
		tfVerlaengerung_Monate.setEnabled(wert);
		tfVertragsende.setEnabled(wert);
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setEnabled(wert);
		tfBemerkung.setEnabled(wert);
		tfRestwert_Leasingende.setEnabled(wert);
		tfSoll_Laufleistung_Km.setEnabled(wert);
		tfkm_Stand.setEnabled(wert);
		tfAnschaffungswert__Netto.setEnabled(wert);
		tfFinanzierungsrate.setEnabled(wert);
		tfZulassungsart.setEnabled(wert);
		tfMotorleistung_KW_P_2.setEnabled(wert);
		tfSommerreifen.setEnabled(wert);
		tfWinterreifen.setEnabled(wert);
		tfKostenstelle.setEnabled(wert);
		tfTyp.setEnabled(wert);
		tfErstzulassung.setEnabled(wert);
		chkbxBelueftung.setEnabled(wert);
		chkbxFahrerunterweisung.setEnabled(wert);
		chkbxFoliert.setEnabled(wert);
		chkbxUVV.setEnabled(wert);
		chkbxWartung.setEnabled(wert);
		chkbxWerkstatteinrichtung.setEnabled(wert);
		chkbxPruefung1.setEnabled(wert);
		chkbxPruefung2.setEnabled(wert);
		chkbxFahrerlaubnis.setEnabled(wert);
		btnSave.setEnabled(wert);
		btnAbbrechen.setEnabled(wert);
	}

	public void clearAllFields() {
		tfFIN.setText("");
		tfFirmaNr.setText("");
		cBoxFirmenname.setSelectedIndex(0);
		tfNLNr.setText("");
		cBoxNLName.setSelectedIndex(0);
		tfFZG_Marke.setText("");
		tfFZG_Bezeichnung.setText("");
		tfamtl_Kennzeichen.setText("");
		tfFZG_Typ.setText("");
		tfDatum_Erfassung_km_Stand.setText("");
		tfAbmeldedatum.setText("");
		comboBox.setSelectedIndex(0);
		tfFahrer2.setText("");
		tfFinanzstatus.setText("");
		tfBank_Leasinggesellschaft.setText("");
		tfVertragsNr.setText("");
		tfLeasingdauer_Monate.setText("");
		tfVerlaengerung_Monate.setText("");
		tfVertragsende.setText("");
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setText("");
		tfBemerkung.setText("");
		tfRestwert_Leasingende.setText("");
		tfSoll_Laufleistung_Km.setText("");
		tfkm_Stand.setText("");
		tfAnschaffungswert__Netto.setText("");
		tfFinanzierungsrate.setText("");
		tfZulassungsart.setText("");
		tfMotorleistung_KW_P_2.setText("");
		tfSommerreifen.setText("");
		tfWinterreifen.setText("");
		tfKostenstelle.setText("");
		tfTyp.setText("");
		tfErstzulassung.setText("");
		tfSuche.setText("");
		chkbxBelueftung.setText("");
		chkbxFahrerunterweisung.setText("");
		chkbxFoliert.setText("");
		chkbxUVV.setText("");
		chkbxWartung.setText("");
		chkbxWerkstatteinrichtung.setText("");
		chkbxPruefung1.setText("");
		chkbxPruefung2.setText("");
		chkbxFahrerlaubnis.setText("");
		wichtigTf(tfFIN);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNLNr);
		wichtigTf(tfFZG_Marke);
		wichtigTf(tfFZG_Typ);
		wichtigTf(tfFZG_Bezeichnung);
		wichtigTf(tfamtl_Kennzeichen);
		wichtigTf(tfErstzulassung);
		wichtigTf(tfFinanzstatus);
		wichtigTf(tfkm_Stand);
		wichtigTf(tfDatum_Erfassung_km_Stand);
		wichtigTf(tfZulassungsart);
		wichtigTf(tfMotorleistung_KW_P_2);
		wichtigTf(tfKostenstelle);
	};

	public static ArrayList<String> fuelleArrayList(ArrayList<String> arrayList) {
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1 = "Select * from Fahrer where AktivKZ <> 4";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query1);
			while (rs.next()) {
				arr.add(rs.getString("ID") + ", " + rs.getString("Personalnummer") + ", " + rs.getString("Name")
						+ ", " + rs.getString("Vorname"));
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
		return arrayList;
	}

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
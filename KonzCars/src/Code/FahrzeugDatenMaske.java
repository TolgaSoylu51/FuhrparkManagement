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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.SystemColor;

public class FahrzeugDatenMaske extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	static Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	ArrayList<Fahrzeug> vergleichsliste = new ArrayList<>();
	
	private static JTable tableFahrzeuge;
	private static int id;
	
	private static ArrayList<String> array = new ArrayList<String>();
	private static ArrayList<String> maxID_array = new ArrayList<String>();
	private static ArrayList<String> arrayFahrer = new ArrayList<String>();
	
	boolean erweitern = false;

	private JTextField tfIdentNr;
	private JTextField tfFirmaNr;
	private JTextField tfNL;
	private JTextField tfFZG_Marke;
	private JTextField tfFZG_Bezeichnung;
	private JTextField tfamtl_Kennzeichen;
	private JTextField tfFZG_Typ;
	private JTextField tfDatum_Erfassung_km_Stand;
	private JTextField tfAbmeldedatum;
	private JTextField tfFahrer2;
	private JTextField tfFinanzstatus;
	private JTextField tfBank_Leasinggesellschaft;
	private JTextField tfVertragsNr;
	private JTextField tfLeasingdauer_Monate;
	private JTextField tfVerlaengerung_Monate;
	private JTextField tfVertragsende;
	private JTextField tfLeasingrate_zzgl_MwSt_Fahrzeug;
	private JTextField tfBemerkung;
	private JTextField tfRestwert_Leasingende;
	private JTextField tfSoll_Laufleistung_Km;
	private JTextField tfkm_Stand;
	private JTextField tfAnschaffungswert__Netto;
	private JTextField tfFinanzierungsrate;
	private JTextField tfZulassungsart;
	private JTextField tfMotorleistung_KW_P_2;
	private JTextField tfSommerreifen;
	private JTextField tfWinterreifen;
	private JTextField tfKostenstelle;
	private JTextField tfTyp;
	private JTextField tfErstzulassung;
	private JTextField tfSuche;
	
	private JCheckBox chkbxBelueftung;
	private JCheckBox chkbxFahrerunterweisung;
	private JCheckBox chkbxFoliert;
	private JCheckBox chkbxUVV;
	private JCheckBox chkbxWartung;
	private JCheckBox chkbxWerkstatteinrichtung;
	private JCheckBox chkbxPruefung1;
	private JCheckBox chkbxPruefung2;
	private JCheckBox chkbxFahrerlaubnis;
	
	private JComboBox<?> comboBox;
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
		setSize(1340, 674);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfSuche = new JTextField();
		tfSuche.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				filter(tfSuche.getText());
			}
		});

		tfSuche.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfSuche.setColumns(10);
		tfSuche.setBackground(SystemColor.menu);
		tfSuche.setBounds(10, 26, 1064, 19);
		contentPane.add(tfSuche);

		btnSave = new JButton("Speichern");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setFocusPainted(false);
		btnSave.setBackground(SystemColor.inactiveCaption);

		btnSave.setBounds(10, 605, 180, 23);
		contentPane.add(btnSave);

		JButton btnClear = new JButton("X");
		btnClear.setFont(new Font("Arial", Font.PLAIN, 10));
		btnClear.setFocusPainted(false);
		btnClear.setBackground(SystemColor.inactiveCaption);
		btnClear.setBounds(1074, 26, 20, 18);
		btnClear.setMargin(new Insets(0, 0, 0, 0));
		contentPane.add(btnClear);

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				tfSuche.setText("");
				filter(tfSuche.getText());
			}
		});

		JLabel lblIdentNr = new JLabel("IdentNr");
		lblIdentNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblIdentNr.setForeground(Color.BLACK);
		lblIdentNr.setBounds(10, 72, 45, 13);
		contentPane.add(lblIdentNr);

		tfIdentNr = new JTextField();
		tfIdentNr.setForeground(Color.BLACK);
		tfIdentNr.setBackground(Color.WHITE);
		tfIdentNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfIdentNr.setBounds(64, 69, 140, 19);
		contentPane.add(tfIdentNr);
		tfIdentNr.setColumns(10);

		JLabel lblFirmaNr = new JLabel("FirmaNr");
		lblFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFirmaNr.setForeground(Color.BLACK);
		lblFirmaNr.setBounds(212, 72, 45, 13);
		contentPane.add(lblFirmaNr);

		tfFirmaNr = new JTextField();
		tfFirmaNr.setForeground(Color.BLACK);
		tfFirmaNr.setBackground(Color.WHITE);
		tfFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFirmaNr.setBounds(267, 69, 45, 19);
		contentPane.add(tfFirmaNr);
		tfFirmaNr.setColumns(10);

		JLabel lblNiederlassung = new JLabel("NL");
		lblNiederlassung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNiederlassung.setForeground(Color.BLACK);
		lblNiederlassung.setBounds(10, 97, 64, 13);
		contentPane.add(lblNiederlassung);

		tfNL = new JTextField();
		tfNL.setForeground(Color.BLACK);
		tfNL.setBackground(Color.WHITE);
		tfNL.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfNL.setColumns(10);
		tfNL.setBounds(64, 94, 248, 19);
		contentPane.add(tfNL);

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

		JLabel lblVerlaengerung_Monate = new JLabel("VerlÃ¤ngerung");
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
				clearAllFields();
				setAllFields(false);
			}
		});
		btnAbbrechen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAbbrechen.setFocusPainted(false);
		btnAbbrechen.setBackground(SystemColor.inactiveCaption);
		btnAbbrechen.setBounds(432, 605, 180, 23);
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
		
		JLabel lblFahrerlaubnis= new JLabel("Fahrerlaubnis");
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
		
		fuelleArrayList(array);		
		String[] a = new String[array.size()+1];

		a[0] = "";
		
		for (int i = 1; i < a.length; i++) {
			a[i] = array.get(i-1);
		}

		comboBox = new JComboBox<Object>(a);
		comboBox.setBounds(92, 254, 220, 19);
		contentPane.add(comboBox);

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

		tableFahrzeuge = new JTable();
		tableFahrzeuge.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFahrzeuge.setBorder(null);
		tableFahrzeuge.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "IdentNr", "FirmaNr", "NL", "FZG_Marke", "FZG_Typ", "FZG_Bezeichnung",
						"amtl_Kennzeichen", "Erstzulassung", "Abmeldedatum", "Fahrer", "Fahrer2", "Finanzstatus",
						"Bank_Leasinggesellschaft", "VertragsNr", "Leasingdauer_Monate", "Verlaengerung_Monate",
						"Leasingrate_zzgl_MwSt_Fahrzeug", "Vertragsende", "Bemerkung", "Restwert_Leasingende",
						"Soll_Laufleistung_Km", "km_Stand", "Datum_Erfassung_km_Stand", "Anschaffungswert__Netto",
						"Finanzierungsrate", "Wartung", "Zulassungsart", "Motorleistung_KW_P_2", "Sommerreifen",
						"Winterreifen", "Kostenstelle", "Foliert", "Typ", "UVV", "Fahrerunterweisung",
						"Werkstatteinrichtung", "Belueftung", "Pruefung1", "Pruefung2", "Fahrerlaubnis", "Bearbeitet"}));

		tableFahrzeuge.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableFahrzeuge.setAutoCreateRowSorter(true);

		wichtigTf(tfIdentNr);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNL);
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

		JButton btn_Anlegen = new JButton("Anlegen");
		btn_Anlegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modus = "anlegen";
				setAllFields(true);
				wichtigTf(tfIdentNr);
				wichtigTf(tfFirmaNr);
				wichtigTf(tfNL);
				wichtigTf(tfFZG_Marke);
				wichtigTf(tfFZG_Typ);
				wichtigTf(tfFZG_Bezeichnung);
				wichtigTf(tfamtl_Kennzeichen);
				wichtigTf(tfErstzulassung);
//				wichtigTf(tfFahrer);
				wichtigTf(tfFinanzstatus);
				wichtigTf(tfkm_Stand);
				wichtigTf(tfDatum_Erfassung_km_Stand);
				wichtigTf(tfZulassungsart);
				wichtigTf(tfMotorleistung_KW_P_2);
				wichtigTf(tfKostenstelle);
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
				chkbxWartung.setSelected(false);
				tfSommerreifen.setText("");
				tfWinterreifen.setText("");
				chkbxFoliert.setSelected(false);
				tfTyp.setText("");
				chkbxUVV.setSelected(false);
				chkbxFahrerunterweisung.setSelected(false);
				chkbxWerkstatteinrichtung.setSelected(false);
				chkbxBelueftung.setSelected(false);
				chkbxPruefung1.setSelected(false);
				chkbxPruefung2.setSelected(false);
				chkbxFahrerlaubnis.setSelected(false);
			}
		});

		JButton btn_Bearbeiten = new JButton("Bearbeiten");
		btn_Bearbeiten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modus = "bearbeiten";
				setAllFields(true);
				tfIdentNr.setEnabled(false);
			}
		});

		JButton btn_Loeschen = new JButton("New button");
		btn_Loeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Object[] options = { "Ja", "Nein" };
				int n = JOptionPane.showOptionDialog(null, "Sind Sie sich sicher?", "Datensatz löschen",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION, null, options, options[1]);
				if (n == 0) {
					try {
						int i = tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
						TableModel model = tableFahrzeuge.getModel();
						id = (int) model.getValueAt(i, 0);

						String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
						conn = DriverManager.getConnection(url);
						String query = "DELETE FROM FuhrparkTest WHERE ID=" + id;
						PreparedStatement pst = conn.prepareStatement(query);

						pst.executeUpdate();

						show_aktualisiertes_fahrzeug();
						vergleichsliste = fahrzeug();
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
				id_Uebergabe_fahrzeug = tableFahrzeuge.getModel()
						.getValueAt(tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow()), 0)
						.toString();
				id_Uebergabe_fahrer = tableFahrzeuge.getModel()
						.getValueAt(tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow()), 10)
						.toString();
				herkunft_ueber_fahrzeug = true;
				DokumenteMaske frame = new DokumenteMaske();
				frame.setVisible(true);
			}
		});

		btn_Anlegen.setBounds(1102, 2, 45, 43);
		contentPane.add(btn_Anlegen);
		btn_Bearbeiten.setBounds(1157, 2, 45, 43);
		contentPane.add(btn_Bearbeiten);
		btn_Loeschen.setBounds(1212, 2, 45, 43);
		contentPane.add(btn_Loeschen);
		btn_Dokumente.setBounds(1267, 2, 45, 43);
		contentPane.add(btn_Dokumente);

		scrollpane(btnSave, btnClear, btnAbbrechen, btn_Anlegen, btn_Bearbeiten, btn_Loeschen, btn_Dokumente);
		
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
		btnExport.setBounds(1134, 605, 180, 23);
		contentPane.add(btnExport);

		tableFahrzeuge.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int i = tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
				TableModel model = tableFahrzeuge.getModel();

				id = (int) model.getValueAt(i, 0);
				tfIdentNr.setText(model.getValueAt(i, 1).toString());
				tfFirmaNr.setText(model.getValueAt(i, 2).toString());
				tfNL.setText(model.getValueAt(i, 3).toString());
				tfFZG_Marke.setText(model.getValueAt(i, 4).toString());
				tfFZG_Typ.setText(model.getValueAt(i, 5).toString());
				tfFZG_Bezeichnung.setText(model.getValueAt(i, 6).toString());
				tfamtl_Kennzeichen.setText(model.getValueAt(i, 7).toString());
				tfErstzulassung.setText(model.getValueAt(i, 8).toString());
				tfAbmeldedatum.setText(model.getValueAt(i, 9).toString());
				
				String item[] = new String[1];
				String s1;
				String s2 = model.getValueAt(i,10).toString();
				for(int j = 1; j < comboBox.getItemCount(); j++) {
					s1 = comboBox.getItemAt(j).toString();
					StringTokenizer strings = new StringTokenizer(s1, ",");
 					item[0] = strings.nextElement().toString();
					if(item[0].equals(s2)) {
						comboBox.setSelectedIndex(j);
					}
				}
				
				if (model.getValueAt(i,10).toString().equals("")) {
					comboBox.setSelectedIndex(0);
				}
				
				tfFahrer2.setText(model.getValueAt(i, 11).toString());
				tfFinanzstatus.setText(model.getValueAt(i, 12).toString());
				tfBank_Leasinggesellschaft.setText(model.getValueAt(i, 13).toString());
				tfVertragsNr.setText(model.getValueAt(i, 14).toString());
				tfLeasingdauer_Monate.setText(model.getValueAt(i, 15).toString());
				tfVerlaengerung_Monate.setText(model.getValueAt(i, 16).toString());
				tfLeasingrate_zzgl_MwSt_Fahrzeug.setText(model.getValueAt(i, 17).toString());
				tfVertragsende.setText(model.getValueAt(i, 18).toString());
				tfBemerkung.setText(model.getValueAt(i, 19).toString());
				tfRestwert_Leasingende.setText(model.getValueAt(i, 20).toString());
				tfSoll_Laufleistung_Km.setText(model.getValueAt(i, 21).toString());
				tfkm_Stand.setText(model.getValueAt(i, 22).toString());
				tfDatum_Erfassung_km_Stand.setText(model.getValueAt(i, 23).toString());
				tfAnschaffungswert__Netto.setText(model.getValueAt(i, 24).toString());
				tfFinanzierungsrate.setText(model.getValueAt(i, 25).toString());
				String wartung = model.getValueAt(i, 26).toString();
				switch (wartung) {
				case "1":
					chkbxWartung.setSelected(true);
					break;
				case "0":
					chkbxWartung.setSelected(false);
					break;
				}
				tfZulassungsart.setText(model.getValueAt(i, 27).toString());
				tfMotorleistung_KW_P_2.setText(model.getValueAt(i, 28).toString());
				tfSommerreifen.setText(model.getValueAt(i, 29).toString());
				tfWinterreifen.setText(model.getValueAt(i, 30).toString());
				tfKostenstelle.setText(model.getValueAt(i, 31).toString());
				String foliert = model.getValueAt(i, 32).toString();
				switch (foliert) {
				case "1":
					chkbxFoliert.setSelected(true);
					break;
				case "0":
					chkbxFoliert.setSelected(false);
					break;
				}
				tfTyp.setText(model.getValueAt(i, 33).toString());
				String uvv = model.getValueAt(i, 34).toString();
				switch (uvv) {
				case "1":
					chkbxUVV.setSelected(true);
					break;
				case "0":
					chkbxUVV.setSelected(false);
					break;
				}
				String fahrerunterweisung = model.getValueAt(i, 35).toString();
				switch (fahrerunterweisung) {
				case "1":
					chkbxFahrerunterweisung.setSelected(true);
					break;
				case "0":
					chkbxFahrerunterweisung.setSelected(false);
					break;
				}
				String werkstatteinrichtung = model.getValueAt(i, 36).toString();
				switch (werkstatteinrichtung) {
				case "1":
					chkbxWerkstatteinrichtung.setSelected(true);
					break;
				case "0":
					chkbxWerkstatteinrichtung.setSelected(false);
					break;
				}
				String belueftung = model.getValueAt(i, 37).toString();
				switch (belueftung) {
				case "1":
					chkbxBelueftung.setSelected(true);
					break;
				case "0":
					chkbxBelueftung.setSelected(false);
					break;
				}
				String pruefung1 = model.getValueAt(i, 38).toString();
				switch (pruefung1) {
				case "1":
					chkbxPruefung1.setSelected(true);
					break;
				case "0":
					chkbxPruefung1.setSelected(false);
					break;
				}
				String pruefung2 = model.getValueAt(i, 39).toString();
				switch (pruefung2) {
				case "1":
					chkbxPruefung2.setSelected(true);
					break;
				case "0":
					chkbxPruefung2.setSelected(false);
					break;
				}
				String fahrerlaubnis = model.getValueAt(i, 40).toString();
				switch (fahrerlaubnis) {
				case "1":
					chkbxFahrerlaubnis.setSelected(true);
					break;
				case "0":
					chkbxFahrerlaubnis.setSelected(false);
					break;
				}
			}
		});

		vergleichsliste = fahrzeug();
		show_fahrzeug();
		

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modus == "bearbeiten") {
					int i = tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
					int aktualisiert;
					aktualisiert = vergleichsliste.get(i).getBearbeitet();
					ArrayList<Fahrzeug> fahrzeugliste = fahrzeug();
					if (aktualisiert == fahrzeugliste.get(i).getBearbeitet()) {
						try {

							emptyTf(tfIdentNr);
							emptyTf(tfFirmaNr);
							emptyTf(tfNL);
							emptyTf(tfFZG_Marke);
							emptyTf(tfFZG_Typ);
							emptyTf(tfFZG_Bezeichnung);
							emptyTf(tfamtl_Kennzeichen);
							emptyTf(tfErstzulassung);
							emptyTf(tfAbmeldedatum);
//							emptyTf(tfFahrer);
							emptyTf(tfFinanzstatus);
							emptyTf(tfkm_Stand);
							emptyTf(tfDatum_Erfassung_km_Stand);
							emptyTf(tfZulassungsart);
							emptyTf(tfMotorleistung_KW_P_2);
							emptyTf(tfKostenstelle);

							TableModel model = tableFahrzeuge.getModel();
							id = (int) model.getValueAt(i, 0);

							String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
							conn = DriverManager.getConnection(url);
							String qry1 = "UPDATE FuhrparkTest SET IdentNr=?, FirmaNr=?, NL=?, FZG_Marke=?, FZG_Typ=?, FZG_Bezeichnung=?, amtl_Kennzeichen=?, Erstzulassung=?, Abmeldedatum=?, Fahrer=?, Fahrer2=?, Finanzstatus=?, Bank_Leasinggesellschaft=?, VertragsNr=?, Leasingdauer_Monate=?, Verlaengerung_Monate=?, Leasingrate_zzgl_MwSt_Fahrzeug=?, Vertragsende=?, Bemerkung=?, Restwert_Leasingende=?, Soll_Laufleistung_Km=?, km_Stand=?, Datum_Erfassung_km_Stand=?, Anschaffungswert__Netto=?, Finanzierungsrate=?, Wartung=?, Zulassungsart=?, Motorleistung_KW_P_2=?, Sommerreifen=?, Winterreifen=?, Kostenstelle=?, Foliert=?, Typ=?, UVV=?, Fahrerunterweisung=?, Werkstatteinrichtung=?, Belueftung=?, Pruefung1=?, Pruefung2=?, Fahrerlaubnis=?, Bearbeitet=? WHERE ID="
									+ id;

							Object object = comboBox.getSelectedItem();
							String selectedItem = object.toString();

							StringTokenizer strings = new StringTokenizer(selectedItem, ",");

							String item[] = new String[1];

							item[0] = strings.nextElement().toString();
							
							String qry2 = "UPDATE FuhrparkTest SET Fahrer = '' where Fahrer = " + item[0];
							String qry3 = "UPDATE MitarbeiterTest set FahrzeugID = " + id + " where ID =" + item[0];
							String qry4 = "UPDATE FuhrparkTest SET Fahrer = " + item[0] + " where ID = " + id;

							PreparedStatement pst1 = conn.prepareStatement(qry1);
							PreparedStatement pst2 = conn.prepareStatement(qry2);
							PreparedStatement pst3 = conn.prepareStatement(qry3);
							PreparedStatement pst4 = conn.prepareStatement(qry4);

							pst1.setString(1, tfIdentNr.getText());
							pst1.setString(2, tfFirmaNr.getText());
							pst1.setString(3, tfNL.getText());
							pst1.setString(4, tfFZG_Marke.getText());
							pst1.setString(5, tfFZG_Typ.getText());
							pst1.setString(6, tfFZG_Bezeichnung.getText());
							pst1.setString(7, tfamtl_Kennzeichen.getText());
							pst1.setString(8, tfErstzulassung.getText());
							pst1.setString(9, tfAbmeldedatum.getText());
							pst1.setString(10, item[0]);
							pst1.setString(11, tfFahrer2.getText());
							pst1.setString(12, tfFinanzstatus.getText());
							pst1.setString(13, tfBank_Leasinggesellschaft.getText());
							pst1.setString(14, tfVertragsNr.getText());
							pst1.setString(15, tfLeasingdauer_Monate.getText());
							pst1.setString(16, tfVerlaengerung_Monate.getText());
							pst1.setString(17, tfLeasingrate_zzgl_MwSt_Fahrzeug.getText());
							pst1.setString(18, tfVertragsende.getText());
							pst1.setString(19, tfBemerkung.getText());
							pst1.setString(20, tfRestwert_Leasingende.getText());
							pst1.setString(21, tfSoll_Laufleistung_Km.getText());
							pst1.setString(22, tfkm_Stand.getText());
							pst1.setString(23, tfDatum_Erfassung_km_Stand.getText());
							pst1.setString(24, tfAnschaffungswert__Netto.getText());
							pst1.setString(25, tfFinanzierungsrate.getText());
							boolean wartung = false;
							if (chkbxWartung.isSelected()) {
								wartung = true;
							}
							if (wartung) {
								pst1.setString(26, "1");
							} else {
								pst1.setString(26, "0");
							}
							pst1.setString(27, tfZulassungsart.getText());
							pst1.setString(28, tfMotorleistung_KW_P_2.getText());
							pst1.setString(29, tfSommerreifen.getText());
							pst1.setString(30, tfWinterreifen.getText());
							pst1.setString(31, tfKostenstelle.getText());
							boolean foliert = false;
							if (chkbxFoliert.isSelected()) {
								foliert = true;
							}
							if (foliert) {
								pst1.setString(32, "1");
							} else {
								pst1.setString(32, "0");
							}
							pst.setString(33, tfTyp.getText());
							boolean uvv = false;
							if (chkbxUVV.isSelected()) {
								uvv = true;
							}
							if (uvv) {
								pst1.setString(34, "1");
							} else {
								pst1.setString(34, "0");
							}
							boolean fahrerunterweisung = false;
							if (chkbxFahrerunterweisung.isSelected()) {
								fahrerunterweisung = true;
							}
							if (fahrerunterweisung) {
								pst1.setString(35, "1");
							} else {
								pst1.setString(35, "0");
							}
							boolean werkstatteinrichtung = false;
							if (chkbxWerkstatteinrichtung.isSelected()) {
								werkstatteinrichtung = true;
							}
							if (werkstatteinrichtung) {
								pst1.setString(36, "1");
							} else {
								pst1.setString(36, "0");
							}
							boolean belueftung = false;
							if (chkbxBelueftung.isSelected()) {
								belueftung = true;
							}
							if (belueftung) {
								pst1.setString(37, "1");
							} else {
								pst1.setString(37, "0");
							}	
							boolean pruefung1 = false;
							if (chkbxPruefung1.isSelected()) {
								pruefung1 = true;
							}
							if (pruefung1) {
								pst1.setString(38, "1");
								checkPruefung1= 1;
							} else {
								pst1.setString(38, "0");
								checkPruefung1= 0;
							}
							boolean pruefung2 = false;
							if (chkbxPruefung2.isSelected()) {
								pruefung2 = true;
							}
							if (pruefung2) {
								pst1.setString(39, "1");
								checkPruefung2= 1;
							} else {
								pst1.setString(39, "0");
								checkPruefung2= 0;
							}
							boolean fahrerlaubnis = false;
							if (chkbxFahrerlaubnis.isSelected()) {
								fahrerlaubnis = true;
							}
							if (fahrerlaubnis) {
								pst1.setString(40, "1");
								checkFahrerlaubnis = 1;
							} else {
								pst1.setString(40, "0");
								checkFahrerlaubnis = 0;
							}

							try {
								fahrzeugliste = fahrzeug();
								// tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
								pst1.setInt(41, fahrzeugliste.get(i).getBearbeitet() + 1);
							} catch (IndexOutOfBoundsException e1) {
								//
							}
							pst1.executeUpdate();
							pst2.executeUpdate();
							pst3.executeUpdate();
							pst4.executeUpdate();
							
							String query5 = "UPDATE MitarbeiterTest SET Erstpruefung=" + checkPruefung1 + ", Zweitpruefung=" + checkPruefung2 + ", Fahrerlaubnis=" + checkFahrerlaubnis + " WHERE ID=" + item[0];
							PreparedStatement pst5 = conn.prepareStatement(query5);
							pst5.executeUpdate();

							vergleichsliste = fahrzeug();
							show_aktualisiertes_fahrzeug();
							setAllFields(false);
							modus = "";
							JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
						}

						catch (IndexOutOfBoundsException e2) {
							// JOptionPane.showMessageDialog(null, e);
						} catch (Exception e2) {
							// e2.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Ihre Aenderungen wurden nicht gespeichert, bitte Ueberarbeiten Sie die Daten gegebenfalls noch einmal.");
					}
				} else if (modus == "anlegen") {
					try {

						emptyTf(tfIdentNr);
						emptyTf(tfFirmaNr);
						emptyTf(tfNL);
						emptyTf(tfFZG_Marke);
						emptyTf(tfFZG_Typ);
						emptyTf(tfFZG_Bezeichnung);
						emptyTf(tfamtl_Kennzeichen);
						emptyTf(tfErstzulassung);
//						emptyTf(tfFahrer);
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
						String qry1 = "insert into FuhrparkTest (IdentNr, FirmaNr, NL, FZG_Marke, FZG_Typ, FZG_Bezeichnung, amtl_Kennzeichen, Erstzulassung, Abmeldedatum, "
								+ "Fahrer, Fahrer2, Finanzstatus, Bank_Leasinggesellschaft, VertragsNr, Leasingdauer_Monate, Verlaengerung_Monate, Leasingrate_zzgl_MwSt_Fahrzeug, "
								+ "Vertragsende, Bemerkung, Restwert_Leasingende, Soll_Laufleistung_Km, km_Stand, Datum_Erfassung_km_Stand, Anschaffungswert__Netto, Finanzierungsrate, "
								+ "Wartung, Zulassungsart, Motorleistung_KW_P_2, Sommerreifen, Winterreifen, Kostenstelle, "
								+ "Foliert, Typ, UVV, Fahrerunterweisung, Werkstatteinrichtung, Belueftung, Pruefung1, Pruefung2, Fahrerlaubnis,"
								+ "Bearbeitet) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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

						StringTokenizer strings = new StringTokenizer(selectedItem, ",");

						String item[] = new String[1];

						item[0] = strings.nextElement().toString();
						
						String qry2 = "UPDATE FuhrparkTest SET Fahrer = '' where Fahrer = " + item[0];
						String qry3 = "UPDATE FuhrparkTest SET Fahrer = " + item[0] + " where ID = " + id;
						String qry4 = "UPDATE MitarbeiterTest SET FahrzeugID = '' where FahrzeugID = " + id;
						String qry5 = "UPDATE MitarbeiterTest SET FahrzeugID = " + id + " where ID =" + item[0];
		
						PreparedStatement pst1 = conn.prepareStatement(qry1);
						PreparedStatement pst2 = conn.prepareStatement(qry2);
						PreparedStatement pst3 = conn.prepareStatement(qry3);
						PreparedStatement pst4 = conn.prepareStatement(qry4);
						PreparedStatement pst5 = conn.prepareStatement(qry5);

						for (int k = 1; k < numOfRows; k++) {
							String checkRow = model.getValueAt(k, 1).toString();
							if (tfIdentNr.getText().equals(checkRow)) {
								throw new Exception("Dieses Fahrzeug exisitiert bereits!");
							}
						}
						
						pst1.setString(1, tfIdentNr.getText());
						pst1.setString(2, tfFirmaNr.getText());
						pst1.setString(3, tfNL.getText());
						pst1.setString(4, tfFZG_Marke.getText());
						pst1.setString(5, tfFZG_Typ.getText());
						pst1.setString(6, tfFZG_Bezeichnung.getText());
						pst1.setString(7, tfamtl_Kennzeichen.getText());
						pst1.setString(8, tfErstzulassung.getText());
						pst1.setString(9, tfAbmeldedatum.getText());
						pst1.setString(10, item[0]);
						pst1.setString(11, tfFahrer2.getText());
						pst1.setString(12, tfFinanzstatus.getText());
						pst1.setString(13, tfBank_Leasinggesellschaft.getText());
						pst1.setString(14, tfVertragsNr.getText());
						pst1.setString(15, tfLeasingdauer_Monate.getText());
						pst1.setString(16, tfVerlaengerung_Monate.getText());
						pst1.setString(17, tfLeasingrate_zzgl_MwSt_Fahrzeug.getText());
						pst1.setString(18, tfVertragsende.getText());
						pst1.setString(19, tfBemerkung.getText());
						pst1.setString(20, tfRestwert_Leasingende.getText());
						pst1.setString(21, tfSoll_Laufleistung_Km.getText());
						pst1.setString(22, tfkm_Stand.getText());
						pst1.setString(23, tfDatum_Erfassung_km_Stand.getText());
						pst1.setString(24, tfAnschaffungswert__Netto.getText());
						pst1.setString(25, tfFinanzierungsrate.getText());
						boolean wartung = false;
						if (chkbxWartung.isSelected()) {
							wartung = true;
						}
						if (wartung) {
							pst1.setString(26, "1");
						} else {
							pst1.setString(26, "0");
						}
						pst1.setString(27, tfZulassungsart.getText());
						pst1.setString(28, tfMotorleistung_KW_P_2.getText());
						pst1.setString(29, tfSommerreifen.getText());
						pst1.setString(30, tfWinterreifen.getText());
						pst1.setString(31, tfKostenstelle.getText());
						boolean foliert = false;
						if (chkbxFoliert.isSelected()) {
							foliert = true;
						}
						if (foliert) {
							pst1.setString(32, "1");
						} else {
							pst1.setString(32, "0");
						}
						pst1.setString(33, tfTyp.getText());
						boolean uvv = false;
						if (chkbxUVV.isSelected()) {
							uvv = true;
						}
						if (uvv) {
							pst1.setString(34, "1");
						} else {
							pst1.setString(34, "0");
						}
						boolean fahrerunterweisung = false;
						if (chkbxFahrerunterweisung.isSelected()) {
							fahrerunterweisung = true;
						}
						if (fahrerunterweisung) {
							pst1.setString(35, "1");
						} else {
							pst1.setString(35, "0");
						}
						boolean werkstatteinrichtung = false;
						if (chkbxWerkstatteinrichtung.isSelected()) {
							werkstatteinrichtung = true;
						}
						if (werkstatteinrichtung) {
							pst1.setString(36, "1");
						} else {
							pst1.setString(36, "0");
						}
						boolean belueftung = false;
						if (chkbxBelueftung.isSelected()) {
							belueftung = true;
						}
						if (belueftung) {
							pst1.setString(37, "1");
						} else {
							pst1.setString(37, "0");
						}
						boolean pruefung1 = false;
						if (chkbxPruefung1.isSelected()) {
							pruefung1 = true;
						}
						if (pruefung1) {
							pst1.setString(38, "1");
							checkPruefung1 = 1;
						} else {
							pst1.setString(38, "0");
							checkPruefung1 = 0;
						}
						boolean pruefung2 = false;
						if (chkbxPruefung2.isSelected()) {
							pruefung2 = true;
						}
						if (pruefung2) {
							pst1.setString(39, "1");
							checkPruefung2 = 1;
						} else {
							pst1.setString(39, "0");
							checkPruefung2 = 0;
						}
						boolean fahrerlaubnis = false;
						if (chkbxFahrerlaubnis.isSelected()) {
							fahrerlaubnis = true;
						}
						if (fahrerlaubnis) {
							pst1.setString(40, "1");
							checkFahrerlaubnis = 1;
						} else {
							pst1.setString(40, "0");
							checkFahrerlaubnis = 0;
						}
						pst1.setInt(41, 0);

						pst1.executeUpdate();
						pst2.executeUpdate();
						pst3.executeUpdate();
						pst4.executeUpdate();
						pst5.executeUpdate();
						
						String qry6 = "UPDATE MitarbeiterTest SET Erstpruefung=" + checkPruefung1 + ", Zweitpruefung=" + checkPruefung2 + ", Fahrerlaubnis=" + checkFahrerlaubnis + " WHERE ID=" + item[0];
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
				qry1 = "UPDATE FuhrparkTest SET Pruefung1 = MitarbeiterTest.Erstpruefung, Pruefung2 = MitarbeiterTest.Zweitpruefung, Fahrerlaubnis = MitarbeiterTest.Fahrerlaubnis FROM FuhrparkTest INNER JOIN MitarbeiterTest ON FuhrparkTest.ID = MitarbeiterTest.FahrzeugID";
				qry2 = "SELECT * FROM FuhrparkTest";
				
			} else {
				qry1 = "UPDATE FuhrparkTest SET Pruefung1 = MitarbeiterTest.Erstpruefung, Pruefung2 = MitarbeiterTest.Zweitpruefung, Fahrerlaubnis = MitarbeiterTest.Fahrerlaubnis FROM FuhrparkTest INNER JOIN MitarbeiterTest ON FuhrparkTest.ID = MitarbeiterTest.FahrzeugID";
				qry2 = "SELECT * FROM FuhrparkTest WHERE FirmaNr=" + LE_Sichtbarkeit;
			}
			
			PreparedStatement pst = conn.prepareStatement(qry1);
			pst.executeUpdate();
			
			Statement st = conn.createStatement();	
			ResultSet rs = st.executeQuery(qry2);
			
			Fahrzeug fahrzeug;
			
			while (rs.next()) {
				
				fahrzeug = new Fahrzeug(rs.getInt("ID"), rs.getString("IdentNr"), rs.getString("FirmaNr"),
						rs.getString("NL"), rs.getString("FZG_Marke"), rs.getString("FZG_Typ"),
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
						rs.getString("Winterreifen"), rs.getString("Kostenstelle"), rs.getString("Foliert"),
						rs.getString("Typ"), rs.getInt("UVV"), rs.getInt("Fahrerunterweisung"),
						rs.getInt("Werkstatteinrichtung"), rs.getInt("Belueftung"),						
						rs.getInt("Pruefung1"), rs.getInt("Pruefung2"), rs.getInt("Fahrerlaubnis"),
						rs.getInt("Bearbeitet"));
				
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
	}

	public static void show_fahrzeug() {
		DefaultTableModel model = (DefaultTableModel) tableFahrzeuge.getModel();
		ArrayList<Fahrzeug> fahrzeug = fahrzeug();
		Object[] row = new Object[42];
		for (int i = 0; i < fahrzeug.size(); i++) {
			row[0] = fahrzeug.get(i).getID();
			row[1] = fahrzeug.get(i).getIdentNr();
			row[2] = fahrzeug.get(i).getFirmaNr();
			row[3] = fahrzeug.get(i).getNL();
			row[4] = fahrzeug.get(i).getFZG_Marke();
			row[5] = fahrzeug.get(i).getFZG_Typ();
			row[6] = fahrzeug.get(i).getFZG_Bezeichnung();
			row[7] = fahrzeug.get(i).getAmtl_Kennzeichen();
			row[8] = fahrzeug.get(i).getErstzulassung();
			row[9] = fahrzeug.get(i).getAbmeldedatum();
			row[10] = fahrzeug.get(i).getFahrer();
			row[11] = fahrzeug.get(i).getFahrer2();
			row[12] = fahrzeug.get(i).getFinanzstatus();
			row[13] = fahrzeug.get(i).getBank_Leasinggesellschaft();
			row[14] = fahrzeug.get(i).getVertragsNr();
			row[15] = fahrzeug.get(i).getLeasingdauer_Monate();
			row[16] = fahrzeug.get(i).getVerlaengerung_Monate();
			row[17] = fahrzeug.get(i).getLeasingrate_zzgl_MwSt_Fahrzeug();
			row[18] = fahrzeug.get(i).getVertragsende();
			row[19] = fahrzeug.get(i).getBemerkung();
			row[20] = fahrzeug.get(i).getRestwert_Leasingende();
			row[21] = fahrzeug.get(i).getSoll_Laufleistung_Km();
			row[22] = fahrzeug.get(i).getKm_Stand();
			row[23] = fahrzeug.get(i).getDatum_Erfassung_km_Stand();
			row[24] = fahrzeug.get(i).getAnschaffungswert_Netto();
			row[25] = fahrzeug.get(i).getFinanzierungsrate();
			row[26] = fahrzeug.get(i).getWartung();
			row[27] = fahrzeug.get(i).getZulassungsart();
			row[28] = fahrzeug.get(i).getMotorleistung_KW_P_2();
			row[29] = fahrzeug.get(i).getSommerreifen();
			row[30] = fahrzeug.get(i).getWinterreifen();
			row[31] = fahrzeug.get(i).getKostenstelle();
			row[32] = fahrzeug.get(i).getFoliert();
			row[33] = fahrzeug.get(i).getTyp();
			row[34] = fahrzeug.get(i).getUVV();
			row[35] = fahrzeug.get(i).getFahrerunterweisung();
			row[36] = fahrzeug.get(i).getWerkstatteinrichtung();
			row[37] = fahrzeug.get(i).getBelueftung();
			row[38] = fahrzeug.get(i).getPruefung1();
			row[39] = fahrzeug.get(i).getPruefung2();
			row[40] = fahrzeug.get(i).getFahrerlaubnis();
			row[41] = fahrzeug.get(i).getBearbeitet();
			model.addRow(row);
		}
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
			JButton btn_Bearbeiten, JButton btn_Loeschen, JButton btn_Dokumente) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(621, 50, 692, 542);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		scrollPane.setViewportView(tableFahrzeuge);

		JLabel lblBackground_2 = new JLabel("");
		lblBackground_2.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));

		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(final WindowEvent evt) {
				if (evt.getNewState() == MAXIMIZED_BOTH) {
					scrollPane.setBounds(620, 50, 1288, 956);
					btnSave.setBounds(10, 986, 180, 23);
					btn_Abbrechen.setBounds(200, 986, 180, 23);
					btn_Anlegen.setBounds(1698, 2, 45, 43);
					btn_Bearbeiten.setBounds(1753, 2, 45, 43);
					btn_Loeschen.setBounds(1807, 2, 45, 43);
					btn_Dokumente.setBounds(1862, 2, 45, 43);
					btnClear.setBounds(1670, 26, 19, 18);
					tfSuche.setBounds(10, 26, 1660, 19);

				} else {
					scrollPane.setBounds(621, 50, 692, 574);
					btnSave.setBounds(10, 605, 180, 23);
					btn_Abbrechen.setBounds(200, 605, 180, 23);
					btn_Anlegen.setBounds(1102, 2, 45, 43);
					btn_Bearbeiten.setBounds(1157, 2, 45, 43);
					btn_Loeschen.setBounds(1212, 2, 45, 43);
					btn_Dokumente.setBounds(1267, 2, 45, 43);
					btnClear.setBounds(1074, 26, 20, 18);
					tfSuche.setBounds(10, 26, 1064, 19);
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
			throw new Exception("Fuellen sie bitte alle Felder aus!");
		}
	}

	public void setAllFields(boolean wert) {
		tfIdentNr.setEnabled(wert);
		tfFirmaNr.setEnabled(wert);
		tfNL.setEnabled(wert);
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
//		tfSuche.setEnabled(wert);
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
		tfIdentNr.setText("");
		tfFirmaNr.setText("");
		tfNL.setText("");
		tfFZG_Marke.setText("");
		tfFZG_Bezeichnung.setText("");
		tfamtl_Kennzeichen.setText("");
		tfFZG_Typ.setText("");
		tfDatum_Erfassung_km_Stand.setText("");
		tfAbmeldedatum.setText("");
//		tfFahrer.setText("");
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
		wichtigTf(tfIdentNr);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNL);
		wichtigTf(tfFZG_Marke);
		wichtigTf(tfFZG_Typ);
		wichtigTf(tfFZG_Bezeichnung);
		wichtigTf(tfamtl_Kennzeichen);
		wichtigTf(tfErstzulassung);
//		wichtigTf(tfFahrer);
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
			String query1 = "Select * from MitarbeiterTest where AktivKZ <> 4";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query1);
			while (rs.next()) {
				array.add(rs.getString("ID") + ", " + rs.getString("Personalnummer") + ", " + rs.getString("Name")
						+ ", " + rs.getString("Vorname"));
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
		return arrayList;
	}

	public static ArrayList<String> fuelleArrayMaxIDList(ArrayList<String> arrayList) {
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query = "Select * from FuhrparkTest";
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
}
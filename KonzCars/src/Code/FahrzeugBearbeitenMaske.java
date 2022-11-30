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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.SystemColor;

public class FahrzeugBearbeitenMaske extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	static Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	ArrayList<Fahrzeug> vergleichsliste = new ArrayList<>();
	private static JTable tableFahrzeuge;
	boolean erweitern = false;
	private String id = null;

	private JTextField tfIdentNr;
	private JTextField tfFirmaNr;
	private JTextField tfNL;
	private JTextField tfFZG_Marke;
	private JTextField tfFZG_Bezeichnung;
	private JTextField tfamtl_Kennzeichen;
	private JTextField tfFZG_Typ;
	private JTextField tfDatum_Erfassung_km_Stand;
	private JTextField tfAbmeldedatum;
	private JTextField tfFahrer;
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

	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FahrzeugBearbeitenMaske frame = new FahrzeugBearbeitenMaske();
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
	public FahrzeugBearbeitenMaske() {
		setTitle("KFM Fahrzeug Bearbeiten");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1278, 674);
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
		tfSuche.setBounds(10, 26, 964, 19);
		contentPane.add(tfSuche);

		JButton btnSave = new JButton("Ändern");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setFocusPainted(false);
		btnSave.setBackground(SystemColor.inactiveCaption);

		btnSave.setBounds(10, 605, 180, 23);
		contentPane.add(btnSave);
		
		JButton btnClear = new JButton("X");
		btnClear.setFont(new Font("Arial", Font.PLAIN, 10));
		btnClear.setFocusPainted(false);
		btnClear.setBackground(SystemColor.inactiveCaption);
		btnClear.setBounds(974, 26, 19, 18);
		btnClear.setMargin(new Insets(0, 0, 0, 0));
		contentPane.add(btnClear);
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent evt){
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
		
		tfFahrer = new JTextField();
		tfFahrer.setBackground(Color.WHITE);
		tfFahrer.setForeground(Color.BLACK);
		tfFahrer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFahrer.setColumns(10);
		tfFahrer.setBounds(92, 254, 220, 19);
		contentPane.add(tfFahrer);
		
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
		
		JLabel lblVerlaengerung_Monate = new JLabel("Verlängerung");
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
		
		JCheckBox chkbxWartung = new JCheckBox("");
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
		
		JCheckBox chkbxFoliert = new JCheckBox("");
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
		
		JCheckBox chkbxUVV = new JCheckBox("");
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
		
		JCheckBox chkbxFahrerunterweisung = new JCheckBox("");
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
		
		JCheckBox chkbxWerkstatteinrichtung = new JCheckBox("");
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
		
		JCheckBox chkbxBelueftung = new JCheckBox("");
		chkbxBelueftung.setOpaque(false);
		chkbxBelueftung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chkbxBelueftung.setForeground(Color.BLACK);
		chkbxBelueftung.setBounds(505, 564, 20, 20);
		contentPane.add(chkbxBelueftung);
		
		JLabel lblBelueftung = new JLabel("Belüftung");
		lblBelueftung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBelueftung.setForeground(Color.BLACK);
		lblBelueftung.setBounds(450, 567, 50, 13);
		contentPane.add(lblBelueftung);

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
				int aktualisiert;
				aktualisiert = vergleichsliste.get(i).getBearbeitet();
				//System.out.print(aktualisiert); // DARF NICHT MITHOCHGERECHNET WERDEN
				//System.out.print(vergleichsliste.get(i).getBearbeitet());
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
						emptyTf(tfFahrer);
						emptyTf(tfFinanzstatus);
						emptyTf(tfkm_Stand);
						emptyTf(tfDatum_Erfassung_km_Stand);
						emptyTf(tfZulassungsart);
						emptyTf(tfMotorleistung_KW_P_2);
						emptyTf(tfKostenstelle);

						String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
						conn = DriverManager.getConnection(url);
						String query = "UPDATE FuhrparkTest SET IdentNr=?, FirmaNr=?, NL=?, FZG_Marke=?, FZG_Typ=?, FZG_Bezeichnung=?, amtl_Kennzeichen=?, Erstzulassung=?, Abmeldedatum=?, Fahrer=?, Fahrer2=?, Finanzstatus=?, Bank_Leasinggesellschaft=?, VertragsNr=?, Leasingdauer_Monate=?, Verlaengerung_Monate=?, Leasingrate_zzgl_MwSt_Fahrzeug=?, Vertragsende=?, Bemerkung=?, Restwert_Leasingende=?, Soll_Laufleistung_Km=?, km_Stand=?, Datum_Erfassung_km_Stand=?, Anschaffungswert__Netto=?, Finanzierungsrate=?, Wartung=?, Zulassungsart=?, Motorleistung_KW_P_2=?, Sommerreifen=?, Winterreifen=?, Kostenstelle=?, Foliert=?, Typ=?, UVV=?, Fahrerunterweisung=?, Werkstatteinrichtung=?, Belueftung=?, Bearbeitet=? WHERE ID="
								+ id;

						PreparedStatement pst = conn.prepareStatement(query);

						pst.setString(1, tfIdentNr.getText());
						pst.setString(2, tfFirmaNr.getText());
						pst.setString(3, tfNL.getText());
						pst.setString(4, tfFZG_Marke.getText());
						pst.setString(5, tfFZG_Typ.getText());
						pst.setString(6, tfFZG_Bezeichnung.getText());
						pst.setString(7, tfamtl_Kennzeichen.getText());
						pst.setString(8, tfErstzulassung.getText());
						pst.setString(9, tfAbmeldedatum.getText());
						pst.setString(10, tfFahrer.getText());
						pst.setString(11, tfFahrer2.getText());
						pst.setString(12, tfFinanzstatus.getText());
						pst.setString(13, tfBank_Leasinggesellschaft.getText());
						pst.setString(14, tfVertragsNr.getText());
						pst.setString(15, tfLeasingdauer_Monate.getText());
						pst.setString(16, tfVerlaengerung_Monate.getText());
						pst.setString(17, tfLeasingrate_zzgl_MwSt_Fahrzeug.getText());
						pst.setString(18, tfVertragsende.getText());
						pst.setString(19, tfBemerkung.getText());
						pst.setString(20, tfRestwert_Leasingende.getText());
						pst.setString(21, tfSoll_Laufleistung_Km.getText());
						pst.setString(22, tfkm_Stand.getText());
						pst.setString(23, tfDatum_Erfassung_km_Stand.getText());
						pst.setString(24, tfAnschaffungswert__Netto.getText());
						pst.setString(25, tfFinanzierungsrate.getText());
						boolean wartung = false;
						if (chkbxWartung.isSelected()) {
							wartung = true;
						}
						if (wartung) {
							pst.setString(26, "1");
						} else {
							pst.setString(26, "0");
						}
						pst.setString(27, tfZulassungsart.getText());
						pst.setString(28, tfMotorleistung_KW_P_2.getText());
						pst.setString(29, tfSommerreifen.getText());
						pst.setString(30, tfWinterreifen.getText());
						pst.setString(31, tfKostenstelle.getText());
						boolean foliert = false;
						if (chkbxFoliert.isSelected()) {
							foliert = true;
						}
						if (foliert) {
							pst.setString(32, "1");
						} else {
							pst.setString(32, "0");
						}
						pst.setString(33, tfTyp.getText());
						boolean uvv = false;
						if (chkbxUVV.isSelected()) {
							uvv = true;
						}
						if (uvv) {
							pst.setString(34, "1");
						} else {
							pst.setString(34, "0");
						}
						boolean fahrerunterweisung = false;
						if (chkbxFahrerunterweisung.isSelected()) {
							fahrerunterweisung = true;
						}
						if (fahrerunterweisung) {
							pst.setString(35, "1");
						} else {
							pst.setString(35, "0");
						}
						boolean werkstatteinrichtung = false;
						if (chkbxWerkstatteinrichtung.isSelected()) {
							werkstatteinrichtung = true;
						}
						if (werkstatteinrichtung) {
							pst.setString(36, "1");
						} else {
							pst.setString(36, "0");
						}
						boolean belueftung = false;
						if (chkbxBelueftung.isSelected()) {
							belueftung = true;
						}
						if (belueftung) {
							pst.setString(37, "1");
						} else {
							pst.setString(37, "0");
						}						
						try {
							fahrzeugliste = fahrzeug();
							// tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
							pst.setInt(38, fahrzeugliste.get(i).getBearbeitet() + 1);
						} catch (IndexOutOfBoundsException e1) {
							//
						}
						pst.executeUpdate();
						show_aktualisiertes_fahrzeug();

						JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
					}

					catch (IndexOutOfBoundsException e2) {
						//JOptionPane.showMessageDialog(null, e);
					} catch (Exception e2) {
						// e2.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Ihre Änderungen wurden nicht gespeichert, bitte überarbeiten Sie die Daten gegebenfalls noch einmal.");
				}
				vergleichsliste = fahrzeug();
				show_aktualisiertes_fahrzeug();
			}

		});

		JButton btnZurueck = new JButton("");
		btnZurueck.setFocusable(false);
		btnZurueck.setBackground(Color.WHITE);
		btnZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnZurueck.setIcon(new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\icons\\pfeil-zurück.png"));
		btnZurueck.setBounds(10, 2, 28, 23);
		contentPane.add(btnZurueck);

		tableFahrzeuge = new JTable();
		tableFahrzeuge.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFahrzeuge.setBorder(null);
		tableFahrzeuge.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "IdentNr", "FirmaNr", "NL", "FZG_Marke", "FZG_Typ", "FZG_Bezeichnung", "amtl_Kennzeichen", "Erstzulassung", 
						"Abmeldedatum", "Fahrer", "Fahrer2", "Finanzstatus", "Bank_Leasinggesellschaft", "VertragsNr", "Leasingdauer_Monate", 
						"Verlaengerung_Monate", "Leasingrate_zzgl_MwSt_Fahrzeug", "Vertragsende", "Bemerkung", "Restwert_Leasingende", 
						"Soll_Laufleistung_Km", "km_Stand", "Datum_Erfassung_km_Stand", "Anschaffungswert__Netto", "Finanzierungsrate", 
						"Wartung", "Zulassungsart", "Motorleistung_KW_P_2", "Sommerreifen", "Winterreifen", "Kostenstelle", "Foliert", 
						"Typ", "UVV", "Fahrerunterweisung", "Werkstatteinrichtung", "Belueftung", "Bearbeitet" }));

		tableFahrzeuge.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		scrollpane(btnSave, btnClear);

		wichtigTf(tfIdentNr);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNL);
		wichtigTf(tfFZG_Marke);
		wichtigTf(tfFZG_Typ);
		wichtigTf(tfFZG_Bezeichnung);
		wichtigTf(tfamtl_Kennzeichen);
		wichtigTf(tfErstzulassung);
		wichtigTf(tfAbmeldedatum);
		wichtigTf(tfFahrer);
		wichtigTf(tfFinanzstatus);
		wichtigTf(tfkm_Stand);
		wichtigTf(tfDatum_Erfassung_km_Stand);
		wichtigTf(tfZulassungsart);
		wichtigTf(tfMotorleistung_KW_P_2);
		wichtigTf(tfKostenstelle);

		tableFahrzeuge.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int i = tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
				TableModel model = tableFahrzeuge.getModel();

				id = model.getValueAt(i, 0).toString();
				tfIdentNr.setText(model.getValueAt(i, 1).toString());
				tfFirmaNr.setText(model.getValueAt(i, 2).toString());
				tfNL.setText(model.getValueAt(i, 3).toString());
				tfFZG_Marke.setText(model.getValueAt(i, 4).toString());
				tfFZG_Typ.setText(model.getValueAt(i, 5).toString());
				tfFZG_Bezeichnung.setText(model.getValueAt(i, 6).toString());
				tfamtl_Kennzeichen.setText(model.getValueAt(i, 7).toString());
				tfErstzulassung.setText(model.getValueAt(i, 8).toString());
				tfAbmeldedatum.setText(model.getValueAt(i, 9).toString());
				tfFahrer.setText(model.getValueAt(i, 10).toString());
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
				String belueftung = model.getValueAt(i, 36).toString();
				switch (belueftung) {
				case "1":
					chkbxBelueftung.setSelected(true);
					break;
				case "0":
					chkbxBelueftung.setSelected(false);
					break;
				}
			}
		});

		vergleichsliste = fahrzeug();

		show_fahrzeug();
	}

	public static ArrayList<Fahrzeug> fahrzeug() {
		ArrayList<Fahrzeug> fahrzeugliste = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1 = "Select * from FuhrparkTest";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query1);
			Fahrzeug fahrzeug;
			while (rs.next()) {
				fahrzeug = new Fahrzeug(
				rs.getInt("ID"),
				rs.getString("IdentNr"),
				rs.getString("FirmaNr"),
				rs.getString("NL"),
				rs.getString("FZG_Marke"),
				rs.getString("FZG_Typ"),
				rs.getString("FZG_Bezeichnung"),
				rs.getString("amtl_Kennzeichen"),
				rs.getString("Erstzulassung"),
				rs.getString("Abmeldedatum"),
				rs.getString("Fahrer"),
				rs.getString("Fahrer2"),
				rs.getString("Finanzstatus"),
				rs.getString("Bank_Leasinggesellschaft"),
				rs.getString("VertragsNr"),
				rs.getString("Leasingdauer_Monate"),
				rs.getString("Verlaengerung_Monate"),
				rs.getString("Leasingrate_zzgl_MwSt_Fahrzeug"),
				rs.getString("Vertragsende"),
				rs.getString("Bemerkung"),
				rs.getString("Restwert_Leasingende"),
				rs.getString("Soll_Laufleistung_Km"),
				rs.getString("km_Stand"),
				rs.getString("Datum_Erfassung_km_Stand"),
				rs.getString("Anschaffungswert__Netto"),
				rs.getString("Finanzierungsrate"),
				rs.getString("Wartung"),
				rs.getString("Zulassungsart"),
				rs.getString("Motorleistung_KW_P_2"),
				rs.getString("Sommerreifen"),
				rs.getString("Winterreifen"),
				rs.getString("Kostenstelle"),
				rs.getString("Foliert"),
				rs.getString("Typ"),
				rs.getInt("UVV"),
				rs.getInt("Fahrerunterweisung"),
				rs.getInt("Werkstatteinrichtung"),
				rs.getInt("Belueftung"),
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
		Object[] row = new Object[39];
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
			row[38] = fahrzeug.get(i).getBearbeitet();
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

	public void scrollpane(JButton btnSave, JButton btnClear) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(622, 50, 630, 574);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		scrollPane.setViewportView(tableFahrzeuge);

		JLabel lblBackground_2 = new JLabel("");
		lblBackground_2.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground.setBounds(0, 0, 1262, 647);
		contentPane.add(lblBackground);

		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(final WindowEvent evt) {
				if (evt.getNewState() == MAXIMIZED_BOTH) {
					contentPane.remove(lblBackground);
					scrollPane.setBounds(635, 50, 1272, 956);
					btnSave.setBounds(10, 986, 180, 23);
					btnClear.setBounds(1619, 26, 19, 18);
					lblBackground_2.setBounds(658, 0, 1262, 651);
					lblBackground.setBounds(0, 0, 1262, 651);
					contentPane.add(lblBackground_2);
					contentPane.add(lblBackground);
					tfSuche.setBounds(10, 26, 1609, 19);
				} else {
					scrollPane.setBounds(622, 50, 630, 574);
					btnSave.setBounds(10, 605, 180, 23);
					lblBackground.setBounds(0, 0, 1262, 647);
					contentPane.remove(lblBackground_2);
					tfSuche.setBounds(10, 26, 980, 19);
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
}
package Code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;

public class FahrzeugAnlegenMaske extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	static Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private static JTable tableFahrzeuge;
	
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
	private JTextField tfzulaessiges_Gesamtgew_F_1;
	private JTextField tfMotorleistung_KW_P_2;
	private JTextField tfSommerreifen;
	private JTextField tfSommer_T_Typ;
	private JTextField tfWinterreifen;
	private JTextField tfWinter_T_Typ;
	private JTextField tfKostenstelle;
	private JTextField tfkm_Stand_Jan;
	private JTextField tfkm_Stand_Jan_Vorjahr;
	private JTextField tfkm_Stand_Jan_VorVorjahr;
	private JTextField tfHaftpflicht;
	private JTextField tfKasko;
	private JTextField tfQuartal;
	private JTextField tfSteuer;
	private JTextField tfFarbe_Auto;
	private JTextField tfFolieren_Farbe;
	private JTextField tfRegale_Geleast_Gekauft;
	private JTextField tfTyp;
	private JTextField tfBelueftung_wegen_Gas;
	private JTextField tfErstzulassung;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FahrzeugAnlegenMaske frame = new FahrzeugAnlegenMaske();
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
	public FahrzeugAnlegenMaske() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1710, 681);
		contentPane = new JPanel();
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSave = new JButton("Anlegen");

		btnSave.setBounds(31, 600, 180, 23);
		contentPane.add(btnSave);
		
		JLabel lblFirmaNr = new JLabel("FirmaNr");
		lblFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFirmaNr.setForeground(Color.BLACK);
		lblFirmaNr.setBounds(23, 53, 45, 13);
		contentPane.add(lblFirmaNr);
		
		tfFirmaNr = new JTextField();
		tfFirmaNr.setForeground(Color.BLACK);
		tfFirmaNr.setBackground(Color.WHITE);
		tfFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfFirmaNr.setBounds(77, 50, 102, 19);
		contentPane.add(tfFirmaNr);
		tfFirmaNr.setColumns(10);
		
		JLabel lblNiederlassung = new JLabel("NL");
		lblNiederlassung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNiederlassung.setForeground(Color.BLACK);
		lblNiederlassung.setBounds(23, 78, 64, 13);
		contentPane.add(lblNiederlassung);
		
		tfNL = new JTextField();
		tfNL.setForeground(Color.BLACK);
		tfNL.setBackground(Color.WHITE);
		tfNL.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfNL.setColumns(10);
		tfNL.setBounds(77, 75, 248, 19);
		contentPane.add(tfNL);
		
		JLabel lblFZG_Marke = new JLabel("Marke");
		lblFZG_Marke.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFZG_Marke.setForeground(Color.BLACK);
		lblFZG_Marke.setBounds(23, 113, 45, 13);
		contentPane.add(lblFZG_Marke);
		
		tfFZG_Marke = new JTextField();
		tfFZG_Marke.setForeground(Color.BLACK);
		tfFZG_Marke.setBackground(Color.WHITE);
		tfFZG_Marke.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfFZG_Marke.setColumns(10);
		tfFZG_Marke.setBounds(77, 110, 102, 19);
		contentPane.add(tfFZG_Marke);
		
		JLabel lblFZG_Typ = new JLabel("Typ");
		lblFZG_Typ.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFZG_Typ.setForeground(Color.BLACK);
		lblFZG_Typ.setBounds(189, 113, 45, 13);
		contentPane.add(lblFZG_Typ);
		
		tfFZG_Bezeichnung = new JTextField();
		tfFZG_Bezeichnung.setForeground(Color.BLACK);
		tfFZG_Bezeichnung.setBackground(Color.WHITE);
		tfFZG_Bezeichnung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfFZG_Bezeichnung.setColumns(10);
		tfFZG_Bezeichnung.setBounds(105, 135, 220, 19);
		contentPane.add(tfFZG_Bezeichnung);
		
		JLabel lblFZG_Bezeichnung = new JLabel("Bezeichnung");
		lblFZG_Bezeichnung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFZG_Bezeichnung.setForeground(Color.BLACK);
		lblFZG_Bezeichnung.setBounds(23, 138, 64, 13);
		contentPane.add(lblFZG_Bezeichnung);
		
		JLabel lblamtl_Kennzeichen = new JLabel("Kennzeichen");
		lblamtl_Kennzeichen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblamtl_Kennzeichen.setForeground(Color.BLACK);
		lblamtl_Kennzeichen.setBounds(23, 163, 64, 13);
		contentPane.add(lblamtl_Kennzeichen);
		
		tfamtl_Kennzeichen = new JTextField();
		tfamtl_Kennzeichen.setForeground(Color.BLACK);
		tfamtl_Kennzeichen.setBackground(Color.WHITE);
		tfamtl_Kennzeichen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfamtl_Kennzeichen.setColumns(10);
		tfamtl_Kennzeichen.setBounds(105, 160, 220, 19);
		contentPane.add(tfamtl_Kennzeichen);
		
		tfFZG_Typ = new JTextField();
		tfFZG_Typ.setForeground(Color.BLACK);
		tfFZG_Typ.setBackground(Color.WHITE);
		tfFZG_Typ.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfFZG_Typ.setColumns(10);
		tfFZG_Typ.setBounds(223, 110, 102, 19);
		contentPane.add(tfFZG_Typ);
		
		JLabel lblErstzulassung = new JLabel("Erstzulassung");
		lblErstzulassung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblErstzulassung.setForeground(Color.BLACK);
		lblErstzulassung.setBounds(23, 188, 80, 13);
		contentPane.add(lblErstzulassung);
		
		tfErstzulassung = new JTextField();
		tfErstzulassung.setBackground(Color.WHITE);
		tfErstzulassung.setForeground(Color.BLACK);
		tfErstzulassung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfErstzulassung.setColumns(10);
		tfErstzulassung.setBounds(105, 185, 120, 19);
		contentPane.add(tfErstzulassung);
		
		JLabel lblDatum_Erfassung_km_Stand = new JLabel("Kilometerstand erfasst am");
		lblDatum_Erfassung_km_Stand.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblDatum_Erfassung_km_Stand.setForeground(Color.BLACK);
		lblDatum_Erfassung_km_Stand.setBounds(23, 513, 120, 13);
		contentPane.add(lblDatum_Erfassung_km_Stand);
		
		tfDatum_Erfassung_km_Stand = new JTextField();
		tfDatum_Erfassung_km_Stand.setBackground(Color.WHITE);
		tfDatum_Erfassung_km_Stand.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfDatum_Erfassung_km_Stand.setForeground(Color.BLACK);
		tfDatum_Erfassung_km_Stand.setColumns(10);
		tfDatum_Erfassung_km_Stand.setBounds(205, 510, 120, 19);
		contentPane.add(tfDatum_Erfassung_km_Stand);
		
		JLabel lblAbmeldedatum = new JLabel("Abmeldedatum");
		lblAbmeldedatum.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAbmeldedatum.setForeground(Color.BLACK);
		lblAbmeldedatum.setBounds(23, 213, 80, 13);
		contentPane.add(lblAbmeldedatum);
		
		tfAbmeldedatum = new JTextField();
		tfAbmeldedatum.setBackground(Color.WHITE);
		tfAbmeldedatum.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfAbmeldedatum.setForeground(Color.BLACK);
		tfAbmeldedatum.setColumns(10);
		tfAbmeldedatum.setBounds(105, 210, 120, 19);
		contentPane.add(tfAbmeldedatum);
		
		JLabel lblFahrer = new JLabel("Fahrer");
		lblFahrer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFahrer.setForeground(Color.BLACK);
		lblFahrer.setBounds(23, 238, 64, 13);
		contentPane.add(lblFahrer);
		
		tfFahrer = new JTextField();
		tfFahrer.setBackground(Color.WHITE);
		tfFahrer.setForeground(Color.BLACK);
		tfFahrer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfFahrer.setColumns(10);
		tfFahrer.setBounds(105, 235, 220, 19);
		contentPane.add(tfFahrer);
		
		JLabel lblFahrer2 = new JLabel("Fahrer2");
		lblFahrer2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFahrer2.setForeground(Color.BLACK);
		lblFahrer2.setBounds(23, 263, 64, 13);
		contentPane.add(lblFahrer2);
		
		tfFahrer2 = new JTextField();
		tfFahrer2.setBackground(Color.WHITE);
		tfFahrer2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfFahrer2.setForeground(Color.BLACK);
		tfFahrer2.setColumns(10);
		tfFahrer2.setBounds(105, 260, 220, 19);
		contentPane.add(tfFahrer2);
		
		JLabel lblFinanzstatus = new JLabel("Finanzstatus");
		lblFinanzstatus.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFinanzstatus.setForeground(Color.BLACK);
		lblFinanzstatus.setBounds(23, 288, 64, 13);
		contentPane.add(lblFinanzstatus);
		
		tfFinanzstatus = new JTextField();
		tfFinanzstatus.setForeground(Color.BLACK);
		tfFinanzstatus.setBackground(Color.WHITE);
		tfFinanzstatus.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfFinanzstatus.setColumns(10);
		tfFinanzstatus.setBounds(105, 285, 220, 19);
		contentPane.add(tfFinanzstatus);
		
		JLabel lblBank_Leasinggesellschaft = new JLabel("Bank/Leasinggesellschaft");
		lblBank_Leasinggesellschaft.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblBank_Leasinggesellschaft.setForeground(Color.BLACK);
		lblBank_Leasinggesellschaft.setBounds(23, 313, 64, 13);
		contentPane.add(lblBank_Leasinggesellschaft);
		
		tfBank_Leasinggesellschaft = new JTextField();
		tfBank_Leasinggesellschaft.setForeground(Color.BLACK);
		tfBank_Leasinggesellschaft.setBackground(Color.WHITE);
		tfBank_Leasinggesellschaft.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfBank_Leasinggesellschaft.setColumns(10);
		tfBank_Leasinggesellschaft.setBounds(105, 310, 220, 19);
		contentPane.add(tfBank_Leasinggesellschaft);
		
		JLabel lblVertragsNr = new JLabel("VertragsNr");
		lblVertragsNr.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblVertragsNr.setForeground(Color.BLACK);
		lblVertragsNr.setBounds(23, 338, 64, 13);
		contentPane.add(lblVertragsNr);
		
		tfVertragsNr = new JTextField();
		tfVertragsNr.setForeground(Color.BLACK);
		tfVertragsNr.setBackground(Color.WHITE);
		tfVertragsNr.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfVertragsNr.setColumns(10);
		tfVertragsNr.setBounds(105, 335, 220, 19);
		contentPane.add(tfVertragsNr);
		
		JLabel lblLeasingdauer_Monate = new JLabel("Leasingdauer");
		lblLeasingdauer_Monate.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLeasingdauer_Monate.setForeground(Color.BLACK);
		lblLeasingdauer_Monate.setBounds(23, 363, 64, 13);
		contentPane.add(lblLeasingdauer_Monate);
		
		JLabel lblVerlaengerung_Monate = new JLabel("Verlängerung");
		lblVerlaengerung_Monate.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblVerlaengerung_Monate.setForeground(Color.BLACK);
		lblVerlaengerung_Monate.setBounds(181, 363, 80, 13);
		contentPane.add(lblVerlaengerung_Monate);
		
		JLabel lblVertragsende = new JLabel("Vertragsende");
		lblVertragsende.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblVertragsende.setForeground(Color.BLACK);
		lblVertragsende.setBounds(23, 388, 64, 13);
		contentPane.add(lblVertragsende);
		
		JLabel lblLeasingrate_zzgl_MwSt_Fahrzeug = new JLabel("Leasingrate zzgl. MwSt");
		lblLeasingrate_zzgl_MwSt_Fahrzeug.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblLeasingrate_zzgl_MwSt_Fahrzeug.setForeground(Color.BLACK);
		lblLeasingrate_zzgl_MwSt_Fahrzeug.setBounds(23, 413, 120, 13);
		contentPane.add(lblLeasingrate_zzgl_MwSt_Fahrzeug);
		
		JLabel lblBemerkung = new JLabel("Bemerkung");
		lblBemerkung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblBemerkung.setForeground(Color.BLACK);
		lblBemerkung.setBounds(346, 553, 64, 13);
		contentPane.add(lblBemerkung);
		
		tfLeasingdauer_Monate = new JTextField();
		tfLeasingdauer_Monate.setBackground(Color.WHITE);
		tfLeasingdauer_Monate.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfLeasingdauer_Monate.setForeground(Color.BLACK);
		tfLeasingdauer_Monate.setColumns(10);
		tfLeasingdauer_Monate.setBounds(105, 360, 64, 19);
		contentPane.add(tfLeasingdauer_Monate);
		
		tfVerlaengerung_Monate = new JTextField();
		tfVerlaengerung_Monate.setBackground(Color.WHITE);
		tfVerlaengerung_Monate.setForeground(Color.BLACK);
		tfVerlaengerung_Monate.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfVerlaengerung_Monate.setColumns(10);
		tfVerlaengerung_Monate.setBounds(261, 360, 64, 19);
		contentPane.add(tfVerlaengerung_Monate);
		
		tfVertragsende = new JTextField();
		tfVertragsende.setBackground(Color.WHITE);
		tfVertragsende.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfVertragsende.setForeground(Color.BLACK);
		tfVertragsende.setColumns(10);
		tfVertragsende.setBounds(105, 385, 220, 19);
		contentPane.add(tfVertragsende);
		
		tfLeasingrate_zzgl_MwSt_Fahrzeug = new JTextField();
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setForeground(Color.BLACK);
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setBackground(Color.WHITE);
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setColumns(10);
		tfLeasingrate_zzgl_MwSt_Fahrzeug.setBounds(142, 410, 183, 19);
		contentPane.add(tfLeasingrate_zzgl_MwSt_Fahrzeug);
		
		tfBemerkung = new JTextField();
		tfBemerkung.setForeground(Color.BLACK);
		tfBemerkung.setBackground(Color.WHITE);
		tfBemerkung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfBemerkung.setColumns(10);
		tfBemerkung.setBounds(435, 550, 190, 19);
		contentPane.add(tfBemerkung);
		
		JLabel lblRestwert_Leasingende = new JLabel("Restwert Leasingende");
		lblRestwert_Leasingende.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblRestwert_Leasingende.setForeground(Color.BLACK);
		lblRestwert_Leasingende.setBounds(23, 438, 102, 13);
		contentPane.add(lblRestwert_Leasingende);
		
		tfRestwert_Leasingende = new JTextField();
		tfRestwert_Leasingende.setBackground(Color.WHITE);
		tfRestwert_Leasingende.setForeground(Color.BLACK);
		tfRestwert_Leasingende.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfRestwert_Leasingende.setColumns(10);
		tfRestwert_Leasingende.setBounds(165, 435, 160, 19);
		contentPane.add(tfRestwert_Leasingende);
		
		tfSoll_Laufleistung_Km = new JTextField();
		tfSoll_Laufleistung_Km.setBackground(Color.WHITE);
		tfSoll_Laufleistung_Km.setForeground(Color.BLACK);
		tfSoll_Laufleistung_Km.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfSoll_Laufleistung_Km.setColumns(10);
		tfSoll_Laufleistung_Km.setBounds(165, 460, 160, 19);
		contentPane.add(tfSoll_Laufleistung_Km);
		
		JLabel lblSoll_Laufleistung_Km = new JLabel("Soll Laufleistung (km)");
		lblSoll_Laufleistung_Km.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblSoll_Laufleistung_Km.setForeground(Color.BLACK);
		lblSoll_Laufleistung_Km.setBounds(23, 463, 102, 13);
		contentPane.add(lblSoll_Laufleistung_Km);
		
		JLabel lblkm_Stand = new JLabel("Kilometerstand");
		lblkm_Stand.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblkm_Stand.setForeground(Color.BLACK);
		lblkm_Stand.setBounds(23, 488, 102, 13);
		contentPane.add(lblkm_Stand);
		
		tfkm_Stand = new JTextField();
		tfkm_Stand.setBackground(Color.WHITE);
		tfkm_Stand.setForeground(Color.BLACK);
		tfkm_Stand.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfkm_Stand.setColumns(10);
		tfkm_Stand.setBounds(165, 485, 160, 19);
		contentPane.add(tfkm_Stand);
		
		JLabel lblAnschaffungswert__Netto = new JLabel("Anschaffungswert");
		lblAnschaffungswert__Netto.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAnschaffungswert__Netto.setForeground(Color.BLACK);
		lblAnschaffungswert__Netto.setBounds(346, 53, 120, 13);
		contentPane.add(lblAnschaffungswert__Netto);
		
		tfAnschaffungswert__Netto = new JTextField();
		tfAnschaffungswert__Netto.setBackground(Color.WHITE);
		tfAnschaffungswert__Netto.setForeground(Color.BLACK);
		tfAnschaffungswert__Netto.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfAnschaffungswert__Netto.setColumns(10);
		tfAnschaffungswert__Netto.setBounds(471, 50, 154, 19);
		contentPane.add(tfAnschaffungswert__Netto);
		
		JLabel lblFinanzierungsrate = new JLabel("Finanzierungsrate");
		lblFinanzierungsrate.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFinanzierungsrate.setForeground(Color.BLACK);
		lblFinanzierungsrate.setBounds(346, 78, 120, 13);
		contentPane.add(lblFinanzierungsrate);
		
		tfFinanzierungsrate = new JTextField();
		tfFinanzierungsrate.setBackground(Color.WHITE);
		tfFinanzierungsrate.setForeground(Color.BLACK);
		tfFinanzierungsrate.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfFinanzierungsrate.setColumns(10);
		tfFinanzierungsrate.setBounds(471, 75, 154, 19);
		contentPane.add(tfFinanzierungsrate);
		
		JLabel lblWartung = new JLabel("Wartung");
		lblWartung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblWartung.setForeground(Color.BLACK);
		lblWartung.setBounds(23, 548, 45, 13);
		contentPane.add(lblWartung);
		
		JCheckBox chkbxWartung = new JCheckBox("");
		chkbxWartung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		chkbxWartung.setForeground(Color.BLACK);
		chkbxWartung.setBounds(73, 545, 21, 21);
		contentPane.add(chkbxWartung);
		
		tfZulassungsart = new JTextField();
		tfZulassungsart.setBackground(Color.WHITE);
		tfZulassungsart.setForeground(Color.BLACK);
		tfZulassungsart.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfZulassungsart.setColumns(10);
		tfZulassungsart.setBounds(471, 100, 154, 19);
		contentPane.add(tfZulassungsart);
		
		JLabel lblZulassungsart = new JLabel("Zulassungsart");
		lblZulassungsart.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblZulassungsart.setForeground(Color.BLACK);
		lblZulassungsart.setBounds(346, 103, 120, 13);
		contentPane.add(lblZulassungsart);
		
		JLabel lblzulaessiges_Gesamtgew_F_1 = new JLabel("zulässiges Gesamtgewicht");
		lblzulaessiges_Gesamtgew_F_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblzulaessiges_Gesamtgew_F_1.setForeground(Color.BLACK);
		lblzulaessiges_Gesamtgew_F_1.setBounds(346, 128, 120, 13);
		contentPane.add(lblzulaessiges_Gesamtgew_F_1);
		
		tfzulaessiges_Gesamtgew_F_1 = new JTextField();
		tfzulaessiges_Gesamtgew_F_1.setBackground(Color.WHITE);
		tfzulaessiges_Gesamtgew_F_1.setForeground(Color.BLACK);
		tfzulaessiges_Gesamtgew_F_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfzulaessiges_Gesamtgew_F_1.setColumns(10);
		tfzulaessiges_Gesamtgew_F_1.setBounds(471, 125, 154, 19);
		contentPane.add(tfzulaessiges_Gesamtgew_F_1);
		
		JLabel lblMotorleistung_KW_P_2 = new JLabel("Motorleistung (ps)");
		lblMotorleistung_KW_P_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblMotorleistung_KW_P_2.setForeground(Color.BLACK);
		lblMotorleistung_KW_P_2.setBounds(346, 153, 120, 13);
		contentPane.add(lblMotorleistung_KW_P_2);
		
		tfMotorleistung_KW_P_2 = new JTextField();
		tfMotorleistung_KW_P_2.setBackground(Color.WHITE);
		tfMotorleistung_KW_P_2.setForeground(Color.BLACK);
		tfMotorleistung_KW_P_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfMotorleistung_KW_P_2.setColumns(10);
		tfMotorleistung_KW_P_2.setBounds(471, 150, 64, 19);
		contentPane.add(tfMotorleistung_KW_P_2);
		
		JLabel lblSommerreifen = new JLabel("Sommerreifen");
		lblSommerreifen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblSommerreifen.setForeground(Color.BLACK);
		lblSommerreifen.setBounds(346, 178, 120, 13);
		contentPane.add(lblSommerreifen);
		
		tfSommerreifen = new JTextField();
		tfSommerreifen.setBackground(Color.WHITE);
		tfSommerreifen.setForeground(Color.BLACK);
		tfSommerreifen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfSommerreifen.setColumns(10);
		tfSommerreifen.setBounds(471, 175, 154, 19);
		contentPane.add(tfSommerreifen);
		
		JLabel lblSommer_T_Typ = new JLabel("Sommer_T_Typ");
		lblSommer_T_Typ.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblSommer_T_Typ.setForeground(Color.BLACK);
		lblSommer_T_Typ.setBounds(346, 203, 120, 13);
		contentPane.add(lblSommer_T_Typ);
		
		tfSommer_T_Typ = new JTextField();
		tfSommer_T_Typ.setBackground(Color.WHITE);
		tfSommer_T_Typ.setForeground(Color.BLACK);
		tfSommer_T_Typ.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfSommer_T_Typ.setColumns(10);
		tfSommer_T_Typ.setBounds(471, 200, 154, 19);
		contentPane.add(tfSommer_T_Typ);
		
		JLabel lblWinterreifen = new JLabel("Winterreifen");
		lblWinterreifen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblWinterreifen.setForeground(Color.BLACK);
		lblWinterreifen.setBounds(346, 228, 120, 13);
		contentPane.add(lblWinterreifen);
		
		tfWinterreifen = new JTextField();
		tfWinterreifen.setBackground(Color.WHITE);
		tfWinterreifen.setForeground(Color.BLACK);
		tfWinterreifen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfWinterreifen.setColumns(10);
		tfWinterreifen.setBounds(471, 225, 154, 19);
		contentPane.add(tfWinterreifen);
		
		JLabel lblWinter_T_Typ = new JLabel("Winter_T_Typ");
		lblWinter_T_Typ.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblWinter_T_Typ.setForeground(Color.BLACK);
		lblWinter_T_Typ.setBounds(346, 253, 120, 13);
		contentPane.add(lblWinter_T_Typ);
		
		tfWinter_T_Typ = new JTextField();
		tfWinter_T_Typ.setBackground(Color.WHITE);
		tfWinter_T_Typ.setForeground(Color.BLACK);
		tfWinter_T_Typ.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfWinter_T_Typ.setColumns(10);
		tfWinter_T_Typ.setBounds(471, 250, 154, 19);
		contentPane.add(tfWinter_T_Typ);
		
		JLabel lblKostenstelle = new JLabel("Kostenstelle");
		lblKostenstelle.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblKostenstelle.setForeground(Color.BLACK);
		lblKostenstelle.setBounds(346, 278, 120, 13);
		contentPane.add(lblKostenstelle);
		
		tfKostenstelle = new JTextField();
		tfKostenstelle.setBackground(Color.WHITE);
		tfKostenstelle.setForeground(Color.BLACK);
		tfKostenstelle.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfKostenstelle.setColumns(10);
		tfKostenstelle.setBounds(471, 275, 154, 19);
		contentPane.add(tfKostenstelle);
		
		JLabel lblkm_Stand_Jan_Y = new JLabel("Kilometerstand Jan");
		lblkm_Stand_Jan_Y.setForeground(Color.BLACK);
		lblkm_Stand_Jan_Y.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblkm_Stand_Jan_Y.setBounds(346, 303, 120, 13);
		contentPane.add(lblkm_Stand_Jan_Y);
	
		tfkm_Stand_Jan = new JTextField();
		tfkm_Stand_Jan.setBackground(Color.WHITE);
		tfkm_Stand_Jan.setForeground(Color.BLACK);
		tfkm_Stand_Jan.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfkm_Stand_Jan.setColumns(10);
		tfkm_Stand_Jan.setBounds(471, 300, 154, 19);
		contentPane.add(tfkm_Stand_Jan);
		
		JLabel lblkm_Stand_Jan_VJ = new JLabel("Kilometerstand Jan Vj.");
		lblkm_Stand_Jan_VJ.setForeground(Color.BLACK);
		lblkm_Stand_Jan_VJ.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblkm_Stand_Jan_VJ.setBounds(346, 328, 120, 13);
		contentPane.add(lblkm_Stand_Jan_VJ);
		
		tfkm_Stand_Jan_Vorjahr = new JTextField();
		tfkm_Stand_Jan_Vorjahr.setBackground(Color.WHITE);
		tfkm_Stand_Jan_Vorjahr.setForeground(Color.BLACK);
		tfkm_Stand_Jan_Vorjahr.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfkm_Stand_Jan_Vorjahr.setColumns(10);
		tfkm_Stand_Jan_Vorjahr.setBounds(471, 325, 154, 19);
		contentPane.add(tfkm_Stand_Jan_Vorjahr);
		
		JLabel lblkm_Stand_Jan_VVJ = new JLabel("Kilometerstand Jan Vvj.");
		lblkm_Stand_Jan_VVJ.setForeground(Color.BLACK);
		lblkm_Stand_Jan_VVJ.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblkm_Stand_Jan_VVJ.setBounds(346, 353, 120, 13);
		contentPane.add(lblkm_Stand_Jan_VVJ);
		
		tfkm_Stand_Jan_VorVorjahr = new JTextField();
		tfkm_Stand_Jan_VorVorjahr.setBackground(Color.WHITE);
		tfkm_Stand_Jan_VorVorjahr.setForeground(Color.BLACK);
		tfkm_Stand_Jan_VorVorjahr.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfkm_Stand_Jan_VorVorjahr.setColumns(10);
		tfkm_Stand_Jan_VorVorjahr.setBounds(471, 350, 154, 19);
		contentPane.add(tfkm_Stand_Jan_VorVorjahr);
		
		JLabel lblHaftpflicht = new JLabel("Haftpflicht");
		lblHaftpflicht.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblHaftpflicht.setForeground(Color.BLACK);
		lblHaftpflicht.setBounds(346, 378, 52, 13);
		contentPane.add(lblHaftpflicht);
		
		tfHaftpflicht = new JTextField();
		tfHaftpflicht.setBackground(Color.WHITE);
		tfHaftpflicht.setForeground(Color.BLACK);
		tfHaftpflicht.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfHaftpflicht.setColumns(10);
		tfHaftpflicht.setBounds(401, 375, 88, 19);
		contentPane.add(tfHaftpflicht);
		
		JLabel lblKasko = new JLabel("Kasko");
		lblKasko.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblKasko.setForeground(Color.BLACK);
		lblKasko.setBounds(346, 403, 35, 13);
		contentPane.add(lblKasko);
		
		tfKasko = new JTextField();
		tfKasko.setBackground(Color.WHITE);
		tfKasko.setForeground(Color.BLACK);
		tfKasko.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfKasko.setColumns(10);
		tfKasko.setBounds(401, 400, 88, 19);
		contentPane.add(tfKasko);
		
		JLabel lblQuartal = new JLabel("Quartal");
		lblQuartal.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblQuartal.setForeground(Color.BLACK);
		lblQuartal.setBounds(496, 403, 45, 13);
		contentPane.add(lblQuartal);
		
		tfQuartal = new JTextField();
		tfQuartal.setBackground(Color.WHITE);
		tfQuartal.setForeground(Color.BLACK);
		tfQuartal.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfQuartal.setColumns(10);
		tfQuartal.setBounds(537, 400, 88, 19);
		contentPane.add(tfQuartal);
		
		JLabel lblSteuer = new JLabel("Steuer");
		lblSteuer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblSteuer.setForeground(Color.BLACK);
		lblSteuer.setBounds(496, 378, 35, 13);
		contentPane.add(lblSteuer);
		
		tfSteuer = new JTextField();
		tfSteuer.setBackground(Color.WHITE);
		tfSteuer.setForeground(Color.BLACK);
		tfSteuer.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfSteuer.setColumns(10);
		tfSteuer.setBounds(537, 375, 88, 19);
		contentPane.add(tfSteuer);
		
		JLabel lblFarbe_Auto = new JLabel("Autofarbe");
		lblFarbe_Auto.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFarbe_Auto.setForeground(Color.BLACK);
		lblFarbe_Auto.setBounds(346, 428, 120, 13);
		contentPane.add(lblFarbe_Auto);
		
		tfFarbe_Auto = new JTextField();
		tfFarbe_Auto.setBackground(Color.WHITE);
		tfFarbe_Auto.setForeground(Color.BLACK);
		tfFarbe_Auto.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfFarbe_Auto.setColumns(10);
		tfFarbe_Auto.setBounds(505, 425, 120, 19);
		contentPane.add(tfFarbe_Auto);
		
		JCheckBox chkbxFoliert = new JCheckBox("");
		chkbxFoliert.setFont(new Font("Tahoma", Font.PLAIN, 10));
		chkbxFoliert.setForeground(Color.BLACK);
		chkbxFoliert.setBounds(150, 545, 21, 21);
		contentPane.add(chkbxFoliert);
		
		JLabel lblFoliert = new JLabel("Foliert");
		lblFoliert.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFoliert.setForeground(Color.BLACK);
		lblFoliert.setBounds(110, 548, 35, 13);
		contentPane.add(lblFoliert);
		
		JLabel lblFolieren_Planung = new JLabel("Folierung in Planung");
		lblFolieren_Planung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFolieren_Planung.setForeground(Color.BLACK);
		lblFolieren_Planung.setBounds(188, 548, 102, 13);
		contentPane.add(lblFolieren_Planung);
		
		JCheckBox chkbxFolieren_Planung = new JCheckBox("");
		chkbxFolieren_Planung.setFont(new Font("Tahoma", Font.PLAIN, 10));
		chkbxFolieren_Planung.setForeground(Color.BLACK);
		chkbxFolieren_Planung.setBounds(290, 545, 21, 21);
		contentPane.add(chkbxFolieren_Planung);
		
		JLabel lblFolieren_Farbe = new JLabel("Folierfarbe");
		lblFolieren_Farbe.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblFolieren_Farbe.setForeground(Color.BLACK);
		lblFolieren_Farbe.setBounds(346, 453, 120, 13);
		contentPane.add(lblFolieren_Farbe);
		
		tfFolieren_Farbe = new JTextField();
		tfFolieren_Farbe.setBackground(Color.WHITE);
		tfFolieren_Farbe.setForeground(Color.BLACK);
		tfFolieren_Farbe.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfFolieren_Farbe.setColumns(10);
		tfFolieren_Farbe.setBounds(505, 450, 120, 19);
		contentPane.add(tfFolieren_Farbe);
		
		JLabel lblRegale_Geleast_Gekauft = new JLabel("Regalfinanzstatus");
		lblRegale_Geleast_Gekauft.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblRegale_Geleast_Gekauft.setForeground(Color.BLACK);
		lblRegale_Geleast_Gekauft.setBounds(346, 478, 80, 13);
		contentPane.add(lblRegale_Geleast_Gekauft);
		
		tfRegale_Geleast_Gekauft = new JTextField();
		tfRegale_Geleast_Gekauft.setBackground(Color.WHITE);
		tfRegale_Geleast_Gekauft.setForeground(Color.BLACK);
		tfRegale_Geleast_Gekauft.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfRegale_Geleast_Gekauft.setColumns(10);
		tfRegale_Geleast_Gekauft.setBounds(471, 475, 154, 19);
		contentPane.add(tfRegale_Geleast_Gekauft);
		
		JLabel lblTyp = new JLabel("Regaltyp");
		lblTyp.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblTyp.setForeground(Color.BLACK);
		lblTyp.setBounds(346, 503, 120, 13);
		contentPane.add(lblTyp);
		
		tfTyp = new JTextField();
		tfTyp.setBackground(Color.WHITE);
		tfTyp.setForeground(Color.BLACK);
		tfTyp.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfTyp.setColumns(10);
		tfTyp.setBounds(471, 500, 154, 19);
		contentPane.add(tfTyp);
		
		JLabel lblBelueftung_wegen_Gas = new JLabel("Belüftung");
		lblBelueftung_wegen_Gas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblBelueftung_wegen_Gas.setForeground(Color.BLACK);
		lblBelueftung_wegen_Gas.setBounds(346, 528, 120, 13);
		contentPane.add(lblBelueftung_wegen_Gas);
		
		tfBelueftung_wegen_Gas = new JTextField();
		tfBelueftung_wegen_Gas.setBackground(Color.WHITE);
		tfBelueftung_wegen_Gas.setForeground(Color.BLACK);
		tfBelueftung_wegen_Gas.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfBelueftung_wegen_Gas.setColumns(10);
		tfBelueftung_wegen_Gas.setBounds(471, 525, 154, 19);
		contentPane.add(tfBelueftung_wegen_Gas);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(635, 50, 1040, 521);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
					conn = DriverManager.getConnection(url);
					String query = "insert into FuhrparkTest (FirmaNr, NL, FZG_Marke, FZG_Typ, FZG_Bezeichnung, amtl_Kennzeichen, Erstzulassung, Abmeldedatum, Fahrer, Fahrer2, Finanzstatus, Bank_Leasinggesellschaft, VertragsNr, Leasingdauer_Monate, Verlaengerung_Monate, Leasingrate_zzgl_MwSt_Fahrzeug, Vertragsende, Bemerkung, Restwert_Leasingende, Soll_Laufleistung_Km, km_Stand, Datum_Erfassung_km_Stand, Anschaffungswert__Netto, Finanzierungsrate, Wartung, Zulassungsart, zulaessiges_Gesamtgew_F_1, Motorleistung_KW_P_2, Sommerreifen, Sommer_T_Typ, Winterreifen, Winter_T_Typ, Kostenstelle, km_Stand_Jan_Y, km_Stand_Jan_VJ, km_Stand_Jan_VVJ, Haftpflicht, Kasko, Quartal, Steuer, Farbe_Auto, Foliert, Folieren_Planung, Folieren_Farbe, Regale_Geleast_Gekauft, Typ, Belueftung_wegen_Gas) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.setString(1, tfFirmaNr.getText());
					pst.setString(2, tfNL.getText());
					pst.setString(3, tfFZG_Marke.getText());
					pst.setString(4, tfFZG_Typ.getText());
					pst.setString(5, tfFZG_Bezeichnung.getText());
					pst.setString(6, tfamtl_Kennzeichen.getText());
					pst.setString(7, tfErstzulassung.getText());
					pst.setString(8, tfAbmeldedatum.getText());
					pst.setString(9, tfFahrer.getText());
					pst.setString(10, tfFahrer2.getText());
					pst.setString(11, tfFinanzstatus.getText());
					pst.setString(12, tfBank_Leasinggesellschaft.getText());
					pst.setString(13, tfVertragsNr.getText());
					pst.setString(14, tfLeasingdauer_Monate.getText());
					pst.setString(15, tfVerlaengerung_Monate.getText());
					pst.setString(16, tfLeasingrate_zzgl_MwSt_Fahrzeug.getText());
					pst.setString(17, tfVertragsende.getText());
					pst.setString(18, tfBemerkung.getText());
					pst.setString(19, tfRestwert_Leasingende.getText());
					pst.setString(20, tfSoll_Laufleistung_Km.getText());
					pst.setString(21, tfkm_Stand.getText());
					pst.setString(22, tfDatum_Erfassung_km_Stand.getText());
					pst.setString(23, tfAnschaffungswert__Netto.getText());
					pst.setString(24, tfFinanzierungsrate.getText());
					boolean wartung = false;
					if (chkbxWartung.isSelected()) {
						wartung = true;
					}
					if (wartung) {
						pst.setString(25, "1");
					} else {
						pst.setString(25, "0");
					}
					pst.setString(26, tfZulassungsart.getText());
					pst.setString(27, tfzulaessiges_Gesamtgew_F_1.getText());
					pst.setString(28, tfMotorleistung_KW_P_2.getText());
					pst.setString(29, tfSommerreifen.getText());
					pst.setString(30, tfSommer_T_Typ.getText());
					pst.setString(31, tfWinterreifen.getText());
					pst.setString(32, tfWinter_T_Typ.getText());
					pst.setString(33, tfKostenstelle.getText());
					pst.setString(34, tfkm_Stand_Jan.getText());
					pst.setString(35, tfkm_Stand_Jan_Vorjahr.getText());
					pst.setString(36, tfkm_Stand_Jan_VorVorjahr.getText());
					pst.setString(37, tfHaftpflicht.getText());
					pst.setString(38, tfKasko.getText());
					pst.setString(39, tfQuartal.getText());
					pst.setString(40, tfSteuer.getText());
					pst.setString(41, tfFarbe_Auto.getText());
					boolean foliert = false;
					if (chkbxFoliert.isSelected()) {
						foliert = true;
					}
					if (foliert) {
						pst.setString(42, "1");
					} else {
						pst.setString(42, "0");
					}
					boolean folieren_planung = false;
					if (chkbxFolieren_Planung.isSelected()) {
						folieren_planung = true;
					}
					if (folieren_planung) {
						pst.setString(43, "1");
					} else {
						pst.setString(43, "0");
					}
					pst.setString(44, tfFolieren_Farbe.getText());
					pst.setString(45, tfRegale_Geleast_Gekauft.getText());
					pst.setString(46, tfTyp.getText());
					pst.setString(47, tfBelueftung_wegen_Gas.getText());	

					pst.executeUpdate();

					show_hinzugefuegtes_fahrzeug();

					JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
				}

				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		JButton btnReset = new JButton("Zurücksetzen");
		btnReset.setBounds(237, 600, 180, 23);
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
		
		tableFahrzeuge = new JTable();

		tableFahrzeuge.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableFahrzeuge);
		tableFahrzeuge.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableFahrzeuge.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "FirmaNr", "NL", "FZG_Marke", "FZG_Typ", "FZG_Bezeichnung", "amtl_Kennzeichen", "Erstzulassung", 
						"Abmeldedatum", "Fahrer", "Fahrer2", "Finanzstatus", "Bank_Leasinggesellschaft", "VertragsNr", "Leasingdauer_Monate", 
						"Verlaengerung_Monate", "Leasingrate_zzgl_MwSt_Fahrzeug", "Vertragsende", "Bemerkung", "Restwert_Leasingende", 
						"Soll_Laufleistung_Km", "km_Stand", "Datum_Erfassung_km_Stand", "Anschaffungswert__Netto", "Finanzierungsrate", 
						"Wartung", "Zulassungsart", "zulaessiges_Gesamtgew_F_1", "Motorleistung_KW_P_2", "Sommerreifen", "Sommer_T_Typ", 
						"Winterreifen", "Winter_T_Typ", "Kostenstelle", "km_Stand_Jan", "km_Stand_Jan_Vorjahr", "km_Stand_Jan_VorVorjahr", "Haftpflicht", "Kasko", "Quartal", "Steuer", 
						"Farbe_Auto", "Foliert", "Folieren_Planung", "Folieren_Farbe", "Regale_Geleast_Gekauft", "Typ", "Belueftung_wegen_Gas" }));
		
		tableFahrzeuge.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfFirmaNr.setText("");
				tfNL.setText("");
				tfFZG_Marke.setText("");
				tfFZG_Typ.setText("");
				tfFZG_Bezeichnung.setText("");
				tfamtl_Kennzeichen.setText("");
				tfErstzulassung.setText("");
				tfAbmeldedatum.setText("");
				tfFahrer.setText("");
				tfFahrer2.setText("");
				tfFinanzstatus.setText("");
				tfBank_Leasinggesellschaft.setText("");
				tfVertragsNr.setText("");
				tfLeasingdauer_Monate.setText("");
				tfVerlaengerung_Monate.setText("");
				tfLeasingrate_zzgl_MwSt_Fahrzeug.setText("");
				tfVertragsende.setText("");
				tfBemerkung.setText("");
				tfRestwert_Leasingende.setText("");
				tfSoll_Laufleistung_Km.setText("");
				tfkm_Stand.setText("");
				tfDatum_Erfassung_km_Stand.setText("");
				tfAnschaffungswert__Netto.setText("");
				tfFinanzierungsrate.setText("");
				chkbxWartung.setSelected(false);
				tfZulassungsart.setText("");
				tfzulaessiges_Gesamtgew_F_1.setText("");
				tfMotorleistung_KW_P_2.setText("");
				tfSommerreifen.setText("");
				tfSommer_T_Typ.setText("");
				tfWinterreifen.setText("");
				tfWinter_T_Typ.setText("");
				tfKostenstelle.setText("");
				tfkm_Stand_Jan.setText("");
				tfkm_Stand_Jan_Vorjahr.setText("");
				tfkm_Stand_Jan_VorVorjahr.setText("");
				tfHaftpflicht.setText("");
				tfKasko.setText("");
				tfQuartal.setText("");
				tfSteuer.setText("");
				tfFarbe_Auto.setText("");
				chkbxFoliert.setSelected(false);
				chkbxFolieren_Planung.setSelected(false);
				tfFolieren_Farbe.setText("");
				tfRegale_Geleast_Gekauft.setText("");
				tfTyp.setText("");
				tfBelueftung_wegen_Gas.setText("");
			}
		});
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
				rs.getString("zulaessiges_Gesamtgew_F_1"),
				rs.getString("Motorleistung_KW_P_2"),
				rs.getString("Sommerreifen"),
				rs.getString("Sommer_T_Typ"),
				rs.getString("Winterreifen"),
				rs.getString("Winter_T_Typ"),
				rs.getString("Kostenstelle"),
				rs.getString("km_Stand_Jan"),
				rs.getString("tfkm_Stand_Jan_Vorjahr"),
				rs.getString("tfkm_Stand_Jan_VorVorjahr"),
				rs.getString("Haftpflicht"),
				rs.getString("Kasko"),
				rs.getString("Quartal"),
				rs.getString("Steuer"),
				rs.getString("Farbe_Auto"),
				rs.getString("Foliert"),
				rs.getString("Folieren_Planung"),
				rs.getString("Folieren_Farbe"),
				rs.getString("Regale_Geleast_Gekauft"),
				rs.getString("Typ"),
				rs.getString("Belueftung_wegen_Gas"));
				fahrzeugliste.add(fahrzeug);
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

		return fahrzeugliste;
	}

	public static void show_fahrzeug() {
		DefaultTableModel model = (DefaultTableModel) tableFahrzeuge.getModel();
		ArrayList<Fahrzeug> fahrzeug = fahrzeug();
		Object[] row = new Object[48];
		for (int i = 0; i < fahrzeug.size(); i++) {
			row[0] = fahrzeug.get(i).getID();
			row[1] = fahrzeug.get(i).getFirmaNr();
			row[2] = fahrzeug.get(i).getNL();
			row[3] = fahrzeug.get(i).getFZG_Marke();
			row[4] = fahrzeug.get(i).getFZG_Typ();
			row[5] = fahrzeug.get(i).getFZG_Bezeichnung();
			row[6] = fahrzeug.get(i).getAmtl_Kennzeichen();
			row[7] = fahrzeug.get(i).getErstzulassung();
			row[8] = fahrzeug.get(i).getAbmeldedatum();
			row[9] = fahrzeug.get(i).getFahrer();
			row[10] = fahrzeug.get(i).getFahrer2();
			row[11] = fahrzeug.get(i).getFinanzstatus();
			row[12] = fahrzeug.get(i).getBank_Leasinggesellschaft();
			row[13] = fahrzeug.get(i).getVertragsNr();
			row[14] = fahrzeug.get(i).getLeasingdauer_Monate();
			row[15] = fahrzeug.get(i).getVerlaengerung_Monate();
			row[16] = fahrzeug.get(i).getLeasingrate_zzgl_MwSt_Fahrzeug();
			row[17] = fahrzeug.get(i).getVertragsende();
			row[18] = fahrzeug.get(i).getBemerkung();
			row[19] = fahrzeug.get(i).getRestwert_Leasingende();
			row[20] = fahrzeug.get(i).getSoll_Laufleistung_Km();
			row[21] = fahrzeug.get(i).getKm_Stand();
			row[22] = fahrzeug.get(i).getDatum_Erfassung_km_Stand();
			row[23] = fahrzeug.get(i).getAnschaffungswert_Netto();
			row[24] = fahrzeug.get(i).getFinanzierungsrate();
			row[25] = fahrzeug.get(i).getWartung();
			row[26] = fahrzeug.get(i).getZulassungsart();
			row[27] = fahrzeug.get(i).getZulaessiges_Gesamtgew_F_1();
			row[28] = fahrzeug.get(i).getMotorleistung_KW_P_2();
			row[29] = fahrzeug.get(i).getSommerreifen();
			row[30] = fahrzeug.get(i).getSommer_T_Typ();
			row[31] = fahrzeug.get(i).getWinterreifen();
			row[32] = fahrzeug.get(i).getWinter_T_Typ();
			row[33] = fahrzeug.get(i).getKostenstelle();
			row[34] = fahrzeug.get(i).getKm_Stand_Jan();
			row[35] = fahrzeug.get(i).getKm_Stand_Jan_Vorjahr();
			row[36] = fahrzeug.get(i).getKm_Stand_Jan_VorVorjahr();
			row[37] = fahrzeug.get(i).getHaftpflicht();
			row[38] = fahrzeug.get(i).getKasko();
			row[39] = fahrzeug.get(i).getQuartal();
			row[40] = fahrzeug.get(i).getSteuer();
			row[41] = fahrzeug.get(i).getFarbe_Auto();
			row[42] = fahrzeug.get(i).getFoliert();
			row[43] = fahrzeug.get(i).getFolieren_Planung();
			row[44] = fahrzeug.get(i).getFolieren_Farbe();
			row[45] = fahrzeug.get(i).getRegale_Geleast_Gekauft();
			row[46] = fahrzeug.get(i).getTyp();
			row[47] = fahrzeug.get(i).getBelueftung_wegen_Gas();
			model.addRow(row);
		}
	}

	public static void show_hinzugefuegtes_fahrzeug() {
		try {
			DefaultTableModel model = (DefaultTableModel) tableFahrzeuge.getModel();
			model.setRowCount(0);
			show_fahrzeug();
		} catch (IndexOutOfBoundsException e) {
			//JOptionPane.showMessageDialog(null, e);
		}
		;
	}
}

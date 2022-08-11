package Code;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FahrzeugLoeschenMaske extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	static Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private static JTable tableFahrzeuge;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FahrzeugLoeschenMaske frame = new FahrzeugLoeschenMaske();
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
	public FahrzeugLoeschenMaske() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1710, 681);
		contentPane = new JPanel();
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnDelete = new JButton("Löschen");
		btnDelete.setBounds(31, 600, 180, 23);
		contentPane.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 40, 1634, 521);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
					conn = DriverManager.getConnection(url);
					int i = tableFahrzeuge.getSelectedRow();
					String value = (tableFahrzeuge.getModel().getValueAt(i, 0).toString());
					String query = "DELETE FROM FuhrparkTest WHERE ID="+value;
					PreparedStatement pst = conn.prepareStatement(query);

					pst.executeUpdate();


					show_aktualisiertes_fahrzeug();

					JOptionPane.showMessageDialog(null, "Datensatz wurde gelöscht!");
				}

				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
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
						"Winterreifen", "Winter_T_Typ", "Kostenstelle", "km_Stand_Jan_Y", "km_Stand_Jan_VJ", "km_Stand_Jan_VVJ", "Haftpflicht", "Kasko", "Quartal", "Steuer", 
						"Farbe_Auto", "Foliert", "Folieren_Planung", "Folieren_Farbe", "Regale_Geleast_Gekauft", "Typ", "Belueftung_wegen_Gas" }));
		
		tableFahrzeuge.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JButton btnZurück = new JButton("");
		btnZurück.setBounds(0, 0, 40, 20);
		btnZurück.setBackground(Color.WHITE);
		contentPane.add(btnZurück);
		
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
						rs.getString("km_Stand_Jan_Y"),
						rs.getString("km_Stand_Jan_VJ"),
						rs.getString("km_Stand_Jan_VVJ"),
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

	public static void show_aktualisiertes_fahrzeug() {
		try {
			DefaultTableModel model = (DefaultTableModel) tableFahrzeuge.getModel();
			model.setRowCount(0);
			show_fahrzeug();
		} catch (IndexOutOfBoundsException e) {
			// JOptionPane.showMessageDialog(null, e);
		};
	}
}

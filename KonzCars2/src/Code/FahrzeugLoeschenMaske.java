package Code;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextField;

public class FahrzeugLoeschenMaske extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	static Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private static JTable tableFahrzeuge;
	private JTextField tfSuche;
	private String id = null;
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
		setTitle("KFM Fahrzeug Löschen");
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

		JButton btnDelete = new JButton("Löschen");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDelete.setFocusPainted(false);
		btnDelete.setBackground(SystemColor.inactiveCaption);
		btnDelete.setBounds(10, 605, 180, 23);
		contentPane.add(btnDelete);
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
					conn = DriverManager.getConnection(url);
					String query = "DELETE FROM FuhrparkTest WHERE ID="+id;
					PreparedStatement pst = conn.prepareStatement(query);

					pst.executeUpdate();


					show_aktualisiertes_fahrzeug();

					//JOptionPane.showMessageDialog(null, "Datensatz wurde gelöscht!");
				}

				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		
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
		
		tableFahrzeuge = new JTable();
		tableFahrzeuge.setBorder(null);

		tableFahrzeuge.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFahrzeuge.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "IdentNr", "FirmaNr", "NL", "FZG_Marke", "FZG_Typ", "FZG_Bezeichnung", "amtl_Kennzeichen", "Erstzulassung", 
						"Abmeldedatum", "Fahrer", "Fahrer2", "Finanzstatus", "Bank_Leasinggesellschaft", "VertragsNr", "Leasingdauer_Monate", 
						"Verlaengerung_Monate", "Leasingrate_zzgl_MwSt_Fahrzeug", "Vertragsende", "Bemerkung", "Restwert_Leasingende", 
						"Soll_Laufleistung_Km", "km_Stand", "Datum_Erfassung_km_Stand", "Anschaffungswert__Netto", "Finanzierungsrate", 
						"Wartung", "Zulassungsart", "Motorleistung_KW_P_2", "Sommerreifen", "Winterreifen", "Kostenstelle", "Foliert", 
						"Typ", "UVV", "Fahrerunterweisung", "Werkstatteinrichtung", "Belueftung", "Bearbeitet" }));
		
		tableFahrzeuge.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JButton btnZurück = new JButton("");
		btnZurück.setFocusable(false);
		btnZurück.setBackground(Color.WHITE);
		btnZurück.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnZurück.setIcon(new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\icons\\pfeil-zurück.png"));
		btnZurück.setBounds(10, 2, 28, 23);
		contentPane.add(btnZurück);
		
		tableFahrzeuge.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = tableFahrzeuge.convertRowIndexToModel(tableFahrzeuge.getSelectedRow());
				TableModel model = tableFahrzeuge.getModel();
				id = model.getValueAt(i, 0).toString();
			}
		});
		show_fahrzeug();
		scrollpane(btnDelete, btnClear);
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
		};
	}
	
	public void scrollpane(JButton btnDelete, JButton btnClear) {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 50, 1242, 550);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		scrollPane.setViewportView(tableFahrzeuge);
		
		JLabel lblBackground_2 = new JLabel("");
		lblBackground_2.setIcon(new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground.setBounds(0, 0, 1262, 647);
		contentPane.add(lblBackground);
		
		addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(final WindowEvent evt) {
                if (evt.getNewState() == MAXIMIZED_BOTH) {
                	contentPane.remove(lblBackground);
                	scrollPane.setBounds(10, 50, 1900, 932);
                	btnDelete.setBounds(10, 986, 180, 23);
                	btnClear.setBounds(1619, 26, 19, 18);
                	lblBackground_2.setBounds(658, 0, 1262, 651);
            		lblBackground.setBounds(0, 0, 1262, 651);
            		contentPane.add(lblBackground_2);
            		contentPane.add(lblBackground);
            		tfSuche.setBounds(10, 26, 1638, 19);
            		tfSuche.setBounds(10, 26, 1609, 19);
                }
                else {
                	scrollPane.setBounds(10, 50, 1242, 550);
                	btnDelete.setBounds(10, 605, 180, 23);
                	lblBackground.setBounds(0, 0, 1262, 647);
            		contentPane.remove(lblBackground_2);
            		tfSuche.setBounds(10, 26, 980, 19);
                }
            }
        });
	}
}
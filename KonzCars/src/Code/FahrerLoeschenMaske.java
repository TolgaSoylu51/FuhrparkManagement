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
import javax.swing.JOptionPane;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

public class FahrerLoeschenMaske extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
					FahrerLoeschenMaske frame = new FahrerLoeschenMaske();
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
	public FahrerLoeschenMaske() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1710, 681);
		contentPane = new JPanel();
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnDelete = new JButton("Löschen");

		btnDelete.setBounds(20, 560, 180, 23);
		contentPane.add(btnDelete);

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
					con = DriverManager.getConnection(url);
					int i = tableFahrer.getSelectedRow();
					String value = (tableFahrer.getModel().getValueAt(i, 0).toString());
					String query = "DELETE FROM MitarbeiterTest WHERE ID="+value;
					
					PreparedStatement pst = con.prepareStatement(query);

					pst.executeUpdate();

					show_aktualisierte_fahrerliste();

					JOptionPane.showMessageDialog(null, "Datensatz wurde gelöscht!");
				}

				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});

		JButton btnZurück = new JButton("");
		btnZurück.setBackground(Color.WHITE);
		btnZurück.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hauptmenue frame = new Hauptmenue();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnZurück.setIcon(
				new ImageIcon("C:\\Users\\Tolga.Soylu\\OneDrive - KHW Konzmann GmbH\\Desktop\\back-icon (1).png"));
		btnZurück.setBounds(0, 0, 40, 20);
		contentPane.add(btnZurück);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 31, 1221, 510);
		contentPane.add(scrollPane);

		tableFahrer = new JTable();

		scrollPane.setViewportView(tableFahrer);
		tableFahrer.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Personalnummer", "AktivKZ", "Name", "Vorname", "FirmaNr", "NL_Nr",
						"Fahrerlaubnis", "Erstpruefung", "Pruefungszeitpunkt1", "Kommentar1", "Zweitpruefung",
						"Pruefungszeitpunkt2", "Kommentar2" }));

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
}

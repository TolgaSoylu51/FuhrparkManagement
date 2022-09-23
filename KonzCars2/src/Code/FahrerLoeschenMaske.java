package Code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.awt.SystemColor;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextField;

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
	private JTextField tfSuche;
	private String id = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FahrerLoeschenMaske frame = new FahrerLoeschenMaske();
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
	public FahrerLoeschenMaske() {
		setTitle("KFM Fahrer Löschen");
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
		tfSuche.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfSuche.setColumns(10);
		tfSuche.setBackground(SystemColor.menu);
		tfSuche.setBounds(10, 26, 964, 19);
		contentPane.add(tfSuche);
		
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
					con = DriverManager.getConnection(url);
					String query = "UPDATE MitarbeiterTest SET AktivKZ=? WHERE ID="
							+ id;
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, "4");
					
					pst.executeUpdate();

					show_aktualisierte_fahrerliste();

					//JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
				}

				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
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
		btnZurück.setIcon(
				new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\icons\\pfeil-zurück.png"));
		btnZurück.setBounds(10, 2, 28, 23);
		contentPane.add(btnZurück);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 50, 1242, 550);
		contentPane.add(scrollPane);

		tableFahrer = new JTable();
		tableFahrer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane.setViewportView(tableFahrer);
		tableFahrer.setBorder(null);
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Personalnummer", "AktivKZ", "Name", "Vorname", "FirmaNr", "NL_Nr",
						"Fahrerlaubnis", "Erstpruefung", "Pruefungszeitpunkt1", "Kommentar1", "Zweitpruefung",
						"Pruefungszeitpunkt2", "Kommentar2" }));
		
		JLabel lblBackground_1 = new JLabel("");
		lblBackground_1.setIcon(new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground_1.setBounds(0, 0, 1262, 647);
		contentPane.add(lblBackground_1);
		
		tableFahrer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow());
				TableModel model = tableFahrer.getModel();
				id = model.getValueAt(i, 0).toString();
			}
		});

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
						rs.getString("Kommentar2"));
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
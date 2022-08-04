package Code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.ListSelectionModel;

public class DokumentAnlegenMaske extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfDokumentName;

	static Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private static JTable tableFahrer;
	private JTextField tfDokument;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DokumentAnlegenMaske frame = new DokumentAnlegenMaske();
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
	public DokumentAnlegenMaske() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1710, 681);
		contentPane = new JPanel();
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSave = new JButton("Anlegen");
		btnSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				createBLOB();
				show_hinzugefuegtes_Dokument();
			}
		});

		btnSave.setBounds(20, 466, 180, 23);
		contentPane.add(btnSave);

		tfDokumentName = new JTextField();
		tfDokumentName.setBounds(181, 69, 209, 20);
		contentPane.add(tfDokumentName);
		tfDokumentName.setColumns(10);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.GRAY);
		separator.setBackground(Color.WHITE);
		separator.setBounds(420, 20, 2, 537);
		contentPane.add(separator);

//		btnSave.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try {
//					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
//					con = DriverManager.getConnection(url);
//					String query = "insert into MitarbeiterTest (Personalnummer,AktivKZ,Name,Vorname,FirmaNr,NL_Nr,Fahrerlaubnis,Erstprüfung,Prüfungszeitpunkt1,Kommentar1,Zweitprüfung,Prüfungszeitpunkt2,Kommentar2) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
//					PreparedStatement pst = con.prepareStatement(query);
//					//pst.setString(1, "");
//					pst.setString(1, tfPersonalnummer.getText());
//					pst.setString(2, tfAktivKz.getText());
//					pst.setString(3, tfNachname.getText());
//					pst.setString(4, tfVorname.getText());
//					pst.setString(5, tfFirmaNr.getText());
//					pst.setString(6, tfNlNr.getText());
//
//					boolean fahrerlaubnis = false;
//					if (chckbxFahrerlaubnis.isSelected()) {
//						fahrerlaubnis = true;
//					}
//					if (fahrerlaubnis) {
//						pst.setString(7, "1");
//					} else {
//						pst.setString(7, "0");
//					}
//
//					boolean pruefung1 = false;
//					if (chckbxPruefung1.isSelected()) {
//						pruefung1 = true;
//					}
//					if (pruefung1) {
//						pst.setString(8, "1");
//						;
//					} else {
//						pst.setString(8, "0");
//					}
//
//					pst.setString(9, "");
//					pst.setString(10, tfKommentar1.getText());
//
//					boolean pruefung2 = false;
//					if (chckbxPruefung2.isSelected()) {
//						pruefung2 = true;
//					}
//					if (pruefung2) {
//						pst.setString(11, "1");
//						;
//					} else {
//						pst.setString(11, "0");
//					}
//
//					pst.setString(12, "");
//					pst.setString(13, tfKommentar2.getText());
//
//					pst.executeUpdate();
//
//					show_hinzugefuegten_fahrer();
//
//					JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
//				}
//
//				catch (Exception e1) {
//					JOptionPane.showMessageDialog(null, e1);
//				}
//
//			}
//		});

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
		scrollPane.setBounds(450, 30, 1220, 510);
		contentPane.add(scrollPane);

		tableFahrer = new JTable();

		tableFahrer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableFahrer);
		tableFahrer.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "DokumentName", "Dokument", "Extension" }));

		tfDokument = new JTextField();
		tfDokument.setColumns(10);
		tfDokument.setBounds(181, 100, 209, 20);
		contentPane.add(tfDokument);

		show_Dokument();

	}

	public static ArrayList<Dokument> dokument() {
		ArrayList<Dokument> dokumentliste = new ArrayList<>();
//C:\Users\Tolga.Soylu\OneDrive - KHW Konzmann GmbH\Desktop\Controllinginstrumente.txt
		try {
			con = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1 = "Select * from DokumenteTest";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);
			Dokument dokument;
			while (rs.next()) {
				dokument = new Dokument(rs.getInt("ID"), rs.getString("DokumentName"), rs.getBytes("Dokument"),
						rs.getString("Extension"));
				dokumentliste.add(dokument);
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

		return dokumentliste;
	}

	public static void show_Dokument() {
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		ArrayList<Dokument> dokument = dokument();
		Object[] row = new Object[4];
		for (int i = 0; i < dokument.size(); i++) {
			row[0] = dokument.get(i).getId();
			row[1] = dokument.get(i).getDokumentName();
			row[2] = dokument.get(i).getDokument();
			row[3] = dokument.get(i).getExtension();
			model.addRow(row);
		}
	}

	public static void show_hinzugefuegtes_Dokument() {
		try {
			DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
			model.setRowCount(0);
			show_Dokument();
		} catch (IndexOutOfBoundsException e) {
			// JOptionPane.showMessageDialog(null, e);
		}
		;
	}

	public void createBLOB() {
		FileInputStream input;
		try {
			con = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query = "insert into DokumenteTest (DokumentName, Dokument)values (?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, tfDokumentName.getText());
			File theFile = new File(tfDokument.getText());
			input = new FileInputStream(theFile);
			pst.setBinaryStream(2, input);

//			String dateiname = "C:\\Users\\Tolga.Soylu\\OneDrive - KHW Konzmann GmbH\\Desktop\\Controllinginstrumente.txt";
//			String endung = "";

//			for (int i = dateiname.length() - 1; i > 0; i--) {
//				if (dateiname.charAt(i - 1) == '.') {
//					return;
//				}
//				endung = endung + dateiname.charAt(i - 1);
//			}

			// pst.setString(3, "hallo");

			pst.executeUpdate();
		} catch (

		Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
	}
}

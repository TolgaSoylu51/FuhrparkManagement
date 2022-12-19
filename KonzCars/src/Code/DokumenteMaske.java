package Code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.io.IOException;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextField;

public class DokumenteMaske extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	static Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private static JTable tableFahrer;
	private JTextField tfSuche;
	private static String fahrzeugid;
	private static String fahrerid;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DokumenteMaske frame = new DokumenteMaske();
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
	public DokumenteMaske() {
		setTitle("KFM Dokument Ansehen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1278, 674);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnOeffnen = new JButton("Oeffnen");
		btnOeffnen.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnOeffnen.setFocusPainted(false);
		btnOeffnen.setBackground(SystemColor.inactiveCaption);
		btnOeffnen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					String path = tableFahrer.getValueAt(tableFahrer.getSelectedRow(), 2).toString();
					File file = new File(path);
					if (file.exists()) {
						Process pro = Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + path);
						pro.waitFor();
					}
				} catch (InterruptedException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});

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

		btnOeffnen.setBounds(10, 605, 180, 23);
		contentPane.add(btnOeffnen);
		
		JButton btnZurueck = new JButton("");
		btnZurueck.setFocusable(false);
		btnZurueck.setBackground(Color.WHITE);
		btnZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FahrzeugDatenMaske.herkunft_ueber_fahrzeug = false;
				FahrerDatenMaske.herkunft_ueber_fahrzeug = false;
				Hauptmenue frame = new Hauptmenue();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnZurueck.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\icons\\pfeil-zurueck.png"));
		btnZurueck.setBounds(10, 2, 28, 23);
		contentPane.add(btnZurueck);

		JButton btnClear = new JButton("X");
		btnClear.setFont(new Font("Arial", Font.PLAIN, 10));
		btnClear.setFocusPainted(false);
		btnClear.setBackground(SystemColor.inactiveCaption);
		btnClear.setBounds(974, 26, 19, 18);
		btnClear.setMargin(new Insets(0, 0, 0, 0));
		contentPane.add(btnClear);

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				tfSuche.setText("");
				filter(tfSuche.getText());
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 50, 1242, 550);
		contentPane.add(scrollPane);

		tableFahrer = new JTable();

		tableFahrer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableFahrer);
		tableFahrer.setBorder(null);
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "DokumentName", "Pfad", "Dokument", "Extension" }));

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground.setBounds(0, 0, 1262, 647);
		contentPane.add(lblBackground);

		show_Dokument();

	}

	public static ArrayList<Dokument> dokument() {
		ArrayList<Dokument> dokumentliste = new ArrayList<>();
//C:\Users\Tolga.Soylu\OneDrive - KHW Konzmann GmbH\Desktop\Controllinginstrumente.txt
		try {
			if (FahrzeugDatenMaske.herkunft_ueber_fahrzeug == true) {
				fahrzeugid = FahrzeugDatenMaske.id_Uebergabe_fahrzeug;
				fahrerid = FahrzeugDatenMaske.id_Uebergabe_fahrer;
			}
			if (FahrerDatenMaske.herkunft_ueber_fahrzeug == true) {
				fahrzeugid = FahrerDatenMaske.id_Uebergabe_fahrzeug;
				fahrerid = FahrerDatenMaske.id_Uebergabe_fahrer;
			}
			con = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1 = null;
			if (FahrzeugDatenMaske.herkunft_ueber_fahrzeug == true) {
				query1 = "Select * from DokumenteTest where FahrzeugID=" + fahrzeugid + " or FahrerID=" + fahrerid;
			}
			if (FahrerDatenMaske.herkunft_ueber_fahrzeug == true) {
				query1 = "Select * from DokumenteTest where FahrzeugID=" + fahrzeugid + " or FahrerID=" + fahrerid;
			}
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);
			Dokument dokument;
			while (rs.next()) {
				dokument = new Dokument(rs.getInt("ID"), rs.getString("DokumentName"), rs.getString("Pfad"),
						rs.getBytes("Dokument"), rs.getString("Extension"));
				dokumentliste.add(dokument);
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

		return dokumentliste;
	}

	public void filter(String str) {
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		TableRowSorter<DefaultTableModel> rowFilter = new TableRowSorter<DefaultTableModel>(model);
		tableFahrer.setRowSorter(rowFilter);

		rowFilter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
	}

	public static void show_Dokument() {
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		ArrayList<Dokument> dokument = dokument();
		Object[] row = new Object[5];
		for (int i = 0; i < dokument.size(); i++) {
			row[0] = dokument.get(i).getId();
			row[1] = dokument.get(i).getDokumentName();
			row[2] = dokument.get(i).getPfad();
			row[3] = dokument.get(i).getDokument();
			row[4] = dokument.get(i).getExtension();
			model.addRow(row);
		}
	}

}

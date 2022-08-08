package Code;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JFileChooser;
import javax.swing.JTable;
import java.io.IOException;
import java.awt.Desktop;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.ListSelectionModel;

public class DokumentAnsehenMaske extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	static Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private static JTable tableFahrer;
//	private String dateiname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DokumentAnsehenMaske frame = new DokumentAnsehenMaske();
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
	public DokumentAnsehenMaske() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1710, 681);
		contentPane = new JPanel();
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnOeffnen = new JButton("Öffnen");
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

		btnOeffnen.setBounds(34, 588, 180, 23);
		contentPane.add(btnOeffnen);

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
		scrollPane.setBounds(34, 69, 1639, 510);
		contentPane.add(scrollPane);

		tableFahrer = new JTable();

		tableFahrer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableFahrer);
		tableFahrer.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "DokumentName", "Pfad", "Dokument", "Extension" }));

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

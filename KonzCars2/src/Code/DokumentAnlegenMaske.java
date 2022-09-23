
package Code;

import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JFrame;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JLabel;

public class DokumentAnlegenMaske extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JTextField tfDokumentName;

	static Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private static JTable tableFahrer;
	private static JTextField tfDokument;
	int letzteZeile;
	static ArrayList<Dokument> dokument;
	private JTextField tfSuche;
//	private String dateiname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DokumentAnlegenMaske frame = new DokumentAnlegenMaske();
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
	public DokumentAnlegenMaske() {
		setTitle("KFM Dokument Anlegen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1278, 674);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnSave = new JButton("Anlegen");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setFocusPainted(false);
		btnSave.setBackground(SystemColor.inactiveCaption);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					copyFile(tfDokument.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				createBLOB();
				show_hinzugefuegtes_Dokument();

				int endungStart = 0;
				String endung = "";

				for (int i = 0; i < tfDokument.getText().length(); i++) {
					if (tfDokument.getText().charAt(i) == '.') {
						endungStart = i;
					}
				}

				endung = tfDokument.getText().substring(endungStart);

				int letzteZeile = dokument.get(dokument.size() - 1).getId();

				try {
					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
					con = DriverManager.getConnection(url);
					String query = "UPDATE DokumenteTest SET Pfad=? WHERE ID=" + letzteZeile;

					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1,
							"C://Users//Tolga.Soylu//OneDrive - KHW Konzmann GmbH//Desktop//FuhrparkManagement/"
									+ tfDokumentName.getText() + endung);
					//JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
				} catch (Exception e1) {
//					JOptionPane.showMessageDialog(null, e1);
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

		btnSave.setBounds(10, 605, 180, 23);
		contentPane.add(btnSave);

		tfDokumentName = new JTextField();
		tfDokumentName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfDokumentName.setBounds(42, 69, 288, 20);
		contentPane.add(tfDokumentName);
		tfDokumentName.setColumns(10);

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
		scrollPane.setViewportBorder(null);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(340, 50, 912, 574);

		contentPane.add(scrollPane);

		tableFahrer = new JTable();

		tableFahrer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableFahrer);
		tableFahrer.setBorder(null);
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "DokumentName", "Pfad", "Dokument", "Extension" }));

		tfDokument = new JTextField();
		tfDokument.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfDokument.setColumns(10);
		tfDokument.setBounds(42, 100, 288, 20);
		contentPane.add(tfDokument);

		JButton btnDurchsuchen = new JButton("Durchsuchen");
		btnDurchsuchen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDurchsuchen.setFocusPainted(false);
		btnDurchsuchen.setBackground(SystemColor.inactiveCaption);
		btnDurchsuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					tfDokument.setText(selectedFile.getAbsolutePath().toString());
				}
			}
		});
		btnDurchsuchen.setBounds(10, 144, 180, 23);
		contentPane.add(btnDurchsuchen);

		contentPane.setDropTarget(new DropTarget() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						tfDokument.setText(file.getAbsolutePath());
					}
				} catch (Exception ex) {
					//ex.printStackTrace();
				}
			}
		});

		JLabel lblDokumentName = new JLabel("Name");
		lblDokumentName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDokumentName.setBounds(10, 72, 45, 13);
		contentPane.add(lblDokumentName);

		JLabel lblDokument = new JLabel("Pfad");
		lblDokument.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDokument.setBounds(10, 103, 45, 13);
		contentPane.add(lblDokument);

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
	
	public void filter(String str) {
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		TableRowSorter<DefaultTableModel> rowFilter = new TableRowSorter<DefaultTableModel>(model);
		tableFahrer.setRowSorter(rowFilter);
		
		rowFilter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
	}

	public static void show_Dokument() {
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		dokument = dokument();
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

	@SuppressWarnings("resource")
	public static void copyFile(String string) throws IOException {
		
		File f = new File(System.getProperty("user.home") + "/Desktop/FuhrparkManagement_Dokumente");
		if (!f.exists()){
			Files.createDirectory(Paths.get(System.getProperty("user.home") + "/Desktop/FuhrparkManagement_Dokumente"));;
		}

		int endungStart = 0;
		String endung = "";

		for (int i = 0; i < tfDokument.getText().length(); i++) {
			if (tfDokument.getText().charAt(i) == '.') {
				endungStart = i;
			}
		}

		endung = tfDokument.getText().substring(endungStart);

		File file = new File(System.getProperty("user.home") + "/Desktop/FuhrparkManagement_Dokumente/"
				+ tfDokumentName.getText() + endung);
		file.createNewFile();

		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			inChannel = new FileInputStream(string).getChannel();
			outChannel = new FileOutputStream(
					System.getProperty("user.home") + "/Desktop/FuhrparkManagement_Dokumente/"
							+ tfDokumentName.getText() + endung)
					.getChannel();
			inChannel.transferTo(0, inChannel.size(), outChannel);

		} catch (IOException e) {
			//throw e;
		} finally {
			try {
				if (inChannel != null)
					inChannel.close();
				if (outChannel != null)
					outChannel.close();
			} catch (IOException e) {
			}
		}
	}

	public void createBLOB() {
		FileInputStream input;
		try {
			con = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query = "insert into DokumenteTest (DokumentName, Pfad, Dokument, Extension)values (?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, tfDokumentName.getText());
			File theFile = new File(tfDokument.getText());
			input = new FileInputStream(theFile);
			pst.setBinaryStream(3, input);

//			String dateiname = "C:\\Users\\Tolga.Soylu\\OneDrive - KHW Konzmann GmbH\\Desktop\\Controllinginstrumente.txt";
			int endungStart = 0;
			String endung = "";

			for (int i = 0; i < tfDokument.getText().length(); i++) {
				if (tfDokument.getText().charAt(i) == '.') {
					endungStart = i;
				}
			}
			
			endung = tfDokument.getText().substring(endungStart);
			pst.setString(2, System.getProperty("user.home") + "/Desktop/FuhrparkManagement_Dokumente/" + tfDokumentName.getText() + endung);
			pst.setString(4, endung);

			pst.executeUpdate();
		} catch (

		Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
	}
}
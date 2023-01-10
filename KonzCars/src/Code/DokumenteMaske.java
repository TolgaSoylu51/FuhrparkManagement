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
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.SystemColor;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JComboBox;

public class DokumenteMaske extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextField tfDokumentName;
	private static JTextField tfDokument;
	int letzteZeile;
	static ArrayList<Dokument> dokument;
	private String id = null;
	static Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private static JTable tableFahrer;
	private JTextField tfSuche;
	private static String fahrzeugid;
	private static String fahrerid;
	private static String LE_Sichtbarkeit;
	static LoginMaske loginMaske;
	private JComboBox<?> comboBox;
	private static ArrayList<String> array = new ArrayList<String>();

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
		setTitle("KFM Dokumente");
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
				show_aktualisierte_Dokument();

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
					String query = "UPDATE Dokumente SET Pfad=? WHERE ID=" + letzteZeile;

					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, "E:/Fuhrpark App/Fuhrpark_Dokumente/" + tfDokumentName.getText() + endung);
					// JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
				} catch (Exception e1) {
//					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});

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

		fuelleArrayList(array);
		String[] a = new String[array.size() + 1];

		a[0] = "";

		for (int i = 1; i < a.length; i++) {
			a[i] = array.get(i - 1);
		}

		comboBox = new JComboBox(a);
		comboBox.setBounds(88, 143, 242, 22);
		contentPane.add(comboBox);

		JLabel lblDokumenttyp = new JLabel("Dokumenttyp");
		lblDokumenttyp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDokumenttyp.setBounds(10, 148, 72, 13);
		contentPane.add(lblDokumenttyp);
		tfSuche.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfSuche.setColumns(10);
		tfSuche.setBackground(SystemColor.menu);
		tfSuche.setBounds(10, 26, 964, 19);
		contentPane.add(tfSuche);

		btnOeffnen.setBounds(356, 605, 180, 23);
		contentPane.add(btnOeffnen);

		btnSave.setBounds(10, 605, 180, 23);
		contentPane.add(btnSave);

		tfDokumentName = new JTextField();
		tfDokumentName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfDokumentName.setBounds(42, 69, 288, 20);
		contentPane.add(tfDokumentName);
		tfDokumentName.setColumns(10);

		JButton btnZurueck = new JButton("");
		btnZurueck.setFocusable(false);
		btnZurueck.setBackground(Color.WHITE);
		btnZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(FahrzeugDatenMaske.herkunft_ueber_fahrzeug == true) {
					FahrzeugDatenMaske.herkunft_ueber_fahrzeug = false;
				}
				if(FahrerDatenMaske.herkunft_ueber_fahrzeug == true) {
					FahrerDatenMaske.herkunft_ueber_fahrzeug = false;
				}
				if(Hauptmenue.herkunft_ueber_hauptmenue == true) {
					Hauptmenue.herkunft_ueber_hauptmenue = false;
					Hauptmenue frame = new Hauptmenue();
					frame.setVisible(true);
				}
				setVisible(false);
			}
		});
		try {
			btnZurueck.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/res/pfeil-zurueck.png"))));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
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
		scrollPane.setBounds(356, 50, 896, 550);
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

		JButton btnLoeschen = new JButton("Löschen");
		btnLoeschen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnLoeschen.setFocusPainted(false);
		btnLoeschen.setBackground(SystemColor.inactiveCaption);
		btnLoeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
					con = DriverManager.getConnection(url);
					int i = tableFahrer.convertRowIndexToModel(tableFahrer.getSelectedRow());
					TableModel model = tableFahrer.getModel();
					String query = "DELETE FROM Dokumente WHERE ID=" + id;
					String pfad = model.getValueAt(i, 2).toString();
					String name = model.getValueAt(i, 1).toString();

					PreparedStatement pst = con.prepareStatement(query);

					pst.executeUpdate();

					int endungStart = 0;
					String endung = "";

					for (int j = 0; j < pfad.length(); j++) {
						if (pfad.charAt(j) == '.') {
							endungStart = j;
						}
					}

					endung = pfad.substring(endungStart);

					Path path = Paths.get("E:/Fuhrpark App/Fuhrpark_Dokumente/" + name + endung);
					try {
						Files.delete(path);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					show_aktualisierte_Dokument();

					// JOptionPane.showMessageDialog(null, "Datensatz wurde gelöscht!");
				}

				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}

				show_aktualisierte_Dokument();
			}
		});
		btnLoeschen.setBounds(1072, 605, 180, 23);
		contentPane.add(btnLoeschen);

		JButton btnDurchsuchen = new JButton("Durchsuchen");
		btnDurchsuchen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDurchsuchen.setFocusPainted(false);
		btnDurchsuchen.setBackground(SystemColor.inactiveCaption);
		btnDurchsuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("E:/Fuhrpark App/Fuhrpark_Dokumente"));
				int result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					tfDokument.setText(selectedFile.getAbsolutePath().toString());
				}
			}
		});
		btnDurchsuchen.setBounds(10, 187, 180, 23);
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
					// ex.printStackTrace();
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
		try {
			lblBackground.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/res/Vorschlag1.jpg"))));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		lblBackground.setBounds(0, 0, 1262, 647);
		contentPane.add(lblBackground);

		show_Dokument();

	}

	public static ArrayList<Dokument> dokument() {
		ArrayList<Dokument> dokumentliste = new ArrayList<>();
		try {
			if (Hauptmenue.herkunft_ueber_hauptmenue == true) {
				LE_Sichtbarkeit = LoginMaske.LE_Sichtbarkeit_Uebergabe;
			}
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
//				if (fahrerid != null || !fahrerid.equals("")) {
//					query1 = "Select * from Dokumente where FahrzeugID=" + fahrzeugid + " or FahrerID=" + fahrerid;
//				} else {
//					query1 = "Select * from Dokumente where FahrzeugID=" + fahrzeugid;// + " or FahrerID=" + fahrerid;
//				}
				query1 = "Select * from Dokumente where FahrzeugID=" + fahrzeugid;// + " or FahrerID=" + fahrerid;
			}
			if (FahrerDatenMaske.herkunft_ueber_fahrzeug == true) {
//				if (fahrzeugid != null || !fahrzeugid.equals("")) {
//					query1 = "Select * from Dokumente where FahrerID=" + fahrerid + " or FahrzeugID=" + fahrzeugid;
//				} else {
//					query1 = "Select * from Dokumente where FahrerID=" + fahrerid;// + " or FahrerID=" + fahrerid;
//				}
				
				query1 = "Select * from Dokumente where FahrerID=" + fahrerid;// + " or FahrerID=" + fahrerid;
			}
			if (Hauptmenue.herkunft_ueber_hauptmenue == true) {
				if (LE_Sichtbarkeit.equals("Admin")) {
					query1 = "Select * from Dokumente";
				} else {
					query1 = "Select * from Dokumente where FirmaNr=" + LE_Sichtbarkeit;
				}
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

	public static void show_aktualisierte_Dokument() {
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
//		File f = new File(System.getProperty("user.home") + "/FuhrparkManagement_Dokumente");
//		if (!f.exists()) {
//			f.mkdirs();
//		}

		int endungStart = 0;
		String endung = "";

		for (int i = 0; i < tfDokument.getText().length(); i++) {
			if (tfDokument.getText().charAt(i) == '.') {
				endungStart = i;
			}
		}

		endung = tfDokument.getText().substring(endungStart);

		File file = new File("E:/Fuhrpark App/Fuhrpark_Dokumente/" + tfDokumentName.getText() + endung);
		file.createNewFile();

		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			inChannel = new FileInputStream(string).getChannel();
			outChannel = new FileOutputStream("E:/Fuhrpark App/Fuhrpark_Dokumente/" + tfDokumentName.getText() + endung)
					.getChannel();
			inChannel.transferTo(0, inChannel.size(), outChannel);

		} catch (IOException e) {
			// throw e;
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
			String query = "insert into Dokumente (DokumentName, Pfad, Dokument, Extension, FahrzeugID, FahrerID, FirmaNr, Dokumenttyp)values (?,?,?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, tfDokumentName.getText());
			File theFile = new File(tfDokument.getText());
			input = new FileInputStream(theFile);
			pst.setBinaryStream(3, input);

			int endungStart = 0;
			String endung = "";

			for (int i = 0; i < tfDokument.getText().length(); i++) {
				if (tfDokument.getText().charAt(i) == '.') {
					endungStart = i;
				}
			}

			endung = tfDokument.getText().substring(endungStart);
			pst.setString(2, "E:/Fuhrpark App/Fuhrpark_Dokumente/" + tfDokumentName.getText() + endung);
			pst.setString(4, endung);
			pst.setString(5, fahrzeugid);
			pst.setString(6, fahrerid);
			pst.setString(7, LE_Sichtbarkeit);

			Object object = comboBox.getSelectedItem();
			pst.setString(8, object.toString());

			pst.executeUpdate();
		} catch (

		Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
	}

	public static ArrayList<String> fuelleArrayList(ArrayList<String> arrayList) {
		try {
			con = DriverManager.getConnection(
					"jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;",
					"KonzCars", "KonzCars");
			String query1 = "Select * from Dokumenttypen";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query1);
			while (rs.next()) {
				array.add(rs.getString("Dokumenttyp"));
			}
		}

		catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
		return arrayList;
	}
}

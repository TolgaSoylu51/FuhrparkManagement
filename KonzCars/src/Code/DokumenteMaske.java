package Code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class DokumenteMaske extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Dimension screenSize;
	private static JTextField tfDokumentName;
	private static JTextField tfZielpfad;
	private static JTextField tfDokument;
	int letzteZeile;
	static ArrayList<Dokument> dokument;
	private String id = null;
	int f_width, f_height;
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
	
	public static JLabel lblZeilenAnzahl;
	public static int zeilenAnzahl;

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DokumenteMaske() {
		setTitle("KFM Dokumente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//screensize
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		f_width = getWidth();
		f_height = getHeight();
		
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		f_width = getWidth();
		f_height = getHeight();

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

//				int endungStart = 0;
//				String endung = "";
//
//				for (int i = 0; i < tfDokument.getText().length(); i++) {
//					if (tfDokument.getText().charAt(i) == '.') {
//						endungStart = i;
//					}
//				}
//
//				endung = tfDokument.getText().substring(endungStart);

//				int letzteZeile = dokument.get(dokument.size() - 1).getId();

//				try {
//					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
//					con = DriverManager.getConnection(url);
//					String query = "UPDATE Dokumente SET Pfad=? WHERE ID=" + letzteZeile;
//
//					PreparedStatement pst = con.prepareStatement(query);
//					pst.setString(1, "E:/Fuhrpark App/Fuhrpark_Dokumente/" + tfDokumentName.getText() + endung);
//					// JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
//				} catch (Exception e1) {
////					JOptionPane.showMessageDialog(null, e1);
//				}
				
				tfDokument.setText("");
				tfDokumentName.setText("");
				tfZielpfad.setText("");
				comboBox.setSelectedIndex(0);
				
				JOptionPane.showMessageDialog(null, "Das Dokument wurde angelegt!");
			}
		});

		JButton btnOeffnen = new JButton("\u00D6ffnen");
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

		JLabel lblZielpfad = new JLabel("Zielpfad");
		lblZielpfad.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblZielpfad.setBounds(10, 124, 58, 13);
		contentPane.add(lblZielpfad);

		JButton btnDurchsuchen2 = new JButton("Durchsuchen");

		btnDurchsuchen2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setCurrentDirectory(new File("E:/Fuhrpark App/Fuhrpark_Dokumente"));
				int result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					tfZielpfad.setText(selectedFile.getAbsolutePath().toString());
				}
			}

		});
		btnDurchsuchen2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDurchsuchen2.setFocusPainted(false);
		btnDurchsuchen2.setBackground(SystemColor.inactiveCaption);
		btnDurchsuchen2.setBounds(150, 152, 180, 23);
		contentPane.add(btnDurchsuchen2);

		tfZielpfad = new JTextField();
		tfZielpfad.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfZielpfad.setColumns(10);
		tfZielpfad.setBounds(63, 121, 267, 20);
		contentPane.add(tfZielpfad);

		comboBox = new JComboBox(a);
		comboBox.setBounds(88, 231, 242, 22);
		contentPane.add(comboBox);

		JLabel lblDokumenttyp = new JLabel("Dokumenttyp");
		lblDokumenttyp.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDokumenttyp.setBounds(10, 236, 72, 13);
		contentPane.add(lblDokumenttyp);
		tfSuche.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfSuche.setColumns(10);
		tfSuche.setBackground(SystemColor.menu);
		tfSuche.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		tfSuche.setBounds(10, 26, f_width -38, 19);
		contentPane.add(tfSuche);

		btnOeffnen.setBounds(336, f_height -100, 180, 23);
		contentPane.add(btnOeffnen);

		btnSave.setBounds(10, f_height -100, 180, 23);
		contentPane.add(btnSave);

		tfDokumentName = new JTextField();
		tfDokumentName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfDokumentName.setBounds(42, 193, 288, 20);
		contentPane.add(tfDokumentName);
		tfDokumentName.setColumns(10);

		JButton btnZurueck = new JButton("");
		btnZurueck.setFocusable(false);
		btnZurueck.setBackground(Color.WHITE);
		btnZurueck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FahrzeugDatenMaske.herkunft_ueber_fahrzeug == true) {
					FahrzeugDatenMaske.herkunft_ueber_fahrzeug = false;
				}
				if (FahrerDatenMaske.herkunft_ueber_fahrzeug == true) {
					FahrerDatenMaske.herkunft_ueber_fahrzeug = false;
				}
				if (Hauptmenue.herkunft_ueber_hauptmenue == true) {
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
		btnClear.setBounds(f_width -28, 26, 19, 18);
		btnClear.setMargin(new Insets(0, 0, 0, 0));
		contentPane.add(btnClear);

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				tfSuche.setText("");
				filter(tfSuche.getText());
			}
		});
		
		lblZeilenAnzahl = new JLabel("Zeilenanzahl: ");
		lblZeilenAnzahl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblZeilenAnzahl.setBounds(526, f_height -100, f_width -400, 23);
		contentPane.add(lblZeilenAnzahl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(336, 50, f_width -344, f_height -158);
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
		tfDokument.setBounds(63, 56, 267, 20);
		contentPane.add(tfDokument);

		JButton btnLoeschen = new JButton("L\u00F6schen");
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
		btnLoeschen.setBounds(f_width -190, f_height -100, 180, 23);
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
		btnDurchsuchen.setBounds(150, 87, 180, 23);
		contentPane.add(btnDurchsuchen);

		contentPane.setDropTarget(new DropTarget() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
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
		
		addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	setExtendedState(JFrame.MAXIMIZED_BOTH);
                f_width = getWidth();
                f_height = getHeight();
            }
        });

		JLabel lblDokumentName = new JLabel("Name");
		lblDokumentName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDokumentName.setBounds(10, 196, 45, 13);
		contentPane.add(lblDokumentName);

		JLabel lblDokument = new JLabel("Quellpfad");
		lblDokument.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDokument.setBounds(10, 59, 58, 13);
		contentPane.add(lblDokument);

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
	
	public static void rowCount() {
		zeilenAnzahl = tableFahrer.getRowCount();
		lblZeilenAnzahl.setText("Zeilenanzahl: " + zeilenAnzahl);
	}

	public void filter(String str) {
		DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
		TableRowSorter<DefaultTableModel> rowFilter = new TableRowSorter<DefaultTableModel>(model);
		tableFahrer.setRowSorter(rowFilter);

		rowFilter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
		rowCount();
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
		rowCount();
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

		@SuppressWarnings("unused")
		String tfZielpfadNeu;
		
		//tfZielpfadNeu = tfZielpfad.getText().replace('\', '/'');
		
		File file = new File(tfZielpfad.getText() + "/" + tfDokumentName.getText() + endung);
		file.createNewFile();

		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			inChannel = new FileInputStream(string).getChannel();
			outChannel = new FileOutputStream(tfZielpfad.getText() + "/" + tfDokumentName.getText() + endung)
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
			pst.setString(2, tfZielpfad.getText() + "/" + tfDokumentName.getText() + endung);
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

package Code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Insets;

public class FahrerAnlegenMaske extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfPersonalnummer;
	private JTextField tfVorname;
	private JTextField tfNachname;
	private JTextField tfAktivKz;
	private JTextField tfFirmaNr;
	private JTextField tfNlNr;
	private JTextField tfKommentar1;
	private JTextField tfKommentar2;

	static Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private static JTable tableFahrer;
	private JTextField tfSuche;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FahrerAnlegenMaske frame = new FahrerAnlegenMaske();
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
	public FahrerAnlegenMaske() {
		setTitle("KFM Fahrer Anlegen");
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
						
		JLabel lblZweitePrfung = new JLabel("Zweite Prüfung");
		lblZweitePrfung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblZweitePrfung.setBounds(50, 397, 75, 14);
		contentPane.add(lblZweitePrfung);

		JLabel lblErstePrfung = new JLabel("Erste Prüfung");
		lblErstePrfung.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErstePrfung.setBounds(50, 328, 75, 14);
		contentPane.add(lblErstePrfung);

		JLabel lblFahrerlaubnis = new JLabel("Fahrerlaubnis");
		lblFahrerlaubnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFahrerlaubnis.setBounds(50, 284, 75, 14);
		contentPane.add(lblFahrerlaubnis);

		JButton btnSave = new JButton("Anlegen");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setFocusPainted(false);
		btnSave.setBackground(SystemColor.inactiveCaption);

		btnSave.setBounds(10, 605, 180, 23);
		contentPane.add(btnSave);
		
		tfPersonalnummer = new JTextField();
		tfPersonalnummer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfPersonalnummer.setBounds(137, 69, 243, 20);
		contentPane.add(tfPersonalnummer);
		tfPersonalnummer.setColumns(10);

		tfVorname = new JTextField();
		tfVorname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfVorname.setColumns(10);
		tfVorname.setBounds(137, 100, 243, 20);
		contentPane.add(tfVorname);

		tfNachname = new JTextField();
		tfNachname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfNachname.setColumns(10);
		tfNachname.setBounds(137, 131, 243, 20);
		contentPane.add(tfNachname);

		tfAktivKz = new JTextField();
		tfAktivKz.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfAktivKz.setColumns(10);
		tfAktivKz.setBounds(137, 162, 243, 20);
		contentPane.add(tfAktivKz);

		tfFirmaNr = new JTextField();
		tfFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfFirmaNr.setColumns(10);
		tfFirmaNr.setBounds(137, 193, 243, 20);
		contentPane.add(tfFirmaNr);

		tfNlNr = new JTextField();
		tfNlNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfNlNr.setColumns(10);
		tfNlNr.setBounds(137, 224, 243, 20);
		contentPane.add(tfNlNr);

		JLabel lblPersonalnummer = new JLabel("Personalnummer");
		lblPersonalnummer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPersonalnummer.setBounds(10, 69, 100, 14);
		contentPane.add(lblPersonalnummer);

		JLabel lblVorname = new JLabel("Vorname");
		lblVorname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVorname.setBounds(10, 100, 123, 14);
		contentPane.add(lblVorname);

		JLabel lblNachname = new JLabel("Nachname");
		lblNachname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNachname.setBounds(10, 132, 123, 14);
		contentPane.add(lblNachname);

		JLabel lblAktivKZ = new JLabel("Aktiv KZ");
		lblAktivKZ.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAktivKZ.setBounds(10, 162, 123, 14);
		contentPane.add(lblAktivKZ);

		JLabel lblFirmaNr = new JLabel("FirmaNr");
		lblFirmaNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFirmaNr.setBounds(10, 193, 123, 14);
		contentPane.add(lblFirmaNr);

		JLabel lblNLNr = new JLabel("Niederlassungsnummer");
		lblNLNr.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNLNr.setBounds(10, 224, 123, 14);
		contentPane.add(lblNLNr);

		JCheckBox chckbxPruefung1 = new JCheckBox("erste Prüfung");
		chckbxPruefung1.setOpaque(false);
		chckbxPruefung1.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxPruefung1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxPruefung1.setBounds(10, 324, 20, 20);
		contentPane.add(chckbxPruefung1);

		JCheckBox chckbxPruefung2 = new JCheckBox("zweite Prüfung");
		chckbxPruefung2.setOpaque(false);
		chckbxPruefung2.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxPruefung2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxPruefung2.setBounds(10, 393, 20, 20);
		contentPane.add(chckbxPruefung2);

		tfKommentar1 = new JTextField();
		tfKommentar1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfKommentar1.setBounds(10, 351, 370, 23);
		contentPane.add(tfKommentar1);
		tfKommentar1.setColumns(10);

		tfKommentar2 = new JTextField();
		tfKommentar2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfKommentar2.setBounds(10, 420, 370, 23);
		contentPane.add(tfKommentar2);
		tfKommentar2.setColumns(10);

		JCheckBox chckbxFahrerlaubnis = new JCheckBox("Fahrerlaubnis");
		chckbxFahrerlaubnis.setOpaque(false);
		chckbxFahrerlaubnis.setBackground(SystemColor.inactiveCaptionBorder);
		chckbxFahrerlaubnis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxFahrerlaubnis.setBounds(10, 280, 20, 20);
		contentPane.add(chckbxFahrerlaubnis);

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					emptyTf(tfPersonalnummer);
					emptyTf(tfNachname);
					emptyTf(tfAktivKz);
					emptyTf(tfFirmaNr);
					emptyTf(tfNlNr);
					
					String url = "jdbc:sqlserver://konzmannSQL:1433;databaseName=KonzCars;encrypt=true;trustServerCertificate=true;;user=KonzCars;password=KonzCars";
					con = DriverManager.getConnection(url);
					String query = "insert into MitarbeiterTest (Personalnummer,AktivKZ,Name,Vorname,FirmaNr,NL_Nr,Fahrerlaubnis,Erstprüfung,Prüfungszeitpunkt1,Kommentar1,Zweitprüfung,Prüfungszeitpunkt2,Kommentar2, Bearbeitet) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement pst = con.prepareStatement(query);
					
					TableModel model = tableFahrer.getModel();
					int numOfRows = model.getRowCount();
					
					for(int i = 1; i < numOfRows; i++) {
						String checkRow = model.getValueAt(i, 1).toString();
						if(tfPersonalnummer.getText().equals(checkRow)) {
							throw new Exception("Dieser Fahrer exisitiert bereits!");
						}
					}
					
					pst.setString(1, tfPersonalnummer.getText());
					pst.setString(2, "1");
					pst.setString(3, tfNachname.getText());
					pst.setString(4, tfVorname.getText());
					pst.setString(5, tfFirmaNr.getText());
					pst.setString(6, tfNlNr.getText());

					boolean fahrerlaubnis = false;
					if (chckbxFahrerlaubnis.isSelected()) {
						fahrerlaubnis = true;
					}
					if (fahrerlaubnis) {
						pst.setString(7, "1");
					} else {
						pst.setString(7, "0");
					}

					boolean pruefung1 = false;
					if (chckbxPruefung1.isSelected()) {
						pruefung1 = true;
					}
					if (pruefung1) {
						pst.setString(8, "1");
						;
					} else {
						pst.setString(8, "0");
					}

					pst.setString(9, "");
					pst.setString(10, tfKommentar1.getText());

					boolean pruefung2 = false;
					if (chckbxPruefung2.isSelected()) {
						pruefung2 = true;
					}
					if (pruefung2) {
						pst.setString(11, "1");
						;
					} else {
						pst.setString(11, "0");
					}

					pst.setString(12, "");
					pst.setString(13, tfKommentar2.getText());
					pst.setInt(14, 0);	
					
					
					pst.executeUpdate();

					show_hinzugefuegten_fahrer();

					//JOptionPane.showMessageDialog(null, "Daten wurden gespeichert!");
				}

				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}

			}
		});

		JButton btnReset = new JButton("Zurücksetzen");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnReset.setFocusPainted(false);
		btnReset.setBackground(SystemColor.inactiveCaption);
		btnReset.setBounds(200, 605, 180, 23);
		contentPane.add(btnReset);

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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(390, 50, 862, 574);
		contentPane.add(scrollPane);
//		ab hier
		tableFahrer = new JTable();

		tableFahrer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableFahrer);
		tableFahrer.setBorder(null);
		tableFahrer.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Personalnummer", "AktivKZ", "Name", "Vorname", "FirmaNr", "NL_Nr",
						"Fahrerlaubnis", "Erstpr\u00FCfung", "Pr\u00FCfungszeitpunkt1", "Kommentar1",
						"Zweitpr\u00FCfung", "Pr\u00FCfungszeitpunkt2", "Kommentar2", "Bearbeitet" }));
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground.setBounds(0, 0, 1262, 647);
		contentPane.add(lblBackground);
		
		JLabel lblBackground_1 = new JLabel("");
		lblBackground_1.setIcon(new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground_1.setBounds(1260, 0, 433, 651);
		contentPane.add(lblBackground_1);
		
		wichtigTf(tfPersonalnummer);
		wichtigTf(tfNachname);
		wichtigTf(tfAktivKz);
		wichtigTf(tfFirmaNr);
		wichtigTf(tfNlNr);

		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wichtigTf(tfPersonalnummer);
				wichtigTf(tfNachname);
				wichtigTf(tfAktivKz);
				wichtigTf(tfFirmaNr);
				wichtigTf(tfNlNr);
				tfVorname.setText("");
				tfKommentar1.setText("");
				tfKommentar2.setText("");
				chckbxFahrerlaubnis.setSelected(false);
				chckbxPruefung1.setSelected(false);
				chckbxPruefung2.setSelected(false);
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
						rs.getString("Kommentar2"), rs.getInt("Bearbeitet"));
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
		Object[] row = new Object[15];
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
			row[14] = fahrer.get(i).getBearbeitet();
			model.addRow(row);
		}
	}

	public static void show_hinzugefuegten_fahrer() {
		try {
			DefaultTableModel model = (DefaultTableModel) tableFahrer.getModel();
			model.setRowCount(0);
			show_fahrer();
		} catch (IndexOutOfBoundsException e) {
			//JOptionPane.showMessageDialog(null, e);
		};
	}
	
	public void wichtigTf(JTextField tf) {
		tf.setText("!");
		tf.setForeground(Color.red);
		tf.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evtfg) {
				if(tf.getText().equals("!")) {
					tf.setText("");
					tf.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent evtfl) {
				if(tf.getText().equals("")) {
					tf.setText("!");
					tf.setForeground(Color.red);
				}
			}
		});
	}
	
	public void emptyTf(JTextField tf) throws Exception {
		if(tf.getText().equals("!")) {
			throw new Exception("Füllen sie bitte alle Felder aus!");
		}
	}
}
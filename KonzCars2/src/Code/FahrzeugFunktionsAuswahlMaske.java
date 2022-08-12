package Code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FahrzeugFunktionsAuswahlMaske extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FahrzeugFunktionsAuswahlMaske frame = new FahrzeugFunktionsAuswahlMaske();
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
	public FahrzeugFunktionsAuswahlMaske() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 345, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnZurück = new JButton("");
		btnZurück.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hauptmenue frame = new Hauptmenue();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		
		btnZurück.setIcon(new ImageIcon("C:\\Users\\Tolga.Soylu\\OneDrive - KHW Konzmann GmbH\\Desktop\\back-icon (1).png"));
		btnZurück.setBackground(Color.WHITE);
		btnZurück.setBounds(0, 0, 40, 20);
		contentPane.add(btnZurück);
		
		JButton btnNewButton = new JButton("Neues Fahrzeug anlegen");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FahrzeugAnlegenMaske frame = new FahrzeugAnlegenMaske();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton.setBounds(88, 50, 157, 47);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Fahrzeug bearbeiten");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FahrzeugBearbeitenMaske frame = new FahrzeugBearbeitenMaske();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_1.setBounds(88, 108, 157, 47);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Fahrzeug löschen");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FahrzeugLoeschenMaske frame = new FahrzeugLoeschenMaske();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_1_1.setBounds(88, 166, 157, 47);
		contentPane.add(btnNewButton_1_1);
	}
}

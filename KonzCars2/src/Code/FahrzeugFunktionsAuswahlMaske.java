package Code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Font;

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
	public FahrzeugFunktionsAuswahlMaske() {
		setTitle("KFM Fahrzeug Funktionsmenü");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(360, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnZurück = new JButton("");
		btnZurück.setFocusable(false);
		btnZurück.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hauptmenue frame = new Hauptmenue();
				frame.setResizable(false);
				ImageIcon icon = new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
				frame.setIconImage(icon.getImage());
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnZurück.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key_pressed = e.getKeyCode();
				if(key_pressed == KeyEvent.VK_ESCAPE) {
					Hauptmenue frame = new Hauptmenue();
					frame.setResizable(false);
					ImageIcon icon = new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
					frame.setIconImage(icon.getImage());
					frame.setVisible(true);
					setVisible(false);
				}
			}
		});
		
		btnZurück.setIcon(new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\icons\\pfeil-zurück.png"));
		btnZurück.setBackground(Color.WHITE);
		btnZurück.setBounds(10, 2, 28, 23);
		contentPane.add(btnZurück);
		
		JButton btnNewButton = new JButton("Neues Fahrzeug anlegen");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBackground(SystemColor.inactiveCaption);
		btnNewButton.setFocusPainted(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FahrzeugAnlegenMaske frame = new FahrzeugAnlegenMaske();
				ImageIcon icon = new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
				frame.setIconImage(icon.getImage());
				frame.setVisible(true);
			}
		});
		btnNewButton.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key_pressed = e.getKeyCode();
				if(key_pressed == KeyEvent.VK_ENTER) {
					FahrzeugAnlegenMaske frame = new FahrzeugAnlegenMaske();
					ImageIcon icon = new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
					frame.setIconImage(icon.getImage());
					frame.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(64, 50, 214, 47);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Fahrzeug bearbeiten");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.setBackground(SystemColor.inactiveCaption);
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FahrzeugBearbeitenMaske frame = new FahrzeugBearbeitenMaske();
				ImageIcon icon = new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
				frame.setIconImage(icon.getImage());
				frame.setVisible(true);
			}
		});
		btnNewButton_1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key_pressed = e.getKeyCode();
				if(key_pressed == KeyEvent.VK_ENTER) {
					FahrzeugBearbeitenMaske frame = new FahrzeugBearbeitenMaske();
					ImageIcon icon = new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
					frame.setIconImage(icon.getImage());
					frame.setVisible(true);
				}
			}
		});
		btnNewButton_1.setBounds(64, 108, 214, 47);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Fahrzeug löschen");
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1_1.setBackground(SystemColor.inactiveCaption);
		btnNewButton_1_1.setFocusPainted(false);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FahrzeugLoeschenMaske frame = new FahrzeugLoeschenMaske();
				ImageIcon icon = new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
				frame.setIconImage(icon.getImage());
				frame.setVisible(true);
			}
		});
		btnNewButton_1_1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key_pressed = e.getKeyCode();
				if(key_pressed == KeyEvent.VK_ENTER) {
					FahrzeugLoeschenMaske frame = new FahrzeugLoeschenMaske();
					ImageIcon icon = new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
					frame.setIconImage(icon.getImage());
					frame.setVisible(true);
				}
			}
		});
		btnNewButton_1_1.setBounds(64, 166, 214, 47);
		contentPane.add(btnNewButton_1_1);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground.setBounds(0, 0, 344, 261);
		contentPane.add(lblBackground);
	}
}

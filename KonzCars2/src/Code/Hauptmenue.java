package Code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;

public class Hauptmenue extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	LoginMaske loginMaske = new LoginMaske();
	int rolle = loginMaske.rolle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hauptmenue frame = new Hauptmenue();
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
	public Hauptmenue() {

		setTitle("KFM Hauptmenü");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(780, 420);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWillkommen = new JLabel("Fuhrpark Management System");
		lblWillkommen.setFont(new Font("Arial", Font.BOLD, 26));
		lblWillkommen.setBounds(355, 21, 379, 82);
		contentPane.add(lblWillkommen);

		JButton btnNewButton = new JButton("Fahrerdaten verwalten");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBackground(SystemColor.inactiveCaption);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				FahrerFunktionsAuswahlMaske frame = new FahrerFunktionsAuswahlMaske();
				frame.setResizable(false);
				ImageIcon icon = new ImageIcon(
						"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
				frame.setIconImage(icon.getImage());
				frame.setVisible(true);
				setVisible(false);

			}
		});
		btnNewButton.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key_pressed = e.getKeyCode();
				if (key_pressed == KeyEvent.VK_ENTER) {
					FahrerFunktionsAuswahlMaske frame = new FahrerFunktionsAuswahlMaske();
					frame.setResizable(false);
					ImageIcon icon = new ImageIcon(
							"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
					frame.setIconImage(icon.getImage());
					frame.setVisible(true);
					setVisible(false);
				}
			}
		});

		btnNewButton.setBounds(472, 114, 261, 40);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Fahrzeuge verwalten");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.setBackground(new Color(191, 205, 219));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FahrzeugFunktionsAuswahlMaske frame = new FahrzeugFunktionsAuswahlMaske();
				frame.setResizable(false);
				ImageIcon icon = new ImageIcon(
						"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
				frame.setIconImage(icon.getImage());
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key_pressed = e.getKeyCode();
				if (key_pressed == KeyEvent.VK_ENTER) {
					FahrzeugFunktionsAuswahlMaske frame = new FahrzeugFunktionsAuswahlMaske();
					frame.setResizable(false);
					ImageIcon icon = new ImageIcon(
							"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
					frame.setIconImage(icon.getImage());
					frame.setVisible(true);
					setVisible(false);
				}
			}
		});

		btnNewButton_1.setBounds(472, 165, 261, 40);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Dokumente verwalten");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_2.setBackground(new Color(191, 205, 219));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DokumentFunktionsAuswahlMaske frame = new DokumentFunktionsAuswahlMaske();
				frame.setResizable(false);
				ImageIcon icon = new ImageIcon(
						"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
				frame.setIconImage(icon.getImage());
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_2.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key_pressed = e.getKeyCode();
				if (key_pressed == KeyEvent.VK_ENTER) {
					DokumentFunktionsAuswahlMaske frame = new DokumentFunktionsAuswahlMaske();
					frame.setResizable(false);
					ImageIcon icon = new ImageIcon(
							"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
					frame.setIconImage(icon.getImage());
					frame.setVisible(true);
					setVisible(false);
				}
			}
		});

		btnNewButton_2.setBounds(472, 216, 261, 37);
		contentPane.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_3.setBackground(new Color(191, 205, 219));
		btnNewButton_3.setBounds(472, 264, 261, 41);
		contentPane.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Abmelden");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_4.setBackground(new Color(191, 205, 219));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginMaske frame = new LoginMaske();
				frame.setResizable(false);
				ImageIcon icon = new ImageIcon(
						"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
				frame.setIconImage(icon.getImage());
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_4.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key_pressed = e.getKeyCode();
				if (key_pressed == KeyEvent.VK_ENTER) {
					LoginMaske frame = new LoginMaske();
					frame.setResizable(false);
					ImageIcon icon = new ImageIcon(
							"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\Logo-weißesK-roterHintergrund.jpg");
					frame.setIconImage(icon.getImage());
					frame.setVisible(true);
					setVisible(false);
				}
			}
		});

		btnNewButton_4.setBounds(472, 316, 261, 42);
		contentPane.add(btnNewButton_4);

		JLabel lbl_img = new JLabel("");
		lbl_img.setDoubleBuffered(true);
		lbl_img.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\grafiken\\KONZMANN_HOLDING_LOGO_FLOW_GRAU_RGB.png"));
		lbl_img.setBounds(44, 32, 293, 60);
		contentPane.add(lbl_img);

		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(
				"C:\\Users\\Hermann.Zelesnov\\OneDrive - KHW Konzmann GmbH\\Dokumente\\bilder\\hintergrund\\Vorschlag1.jpg"));
		lblBackground.setBounds(0, 0, 764, 381);
		contentPane.add(lblBackground);
		
	}
}

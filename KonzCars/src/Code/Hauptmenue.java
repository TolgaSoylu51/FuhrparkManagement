package Code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Hauptmenue extends JFrame {

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
					Hauptmenue frame = new Hauptmenue();
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWillkommen = new JLabel("Fuhrpark Management System");
		lblWillkommen.setFont(new Font("Arial", Font.BOLD, 34));
		lblWillkommen.setBounds(158, 11, 515, 89);
		contentPane.add(lblWillkommen);
		
		JButton btnNewButton = new JButton("Fahrerdaten verwalten");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FahrerFunktionsAuswahlMaske frame = new FahrerFunktionsAuswahlMaske();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton.setBounds(492, 121, 261, 40);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Neu");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(492, 172, 261, 40);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(492, 223, 261, 37);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(492, 271, 261, 41);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Abmelden");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginMaske frame = new LoginMaske();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_4.setBounds(492, 323, 261, 42);
		contentPane.add(btnNewButton_4);
	}
}

package Code;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DokumentFunktionsAuswahlMaske extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DokumentFunktionsAuswahlMaske frame = new DokumentFunktionsAuswahlMaske();
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
	public DokumentFunktionsAuswahlMaske() {
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
		
		JButton btnNewButton = new JButton("Neues Dokument anlegen");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DokumentAnlegenMaske frame = new DokumentAnlegenMaske();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton.setBounds(58, 50, 214, 47);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Dokument ansehen");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DokumentAnsehenMaske frame = new DokumentAnsehenMaske();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_1.setBounds(58, 108, 214, 47);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Dokument löschen");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DokumentLöeschenMaske frame = new DokumentLöeschenMaske();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton_1_1.setBounds(58, 166, 214, 47);
		contentPane.add(btnNewButton_1_1);
	}
}

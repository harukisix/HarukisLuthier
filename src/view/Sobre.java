package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.Color;

public class Sobre extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Sobre() {
		setTitle("Sobre");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/52381_clown fish_fish_nemo_icon.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);//Centralizar a janela
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(205, 12, 94));
		panel.setBounds(0, 213, 434, 48);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Autor: Haruki Urata");
		lblNewLabel.setBounds(170, 11, 142, 33);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 15));
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(80, 7, 45, 45);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/4043278_avatar_male_ozzy_rock_singer_icon.png")));
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		lblNewLabel_1.setBounds(298, 106, 126, 100);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_4 = new JLabel("Sistema de gestão de serviços de Luthiearia");
		lblNewLabel_4.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblNewLabel_4.setBounds(10, 22, 382, 22);
		getContentPane().add(lblNewLabel_4); 

	}
}

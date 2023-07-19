package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class Login extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	// objeto tela principal
	Principal principal = new Principal();

	private JPanel contentPane;
	private JTextField txtLogin;
	private JLabel lblData;
	private JLabel lblStatus;
	private JPasswordField txtSenha;
	private JButton btnAcessar;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/9118406_rock_on_icon.png")));
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);//Centralizar a janela

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblLogin.setBounds(10, 35, 75, 20);
		contentPane.add(lblLogin);

		JLabel lblNewLabel = new JLabel("Senha:");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 105, 75, 20);
		contentPane.add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setBounds(75, 37, 170, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		btnAcessar = new JButton("Acessar");
		btnAcessar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAcessar.setContentAreaFilled(false);
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setBounds(116, 157, 89, 23);
		contentPane.add(btnAcessar);
		// substituir o click pela tecla <ENTER> em um botão
		getRootPane().setDefaultButton(btnAcessar);
		
				txtSenha = new JPasswordField();
				txtSenha.setBounds(75, 107, 170, 20);
				contentPane.add(txtSenha);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(219, 26, 84));
		panel.setBounds(0, 191, 334, 50);
		contentPane.add(panel);
		panel.setLayout(null);

		lblData = new JLabel("New label");
		lblData.setForeground(new Color(255, 255, 255));
		lblData.setFont(new Font("Century Gothic", Font.BOLD, 11));
		lblData.setBounds(10, 11, 190, 28);
		panel.add(lblData);

		lblStatus = new JLabel("");
		lblStatus.setEnabled(false);
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(276, 0, 48, 48);
		panel.add(lblStatus);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/img/3005767_account_door_enter_login_icon (1).png")));
		lblNewLabel_1.setBounds(255, 48, 64, 64);
		contentPane.add(lblNewLabel_1);
	}

	/**
	 * Método para autenticar um usuário
	 */
	private void logar() {
		// Criar uma variável para capturar a senha
		String capturaSenha = new String(txtSenha.getPassword());

		// Validação
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {
			// Lógica principal
			String read = "select * from usuarios where login=? and senha=md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					//capturar o perfil do usuário
					System.out.println(rs.getString(5));//apoio a lógica
					//tratamento do perfil do usuário
					String perfil = rs.getString(5);
					if(perfil.equals("admin")) {
						//logar -> acessar a tela principal 
						principal.setVisible(true);
						//setar a label da tela principal com o nome do usuário
						principal.lblUsuario.setText(rs.getString(2));
						//habilitar os botões 
						principal.btnFornecedor.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						//mudar a cor do rodapé
						principal.panelRodape.setBackground(Color.GRAY);
						// fechar a janela de login
						this.dispose();
						
					} else {
						
					}
					// logar -> acessar a tela principal
					principal.setVisible(true);
					principal.lblUsuario.setText(rs.getString(2));
					// fechar a tela de login
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "usuário e/ou senha inválido(s)");
				}
				con.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Método responsável por exibir o status da conexão
	 */
	private void status() {
		try {
			// abrir a conexão
			con = dao.conectar();
			if (con == null) {
				System.out.println("Erro de conexão");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
			} else {
				System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dbon.png")));
			}
			// NUNCA esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}// fim do método status()

	/**
	 * Método responsável por setar a data no rodapé
	 */
	private void setarData() {
		// Criar objeto para trazer a data do sistema
		Date data = new Date();
		// Criar objeto para formatar a data
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		// Alterar o texto da label pela data atual formatada
		lblData.setText(formatador.format(data));
	}
}

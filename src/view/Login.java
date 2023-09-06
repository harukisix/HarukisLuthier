package view;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;

public class Login extends JFrame {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	Principal principal = new Principal();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JLabel lblStatus;
	private JLabel lblData;

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
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/note.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 397, 267);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(20, 40, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(81, 37, 277, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(20, 95, 49, 14);
		contentPane.add(lblNewLabel_1);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(81, 92, 277, 20);
		contentPane.add(txtSenha);
		
		JButton btnAcessar = new JButton("Acessar");
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setBounds(269, 134, 89, 23);
		contentPane.add(btnAcessar);
				getRootPane().setDefaultButton(btnAcessar);
				
				JPanel panel = new JPanel();
				panel.setBorder(null);
				panel.setBackground(new Color(128, 0, 255));
				panel.setBounds(0, 179, 381, 49);
				contentPane.add(panel);
				panel.setLayout(null);
				
				lblStatus = new JLabel("");
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));
				lblStatus.setBounds(323, 0, 48, 48);
				panel.add(lblStatus);
				
				lblData = new JLabel("New label");
				lblData.setForeground(SystemColor.text);
				lblData.setFont(new Font("Tahoma", Font.BOLD, 16));
				lblData.setBounds(10, 11, 303, 27);
				panel.add(lblData);
	} // Fim do construtor
	
	/**
	 *  Método para autenticar um usuário 
	 */
	private void logar() {
		String capturaSenha = new String(txtSenha.getPassword());
		
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if(capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {
			String read = "select * from usuarios where login=? and senha=md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if(rs.next()) {
					System.out.println(rs.getString(5));//apoio a lógica
					String perfil = rs.getString(5);
					if(perfil.equals("admin")) {
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
						principal.btnFornecedor.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						principal.panelRodape.setBackground(Color.BLUE);
						this.dispose();
					} else {
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();
					}
					
					
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
	private void status(){
		try {
			con = dao.conectar();
			if(con == null) {
					System.out.println("Erro de conexão");
					lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
			} else {
				System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dbon.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}//fim do método status()
	/**
	 * Método responsável por setar a data no rodapé
	 */
	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
	}
}// Fim do código

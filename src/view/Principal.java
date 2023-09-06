package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.DAO;

public class Principal extends JFrame {
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	public JLabel lblUsuario;
	public JPanel panelRodape;
	public JButton btnUsuarios;
	public JButton btnFornecedor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setResizable(false);
		setTitle("Haruki´s Luthier - Serviços de Instrumentos Musicais");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/note.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setForeground(new Color(128, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
	    setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSobre = new JButton("");
		btnSobre.setBorder(null);
		btnSobre.setContentAreaFilled(false);
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setToolTipText("Sobre");
		btnSobre.setBounds(690, 11, 48, 48);
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/sobre.png")));
		contentPane.add(btnSobre);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.setBorder(new LineBorder(new Color(128, 0, 255), 3));
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/users.png")));
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setBounds(47, 34, 135, 128);
		contentPane.add(btnUsuarios);
		
		panelRodape = new JPanel();
		panelRodape.setForeground(new Color(128, 0, 255));
		panelRodape.setBackground(new Color(128, 0, 255));
		panelRodape.setBounds(-10, 505, 842, 71);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
		lblData = new JLabel("New label");
		lblData.setBounds(30, 26, 221, 20);
		panelRodape.add(lblData);
		lblData.setForeground(SystemColor.text);
		lblData.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblUsuario2 = new JLabel("Usuário: ");
		lblUsuario2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario2.setForeground(new Color(255, 255, 255));
		lblUsuario2.setBounds(290, 23, 61, 27);
		panelRodape.add(lblUsuario2);
		
		lblUsuario = new JLabel("");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuario.setBounds(314, 26, 232, 20);
		panelRodape.add(lblUsuario);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(690, 11, 48, 48);
		panelRodape.add(lblStatus);
		lblStatus.setToolTipText("StatusOff");
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
		
		JButton btnservicoOS = new JButton("");
		btnservicoOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
					servicos.setVisible(true);	
			}
		});
		btnservicoOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnservicoOS.setIcon(new ImageIcon(Principal.class.getResource("/img/os.png")));
		btnservicoOS.setToolTipText("Ordem de Serviços");
		btnservicoOS.setContentAreaFilled(false);
		btnservicoOS.setBorder(new LineBorder(new Color(128, 0, 255), 3));
		btnservicoOS.setBounds(47, 355, 135, 128);
		contentPane.add(btnservicoOS);
		
		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/cliente.png")));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setContentAreaFilled(false);
		btnClientes.setBorder(new LineBorder(new Color(128, 0, 255), 3));
		btnClientes.setBounds(260, 34, 135, 128);
		contentPane.add(btnClientes);
		
		JButton btnRelatorios = new JButton("");
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
				
			}
		});
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/relatorio.png")));
		btnRelatorios.setToolTipText("Relatórios");
		btnRelatorios.setContentAreaFilled(false);
		btnRelatorios.setBorder(new LineBorder(new Color(128, 0, 255), 3));
		btnRelatorios.setBounds(260, 355, 135, 128);
		contentPane.add(btnRelatorios);
		
		JButton btnProdutos = new JButton("");
		btnProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produtos produtos = new Produtos();
				produtos.setVisible(true);
			}
		});
		btnProdutos.setIcon(new ImageIcon(Principal.class.getResource("/img/produtos.png")));
		btnProdutos.setToolTipText("Produtos");
		btnProdutos.setContentAreaFilled(false);
		btnProdutos.setBorder(new LineBorder(new Color(128, 0, 255), 3));
		btnProdutos.setBounds(480, 34, 135, 128);
		contentPane.add(btnProdutos);
		
		btnFornecedor = new JButton("");
		btnFornecedor.setEnabled(false);
		btnFornecedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setVisible(true);
			}
		});
		btnFornecedor.setIcon(new ImageIcon(Principal.class.getResource("/img/fornecedor.png")));
		btnFornecedor.setToolTipText("Fornecedor");
		btnFornecedor.setContentAreaFilled(false);
		btnFornecedor.setBorder(new LineBorder(new Color(128, 0, 255), 3));
		btnFornecedor.setBounds(480, 355, 135, 128);
		contentPane.add(btnFornecedor);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/kkkkkkk.png")));
		lblNewLabel.setBounds(639, 367, 135, 127);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Usuários");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblNewLabel_1.setBounds(70, 173, 95, 35);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Clientes");
		lblNewLabel_1_1.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(284, 173, 95, 35);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Produtos");
		lblNewLabel_1_2.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblNewLabel_1_2.setBounds(505, 173, 95, 35);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("OS");
		lblNewLabel_1_3.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblNewLabel_1_3.setBounds(99, 309, 95, 35);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Relatórios");
		lblNewLabel_1_4.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblNewLabel_1_4.setBounds(284, 309, 95, 35);
		contentPane.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Fornecedor");
		lblNewLabel_1_5.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblNewLabel_1_5.setBounds(493, 309, 135, 35);
		contentPane.add(lblNewLabel_1_5);
	}// fim do construtor
	
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
}//fim do código

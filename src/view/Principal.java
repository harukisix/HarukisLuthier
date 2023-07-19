package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
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

import model.DAO;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Principal extends JFrame {
	//Instanciar obejtos JDBC
		DAO dao = new DAO ();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnStatus;
	private JLabel lblData;
	public JLabel lblUsuario;
	public JButton btnFornecedor;
	public JButton btnUsuarios;
	private JLabel lblProdutos;
	public JPanel panelRodape;

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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		setTitle("Haruki´s Luthier");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/9118406_rock_on_icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(205, 12, 94), 2));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setBorder(new LineBorder(new Color(211, 22, 107), 5, true));
		btnUsuarios.setBackground(SystemColor.controlHighlight);
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/285648_group_user_icon.png")));
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setBounds(130, 45, 130, 128);
		contentPane.add(btnUsuarios);
		
		JButton btnSobre = new JButton("");
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setBorder(null);
		btnSobre.setContentAreaFilled(false);
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/1282960_info_information_informations_icon.png")));
		btnSobre.setBounds(647, 11, 48, 48);
		contentPane.add(btnSobre);
		
		panelRodape = new JPanel();
		panelRodape.setBorder(null);
		panelRodape.setBackground(new Color(219, 26, 84));
		panelRodape.setForeground(new Color(255, 255, 255));
		panelRodape.setBounds(0, 377, 705, 64);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
		btnStatus = new JButton("");
		btnStatus.setEnabled(false);
		btnStatus.setBounds(598, 0, 97, 73);
		panelRodape.add(btnStatus);
		btnStatus.setContentAreaFilled(false);
		btnStatus.setBorderPainted(false);
		btnStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/9111281_cloud_no_icon.png")));
		
		lblData = new JLabel("New Label");
		lblData.setBackground(SystemColor.text);
		lblData.setBounds(10, 30, 300, 24);
		panelRodape.add(lblData);
		lblData.setForeground(SystemColor.text);
		lblData.setFont(new Font("Century Gothic", Font.BOLD, 16));
		
		JLabel lblUsu = new JLabel("Usuário:");
		lblUsu.setForeground(new Color(255, 255, 255));
		lblUsu.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblUsu.setBounds(334, 35, 97, 14);
		panelRodape.add(lblUsu);
		
		lblUsuario = new JLabel("");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblUsuario.setBounds(400, 30, 131, 22);
		panelRodape.add(lblUsuario);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/310721_guiter_hard_instrument_loud noise_music_icon.png")));
		lblLogo.setBounds(10, 11, 128, 128);
		contentPane.add(lblLogo);
		
		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente cliente = new Cliente();
				cliente.setVisible(true);
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setContentAreaFilled(false);
		btnClientes.setBorder(new LineBorder(new Color(211, 22, 107), 5, true));
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/285645_user_icon.png")));
		btnClientes.setBounds(295, 200, 128, 128);
		contentPane.add(btnClientes);
		
		JButton btnRelatorio = new JButton("");
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setContentAreaFilled(false);
		btnRelatorio.setBorder(new LineBorder(new Color(211, 22, 107), 5, true));
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/1622837_analytics_docs_documents_graph_pdf_icon (1).png")));
		btnRelatorio.setBounds(130, 200, 128, 128);
		contentPane.add(btnRelatorio);
		
		JButton btnServicos = new JButton("");
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setContentAreaFilled(false);
		btnServicos.setBorder(new LineBorder(new Color(211, 22, 107), 5, true));
		btnServicos.setIcon(new ImageIcon(Principal.class.getResource("/img/3209381_application_article_content_form_paper_icon (1).png")));
		btnServicos.setBounds(295, 45, 128, 128);
		contentPane.add(btnServicos);
		
		btnFornecedor = new JButton("New button");
		btnFornecedor.setHorizontalAlignment(SwingConstants.LEADING);
		btnFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setVisible(true);
			}
		});
		btnFornecedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedor.setContentAreaFilled(false);
		btnFornecedor.setBorder(new LineBorder(new Color(217, 13, 109), 5, true));
		btnFornecedor.setIcon(new ImageIcon(Principal.class.getResource("/img/maozona.png")));
		btnFornecedor.setBounds(450, 200, 140, 128);
		contentPane.add(btnFornecedor);
		
		lblProdutos = new JLabel("New label");
		lblProdutos.setBorder(new LineBorder(new Color(190, 12, 87), 5, true));
		lblProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblProdutos.setIcon(new ImageIcon(Principal.class.getResource("/img/6214719_box_dropbox_logo_icon.png")));
		lblProdutos.setBounds(450, 45, 140, 128);
		contentPane.add(lblProdutos);
		setLocationRelativeTo(null);//Centralizar a janela 
	}
	/**
	 * Método responsável por exibir o status de conexão
	 * 
	 */
	private void status() {
		try {
			//abrir a conexão
			con = dao.conectar();
			if (con == null) {
				//System.out.println("Erro de Conexão");
				btnStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dboff.png")));
			} else {
				//System.out.println("Banco Conectado");
				btnStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dbon.png")));
			}
			//NUNCA esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim do metodo status ()
	/**
	 * Método responsável por setar a data no rodapé
	 */
	private void setarData() {
		// criar objeto para trazer a data do sistema
		Date data = new Date();
		// criar objeto para formatar data
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		// alterar o texto da label pela data atual formatada
		lblData.setText(formatador.format(data));
	}
}//fim do código

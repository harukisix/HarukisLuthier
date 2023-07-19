package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Cliente extends JDialog {
	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtFone;
	private JTextField txtEndereco;
	private JTextField txtEmail;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JTextField txtCEP;
	private JTextField txtNumero;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JComboBox cboUF;
	private JButton btnBuscarCep;
	private JTextField txtComplemento;
	private JTextField txtReferencia;
	private JScrollPane scrollPane;
	private JList listaClient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente dialog = new Cliente();
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
	public Cliente() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		getContentPane().setFont(new Font("DeVinne Txt BT", Font.BOLD, 11));
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Cliente.class.getResource("/img/2203549_admin_avatar_human_login_user_icon.png")));

		setTitle("Cadastro de Cliente");
		setResizable(false);
		setBounds(100, 100, 454, 505);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);// Centralizar a janela
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(122, 70, 253, 45);
		getContentPane().add(scrollPane);
		
		listaClient = new JList();
		listaClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
				listaClient.setEnabled(true);
			}
		});
		listaClient.setBorder(null);
		scrollPane.setViewportView(listaClient);

		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setBorder(new LineBorder(Color.BLACK));
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(122, 19, 86, 20);
		getContentPane().add(txtID);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(24, 21, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(24, 91, 46, 16);
		getContentPane().add(lblNewLabel_2);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
				
			}
		});
		txtNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtNome.setBorder(new LineBorder(Color.BLACK));
		txtNome.setColumns(10);
		txtNome.setBounds(122, 50, 253, 20);
		getContentPane().add(txtNome);
		// Uso do Validador para limitar o número de caracteres
		txtNome.setDocument(new Validador(50));

		txtFone = new JTextField();
		txtFone.setBorder(new LineBorder(Color.BLACK));
		txtFone.setColumns(10);
		txtFone.setBounds(122, 91, 253, 20);
		getContentPane().add(txtFone);
		// Uso do Validador para limitar o número de caracteres
		txtFone.setDocument(new Validador(11));

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(24, 51, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(24, 135, 46, 14);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Endereço");
		lblNewLabel_4.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_4.setBounds(24, 218, 76, 18);
		getContentPane().add(lblNewLabel_4);

		txtEndereco = new JTextField();
		txtEndereco.setBorder(new LineBorder(Color.BLACK));
		txtEndereco.setBounds(122, 216, 175, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);
		// Uso do Validador para limitar o número de caracteres
		txtEndereco.setDocument(new Validador(50));

		txtEmail = new JTextField();
		txtEmail.setBorder(new LineBorder(Color.BLACK));
		txtEmail.setBounds(122, 134, 253, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		// Uso do Validador para limitar o número de caracteres
		txtEmail.setDocument(new Validador(50));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(219, 26, 84));
		panel.setBounds(0, 406, 474, 60);
		getContentPane().add(panel);
		panel.setLayout(null);

		btnAdicionar = new JButton("");
		btnAdicionar.setBounds(11, 8, 49, 49);
		panel.add(btnAdicionar);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar
				.setIcon(new ImageIcon(Cliente.class.getResource("/img/4781840_+_add_circle_create_expand_icon.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);

		btnEditar = new JButton("");
		btnEditar.setBounds(106, 1, 60, 60);
		panel.add(btnEditar);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarContato();
			}
		});
		btnEditar.setIcon(new ImageIcon(Cliente.class.getResource("/img/8664843_pen_to_square_icon.png")));
		btnEditar.setEnabled(false);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);

		btnExcluir = new JButton("");
		btnExcluir.setBounds(265, 0, 60, 60);
		panel.add(btnExcluir);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Cliente.class.getResource("/img/8664938_trash_can_delete_remove_icon.png")));
		btnExcluir.setEnabled(false);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);

		btnLimpar = new JButton("");
		btnLimpar.setBounds(375, 0, 60, 60);
		panel.add(btnLimpar);
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Cliente.class.getResource("/img/9055449_bxs_eraser_icon.png")));
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);

		JLabel lblCEP = new JLabel("CEP");
		lblCEP.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblCEP.setBounds(24, 179, 46, 14);
		getContentPane().add(lblCEP);

		txtCEP = new JTextField();
		txtCEP.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtCEP.setBounds(122, 179, 86, 20);
		getContentPane().add(txtCEP);
		txtCEP.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("Nº");
		lblNewLabel_6.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_6.setBounds(307, 219, 46, 14);
		getContentPane().add(lblNewLabel_6);

		txtNumero = new JTextField();
		txtNumero.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtNumero.setBounds(331, 216, 44, 20);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Bairro");
		lblNewLabel_5.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_5.setBounds(24, 288, 46, 14);
		getContentPane().add(lblNewLabel_5);

		txtBairro = new JTextField();
		txtBairro.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtBairro.setBounds(122, 287, 175, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Cidade");
		lblNewLabel_7.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_7.setBounds(24, 319, 76, 14);
		getContentPane().add(lblNewLabel_7);

		txtCidade = new JTextField();
		txtCidade.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtCidade.setBounds(122, 318, 175, 20);
		getContentPane().add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("UF");
		lblNewLabel_8.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_8.setBounds(307, 319, 46, 14);
		getContentPane().add(lblNewLabel_8);

		cboUF = new JComboBox();
		cboUF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboUF.setFont(new Font("Century Gothic", Font.BOLD, 11));
		cboUF.setModel(new DefaultComboBoxModel(
				new String[] { " ", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUF.setBounds(331, 316, 44, 22);
		getContentPane().add(cboUF);

		btnBuscarCep = new JButton("Buscar CEP");
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnBuscarCep.setBounds(218, 177, 157, 23);
		getContentPane().add(btnBuscarCep);

		JLabel lblNewLabel_5_1 = new JLabel("Comp");
		lblNewLabel_5_1.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_5_1.setBounds(24, 247, 76, 24);
		getContentPane().add(lblNewLabel_5_1);

		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBorder(new LineBorder(Color.BLACK));
		txtComplemento.setBounds(122, 247, 175, 20);
		getContentPane().add(txtComplemento);
		
		txtReferencia = new JTextField();
		txtReferencia.setColumns(10);
		txtReferencia.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtReferencia.setBounds(122, 349, 175, 20);
		getContentPane().add(txtReferencia);
		
		JLabel lblNewLabel_7_1 = new JLabel("Referência ");
		lblNewLabel_7_1.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_7_1.setBounds(24, 355, 88, 14);
		getContentPane().add(lblNewLabel_7_1);

	}// fim do método construtor

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtEmail.setText(null);
		txtFone.setText(null);
		txtEndereco.setText(null);
		txtComplemento.setText(null);
		txtCEP.setText(null);
		txtNumero.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUF.setSelectedItem(null);
		txtReferencia.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		listaClient.setEnabled(true);

	}// fim do método limparCampos()

	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatórios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do cliente");
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do cliente");
			txtFone.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email do cliente");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço do cliente");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número do cliente");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do cliente");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade do cliente");
			txtCidade.requestFocus();
		} else {

			// Lógica Principal
			// CRUD Create
			String create = "insert into cliente(nome,fone,email,endereco,numero,cep,comp,bairro,cidade,uf,ref) values (?,?,?,?,?,?,?,?,?,?,?)";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql - CRUD Read)
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtNumero.getText());
				pst.setString(6, txtCEP.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, cboUF.getSelectedItem().toString());
				pst.setString(11, txtReferencia.getText());
				// executa a query(instrução sql (CRUD - Create))
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Cliente Adicionado");
				// limpar os campos
				limparCampos();
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}// fim do método adicionar

	/**
	 * Método para editar um contato (ATENÇÃO!!! Usar o ID)
	 */
	private void editarContato() {
		// System.out.println("teste do botão editar");
		// Validação dos campos obrigatórios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do cliente");
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do cliente");
			txtFone.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email do cliente");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço do cliente");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número do cliente");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do cliente");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade do cliente");
			txtCidade.requestFocus();
		} else {
			// lógica principal
			// CRUD - Update
			String update = "update cliente set nome=?,fone=?,email=?,endereco=?,numero=?,cep=?,comp=?,bairro=?,cidade=?,uf=?,ref=? where idcli =?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtNumero.getText());
				pst.setString(6, txtCEP.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, cboUF.getSelectedItem().toString());
				pst.setString(11, txtReferencia.getText());
				pst.setString(12, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do cliente editado com sucesso.");
				// Limpar os campos
				limparCampos();
				// Fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}// fim do método editar contato

	/**
	 * Método usado para excluir um contato
	 */
	private void excluirUsuario() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confirma captura a opção escolhida
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste cliente?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete
			String delete = "delete from cliente where idcli=?";
			// tratamento de exceções
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				// substituir a ? pelo id do contato
				pst.setString(1, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// limpar campos
				limparCampos();
				// exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, "Cliente excluído");
				// fechar a conexão
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Pedido não entregue.");
			} catch (Exception e2) {
				System.out.println(e2);
		}
		}
	} // fim do método excluir contato
	
		// PlainDocument -> recursos para formatação

	public class Validador extends PlainDocument {
		// variável que irá armazenar o número máximo de caracteres permitidos
		private int limite;
		// construtor personalizado -> será usado pela caixas de texto JTextFiled

		public Validador(int limite) {
			super();
			this.limite = limite;
		}

		// Método interno para validar o limite de caracteres
		// BadLocation -> Tratamento de exceções
		public void insertString(int ofs, String str, AttributeSet a) throws BadLocationException {
			// Se o limite não for ultrapassado permitir a digitação
			if ((getLength() + str.length()) <= limite) {
				super.insertString(ofs, str, a);
			}
		}

	}

	/**
	 * buscarCep
	 */
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCEP.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUF.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						System.out.println("Ok");
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

/**
	 * Método usado para listar o nome dos usuários na lista
	 */
	private void listarClientes() {
		// System.out.println("teste");
		// A linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// objeto irá temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// Setar o modelo (vetor na lista)
		listaClient.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select * from cliente where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			// Abrir a conexão
			con = dao.conectar();
			// Preparar a query (instrução sql)
			pst = con.prepareStatement(readLista);
			// Executar a query e trazer o resultado para lista
			rs = pst.executeQuery();
			// Uso do while para trzer os usuários enquanto existir
			while (rs.next()) {
				// Mostrar a barra de rolagem (scrollpane)
				scrollPane.setVisible(true);
				// Mostrar a lista
				// listaUsers.setVisible(true);
				// Adicionar os usuários no vetor -> lista
				modelo.addElement(rs.getString(2));
				// Esconder a lista se nenhuma letra for digitada
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
			}
			}
			// Fechar Conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	/**
	 * Método que busca o usuário selecionado na lista
	 */
	private void buscarClienteLista() {
		//System.out.println("Teste");
		// Variável que captura o índice da linha da lista
		int linha = listaClient.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução SQL)
			// limit (0,1) -> seleciona o índice 0 do vetor e 1 usuário da lista
			String readListaUsuario = "select * from cliente where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				//abrir a conexão
				con = dao.conectar();
				//preparar a query para execução
				pst = con.prepareStatement(readListaUsuario);
				// executar e obter o resultado
				rs = pst.executeQuery();
				
				if(rs.next()) {
					// Esconder a lista
					scrollPane.setVisible(false);
					// Setar os campos
					txtID.setText(rs.getString(1)); 
					txtNome.setText(rs.getString(2));
					txtFone.setText(rs.getString(3));
					txtEmail.setText(rs.getString(4)); 
					txtCEP.setText(rs.getString(7)); 
					txtEndereco.setText(rs.getString(5));
					txtNumero.setText(rs.getString(6)); 
					txtComplemento.setText(rs.getString(8)); 
					txtBairro.setText(rs.getString(9)); 
					txtCidade.setText(rs.getString(10));
					cboUF.setSelectedItem(rs.getString(11)); 
					txtReferencia.setText(rs.getString(12)); 
					// Validação (liberação dos botões)
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					listaClient.setEnabled(false);
					btnAdicionar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Cliente inexistente");
				}
				// Fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// Se não existir no banco um usuário da lista
			scrollPane.setVisible(false);
		}
		
	} // Fim método buscar Usuário AVANÇADO (Busca Google)
}// fim do código
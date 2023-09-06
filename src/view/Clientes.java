package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import javax.swing.JPanel;

public class Clientes extends JDialog {
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
	private JTextField txtEndereco;
	private JTextField txtComplemento;
	private JTextField txtEmail;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JTextField txtReferencia;
	private JTextField txtNumero;
	private JTextField txtCEP;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JLabel lblUF;
	private JComboBox cboUF;
	private JButton btnBuscarCep;
	private JScrollPane scrollPane;
	private JList listaClient;
	private JFormattedTextField txtFone;
	private JPanel panel;
	private JLabel lblNewLabel_6;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
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
	public Clientes() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/note.png")));
		setTitle("Cadastro de Cliente");
		setResizable(false);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
	    setLocationRelativeTo(null);
		
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(169, 69, 290, 60);
		getContentPane().add(scrollPane);
		
		listaClient = new JList();
		scrollPane.setViewportView(listaClient);
		listaClient.setBorder(null);
		listaClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
				listaClient.setEnabled(true);
			}
		});

		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setBorder(new LineBorder(Color.BLACK));
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(169, 21, 86, 20);
		getContentPane().add(txtID);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel.setBounds(24, 21, 46, 18);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_2.setBounds(24, 91, 46, 16);
		getContentPane().add(lblNewLabel_2);

		

		
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setBorder(new LineBorder(Color.BLACK));
		txtNome.setColumns(10);
		txtNome.setBounds(169, 55, 290, 20);
		getContentPane().add(txtNome);
		txtNome.setDocument(new Validador(50));
		

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_1.setBounds(24, 51, 75, 20);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_3.setBounds(24, 135, 46, 19);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Endereço");
		lblNewLabel_4.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_4.setBounds(24, 258, 125, 32);
		getContentPane().add(lblNewLabel_4);

		txtEndereco = new JTextField();
		txtEndereco.setBorder(new LineBorder(Color.BLACK));
		txtEndereco.setBounds(169, 268, 290, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);
		txtEndereco.setDocument(new Validador(50));

		JLabel lblNewLabel_5 = new JLabel("Complemento");
		lblNewLabel_5.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_5.setBounds(24, 317, 136, 21);
		getContentPane().add(lblNewLabel_5);

		txtComplemento = new JTextField();
		txtComplemento.setBorder(new LineBorder(Color.BLACK));
		txtComplemento.setBounds(170, 321, 289, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);
		txtComplemento.setDocument(new Validador(20));

		txtEmail = new JTextField();
		txtEmail.setBorder(new LineBorder(Color.BLACK));
		txtEmail.setBounds(169, 134, 290, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setDocument(new Validador(50));

		JLabel lblReferencia = new JLabel("Referência");
		lblReferencia.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblReferencia.setBounds(24, 417, 136, 21);
		getContentPane().add(lblReferencia);

		txtReferencia = new JTextField();
		txtReferencia.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtReferencia.setBounds(169, 421, 290, 20);
		getContentPane().add(txtReferencia);
		txtReferencia.setColumns(10);
		txtReferencia.setDocument(new Validador(50));

		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBorder(new LineBorder(Color.BLACK));
		txtNumero.setBounds(554, 268, 94, 20);
		getContentPane().add(txtNumero);
		txtNumero.setDocument(new Validador(10));

		JLabel lblNewLabel_4_1 = new JLabel("Nº");
		lblNewLabel_4_1.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_4_1.setBounds(484, 270, 60, 14);
		getContentPane().add(lblNewLabel_4_1);

		JLabel lblNewLabel_4_2 = new JLabel("CEP");
		lblNewLabel_4_2.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_4_2.setBounds(24, 211, 60, 20);
		getContentPane().add(lblNewLabel_4_2);

		txtCEP = new JTextField();
		txtCEP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
			}
		}});
		txtCEP.setColumns(10);
		txtCEP.setBorder(new LineBorder(Color.BLACK));
		txtCEP.setBounds(169, 215, 145, 20);
		getContentPane().add(txtCEP);
		txtCEP.setDocument(new Validador(10));
		

		JLabel lblNewLabel_4_1_1 = new JLabel("Bairro");
		lblNewLabel_4_1_1.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_4_1_1.setBounds(484, 369, 60, 18);
		getContentPane().add(lblNewLabel_4_1_1);

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBorder(new LineBorder(Color.BLACK));
		txtBairro.setBounds(554, 372, 197, 20);
		getContentPane().add(txtBairro);
		txtBairro.setDocument(new Validador(30));

		JLabel lblNewLabel_5_1 = new JLabel("Cidade");
		lblNewLabel_5_1.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel_5_1.setBounds(24, 372, 92, 18);
		getContentPane().add(lblNewLabel_5_1);

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBorder(new LineBorder(Color.BLACK));
		txtCidade.setBounds(169, 372, 290, 20);
		getContentPane().add(txtCidade);
		txtCidade.setDocument(new Validador(30));

		lblUF = new JLabel("UF");
		lblUF.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblUF.setBounds(482, 420, 28, 14);
		getContentPane().add(lblUF);

		cboUF = new JComboBox();
		cboUF.setFont(new Font("Tahoma", Font.BOLD, 13));
		cboUF.setModel(new DefaultComboBoxModel(
				new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
						"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUF.setBounds(554, 419, 46, 22);
		getContentPane().add(cboUF);

		btnBuscarCep = new JButton("Buscar CEP");
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(484, 214, 114, 23);
		getContentPane().add(btnBuscarCep);
		
				MaskFormatter msf = null;
				try { msf = new MaskFormatter("(##)#####-####");			
				} catch (Exception e) {
					e.printStackTrace();
				}
		txtFone = new JFormattedTextField(msf);
		txtFone.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtFone.setBounds(169, 93, 290, 20);
		getContentPane().add(txtFone);
		
		panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 481, 795, 80);
		getContentPane().add(panel);
				panel.setLayout(null);
		
				btnLimpar = new JButton("");
				btnLimpar.setBounds(670, 0, 65, 76);
				panel.add(btnLimpar);
				btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnLimpar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						limparCampos();
					}
				});
				btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/borracha.png")));
				btnLimpar.setToolTipText("Limpar Campos");
				btnLimpar.setContentAreaFilled(false);
				btnLimpar.setBorder(null);
				
						btnExcluir = new JButton("");
						btnExcluir.setBounds(470, 0, 65, 71);
						panel.add(btnExcluir);
						btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						btnExcluir.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								excluirUsuario();
							}
						});
						btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/excluir.png")));
						btnExcluir.setEnabled(false);
						btnExcluir.setContentAreaFilled(false);
						btnExcluir.setBorder(null);
						
								btnEditar = new JButton("");
								btnEditar.setBounds(220, 0, 65, 71);
								panel.add(btnEditar);
								btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								btnEditar.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										editarContato();
									}
								});
								btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/editar.png")));
								btnEditar.setEnabled(false);
								btnEditar.setContentAreaFilled(false);
								btnEditar.setBorder(null);
								
										
										btnAdicionar = new JButton("");
										btnAdicionar.setBounds(31, 0, 65, 71);
										panel.add(btnAdicionar);
										btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										btnAdicionar.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												adicionar();
											}
										});
										btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/adicionar.png")));
										btnAdicionar.setContentAreaFilled(false);
										btnAdicionar.setBorder(null);
										
										lblNewLabel_6 = new JLabel("");
										lblNewLabel_6.setIcon(new ImageIcon(Clientes.class.getResource("/view/clientee.png")));
										lblNewLabel_6.setBounds(577, 21, 174, 151);
										getContentPane().add(lblNewLabel_6);



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

	}

	
	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
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

		
			String create = "insert into cliente(nome,fone,email,cep,endereco,numero,comp,bairro,cidade,uf,ref) values (?,?,?,?,?,?,?,?,?,?,?)";
			try {
				// abrir a conexão
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtCEP.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, cboUF.getSelectedItem().toString());
				pst.setString(11, txtReferencia.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Cliente Adicionado");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}
	/**
	 * Método para editar um contato (ATENÇÃO!!! Usar o ID)
	 */
	private void editarContato() {
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
		
			String update = "update cliente set nome=?,fone=?,email=?,cep=?,endereco=?,numero=?,comp=?,bairro=?,cidade=?,uf=?,ref=? where idcli =?";
	
			try {
			
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(7, txtID.getText());
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtCEP.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, cboUF.getSelectedItem().toString());
				pst.setString(11, txtReferencia.getText());
				pst.setString(12, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do cliente editado com sucesso.");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * Método usado para excluir um contato
	 */
	private void excluirUsuario() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste cliente?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			
			String delete = "delete from cliente where idcli=?";
			
			try {
			
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Cliente excluído");
				con.close();
				
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não excluido. \nEste cliente ainda tem um serviço pendente");
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	} 

	public class Validador extends PlainDocument {
		
		private int limite;
		

		public Validador(int limite) {
			super();
			this.limite = limite;
		}

		
		public void insertString(int ofs, String str, AttributeSet a) throws BadLocationException {
			
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
						System.out.println("OK!");
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
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaClient.setModel(modelo);
		String readLista = "select * from cliente where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
			}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	/**
	 * Método que busca o usuário selecionado na lista
	 */
	private void buscarClienteLista() {
		int linha = listaClient.getSelectedIndex();
		if (linha >= 0) {
			String readListaUsuario = "select * from cliente where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(readListaUsuario);
			
				rs = pst.executeQuery();
				
				if(rs.next()) {
					
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1)); 
					txtNome.setText(rs.getString(2));
					txtFone.setText(rs.getString(3));
					txtEmail.setText(rs.getString(4)); 
					txtCEP.setText(rs.getString(5)); 
					txtEndereco.setText(rs.getString(6));
					txtNumero.setText(rs.getString(7)); 
					txtComplemento.setText(rs.getString(8)); 
					txtBairro.setText(rs.getString(9)); 
					txtCidade.setText(rs.getString(10));
					cboUF.setSelectedItem(rs.getString(11)); 
					txtReferencia.setText(rs.getString(12)); 
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					listaClient.setEnabled(false);
					btnAdicionar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Cliente inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPane.setVisible(false);
		}
		
	}
}

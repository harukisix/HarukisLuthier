package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import view.Clientes.Validador;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JComboBox;
import java.awt.Font;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Cursor;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.SystemColor;

public class Fornecedor extends JDialog {
		DAO dao = new DAO();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtRazaoSocial;
	private JTextField txtEmail;
	private JTextField txtCEP;
	private JTextField txtEndereco;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JTextField txtReferencia;
	private JTextField txtCNPJ;
	private JFormattedTextField txtFone;
	private JComboBox cboUF;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JList listaFornecedor;
	private JScrollPane scrollPane;
	private JTextField txtFantasia;
	private JTextField txtVendedor;
	private JTextField txtSite;
	private JLabel lblNewLabel_3_1_2_2;
	private JTextField txtIE;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fornecedor dialog = new Fornecedor();
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
	public Fornecedor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fornecedor.class.getResource("/img/note.png")));
		setTitle("Fornecedores");
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
	    setLocationRelativeTo(null);
		
		txtID = new JTextField();
		txtID.setEnabled(false);
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBorder(new LineBorder(Color.BLACK));
		txtID.setBounds(35, 38, 86, 20);
		getContentPane().add(txtID);
		
		txtRazaoSocial = new JTextField();
		txtRazaoSocial.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		txtRazaoSocial.setColumns(10);
		txtRazaoSocial.setBorder(new LineBorder(Color.BLACK));
		txtRazaoSocial.setBounds(35, 88, 240, 20);
		getContentPane().add(txtRazaoSocial);
		txtRazaoSocial.setDocument(new Validador(50));
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(35, 108, 290, 61);
		getContentPane().add(scrollPane);
		
		listaFornecedor = new JList();
		scrollPane.setViewportView(listaFornecedor);
		listaFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedorLista();
				listaFornecedor.setEnabled(rootPaneCheckingEnabled);;
			}
		});
		listaFornecedor.setBorder(null);
		
		MaskFormatter msf = null;
		try { msf = new MaskFormatter("(##)#####-####");			
		} catch (Exception e) {
			e.printStackTrace();
		}
		txtFone = new JFormattedTextField(msf);
		txtFone.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtFone.setBounds(35, 357, 240, 20);
		getContentPane().add(txtFone);
		txtFone.setDocument(new Validador(18));
		
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBorder(new LineBorder(Color.BLACK));
		txtEmail.setBounds(436, 38, 290, 20);
		getContentPane().add(txtEmail);
		txtEmail.setDocument(new Validador(50));
		
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
		txtCEP.setBounds(436, 88, 145, 20);
		getContentPane().add(txtCEP);
		txtCEP.setDocument(new Validador(10));
		
		JButton btnBuscarCep = new JButton("Buscar CEP");
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(610, 87, 114, 23);
		getContentPane().add(btnBuscarCep);
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBorder(new LineBorder(Color.BLACK));
		txtEndereco.setBounds(436, 139, 290, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setDocument(new Validador(50));
		
		
		txtNumero = new JTextField();
		txtNumero.setColumns(10);
		txtNumero.setBorder(new LineBorder(Color.BLACK));
		txtNumero.setBounds(436, 187, 94, 20);
		getContentPane().add(txtNumero);
		txtNumero.setDocument(new Validador(20));
		
		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBorder(new LineBorder(Color.BLACK));
		txtComplemento.setBounds(436, 243, 199, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setDocument(new Validador(20));
		
		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBorder(new LineBorder(Color.BLACK));
		txtBairro.setBounds(540, 187, 188, 20);
		getContentPane().add(txtBairro);
		txtBairro.setDocument(new Validador(30));
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBorder(new LineBorder(Color.BLACK));
		txtCidade.setBounds(436, 299, 290, 20);
		getContentPane().add(txtCidade);
		txtCidade.setDocument(new Validador(30));
		
		txtReferencia = new JTextField();
		txtReferencia.setColumns(10);
		txtReferencia.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtReferencia.setBounds(436, 414, 290, 20);
		getContentPane().add(txtReferencia);
		txtReferencia.setDocument(new Validador(50));
		
		cboUF = new JComboBox();
		cboUF.setModel(new DefaultComboBoxModel(new String[] {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUF.setFont(new Font("Tahoma", Font.BOLD, 13));
		cboUF.setBounds(436, 355, 46, 22);
		getContentPane().add(cboUF);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(35, 13, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(35, 330, 46, 16);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_4_2 = new JLabel("CEP");
		lblNewLabel_4_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4_2.setBounds(436, 69, 60, 14);
		getContentPane().add(lblNewLabel_4_2);
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(436, 13, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Endereço");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4.setBounds(436, 114, 60, 14);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Complemento");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5.setBounds(436, 218, 92, 14);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("Cidade");
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5_1.setBounds(436, 274, 92, 14);
		getContentPane().add(lblNewLabel_5_1);
		
		JLabel lblReferencia = new JLabel("Referência");
		lblReferencia.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblReferencia.setBounds(436, 389, 92, 14);
		getContentPane().add(lblReferencia);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("Bairro");
		lblNewLabel_4_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4_1_1.setBounds(540, 170, 60, 14);
		getContentPane().add(lblNewLabel_4_1_1);
		
		JLabel lblNewLabel_4_1 = new JLabel("Número");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4_1.setBounds(436, 170, 60, 14);
		getContentPane().add(lblNewLabel_4_1);
		
		JLabel lblRazoSocial = new JLabel("Razão Social");
		lblRazoSocial.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRazoSocial.setBounds(35, 69, 114, 14);
		getContentPane().add(lblRazoSocial);
		
		JLabel lblNewLabel_3_1 = new JLabel("CNPJ");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1.setBounds(35, 388, 46, 14);
		getContentPane().add(lblNewLabel_3_1);
		
		txtCNPJ = new JTextField();
		txtCNPJ.setColumns(10);
		txtCNPJ.setBorder(new LineBorder(Color.BLACK));
		txtCNPJ.setBounds(35, 413, 240, 20);
		getContentPane().add(txtCNPJ);
		
		JLabel lblUF = new JLabel("UF");
		lblUF.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUF.setBounds(436, 330, 46, 14);
		getContentPane().add(lblUF);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("CNPJ");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1_1.setBounds(-52, 89, 46, 14);
		getContentPane().add(lblNewLabel_3_1_1);
		
		txtFantasia = new JTextField();
		txtFantasia.setColumns(10);
		txtFantasia.setBorder(new LineBorder(Color.BLACK));
		txtFantasia.setBounds(35, 139, 240, 20);
		getContentPane().add(txtFantasia);
		
		JLabel lblFantasia = new JLabel("Fantasia");
		lblFantasia.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFantasia.setBounds(35, 119, 114, 14);
		getContentPane().add(lblFantasia);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("Vendedor");
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1_2.setBounds(35, 164, 94, 14);
		getContentPane().add(lblNewLabel_3_1_2);
		
		txtVendedor = new JTextField();
		txtVendedor.setColumns(10);
		txtVendedor.setBorder(new LineBorder(Color.BLACK));
		txtVendedor.setBounds(35, 187, 240, 20);
		getContentPane().add(txtVendedor);
		
		JLabel lblNewLabel_3_1_2_1 = new JLabel("Site");
		lblNewLabel_3_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1_2_1.setBounds(35, 218, 94, 14);
		getContentPane().add(lblNewLabel_3_1_2_1);
		
		txtSite = new JTextField();
		txtSite.setColumns(10);
		txtSite.setBorder(new LineBorder(Color.BLACK));
		txtSite.setBounds(35, 243, 240, 20);
		getContentPane().add(txtSite);
		
		lblNewLabel_3_1_2_2 = new JLabel("IE");
		lblNewLabel_3_1_2_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1_2_2.setBounds(35, 274, 94, 14);
		getContentPane().add(lblNewLabel_3_1_2_2);
		
		txtIE = new JTextField();
		txtIE.setColumns(10);
		txtIE.setBorder(new LineBorder(Color.BLACK));
		txtIE.setBounds(35, 299, 240, 20);
		getContentPane().add(txtIE);
		
		panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(-1, 481, 794, 99);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		btnLimpar = new JButton("");
		btnLimpar.setBounds(670, 11, 60, 60);
		panel.add(btnLimpar);
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/borracha.png")));
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		
		btnExcluir = new JButton("");
		btnExcluir.setBounds(515, 8, 71, 63);
		panel.add(btnExcluir);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirFornecedor();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/excluir.png")));
		btnExcluir.setEnabled(false);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		
		btnEditar = new JButton("");
		btnEditar.setBounds(161, 11, 71, 61);
		panel.add(btnEditar);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarFornecedor();
			}
		});
		btnEditar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/editar.png")));
		btnEditar.setEnabled(false);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setBounds(23, 9, 71, 63);
		panel.add(btnAdicionar);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Fornecedor.class.getResource("/img/adicionar.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(128, 0, 255));
		panel_1.setForeground(new Color(128, 0, 255));
		panel_1.setBounds(350, 0, 60, 579);
		getContentPane().add(panel_1);



		
	}//FIM DO CONSTRUTOR
	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtCNPJ.setText(null);
		txtRazaoSocial.setText(null);
		txtEmail.setText(null);
		txtFone.setText(null);
		txtEndereco.setText(null);
		txtComplemento.setText(null);
		txtCEP.setText(null);
		txtNumero.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		txtFantasia.setText(null);
		txtVendedor.setText(null);
		txtSite.setText(null);
		txtIE.setText(null);
		cboUF.setSelectedItem(null);
		txtReferencia.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		listaFornecedor.setEnabled(true);

	}

	
	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a razão social do fornecedor");
			txtRazaoSocial.requestFocus();
		} else if (txtFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a fantasia do fornecedor");
			txtFantasia.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha o fone do fornecedor");
					txtFone.requestFocus();
		} else if (txtCNPJ.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o CNPJ do fornecedor");
				txtCNPJ.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email do fornecedor");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço do fornecedor");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número do fornecedor");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do fornecedor");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade do fornecedor");
			txtCidade.requestFocus();
		} else {

			String create = "insert into fornecedores(razaosocial,fantasia,vendedor,site,ie,fone,cnpj,email,cep,endereco,numero,comp,bairro,cidade,uf,ref) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
			try {
				
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtRazaoSocial.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtVendedor.getText());
				pst.setString(4, txtFone.getText());
				pst.setString(5, txtSite.getText());
				pst.setString(6, txtIE.getText());
				pst.setString(7, txtCNPJ.getText());
				pst.setString(8, txtEmail.getText());
				pst.setString(9, txtCEP.getText());
				pst.setString(10, txtEndereco.getText());
				pst.setString(11, txtNumero.getText());
				pst.setString(12, txtComplemento.getText());
				pst.setString(13, txtBairro.getText());
				pst.setString(14, txtCidade.getText());
				pst.setString(15, cboUF.getSelectedItem().toString());
				pst.setString(16, txtReferencia.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Fornecedor Adicionado");
				limparCampos();
				con.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "CNPJ JA CADASTRADO!!");
			}

		}
	}

	/**
	 * Método para editar um contato (ATENÇÃO!!! Usar o ID)
	 */
	private void editarFornecedor() {
		
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a razão social do fornecedor");
			txtRazaoSocial.requestFocus();
		} else if (txtFantasia.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha a fantasia do fornecedor");
				txtFantasia.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do fornecedor");
			txtFone.requestFocus();
		} else if (txtCNPJ.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o CNPJ do fornecedor");
				txtCNPJ.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email do fornecedor");
			txtEmail.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço do fornecedor");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número do fornecedor");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro do fornecedor");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a cidade do fornecedor");
			txtCidade.requestFocus();
		} else {
			
			String update = "update fornecedores set razaosocial=?,fone=?,fantasia=?,vendedor=?,site=?,ie=?,cnpj=?,email=?,cep=?,endereco=?,numero=?,comp=?,bairro=?,cidade=?,uf=?,ref=? where codefornecedor =?";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(update);
				pst.setString(17, txtID.getText());
				pst.setString(1, txtRazaoSocial.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtSite.getText());
				pst.setString(6, txtIE.getText());
				pst.setString(7, txtCNPJ.getText());
				pst.setString(8, txtEmail.getText());
				pst.setString(9, txtCEP.getText());
				pst.setString(10, txtEndereco.getText());
				pst.setString(11, txtNumero.getText());
				pst.setString(12, txtComplemento.getText());
				pst.setString(13, txtBairro.getText());
				pst.setString(14, txtCidade.getText());
				pst.setString(15, cboUF.getSelectedItem().toString());
				pst.setString(16, txtReferencia.getText());
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Dados do fornecedor editado com sucesso.");
				
				limparCampos();
				
				con.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "CNPJ JA CADASTRADO!!");
			}

		}

	}

	/**
	 * Método usado para excluir um contato
	 */
	private void excluirFornecedor() {
		
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste fornecedor?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			
			String delete = "delete from fornecedores where codefornecedor=?";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(delete);
				
				pst.setString(1, txtID.getText());
			
				pst.executeUpdate();
				
				limparCampos();
				
				JOptionPane.showMessageDialog(null, "Fornecedor excluído");
				
				con.close();
				
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Fornecedor não excluido. \nEste fornecedor ainda tem um serviço pendente");
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

	} // Fim do validador

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
	private void listarFornecedor() {
		
		DefaultListModel<String> modelo = new DefaultListModel<>();
		
		listaFornecedor.setModel(modelo);
		
		String readLista = "select * from fornecedores where razaosocial like '" + txtRazaoSocial.getText() + "%'" + "order by razaosocial";
		try {
			
			con = dao.conectar();
			
			pst = con.prepareStatement(readLista);
			
			rs = pst.executeQuery();
		
			while (rs.next()) {
				
				scrollPane.setVisible(true);
				
				modelo.addElement(rs.getString(2));
			
				if (txtRazaoSocial.getText().isEmpty()) {
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
	private void buscarFornecedorLista() {
		
		int linha = listaFornecedor.getSelectedIndex();
		if (linha >= 0) {
		
			String readListaUsuario = "select * from fornecedores where razaosocial like '" + txtRazaoSocial.getText() + "%'" + "order by razaosocial limit " + (linha) + " , 1";
			try {
			
				con = dao.conectar();
				
				pst = con.prepareStatement(readListaUsuario);
				
				rs = pst.executeQuery();
				
				if(rs.next()) {
					
					scrollPane.setVisible(false);
					
					txtID.setText(rs.getString(1)); 
					txtRazaoSocial.setText(rs.getString(2));
					txtFantasia.setText(rs.getString(3));
					txtFone.setText(rs.getString(7));
					txtVendedor.setText(rs.getString(5));
					txtSite.setText(rs.getString(8));
					txtIE.setText(rs.getString(4));
					txtCNPJ.setText(rs.getString(6)); 
					txtEmail.setText(rs.getString(9)); 
					txtCEP.setText(rs.getString(10)); 
					txtEndereco.setText(rs.getString(11));
					txtNumero.setText(rs.getString(12)); 
					txtComplemento.setText(rs.getString(13)); 
					txtBairro.setText(rs.getString(14)); 
					txtCidade.setText(rs.getString(15));
					cboUF.setSelectedItem(rs.getString(16)); 
					txtReferencia.setText(rs.getString(17)); 
				
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					listaFornecedor.setEnabled(false);
					btnAdicionar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");
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

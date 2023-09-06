package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import com.toedter.calendar.JDateChooser;

import model.DAO;

public class Produtos extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private FileInputStream fis;

	private int tamanho;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JFileChooser jfc = new JFileChooser();
	private JTextField txtID;
	private JTextField txtBarCode;
	private JTextField txtEstoque;
	private JTextField txtEstoqueMin;
	private JTextField txtLocalArmazenamento;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JLabel lblFoto;
	private JButton btnCarregar;
	private JComboBox cboUNID;
	private JLabel txt;
	private JScrollPane scrollPane;
	private JList listaFornecedor;
	private JTextField txtFornecedor;
	private JTextField txtIDFornecedor;
	private JButton btnBuscarID;
	private JTextField txtProduto;
	private JTextField txtLote;
	private JTextField txtFabricante;
	private JTextField txtCusto;
	private JLabel lblCusto;
	private JLabel lblLucro;
	private JTextField txtLucro;
	private JDateChooser dateEnt;
	private JTextArea txtaDescricao;
	private JDateChooser dateVal;
	private JCheckBox chckFoto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produtos dialog = new Produtos();
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
	public Produtos() {
		getContentPane().setLocation(-322, -13);
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/note.png")));
		setTitle("Produtos\r\n");
		setBounds(-8, -16, 800, 600);
		getContentPane().setLayout(null);

		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBounds(296, 10, 206, 82);
		panel.setBorder(new TitledBorder(null, "Fornecedores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(10, 38, 185, 54);
		panel.add(scrollPane);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		listaFornecedor = new JList();
		scrollPane.setViewportView(listaFornecedor);
		listaFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedorLista();
				listaFornecedor.setEnabled(true);
				;
			}
		});
		listaFornecedor.setBorder(null);

		txtFornecedor = new JTextField();
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
		});
		txtFornecedor.setBounds(10, 21, 186, 20);
		panel.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("ID do Fornecedor");
		lblNewLabel_2.setBounds(10, 52, 104, 14);
		panel.add(lblNewLabel_2);

		txtIDFornecedor = new JTextField();
		txtIDFornecedor.setEditable(false);
		txtIDFornecedor.setBounds(114, 49, 71, 20);
		panel.add(txtIDFornecedor);
		txtIDFornecedor.setColumns(10);

		txtID = new JTextField();
		txtID.setBounds(137, 23, 97, 20);
		txtID.addKeyListener(new KeyAdapter() {
		});
		txtID.setEnabled(false);
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(txtID);

		JLabel lblNewLabel = new JLabel("Código");
		lblNewLabel.setBounds(25, 23, 67, 18);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblNewLabel);

		JLabel lblBarcode = new JLabel("BarCode");
		lblBarcode.setBounds(25, 120, 67, 14);
		lblBarcode.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblBarcode);

		txtBarCode = new JTextField();
		txtBarCode.setBounds(137, 117, 309, 20);
		txtBarCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarBarCode();
				}
			}
		});
		txtBarCode.setColumns(10);
		txtBarCode.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(txtBarCode);

		txtBarCode.setDocument(new Validador(20));

		JLabel lblDescrio = new JLabel("Imagem");
		lblDescrio.setBounds(505, 250, 86, 20);
		lblDescrio.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblDescrio);

		JLabel lblEstoque = new JLabel("Estoque");
		lblEstoque.setBounds(505, 321, 86, 14);
		lblEstoque.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblEstoque);

		txtEstoque = new JTextField();
		txtEstoque.setBounds(601, 319, 173, 20);
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEstoque.setColumns(10);
		txtEstoque.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(txtEstoque);

		JLabel lblEstoqueMin = new JLabel("Estoque Min.");
		lblEstoqueMin.setBounds(505, 358, 86, 14);
		lblEstoqueMin.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblEstoqueMin);

		txtEstoqueMin = new JTextField();
		txtEstoqueMin.setBounds(601, 356, 173, 20);
		txtEstoqueMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtEstoqueMin.setColumns(10);
		txtEstoqueMin.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(txtEstoqueMin);

		btnCarregar = new JButton("Pesquisar");
		btnCarregar.setBounds(601, 250, 173, 23);
		btnCarregar.setEnabled(false);
		btnCarregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		getContentPane().add(btnCarregar);

		JLabel lblUnid = new JLabel("UNID.");
		lblUnid.setBounds(601, 430, 61, 14);
		lblUnid.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblUnid);

		cboUNID = new JComboBox();
		cboUNID.setBounds(655, 426, 46, 22);
		cboUNID.setModel(new DefaultComboBoxModel(new String[] { "UN", "CX", "PC", "KG", "M" }));
		cboUNID.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(cboUNID);

		JLabel lblLocalDeArmazem = new JLabel("Local ");
		lblLocalDeArmazem.setBounds(505, 383, 52, 14);
		lblLocalDeArmazem.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblLocalDeArmazem);

		txtLocalArmazenamento = new JTextField();
		txtLocalArmazenamento.setBounds(601, 399, 173, 20);
		txtLocalArmazenamento.setColumns(10);
		txtLocalArmazenamento.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(txtLocalArmazenamento);

		txtLocalArmazenamento.setDocument(new Validador(50));

		JLabel lblArmazenamento = new JLabel("Armaz");
		lblArmazenamento.setBounds(505, 398, 61, 21);
		lblArmazenamento.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblArmazenamento);

		lblFoto = new JLabel("");
		lblFoto.setBounds(518, 10, 256, 224);
		lblFoto.setToolTipText("");
		lblFoto.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/image add 256.png")));
		getContentPane().add(lblFoto);

		txt = new JLabel("Descrição");
		txt.setBounds(25, 250, 86, 20);
		txt.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(txt);

		btnBuscarID = new JButton("");
		btnBuscarID.setBounds(244, 10, 32, 32);
		btnBuscarID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarID();
			}
		});
		btnBuscarID.setBorder(null);
		btnBuscarID.setContentAreaFilled(false);
		btnBuscarID.setIcon(new ImageIcon(Produtos.class.getResource("/img/Pesquisar.png")));
		btnBuscarID.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().add(btnBuscarID);

		txtProduto = new JTextField();
		txtProduto.setBounds(137, 161, 309, 20);
		txtProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarProdutos();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
		});
		txtProduto.setColumns(10);
		txtProduto.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(txtProduto);

		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(25, 164, 67, 14);
		lblProduto.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblProduto);

		txtLote = new JTextField();
		txtLote.setBounds(137, 205, 309, 20);
		txtLote.setColumns(10);
		txtLote.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(txtLote);

		JLabel lblLote = new JLabel("Lote");
		lblLote.setBounds(25, 208, 67, 14);
		lblLote.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblLote);

		txtFabricante = new JTextField();
		txtFabricante.setBounds(137, 319, 309, 20);
		txtFabricante.setColumns(10);
		txtFabricante.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(txtFabricante);

		JLabel lblFabricante = new JLabel("Fabricante");
		lblFabricante.setBounds(25, 318, 86, 14);
		lblFabricante.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblFabricante);

		JLabel lblDataEnt = new JLabel("Data Ent");
		lblDataEnt.setBounds(25, 358, 86, 14);
		lblDataEnt.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblDataEnt);
		MaskFormatter msf = null;
		try {
			msf = new MaskFormatter("####-##-##");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel lblDataEnt_1 = new JLabel("Data Val");
		lblDataEnt_1.setBounds(25, 390, 86, 14);
		lblDataEnt_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblDataEnt_1);
		MaskFormatter msf2 = null;
		try {
			msf2 = new MaskFormatter("####-##-##");
		} catch (Exception e) {
			e.printStackTrace();
		}

		txtCusto = new JTextField();
		txtCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtCusto.setBounds(340, 350, 106, 20);
		txtCusto.setColumns(10);
		txtCusto.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(txtCusto);

		lblCusto = new JLabel("Custo");
		lblCusto.setBounds(284, 353, 61, 14);
		lblCusto.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblCusto);

		lblLucro = new JLabel("Lucro");
		lblLucro.setBounds(283, 387, 61, 14);
		lblLucro.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(lblLucro);

		txtLucro = new JTextField();
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtLucro.setBounds(341, 381, 106, 20);
		txtLucro.setText("0");
		txtLucro.setColumns(10);
		txtLucro.setBorder(new LineBorder(Color.BLACK));
		getContentPane().add(txtLucro);

		dateVal = new JDateChooser();
		dateVal.setBounds(137, 384, 139, 20);
		getContentPane().add(dateVal);

		dateEnt = new JDateChooser();
		dateEnt.setBounds(137, 353, 139, 20);
		getContentPane().add(dateEnt);

		txtaDescricao = new JTextArea();
		txtaDescricao.setBounds(137, 250, 309, 59);
		txtaDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(txtaDescricao);

		chckFoto = new JCheckBox("Alterar Foto");
		chckFoto.setBounds(611, 280, 121, 32);
		chckFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCarregar.setEnabled(true);
				if (chckFoto.isSelected()) {
					lblFoto.setIcon(null);
					lblFoto.requestFocus();
					lblFoto.setBackground(Color.YELLOW);
					lblFoto.setIcon(new ImageIcon(Principal.class.getResource("/img/image add 256.png")));
				} else {
					lblFoto.setBackground(Color.WHITE);
				}
			}
		});
		getContentPane().add(chckFoto);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(128, 0, 255));
		panel_1.setBounds(0, 472, 811, 89);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		btnAdicionar = new JButton("");
		btnAdicionar.setBounds(36, 11, 72, 67);
		panel_1.add(btnAdicionar);
		btnAdicionar.setEnabled(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setIcon(new ImageIcon(Produtos.class.getResource("/img/adicionar.png")));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);

		btnEditar = new JButton("");
		btnEditar.setBounds(205, 11, 72, 67);
		panel_1.add(btnEditar);
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckFoto.isSelected()) {
					editarProdutosComFoto();
				} else {
					editarProdutos();
				}
			}
		});
		btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/editar.png")));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);

		btnExcluir = new JButton("");
		btnExcluir.setBounds(510, 11, 72, 67);
		panel_1.add(btnExcluir);
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirProdutos();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/excluir.png")));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);

		btnLimpar = new JButton("");
		btnLimpar.setBounds(672, 11, 72, 67);
		panel_1.add(btnLimpar);
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setIcon(new ImageIcon(Produtos.class.getResource("/img/borracha.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);

	}// FIM DO CONSTRUTOR

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar arquivo de Imagem");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);

		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {

				fis = new FileInputStream(jfc.getSelectedFile());

				tamanho = (int) jfc.getSelectedFile().length();

				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
			} catch (Exception e) {
				System.out.println(e);

			}
		}

	}

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		dateEnt.setDate(null);
		dateVal.setDate(null);
		txtProduto.setText(null);
		txtLote.setText(null);
		txtFabricante.setText(null);
		txtCusto.setText(null);
		txtLucro.setText(null);
		txtFornecedor.setText(null);
		txtIDFornecedor.setText(null);
		;
		txtID.setText(null);
		txtBarCode.setText(null);
		txtaDescricao.setText(null);
		lblFoto.setIcon(null);
		txtEstoque.setText(null);
		txtEstoqueMin.setText(null);
		txtLocalArmazenamento.setText(null);
		cboUNID.setSelectedItem(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnBuscarID.setEnabled(true);
		btnCarregar.setEnabled(false);
		chckFoto.setSelected(false);
		lblFoto.setIcon(new ImageIcon(Principal.class.getResource("/img/image add 256.png")));

	}

	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {

		if (txtBarCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Barcode do produto");
			txtBarCode.requestFocus();
		} else if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do produto");
			txtProduto.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o lote do produto");
			txtLote.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (txtaDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtaDescricao.requestFocus();
		} else if (dateVal.getDate().toString().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a data de validade do produto");
			dateVal.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o custo do produto");
			txtCusto.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque do produto");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque mínimo do produto");
			txtEstoqueMin.requestFocus();
		} else if (txtLocalArmazenamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o local de armazenamento do produto");
			txtLocalArmazenamento.requestFocus();
		} else {

			String create = "insert into produtos(barcode,produto,lote,foto,descricao,fabricante,dataval,custo,lucro,estoque,estoquemin,unidademedida,localarmazenagem,idfornecedor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(create);
				pst.setString(1, txtBarCode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtLote.getText());
				pst.setBlob(4, fis, tamanho);
				pst.setString(5, txtaDescricao.getText());
				pst.setString(6, txtFabricante.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateVal.getDate());
				pst.setString(7, dataFormatada);
				pst.setString(8, txtCusto.getText());
				pst.setString(9, txtLucro.getText());
				pst.setString(10, txtEstoque.getText());
				pst.setString(11, txtEstoqueMin.getText());
				pst.setString(12, cboUNID.getSelectedItem().toString());
				pst.setString(13, txtLocalArmazenamento.getText());
				pst.setString(14, txtIDFornecedor.getText());

				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Produto Adicionado");
				} else {
					JOptionPane.showMessageDialog(null, "Erro! Produto não adicionado.");
				}

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
	private void editarProdutos() {

		if (txtBarCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Barcode do produto");
			txtBarCode.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (txtaDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtaDescricao.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque mínimo do produto");
			txtEstoqueMin.requestFocus();
		} else if (txtLocalArmazenamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o local de armazenamento do produto");
			txtLocalArmazenamento.requestFocus();
		} else {

			String update = "update produtos set barcode=?, produto=?,lote=?,descricao=?,fabricante=?,custo=?,lucro=?,estoque=?,estoquemin=?,unidademedida=?,localarmazenagem=? where codeproduto =?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(update);
				pst.setString(1, txtBarCode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtLote.getText());
				pst.setString(4, txtaDescricao.getText());
				pst.setString(5, txtFabricante.getText());
				pst.setString(6, txtCusto.getText());
				pst.setString(7, txtLucro.getText());
				pst.setString(8, txtEstoque.getText());
				pst.setString(9, txtEstoqueMin.getText());
				pst.setString(10, cboUNID.getSelectedItem().toString());
				pst.setString(11, txtLocalArmazenamento.getText());
				pst.setString(12, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do produto editado com sucesso.");

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
	private void editarProdutosComFoto() {

		JFileChooser capturaFoto = new JFileChooser();

		if (txtBarCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Barcode do produto");
			txtBarCode.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (txtaDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtaDescricao.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque mínimo do produto");
			txtEstoqueMin.requestFocus();
		} else if (txtLocalArmazenamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o local de armazenamento do produto");
			txtLocalArmazenamento.requestFocus();
		} else {

			String update = "update produtos set barcode=?, produto=?,lote=?,foto=?,descricao=?,fabricante=?,custo=?,lucro=?,estoque=?,estoquemin=?,unidademedida=?,localarmazenagem=? where codeproduto =?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(update);
				pst.setString(1, txtBarCode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtLote.getText());
				pst.setBlob(4, fis, tamanho);
				pst.setString(5, txtaDescricao.getText());
				pst.setString(6, txtFabricante.getText());
				pst.setString(7, txtCusto.getText());
				pst.setString(8, txtLucro.getText());
				pst.setString(9, txtEstoque.getText());
				pst.setString(10, txtEstoqueMin.getText());
				pst.setString(11, cboUNID.getSelectedItem().toString());
				pst.setString(12, txtLocalArmazenamento.getText());
				pst.setString(13, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do produto editado com sucesso.");

				limparCampos(); // Fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * Método usado para excluir um contato
	 */
	private void excluirProdutos() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {

			String delete = "delete from produtos where codeproduto=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(delete);

				pst.setString(1, txtID.getText());

				pst.executeUpdate();

				limparCampos();

				JOptionPane.showMessageDialog(null, "Produto excluído");

				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Produto não excluido. \nEste produto ainda tem um serviço pendente");
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
	 * Método usado para listar o nome dos usuários na lista
	 */
	private void listarFornecedor() {
	
		DefaultListModel<String> modelo = new DefaultListModel<>();
	
		listaFornecedor.setModel(modelo);
		
		String readListaFornecedor = "select * from fornecedores where razaosocial like '" + txtFornecedor.getText()
				+ "%'" + "order by razaosocial";
		try {
			
			con = dao.conectar();
			
			pst = con.prepareStatement(readListaFornecedor);
		
			rs = pst.executeQuery();
		
			while (rs.next()) {
			
				scrollPane.setVisible(true);
				
				modelo.addElement(rs.getString(2));
			
				if (txtFornecedor.getText().isEmpty()) {
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
			
			String readListaFornecedor = "select * from fornecedores where razaosocial like '" + txtFornecedor.getText()
					+ "%'" + "order by razaosocial limit " + (linha) + " , 1";
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(readListaFornecedor);
			
				rs = pst.executeQuery();

				if (rs.next()) {
					
					scrollPane.setVisible(false);
					
					txtFornecedor.setText(rs.getString(2));
					txtIDFornecedor.setText(rs.getString(1));
					
					listaFornecedor.setEnabled(false);

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

	/**
	 * Método para buscar um contato pelo nome
	 */
	private void buscarID() {

		
		String numOS = JOptionPane.showInputDialog("ID do Produto");

	
		String read = "select * from produtos inner join fornecedores on produtos.idfornecedor = fornecedores.codefornecedor where codeproduto = ?";
		
		try {
			
			con = dao.conectar();
			
			pst = con.prepareStatement(read);
	
			pst.setString(1, numOS);
		
			rs = pst.executeQuery();
					if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtBarCode.setText(rs.getString(2));
				txtProduto.setText(rs.getString(3));
				txtLote.setText(rs.getString(4));
				txtaDescricao.setText(rs.getNString(6));
				txtFabricante.setText(rs.getString(7));
				dateEnt.setDate(rs.getDate(8));
				dateVal.setDate(rs.getDate(9));
				txtCusto.setText(rs.getString(10));
				txtLucro.setText(rs.getString(11));
				txtEstoque.setText(rs.getString(12));
				txtEstoqueMin.setText(rs.getString(13));
				cboUNID.setSelectedItem(rs.getString(14));
				txtLocalArmazenamento.setText(rs.getString(15));
				txtIDFornecedor.setText(rs.getString(16));
				
				txtFornecedor.setText(rs.getString(18));
				Blob blob = (Blob) rs.getBlob(5);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
				
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				listaFornecedor.setEnabled(true);
				btnAdicionar.setEnabled(false);

			} else {
				
				JOptionPane.showMessageDialog(null, "Código do produto não cadastrado");
				
				btnAdicionar.setEnabled(true);
				btnBuscarID.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnEditar.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarBarCode() {
	
		String readBarCode = "select * from produtos inner join fornecedores on produtos.idfornecedor = fornecedores.codefornecedor where barcode = ?";
	
		try {
			
			con = dao.conectar();
			
			pst = con.prepareStatement(readBarCode);
			
			pst.setString(1, txtBarCode.getText());
		
			rs = pst.executeQuery();
		
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtProduto.setText(rs.getString(3));
				txtLote.setText(rs.getString(4));
				txtaDescricao.setText(rs.getNString(6));
				txtFabricante.setText(rs.getString(7));
				dateEnt.setDate(rs.getDate(8));
				dateVal.setDate(rs.getDate(9));
				txtCusto.setText(rs.getString(10));
				txtLucro.setText(rs.getString(11));
				txtEstoque.setText(rs.getString(12));
				txtEstoqueMin.setText(rs.getString(13));
				cboUNID.setSelectedItem(rs.getString(14));
				txtLocalArmazenamento.setText(rs.getString(15));
				txtIDFornecedor.setText(rs.getString(16));
				
				txtFornecedor.setText(rs.getString(18));
				Blob blob = (Blob) rs.getBlob(5);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
				
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				listaFornecedor.setEnabled(true);
				btnAdicionar.setEnabled(false);

			} else {
				
				JOptionPane.showMessageDialog(null, "BarCode do produto não encontrado");
				
				btnAdicionar.setEnabled(true);
				btnBuscarID.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnEditar.setEnabled(false);
			}
			
			con.close();
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarProdutos() {
		
		String readProdutos = "select * from produtos inner join fornecedores on produtos.idfornecedor = fornecedores.codefornecedor where produto = ?";
		
		try {
			
			con = dao.conectar();
			
			pst = con.prepareStatement(readProdutos);
			
			pst.setString(1, txtProduto.getText());
			
			rs = pst.executeQuery();
			
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtBarCode.setText(rs.getString(2));
				txtLote.setText(rs.getString(4));
				txtaDescricao.setText(rs.getNString(6));
				txtFabricante.setText(rs.getString(7));
				dateEnt.setDate(rs.getDate(8));
				dateVal.setDate(rs.getDate(9));
				txtCusto.setText(rs.getString(10));
				txtLucro.setText(rs.getString(11));
				txtEstoque.setText(rs.getString(12));
				txtEstoqueMin.setText(rs.getString(13));
				cboUNID.setSelectedItem(rs.getString(14));
				txtLocalArmazenamento.setText(rs.getString(15));
				txtIDFornecedor.setText(rs.getString(16));
				
				txtFornecedor.setText(rs.getString(19));
				Blob blob = (Blob) rs.getBlob(5);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
				
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				listaFornecedor.setEnabled(true);
				btnAdicionar.setEnabled(false);

			} else {
				
				JOptionPane.showMessageDialog(null, "BarCode do produto não encontrado");
				
				btnAdicionar.setEnabled(true);
				btnBuscarID.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnEditar.setEnabled(false);
			}
			
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarNomeFornecedor() {
		
		String readForn = "select fantasia from fornecedores where codefornecedor= ?";
		
		try {
			
			con = dao.conectar();
			
		
			pst = con.prepareStatement(readForn);
		
			pst.setString(1, txtFornecedor.getText());
			
			rs = pst.executeQuery();
		
			if (rs.next()) {
				txtFornecedor.setText(rs.getString(1));
				try {
				} catch (Exception e) {
					System.out.println(e);
				}

			} else {
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

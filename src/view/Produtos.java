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

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import model.DAO;

public class Produtos extends JDialog {

	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	// Instancioar objetos para fluxo de bytes
	private FileInputStream fis;

	// Variável global para armazenar o tamanho da imagem(bytes)
	private int tamanho;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtBarCode;
	private JTextField txtEstoque;
	private JTextField txtEstoqueMin;
	private JTextField txtValor;
	private JTextField txtLocalArmazenamento;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JLabel lblFoto;
	private JButton btnCarregar;
	private JLabel lblNewLabel_1;
	private JComboBox cboUNID;
	private JLabel txt;
	private JScrollPane scrollPane;
	private JList listaFornecedor;
	private JTextField txtFornecedor;
	private JTextField txtIDFornecedor;
	private JButton btnBuscar;
	private JTextField txtDescricao;
	private JTextField txtTest;

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
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/note.png")));
		setTitle("Produtos\r\n");
		setBounds(-8, -16, 664, 591);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fornecedores", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(317, 13, 206, 82);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(11, 38, 185, 54);
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
		txtID.setEnabled(false);
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBorder(new LineBorder(Color.BLACK));
		txtID.setBounds(158, 57, 86, 20);
		getContentPane().add(txtID);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(46, 59, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblBarcode = new JLabel("BarCode");
		lblBarcode.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBarcode.setBounds(46, 120, 67, 14);
		getContentPane().add(lblBarcode);

		txtBarCode = new JTextField();
		txtBarCode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		txtBarCode.setColumns(10);
		txtBarCode.setBorder(new LineBorder(Color.BLACK));
		txtBarCode.setBounds(158, 118, 309, 20);
		getContentPane().add(txtBarCode);
		// Uso do Validador para limitar o número de caracteres
		txtBarCode.setDocument(new Validador(20));

		JLabel lblDescrio = new JLabel("Imagem");
		lblDescrio.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDescrio.setBounds(46, 159, 86, 20);
		getContentPane().add(lblDescrio);

		JLabel lblEstoque = new JLabel("Estoque");
		lblEstoque.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEstoque.setBounds(46, 293, 86, 14);
		getContentPane().add(lblEstoque);

		txtEstoque = new JTextField();
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
		txtEstoque.setBounds(158, 291, 309, 20);
		getContentPane().add(txtEstoque);

		JLabel lblEstoqueMin = new JLabel("Estoque Min.");
		lblEstoqueMin.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEstoqueMin.setBounds(46, 335, 86, 14);
		getContentPane().add(lblEstoqueMin);

		txtEstoqueMin = new JTextField();
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
		txtEstoqueMin.setBounds(158, 333, 309, 20);
		getContentPane().add(txtEstoqueMin);

		btnCarregar = new JButton("Pesquisar");
		btnCarregar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		btnCarregar.setBounds(158, 158, 139, 23);
		getContentPane().add(btnCarregar);

		JLabel lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblValor.setBounds(46, 377, 67, 14);
		getContentPane().add(lblValor);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtValor.setColumns(10);
		txtValor.setBorder(new LineBorder(Color.BLACK));
		txtValor.setBounds(158, 375, 173, 20);
		getContentPane().add(txtValor);
		// Uso do Validador para limitar o número de caracteres
		txtValor.setDocument(new Validador(15));

		JLabel lblUnid = new JLabel("UNID.");
		lblUnid.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUnid.setBounds(361, 377, 61, 14);
		getContentPane().add(lblUnid);

		cboUNID = new JComboBox();
		cboUNID.setModel(new DefaultComboBoxModel(new String[] { "UN", "CX", "PC", "KG", "M" }));
		cboUNID.setFont(new Font("Tahoma", Font.BOLD, 13));
		cboUNID.setBounds(421, 373, 46, 22);
		getContentPane().add(cboUNID);

		JLabel lblLocalDeArmazem = new JLabel("Local ");
		lblLocalDeArmazem.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLocalDeArmazem.setBounds(46, 406, 52, 14);
		getContentPane().add(lblLocalDeArmazem);

		txtLocalArmazenamento = new JTextField();
		txtLocalArmazenamento.setColumns(10);
		txtLocalArmazenamento.setBorder(new LineBorder(Color.BLACK));
		txtLocalArmazenamento.setBounds(159, 417, 308, 20);
		getContentPane().add(txtLocalArmazenamento);
		// Uso do Validador para limitar o número de caracteres
		txtLocalArmazenamento.setDocument(new Validador(50));

		JLabel lblArmazenamento = new JLabel("Armazenamento");
		lblArmazenamento.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblArmazenamento.setBounds(46, 420, 121, 21);
		getContentPane().add(lblArmazenamento);

		btnAdicionar = new JButton("");
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
		btnAdicionar.setBounds(64, 457, 60, 60);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarProdutos();
			}
		});
		btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/editar.png")));
		btnEditar.setEnabled(false);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorder(null);
		btnEditar.setBounds(176, 457, 60, 60);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirProdutos();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/excluir.png")));
		btnExcluir.setEnabled(false);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(293, 457, 60, 60);
		getContentPane().add(btnExcluir);

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setIcon(new ImageIcon(Produtos.class.getResource("/img/Borracha.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorder(null);
		btnLimpar.setBounds(407, 457, 60, 60);
		getContentPane().add(btnLimpar);

		lblFoto = new JLabel("");
		lblFoto.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/file camera.png")));
		lblFoto.setBounds(482, 120, 128, 128);
		getContentPane().add(lblFoto);

		lblNewLabel_1 = new JLabel("=================>");
		lblNewLabel_1.setBounds(321, 163, 188, 14);
		getContentPane().add(lblNewLabel_1);

		txt = new JLabel("Descrição");
		txt.setFont(new Font("Tahoma", Font.BOLD, 13));
		txt.setBounds(46, 206, 86, 20);
		getContentPane().add(txt);

		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBorder(null);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setIcon(new ImageIcon(Produtos.class.getResource("/img/Pesquisar.png")));
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBounds(254, 49, 32, 32);
		getContentPane().add(btnBuscar);
		
		txtDescricao = new JTextField();
		txtDescricao.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtDescricao.setBounds(158, 206, 309, 59);
		getContentPane().add(txtDescricao);
		txtDescricao.setColumns(10);
		
		txtTest = new JTextField();
		txtTest.setBounds(492, 291, 86, 20);
		getContentPane().add(txtTest);
		txtTest.setColumns(10);

	}// FIM DO CONSTRUTOR

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar arquivo");
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
		txtFornecedor.setText(null);
		txtID.setText(null);
		txtBarCode.setText(null);
		txtDescricao.setText(null);
		lblFoto.setIcon(null);
		txtEstoque.setText(null);
		txtEstoqueMin.setText(null);
		txtValor.setText(null);
		txtLocalArmazenamento.setText(null);
		cboUNID.setSelectedItem(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnBuscar.setEnabled(true);
		lblFoto.setIcon(new ImageIcon(Principal.class.getResource("/img/file camera.png")));

	}// fim do método limparCampos()

	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatórios
		if (txtBarCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Barcode do produto");
			txtBarCode.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtDescricao.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque do produto");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque mínimo do produto");
			txtEstoqueMin.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor do produto");
			txtValor.requestFocus();
		} else if (txtLocalArmazenamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o local de armazenamento do produto");
			txtLocalArmazenamento.requestFocus();
		} else {

			// Lógica Principal
			// CRUD Create
			String create = "insert into produtos(barcode,foto,descricao,estoque,estoquemin,valor,unidademedida,localarmazenagem,idfornecedor) values (?,?,?,?,?,?,?,?,?)";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução da query (instrução sql - CRUD Read)
				pst = con.prepareStatement(create);
				pst.setString(1, txtBarCode.getText());
				pst.setBlob(2, fis, tamanho);
				pst.setString(3, txtDescricao.getText());
				pst.setString(4, txtEstoque.getText());
				pst.setString(5, txtEstoqueMin.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, cboUNID.getSelectedItem().toString());
				pst.setString(8, txtLocalArmazenamento.getText());
				pst.setString(9, txtIDFornecedor.getText());
				// executa a query(instrução sql (CRUD - Create))
				//pst.executeUpdate();
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Produto Adicionado");
				} else {
					JOptionPane.showMessageDialog(null, "Erro! Produto não adicionado.");
				}

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
	private void editarProdutos() {
		// System.out.println("teste do botão editar");
		// Validação dos campos obrigatórios
		if (txtBarCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Barcode do produto");
			txtBarCode.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtDescricao.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque mínimo do produto");
			txtEstoqueMin.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor do produto");
			txtValor.requestFocus();
		} else if (txtLocalArmazenamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o local de armazenamento do produto");
			txtLocalArmazenamento.requestFocus();
		} else {
			// lógica principal
			// CRUD - Update
			String update = "update produtos set barcode=?,foto=?,descricao=?,estoque=?,estoquemin=?,valor=?,unidademedida=?,localarmazenagem=?,idfornecedor=? where codeproduto =?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtBarCode.getText());
				pst.setBlob(2, fis, tamanho);
				pst.setString(3, txtDescricao.getText());	
				pst.setString(4, txtEstoque.getText());
				pst.setString(5, txtEstoqueMin.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, cboUNID.getSelectedItem().toString());
				pst.setString(8, txtLocalArmazenamento.getText());
				pst.setString(9, txtID.getText());
				pst.setString(10, txtIDFornecedor.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do produto editado com sucesso.");
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
	private void excluirProdutos() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confirma captura a opção escolhida
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete
			String delete = "delete from produtos where codeproduto=?";
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
				JOptionPane.showMessageDialog(null, "Produto excluído");
				// fechar a conexão
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Produto não excluido. \nEste produto ainda tem um serviço pendente");
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

	} // Fim do validador

	/**
	 * Método usado para listar o nome dos usuários na lista
	 */
	private void listarFornecedor() {
		// System.out.println("teste");
		// A linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// objeto irá temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// Setar o modelo (vetor na lista)
		listaFornecedor.setModel(modelo);
		// Query (instrução sql)
		String readListaFornecedor = "select * from fornecedores where razaosocial like '" + txtFornecedor.getText()
				+ "%'" + "order by razaosocial";
		try {
			// Abrir a conexão
			con = dao.conectar();
			// Preparar a query (instrução sql)
			pst = con.prepareStatement(readListaFornecedor);
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
				if (txtFornecedor.getText().isEmpty()) {
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
	private void buscarFornecedorLista() {
		// System.out.println("Teste");
		// Variável que captura o índice da linha da lista
		int linha = listaFornecedor.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução SQL)
			// limit (0,1) -> seleciona o índice 0 do vetor e 1 usuário da lista
			String readListaFornecedor = "select * from fornecedores where razaosocial like '" + txtFornecedor.getText()
					+ "%'" + "order by razaosocial limit " + (linha) + " , 1";
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query para execução
				pst = con.prepareStatement(readListaFornecedor);
				// executar e obter o resultado
				rs = pst.executeQuery();

				if (rs.next()) {
					// Esconder a lista
					scrollPane.setVisible(false);
					// Setar os campos
					txtFornecedor.setText(rs.getString(2));
					txtIDFornecedor.setText(rs.getString(1));
					// Validação (liberação dos botões)
					listaFornecedor.setEnabled(false);

				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");
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

	/**
	 * Método para buscar um contato pelo nome
	 */
	private void buscar() {

		// Captura do número da OS (sem usar a caixa de texto)
		String numOS = JOptionPane.showInputDialog("ID do Produto");

		// dica - testar o evento primeiro
		// System.out.println("teste do botão buscar");
		// Criar uma variável com a query (instrução do banco)
		String read = "select * from produtos where codeproduto = ?";
		// tratamento de exceções
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execução da query (instrução sql - CRUD Read)
			// O parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(read);
			// Substituir a ?(Parâmetro) pelo número da OS
			pst.setString(1, numOS);
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtBarCode.setText(rs.getString(2));
				txtDescricao.setText(rs.getString(3));
				txtTest.setText(rs.getString(3));
				txtEstoque.setText(rs.getString(5));
				txtEstoqueMin.setText(rs.getString(6));
				txtValor.setText(rs.getString(7));
				cboUNID.setSelectedItem(rs.getString(8));
				txtLocalArmazenamento.setText(rs.getString(9));
				txtFornecedor.setText(rs.getString(10));
				Blob blob = (Blob) rs.getBlob(4);
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
				// Validação (liberação dos botões)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				listaFornecedor.setEnabled(true);
				btnAdicionar.setEnabled(false);

			} else {
				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "ID do produto inexistente");
				// Validação (liberação do botão adicionar)
				btnAdicionar.setEnabled(true);
				btnBuscar.setEnabled(true);
				btnExcluir.setEnabled(false);	
				btnEditar.setEnabled(false);
			}
			// fechar conexão (IMPORTANTE)
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// FIM DO METODO BUSCAR
}

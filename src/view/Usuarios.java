package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import model.DAO;

public class Usuarios extends JDialog {
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;

	// Instanciar obejtos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnPesquisar;
	private JPasswordField txtSenha2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
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
	public Usuarios() {
		setTitle("Usuários");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Usuarios.class.getResource("/img/52381_clown fish_fish_nemo_icon.png")));
		getContentPane().setFont(new Font("Century Gothic", Font.BOLD, 14));
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);// Centralizar a janela

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 30, 55, 18);
		getContentPane().add(lblNewLabel);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(10, 53, 86, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblNome.setBounds(220, 32, 55, 14);
		getContentPane().add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(220, 53, 135, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		// Uso do Validador para limitar o número de caracteres
		txtNome.setDocument(new Validador(40));

		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, 84, 46, 18);
		getContentPane().add(lblNewLabel_2);

		txtLogin = new JTextField();
		txtLogin.setBounds(10, 112, 86, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		// Uso do Validador para limitar o número de caracteres
		txtLogin.setDocument(new Validador(15));

		JLabel lblNewLabel_1 = new JLabel("Senha:");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblNewLabel_1.setBounds(220, 84, 76, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(
				new ImageIcon(Usuarios.class.getResource("/img/2203549_admin_avatar_human_login_user_icon.png")));
		lblNewLabel_3.setBounds(367, 25, 48, 48);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setIcon(
				new ImageIcon(Usuarios.class.getResource("/img/511942_lock_login_protection_secure_icon.png")));
		lblNewLabel_4.setBounds(367, 84, 48, 48);
		getContentPane().add(lblNewLabel_4);

		btnPesquisar = new JButton("");
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();

			}
		});
		btnPesquisar.setBorder(null);
		btnPesquisar.setIcon(new ImageIcon(
				Usuarios.class.getResource("/img/3688454_find_lens_search_magnifier_magnifying_icon.png")));
		btnPesquisar.setBounds(106, 68, 45, 45);
		getContentPane().add(btnPesquisar);

		JPanel panel = new JPanel();
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panel.setForeground(new Color(208, 26, 81));
		panel.setBackground(new Color(219, 26, 84));
		panel.setBounds(0, 212, 434, 49);
		getContentPane().add(panel);

		JButton btnLimpar = new JButton("");
		btnLimpar.setBounds(379, 0, 48, 48);
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		panel.setLayout(null);
		btnLimpar.setBackground(new Color(205, 12, 94));
		panel.add(btnLimpar);
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/9055449_bxs_eraser_icon.png")));

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarUsuario();

			}
		});
		btnEditar.setBorder(null);
		btnEditar.setEnabled(false);
		btnEditar.setBounds(75, 0, 64, 48);
		panel.add(btnEditar);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(
				Usuarios.class.getResource("/img/1976055_edit_edit document_edit file_edited_editing_icon.png")));

		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.setBounds(10, 0, 55, 48);
		panel.add(btnAdicionar);
		btnAdicionar.setBorder(null);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar
				.setIcon(new ImageIcon(Usuarios.class.getResource("/img/4781840_+_add_circle_create_expand_icon.png")));

		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBorder(null);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setIcon(
				new ImageIcon(Usuarios.class.getResource("/img/4781839_cancel_circle_close_delete_discard_icon.png")));
		btnExcluir.setBounds(321, 0, 48, 48);
		panel.add(btnExcluir);

		// substituir o click pela tecla <ENTER> em um botão
		getRootPane().setDefaultButton(btnPesquisar);
		
		txtSenha2 = new JPasswordField();
		txtSenha2.setBounds(220, 112, 125, 20);
		getContentPane().add(txtSenha2);

	}// FIM DO CONSTRUTOR

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha2.setText(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisar.setEnabled(true);
	}

	// FIM DO CODIGO
	/**
	 * metodo para buscar um contato pelo nome
	 */

	private void buscar() {
		// dica - testar o evento primeiro
		//System.out.println("teste de botão buscar");
		// criar uma variável com o quary (instrução do banco)
		String read = "select * from usuarios where login = ?";
		// tratamento de exeções
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execução da quary(instrução sql - CRUD Read
			// o parâmetro 1 substitui a ? pelo conteudo da caixa de texto
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			// executar a quary e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				// preencher as caixas de texto com as informações
				txtID.setText(rs.getString(1)); // 1º campo da tabela ID
				txtNome.setText(rs.getString(2)); // 2º campo da tabela Nome
				txtLogin.setText(rs.getString(3)); // 3º campo da tabela (Login)
				txtSenha2.setText(rs.getString(4)); // 4º campo da tabela (Senha)
				// validação (liberação dos botões alterar e excluir)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnPesquisar.setEnabled(false);
			} else {
				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "Usuário Inexistente");
				// validação (liberado de borão adcionar)
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(false);
			}
			// fechar conexão (MUITO IMPORTANTE)
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
// Criar uma variável para capturar a senha
			String capturaSenha = new String(txtSenha2.getPassword());
		System.out.println("teste");
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha2.requestFocus();
		} else {

			// Lógica Principal
			// CRUD create
			String create = "insert into usuarios (nome,login,senha) values (?,?,?)";
			// tratamento de exeções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execução do quary (instrução sal - CRUD Read
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturaSenha);
				// excuta a query(instrução sal (CRUD - Create))
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "usuário adicionado");
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// fim do metodo adicionar

	/**
	 * Método usado para excluir um contato
	 */
	private void excluirUsuario() {

		// Criar uma variável para capturar a senha
			String capturaSenha = new String(txtSenha2.getPassword());
		//System.out.println("Teste do botão excluir");
		//validação de exclusão - a variável confirma captura a opção escolhida
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Usuário?","Atenção!", JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			//CRUD - Delete
			String delete = "delete from usuarios where id=?";
			//tratamento de exceções
			try {
				//abrir conexão
				con = dao.conectar();
				//preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				//substituir a ? pelo id do contato
				pst.setString(1, txtID.getText());
				//executar a query
				pst.executeUpdate();
				//limpar campos
				limparCampos();
				//exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, "Usuário excluído");
				//fechar a conexão
				con.close();
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	} // fim do método excluir contato

//PlainDocument -> recursos para formatação
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
	 * Método para editar um contato (ATENÇÃO!!! Usar o ID)
	 */
	private void editarUsuario() {
		// Criar uma variável para capturar a senha
		String capturaSenha = new String(txtSenha2.getPassword());
		//System.out.println("teste do botão editar");
		//Validação dos campos obrigatórios
		if(txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuário");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Digite a senha do usuário");
			txtSenha2.requestFocus();
		} else {
			//lógica principal
			//CRUD - Update
			String update = "update usuarios set nome=?,login=?,senha=md5(?) where id =?";
			//tratamento de exceções
			try {
				//abrir a conexão
				con = dao.conectar();
				//preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, txtID.getText());
				//executar a query
				pst.executeUpdate();
				//confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do usuário editado com sucesso.");
				//Limpar os campos 
				limparCampos();
				//Fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
		
		
	}// fim do método editar contato


}// fim do código

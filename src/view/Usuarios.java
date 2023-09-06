package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import model.DAO;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class Usuarios extends JDialog {
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
	private JTextField txtLogin;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnPesquisar;
	private JPasswordField txtSenha;
	private JList listaUsers;
	private JScrollPane scrollPane;
	private JComboBox cboPerfil;
	private JCheckBox chckSenha;
	private JPanel panel;

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
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
				
			}
		});
		setTitle("Entrada");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/note.png")));
		setModal(true);
		setBounds(100, 100, 450, 361);
		getContentPane().setLayout(null);
	    setLocationRelativeTo(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(66, 136, 220, 60);
		getContentPane().add(scrollPane);
		
				listaUsers = new JList();
				listaUsers.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						buscarUsuarioLista();
					}
				});
				scrollPane.setViewportView(listaUsers);
				listaUsers.setBorder(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 34, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 119, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(10, 78, 46, 16);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Senha");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setBounds(10, 159, 46, 14);
		getContentPane().add(lblNewLabel_3);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(66, 32, 86, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		txtNome = new JTextField();
		txtNome.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();

			}
		});
		txtNome.setBounds(66, 117, 220, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));

		txtLogin = new JTextField();
		txtLogin.setBounds(66, 77, 220, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(15));

		btnPesquisar = new JButton("");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();

			}
		});
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setBorder(null);
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Pesquisar.png")));
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setBounds(296, 60, 48, 50);
		getContentPane().add(btnPesquisar);

		JButton btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBorder(null);
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Borracha.png")));
		btnLimpar.setBounds(350, 243, 74, 68);
		getContentPane().add(btnLimpar);

		getRootPane().setDefaultButton(btnPesquisar);

		btnAdicionar = new JButton("");
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setEnabled(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBorder(null);
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/adicionar.png")));
		btnAdicionar.setBounds(10, 243, 74, 68);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setToolTipText("Editar ");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					editarUsuario();
				} else {
					editarUsuarioExcetoSenha();
				}
			}
		});
		btnEditar.setBorder(null);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/editar.png")));
		btnEditar.setBounds(124, 243, 74, 68);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setBorder(null);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/excluir.png")));
		btnExcluir.setBounds(238, 243, 74, 68);
		getContentPane().add(btnExcluir);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(66, 157, 220, 20);
		getContentPane().add(txtSenha);
		
		JLabel lblNewLabel_4 = new JLabel("Perfil");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(293, 160, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"", "admin", "user", "bugg-head"}));
		cboPerfil.setBounds(337, 155, 92, 22);
		getContentPane().add(cboPerfil);
		
		chckSenha = new JCheckBox("Alterar Senha");
		chckSenha.setEnabled(false);
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					txtSenha.setText(null);
					txtSenha.requestFocus();
					txtSenha.setBackground(Color.YELLOW);
				} else {
					txtSenha.setBackground(Color.WHITE);
				}
			}
		});
		chckSenha.setBounds(10, 213, 152, 23);
		getContentPane().add(chckSenha);
		
		panel = new JPanel();
		panel.setBackground(new Color(128, 0, 255));
		panel.setBounds(0, 238, 434, 84);
		getContentPane().add(panel);

	}

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		txtSenha.setBackground(Color.WHITE);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisar.setEnabled(true);
		scrollPane.setVisible(false);
		cboPerfil.setSelectedItem("");
		chckSenha.setSelected(false);
		chckSenha.setEnabled(false);
	}

	/**
	 * Método para buscar um contato pelo nome
	 */
	private void buscar() {
		
		String read = "select * from usuarios where login = ?";
		
		try {
			con = dao.conectar();
			
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			
			rs = pst.executeQuery();
			
			if (rs.next()) {
				
				txtID.setText(rs.getString(1)); // 1º Campo da tabela ID
				txtNome.setText(rs.getString(2)); // 3º Campo da tabela (Fone)
				txtLogin.setText(rs.getString(3)); // 3º Campo da tabela (Fone)
				txtSenha.setText(rs.getString(4)); // 4° Campo da tabela (Email)
				cboPerfil.setSelectedItem(rs.getString(5));
			
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnPesquisar.setEnabled(false);
				chckSenha.setEnabled(true);

			} else {
				
				JOptionPane.showMessageDialog(null, "Usuário inexistente");
			
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(false);
			}
		
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
		
		String capturaSenha = new String(txtSenha.getPassword());
	
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login do usuário");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha do usuário");
			txtSenha.requestFocus();
		} else {

		
			String create = "insert into usuarios(nome,login,senha,perfil) values (?,?,md5(?),?)";
			
			try {
				
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Usuário Adicionado");
				limparCampos();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste login já está sendo utilizado.");
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
		}
			

		}
		
	}

	/**
	 * Método para editar um contato (ATENÇÃO!!! Usar o ID)
	 */
	private void editarUsuario() {
		
		String capturaSenha = new String(txtSenha.getPassword());
		
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuário");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Digite a senha do usuário");
			txtSenha.requestFocus();
		} else {
			
			String update = "update usuarios set nome=?,login=?,senha=md5(?),perfil=? where id =?";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtID.getText());
				
				pst.executeUpdate();
			
				JOptionPane.showMessageDialog(null, "Dados do usuário editado com sucesso.");
			
				limparCampos();
				
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	private void editarUsuarioExcetoSenha() {
		
		String capturaSenha = new String(txtSenha.getPassword());
		
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuário");
			txtLogin.requestFocus();
		} else {
		
			String update2 = "update usuarios set nome=?,login=?,perfil=? where id =?";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(update2);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtID.getText());
				
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Dados do usuário editado com sucesso.");
				
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
		
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Usuário?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			
			String delete = "delete from usuarios where id=?";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(delete);
				
				pst.setString(1, txtID.getText());
				
				pst.executeUpdate();
				
				limparCampos();
				
				JOptionPane.showMessageDialog(null, "Usuário excluído");
				
				con.close();

			} catch (Exception e) {
				System.out.println(e);
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
	private void listarUsuarios() {

		DefaultListModel<String> modelo = new DefaultListModel<>();
		
		listaUsers.setModel(modelo);
		
		String readLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
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
	private void buscarUsuarioLista() {
		
		int linha = listaUsers.getSelectedIndex();
		if (linha >= 0) {
			
			String readListaUsuario = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(readListaUsuario);
				
				rs = pst.executeQuery();
				
				if(rs.next()) {
				
					scrollPane.setVisible(false);
				
					txtID.setText(rs.getString(1)); 
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3));
					txtSenha.setText(rs.getString(4)); 
					cboPerfil.setSelectedItem(rs.getString(5));
				} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente");
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

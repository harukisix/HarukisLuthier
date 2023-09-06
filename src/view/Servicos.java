package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import view.Clientes.Validador;

import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;

public class Servicos extends JDialog {
		DAO dao = new DAO();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtEquipamento;
	private JTextField txtValor;
	private JTextField txtIDCli;
	private JButton btnBuscar;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JTextField txtCliente;
	private JScrollPane scrollPane;
	private JList listaClient;
	private JButton btnOS;
	private JPanel panel_1;
	private JLabel lblNewLabel_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
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
	public Servicos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/note.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
	    setLocationRelativeTo(null);
		JLabel lblNewLabel = new JLabel("OS");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 36, 30, 14);
		getContentPane().add(lblNewLabel);
		
		txtOS = new JTextField();
		txtOS.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtOS.setEditable(false);
		txtOS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
			}
			}});
		txtOS.setBounds(102, 33, 129, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_1.setBounds(10, 82, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtData = new JTextField();
		txtData.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtData.setEditable(false);
		txtData.setBounds(102, 79, 129, 20);
		getContentPane().add(txtData);
		txtData.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Serviço");
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_2.setBounds(10, 282, 90, 32);
		getContentPane().add(lblNewLabel_2);
		
		txtEquipamento = new JTextField();
		txtEquipamento.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtEquipamento.setBounds(102, 291, 240, 20);
		getContentPane().add(txtEquipamento);
		txtEquipamento.setColumns(10);
				txtEquipamento.setDocument(new Validador(200));
		
		JLabel lblNewLabel_3 = new JLabel((String) null);
		lblNewLabel_3.setBounds(10, 136, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Valor");
		lblNewLabel_4.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_4.setBounds(10, 349, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		txtValor = new JTextField();
		txtValor.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
			}}
		});
		txtValor.setBounds(102, 349, 240, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);
				txtValor.setDocument(new Validador(15));
		
		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBorder(null);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/Pesquisar.png")));
		btnBuscar.setBounds(267, 18, 32, 32);
		getContentPane().add(btnBuscar);

		getRootPane().setDefaultButton(btnBuscar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(401, 19, 194, 77);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(10, 33, 174, 61);
		panel.add(scrollPane);
		
		listaClient = new JList();
		listaClient.setBorder(null);
		listaClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
				listaClient.setEnabled(true);
			}
		});
		scrollPane.setViewportView(listaClient);
		scrollPane.setVisible(false);
		
		txtIDCli = new JTextField();
		txtIDCli.setEditable(false);
		txtIDCli.setBounds(88, 43, 69, 20);
		panel.add(txtIDCli);
		txtIDCli.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
			}
			}});
		txtIDCli.setColumns(10);
		
		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		txtCliente.setColumns(10);
		txtCliente.setBounds(10, 15, 174, 20);
		panel.add(txtCliente);
		

				
		JLabel lblNewLabel_5 = new JLabel("ID do Cliente");
		lblNewLabel_5.setBounds(10, 46, 79, 14);
		panel.add(lblNewLabel_5);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(128, 0, 255));
		panel_1.setBounds(0, 476, 784, 85);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		btnOS = new JButton("");
		btnOS.setBounds(690, 11, 64, 64);
		panel_1.add(btnOS);
		btnOS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOS.setBorder(null);
		btnOS.setContentAreaFilled(false);
		btnOS.setIcon(new ImageIcon(Servicos.class.getResource("/img/pdf.png")));
		
		btnLimpar = new JButton("");
		btnLimpar.setBounds(534, 11, 64, 64);
		panel_1.add(btnLimpar);
		btnLimpar.setBorder(null);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/borracha.png")));
		
		btnExcluir = new JButton("");
		btnExcluir.setBounds(361, 11, 64, 64);
		panel_1.add(btnExcluir);
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirServico();
			}
		});
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/excluir.png")));
		
		btnAdicionar = new JButton("");
		btnAdicionar.setBounds(45, 11, 64, 64);
		panel_1.add(btnAdicionar);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/adicionar.png")));
		
		btnEditar = new JButton("");
		btnEditar.setBounds(195, 11, 64, 64);
		panel_1.add(btnEditar);
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarServico();
			}
		});
		btnEditar.setBorder(null);
		btnEditar.setContentAreaFilled(false);
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/editar.png")));
		btnOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(128, 0, 255));
		panel_2.setBounds(678, 0, 123, 561);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(Servicos.class.getResource("/img/teste 3.png")));
		lblNewLabel_6.setBounds(376, 208, 302, 304);
		getContentPane().add(lblNewLabel_6);
		
	}

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtIDCli.setText(null);
		txtCliente.setText(null);
		txtOS.setText(null);
		txtData.setText(null);
		txtEquipamento.setText(null);
		txtValor.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnBuscar.setEnabled(true);

	}
	
	/**
	 * Método para buscar um contato pelo nome
	 */
	private void buscar() {
		
		
		String numOS = JOptionPane.showInputDialog("Número da OS");
		
	
		String read = "select * from servicos where os = ?";
	
		try {
		
			con = dao.conectar();
		
			pst = con.prepareStatement(read);
		
			pst.setString(1, numOS);
		
			rs = pst.executeQuery();
			
			if (rs.next()) {
				
				txtOS.setText(rs.getString(1)); // 1º Campo da tabela ID
				txtData.setText(rs.getString(2)); // 3º Campo da tabela (Fone)
				txtEquipamento.setText(rs.getString(3)); // 3º Campo da tabela (Fone)
				txtValor.setText(rs.getString(4));
				txtIDCli.setText(rs.getString(5));
			
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnBuscar.setEnabled(false);
				btnAdicionar.setEnabled(false);

			} else {
				
				JOptionPane.showMessageDialog(null, "OS inexistente");
			
				btnAdicionar.setEnabled(true);
				btnBuscar.setEnabled(false);
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
		
		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Serviço do Cliente");
			txtEquipamento.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Valor do Serviço");
			txtValor.requestFocus();
		} else {
			
			String create = "insert into servicos(servico,valor,idcli) values (?,?,?)";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(create);
				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtValor.getText());
				pst.setString(3, txtIDCli.getText());
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Serviço Adicionado");
				
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
	private void editarServico() {
		
		 if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Equipamento do Cliente");
			txtEquipamento.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Valor do Serviço");
			txtValor.requestFocus();
		} else {
			
			String update = "update servicos set os=?,dataOS=?,servico=?,valor=? where idcli =?";
		
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(update);
				pst.setString(5, txtIDCli.getText());
				pst.setString(1, txtOS.getText());
				pst.setString(2, txtData.getText());
				pst.setString(3, txtEquipamento.getText());
				pst.setString(4, txtValor.getText());
				
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Dados do serviço editado com sucesso.");
			
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
	private void excluirServico() {
	
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste serviço?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			
			String delete = "delete from servicos where idcli=?";
			
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(delete);
				
				pst.setString(1, txtIDCli.getText());
			
				pst.executeUpdate();
				
				limparCampos();
				
				JOptionPane.showMessageDialog(null, "Serviço excluído");
				
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
	private void listarClientes() {
	
		DefaultListModel<String> modelo = new DefaultListModel<>();
	
		listaClient.setModel(modelo);
	
		String readLista = "select * from cliente where nome like '" + txtCliente.getText() + "%'" + "order by nome";
		try {
		
			con = dao.conectar();
		
			pst = con.prepareStatement(readLista);
		
			rs = pst.executeQuery();
		
			while (rs.next()) {
				
				scrollPane.setVisible(true);
				
				modelo.addElement(rs.getString(2));
			
				if (txtCliente.getText().isEmpty()) {
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
		
			String readListaCliente = "select * from cliente where nome like '" + txtCliente.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				
				con = dao.conectar();
		
				pst = con.prepareStatement(readListaCliente);
	
				rs = pst.executeQuery();
				
				if(rs.next()) {
				
					scrollPane.setVisible(false);
					
					txtCliente.setText(rs.getString(2));
					txtIDCli.setText(rs.getString(1)); 	
				
					listaClient.setEnabled(false);

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
	
	/**
	 * Impressão da OS
	 */
	private void imprimirOS() {
	
		Document document = new Document();

		try {
		
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			
			document.open();
			String readOS = " select * from servicos where os = ?";
			
			try {
				
				con = dao.conectar();
			
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
			
				rs = pst.executeQuery();
			
				if (rs.next()) {					
					//document.add(new Paragraph("OS: " + rs.getString(1)));
					Paragraph os = new Paragraph ("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);
					
					Paragraph equipamento = new Paragraph ("Equipamento: " + rs.getString(3));
					equipamento.setAlignment(Element.ALIGN_LEFT);
					document.add(equipamento);
					
					Paragraph valor = new Paragraph ("Valor: " + rs.getString(4));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);
					
					Paragraph data = new Paragraph ("Data: " + rs.getString(2));
					data.setAlignment(Element.ALIGN_LEFT);
					document.add(data);
				
					
					Image imagem = Image.getInstance(Servicos.class.getResource("/img/os.png"));
			
					imagem.scaleToFit(75,75);
				
					imagem.setAbsolutePosition(20, 625);
					document.add(imagem);					
				}
			
				con.close();
				} catch (Exception e) {
					System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		document.close();
		
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

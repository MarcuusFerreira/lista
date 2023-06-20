package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.ClienteController;
import model.entity.Cliente;
import model.exception.ErroLoginException;

public class TelaLogin extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomeUsuario;
	private JPasswordField txtSenha;
	private PainelCadastroCliente painelCadastroUsuario;
	private AbstractButton btnEntrar;
	
	private ClienteController controller;
	private AbstractButton btnCadastrar;
	private JLabel lblSenha;
	private JLabel lblUsuario;
	private JComponent lblLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
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
	public TelaLogin() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 260);
		setLocationRelativeTo(null); // Centralizar na tela
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(150dlu;default):grow"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblLogin, "1, 2, 8, 1, center, default");

		lblUsuario = new JLabel("Usuário");
		contentPane.add(lblUsuario, "2, 6, right, default");

		txtNomeUsuario = new JTextField();
		contentPane.add(txtNomeUsuario, "4, 6, 3, 1");
		txtNomeUsuario.setColumns(10);

		lblSenha = new JLabel("Senha");
		contentPane.add(lblSenha, "2, 8, right, default");

		txtSenha = new JPasswordField();
		contentPane.add(txtSenha, "4, 8, 3, 1");

		btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller = new ClienteController();
				Cliente cliente = new Cliente();
				cliente.setNomeUsuario(txtNomeUsuario.getText());
				System.out.println(cliente.getNomeUsuario());
				cliente.setSenha(txtSenha.getText());
				System.out.println(cliente.getSenha());
				try {
					controller.verificarCredenciaisController(cliente);
				} catch (ErroLoginException mensagem) {
					JOptionPane.showMessageDialog(null, mensagem);
				}
			}
		});
		contentPane.add(btnEntrar, "1, 12, 8, 1, center, default");

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelCadastroCliente painelCadastro = new PainelCadastroCliente();
				setContentPane(painelCadastro);
				setBounds(100, 100, 610, 650);
				setLocationRelativeTo(null);
				revalidate();
			}
		});
		contentPane.add(btnCadastrar, "1, 14, 8, 1, center, default");
	}
}

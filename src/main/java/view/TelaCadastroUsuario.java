package view;

import java.awt.Component;
import java.awt.EventQueue;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.ClienteController;
import model.entity.Cliente;
import model.exception.CpfInvalidoException;
import model.exception.ErroCadastroException;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastroUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField textNome;
	private MaskFormatter mascaraCpf;
	private MaskFormatter mascaraCep;
	private JTextField textUsuario;
	private JPasswordField textSenha;
	private JTextField textDataNascimento;
	private ClienteController controller;
	private JButton btnCadastrar;
	private JFormattedTextField textCpf;
	private JButton btnVoltar;
	private JButton btnLimpar;
	private JButton btnPegarData;
	private JLabel lblNomeCompleto;
	private Component lblCpf;
	private JLabel lblDataNascimento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroUsuario frame = new TelaCadastroUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws ParseException
	 */
	public TelaCadastroUsuario() throws ParseException {
		setTitle("Cadastro de Usuário");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		setLocationRelativeTo(null); // Centralizar na tela
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));

		lblNomeCompleto = new JLabel("Nome completo:");
		contentPane.add(lblNomeCompleto, "4, 4, right, default");

		textNome = new JTextField();
		contentPane.add(textNome, "6, 4, fill, default");
		textNome.setColumns(10);

		lblCpf = new JLabel("CPF:");
		contentPane.add(lblCpf, "4, 8, right, default");

		mascaraCpf = new MaskFormatter("###.###.###-##");
		mascaraCpf.setValueContainsLiteralCharacters(false);

		textCpf = new JFormattedTextField(mascaraCpf);
		contentPane.add(textCpf, "6, 8, fill, default");

		lblDataNascimento = new JLabel("Data de nascimento:");
		contentPane.add(lblDataNascimento, "4, 12, right, default");
		
		textDataNascimento = new JTextField();
		contentPane.add(textDataNascimento, "6, 12, fill, default");
		textDataNascimento.setColumns(10);
		
		btnPegarData = new JButton("...");
		btnPegarData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// codificar aqui o date picker
			}
		});
		contentPane.add(btnPegarData, "8, 12");

		mascaraCep = new MaskFormatter("##.###-##");
		mascaraCep.setValueContainsLiteralCharacters(false);

		JLabel lblUsuario = new JLabel("Usuário:");
		contentPane.add(lblUsuario, "4, 16, right, default");

		textUsuario = new JTextField();
		contentPane.add(textUsuario, "6, 16, fill, default");
		textUsuario.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		contentPane.add(lblSenha, "4, 20, right, default");

		textSenha = new JPasswordField();
		contentPane.add(textSenha, "6, 20, fill, default");
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente cliente = new Cliente();
				cliente.setNomeCliente(textNome.getText());
				cliente.setCpf(textCpf.getText());
				//cliente.setDataNascimento(LocalDate.parse(textDataNascimento.getText()));
				cliente.setDataNascimento(LocalDate.now());
				cliente.setDataCadastro(LocalDate.now());
				cliente.setTipoUsuario(1);
				cliente.setNomeUsuario(textUsuario.getText());
				cliente.setSenha(textSenha.getSelectedText());
				try {
					controller = new ClienteController();
					controller.cadastrarNovoClienteController(cliente);
				} catch (ErroCadastroException mensagem) {
					JOptionPane.showMessageDialog(null, mensagem);
				} catch (CpfInvalidoException mensagem) {
					JOptionPane.showMessageDialog(null, mensagem);
				}
			}
		});
		
		btnVoltar = new JButton("Voltar");
		contentPane.add(btnVoltar, "4, 24");
		contentPane.add(btnCadastrar, "6, 24");
		
		btnLimpar = new JButton("Limpar");
		contentPane.add(btnLimpar, "8, 24");
	}
}

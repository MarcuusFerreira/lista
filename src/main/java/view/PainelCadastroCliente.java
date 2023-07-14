package view;

import java.awt.Font;
import java.awt.JobAttributes;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.ClienteController;
import model.exception.CpfInvalidoException;
import model.exception.DataNascimentoInvalidaException;
import model.exception.ErroAtualizarException;
import model.exception.ErroCadastroException;
import model.exception.ErroClienteNaoCadastradoException;
import model.exception.ErroConsultarException;
import model.util.FormatadorData;
import model.vo.Cliente;

public class PainelCadastroCliente extends JPanel {
	protected static final Integer USUARIO = 1;
	private JTextField textNome;
	private JFormattedTextField textCpf;
	private MaskFormatter mascaraCpf;
	private DatePickerSettings dateSettings;
	protected ClienteController controller;
	private JButton btnVoltar;
	private JButton btnLimpar;
	private JTextField textNomeUsuario;
	private JPasswordField textSenha;
	private JLabel lblCliente;
	private DatePicker dtNascimento;
	private Cliente clienteSelecionado;
	private TelaPrincipal telaPrincipal;
	private PainelCadastroCliente painelCadastro;

	/**
	 * Create the panel.
	 * 
	 * @throws ParseException
	 */
	public PainelCadastroCliente(Cliente cliente) {
		setBounds(100, 100, 610, 650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FormLayout(
				new ColumnSpec[] { ColumnSpec.decode("30px"), ColumnSpec.decode("115px"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("336px:grow"), FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("102px"), },
				new RowSpec[] { RowSpec.decode("36px"), RowSpec.decode("17px"), FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("20px"), RowSpec.decode("31px"), RowSpec.decode("20px"), RowSpec.decode("23px"),
						RowSpec.decode("23px"), RowSpec.decode("31px"), RowSpec.decode("20px"), RowSpec.decode("31px"),
						RowSpec.decode("20px"), RowSpec.decode("31px"), RowSpec.decode("23px"), }));

		lblCliente = new JLabel("Cadastro de Cliente");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCliente.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblCliente, "2, 2, 5, 1, fill, top");

		JLabel lblNomeCompleto = new JLabel("Nome completo:");
		add(lblNomeCompleto, "1, 4, 2, 1, right, center");
		if (cliente == null) {
			textNome = new JTextField();
		} else {
			textNome = new JTextField(cliente.getNomeCliente());
		}
		add(textNome, "4, 4, fill, top");
		textNome.setColumns(10);
		// Adiciona um listener para definir o foco no campo textNome quando o painel
		// for exibido
		addAncestorListener(new javax.swing.event.AncestorListener() {
			public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
				textNome.requestFocusInWindow();
			}

			public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
			}

			public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
			}
		});

		JLabel lblCpf = new JLabel("CPF:");
		add(lblCpf, "1, 6, 2, 1, right, center");

		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setValueContainsLiteralCharacters(false);
		} catch (ParseException e) {
			// não faz nada
		}

		textCpf = new JFormattedTextField(mascaraCpf);
		add(textCpf, "4, 6, fill, top");
		if (cliente != null) {
			textCpf.setText(cliente.getCpf());
		}

		dtNascimento = new DatePicker();
		dtNascimento.getComponentToggleCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		add(dtNascimento, "4, 8");

		JLabel lblDataNascimento = new JLabel("Data de nascimento:");
		add(lblDataNascimento, "1, 8, 2, 1, right, center");

		// Configurações da parte de DATAS do componente
		dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);

		JLabel lblUsuario = new JLabel("Usuário:");
		add(lblUsuario, "1, 10, 2, 1, right, center");
		if (cliente == null) {
			textNomeUsuario = new JTextField();
			textNomeUsuario.setToolTipText(
					"<html>O usuário deve ser composto por:<br/>Pelo menos um caractere maiúsculo e minúsculo<br/>sendo seu tamanho mínimo de 8<br/>\r\nEx: Giovanne</html>");
		} else {
			textNomeUsuario = new JTextField(cliente.getNomeUsuario());
			textNomeUsuario.setToolTipText(
					"<html>O usuário deve ser composto por:<br/>Pelo menos um caractere maiúsculo e minúsculo<br/>sendo seu tamanho mínimo de 8<br/>\r\nEx: Giovanne</html>");
		}
		textNomeUsuario.setColumns(10);
		add(textNomeUsuario, "4, 10, fill, top");

		JLabel lblSenha = new JLabel("Senha:");
		add(lblSenha, "1, 12, 2, 1, right, center");

		if (cliente == null) {
			textSenha = new JPasswordField();
		} else {
			textSenha = new JPasswordField(cliente.getSenha());
		}

		textSenha.setToolTipText(
				"<html>A senha deve ser composta por:<br/>Pelo menos um caractere maiúsculo, minúsculo, numérico e especial<br/> sendo seu tamanho mínimo de 6<br/>\r\nEx: Giovanne1!</html>");
		add(textSenha, "4, 12, fill, top");

		JButton btnCadastrar = new JButton("Salvar");
		btnCadastrar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				controller = new ClienteController();
				Cliente clienteNovo = new Cliente();
				clienteNovo.setNomeCliente(textNome.getText());
				String cpfSemMascara = null;
				try {
					cpfSemMascara = (String) mascaraCpf.stringToValue(textCpf.getText());
					clienteNovo.setCpf(cpfSemMascara);
				} catch (ParseException mensagem) {
				}
				clienteNovo.setDataNascimento(dtNascimento.getDate());
				clienteNovo.setDataCadastro(LocalDateTime.now());
				clienteNovo.setTipoUsuario(USUARIO);
				clienteNovo.setNomeUsuario(textNomeUsuario.getText());
				clienteNovo.setSenha(textSenha.getText());
				if (cliente == null || clienteNovo.getIdCliente() == null) {
					try {
						if(controller.cadastrarNovoClienteController(clienteNovo)) {
							JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
							limparCampos();
						}
					} catch (ErroCadastroException | CpfInvalidoException | DataNascimentoInvalidaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				} else {
					clienteNovo.setIdCliente(cliente.getIdCliente());
					try {
						if (controller.atualizarCliente(clienteNovo)) {
							JOptionPane.showMessageDialog(null, "Cliente Atualizado com Sucesso!");
						}
					} catch (ErroAtualizarException | ErroConsultarException | ErroClienteNaoCadastradoException
							| ErroCadastroException | CpfInvalidoException e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});

		btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window window = SwingUtilities.getWindowAncestor(PainelCadastroCliente.this);
				if (window != null) {
					window.dispose(); // Fecha a janela anterior
				}
				TelaPrincipal frame = new TelaPrincipal(null);
				frame.setVisible(true);
			}
		});

		add(btnVoltar, "2, 14, fill, top");
		add(btnCadastrar, "4, 14, fill, top");

		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		add(btnLimpar, "6, 14, fill, top");
	}

	private void limparCampos() {
		textNome.setText("");
		textCpf.setText("");
		dtNascimento.setText("");
		textNomeUsuario.setText("");
		textSenha.setText("");
		textNome.requestFocus();
	}

	public void preencherCampos(Cliente cliente) {
		textNome.setText(cliente.getNomeCliente());
		textCpf.setText(cliente.getCpf());
		dtNascimento.setText(FormatadorData.formatarDataParaTela(cliente.getDataNascimento()));
		textNomeUsuario.setText(cliente.getNomeUsuario());
		textSenha.setText(cliente.getSenha());
	}

}

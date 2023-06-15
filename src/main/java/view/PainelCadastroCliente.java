package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.ClienteController;
import model.entity.Cliente;
import model.exception.CpfInvalidoException;
import model.exception.ErroCadastroException;
import javax.swing.SwingConstants;
import java.awt.Font;

public class PainelCadastroCliente extends JPanel  {
	private JTextField textNome;
	private JFormattedTextField textCpf;
	private MaskFormatter mascaraCpf;
	private MaskFormatter mascaraCep;
	private DatePickerSettings dateSettings;
	private DateTimePicker dataTeste;
	private JTextField textDataNascimento;
	protected ClienteController controller;
	private JButton btnVoltar;
	private JButton btnLimpar;
	private JButton btnPegarData;
	private JTextField textNomeUsuario;
	private JPasswordField textSenha;
	private JLabel lblCliente;

	/**
	 * Create the panel.
	 * @throws ParseException 
	 */
	public PainelCadastroCliente() {
		setBounds(100, 100, 610, 650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("30px"),
				ColumnSpec.decode("105px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("336px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("102px"),},
			new RowSpec[] {
				RowSpec.decode("36px"),
				RowSpec.decode("17px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				RowSpec.decode("31px"),
				RowSpec.decode("20px"),
				RowSpec.decode("31px"),
				RowSpec.decode("23px"),
				RowSpec.decode("31px"),
				RowSpec.decode("20px"),
				RowSpec.decode("31px"),
				RowSpec.decode("20px"),
				RowSpec.decode("31px"),
				RowSpec.decode("23px"),}));
		
		lblCliente = new JLabel("Cadastro de Cliente");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCliente.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblCliente, "2, 2, 5, 1, fill, top");
		
				JLabel lblNomeCompleto = new JLabel("Nome completo:");
				add(lblNomeCompleto, "2, 4, right, center");
		
				textNome = new JTextField();
				add(textNome, "4, 4, fill, top");
				textNome.setColumns(10);

		JLabel lblCpf = new JLabel("CPF:");
		add(lblCpf, "2, 6, right, center");

		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setValueContainsLiteralCharacters(false);
		} catch (ParseException e) {
			//não faz nada
		}

		textCpf = new JFormattedTextField(mascaraCpf);
		add(textCpf, "4, 6, fill, top");

		JLabel lblDataNascimento = new JLabel("Data de nascimento:");
		add(lblDataNascimento, "2, 8, right, center");

		// Configurações da parte de DATAS do componente
		dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);

		textDataNascimento = new JTextField();
		add(textDataNascimento, "4, 8, fill, center");
		textDataNascimento.setColumns(10);

		btnPegarData = new JButton("...");
		btnPegarData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Atributos próprios do componente datePicker (date e time)
				LocalDate dataSelecionada = dataTeste.getDatePicker().getDate();

				LocalDateTime dataComHora = LocalDateTime.of(dataSelecionada, null);

				JOptionPane.showMessageDialog(null, "Data selecionada: " + dataSelecionada.toString());
				JOptionPane.showMessageDialog(null, "Data e hora selecionada: " + dataComHora.toString());

			}
		});
		add(btnPegarData, "6, 8, left, top");

		try {
			mascaraCep = new MaskFormatter("##.###-##");
			mascaraCep.setValueContainsLiteralCharacters(false);
		} catch (ParseException e1) {
		}

		JLabel lblUsuario = new JLabel("Usuário:");
		add(lblUsuario, "2, 10, right, center");
		
		textNomeUsuario = new JTextField();
		textNomeUsuario.setColumns(10);
		add(textNomeUsuario, "4, 10, fill, top");

		JLabel lblSenha = new JLabel("Senha:");
		add(lblSenha, "2, 12, right, center");

		textSenha = new JPasswordField();
		add(textSenha, "4, 12, fill, top");
		
		
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validarCampos()) {
					JOptionPane.showMessageDialog(null, "Existem campos em branco");
				}
				Cliente cliente = new Cliente();
				cliente.setNomeCliente(textNome.getText());
				cliente.setCpf(textCpf.getText());   // precisou colocar a palavra reservada final pra funcionar 
				//cliente.setDataNascimento(LocalDate.parse(textDataNascimento.getText()));
				cliente.setDataNascimento(LocalDate.now());
				cliente.setDataCadastro(LocalDate.now());
				cliente.setTipoUsuario(1);
				cliente.setNomeUsuario(textNomeUsuario.getText());
				cliente.setSenha(textSenha.getText());
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
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		add(btnVoltar, "2, 14, fill, top");
		add(btnCadastrar, "4, 14, fill, top");

		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				textNome.setText("");
				textCpf.setText("");
				textDataNascimento.setText("");
				textNomeUsuario.setText("");
				textSenha.setText("");
			}
		});
		add(btnLimpar, "6, 14, fill, top");
	}
	
	private boolean validarCampos() {
		boolean retorno = false;
		if(textNome.getText().trim().isBlank() ||
			textCpf.getText().trim().isBlank() ||
			textDataNascimento.getText().trim().isBlank() ||
			textNomeUsuario.getText().trim().isBlank() ||
			textSenha.getText().trim().isBlank()) {
			retorno = true;
		}
		return retorno;
	}
}

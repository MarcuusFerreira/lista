package view;

import java.awt.EventQueue;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
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
	private JTextField textField;
	private JPasswordField passwordField;
	private DatePickerSettings dateSettings;
	private DateTimePicker dataTeste;
	private JTextField textDataNascimento;

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

		JLabel lblNomeCompleto = new JLabel("Nome completo:");
		contentPane.add(lblNomeCompleto, "4, 4, right, default");

		textNome = new JTextField();
		contentPane.add(textNome, "6, 4, fill, default");
		textNome.setColumns(10);

		JLabel lblCpf = new JLabel("CPF:");
		contentPane.add(lblCpf, "4, 8, right, default");

		mascaraCpf = new MaskFormatter("###.###.###-##");
		mascaraCpf.setValueContainsLiteralCharacters(false);

		JFormattedTextField textCpf = new JFormattedTextField(mascaraCpf);
		contentPane.add(textCpf, "6, 8, fill, default");

		JLabel lblDataNascimento = new JLabel("Data de nascimento:");
		contentPane.add(lblDataNascimento, "4, 12, right, default");

		// Configurações da parte de DATAS do componente
		dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);
		
		
		
		
		
		
		
		
		
		
		
		
		
		textDataNascimento = new JTextField();
		contentPane.add(textDataNascimento, "6, 12, fill, default");
		textDataNascimento.setColumns(10);
		
		JButton btnPegarData = new JButton("...");
		btnPegarData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Atributos próprios do componente datePicker (date e time)
				LocalDate dataSelecionada = dataTeste.getDatePicker().getDate();
				
				LocalDateTime dataComHora = LocalDateTime.of(dataSelecionada, null);

				JOptionPane.showMessageDialog(null, "Data selecionada: " + dataSelecionada.toString());
				JOptionPane.showMessageDialog(null, "Data e hora selecionada: " + dataComHora.toString());
				
			}
		});
		contentPane.add(btnPegarData, "8, 12");

		mascaraCep = new MaskFormatter("##.###-##");
		mascaraCep.setValueContainsLiteralCharacters(false);

		JLabel lblUsuario = new JLabel("Usuário:");
		contentPane.add(lblUsuario, "4, 16, right, default");

		textField = new JTextField();
		contentPane.add(textField, "6, 16, fill, default");
		textField.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		contentPane.add(lblSenha, "4, 20, right, default");

		passwordField = new JPasswordField();
		contentPane.add(passwordField, "6, 20, fill, default");
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnVoltar = new JButton("Voltar");
		contentPane.add(btnVoltar, "4, 24");
		contentPane.add(btnCadastrar, "6, 24");
		
		JButton btnLimpar = new JButton("Limpar");
		contentPane.add(btnLimpar, "8, 24");
		
	}

}

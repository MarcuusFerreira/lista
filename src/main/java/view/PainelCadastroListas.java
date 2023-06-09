package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class PainelCadastroListas extends JPanel  {
	private JComboBox cbNomeListas;
	private MaskFormatter mascaraCpf;
	private MaskFormatter mascaraCep;
	private DatePickerSettings dateSettings;
	private DateTimePicker dataTeste;

	/**
	 * Create the panel.
	 * @throws ParseException 
	 */
	public PainelCadastroListas() {
		setBounds(100, 100, 450, 490);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FormLayout(new ColumnSpec[] {
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
				RowSpec.decode("default:grow"),
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
		
		JLabel lblCadastroLista = new JLabel("Cadastro de Lista");
		add(lblCadastroLista, "1, 2, 8, 1, center, default");

		JLabel lblNomeLista = new JLabel("Nome da Lista:");
		add(lblNomeLista, "4, 6, right, default");

		cbNomeListas = new JComboBox();
		cbNomeListas.setSelectedIndex(-1);
		cbNomeListas.addItem("Lista do Mercado 1");
		cbNomeListas.addItem("Lista do Mercado 2");
		cbNomeListas.addItem("Lista do Mercado 3");
		add(cbNomeListas, "6, 6, fill, default");
		

		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setValueContainsLiteralCharacters(false);
		} catch (ParseException e) {
			//não faz nada
		}

		// Configurações da parte de DATAS do componente
		dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);

		try {
			mascaraCep = new MaskFormatter("##.###-##");
			mascaraCep.setValueContainsLiteralCharacters(false);
		} catch (ParseException e1) {
		}

		JButton btnCadastrarLista = new JButton("Salvar Lista");
		btnCadastrarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel lblProduto = new JLabel("Produto:");
		add(lblProduto, "4, 10, right, default");
		
		JComboBox cbProdutos = new JComboBox();
		cbProdutos.setSelectedIndex(-1);
		add(cbProdutos, "6, 10, fill, default");
		
		JButton btnAdicionar = new JButton("+");
		add(btnAdicionar, "8, 10");
		
		JLabel lblProdutoSelecionados = new JLabel("Produtos Selecionados");
		lblProdutoSelecionados.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblProdutoSelecionados, "6, 14");
		
		JList listaDeProdutos = new JList();
		add(listaDeProdutos, "6, 16, 1, 7, fill, default");
		add(btnVoltar, "4, 26");
		add(btnCadastrarLista, "6, 26");

		JButton btnLimpar = new JButton("Limpar");
		add(btnLimpar, "8, 26");
	}
}

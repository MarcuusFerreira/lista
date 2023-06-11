package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JCheckBox;

public class PainelCadastroListas extends JPanel {
	private JComboBox cbNomeListas;
	private MaskFormatter mascaraCpf;
	private MaskFormatter mascaraCep;
	private DatePickerSettings dateSettings;
	private DateTimePicker dataTeste;
	private JTable tableProdutos;
	private String[] nomesColunas = { "Unidade de medida", "Nome" };

	/**
	 * Create the panel.
	 * 
	 * @throws ParseException
	 */
	public PainelCadastroListas() {
		setBounds(100, 100, 450, 490);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblCadastroLista = new JLabel("Adicione uma Lista seus Produtos");
		lblCadastroLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblCadastroLista, "1, 2, 8, 1, center, default");
		
		JLabel lblDesejaAdicionarUma = new JLabel("Deseja utilizar a última lista criada?");
		add(lblDesejaAdicionarUma, "6, 4, center, center");
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Sim");
		add(chckbxNewCheckBox, "8, 4");

		JLabel lblNomeLista = new JLabel("Nome da Lista:");
		add(lblNomeLista, "4, 6, left, default");

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
			// não faz nada
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

		JComboBox cbProdutos = new JComboBox();
		add(cbProdutos, "6, 8, fill, default");
		cbProdutos.setSelectedIndex(-1);
		cbProdutos.addItem("Óleo de cozinha");
		cbProdutos.addItem("Arroz");
		cbProdutos.addItem("Leite");

		JLabel lblNewLabel = new JLabel("Unidade de Medida:");
		add(lblNewLabel, "4, 10, left, default");

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Quantidade");
		add(rdbtnNewRadioButton, "6, 10");

		JRadioButton rdbtnKilogramas = new JRadioButton("Kilogramas");
		add(rdbtnKilogramas, "6, 12");

		JFormattedTextField frmtdtxtfldKgOuUnidade = new JFormattedTextField();
		frmtdtxtfldKgOuUnidade.setToolTipText("Use Kg ou Unidade");
		frmtdtxtfldKgOuUnidade.setHorizontalAlignment(SwingConstants.CENTER);
		add(frmtdtxtfldKgOuUnidade, "6, 14, fill, default");

		JButton btnAdicionar = new JButton("+");
		add(btnAdicionar, "8, 14, left, default");

		JLabel lblProdutoSelecionados = new JLabel("Produtos Selecionados");
		lblProdutoSelecionados.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblProdutoSelecionados, "6, 16");

		tableProdutos = new JTable();
		this.limparTabelaProdutos();
		add(tableProdutos, "6, 18, fill, fill");
		
		JButton btnAdicionar_1 = new JButton("-");
		add(btnAdicionar_1, "8, 18, left, top");
		add(btnVoltar, "4, 26");
		add(btnCadastrarLista, "6, 26");

		JButton btnLimpar = new JButton("Limpar");
		add(btnLimpar, "8, 26");
	}

	private void limparTabelaProdutos() {
		tableProdutos.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
	}
}

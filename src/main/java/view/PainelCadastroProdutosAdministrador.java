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

public class PainelCadastroProdutosAdministrador extends JPanel {
	private JTextField cbNomeListas;
	private MaskFormatter mascaraCpf;
	private MaskFormatter mascaraCep;
	private DatePickerSettings dateSettings;
	private DateTimePicker dataTeste;
	private JTable tableProdutos;
	private String[] nomesColunas = { "Setor", "Nome do produto", "Unidade de medida", "Data de adição" };
	private JRadioButton rdbtnKilogramas;
	private JRadioButton rdbtnQuantidade;

	/**
	 * Create the panel.
	 * 
	 * @throws ParseException
	 */
	public PainelCadastroProdutosAdministrador() {
		setBounds(100, 100, 610, 650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
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
				FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel lblCRUD = new JLabel("CRUD de Produto");
		lblCRUD.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblCRUD, "1, 2, 18, 1, center, default");

		JLabel lblSetor = new JLabel("Setor:");
		add(lblSetor, "4, 4, right, default");

		JComboBox cbProdutos = new JComboBox();

		add(cbProdutos, "6, 4, 5, 1");
		cbProdutos.addItem("Frutas e Vegetais");
		cbProdutos.addItem("Padaria");
		cbProdutos.addItem("Açougue");
		cbProdutos.addItem("Laticínios");
		cbProdutos.addItem("Congelados");
		cbProdutos.addItem("Mercearia");
		cbProdutos.addItem("Higiene Pessoal");
		cbProdutos.addItem("Limpeza Doméstica");
		cbProdutos.addItem("Bebidas Alcoólicas");
		cbProdutos.addItem("Outros");
		cbProdutos.setSelectedIndex(-1);

		JLabel lblNomeLista = new JLabel("Nome do Produto:");
		add(lblNomeLista, "4, 6, right, default");

		cbNomeListas = new JTextField();
		add(cbNomeListas, "6, 6, 5, 1, fill, default");

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

		JLabel lblNewLabel = new JLabel("Unidade de Medida:");
		add(lblNewLabel, "4, 8");

		
		rdbtnQuantidade = new JRadioButton("Quantidade");
		rdbtnQuantidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnKilogramas.setSelected(false); // Desmarca o outro botão
			}
		});
		add(rdbtnQuantidade, "6, 8");
		rdbtnKilogramas = new JRadioButton("Kilogramas");
		rdbtnKilogramas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnQuantidade.setSelected(false); // Desmarca o outro botão
			}
		});
		JButton btnAdicionarProduto = new JButton("Adicionar Produto");
		btnAdicionarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnQuantidade.isSelected() || rdbtnKilogramas.isSelected()) {
					// Pelo menos um botão está selecionado, faça o que precisa ser feito
				} else {
					String message = "Por favor selecione a Unidade de Medida do produto.";
					JOptionPane.showMessageDialog(null, message, "Sobre nós", JOptionPane.INFORMATION_MESSAGE);
					// Nenhum botão está selecionado, mostre uma mensagem de erro ou faça alguma
					// outra ação
				}
			}
		});
		add(rdbtnKilogramas, "10, 8");
		add(btnAdicionarProduto, "6, 10, 5, 1, fill, default");

		JLabel lblProdutoSelecionados = new JLabel("Produtos Recém Adicionados");
		lblProdutoSelecionados.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblProdutoSelecionados, "4, 14, 15, 1");

		tableProdutos = new JTable();
		this.limparTabelaProdutos();
		add(tableProdutos, "4, 16, 15, 1, fill, fill");

		JButton btnAlterarProduto = new JButton("Alterar Produto");
		add(btnAlterarProduto, "6, 18, 5, 1");

		JButton btnDeletarProduto = new JButton("Deletar Produto");
		add(btnDeletarProduto, "6, 20, 5, 1");
		
				JButton btnVoltar = new JButton("Voltar");
				btnVoltar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				add(btnVoltar, "4, 24");
	}

	private void limparTabelaProdutos() {
		tableProdutos.setModel(new DefaultTableModel(
				new Object[][] { { "Setor", "Nome", "Unidade de medida", "Data de adi\u00E7\u00E3o" }, },
				new String[] { "Setor", "Nome", "Unidade de medida", "Data de adi\u00E7\u00E3o" }));
	}
}

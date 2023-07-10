package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.ListaController;
import controller.ProdutoController;
import model.exception.ErroConsultarException;
import model.vo.Cliente;
import model.vo.Lista;
import model.vo.Produto;
import model.vo.ProdutoLista;

public class PainelCadastroListas extends JPanel {
	private int idCliente = 1;
	private JComboBox cbNomeListas;
	private MaskFormatter mascaraCpf;
	private MaskFormatter mascaraCep;
	private DatePickerSettings dateSettings;
	private DateTimePicker dataTeste;
	private JTable tableProdutos;
	private String[] nomesColunas = { "Nome do Produto", "Nome do Setor", "Unidade de medida", "Quantidade/Peso" };
	public JFrame frmTelaPrincipal;
	private JFormattedTextField textKgOuUnidade;
	private JComboBox cbProdutos;
	private JRadioButton rdbtnQuantidade;
	private JRadioButton rdbtnKilogramas;
	private JTextField tFListaNova;
	private Lista listaNova;
	private List<Lista> listas;
	private List<Produto> produtos;
	private List<ProdutoLista> itemLista;

	/**
	 * Create the panel.
	 * 
	 * @throws ParseException
	 */
	public PainelCadastroListas(Cliente cliente) {
		setBounds(100, 100, 610, 650);
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
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblCadastroLista = new JLabel("Adicione uma Lista seus Produtos");
		lblCadastroLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblCadastroLista, "1, 2, 8, 1, center, default");

		JLabel lblDesejaAdicionarUma = new JLabel("Deseja utilizar a última lista criada?");
		add(lblDesejaAdicionarUma, "6, 4, center, center");

		JLabel lblNomeLista = new JLabel("Nome da Lista:");
		add(lblNomeLista, "4, 6, right, default");

		cbNomeListas = new JComboBox<>();
		cbNomeListas.setSelectedIndex(-1);
		add(cbNomeListas, "6, 6, fill, default");
		try {
			listas = new ListaController().consultarListaPorId(cliente.getIdCliente());
		} catch (ErroConsultarException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao consultar" + e.getCause(), JOptionPane.ERROR_MESSAGE);
		}
		for(Lista lista : listas) {
			cbNomeListas.addItem(lista.getNomeLista());
		}
		cbNomeListas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setValueContainsLiteralCharacters(false);
		} catch (ParseException e) {
			// não faz nada
		}

		// Configurações da parte de DATAS do componente
		dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);

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

		JLabel lblOuCrieUma = new JLabel("Ou Crie uma nova:");
		add(lblOuCrieUma, "4, 8, right, default");

		tFListaNova = new JTextField();
		add(tFListaNova, "6, 8, fill, default");
		tFListaNova.setColumns(10);

		JButton btnAdicionar_2 = new JButton("+");
		btnAdicionar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listaNova = new Lista();
				listaNova.setNomeLista(tFListaNova.getText());
				cbNomeListas.addItem(listaNova);
			}
		});
		add(btnAdicionar_2, "8, 8, left, default");

		JLabel lblProduto = new JLabel("Produto:");
		add(lblProduto, "4, 12, right, default");

		try {
			produtos = new ProdutoController().consultarProdutos();
			cbProdutos = new JComboBox(produtos.stream().map(Produto::getNome).toArray(String[]::new));
		} catch (ErroConsultarException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		add(cbProdutos, "6, 12, fill, default");
		cbProdutos.setSelectedIndex(-1);

		JLabel lblNewLabel = new JLabel("Unidade de Medida:");
		add(lblNewLabel, "4, 14, left, default");

		rdbtnQuantidade = new JRadioButton("Quantidade");
		rdbtnQuantidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnKilogramas.setSelected(false);
			}
		});
		add(rdbtnQuantidade, "6, 14");

		rdbtnKilogramas = new JRadioButton("Kilogramas");
		rdbtnKilogramas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnQuantidade.setSelected(false);
			}
		});
		add(rdbtnKilogramas, "6, 16");

		JLabel lblQuantidadekg = new JLabel("Quantidade/Peso:");
		add(lblQuantidadekg, "4, 18, right, default");

		textKgOuUnidade = new JFormattedTextField();
		textKgOuUnidade.setToolTipText("Use Kg ou Unidade");
		textKgOuUnidade.setHorizontalAlignment(SwingConstants.CENTER);
		add(textKgOuUnidade, "6, 18, fill, default");

		JButton btnAdicionar = new JButton("+");
		add(btnAdicionar, "8, 18, left, default");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbProdutos.getSelectedIndex();
				ProdutoLista itemLista = new ProdutoLista();
			}
		});

		JLabel lblProdutoSelecionados = new JLabel("Produtos Selecionados");
		lblProdutoSelecionados.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblProdutoSelecionados, "6, 20");

		tableProdutos = new JTable();
		this.limparTabelaProdutos();
		add(tableProdutos, "6, 22, 1, 3, fill, fill");

		JButton btnAdicionar_1 = new JButton("-");
		add(btnAdicionar_1, "8, 22, left, top");
		add(btnVoltar, "4, 30");
		add(btnCadastrarLista, "6, 30");

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}

		});
		add(btnLimpar, "8, 30");
	}

	private void limparCampos() {
		// Limpar campos de texto
		textKgOuUnidade.setText("");

		// Limpar seleção dos JComboBox
		cbNomeListas.setSelectedIndex(-1);
		cbProdutos.setSelectedIndex(-1);

		// Limpar seleção dos JRadioButtons
		rdbtnQuantidade.setSelected(false);
		rdbtnKilogramas.setSelected(false);

		// Limpar tabela de produtos
		limparTabelaProdutos();

	}

	private void limparTabelaProdutos() {
		DefaultTableModel model = (DefaultTableModel) tableProdutos.getModel();
		model.setRowCount(0);
	}

	private void AtualizarTabela() {
		this.limparTabelaProdutos();

		DefaultTableModel model = (DefaultTableModel) tableProdutos.getModel();

		for (ProdutoLista produto : itemLista) {
			Object[] novaLinha = new Object[4];
			novaLinha[0] = produto.getNome();
			novaLinha[1] = produto.getSetor();
			novaLinha[2] = produto.getUnidadeMedida();
			novaLinha[3] = produto.getValorMedida();
			model.addRow(novaLinha);
		}
	}
}

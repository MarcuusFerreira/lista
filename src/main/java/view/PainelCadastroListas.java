package view;

import java.awt.Event;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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
import model.exception.ErroCadastroException;
import model.exception.ErroConsultarException;
import model.exception.ErroListaCadastradaException;
import model.vo.Cliente;
import model.vo.Lista;
import model.vo.Produto;
import model.vo.ProdutoLista;
import model.vo.UnidadeMedida;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PainelCadastroListas extends JPanel {
	private JComboBox cbNomeListas;
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
	private List<ProdutoLista> itensLista;
	private ListaController controller;

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
		add(cbNomeListas, "6, 6, fill, default");
		try {
			listas = new ListaController().consultarListaController(cliente.getIdCliente());
		} catch (ErroConsultarException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao consultar" + e.getCause(),
					JOptionPane.ERROR_MESSAGE);
		}

		for (Lista lista : listas) {
			cbNomeListas.addItem(lista.getNomeLista());
		}
		cbNomeListas.setSelectedIndex(-1);
		
		// Configurações da parte de DATAS do componente
		dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);

		controller = new ListaController();
		JButton btnCadastrarLista = new JButton("Salvar Lista");
		btnCadastrarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Lista lista : listas) {
					if (lista.getIdLista() == null) {
						try {
							controller.cadastrarListasController(lista);
						} catch (ErroCadastroException | ErroListaCadastradaException e1) {
							JOptionPane.showMessageDialog(null, "Erro ao cadastrar " + e1.getCause(),
									"Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
					}
				}
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

		JButton btnAdicionarLista = new JButton("+");
		btnAdicionarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listaNova = new Lista();
				listaNova.setIdCliente(cliente.getIdCliente());
				listaNova.setNomeLista(tFListaNova.getText());
				listaNova.setDataLista(LocalDateTime.now());
				cbNomeListas.addItem(listaNova.getNomeLista());
				listas.add(listaNova);
				tFListaNova.setText("");
			}
		});
		add(btnAdicionarLista, "8, 8, left, default");

		JLabel lblProduto = new JLabel("Produto:");
		add(lblProduto, "4, 12, right, default");

		cbProdutos = new JComboBox<>();
		add(cbProdutos, "6, 12, fill, default");

		try {
			produtos = new ProdutoController().consultarProdutos();
		} catch (ErroConsultarException e1) {
			JOptionPane.showMessageDialog(null, "Erro ao consultar");
			e1.printStackTrace();
		}

		for (Produto produto : produtos) {
			cbProdutos.addItem(produto.getNome());
		}
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

		itensLista = new ArrayList<ProdutoLista>();
		JButton btnAdicionarProduto = new JButton("+");
		add(btnAdicionarProduto, "8, 18, left, default");
		btnAdicionarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selecionado = cbProdutos.getSelectedItem().toString();
				ProdutoLista itemLista = new ProdutoLista();
				for (Produto produto : produtos) {
					if (produto.getNome().equals(selecionado)) {
						itemLista.setIdProduto(produto.getIdProduto());
						itemLista.setNome(produto.getNome());
						itemLista.setMarca(produto.getMarca());
						if (rdbtnKilogramas.isSelected()) {
							itemLista.setUnidadeMedida(UnidadeMedida.KG);
						}
						if (rdbtnQuantidade.isSelected()) {
							itemLista.setUnidadeMedida(UnidadeMedida.QTD);
						}
						try {
							itemLista.setValorMedida(Integer.parseInt(textKgOuUnidade.getText()));
						} catch (NumberFormatException excecao) {
							JOptionPane.showInternalMessageDialog(null, "O campo só aceita números", "Campo Inválido",
									JOptionPane.ERROR_MESSAGE);
						}
						itensLista.add(itemLista);
					}
				}
				textKgOuUnidade.setText("");
				if (rdbtnKilogramas.isSelected()) {
					rdbtnKilogramas.setSelected(false);
				}

				if (rdbtnQuantidade.isSelected()) {
					rdbtnQuantidade.setSelected(false);
				}
				cbProdutos.setSelectedIndex(-1);
				listaNova.setProdutos(itensLista);
				AtualizarTabela();
			}
		});

		JLabel lblProdutoSelecionados = new JLabel("Produtos Selecionados");
		lblProdutoSelecionados.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblProdutoSelecionados, "6, 20");

		tableProdutos = new JTable();
		this.limparTabela();
		add(tableProdutos, "6, 22, 1, 3, fill, fill");

		JButton btnRemover = new JButton("-");
		add(btnRemover, "8, 22, left, top");
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
		limparTabela();

	}

	private void AtualizarTabela() {
		this.limparTabela();

		DefaultTableModel model = (DefaultTableModel) tableProdutos.getModel();

		for (ProdutoLista produto : itensLista) {
			Object[] novaLinha = new Object[4];
			novaLinha[0] = produto.getNome();
			novaLinha[1] = produto.getSetor();
			novaLinha[2] = produto.getUnidadeMedida();
			novaLinha[3] = produto.getValorMedida();
			model.addRow(novaLinha);
		}
	}

	public void limparTabela() {
		tableProdutos.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
	}
}

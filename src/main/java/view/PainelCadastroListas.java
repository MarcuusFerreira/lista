package view;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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

public class PainelCadastroListas extends JPanel {
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
		
		// Configurações da parte de DATAS do componente
		dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);

		controller = new ListaController();
		JButton btnCadastrarLista = new JButton("Salvar Lista");
		btnCadastrarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller = new ListaController();
				listaNova = new Lista();
				listaNova.setIdCliente(cliente.getIdCliente());
				listaNova.setNomeLista(tFListaNova.getText());
				listaNova.setDataLista(LocalDateTime.now());
				listaNova.setProdutos(itensLista);
				try {
					if(controller.cadastrarListasController(listaNova)) {
						JOptionPane.showMessageDialog(null, "Lista salva com sucesso!");
						limparCampos();
					}
				} catch (ErroCadastroException | ErroListaCadastradaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro ao consultar" + e1.getCause(),
							JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window window = SwingUtilities.getWindowAncestor(PainelCadastroListas.this);
				if (window != null) {
					window.dispose(); // Fecha a janela anterior
				}
				TelaPrincipal frame = new TelaPrincipal(null);
				frame.setVisible(true);
			}
		});
		
				tFListaNova = new JTextField();
				add(tFListaNova, "6, 6, fill, default");
				tFListaNova.setColumns(10);
		
				JLabel lblProduto = new JLabel("Produto:");
				add(lblProduto, "4, 10, right, default");

		try {
			produtos = new ProdutoController().consultarProdutos();
		} catch (ErroConsultarException e1) {
			JOptionPane.showMessageDialog(null, "Erro ao consultar");
			e1.printStackTrace();
		}

		cbProdutos = new JComboBox<>();
		add(cbProdutos, "6, 10, fill, default");
		
		for (Produto produto : produtos) {
			cbProdutos.addItem(produto.getNome());
		}
		cbProdutos.setSelectedIndex(-1);
		
				
		JLabel lblNewLabel = new JLabel("Unidade de Medida:");
		add(lblNewLabel, "4, 12, left, default");
		
				rdbtnQuantidade = new JRadioButton("Quantidade");
				rdbtnQuantidade.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						rdbtnKilogramas.setSelected(false);
					}
				});
				add(rdbtnQuantidade, "6, 12");
		
				rdbtnKilogramas = new JRadioButton("Kilogramas");
				rdbtnKilogramas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						rdbtnQuantidade.setSelected(false);
					}
				});
				add(rdbtnKilogramas, "6, 14");
				
						JLabel lblQuantidadekg = new JLabel("Quantidade/Peso:");
						add(lblQuantidadekg, "4, 16, right, default");
		
				textKgOuUnidade = new JFormattedTextField();
				textKgOuUnidade.setToolTipText("Use Kg ou Unidade");
				textKgOuUnidade.setHorizontalAlignment(SwingConstants.CENTER);
				add(textKgOuUnidade, "6, 16, fill, default");
		JButton btnAdicionarProduto = new JButton("+");
		add(btnAdicionarProduto, "8, 16, left, default");
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
				AtualizarTabela();
			}
		});

		itensLista = new ArrayList<ProdutoLista>();

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
		tFListaNova.setText("");
		textKgOuUnidade.setText("");

		// Limpar seleção dos JComboBox
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

package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.github.lgooddatepicker.components.DatePicker;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.ClienteController;
import controller.ListaController;
import model.exception.ErroConsultarException;
import model.vo.Cliente;
import model.vo.Lista;

import java.awt.Font;
import java.util.List;
import javax.swing.JComboBox;
//import model.exception.ClienteComTelefoneException;
//import model.seletor.ClienteSeletor;

public class PainelMostrarListas extends JPanel {
	private JTable tblClientes;
	private ArrayList<Cliente> clientes;
	private String[] nomesColunas = { "Nome da Lista", "Setor", "Marca", "Nome do Produto", "Unidade de Medida", "Data da Lista" };
	private JComboBox cbNomeListas;
	private MaskFormatter mascaraCpf;
	private JButton btnEditar;
	private JButton btnGerarPlanilha;
	private JButton btnExcluir;
	private JLabel lblNome;

	private ClienteController controller = new ClienteController();
	private Cliente clienteSelecionado;

	// Atributos para a PAGINAÇÃO
	private final int TAMANHO_PAGINA = 5;
	private int paginaAtual = 1;
	private int totalPaginas = 0;
	private JButton btnVoltarPagina;
	private JButton btnAvancarPagina;
	private JLabel lblNewLabel;
	private JButton btnAvancarPagina_1;
	private JLabel lblProdutos;
//	private ClienteSeletor seletor = new ClienteSeletor();
	private JButton btnGerarPlanilhaDeTodasAsListas;
	private List<Lista> listas;

	private void limparTabelaClientes() {
		tblClientes.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
	}

//	private void atualizarTabelaClientes() {
//		this.limparTabelaClientes();
//
//		DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
//
//		for (Cliente c : clientes) {
//			Object[] novaLinhaDaTabela = new Object[5];
//			novaLinhaDaTabela[0] = c.getNome();
//			novaLinhaDaTabela[1] = c.getCpf();
//			novaLinhaDaTabela[2] = c.getEndereco().getEnderecoResumido();
//			novaLinhaDaTabela[3] = c.getTelefones().size();
//			novaLinhaDaTabela[4] = c.isAtivo() ? "Sim" : "Não";
//
//			model.addRow(novaLinhaDaTabela);
//		}
//	}

	public PainelMostrarListas(Cliente cliente) {
		setBounds(100, 100, 610, 650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("94px"),
				ColumnSpec.decode("94px"),
				ColumnSpec.decode("center:101px"),
				ColumnSpec.decode("center:101px"),
				ColumnSpec.decode("94px"),
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("29px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("35px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("133px:grow"),
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("33px"),
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));

		tblClientes = new JTable();
		this.limparTabelaClientes();

		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indiceSelecionado = tblClientes.getSelectedRow();

				if (indiceSelecionado > 0) {
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					clienteSelecionado = clientes.get(indiceSelecionado - 1);
				} else {
					btnEditar.setEnabled(false);
					btnExcluir.setEnabled(false);
				}
			}
		});

		lblNewLabel = new JLabel("Mostrar Lista");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, "1, 2, 7, 1");

		lblProdutos = new JLabel("Produtos");
		lblProdutos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProdutos.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblProdutos, "1, 8, 8, 1");
		this.add(tblClientes, "2, 10, 6, 1, fill, fill");

		lblNome = new JLabel("Selecione a Lista:");
		this.add(lblNome, "2, 4, 2, 1, fill, center");

		cbNomeListas = new JComboBox<>();
		cbNomeListas.setSelectedIndex(-1);
		
		try {
			listas = new ListaController().consultarListaPorId(cliente.getIdCliente());
		} catch (ErroConsultarException e1) {
			JOptionPane.showMessageDialog(null, e1, "Erro ao consultar " + e1.getCause(), JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		
		for(Lista lista : listas) {
			cbNomeListas.addItem(lista.getNomeLista());
		}
		
		this.add(cbNomeListas, "4, 4, 4, 1, fill, default");

		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setValueContainsLiteralCharacters(false);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
						btnAvancarPagina_1 = new JButton("Voltar");
						btnAvancarPagina_1.setEnabled(false);
						add(btnAvancarPagina_1, "3, 13, fill, default");

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		
				btnGerarPlanilha = new JButton("Gerar Planilha");
				btnGerarPlanilha.setEnabled(false);
				btnGerarPlanilha.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser janelaSelecaoDestinoArquivo = new JFileChooser();
						janelaSelecaoDestinoArquivo.setDialogTitle("Selecione um destino para a planilha...");

						int opcaoSelecionada = janelaSelecaoDestinoArquivo.showSaveDialog(null);
						if (opcaoSelecionada == JFileChooser.APPROVE_OPTION) {
							String caminhoEscolhido = janelaSelecaoDestinoArquivo.getSelectedFile().getAbsolutePath();
							// TODO decomentar na aula 11
							// controller.gerarRelatorio(clientes, caminhoEscolhido);
						}
					}
				});
						//		btnExcluir.addActionListener(new ActionListener() {
						
						//			@Override
						//			public void actionPerformed(ActionEvent e) {
						//				int opcaoSelecionada = JOptionPane.showConfirmDialog(null, "Confirma a exclusão do cliente selecionado?");
						//				
						//				if(opcaoSelecionada == JOptionPane.YES_OPTION) {
						//					try {
						//						controller.excluir(clienteSelecionado.getId());
						//						JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso");
						//						clientes = (ArrayList<Cliente>) controller.consultarTodos();
						//						atualizarTabelaClientes();
						//					} catch (ClienteComTelefoneException e1) {
						//						JOptionPane.showConfirmDialog(null, e1.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
						//					}
						//				}
						//			}
						//		});
						//		this.add(btnExcluir, "15, 14, fill, fill");
						//		
						//		btnVoltarPagina = new JButton("<< Voltar");
						//		btnVoltarPagina.addActionListener(new ActionListener() {
						//			public void actionPerformed(ActionEvent e) {
						//				paginaAtual--;
						////				buscarClientesComFiltros();
						//				lblPaginacao.setText(paginaAtual + " / " + totalPaginas);
						//				btnVoltarPagina.setEnabled(paginaAtual > 1);
						//				btnAvancarPagina.setEnabled(paginaAtual < totalPaginas);
						//			}
						//		});
						//		btnVoltarPagina.setEnabled(false);
						//		add(btnVoltarPagina, "6, 12, 5, 1, fill, top");
						
								btnAvancarPagina = new JButton("Avançar");
								btnAvancarPagina.setEnabled(false);
								btnAvancarPagina.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										paginaAtual++;
//				buscarClientesComFiltros();
//						lblPaginacao.setText(paginaAtual + " / " + totalPaginas);
										btnVoltarPagina.setEnabled(paginaAtual > 1);
										btnAvancarPagina.setEnabled(paginaAtual < totalPaginas);
									}
								});
								add(btnAvancarPagina, "6, 13, fill, default");
				this.add(btnGerarPlanilha, "4, 15, 2, 1");
		
				btnEditar = new JButton("Editar");
				btnEditar.setEnabled(false);
				this.add(btnEditar, "4, 16, 2, 1");
		
		btnGerarPlanilhaDeTodasAsListas = new JButton("Gerar Planilha de Todas as Listas");
		btnGerarPlanilhaDeTodasAsListas.setEnabled(false);
		add(btnGerarPlanilhaDeTodasAsListas, "3, 20, 4, 1, fill, default");

//		atualizarQuantidadePaginas();
	}

//	private void atualizarQuantidadePaginas() {
//		//Cálculo do total de páginas (poderia ser feito no backend)
////		int totalRegistros = controller.contarTotalRegistrosComFiltros(seletor);
//		
//		//QUOCIENTE da divisão inteira
////		totalPaginas = totalRegistros / TAMANHO_PAGINA;
//		
//		//RESTO da divisão inteira
////		if(totalRegistros % TAMANHO_PAGINA > 0) { 
//			totalPaginas++;
//		}
//		
////		lblPaginacao.setText(paginaAtual + " / " + totalPaginas);
//	}

//	protected void buscarClientesComFiltros() {
//		seletor = new ClienteSeletor();
//		seletor.setLimite(TAMANHO_PAGINA);
//		seletor.setPagina(paginaAtual);
//		seletor.setNome(txtNome.getText());
//		
//		String cpfSemMascara;
//		try {
//			cpfSemMascara = (String) mascaraCpf.stringToValue(
//					txtCPF.getText());
//			seletor.setCpf(cpfSemMascara);
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		}
//		
//		seletor.setDataNascimentoInicial(dtNascimentoInicial.getDate());
//		seletor.setDataNascimentoFinal(dtNascimentoFinal.getDate());
//		clientes = (ArrayList<Cliente>) controller.consultarComFiltros(seletor);
//		atualizarTabelaClientes();
//		atualizarQuantidadePaginas();
//	}

	// Torna o btnEditar acessível externamente à essa classe
	public JButton getBtnEditar() {
		return this.btnEditar;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}
}

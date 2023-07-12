package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.ListaController;
import model.exception.ErroConsultarException;
import model.exception.ErroExcluirException;
import model.seletor.ListaSeletor;
import model.vo.Cliente;
import model.vo.Lista;

public class PainelMostrarListas extends JPanel {
	private JTable tblListas;
	private String[] nomesColunas = { "Código", "Lista", "Data de cadastro"};
	private MaskFormatter mascaraCpf;
	private JButton btnEditar;
	private JButton btnGerarPlanilha;
	private JButton btnExcluir;
	private ListaController controller;
	private Lista listaSelecionado;
	// Atributos para a PAGINAÇÃO
	private final int TAMANHO_PAGINA = 5;
	private int paginaAtual = 1;
	private int totalPaginas = 0;
	private JButton btnVoltarPagina;
	private JButton btnAvancarPagina;
	private JLabel lblNewLabel;
	private JButton btnAvancarPagina_1;
	private JButton btnGerarPlanilhaDeTodasAsListas;
	private List<Lista> listas;
	private JLabel lblNome;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField txtNome;
	private JFormattedTextField ftxtDtIFim;
	private JFormattedTextField ftxtDtIni;
	private MaskFormatter dataMascarada;
	private JButton btnBuscar;
	private ListaSeletor seletor;

	private void limparTabelaClientes() {
		tblListas.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
	}

	private void atualizarTabela() {
		limparTabelaClientes();
		
		DefaultTableModel modelo = (DefaultTableModel) tblListas.getModel();
		
		for (Lista lista : listas) {
			Object[] novaLinha = new Object[3];
			novaLinha[0] = lista.getIdLista();
			novaLinha[1] = lista.getNomeLista();
			novaLinha[2] = lista.getDataLista();
			modelo.addRow(novaLinha);
		}
	}

	public PainelMostrarListas(Cliente cliente) {
		setBounds(100, 100, 610, 650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("94px"),
				ColumnSpec.decode("94px"),
				ColumnSpec.decode("center:101px:grow"),
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

		tblListas = new JTable();
		this.limparTabelaClientes();

		tblListas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indiceSelecionado = tblListas.getSelectedRow();

				if (indiceSelecionado > 0) {
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					listaSelecionado = listas.get(indiceSelecionado - 1);
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
		
		lblNome = new JLabel("Nome");
		add(lblNome, "2, 4");
		
		txtNome = new JTextField();
		add(txtNome, "3, 4, 4, 1, fill, default");
		txtNome.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Data de Cadastro");
		add(lblNewLabel_1, "2, 6, 2, 1");
		
		try {
			dataMascarada = new MaskFormatter("##/##/####");
			dataMascarada.setValueContainsLiteralCharacters(false);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ftxtDtIni = new JFormattedTextField(dataMascarada);
		add(ftxtDtIni, "4, 6, fill, default");
		
		lblNewLabel_2 = new JLabel("até");
		add(lblNewLabel_2, "2, 8, left, default");
		
		ftxtDtIFim = new JFormattedTextField(dataMascarada);
		add(ftxtDtIFim, "4, 8, fill, default");
		this.add(tblListas, "2, 10, 6, 1, fill, fill");
		
						btnAvancarPagina_1 = new JButton("Voltar");
						btnAvancarPagina_1.setEnabled(false);
						add(btnAvancarPagina_1, "3, 13, fill, default");

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir a lista?");
				if(opcao == JOptionPane.YES_OPTION) {
				controller = new ListaController();
				try {
					controller.excluirListaController(listaSelecionado.getIdLista());
				} catch (ErroExcluirException e1) {
					JOptionPane.showConfirmDialog(null, e1.getMessage(), "Erro ao excluir", JOptionPane.WARNING_MESSAGE);
				}
				}
			}
		});
		
		
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
						//		acbNomeListasdd(btnVoltarPagina, "6, 12, 5, 1, fill, top");
						
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
		
		btnBuscar = new JButton("Buscar");
		add(btnBuscar, "4, 18, 2, 1");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller = new ListaController();
				seletor = new ListaSeletor();
				seletor.setNome(txtNome.getText());
				String dataIni;
				String dataFim;
				try {
					dataIni = (String) dataMascarada.stringToValue(ftxtDtIni.getText());
					if(dataIni != null && dataIni.isBlank()) {
						seletor.setDataCadastroInicio(LocalDate.parse(ftxtDtIni.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					}
					dataFim = (String) dataMascarada.stringToValue(ftxtDtIFim.getText());
					if(dataFim != null && dataFim.isBlank()) {
						seletor.setDataCadastroFim(LocalDate.parse(ftxtDtIFim.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				if(seletor.temFiltro()) {
					try {
						listas = controller.consultarComFiltro(cliente.getIdCliente(), seletor);
					} catch (ErroConsultarException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1, "Erro ao consultar", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					try {
						listas =controller.consultarListaController(cliente.getIdCliente());
					} catch (ErroConsultarException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, e1, "Erro ao consultar", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				atualizarTabela();
			}
		});
		btnGerarPlanilhaDeTodasAsListas = new JButton("Gerar Planilha de Todas as Listas");
		btnGerarPlanilhaDeTodasAsListas.setEnabled(false);
		add(btnGerarPlanilhaDeTodasAsListas, "3, 20, 4, 1, fill, default");
	}

	public JButton getBtnEditar() {
		return this.btnEditar;
	}
}

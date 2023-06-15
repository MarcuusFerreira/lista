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
import model.entity.Cliente;
import java.awt.Font;
import javax.swing.JComboBox;
//import model.exception.ClienteComTelefoneException;
//import model.seletor.ClienteSeletor;

public class PainelMostrarListas extends JPanel {
	private JTable tblClientes;
	private ArrayList<Cliente> clientes;
	private String[] nomesColunas = { "Unidade de Medida", "Nome do Produto" };
	private JComboBox cbNomeListas;
	private MaskFormatter mascaraCpf;
	private JButton btnEditar;
	private JButton btnGerarPlanilha;
	private JButton btnExcluir;
	private JLabel lblNome;
	private JLabel lblPaginacao;
	
	private ClienteController controller = new ClienteController();
	private Cliente clienteSelecionado;
	
	//Atributos para a PAGINAÇÃO
	private final int TAMANHO_PAGINA = 5;
	private int paginaAtual = 1;
	private int totalPaginas = 0;
	private JButton btnVoltarPagina;
	private JButton btnAvancarPagina;
	private JLabel lblNewLabel;
	private JButton btnAvancarPagina_1;
	private JLabel lblProdutos;
//	private ClienteSeletor seletor = new ClienteSeletor();
	
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
	
	public PainelMostrarListas() {
		setBounds(100, 100, 610, 650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("61px"),
				ColumnSpec.decode("94px"),
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
				RowSpec.decode("133px"),
				RowSpec.decode("22px"),
				RowSpec.decode("23px"),
				RowSpec.decode("33px"),}));

		tblClientes = new JTable();
		this.limparTabelaClientes(); // Adicionei essa linha

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
						add(lblNewLabel, "2, 2, 5, 1");
		
		lblProdutos = new JLabel("Produtos");
		lblProdutos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblProdutos.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblProdutos, "3, 8, 3, 1");
		this.add(tblClientes, "2, 10, 5, 1, fill, fill");

		lblNome = new JLabel("Selecione a Lista:");
		this.add(lblNome, "2, 4, 2, 1, fill, center");

		cbNomeListas = new JComboBox();
		cbNomeListas.setSelectedIndex(-1);
		cbNomeListas.addItem("Lista do Mercado 1");
		cbNomeListas.addItem("Lista do Mercado 2");
		cbNomeListas.addItem("Lista do Mercado 3");
		this.add(cbNomeListas, "4, 4, 3, 1, fill, default");

		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setValueContainsLiteralCharacters(false);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
				btnGerarPlanilha = new JButton("Gerar Planilha");
				btnGerarPlanilha.setEnabled(false);
				btnGerarPlanilha.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser janelaSelecaoDestinoArquivo = new JFileChooser();
						janelaSelecaoDestinoArquivo.setDialogTitle("Selecione um destino para a planilha...");

						int opcaoSelecionada = janelaSelecaoDestinoArquivo.showSaveDialog(null);
						if (opcaoSelecionada == JFileChooser.APPROVE_OPTION) {
							String caminhoEscolhido = janelaSelecaoDestinoArquivo.getSelectedFile().getAbsolutePath();
							//TODO decomentar na aula 11
							//controller.gerarRelatorio(clientes, caminhoEscolhido);
						}
					}
				});
				
				btnAvancarPagina_1 = new JButton("Voltar");
				btnAvancarPagina_1.setEnabled(false);
				add(btnAvancarPagina_1, "3, 12");
				this.add(btnGerarPlanilha, "2, 13, 2, 1");
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
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
				lblPaginacao.setText(paginaAtual + " / " + totalPaginas);
				btnVoltarPagina.setEnabled(paginaAtual > 1);
				btnAvancarPagina.setEnabled(paginaAtual < totalPaginas);
			}
		});
		add(btnAvancarPagina, "5, 12");
		
		lblPaginacao = new JLabel("0 / 0");
		lblPaginacao.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblPaginacao, "4, 12, fill, center");
		
				btnEditar = new JButton("Editar");
				btnEditar.setEnabled(false);
				this.add(btnEditar, "5, 13, fill, default");
		
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

	//Torna o btnEditar acessível externamente à essa classe
	public JButton getBtnEditar() {
		return this.btnEditar;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}
}

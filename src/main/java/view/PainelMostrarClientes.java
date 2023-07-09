package view;

import java.awt.Font;
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
import model.exception.ErroConsultarException;
import model.exception.ErroExcluirException;
import model.seletor.ClienteSeletor;
import model.util.FormatadorData;
import model.vo.Cliente;

public class PainelMostrarClientes extends JPanel {
	private static final Integer USUARIO = 1;
	private JTable tblClientes;
	private ArrayList<Cliente> clientes;
	private String[] nomesColunas = { "#IdCliente", "Nome Cliente", "CPF", "Data Nascimento", "Data Cadastro",
			"Tipo Usuario", "Nome Usuario" };
	private JTextField txtNome;
	private MaskFormatter mascaraCpf;
	private JFormattedTextField txtCPF;

	// componentes externos -> dependência "LGoodDatePicker" foi adicionada no
	// pom.xml
	private DatePicker dtNascimentoInicial;
	private DatePicker dtNascimentoFinal;
	private JButton btnEditar;
	private JButton btnBuscar;
	private JButton btnGerarPlanilha;
	private JButton btnExcluir;
	private JLabel lblCpf;
	private JLabel lblNome;
	private JLabel lblDataNascimentoDe;
	private JLabel lblAte;
	private JLabel lblPaginacao;
	private PainelCadastroCliente painelEditar;
	private PainelCadastroCliente painelCadastroCliente;

	private ClienteController clienteController = new ClienteController();
	private Cliente clienteSelecionado;
	private Cliente cliente;

	// Atributos para a PAGINAÇÃO
	private final int TAMANHO_PAGINA = 5;
	private int paginaAtual = 1;
	private int totalPaginas = 0;
	private JButton btnVoltarPagina;
	private JButton btnAvancarPagina;
	private JLabel lblMostrarClientes;
	private JButton btnAvancarPagina_1;
	private ClienteSeletor seletor = new ClienteSeletor();

	private void limparTabelaClientes() {
		tblClientes.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
	}

	private void atualizarTabelaClientes() {
		this.limparTabelaClientes();
		DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();

		for (Cliente c : clientes) {
			Object[] novaLinhaDaTabela = new Object[7];
			novaLinhaDaTabela[0] = c.getIdCliente();
			novaLinhaDaTabela[1] = c.getNomeCliente();
			novaLinhaDaTabela[2] = c.getCpf();
			novaLinhaDaTabela[3] = FormatadorData.formatarDataParaTela(c.getDataNascimento());
			novaLinhaDaTabela[4] = FormatadorData.formataLocalDateTimeParaTela(c.getDataCadastro());
			if (c.getTipoUsuario() == USUARIO) {
				novaLinhaDaTabela[5] = "USUARIO";
			} else {
				novaLinhaDaTabela[5] = "ADM";
			}

			novaLinhaDaTabela[6] = c.getNomeUsuario();

			model.addRow(novaLinhaDaTabela);

		}
	}

	public PainelMostrarClientes() {
		setBounds(100, 100, 610, 650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("61px"),
				ColumnSpec.decode("154px"),
				ColumnSpec.decode("center:101px"),
				ColumnSpec.decode("141px"),
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("29px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("30px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("35px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("234px"),
				RowSpec.decode("22px"),
				RowSpec.decode("23px"),
				RowSpec.decode("33px"),}));

		tblClientes = new JTable();
		tblClientes.setFillsViewportHeight(true);
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

		lblMostrarClientes = new JLabel("Mostrar de Clientes");
		lblMostrarClientes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMostrarClientes.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblMostrarClientes, "2, 2, 5, 1");

		lblCpf = new JLabel("CPF:");
		this.add(lblCpf, "2, 6, fill, center");
		txtCPF = new JFormattedTextField(mascaraCpf);
		this.add(txtCPF, "4, 6, 3, 1, fill, default");
		txtCPF.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClienteController controller = new ClienteController();
				Integer idCliente = null;
				try {
					clientes = controller.listarTodosClientes();
				} catch (ErroConsultarException ex) {
					JOptionPane.showMessageDialog(null, ex);
				}

				atualizarTabelaClientes();

			}
		});
		this.add(btnBuscar, "5, 12, fill, default");
		this.add(tblClientes, "2, 14, 5, 2, fill, fill");

		lblNome = new JLabel("Nome:");
		this.add(lblNome, "2, 4, fill, center");

		txtNome = new JTextField();
		this.add(txtNome, "4, 4, 3, 1, fill, default");
		txtNome.setColumns(10);

		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setValueContainsLiteralCharacters(false);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		lblDataNascimentoDe = new JLabel("Data de dascimento. De:");
		this.add(lblDataNascimentoDe, "2, 8, 2, 1, fill, top");

		dtNascimentoInicial = new DatePicker();
		this.add(dtNascimentoInicial, "4, 8, 3, 1, fill, default");

		lblAte = new JLabel("Até:");
		this.add(lblAte, "2, 10, 2, 1, fill, top");

		dtNascimentoFinal = new DatePicker();
		this.add(dtNascimentoFinal, "4, 10, 3, 1, fill, default");

		btnGerarPlanilha = new JButton("Gerar Planilha");
		btnGerarPlanilha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser janelaSelecaoDestinoArquivo = new JFileChooser();
				janelaSelecaoDestinoArquivo.setDialogTitle("Selecione um destino para a planilha...");

				int opcaoSelecionada = janelaSelecaoDestinoArquivo.showSaveDialog(null);
				if (opcaoSelecionada == JFileChooser.APPROVE_OPTION) {
					String destinoArquivo = janelaSelecaoDestinoArquivo.getSelectedFile().getAbsolutePath();
					clienteController = new ClienteController();
					clienteController.exportarDadosController(clientes, destinoArquivo);
				}
			}
		});

		btnAvancarPagina = new JButton("Avançar>>");
		btnAvancarPagina.setEnabled(false);
		btnAvancarPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paginaAtual++;
				buscarClientesComFiltros();
				lblPaginacao.setText(paginaAtual + " / " + totalPaginas);
				btnVoltarPagina.setEnabled(paginaAtual > 1);
				btnAvancarPagina.setEnabled(paginaAtual < totalPaginas);
			}
		});

		btnVoltarPagina = new JButton("<< Voltar");
		btnVoltarPagina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paginaAtual--;
				buscarClientesComFiltros();
				lblPaginacao.setText(paginaAtual + " / " + totalPaginas);
				btnVoltarPagina.setEnabled(paginaAtual > 1);
				btnAvancarPagina.setEnabled(paginaAtual < totalPaginas);
			}
		});
		btnVoltarPagina.setEnabled(false);
		add(btnVoltarPagina, "3, 16");
		add(btnAvancarPagina, "5, 16");

		lblPaginacao = new JLabel("0 / 0");
		lblPaginacao.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblPaginacao, "4, 16, fill, center");

		btnExcluir = new JButton("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int opcaoSelecionada = JOptionPane.showConfirmDialog(null,
						"Confirma a exclusão do cliente selecionado?");

				if (opcaoSelecionada == JOptionPane.YES_OPTION) {

					try {
						clienteController.excluirCliente(clienteSelecionado);
					} catch (ErroExcluirException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ErroConsultarException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // .excluir(clienteSelecionado.getId());
					JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso");
					try {
						clientes = (ArrayList<Cliente>) clienteController.listarTodosClientes();
					} catch (ErroConsultarException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // .consultarTodos();
					atualizarTabelaClientes();

				}
			}
		});
		this.add(btnExcluir, "3, 17");

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        painelCadastroCliente = new PainelCadastroCliente(clienteSelecionado);
		        painelCadastroCliente.preencherCampos(clienteSelecionado);
		        
		        JOptionPane.showMessageDialog(null, painelCadastroCliente, "Editar Cliente", JOptionPane.PLAIN_MESSAGE);
		    }
		});

			
		
		btnEditar.setEnabled(false);
		this.add(btnEditar, "5, 17, fill, default");

		atualizarQuantidadePaginas();
	}

	private void atualizarQuantidadePaginas() {
		// Cálculo do total de páginas (poderia ser feito no backend)
		int totalRegistros = clienteController.contarTotalRegistrosComFiltros(seletor);

		// QUOCIENTE da divisão inteira
		totalPaginas = totalRegistros / TAMANHO_PAGINA;

		// RESTO da divisão inteira
		if (totalRegistros % TAMANHO_PAGINA > 0) {
			totalPaginas++;
		}

		lblPaginacao.setText(paginaAtual + " / " + totalPaginas);
	}

	protected void buscarClientesComFiltros() {
		seletor = new ClienteSeletor();
		seletor.setLimite(TAMANHO_PAGINA);
		seletor.setPagina(paginaAtual);
		seletor.setNome(txtNome.getText());

		String cpfSemMascara;
		try {
			cpfSemMascara = (String) mascaraCpf.stringToValue(txtCPF.getText());
			seletor.setCpf(cpfSemMascara);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}
		//
		seletor.setDataNascimentoInicial(dtNascimentoInicial.getDate());
		seletor.setDataNascimentoFinal(dtNascimentoFinal.getDate());
		clientes = (ArrayList<Cliente>) clienteController.consultarComFiltros(seletor);
		atualizarTabelaClientes();
		atualizarQuantidadePaginas();
	}

	// Torna o btnEditar acessível externamente à essa classe
	public JButton getBtnEditar() {
		return this.btnEditar;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}
}

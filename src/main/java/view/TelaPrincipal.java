package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class TelaPrincipal {

	private JFrame frmTelaPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frmTelaPrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTelaPrincipal = new JFrame();
		frmTelaPrincipal.setTitle("Tela Principal");
		frmTelaPrincipal.setBounds(100, 100, 450, 490);
		frmTelaPrincipal.setLocationRelativeTo(null); // Centralizar na tela
		frmTelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmTelaPrincipal.setJMenuBar(menuBar);

		JMenu mnListas = new JMenu("Listas");
		menuBar.add(mnListas);

		JMenuItem mntmCadastrarLista = new JMenuItem("Cadastrar Listas");
		mntmCadastrarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelCadastroListas painelCadastroLista = new PainelCadastroListas();
				frmTelaPrincipal.setContentPane(painelCadastroLista);
				frmTelaPrincipal.setTitle("Cadastro de Lista");
				frmTelaPrincipal.setBounds(100, 100, 450, 490);
				frmTelaPrincipal.setLocationRelativeTo(null);
				frmTelaPrincipal.revalidate();
			}
		});
		mnListas.add(mntmCadastrarLista);

		JMenuItem mntmMostrarListas = new JMenuItem("Mostrar Listas");
		mntmMostrarListas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelMostrarListas painelMostrarListas = new PainelMostrarListas();
				frmTelaPrincipal.setContentPane(painelMostrarListas);
				frmTelaPrincipal.setTitle("Mostrar Listas");
				frmTelaPrincipal.setBounds(100, 100, 450, 490);
				frmTelaPrincipal.setLocationRelativeTo(null);
				frmTelaPrincipal.revalidate();
			}
		});
		mnListas.add(mntmMostrarListas);

		JMenu mnSobre = new JMenu("Sobre");
		menuBar.add(mnSobre);

		JMenuItem mntmSobreNos = new JMenuItem("Sobre Nós");
		mntmSobreNos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "Software pensado e devenvolvido por:\n - Guilherme Caon \n- Marcus Ferreira\n- Vinicius Alves\n";
				JOptionPane.showMessageDialog(null, message, "Sobre nós", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnSobre.add(mntmSobreNos);

		JMenu mnAdm = new JMenu("Adm");
		menuBar.add(mnAdm);

		JMenuItem mntmCadastrarCliente = new JMenuItem("Cadastrar Cliente");
		mnAdm.add(mntmCadastrarCliente);
		mntmCadastrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelCadastroCliente painelCadastro = new PainelCadastroCliente();
				frmTelaPrincipal.setContentPane(painelCadastro);
				frmTelaPrincipal.setTitle("Cadastrar Cliente");
				frmTelaPrincipal.setBounds(100, 100, 450, 490);
				frmTelaPrincipal.setLocationRelativeTo(null);
				frmTelaPrincipal.revalidate();
			}
		});

		JMenuItem mntmMostrarClientes = new JMenuItem("Mostrar Clientes");
		mnAdm.add(mntmMostrarClientes);
		
				JMenuItem mntmCadastroProdutos = new JMenuItem("CRUD de produtos");
				menuBar.add(mntmCadastroProdutos);
				mntmCadastroProdutos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PainelCadastroProdutosAdministrador painelCadastroProdutosAdministrador = new PainelCadastroProdutosAdministrador();
						frmTelaPrincipal.setContentPane(painelCadastroProdutosAdministrador);
						frmTelaPrincipal.setTitle("CRUD de Produtos");
						frmTelaPrincipal.setBounds(100, 100, 450, 490);
						frmTelaPrincipal.setLocationRelativeTo(null);
						frmTelaPrincipal.revalidate();

					}
				});
		mntmMostrarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelMostrarClientes painelMostrarClientes = new PainelMostrarClientes();
				frmTelaPrincipal.setContentPane(painelMostrarClientes);
				frmTelaPrincipal.setTitle("Listagem de Clientes");
				frmTelaPrincipal.setBounds(100, 100, 450, 490);
				frmTelaPrincipal.setLocationRelativeTo(null);
				frmTelaPrincipal.revalidate();
			}
		});
		frmTelaPrincipal.getContentPane().setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblNewLabel = new JLabel("Bem vindo ao sistema, navegue usando o MenuBar acima");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frmTelaPrincipal.getContentPane().add(lblNewLabel, "4, 8, center, center");
	}
	
}

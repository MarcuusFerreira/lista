package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.exception.ErroConsultarException;
import model.vo.Cliente;

public class PainelTelaPrincipal extends JPanel {

	private PainelMostrarClientes painelMostrarClientes;
	private PainelCadastroCliente painelCadastro;
	private PainelMostrarListas painelMostrarListas;
	private PainelCadastroListas painelCadastroLista;
	private DialogoSistema dialogo;
	private JMenuItem mntmCadastrarCliente;
	private JMenuItem mntmMostrarClientes;
	private JLabel lblNewLabel;
	private JMenu mnAdm;
	private JMenuItem mntmSobreNos;
	private JMenu mnSobre;
	private JMenuItem mntmMostrarListas;
	private JMenuItem mntmCadastrarLista;
	private JMenuBar menuBar;
	private JMenu mnListas;
	private JMenu mnSair;
	private JMenuItem mntmSair;
	private JMenuItem mntmIrsCompras;

	public PainelTelaPrincipal(Cliente cliente) {
		initialize(cliente);
	}

	private void initialize(Cliente cliente) {
		setLayout(new BorderLayout());

		menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		mnListas = new JMenu("Listas");
		menuBar.add(mnListas);

		mntmCadastrarLista = new JMenuItem("Cadastrar Listas");
		mntmCadastrarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelCadastroLista = new PainelCadastroListas();
				add(painelCadastroLista);
				painelCadastroLista.setVisible(true);
				revalidate();
			}
		});
		mnListas.add(mntmCadastrarLista);

		mntmMostrarListas = new JMenuItem("Mostrar Listas");
		mntmMostrarListas.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				painelMostrarListas = new PainelMostrarListas();
				add(painelMostrarListas);
				painelMostrarListas.setVisible(true);
				revalidate();
				
			}
		});
		mnListas.add(mntmMostrarListas);

		mntmIrsCompras = new JMenuItem("Ir às compras");
		mntmIrsCompras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TelaMobile telaMobile = new TelaMobile();
					telaMobile.setVisible(true);
					telaMobile.setAlwaysOnTop(true);
//		            SwingUtilities.getWindowAncestor(PainelTelaPrincipal.this).setVisible(false);
				} catch (ErroConsultarException ex) {
					ex.printStackTrace();
				}
			}
		});
		mnListas.add(mntmIrsCompras);

		mnSobre = new JMenu("Sobre");
		menuBar.add(mnSobre);

		mntmSobreNos = new JMenuItem("Sobre Nós");
		mntmSobreNos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "Software pensado e devenvolvido por:\n - Guilherme Caon \n- Marcus Ferreira\n- Vinicius Alves\n";
				JOptionPane.showMessageDialog(null, message, "Sobre nós", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnSobre.add(mntmSobreNos);

		mnAdm = new JMenu("Adm");
		menuBar.add(mnAdm);

		mntmCadastrarCliente = new JMenuItem("Cadastrar Cliente");
		mnAdm.add(mntmCadastrarCliente);
		mntmCadastrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelCadastroCliente painelCadastro = new PainelCadastroCliente();
				painelCadastro.setSize(610, 650);
			    
			}
		});

		mntmMostrarClientes = new JMenuItem("Mostrar Clientes");
		mnAdm.add(mntmMostrarClientes);
		mntmMostrarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelMostrarClientes = new PainelMostrarClientes();
				setVisible(false);
				painelMostrarClientes.setVisible(true);
				revalidate();
			}
		});

		mnSair = new JMenu("Sair");
		menuBar.add(mnSair);

		mntmSair = new JMenuItem("Sair");
		mnSair.add(mntmSair);
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogo = new DialogoSistema("Tem certeza que deseja sair do sistema?");
				dialogo.setVisible(true);
			}
		});

		lblNewLabel = new JLabel("Bem vindo ao sistema, navegue usando o MenuBar acima");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, BorderLayout.CENTER);
	}

}

package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.vo.Cliente;

public class PainelTelaPrincipal extends JPanel {

	
	public PainelTelaPrincipal(Cliente cliente) {
		initialize(cliente);
	}

	private void initialize(Cliente cliente) {
		setLayout(new BorderLayout());

		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);

		JMenu mnListas = new JMenu("Listas");
		menuBar.add(mnListas);

		JMenuItem mntmCadastrarLista = new JMenuItem("Cadastrar Listas");
		mntmCadastrarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelCadastroListas painelCadastroLista = new PainelCadastroListas();
				setVisible(false);
				painelCadastroLista.setVisible(true);
			}
		});
		mnListas.add(mntmCadastrarLista);

		JMenuItem mntmMostrarListas = new JMenuItem("Mostrar Listas");
		mntmMostrarListas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelMostrarListas painelMostrarListas = new PainelMostrarListas();
				setVisible(false);
				painelMostrarListas.setVisible(true);
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
				setVisible(false);
				painelCadastro.setVisible(true);
			}
		});

		JMenuItem mntmMostrarClientes = new JMenuItem("Mostrar Clientes");
		mnAdm.add(mntmMostrarClientes);
		mntmMostrarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelMostrarClientes painelMostrarClientes = new PainelMostrarClientes();
				setVisible(false);
				painelMostrarClientes.setVisible(true);
			}
		});

		JLabel lblNewLabel = new JLabel("Bem vindo ao sistema, navegue usando o MenuBar acima");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, BorderLayout.CENTER);
	}
	
}

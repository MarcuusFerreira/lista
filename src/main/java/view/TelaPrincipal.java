package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import model.exception.ErroConsultarException;
import model.vo.Cliente;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {
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
	private TelaMobile telaMobile;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param cliente 
	 */
	public TelaPrincipal(Cliente cliente) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Listas");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Cadastrar Listas");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelCadastroLista = new PainelCadastroListas();
				setContentPane(painelCadastroLista);
				revalidate();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Mostrar Listas");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelMostrarListas = new PainelMostrarListas();
				setContentPane(painelMostrarListas);
				revalidate();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Ir às Compras");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					telaMobile = new TelaMobile(cliente);
					telaMobile.setVisible(true);
					telaMobile.setAlwaysOnTop(true);
					telaMobile.revalidate();
//		            SwingUtilities.getWindowAncestor(PainelTelaPrincipal.this).setVisible(false);
				} catch (ErroConsultarException ex) {
					ex.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);

		JMenu mnNewMenu_1 = new JMenu("Sobre");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Sobre Nós");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "Software pensado e devenvolvido por:\n - Guilherme Caon \n- Marcus Ferreira\n- Vinicius Alves\n";
				JOptionPane.showMessageDialog(null, message, "Sobre nós", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);

		JMenu mnNewMenu_2 = new JMenu("Adm");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Cadastrar Cliente");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelCadastro = new PainelCadastroCliente();
				setContentPane(painelCadastro);
				revalidate();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Mostrar Clientes");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelMostrarClientes = new PainelMostrarClientes();
				setContentPane(painelMostrarClientes);
				revalidate();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_5);

		JMenu mnNewMenu_3 = new JMenu("Sair");
		menuBar.add(mnNewMenu_3);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Sair");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogo = new DialogoSistema("Tem certeza que deseja sair do sistema?");
				dialogo.setVisible(true);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_6);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Bem vindo ao sistema, navegue usando o MenuBar acima");
		lblNewLabel.setBounds(149, 181, 367, 14);
		contentPane.add(lblNewLabel);
	}
}

package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class TelaPrincipal {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 490);
		frame.setLocationRelativeTo(null); // Centralizar na tela
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnUsuarios = new JMenu("Usuários");
		menuBar.add(mnUsuarios);
		
		JMenuItem mntmCadastrarUsuario = new JMenuItem("Cadastrar Usuário");
		mntmCadastrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelCadastroUsuario painelCadastro = new PainelCadastroUsuario();
				frame.setContentPane(painelCadastro);
				frame.setBounds(100, 100, 450, 490);
				frame.setLocationRelativeTo(null);
				frame.revalidate();
			}
		});
		mnUsuarios.add(mntmCadastrarUsuario);
		
		JMenuItem mntmMostrarUsuarios = new JMenuItem("Mostrar Usuários");
		mntmMostrarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			PainelMostrarClientes painelMostrarClientes = new PainelMostrarClientes();
			frame.setContentPane(painelMostrarClientes);
			frame.setBounds(100, 100, 450, 490);
			frame.setLocationRelativeTo(null);
			frame.revalidate();
			}
		});
		mnUsuarios.add(mntmMostrarUsuarios);
		
		JMenu mnListas = new JMenu("Listas");
		menuBar.add(mnListas);
		
		JMenuItem mntmCadastrarLista = new JMenuItem("Cadastrar Lista");
		mntmCadastrarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PainelCadastroListas painelCadastroLista = new PainelCadastroListas();
				frame.setContentPane(painelCadastroLista);
				frame.setBounds(100, 100, 450, 490);
				frame.setLocationRelativeTo(null);
				frame.revalidate();
			}
		});
		mnListas.add(mntmCadastrarLista);
		
		JMenuItem mntmMostrarListas = new JMenuItem("Mostrar Listas");
		mnListas.add(mntmMostrarListas);
		
		JMenu mnSobre = new JMenu("Sobre");
		menuBar.add(mnSobre);
		
		JMenuItem mntmSobreNos = new JMenuItem("Sobre Nós");
		mntmSobreNos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "Software pensado e devenvolvido por:\n - Guilherme Caon \n- Marcus Ferreira\n- Vinicius Alves";
		        JOptionPane.showMessageDialog(null, message, "Sobre nós", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnSobre.add(mntmSobreNos);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
	}

}

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class PainelListarProdutos extends JPanel {
	private JTextField txtNome;
	private JTextField txtSetor;
	private JLabel lblListarProdutos;
	private JTable tblProdutos;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JTextField txtMarca;
	private JLabel lblNome;
	private JLabel lblSetor;
	private JLabel lblMarca;
	private JLabel lblDtCadastro;
	private JButton btnBuscar;
	private JLabel lblAte;
	private String[] nomesColunas = {"Código", "Nome", "Setor", "Marca", "Data de Cadastro"};

	public PainelListarProdutos() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
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
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lblListarProdutos = new JLabel("Listar Produtos");
		add(lblListarProdutos, "15, 2, 4, 1, center, default");
		
		lblNome = new JLabel("Nome");
		add(lblNome, "8, 4");
		
		txtNome = new JTextField();
		add(txtNome, "9, 4, 8, 1, fill, default");
		txtNome.setColumns(10);
		
		lblDtCadastro = new JLabel("Data de Cadastro");
		add(lblDtCadastro, "22, 4");
		
		lblSetor = new JLabel("Setor");
		add(lblSetor, "8, 6");
		
		txtSetor = new JTextField();
		txtSetor.setColumns(10);
		add(txtSetor, "9, 6, 8, 1, fill, default");
		
		lblAte = new JLabel("até");
		lblAte.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblAte, "22, 6");
		
		lblMarca = new JLabel("Marca");
		add(lblMarca, "8, 8");
		
		txtMarca = new JTextField();
		txtMarca.setColumns(10);
		add(txtMarca, "9, 8, 8, 1, fill, default");
		
		btnEditar = new JButton("Editar");
		add(btnEditar, "12, 12");
		
		btnBuscar = new JButton("Buscar");
		add(btnBuscar, "16, 12");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		btnExcluir = new JButton("Excluir");
		add(btnExcluir, "20, 12");
		
		tblProdutos = new JTable();
		add(tblProdutos, "3, 14, 31, 6, fill, fill");

	}

}

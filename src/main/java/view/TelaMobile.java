package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import controller.ListaController;
import model.exception.ErroConsultarException;
import model.vo.Cliente;

public class TelaMobile extends JFrame {
	private JComboBox cbLista;
	private int idCliente = 1;

	public TelaMobile(Cliente cliente) throws ErroConsultarException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaMobile.class.getResource("/icon/android.png")));
		// Configurações básicas da janela
		setTitle("\"Galaxy S23\"");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(480, 550));
		setResizable(false);

		JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// Cria um modelo de tabela com três colunas
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] { { "1", "Lasanha", true }, { "2", "Macarrão", true }, { "4", "Arroz", true }, },
				new String[] { "Quantidade", "Item", "Já Pegou?" });

		// Cria uma tabela com o modelo de tabela
		JTable table = new JTable(model) {
			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 2) {
					return Boolean.class;
				}
				return super.getColumnClass(column);
			}
		};
		table.setFillsViewportHeight(true);

		// Define o renderizador personalizado para a coluna de quantidades
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setCellRenderer(new TableCellRenderer() {
			final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER); // Define o alinhamento central
				return component;
			}
		});

		// Define o renderizador personalizado para a coluna de itens
		columnModel.getColumn(1).setCellRenderer(new TableCellRenderer() {
			final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER); // Define o alinhamento central
				return component;
			}
		});

		// Define o renderizador personalizado para a coluna de checkboxes
		columnModel.getColumn(2).setCellRenderer(new TableCellRenderer() {
			final JCheckBox checkBox = new JCheckBox();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				checkBox.setSelected((Boolean) value);
				checkBox.setHorizontalAlignment(SwingConstants.CENTER); // Define o alinhamento central
				checkBox.setBackground(Color.WHITE); // Define a cor de fundo como branco
				return checkBox;
			}
		});

		// Adiciona a tabela ao painel
		JScrollPane scrollPane = new JScrollPane(table);
		painel.add(scrollPane);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		painel.add(btnVoltar);

		// Adiciona o painel à janela
		getContentPane().add(painel);

		ListaController listaController = new ListaController();
		cbLista = new JComboBox<>(listaController.consultarListasClientePorID(idCliente).toArray());
		cbLista.setSelectedIndex(-1);
		painel.add(cbLista);

		cbLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ListaController listaController = new ListaController();
				// String categoria = (String) cbLista.getSelectedItem();
				// Integer idCliente = null;
				//
				// idCliente = 1; // Integer.valueOf(txtIdUsuario.getText());
				//
				//// produtos =

			}
		});

		// Ajusta o tamanho da janela automaticamente
		pack();
		setLocationRelativeTo(null); // Centraliza a janela
	}
}

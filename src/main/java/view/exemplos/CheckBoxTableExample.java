package view.exemplos;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CheckBoxTableExample extends JFrame {
    private JTable table;

    public CheckBoxTableExample() {
        // Cria um modelo de tabela com três colunas
        DefaultTableModel model = new DefaultTableModel(new Object[][] {
                { "Item 1", true },
                { "Item 2", false },
                { "Item 3", true },
                { "Item 4", false }
        }, new String[] { "Item", "Selecionado" });

        // Cria o JTable com o modelo
        table = new JTable(model);

        // Define o renderer personalizado para a coluna "Selecionado"
        table.getColumnModel().getColumn(1).setCellRenderer(new CheckBoxRenderer());

        // Define o editor personalizado para a coluna "Selecionado"
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        // Adiciona a tabela a um JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        // Configura a janela
        setTitle("Exemplo de Tabela com CheckBox");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(scrollPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CheckBoxTableExample();
    }
}

class CheckBoxRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected((Boolean) value);
        return checkBox;
    }
}


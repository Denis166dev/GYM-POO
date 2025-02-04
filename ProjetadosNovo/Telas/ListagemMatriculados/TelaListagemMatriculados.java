import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaListagemMatriculados {

    public static void main(String[] args) {
        // Criar a janela principal (JFrame)
        JFrame frame = new JFrame("Listagem de Matriculados");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLocationRelativeTo((Component)null);
        frame.setResizable(false);
        DefaultTableModel model = new DefaultTableModel(
                new Object[][] {
                        {"01", "Gustavo", "20/01/2023"},
                        {"02", "Denis", "10/07/2024"},
                        {"03", "Paulo", "04/01/2025"}
                },
                new String[] {"MATRICULA", "NOME", "DATA DE CADASTRO"}
        ) {
            // Sobrescreve o método isCellEditable para impedir edição das células
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas as células não serão editáveis
            }
        };

        // Criar os nomes das colunas
        String[] colunas = {"MATRÍCULA", "NOME", "DATA DE MATRICULA"};

        // Criar a JTable com o modelo
        JTable tabela = new JTable(model);

        // Adicionar a tabela em um JScrollPane para permitir rolagem se necessário
        JScrollPane scrollPane = new JScrollPane(tabela);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Tornar a janela visível
        frame.setVisible(true);
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaListagemMatriculados {

    public static void main(String[] args) {
        // Criar a janela principal (JFrame)
        JFrame frame = new JFrame("Listagem de Matriculados");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo((Component)null);
        frame.setResizable(false);

        // Criar os dados (matrículas e nomes)
        String[][] dados = {
                {"12345", "João Silva", "01/01/2025"},
                {"67890", "Maria Oliveira", "01/01/2025"},
                {"11223", "Carlos Santos", "01/01/2025"},
                {"44556", "Ana Souza", "01/01/2025"},
                {"78901", "Pedro Costa", "01/01/2025"}
        };

        // Criar os nomes das colunas
        String[] colunas = {"MATRÍCULA", "NOME", "DATA DE MATRICULA"};

        // Criar o modelo da tabela
        DefaultTableModel model = new DefaultTableModel(dados, colunas);

        // Criar a JTable com o modelo
        JTable tabela = new JTable(model);

        // Adicionar a tabela em um JScrollPane para permitir rolagem se necessário
        JScrollPane scrollPane = new JScrollPane(tabela);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Tornar a janela visível
        frame.setVisible(true);
    }
}

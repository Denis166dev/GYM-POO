import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class TelaListagemMatriculados {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Listagem de Matriculados");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"MATRÍCULA", "NOME", "EMAIL", "TREINOS"}
        );

        JTable tabelaAlunos = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);
        frame.add(scrollPane, BorderLayout.CENTER);

        carregarTabelaAlunos(model);

        tabelaAlunos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tabelaAlunos.getSelectedRow() != -1) {
                int matricula = (int) model.getValueAt(tabelaAlunos.getSelectedRow(), 0);
                new TelaGerenciarTreinos(matricula);
            }
        });

        frame.setVisible(true);
    }

    public static void carregarTabelaAlunos(DefaultTableModel model) {
        model.setRowCount(0);
        String sql = "SELECT a.matricula, a.nome, a.email FROM alunos a " +
                "INNER JOIN usuarios u ON a.matricula = u.matricula WHERE u.tipo = 'aluno'";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("matricula"), rs.getString("nome"), rs.getString("email"), "Treinos"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class TelaGerenciarTreinos extends JFrame {
    private int matriculaAluno;
    private DefaultTableModel model;

    public TelaGerenciarTreinos(int matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
        setTitle("Treinos do Aluno");
        setSize(600, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Data Início", "Data Fim", "Tipo", "Ações", "Exercícios"});
        JTable tabelaTreinos = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabelaTreinos);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnAdicionar = new JButton("Adicionar Treino");
        btnAdicionar.addActionListener(e -> adicionarTreino());
        add(btnAdicionar, BorderLayout.SOUTH);

        carregarTreinos();

        setVisible(true);
    }

    private void carregarTreinos() {
        model.setRowCount(0);
        String sql = "SELECT * FROM treinos WHERE matricula_aluno = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, matriculaAluno);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getInt("id"), rs.getString("data_inicio"), rs.getString("data_fim"), rs.getString("tipo_treino"), "Editar", "Exercícios"});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void adicionarTreino() {
        String tipoTreino = JOptionPane.showInputDialog(this, "Digite o tipo de treino:");
        if (tipoTreino != null && !tipoTreino.isEmpty()) {
            String sql = "INSERT INTO treinos (matricula_aluno, data_inicio, data_fim, tipo_treino) VALUES (?, date('now'), date('now', '+30 days'), ?)";
            try (Connection conn = ConexaoDB.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, matriculaAluno);
                pstmt.setString(2, tipoTreino);
                pstmt.executeUpdate();
                carregarTreinos();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private JSONArray buscarExercicios(String tipoTreino) {
        try {
            String urlString = "https://exercisedb.p.rapidapi.com/exercises/bodyPart/" + tipoTreino;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-RapidAPI-Key", "SUA_CHAVE_API");
            conn.setRequestProperty("X-RapidAPI-Host", "exercisedb.p.rapidapi.com");

            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            return new JSONArray(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
}

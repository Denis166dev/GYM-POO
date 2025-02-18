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

        model = new DefaultTableModel(new Object[][]{}, new String[]{"Treino", "Exercícios", "Ações"});
        JTable tabelaTreinos = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabelaTreinos);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnAdicionar = new JButton("Adicionar Treino");
        btnAdicionar.addActionListener(e -> adicionarTreino());
        add(btnAdicionar, BorderLayout.SOUTH);

        carregarTreinos();

        tabelaTreinos.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tabelaTreinos.getSelectedRow() != -1) {
                String treino = (String) model.getValueAt(tabelaTreinos.getSelectedRow(), 0);
                new TelaExerciciosTreino(matriculaAluno, treino);
            }
        });

        setVisible(true);
    }

    private void carregarTreinos() {
        model.setRowCount(0);
        String sql = "SELECT DISTINCT tipo_treino FROM treinos WHERE matricula_aluno = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, matriculaAluno);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{rs.getString("tipo_treino"), "Ver Exercícios", "Adicionar Exercício"});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void adicionarTreino() {
        String tipoTreino = JOptionPane.showInputDialog(this, "Digite a divisão do treino (Ex: Treino A, Treino B):");
        if (tipoTreino != null && !tipoTreino.isEmpty()) {
            String sql = "INSERT INTO treinos (matricula_aluno, tipo_treino) VALUES (?, ?)";
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
}

class TelaExerciciosTreino extends JFrame {
    private int matriculaAluno;
    private String treino;

    public TelaExerciciosTreino(int matriculaAluno, String treino) {
        this.matriculaAluno = matriculaAluno;
        this.treino = treino;
        setTitle("Exercícios - " + treino);
        setSize(600, 400);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{"Exercício", "Descrição", "GIF"});
        JTable tabelaExercicios = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabelaExercicios);
        add(scrollPane, BorderLayout.CENTER);

        carregarExercicios(model);

        setVisible(true);
    }

    private void carregarExercicios(DefaultTableModel model) {
        model.setRowCount(0);
        JSONArray exercicios = buscarExercicios(treino);
        for (int i = 0; i < exercicios.length(); i++) {
            JSONObject exercicio = exercicios.getJSONObject(i);
            model.addRow(new Object[]{exercicio.getString("name"), exercicio.getString("instructions"), exercicio.getString("gifUrl")});
        }
    }

    private JSONArray buscarExercicios(String tipoTreino) {
        try {
            String urlString = "https://exercisedb.p.rapidapi.com/exercises/bodyPart/" + tipoTreino;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-RapidAPI-Key", "9da07424admshc09c7cd6f38ce47p15e9d1jsn0b682d7283c8");
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

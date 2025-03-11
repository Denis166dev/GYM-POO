import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public void inserirAluno(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (nome, email, numerocel, nascimento, sexo, plano, horario_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getEmail());
            pstmt.setString(3, aluno.getNumerocel());
            pstmt.setString(4, aluno.getNascimento().format(DateTimeFormatter.ISO_LOCAL_DATE)); // Formata LocalDate para String (yyyy-MM-dd)
            pstmt.setString(5, aluno.getSexo());
            pstmt.setString(6, aluno.getPlano());
            pstmt.setString(7, aluno.getHorarioCadastro().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.executeUpdate();
        }
    }
    public List<Aluno> listarAlunos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT matricula, nome, email, numerocel, nascimento, sexo, plano, horario_cadastro FROM alunos";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getInt("matricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setNumerocel(rs.getString("numerocel"));

                // Leitura CORRETA da data (já está em yyyy-MM-dd no banco)
                String dataNascStr = rs.getString("nascimento");
                if (dataNascStr != null && !dataNascStr.isEmpty()) {
                    try {
                        // Try parsing without explicit formatter
                        aluno.setNascimento(LocalDate.parse(dataNascStr));
                    } catch (DateTimeParseException e) {
                        System.err.println("Erro ao converter data de nascimento (listarAlunos): " + dataNascStr);
                        e.printStackTrace();
                    }
                }

                String horarioCadastroStr = rs.getString("horario_cadastro");
                if(horarioCadastroStr != null && !horarioCadastroStr.isEmpty()){
                    try{
                        // Keep the formatter for LocalDateTime parsing (yyyy-MM-dd HH:mm:ss)
                        aluno.setHorarioCadastro(LocalDateTime.parse(horarioCadastroStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    } catch (DateTimeParseException e){
                        System.err.println("Erro ao converter horario de cadastro: " + horarioCadastroStr);
                        e.printStackTrace();
                    }
                }

                alunos.add(aluno);
            }
        }
        return alunos;
    }
    public Aluno buscarPorMatricula(int matricula) throws SQLException {
        String sql = "SELECT nome, email, numerocel, nascimento, sexo, plano, horario_cadastro FROM alunos WHERE matricula = ?";
        Aluno aluno = null;

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, matricula);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    aluno = new Aluno();
                    aluno.setMatricula(matricula);
                    aluno.setNome(rs.getString("nome"));
                    aluno.setEmail(rs.getString("email"));
                    aluno.setNumerocel(rs.getString("numerocel"));

                    // --- Leitura CORRETA da data (sem formatação) ---
                    String dataNascStr = rs.getString("nascimento");
                    if (dataNascStr != null && !dataNascStr.isEmpty()) {
                        try {
                            // Try parsing without explicit formatter
                            aluno.setNascimento(LocalDate.parse(dataNascStr));
                        } catch (DateTimeParseException e) {
                            System.err.println("Erro ao converter data de nascimento (buscarPorMatricula): " + dataNascStr);
                            e.printStackTrace();
                        }
                    }
                    // --- FIM da leitura correta ---
                    String horarioCadastroStr = rs.getString("horario_cadastro");
                    if (horarioCadastroStr != null && !horarioCadastroStr.isEmpty()) {
                        try {
                            // Keep the formatter for LocalDateTime parsing (yyyy-MM-dd HH:mm:ss)
                            aluno.setHorarioCadastro(LocalDateTime.parse(horarioCadastroStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        } catch (DateTimeParseException e) { // DateTimeParseException
                            System.err.println("Erro ao converter horario de cadastro: " + horarioCadastroStr);
                            e.printStackTrace();
                        }
                    }

                    aluno.setSexo(rs.getString("sexo"));
                    aluno.setPlano(rs.getString("plano"));

                }
            }
        }
        return aluno;
    }

    public List<Exercicio> buscarExerciciosPorMatricula(int matricula) throws SQLException {
        List<Exercicio> exercicios = new ArrayList<>();
        String sql = "SELECT nome, carga, repeticoes, series, divisao_treino FROM exercicios WHERE matricula_aluno = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, matricula);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Exercicio exercicio = new Exercicio();
                    exercicio.setNome(rs.getString("nome"));
                    exercicio.setCarga(rs.getInt("carga"));
                    exercicio.setRepeticoes(rs.getInt("repeticoes"));
                    exercicio.setSeries(rs.getInt("series"));
                    exercicio.setDivisaoTreino(rs.getString("divisao_treino")); // NEW LINE - Retrieve and set divisao_treino
                    exercicios.add(exercicio);
                }


            }
            return exercicios;

        }
    }

    public List<Aluno> buscarMatriculasVencidas(int diasAviso) throws SQLException {
        List<Aluno> alunosVencidos = new ArrayList<>();
        String sql = "SELECT matricula, nome, email, numerocel, nascimento, sexo, plano, horario_cadastro FROM alunos";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            LocalDate hoje = LocalDate.now();

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(rs.getInt("matricula"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setNumerocel(rs.getString("numerocel"));
                String dataNascStr = rs.getString("nascimento");
                aluno.setSexo(rs.getString("sexo"));
                aluno.setPlano(rs.getString("plano"));

                if (dataNascStr != null && !dataNascStr.isEmpty()) {
                    try {
                        // Try parsing without explicit formatter
                        aluno.setNascimento(LocalDate.parse(dataNascStr));
                    } catch (DateTimeParseException e) {
                        System.err.println("Erro ao converter data de nascimento (listarAlunos): " + dataNascStr);
                        e.printStackTrace();
                    }
                }
                // --- Leitura e formatação do HORÁRIO DE CADASTRO ---
                String horarioCadastroStr = rs.getString("horario_cadastro");
                if(horarioCadastroStr != null && !horarioCadastroStr.isEmpty()){
                    try{
                        // Keep the formatter for LocalDateTime parsing (yyyy-MM-dd HH:mm:ss)
                        aluno.setHorarioCadastro(LocalDateTime.parse(horarioCadastroStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); //CORRETO
                    } catch (DateTimeParseException e){ //DateTimeParseException, não Exception
                        System.err.println("Erro ao converter horario de cadastro: " + horarioCadastroStr);
                        e.printStackTrace();
                    }
                }

                // --- Lógica de Vencimento ---
                if (aluno.getHorarioCadastro() != null) { // Verifica se não é nulo
                    LocalDate dataCadastro = aluno.getHorarioCadastro().toLocalDate(); //Pega a data do cadastro.
                    LocalDate dataVencimento = null;

                    // Calcular data de vencimento com base no plano
                    if ("Mensal".equalsIgnoreCase(aluno.getPlano())) {
                        dataVencimento = dataCadastro.plusMonths(1);
                    } else if ("Trimestral".equalsIgnoreCase(aluno.getPlano())) {
                        dataVencimento = dataCadastro.plusMonths(3);
                    } else if ("Semestral".equalsIgnoreCase(aluno.getPlano())) {
                        dataVencimento = dataCadastro.plusMonths(6);
                    } else if ("Anual".equalsIgnoreCase(aluno.getPlano())) {
                        dataVencimento = dataCadastro.plusYears(1);
                    }

                    if (dataVencimento != null) {
                        //Verifica se está vencido
                        if (hoje.isAfter(dataVencimento)) {
                            aluno.setStatusVencimento("Vencido"); // Setting status here
                            alunosVencidos.add(aluno);
                        } else {
                            long diasParaVencer = ChronoUnit.DAYS.between(hoje, dataVencimento);
                            if (diasParaVencer <= diasAviso) {
                                aluno.setStatusVencimento("Vence em " + diasParaVencer + " dias"); // Setting status here
                                alunosVencidos.add(aluno);
                            }
                        }
                    }
                }else{
                    System.err.println("Aluno com matrícula " + aluno.getMatricula() + " tem horario_cadastro NULL.");
                }

            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao buscar matrículas vencidas/próximas do vencimento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return alunosVencidos;
    }

    public static void salvarExerciciosAluno(int matriculaAluno, List<Exercicio> exercicios) throws SQLException {
        // First, clear existing exercises for this aluno (optional, but often desired for "re-assigning" трейноs)
        String deleteSql = "DELETE FROM exercicios WHERE matricula_aluno = ?";
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
            deletePstmt.setInt(1, matriculaAluno);
            deletePstmt.executeUpdate();
        }

        // Then, insert the new exercises
            String insertSql = "INSERT INTO exercicios (matricula_aluno, nome, repeticoes, series, divisao_treino) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {

            for (Exercicio exercicio : exercicios) {
                insertPstmt.setInt(1, matriculaAluno);
                insertPstmt.setString(2, exercicio.getNome());
                insertPstmt.setInt(3, exercicio.getRepeticoes());
                insertPstmt.setInt(4, exercicio.getSeries());
                insertPstmt.setString(5, exercicio.getDivisaoTreino()); // NEW LINE - Set divisao_treino parameter
                insertPstmt.executeUpdate(); // Execute insert for each exercise
            }
        }
    }
}
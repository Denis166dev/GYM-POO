import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            pstmt.setString(4, aluno.getNascimento().format(DateTimeFormatter.ISO_LOCAL_DATE));
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

                // Leitura correta da data (já está em yyyy-MM-dd)
                String dataNascStr = rs.getString("nascimento");
                if (dataNascStr != null && !dataNascStr.isEmpty()) {
                    try {
                        aluno.setNascimento(LocalDate.parse(dataNascStr)); // Sem formatter!
                    } catch (DateTimeParseException e) {
                        System.err.println("Erro ao converter data de nascimento (listarAlunos): " + dataNascStr);
                        e.printStackTrace();
                        // Lidar com o erro (opcional, dependendo da sua lógica)
                    }
                }

                aluno.setSexo(rs.getString("sexo"));
                aluno.setPlano(rs.getString("plano"));

                String horarioCadastroStr = rs.getString("horario_cadastro");
                if (horarioCadastroStr != null && !horarioCadastroStr.isEmpty()) {
                    try {
                        aluno.setHorarioCadastro(LocalDateTime.parse(horarioCadastroStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    } catch (DateTimeParseException e) { // DateTimeParseException, não Exception
                        System.err.println("Erro ao converter horario de cadastro: " + horarioCadastroStr);
                        e.printStackTrace();
                        // Lidar com o erro (opcional)
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
                            aluno.setNascimento(LocalDate.parse(dataNascStr)); // SEM FORMATTER!
                        } catch (DateTimeParseException e) {
                            System.err.println("Erro ao converter data de nascimento (buscarPorMatricula): " + dataNascStr);
                            e.printStackTrace();
                        }
                    }
                    // --- FIM da leitura correta ---
                    String horarioCadastroStr = rs.getString("horario_cadastro");
                    if (horarioCadastroStr != null && !horarioCadastroStr.isEmpty()) {
                        try {
                            aluno.setHorarioCadastro(LocalDateTime.parse(horarioCadastroStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        } catch (DateTimeParseException e) {
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
        String sql = "SELECT nome, carga, repeticoes, series FROM exercicios WHERE matricula_aluno = ?";

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
                    exercicios.add(exercicio);
                }
            }
        }
        return exercicios;
    }
}
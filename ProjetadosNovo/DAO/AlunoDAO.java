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
            pstmt.setString(4, String.format(String.valueOf(DateTimeFormatter.ISO_LOCAL_DATE))); // Formato yyyy-MM-dd
            pstmt.setString(5, aluno.getSexo());
            pstmt.setString(6, aluno.getPlano());
            pstmt.setString(7, String.format(String.valueOf(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
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
                // Tratar a data de nascimento (String -> LocalDate)
                String dataNascStr = rs.getString("nascimento");
                if (dataNascStr != null && !dataNascStr.isEmpty()) {
                    try {
                        aluno.setNascimento(LocalDate.parse(dataNascStr, DateTimeFormatter.ISO_LOCAL_DATE));
                    } catch (Exception e) {
                        // Lidar com erro de conversao, por exemplo, definindo uma data padrao ou registrando o erro.
                        System.err.println("Erro ao converter data de nascimento: " + dataNascStr);
                        e.printStackTrace();

                    }
                }
                aluno.setSexo(rs.getString("sexo"));
                aluno.setPlano(rs.getString("plano"));
                String horarioCadastroStr = rs.getString("horario_cadastro");
                if(horarioCadastroStr != null && !horarioCadastroStr.isEmpty()){
                    try{
                        aluno.setHorarioCadastro(LocalDateTime.parse(String.valueOf(LocalDateTime.parse(horarioCadastroStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))));
                    } catch (Exception e){
                        System.err.println("Erro ao converter data de nascimento: " + dataNascStr);
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

                    // --- CORREÇÃO DA DATA ---
                    String dataNascStr = rs.getString("nascimento");
                    if (dataNascStr != null && !dataNascStr.isEmpty()) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato correto
                            aluno.setNascimento(LocalDate.parse(dataNascStr, formatter));
                        } catch (DateTimeParseException e) {
                            System.err.println("Erro ao converter data de nascimento: " + dataNascStr);
                            e.printStackTrace();
                            // Lidar com o erro (ex: definir uma data padrão)
                        }
                    }
                    // --- FIM DA CORREÇÃO ---

                    String horarioCadastroStr = rs.getString("horario_cadastro");
                    if(horarioCadastroStr != null && !horarioCadastroStr.isEmpty()){
                        try{
                            aluno.setHorarioCadastro(LocalDateTime.parse(horarioCadastroStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        } catch (Exception e){
                            System.err.println("Erro ao converter data de nascimento: " + dataNascStr);
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
        String sql = "SELECT nome, Carga, repeticoes, series FROM exercicios WHERE matricula_aluno = ?";
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, matricula);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Exercicio exercicio = new Exercicio();
                    exercicio.setNome(rs.getString("nome"));
                    exercicio.setCarga(rs.getInt("carga"));       // <-- E AQUI
                    exercicio.setRepeticoes(rs.getInt("repeticoes"));
                    exercicio.setSeries(rs.getInt("series"));
                    exercicios.add(exercicio);
                }
            }
        }
        return exercicios;
    }
}
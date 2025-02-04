//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class SetupBD {
//    public static void createTables() {
//        String sql = """
//                    CREATE TABLE IF NOT EXISTS usuarios (
//                        matricula VARCHAR(20) PRIMARY KEY,
//                        senha VARCHAR(255) NOT NULL,
//                        tipo VARCHAR(10) CHECK (tipo IN ('Aluno', 'Instrutor'))
//                    );
//
//                    CREATE TABLE IF NOT EXISTS treinos (
//                        id IDENTITY PRIMARY KEY,
//                        matricula VARCHAR(20),
//                        descricao TEXT NOT NULL,
//                        FOREIGN KEY (matricula) REFERENCES usuarios(matricula)
//                    );
//                """;
//
//        try (Connection conn = ConexaoDB.getConnection();
//             Statement stmt = conn.createStatement()) {
//            stmt.execute(sql);
//            System.out.println("Tabelas criadas!");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        createTables();
//    }
//}

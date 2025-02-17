import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexaoDB {
    private static final String URL = "jjdbc:sqlite:projetados.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void desconectar(Connection conexao) {
        try {
            if (conexao != null) {
                conexao.close();
                System.out.println("Conexão com o banco de dados encerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao desconectar do banco de dados: " + e.getMessage());
        }
    }
}

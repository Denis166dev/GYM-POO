import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexaoDB {
    private static final String URL = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10760978";
    private static final String USER = "sql10760978";
    private static final String PASSWORD = "4ZIMLTdQtS";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
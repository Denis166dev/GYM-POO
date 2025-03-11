import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void inserirUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuarios (matricula, senha, tipo) VALUES (?, ?, ?)"; // SQL INSERT for Usuarios table
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuario.getMatricula());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setString(3, usuario.getTipo());
            pstmt.executeUpdate();
        }
    }
}
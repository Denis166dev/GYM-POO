import javax.swing.*;
import java.sql.*;

public class TelaLogin extends JFrame {
    private JTextField userText;
    private JPasswordField passwordText;
    private JCheckBox alunoCheckBox;
    private JCheckBox instrutorCheckBox;


    public TelaLogin() {
        setTitle("Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        addComponentes();
        setVisible(true);
    }

    private void addComponentes() {
        JLabel userLabel = new JLabel("Matrícula:");
        userLabel.setBounds(10, 20, 80, 25);
        add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        add(userText);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        add(passwordText);

        alunoCheckBox = new JCheckBox("Aluno");
        alunoCheckBox.setBounds(100, 80, 80, 25);
        add(alunoCheckBox);

        instrutorCheckBox = new JCheckBox("Instrutor");
        instrutorCheckBox.setBounds(180, 80, 100, 25);
        add(instrutorCheckBox);

        ButtonGroup group = new ButtonGroup();
        group.add(alunoCheckBox);
        group.add(instrutorCheckBox);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 120, 80, 25);
        add(loginButton);

        loginButton.addActionListener(e -> realizarLogin());
    }

    public void realizarLogin() {
        String matricula = userText.getText();
        String senha = new String(passwordText.getPassword());
        String tipo = alunoCheckBox.isSelected() ? "Aluno" : instrutorCheckBox.isSelected() ? "Instrutor" : "Nenhum";

        if (autenticarUsuario(matricula, senha, tipo)) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            MenuAcademia menuAcademia = new MenuAcademia();
            menuAcademia.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Senha ou email invalidos.");
        }
    }

    private boolean autenticarUsuario(String matricula, String senha, String tipo) {
        String sql = "SELECT * FROM Usuarios WHERE matricula = ? AND senha = ? AND tipo = ?";

        try (Connection conn = ConexaoDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, matricula);
            pstmt.setString(2, senha);
            pstmt.setString(3, tipo);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

public static void main(String[] args) {
    new TelaLogin();
}
}

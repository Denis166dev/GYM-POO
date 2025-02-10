import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ExibirCliente extends JFrame {
    /* Declaração de labels para exibir os dados */
    private JLabel lblNome = new JLabel("Nome: ");
    private JLabel lblEmail = new JLabel("Email: ");
    private JLabel lblGenero = new JLabel("Gênero: ");
    private JLabel lblPeso = new JLabel("Peso: ");
    private JLabel lblAltura = new JLabel("Altura: ");
    private JLabel lblIdade = new JLabel("Idade: ");
    private JLabel lblPlano = new JLabel("Plano: ");
    private JLabel lblHorarioMatricula = new JLabel("Data de Matrícula: ");
    private JButton Btntreino = new JButton("Treino");

    private JLabel txtNome = new JLabel("");
    private JLabel txtEmail = new JLabel("");
    private JLabel txtGenero = new JLabel("");
    private JLabel txtPeso = new JLabel("");
    private JLabel txtAltura = new JLabel("");
    private JLabel txtIdade = new JLabel("");
    private JLabel txtPlano = new JLabel("");
    private JLabel txtHorarioMatricula = new JLabel("");

    public ExibirCliente(int clienteId) {
        /* Configurações iniciais da janela */
        this.setTitle("Exibição de Cliente");
        this.setSize(1000, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        /* Configurar fontes */
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font textFont = new Font("Arial", Font.PLAIN, 14);

        lblNome.setFont(labelFont);
        lblEmail.setFont(labelFont);
        lblGenero.setFont(labelFont);
        lblPeso.setFont(labelFont);
        lblAltura.setFont(labelFont);
        lblIdade.setFont(labelFont);
        lblPlano.setFont(labelFont);
        lblHorarioMatricula.setFont(labelFont);

        txtNome.setFont(textFont);
        txtEmail.setFont(textFont);
        txtGenero.setFont(textFont);
        txtPeso.setFont(textFont);
        txtAltura.setFont(textFont);
        txtIdade.setFont(textFont);
        txtPlano.setFont(textFont);
        txtHorarioMatricula.setFont(textFont);

        /* Painel principal usando GridBagLayout */
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(10, 100, 10, 50);

        /* Adiciona os componentes ao painel principal */
        this.addComponent(panel, constraints, this.lblNome, this.txtNome, 0, 0);
        this.addComponent(panel, constraints, this.lblEmail, this.txtEmail, 1, 0);
        this.addComponent(panel, constraints, this.lblGenero, this.txtGenero, 0, 1);
        this.addComponent(panel, constraints, this.lblPeso, this.txtPeso, 0, 2);
        this.addComponent(panel, constraints, this.lblAltura, this.txtAltura, 1, 2);
        this.addComponent(panel, constraints, this.lblIdade, this.txtIdade, 1, 1);
        this.addComponent(panel, constraints, this.lblPlano, this.txtPlano, 2, 1);
        this.addComponent(panel, constraints, this.lblHorarioMatricula, this.txtHorarioMatricula, 2, 0);
        this.addComponent(panel,constraints, this.Btntreino, null, 2, 2);

        /* Preenche os dados a partir do banco de dados */
        this.exibirDados(clienteId);

        this.add(panel);
        this.setVisible(true);
    }

    /* Método para adicionar componentes ao painel com GridBagLayout */
    private void addComponent(JPanel panel, GridBagConstraints constraints, JComponent label, JComponent field, int gridx, int gridy) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        if (label != null) {
            panel.add(label, constraints);
            ++constraints.gridx;
        }

        if (field != null) {
            panel.add(field, constraints);
        }
    }

    /* Método para buscar e exibir os dados do cliente no banco de dados */
    private void exibirDados(int clienteId) {
        String sql = "SELECT nome, email, genero, peso, altura, idade, plano, horario_matricula FROM alunos WHERE id = ?";

        try (Connection connection = ConexaoDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, clienteId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                txtNome.setText(resultSet.getString("nome"));
                txtEmail.setText(resultSet.getString("email"));
                txtGenero.setText(resultSet.getString("genero"));
                txtPeso.setText(resultSet.getString("peso"));
                txtAltura.setText(resultSet.getString("altura"));
                txtIdade.setText(resultSet.getString("idade"));
                txtPlano.setText(resultSet.getString("plano"));
                txtHorarioMatricula.setText(resultSet.getString("horario_matricula"));
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao exibir dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        /* Exibe a tela com o ID de um cliente específico (por exemplo, ID = 1) */
        new ExibirCliente(10);
    }
}

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CadastroCliente extends JFrame {
    /* Declaração de labels, campos de texto e outros componentes */
    private JLabel lblNome = new JLabel("Nome:");
    private JTextField txtNome = new JTextField(15);
    private JLabel lblEmail = new JLabel("Email:");
    private JTextField txtEmail = new JTextField(15);
    private JLabel lblGenero = new JLabel("Gênero:");
    private JRadioButton rdFeminino = new JRadioButton("Feminino");
    private JRadioButton rdMasculino = new JRadioButton("Masculino");
    private ButtonGroup grupoGenero = new ButtonGroup();
    private JLabel lblPeso = new JLabel("Peso:");
    private JTextField txtPeso = new JTextField(5);
    private JLabel lblAltura = new JLabel("Altura:");
    private JTextField txtAltura = new JTextField(5);
    private JLabel lblNascimento = new JLabel("Nascimento:");
    private JTextField txtNascimento = new JTextField(5);
    private JLabel lblLista = new JLabel("Plano:");
    private JButton btnCadastro = new JButton("Cadastrar");
    private JComboBox<String> comboBox = new JComboBox<>(new String[] {"Mensal","Semestral","Anual"});

    public CadastroCliente() {
        /* Configurações iniciais da janela */
        this.setTitle("Cadastro de Cliente");
        this.setSize(800, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo((Component)null);
        this.setResizable(false);

        /* Configurar fontes */
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        lblNome.setFont(labelFont);
        lblEmail.setFont(labelFont);
        lblGenero.setFont(labelFont);
        lblPeso.setFont(labelFont);
        lblAltura.setFont(labelFont);
        lblNascimento.setFont(labelFont);
        lblLista.setFont(labelFont);
        txtNome.setFont(fieldFont);
        txtEmail.setFont(fieldFont);
        txtPeso.setFont(fieldFont);
        txtAltura.setFont(fieldFont);
        txtNascimento.setFont(fieldFont);
        rdFeminino.setFont(fieldFont);
        rdMasculino.setFont(fieldFont);
        comboBox.setFont(fieldFont);
        btnCadastro.setFont(buttonFont);

        /* Painel principal usando GridBagLayout */
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 20); // Adicionado maior espaçamento horizontal

        /* Adiciona os componentes ao painel principal */
        this.addComponent(panel, constraints, this.lblNome, this.txtNome, 0, 0);
        this.addComponent(panel, constraints, this.lblEmail, this.txtEmail, 0, 1);

        /* Painel para peso e altura na mesma célula */
        JPanel panelPesoAltura = new JPanel(new GridBagLayout());
        GridBagConstraints subConstraints = new GridBagConstraints();
        subConstraints.insets = new Insets(0, 5, 0, 5);

        subConstraints.gridx = 0;
        panelPesoAltura.add(lblPeso, subConstraints);

        subConstraints.gridx = 1;
        panelPesoAltura.add(txtPeso, subConstraints);

        subConstraints.gridx = 2;
        panelPesoAltura.add(lblAltura, subConstraints);

        subConstraints.gridx = 3;
        panelPesoAltura.add(txtAltura, subConstraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(panelPesoAltura, constraints);

        constraints.gridx = 2;
        constraints.gridwidth = 1;
        this.addComponent(panel, constraints, this.lblNascimento, this.txtNascimento, 2, 0);

        /* Painel para os botões de rádio do gênero */
        JPanel panelGenero = new JPanel(new GridBagLayout());
        GridBagConstraints generoConstraints = new GridBagConstraints();
        generoConstraints.insets = new Insets(0, 5, 0, 5);

        generoConstraints.gridx = 0;
        panelGenero.add(rdFeminino, generoConstraints);

        generoConstraints.gridx = 1;
        panelGenero.add(rdMasculino, generoConstraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        panel.add(lblGenero, constraints);

        constraints.gridx = 3;
        panel.add(panelGenero, constraints);

        this.grupoGenero.add(this.rdFeminino);
        this.grupoGenero.add(this.rdMasculino);

        constraints.gridx = 2;
        constraints.gridheight = 1;
        this.addComponent(panel, constraints, this.lblLista, this.comboBox, 2, 2);

        /* Posiciona o botão de cadastro no canto inferior direito */
        constraints.gridx = 4;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        constraints.insets = new Insets(20, 0, 0, 20);
        panel.add(btnCadastro, constraints);

        this.add(panel);
        this.setVisible(true);

        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrar(); // Chama o método cadastrar quando o botão é clicado
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /* Método utilitário para adicionar componentes ao painel com GridBagLayout */
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

    private void cadastrar() throws SQLException {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String sexo = rdFeminino.isSelected() ? "Feminino" : "Masculino";
        String peso = txtPeso.getText();
        String altura = txtAltura.getText();
        String nascimento = txtNascimento.getText();
        String plano = (String) comboBox.getSelectedItem();

        LocalDateTime horarioAtual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String horarioFormatado = horarioAtual.format(formatter);

        Connection connection = ConexaoDB.getConnection();
        try (connection) {

            Usuario usuario = new Usuario("senha_padrao", "aluno"); // Hash da senha aqui!!!
            Aluno aluno = new Aluno(nome, email, nascimento, sexo, Double.parseDouble(peso), Double.parseDouble(altura), plano, horarioFormatado);

            // 3. Inserir no banco de dados
            connection.setAutoCommit(false); // Iniciar transação

            try (PreparedStatement pstmt_usuarios = connection.prepareStatement("INSERT INTO usuarios (matricula, senha, tipo) VALUES (?, ?, ?)")) {
                pstmt_usuarios.setInt(1, usuario.getMatricula());
                pstmt_usuarios.setString(2, usuario.getSenha()); // Senha hasheada
                pstmt_usuarios.setString(3, usuario.getTipo());
                pstmt_usuarios.executeUpdate();
            }

            try (PreparedStatement pstmt_alunos = connection.prepareStatement("INSERT INTO alunos (matricula, nome, email, sexo, peso, altura, nascimento, plano, horario_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                pstmt_alunos.setInt(1, aluno.getMatricula());
                pstmt_alunos.setString(2, aluno.getNome());
                pstmt_alunos.setString(3, aluno.getEmail());
                pstmt_alunos.setString(4, aluno.getSexo());
                pstmt_alunos.setDouble(5, aluno.getPeso());
                pstmt_alunos.setDouble(6, aluno.getAltura());
                pstmt_alunos.setString(7, aluno.getNascimento());
                pstmt_alunos.setString(8, aluno.getPlano());
                pstmt_alunos.setString(9, aluno.getHorarioCadastro());
                pstmt_alunos.executeUpdate();
            }

            connection.commit(); // Finalizar transação
            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");

        } catch (SQLException ex) {
            try { connection.rollback(); } catch (SQLException rollbackException) { rollbackException.printStackTrace(); } // Em caso de erro, rollback
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private int gerarMatricula(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT MAX(matricula) FROM usuarios")) {

            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            } else {
                return 1; // Primeira matrícula
            }
        }
    }




//        String sql = "INSERT INTO alunos (nome, email, sexo, peso, altura, nascimento, plano, horario_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//
//        LocalDateTime horarioAtual = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String horarioFormatado = horarioAtual.format(formatter);
//
//        try (Connection connection = ConexaoDB.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setString(1, nome);
//            statement.setString(2, email);
//            statement.setString(3, sexo);
//            statement.setString(4, peso);
//            statement.setString(5, altura);
//            statement.setString(6, nascimento);
//            statement.setString(7, plano);
//            statement.setString(8, horarioFormatado);
//
//            statement.executeUpdate();
//
//            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Erro ao cadastrar aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        }
//    }
    public static void main(String[] args) {
        /* Cria e exibe a janela de cadastro */
        new CadastroCliente();
    }
}

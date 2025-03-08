// No pacote correto (ex: Telas.Cadastro)
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import javax.swing.text.MaskFormatter;


public class CadastroClienteNETBEANS extends javax.swing.JFrame {

    MaskFormatter mfdata;
    MaskFormatter mfTelefone;  // Nova máscara para telefone
    private AlunoDAO alunoDAO; // Instância do DAO

    public CadastroClienteNETBEANS() {
        try {
            mfdata = new MaskFormatter("##/##/####");
            mfTelefone = new MaskFormatter("(##) #####-####"); // Ex: (11) 99999-9999
        } catch (ParseException ex) {
            System.err.println("Erro ao criar máscara: " + ex.getMessage());
            ex.printStackTrace();
        }
        initComponents();
        alunoDAO = new AlunoDAO(); // Inicializa o DAO
        addListenerToEnviarButton();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        criarTabelaAlunos();
    }
    private void criarTabelaAlunos() {
        String sql = "CREATE TABLE IF NOT EXISTS alunos ("
                + "matricula INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT NOT NULL,"
                + "email TEXT NOT NULL,"
                + "numerocel TEXT,"  // Adicionei numerocel
                + "nascimento TEXT NOT NULL," // Formato: yyyy-MM-dd
                + "sexo TEXT,"
                + "plano TEXT," // Mensal, Semestral, Anual
                + "horario_cadastro TEXT" // Data e hora do cadastro
                + ");";

        try (Connection conn = ConexaoDB.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void addListenerToEnviarButton() {
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });
    }

    private void cadastrarCliente() {
        // 1. Coleta de Dados e Validação
        String nome = NomeTxtField.getText().trim(); // trim() remove espaços em branco
        String email = EmailTxtField.getText().trim();
        String telefone = TelefoneTxtField.getText().trim();
        String dataNascStr = dataNascimento.getText().trim();
        String sexo = MasculinojRadioButton.isSelected() ? "Masculino" : (FemininojRadioButton.isSelected() ? "Feminino" : null);
        String plano = "Mensal"; // Defina o plano, ou use um JComboBox, etc.
        LocalDateTime horarioCadastro = LocalDateTime.now(); //Pega data e hora atual

        // Validação (exemplo - pode ser expandida)
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || dataNascStr.isEmpty() || sexo == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação de email (exemplo simples)
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Email inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação e conversão da data de nascimento
        LocalDate dataNasc = null;
        try {
            DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataNasc = LocalDate.parse(dataNascStr, formatter);
            // Verifica se a data está no futuro
            if (dataNasc.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Data de nascimento no futuro!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida! Use o formato dd/MM/aaaa.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Criação do Objeto Aluno
        Aluno aluno = new Aluno(nome, email, telefone, dataNasc, sexo, plano, horarioCadastro);

        // 3. Inserção no Banco de Dados (usando o DAO)
        try {
            alunoDAO.inserirAluno(aluno);
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            limparCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprime o stack trace para debug
        }
    }


    private void limparCampos() {
        NomeTxtField.setText("");
        EmailTxtField.setText("");
        TelefoneTxtField.setText("");
        dataNascimento.setText("");
        buttonGroup1.clearSelection();
    }

    // ... restante do código (gerado pelo NetBeans) ...
    private void MasculinojRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {}
    private void dataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {}
    private void dataNascimentoFocusLost(java.awt.event.FocusEvent evt) {}

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroClienteNETBEANS().setVisible(true);
            }
        });
    }

    private void NomeTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {}

    // Variables declaration - do not modify
    private javax.swing.JTextField EmailTxtField;
    private javax.swing.JRadioButton FemininojRadioButton;
    private javax.swing.JRadioButton MasculinojRadioButton;
    private javax.swing.JTextField NomeTxtField;
    private javax.swing.JTextField TelefoneTxtField;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFormattedTextField dataNascimento;
    private javax.swing.JLabel datadeNascimentoLabel;
    private javax.swing.JLabel emaillabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nomelabel;
    private javax.swing.JLabel telefonelabel;
    // End of variables declaration

    private void initComponents() {

        buttonGroup1 = new ButtonGroup();
        jPanel1 = new JPanel();
        telefonelabel = new JLabel();
        datadeNascimentoLabel = new JLabel();
        NomeTxtField = new JTextField();
        EmailTxtField = new JTextField();
        TelefoneTxtField = new JFormattedTextField(mfTelefone); // Usando JFormattedTextField para telefone
        dataNascimento = new JFormattedTextField(mfdata);
        MasculinojRadioButton = new JRadioButton();
        FemininojRadioButton = new JRadioButton();
        jButton1 = new JButton();
        nomelabel = new JLabel();
        emaillabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Mantenha, mas DISPOSE_ON_CLOSE no construtor
        setBackground(new Color(0, 0, 0));
        setPreferredSize(new Dimension(520, 400));

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(520, 400));

        telefonelabel.setText("Telefone:");

        datadeNascimentoLabel.setText("Data de Nascimento:");

        NomeTxtField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                NomeTxtFieldActionPerformed(evt);
            }
        });

        dataNascimento.addFocusListener(new FocusAdapter() { // Adicionado FocusListener para validar a data
            public void focusLost(FocusEvent evt) {
                dataNascimentoFocusLost(evt);
            }

        });
        dataNascimento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dataNascimentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(MasculinojRadioButton);
        MasculinojRadioButton.setText("Masculino");
        MasculinojRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                MasculinojRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(FemininojRadioButton);
        FemininojRadioButton.setText("Feminino");

        jButton1.setText("Enviar");

        nomelabel.setText("Nome:");

        emaillabel.setText("Email:");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(51, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(135, 135, 135)
                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(nomelabel)
                                                        .addGap(77, 77, 77)
                                                        .addComponent(NomeTxtField, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(emaillabel)
                                                        .addGap(79, 79, 79)
                                                        .addComponent(EmailTxtField, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(telefonelabel)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(TelefoneTxtField, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(datadeNascimentoLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(dataNascimento, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(MasculinojRadioButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(FemininojRadioButton)))
                                .addGap(60, 60, 60))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(52, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(NomeTxtField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nomelabel))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(EmailTxtField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(emaillabel))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(TelefoneTxtField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(telefonelabel))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(datadeNascimentoLabel)
                                        .addComponent(dataNascimento, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(MasculinojRadioButton)
                                        .addComponent(FemininojRadioButton))
                                .addGap(28, 28, 28)
                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
        );

        pack();
    }
}
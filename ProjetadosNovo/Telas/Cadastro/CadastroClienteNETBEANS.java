import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.xml.stream.XMLStreamReader;

public class CadastroClienteNETBEANS extends javax.swing.JFrame {

    MaskFormatter mfdata;
    private XMLStreamReader jfData; // Possivelmente não é necessário, revise o uso

    public CadastroClienteNETBEANS() {
        try {
            mfdata = new MaskFormatter("##/##/####");
        } catch (ParseException ex) {
            System.out.println("Ocorreu um erro na criação da máscara");
        }
        initComponents();
        addListenerToEnviarButton(); // Adiciona o ActionListener aqui
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
        String nome = NomeTxtField.getText();
        String email = EmailTxtField.getText();
        String telefone = TelefoneTxtField.getText();
        String dataNascStr = dataNascimento.getText();
        String sexo = MasculinojRadioButton.isSelected() ? "Masculino" : (FemininojRadioButton.isSelected() ? "Feminino" : null);

        // Validação básica (pode ser aprimorada)
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || dataNascStr.isEmpty() || sexo == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNasc = null;
        try {
            dataNasc = sdf.parse(dataNascStr);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Conexão com o banco de dados (try-with-resources)
        try (Connection conn = ConexaoDB.getConnection()) {
            // Cria a tabela, se não existir

            // Inserção dos dados (PreparedStatement para segurança)
            String insertSQL = "INSERT INTO alunos (nome, email, numerocel, nascimento, sexo, plano) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, email);
                pstmt.setString(3, telefone);
                pstmt.setString(4, sdf.format(dataNasc)); // Formata a data para consistência
                pstmt.setString(5, sexo);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                limparCampos(); // Limpa os campos após o cadastro

            } catch (SQLException ex) {
                System.err.println("Erro ao inserir dados: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limparCampos() {
        NomeTxtField.setText("");
        EmailTxtField.setText("");
        TelefoneTxtField.setText("");
        dataNascimento.setText("");
        buttonGroup1.clearSelection(); // Desmarca os radio buttons
    }

    // ... restante do código ... (initComponents, etc.)
    private void MasculinojRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MasculinojRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MasculinojRadioButtonActionPerformed

    private void dataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataNascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataNascimentoActionPerformed

    private void dataNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dataNascimentoFocusLost
        //Validação da data: Se a data for inválida, exibe uma mensagem e limpa o campo.

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // Torna a validação mais estrita

        try {
            Date date = sdf.parse(dataNascimento.getText());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida. Use o formato dd/mm/aaaa.", "Erro", JOptionPane.ERROR_MESSAGE);
            dataNascimento.setText(""); // Limpa o campo
        }
    }//GEN-LAST:event_dataNascimentoFocusLost


    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroClienteNETBEANS().setVisible(true);
            }
        });
    }

    private void NomeTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NomeTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomeTxtFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    // End of variables declaration//GEN-END:variables

    private void initComponents() {

        buttonGroup1 = new ButtonGroup();
        jPanel1 = new JPanel();
        telefonelabel = new JLabel();
        datadeNascimentoLabel = new JLabel();
        NomeTxtField = new JTextField();
        EmailTxtField = new JTextField();
        TelefoneTxtField = new JTextField();
        dataNascimento = new JFormattedTextField(mfdata);
        MasculinojRadioButton = new JRadioButton();
        FemininojRadioButton = new JRadioButton();
        jButton1 = new JButton();
        nomelabel = new JLabel();
        emaillabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        dataNascimento.addFocusListener(new FocusAdapter() {
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
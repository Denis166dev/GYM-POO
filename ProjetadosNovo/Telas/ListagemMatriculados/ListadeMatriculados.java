import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class ListadeMatriculados extends JFrame {

    private JTable jTable1; // Declaração correta como campo da classe
    private AlunoDAO alunoDAO;

    public ListadeMatriculados() {
        alunoDAO = new AlunoDAO();
        initComponents();
        loadStudentData();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadStudentData() {
        try {
            List<Aluno> alunos = alunoDAO.listarAlunos();
            Object[][] data = new Object[alunos.size()][4]; // 4 colunas

            for (int i = 0; i < alunos.size(); i++) {
                Aluno aluno = alunos.get(i);
                data[i][0] = aluno.getNome();
                data[i][1] = aluno.getMatricula();
                // Formata a data de nascimento para exibição:
                data[i][2] = (aluno.getNascimento() != null) ? aluno.getNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
                // Formata o horário de cadastro para exibição
                data[i][3] = (aluno.getHorarioCadastro() != null) ? aluno.getHorarioCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "";
            }

            jTable1.setModel(new StudentTableModel(data, new String[]{"Nome", "Matrícula", "Data de Nascimento", "Data e Hora Cadastro"}));
            jTable1.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
            jTable1.addMouseListener(new TableMouseListener());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados dos alunos: " + e.getMessage(),
                    "Erro no Banco de Dados", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private class TableMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent evt) {
            int row = jTable1.rowAtPoint(evt.getPoint());
            int col = jTable1.columnAtPoint(evt.getPoint());
            if (col == 1) {
                Integer matricula = (Integer) jTable1.getValueAt(row, 1);
                TelaPrincipalAlunoNet telaAluno = new TelaPrincipalAlunoNet(matricula);
                telaAluno.setVisible(true);
            }
        }
    }

    private class StudentTableModel extends AbstractTableModel {
        private final Object[][] data;
        private final String[] columnNames;

        public StudentTableModel(Object[][] data, String[] columnNames) {
            this.data = data;
            this.columnNames = columnNames;
        }

        @Override
        public int getRowCount() { return data.length; }
        @Override
        public int getColumnCount() { return columnNames.length; }
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) { return data[rowIndex][columnIndex]; }
        @Override
        public String getColumnName(int column) { return columnNames[column]; }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 1) {
                return Integer.class;
            }
            return String.class;
        }
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
    }


    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            return this;
        }
    }
    // Método initComponents() CORRIGIDO (gerado pelo NetBeans, mas com a correção)
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(); //  <--  Apenas ATRIBUIÇÃO, sem redeclaração.

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //Aqui é definido o modelo da tabela, esse modelo apresenta o que vai ser exibido nela
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {  //Aqui define os nomes das colunas
                        "Nome", "Matricula", "Data de Nascimento", "Data e Hora Cadastro"
                }
        ) {
            //Aqui são definidos os tipos de dados das colunas
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            //Aqui é definido se as colunas podem ser editadas
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };
            //Aqui é definido o tipo de classe de cada coluna
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            //Aqui é definido se a coluna é editavel
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        //Aqui é definido se as colunas podem ser redimensionadas
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
                                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }


    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListadeMatriculados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListadeMatriculados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
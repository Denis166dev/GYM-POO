import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtribuirTreino extends javax.swing.JFrame {

    private MyTableModel tableModel;
    private int matriculaAluno;
    public javax.swing.JComboBox<String> tipostreinoJComboBox;
    private javax.swing.JButton btnatribuir;
    private javax.swing.JButton adicionarLinhaButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblslcnome;

    private AlunoDAO alunoDAO;

    public AtribuirTreino(int matricula) {
        this.matriculaAluno = matricula;
        alunoDAO = new AlunoDAO();
        tableModel = new MyTableModel(getDataForDay("A"), getColumnNames());
        initComponents();
        jTable1.setModel(tableModel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private String[] getColumnNames() {
        return new String[]{"Nome", "Repetições", "Séries"}; // Column Headers: Nome, Repetições, Séries (No Divisão)
    }

    private List<Object[]> getDataForDay(String day) {
        List<Object[]> data = new ArrayList<>();
        switch (day) {
            case "A":
                data.add(createExercicioRow("Supino Reto", 12, 3, "A"));
                data.add(createExercicioRow("Supino Inclinado com Halteres", 12, 3, "A"));
                data.add(createExercicioRow("Crucifixo Reto", 15, 3, "A"));
                break;
            case "B":
                data.add(createExercicioRow("Puxada Reto", 10, 3, "B"));
                data.add(createExercicioRow("Remada Alta", 10, 3, "B"));
                data.add(createExercicioRow("Rosca Concentrada", 12, 3, "B"));
                break;
            case "C":
                data.add(createExercicioRow("Agachamento", 8, 3, "C"));
                data.add(createExercicioRow("Leg Press", 12, 3, "C"));
                data.add(createExercicioRow("Cadeira Extensora", 15, 3, "C"));
                break;
            case "D":
                data.add(createExercicioRow("Desenvolvimento", 8, 3, "D"));
                data.add(createExercicioRow("Elevação Lateral", 12, 3, "D"));
                data.add(createExercicioRow("Triceps Pulley", 12, 3, "D"));
                break;
            case "E":
                data.add(createExercicioRow("Abdominal Supra", 20, 3, "E"));
                data.add(createExercicioRow("Abdominal Infra", 15, 3, "E"));
                data.add(createExercicioRow("Lombar", 15, 3, "E"));
                break;
            case "F":
                data.add(createExercicioRow("Cardio", null, 30, "F"));
                data.add(createExercicioRow("Alongamento", null, null, "F"));
                break;
            default:
                data.add(createExercicioRow("Descanso", 0, 0, "A"));
        }
        return data;
    }
    // --- Helper method to create Object[] for exercise data ---
    private Object[] createExercicioRow(String nome, Integer repeticoes, Integer series, String divisaoTreino) {
        Exercicio exercicio = new Exercicio();
        exercicio.setNome(nome);
        exercicio.setRepeticoes(repeticoes);
        exercicio.setSeries(series);
        exercicio.setDivisaoTreino(divisaoTreino); // Set division here!
        return new Object[]{nome, repeticoes, series}; // Return Object[] with 3 elements
    }


    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblslcnome = new javax.swing.JLabel();
        tipostreinoJComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnatribuir = new javax.swing.JButton();
        adicionarLinhaButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblslcnome.setFont(new java.awt.Font("Arial", 1, 14));
        lblslcnome.setText("Selecione o Dia:");

        tipostreinoJComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F" }));
        tipostreinoJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipostreinoJComboBoxActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null}
                },
                new String [] {
                        "Nome", "Repetições", "Séries"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnatribuir.setText("Atribuir");
        btnatribuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnatribuirActionPerformed(evt);
            }
        });

        adicionarLinhaButton.setText("Adicionar Linha");
        adicionarLinhaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarLinhaButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(lblslcnome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tipostreinoJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(adicionarLinhaButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnatribuir, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblslcnome)
                                        .addComponent(tipostreinoJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnatribuir)
                                        .addComponent(adicionarLinhaButton))
                                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void tipostreinoJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedDay = (String) tipostreinoJComboBox.getSelectedItem();
        List<Object[]> newData = getDataForDay(selectedDay);
        tableModel.setData(newData);
        tableModel.fireTableDataChanged();
    }

    private void btnatribuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnatribuirActionPerformed
        List<Exercicio> exerciciosParaSalvar = new ArrayList<>();
        MyTableModel model = (MyTableModel) jTable1.getModel();
        int rowCount = model.getRowCount();
        String selectedDivisaoTreino = (String) tipostreinoJComboBox.getSelectedItem();

        for (int i = 0; i < rowCount; i++) {
            String nomeExercicio = (String) model.getValueAt(i, 0);
            Integer repeticoes = null;
            Integer series = null;

            Object repValue = model.getValueAt(i, 1);
            if (repValue != null && !repValue.toString().isEmpty()) {
                repeticoes = Integer.parseInt(repValue.toString());
            }
            Object seriesValue = model.getValueAt(i, 2);
            if (seriesValue != null && !seriesValue.toString().isEmpty()) {
                series = Integer.parseInt(seriesValue.toString());
            }

            if (nomeExercicio != null && !nomeExercicio.isEmpty()) {
                Exercicio exercicio = new Exercicio();
                exercicio.setNome(nomeExercicio);
                exercicio.setRepeticoes(repeticoes != null ? repeticoes : 0);
                exercicio.setSeries(series != null ? series : 0);
                exercicio.setDivisaoTreino(selectedDivisaoTreino);
                exerciciosParaSalvar.add(exercicio);
            }
        }

        try {
            alunoDAO.salvarExerciciosAluno(matriculaAluno, exerciciosParaSalvar);
            JOptionPane.showMessageDialog(this, "Treino atribuído e salvo no banco de dados com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar treino no banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnatribuirActionPerformed

    private void adicionarLinhaButtonActionPerformed(java.awt.event.ActionEvent evt) {
        MyTableModel model = (MyTableModel) jTable1.getModel();
        model.addRow();
    }


    private class MyTableModel extends AbstractTableModel {
        private List<Object[]> data;
        private String[] columnNames;

        public MyTableModel(List<Object[]> data, String[] columnNames) {
            this.data = data;
            this.columnNames = columnNames;
        }

        public void setData(List<Object[]> data) {
            this.data = data;
        }

        public void addRow() {
            data.add(new Object[]{null, null, null});
            fireTableRowsInserted(data.size() - 1, data.size() - 1);
        }


        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 1 || columnIndex == 2) {
                return Integer.class;
            }
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            if (rowIndex >= 0 && rowIndex < data.size() && columnIndex >= 0 && columnIndex < 3) {
                data.get(rowIndex)[columnIndex] = value;
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AtribuirTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AtribuirTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AtribuirTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AtribuirTreino.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AtribuirTreino(1).setVisible(true);
            }
        });
    }


}
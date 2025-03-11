import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AtribuirTreino extends javax.swing.JFrame {

    private MyTableModel tableModel;

    /**
     * Creates new form AtribuirTreino
     */
    public AtribuirTreino() {
        initComponents();

        // Inicializa o TableModel com os dados do dia "A"
        tableModel = new MyTableModel(getDataForDay("A"), getColumnNames());
        jTable1.setModel(tableModel);

        // Alinha o texto das células da tabela ao centro
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adiciona o ActionListener ao JComboBox
        tipostreino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDay = (String) tipostreino.getSelectedItem();
                List<Object[]> newData = getDataForDay(selectedDay);
                tableModel.setData(newData);
                tableModel.fireTableDataChanged(); // Notifica a tabela sobre a mudança
            }
        });
    }

    // Define os nomes das colunas da tabela
    private String[] getColumnNames() {
        return new String[]{"Nome", "Carga(Kg)", "Repetições", "Séries"};
    }

    // Retorna os dados para a tabela com base no dia selecionado
    private List<Object[]> getDataForDay(String day) {
        List<Object[]> data = new ArrayList<>();
        switch (day) {
            case "A":
                data.add(new Object[]{"Supino Reto", 60, 12, 3});
                data.add(new Object[]{"Supino Inclinado com Halteres", 30, 12, 3});
                data.add(new Object[]{"Crucifixo Reto", 20, 15, 3});
                break;
            case "B":
                data.add(new Object[]{"Puxada Alta", 50, 10, 3});
                data.add(new Object[]{"Remada Curvada", 40, 10, 3});
                data.add(new Object[]{"Rosca Direta", 30, 12, 3});
                break;
            case "C":
                data.add(new Object[]{"Agachamento Livre", 70, 8, 3});
                data.add(new Object[]{"Leg Press 45", 100, 12, 3});
                data.add(new Object[]{"Extensora", 40, 15, 3});
                break;
            case "D":
                data.add(new Object[]{"Desenvolvimento Militar", 40, 8, 3});
                data.add(new Object[]{"Elevação Lateral", 10, 12, 3});
                data.add(new Object[]{"Tríceps Francês", 25, 12, 3});
                break;
            case "E":
                data.add(new Object[]{"Abdominal Supra", null, 20, 3}); // Valores nulos
                data.add(new Object[]{"Abdominal Infra", null, 15, 3});
                data.add(new Object[]{"Lombar", null, 15, 3});
                break;
            case "F":
                data.add(new Object[]{"Cardio (Esteira)", null, null, 30}); // Cardio com tempo
                data.add(new Object[]{"Alongamento", null, null, null}); //Alongamento
                break;

            default:
                data.add(new Object[]{"Descanso", 0, 0, 0}); // Descanso
        }
        return data;
    }

    // Classe interna para o TableModel
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
            // Determina o tipo de dado de cada coluna
            if (columnIndex == 1 || columnIndex == 2 || columnIndex == 3) { // Carga, repetições e series
                return Integer.class;
            }
            return String.class; // Nome
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false; // Impede a edição das células
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblslcnome = new javax.swing.JLabel();
        tipostreino = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnatribuir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblslcnome.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblslcnome.setText("Selecione o Dia:");

        tipostreino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F" }));
        tipostreino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipostreinoActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Supino",  new Integer(60),  new Integer(12),  new Integer(3)},
                {"Supino inclinado com Halteres",  new Integer(30),  new Integer(12),  new Integer(3)}
            },
            new String [] {
                "Nome", "Carga(Kg)", "Repetições", "Séries"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        btnatribuir.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnatribuir.setText("Atribuir");
        btnatribuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnatribuirActionPerformed(evt);
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
                .addComponent(tipostreino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnatribuir, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblslcnome)
                    .addComponent(tipostreino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(btnatribuir)
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
    }// </editor-fold>//GEN-END:initComponents

    private void tipostreinoActionPerformed(ActionEvent evt) {
    }

    private void btnatribuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnatribuirActionPerformed
        // TODO add your handling code here:
        //Aqui você pode adicionar a lógica para atribuir o treino selecionado.
        //Por exemplo, você pode pegar o dia selecionado no JComboBox
        //e os dados da linha selecionada na JTable e salvar em algum lugar.
    }//GEN-LAST:event_btnatribuirActionPerformed

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
                new AtribuirTreino().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnatribuir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblslcnome;
    public javax.swing.JComboBox<String> tipostreino;
    // End of variables declaration//GEN-END:variables
}
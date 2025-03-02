import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListadeMatriculados extends JFrame {

    private JTable jTable1; // Make jTable1 an instance variable

    public ListadeMatriculados() {
        initComponents();
        loadStudentData();
    }

    private void loadStudentData() {
        String sql = "SELECT nome, matricula FROM alunos";
        List<Object[]> data = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();  // Assuming you have a ConexaoDB class
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Use PreparedStatement

            try (ResultSet rs = pstmt.executeQuery()) { // Nested try-with-resources
                while (rs.next()) {
                    data.add(new Object[]{rs.getString("nome"), rs.getInt("matricula")});
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados dos alunos: " + e.getMessage(), "Erro no Banco de Dados", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return; // Exit on error
        }
        // Convert List to array in one line
        Object[][] dataArray = data.toArray(new Object[0][]);
        jTable1.setModel(new StudentTableModel(dataArray, new String[]{"Nome", "Matrícula"}));
        jTable1.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
        jTable1.addMouseListener(new TableMouseListener()); // Use a named inner class
    }


    private class TableMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent evt) {
            int row = jTable1.rowAtPoint(evt.getPoint());
            int col = jTable1.columnAtPoint(evt.getPoint());
            if (col == 1) {
                Integer matricula = (Integer) jTable1.getValueAt(row, 1);
                JOptionPane.showMessageDialog(ListadeMatriculados.this, "Botão clicado para matrícula: " + matricula);
            }
        }
    }

    // Inner class for TableModel (simplified)
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
        public Class<?> getColumnClass(int columnIndex) { return columnIndex == 1 ? Integer.class : getValueAt(0, columnIndex).getClass(); }
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
    }


    // Inner class for ButtonRenderer (simplified)
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setBackground(isSelected ? table.getSelectionBackground() : UIManager.getColor("Button.background"));
            setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            setText(value == null ? "" : value.toString());
            return this;
        }
    }



    private void initComponents() {
        // ... (Generated code from the Form Editor -  Keep this as is) ...
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel( // Remove initial data here
                new Object [][] {

                },
                new String [] {
                        "Nome", "Matricula"
                }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
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
        // ... (your existing main method) ...
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListadeMatriculados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListadeMatriculados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListadeMatriculados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListadeMatriculados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListadeMatriculados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    //private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
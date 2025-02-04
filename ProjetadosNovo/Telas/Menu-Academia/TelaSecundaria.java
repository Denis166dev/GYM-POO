import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
class TelaSecundaria extends JFrame {
        public TelaSecundaria() {
            // Configurações da janela secundária
            setTitle("Tela Secundária");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas a janela secundária
            setLocationRelativeTo(null); // Centraliza a janela
        }
    }

import javax.swing.*;
import java.awt.*;

public class EstiloMenuPrincipal {
    public static void estilizarBotao(JButton botao) {
        botao.setPreferredSize(new Dimension(300, 100));
        botao.setBackground(Color.decode("#9e9e9e"));
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setForeground(Color.BLACK);
        botao.setBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 2));
        botao.setContentAreaFilled(false); // Remove o fundo padrão
        botao.setFocusPainted(false); // Remove o destaque ao focar
        botao.setOpaque(true); // Permite a customização do fundo
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(Color.decode("#8a8a8a")); // Cor quando o mouse passa sobre o botão
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(Color.decode("#9e9e9e")); // Cor padrão
            }

        });
    }
}

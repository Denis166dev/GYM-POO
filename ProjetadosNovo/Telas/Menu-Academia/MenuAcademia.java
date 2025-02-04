import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// teste de commit
public class MenuAcademia {
    public static void main(String[] args) {
        // Criar o frame
        JFrame frame = new JFrame("ADS GYM MENAGER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        // Definir o layout como GridBagLayout
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        frame.getContentPane().setBackground(Color.decode("#eeeeee"));


        // Carregar a imagem original
        ImageIcon imagemOriginal = new ImageIcon("imgs/logoProjetoAcademia.png"); // Substitua pelo caminho da sua imagem
        ImageIcon IconeImagem = new ImageIcon("imgs/logoProjetoAcademiaSEmFUndo.png");
        frame.setIconImage(IconeImagem.getImage());
        // Redimensionar a imagem
        Image imagemRedimensionada = imagemOriginal.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH);
        ImageIcon imagemFinal = new ImageIcon(imagemRedimensionada);


        // Adicionar a imagem redimensionada a um JLabel
        JLabel labelComImagem = new JLabel(imagemFinal);
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        gbc.gridwidth = 3; // ocupa 3 colunas
        gbc.gridheight = 1; // ocupa 1 linh a
        gbc.insets = new Insets(0, 0, 30, 0); // espaço ao redor do componente
        frame.add(labelComImagem, gbc);

        // Criar o painel de botões
        PainelBotoes painelBotoes = new PainelBotoes();
        gbc.gridx = 0; // Posição na grade (ajustar conforme necessário)
        gbc.gridy = 1; // Posição vertical
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(painelBotoes, gbc);

        frame.setVisible(true);
    }
}
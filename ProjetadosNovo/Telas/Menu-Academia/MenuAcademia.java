import javax.swing.*;
import java.awt.*;

public class MenuAcademia extends JFrame {

    // Construtor da classe
    public MenuAcademia() {
        // Configurações básicas do JFrame
        setTitle("ADS GYM MANAGER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(false);
        getContentPane().setBackground(Color.decode("#eeeeee")); // Define a cor de fundo

        // Definir o layout como GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Carregar a imagem original
        ImageIcon imagemOriginal = new ImageIcon("imgs/logoProjetoAcademia.png"); // Substitua pelo caminho da sua imagem
        ImageIcon IconeImagem = new ImageIcon("imgs/logoProjetoAcademiaSEmFUndo.png");
        setIconImage(IconeImagem.getImage()); // Define o ícone da janela
    
        // Redimensionar a imagem
        Image imagemRedimensionada = imagemOriginal.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH);
        ImageIcon imagemFinal = new ImageIcon(imagemRedimensionada);

        // Adicionar a imagem redimensionada a um JLabel
        JLabel labelComImagem = new JLabel(imagemFinal);
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        gbc.gridwidth = 3; // Ocupa 3 colunas
        gbc.gridheight = 1; // Ocupa 1 linha
        gbc.insets = new Insets(0, 0, 30, 0); // Espaço ao redor do componente
        add(labelComImagem, gbc);

        // Criar o painel de botões
        PainelBotoes painelBotoes = new PainelBotoes();
        gbc.gridx = 0; // Posição na grade (ajustar conforme necessário)
        gbc.gridy = 1; // Posição vertical
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        add(painelBotoes, gbc);
    }

    // Método main para iniciar a aplicação
    public static void main(String[] args) {
        // Executa a interface gráfica na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            MenuAcademia menu = new MenuAcademia(); // Cria uma instância da classe MenuAcademia
            menu.setVisible(true); // Torna a janela visível
        });
    }
}

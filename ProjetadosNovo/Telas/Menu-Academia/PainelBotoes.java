import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelBotoes extends JPanel {

    public PainelBotoes() {
        // Definindo o layout do painel de botões (pode ser GridBagLayout, FlowLayout, etc.)
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Estilo de botões e configuração do layout
        EstiloMenuPrincipal estilo = new EstiloMenuPrincipal(); // Supondo que você tenha um método EstilizarBotao

        // Botão 1
        JButton botao1 = new JButton("CADASTRAR MATRICULA");
        estilo.estilizarBotao(botao1);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(botao1, gbc);

        // Botão 2
        JButton botao2 = new JButton("LISTA DE MATRICULADOS");
        estilo.estilizarBotao(botao2);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(botao2, gbc);

        // Botão 3
        JButton botao3 = new JButton("MATRICULAS VENCIDAS");
        estilo.estilizarBotao(botao3);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(botao3, gbc);

        // Ação para o botão 1 (exemplo de ação do botão)
        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para abrir a tela secundária
                CadastroClienteNETBEANS teladecadastro = new CadastroClienteNETBEANS();
                teladecadastro.setVisible(true);
            }
        });

        // Você pode adicionar ações para os outros botões da mesma forma
        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para botão 2 (exemplo)
                System.out.println("Lista de Matriculados");
            }
        });

        botao3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para botão 3 (exemplo)
                System.out.println("Matrículas Vencidas");
            }
        });
    }
}

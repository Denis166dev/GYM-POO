import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Importe as classes das telas que você vai abrir


public class PainelBotoes extends JPanel {

    public PainelBotoes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        EstiloMenuPrincipal estilo = new EstiloMenuPrincipal();

        JButton botao1 = new JButton("CADASTRAR MATRICULA");
        estilo.estilizarBotao(botao1);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(botao1, gbc);

        JButton botao2 = new JButton("LISTA DE MATRICULADOS");
        estilo.estilizarBotao(botao2);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(botao2, gbc);

        JButton botao3 = new JButton("MATRICULAS VENCIDAS");
        estilo.estilizarBotao(botao3);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(botao3, gbc);

        botao1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroClienteNETBEANS teladecadastro = new CadastroClienteNETBEANS();
                teladecadastro.setVisible(true);
            }
        });

        // --- Ações para os outros botões ---

        botao2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de lista de matriculados
                ListadeMatriculados lista = new ListadeMatriculados();
                lista.setVisible(true);
            }
        });

        botao3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de matrículas vencidas
                MatriculasVencidasNet vencidas = new MatriculasVencidasNet();
                vencidas.setVisible(true);
            }
        });
    }
}
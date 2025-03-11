// No pacote correto (ex: ProjetadosNovo.Model)

public class Exercicio {
    private String nome;
    private int carga;
    private int repeticoes;
    private int series;
    private String divisaoTreino;

    // Construtores
    public Exercicio() {}

    public Exercicio(String nome, int carga, int repeticoes, int series, String divisaoTreino) {
        this.nome = nome;
        this.carga = carga;
        this.repeticoes = repeticoes;
        this.series = series;
        this.divisaoTreino = divisaoTreino;
    }

    // Getters e setters
    public String getDivisaoTreino() {
        return divisaoTreino;
    }
    public void setDivisaoTreino(String divisaoTreino) {
        this.divisaoTreino = divisaoTreino;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }
}
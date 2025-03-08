public class Aluno {
    private int matricula; // Chave estrangeira que referencia a tabela usuarios
    private String nome;
    private String numerocel;
    private String email;
    private String nascimento;
    private String sexo; // "Feminino" ou "Masculino"
    private double peso;
    private double altura;
    private String plano; // "Mensal", "Semestral", "Anual"
    private String horarioCadastro;

    public Aluno() {
    } // Construtor vazio

    public Aluno(String nome, String numerocel, String email, String nascimento, String sexo, double peso, double altura, String plano, String horarioCadastro) {
        this.nome = nome;
        this.numerocel = numerocel;
        this.email = email;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.peso = peso;
        this.altura = altura;
        this.plano = plano;
        this.horarioCadastro = horarioCadastro;
    }

    // Getters e setters para todos os atributos
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getHorarioCadastro() {
        return horarioCadastro;
    }

    public void setHorarioCadastro(String horarioCadastro) {
        this.horarioCadastro = horarioCadastro;
    }

    public String getNumerocel() {
        return numerocel;
    }

    public void setNumerocel(String numerocel) {
        this.numerocel = numerocel;
    }
}
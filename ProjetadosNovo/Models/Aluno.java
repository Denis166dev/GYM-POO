import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Aluno {
    private int matricula; // Chave primária, agora AUTOINCREMENT
    private String nome;
    private String email;
    private String numerocel;
    private LocalDate nascimento; // Usando LocalDate
    private String sexo; // "Feminino" ou "Masculino"
    private String plano; // "Mensal", "Semestral", "Anual"
    private LocalDateTime horarioCadastro;  //Armazena data e hora do cadastro.


    // Construtor vazio
    public Aluno() {}

    public Aluno(String nome, String email, String numerocel, LocalDate nascimento, String sexo, String plano, LocalDateTime horarioCadastro) {
        this.nome = nome;
        this.email = email;
        this.numerocel = numerocel;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.plano = plano;
        this.horarioCadastro = horarioCadastro;
    }
    // Getters e setters
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

    public String getNumerocel() {
        return numerocel;
    }

    public void setNumerocel(String numerocel) {
        this.numerocel = numerocel;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }
    public LocalDateTime getHorarioCadastro() {
        return horarioCadastro;
    }
    public void setHorarioCadastro(LocalDateTime horarioCadastro){
        this.horarioCadastro = horarioCadastro;
    }

}
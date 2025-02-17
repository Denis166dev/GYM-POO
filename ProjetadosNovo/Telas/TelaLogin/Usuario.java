public class Usuario {
    private int matricula;
    private String senha;
    private String tipo; // "aluno" ou "instrutor"

    public Usuario() {
    }

    public Usuario(String senha, String tipo) {
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters e setters para todos os atributos
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}


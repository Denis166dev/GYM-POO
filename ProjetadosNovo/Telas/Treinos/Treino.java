import java.sql.*;
import java.util.Date;

public class Treino {
    private int id;
    private int matriculaAluno;
    private Date dataInicio;
    private Date dataFim;
    private String tipoTreino;

    // Construtores, getters e setters
    public Treino(int matriculaAluno, Date dataInicio, Date dataFim, String tipoTreino) {
        this.matriculaAluno = matriculaAluno;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipoTreino = tipoTreino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(int matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getTipoTreino() {
        return tipoTreino;
    }

    public void setTipoTreino(String tipoTreino) {
        this.tipoTreino = tipoTreino;
    }
}

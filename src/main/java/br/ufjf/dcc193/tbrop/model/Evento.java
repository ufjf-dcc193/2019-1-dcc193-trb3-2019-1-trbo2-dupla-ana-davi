package br.ufjf.dcc193.tbrop.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataHora;
    private int tipo;
    @OneToOne
    private Atendente atendente;

    public Evento() {
    }

    public Evento(Long id, Date dataHora, int tipo, Atendente atendente) {
        this.id = id;
        this.dataHora = dataHora;
        this.tipo = tipo;
        this.atendente = atendente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
    
    public String getDataHoraBR(){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formato.format(dataHora);
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public String getTipoNome(){
        switch (tipo) {
            case 1:
                return "Abertura";
            case 2:
                return "Fechamento";
            case 3:
                return "Alteração de usuário";
            case 4:
                return "Alteração de categoria";
            default:
                return "";
        }
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    @Override
    public String toString() {
        return "Evento{" + "id=" + id + ", dataHora=" + dataHora + ", tipo=" + tipo + ", atendente=" + atendente + '}';
    }

}

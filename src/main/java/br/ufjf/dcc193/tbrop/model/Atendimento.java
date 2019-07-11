package br.ufjf.dcc193.tbrop.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Atendimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Categoria categoria;
    
    private String dataCriacao;

    private String dataFechamento;

    @OneToOne
    private Atendente atendente;

    @OneToOne
    private Usuario usuario;

    private int status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Evento> eventos;

    private String descricao;

    public static int STATUS_EMREVISAO = 1;
    public static int STATUS_ABERTO = 2;
    public static int STATUS_BLOQUEADO = 3;
    public static int STATUS_EMANDAMENTO = 4;
    public static int STATUS_FECHADO = 5;

    public Atendimento() {
    }

    public Atendimento(Long id, Categoria categoria, String dataCriacao, String dataFechamento, Atendente atendente, Usuario usuario, int status, List<Evento> eventos, String descricao) {
        this.id = id;
        this.categoria = categoria;
        this.dataCriacao = dataCriacao;
        this.dataFechamento = dataFechamento;
        this.atendente = atendente;
        this.usuario = usuario;
        this.status = status;
        this.eventos = eventos;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(String dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Atendente getAtendente() {
        return atendente;
    }

    public void setAtendente(Atendente atendente) {
        this.atendente = atendente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusNome() {
        switch (status) {
            case 1:
                return "Em revisão";
            case 2:
                return "Aberto";
            case 3:
                return "Bloqueado";
            case 4:
                return "Em andamento";
            case 5:
                return "Fechado";
            default:
                return "";
        }
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Atendimento{" + "id=" + id + ", categoria=" + categoria + ", dataCriacao=" + dataCriacao + ", dataFechamento=" + dataFechamento + ", atendente=" + atendente + ", usuario=" + usuario + ", status=" + status + ", eventos=" + eventos + ", descricao=" + descricao + '}';
    }
}

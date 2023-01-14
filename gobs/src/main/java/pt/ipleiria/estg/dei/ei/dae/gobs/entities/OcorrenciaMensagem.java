package pt.ipleiria.estg.dei.ei.dae.gobs.entities;

import org.hibernate.annotations.CreationTimestamp;
import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.OcorrenciaMensagemDTO;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Entity
@SuppressWarnings("unused")
public class OcorrenciaMensagem extends EntityId<Integer> {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private Integer sender;//0 means ocorrencia.client another value is the id of funcionario
    @JoinColumn(name = "ocorrencia_id")
    @ManyToOne
    @NotNull
    private Ocorrencia ocorrencia;

    @Size(min = 15)
    @NotBlank
    private String mensagem;

    @NotNull
    @OneToMany(mappedBy = "mensagem", cascade = CascadeType.REMOVE)
    @OrderColumn(name = "criado")
    private Collection<Ficheiro> ficheiros;

    @CreationTimestamp
    private Date criado;

    public OcorrenciaMensagem() {
        this.ficheiros = new LinkedList<>();
    }

    public OcorrenciaMensagem(Integer sender, String mensagem) {
        this.sender = sender;
        this.mensagem = mensagem;
        this.ficheiros = new LinkedList<>();
    }

    public OcorrenciaMensagem(Integer sender, String mensagem, Ocorrencia ocorrencia) {
        this.sender = sender;
        this.mensagem = mensagem;
        this.ocorrencia = ocorrencia;
        this.ficheiros = new LinkedList<>();
    }

    @Override
    public Integer getEntityId() {
        return id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Collection<Ficheiro> getFicheiros() {
        return ficheiros;
    }

    public void setFicheiros(Collection<Ficheiro> ficheiros) {
        this.ficheiros = ficheiros;
    }

    public Date getCriado() {
        return criado;
    }

    public void setCriado(Date criado) {
        this.criado = criado;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addFicheiro(Ficheiro ficheiro) {
        if (ficheiros.contains(ficheiro))
            return false;

        return ficheiros.add(ficheiro);
    }

    public OcorrenciaMensagemDTO toDTO() {
        return new OcorrenciaMensagemDTO(
                this.getId(),
                this.getSender(),
                this.getMensagem(),
                this.getFicheiros().stream().map(Ficheiro::toDTO).collect(Collectors.toList()),
                this.getCriado()
        );
    }
}

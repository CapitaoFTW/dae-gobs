package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import pt.ipleiria.estg.dei.ei.dae.gobs.entities.OcorrenciaMensagem;

public class NewOcorrenciaMensagemDTO {
    private Integer sender;
    private String mensagem;

    public NewOcorrenciaMensagemDTO() {
    }

    public NewOcorrenciaMensagemDTO(Integer sender, String mensagem) {
        this.sender = sender;
        this.mensagem = mensagem;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


    public OcorrenciaMensagem toEntity() {
        return new OcorrenciaMensagem(
                this.getSender(),
                this.getMensagem()
        );
    }
}

package pt.ipleiria.estg.dei.ei.dae.gobs.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuppressWarnings("unused")
public class EmailDTO {
    @NotBlank
    @Size(min = 2, max = 255)
    private String subject;
    @NotBlank
    @Size(min = 3)
    private String message;

    public EmailDTO() {
    }

    public EmailDTO(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

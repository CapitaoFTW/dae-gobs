package pt.ipleiria.estg.dei.ei.dae.gobs.dtos.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ChangePasswordDTO implements Serializable {
    @NotBlank
    @Size(min = 3, max = 255)
    private String oldPassword;
    @NotBlank
    @Size(min = 3, max = 255)
    private String newPassword;
    @NotBlank
    @Size(min = 3, max = 255)
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    @SuppressWarnings("unused")
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    @SuppressWarnings("unused")
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    @SuppressWarnings("unused")
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

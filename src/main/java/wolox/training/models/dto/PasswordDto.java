package wolox.training.models.dto;

import javax.validation.constraints.NotNull;

public class PasswordDto {

    @NotNull
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordDto{" +
                "password='" + password + '\'' +
                '}';
    }
}

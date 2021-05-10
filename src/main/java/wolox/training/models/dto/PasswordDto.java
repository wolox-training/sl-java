package wolox.training.models.dto;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static wolox.training.utils.MessageError.CHECK_ARGUMENT_EMPTY_MESSAGE;
import static wolox.training.utils.MessageError.CHECK_NOT_NULL_MESSAGE;

import javax.validation.constraints.NotNull;
import org.springframework.util.ObjectUtils;

public class PasswordDto {

    @NotNull
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        checkNotNull(password, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(password), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordDto{" +
                "password='" + password + '\'' +
                '}';
    }
}

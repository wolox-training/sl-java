package wolox.training.models.dto;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static wolox.training.utils.MessageError.CHECK_ARGUMENT_EMPTY_MESSAGE;
import static wolox.training.utils.MessageError.CHECK_NOT_NULL_MESSAGE;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Data
public class PasswordDto {

    @NotNull
    @Setter(AccessLevel.NONE)
    private String password;

    public void setPassword(String password) {
        checkNotNull(password, CHECK_NOT_NULL_MESSAGE);
        checkArgument(!ObjectUtils.isEmpty(password), CHECK_ARGUMENT_EMPTY_MESSAGE);
        this.password = password;
    }
}

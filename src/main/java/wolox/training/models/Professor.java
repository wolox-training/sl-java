package wolox.training.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import wolox.training.utils.EntityConstants;

@Entity
@DiscriminatorValue(EntityConstants.PROFESSOR_DISCRIMINATOR_VALUE)
@Setter
@Getter
public class Professor extends User {

    private String subject;
}

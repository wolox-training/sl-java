package wolox.training.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import wolox.training.utils.EntityConstants;

@Entity
@DiscriminatorValue(EntityConstants.STUDENT_DISCRIMINATOR_VALUE)
@Setter
@Getter
@ToString
public class Student extends User {

    private String year;

}

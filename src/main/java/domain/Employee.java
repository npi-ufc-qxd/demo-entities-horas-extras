package domain;

import entities.annotations.View;
import entities.annotations.Views;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Views({
    @View(name = "Employees",
          title = "Employees",
          members = "Employee[name;username;password;roles]",
          template = "@CRUD_PAGE",
          roles = "Admin,RH")
})
public class Employee extends User implements Serializable {

    @NotEmpty
    @Column(length = 50)
    private String name;

    @Override
    public String toString() {
        return name;
    }
}

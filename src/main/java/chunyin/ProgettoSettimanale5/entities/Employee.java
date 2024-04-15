package chunyin.ProgettoSettimanale5.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatarURL;
    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Device> devices;

    public Employee(String name, String surname, String email, String password, String avatarURL) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatarURL = avatarURL;
        this.password = password;
    }
}

package chunyin.ProgettoSettimanale5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Device {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Device(Status status, Type type, Employee employee) {
        this.status = status;
        this.type = type;
        this.employee = employee;
    }
}
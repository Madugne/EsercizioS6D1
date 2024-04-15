package chunyin.ProgettoSettimanale5.payloads;

import chunyin.ProgettoSettimanale5.entities.Employee;
import chunyin.ProgettoSettimanale5.entities.Status;
import chunyin.ProgettoSettimanale5.entities.Type;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewDeviceDTO(@NotEmpty(message = "La stato è obbligatorio")
                           Status status,
                           @NotEmpty(message = "Il tipo è obbligatorio")
                           Type type,
                           Employee employee) {
}
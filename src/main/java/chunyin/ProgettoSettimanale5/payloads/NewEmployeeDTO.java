package chunyin.ProgettoSettimanale5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewEmployeeDTO(@NotEmpty(message = "Il nome proprio è obbligatorio")
                             @Size(min = 3, max = 30, message = "Il nome proprio deve essere compreso tra i 3 e i 30 caratteri")
                             String name,
                             @NotEmpty(message = "Il cognome è obbligatorio")
                             @Size(min = 3, max = 30, message = "Il cognome deve essere compreso tra i 3 e i 30 caratteri")
                             String surname,
                             @NotEmpty(message = "L'email è obbligatoria")
                             @Email(message = "L'email inserita non è valida")
                             String email,
                             @NotEmpty(message = "La password è obbligatoria")
                             String password) {
}
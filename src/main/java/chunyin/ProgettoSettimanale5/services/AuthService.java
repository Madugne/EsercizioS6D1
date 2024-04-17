package chunyin.ProgettoSettimanale5.services;

import chunyin.ProgettoSettimanale5.entities.Employee;
import chunyin.ProgettoSettimanale5.exceptions.UnauthorizedException;
import chunyin.ProgettoSettimanale5.payloads.EmployeeLoginDTO;
import chunyin.ProgettoSettimanale5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUserAndGenerateToken(EmployeeLoginDTO payload){
        Employee employee = this.employeeService.findByEmail(payload.email());
        if(bcrypt.matches(payload.password(), employee.getPassword())) {
            return jwtTools.createToken(employee);
        } else {
            throw new UnauthorizedException("Credenziali non valide! Effettua di nuovo il login!");
        }
    }
}
package chunyin.ProgettoSettimanale5.config;

import chunyin.ProgettoSettimanale5.exceptions.BadRequestException;
import chunyin.ProgettoSettimanale5.payloads.*;
import chunyin.ProgettoSettimanale5.services.AuthService;
import chunyin.ProgettoSettimanale5.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private EmployeeService usersService;

    @PostMapping("/login")
    public EmployeeLoginResposeDTO login(@RequestBody EmployeeLoginDTO payload){
        return new EmployeeLoginResposeDTO(this.authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeeRespDTO saveEmployee(@RequestBody @Validated NewEmployeeDTO body, BindingResult validation){
        if(validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewEmployeeRespDTO(this.usersService.saveEmployee(body).getId());
    }

}
package chunyin.ProgettoSettimanale5.controllers;

import chunyin.ProgettoSettimanale5.entities.Employee;
import chunyin.ProgettoSettimanale5.exceptions.BadRequestException;
import chunyin.ProgettoSettimanale5.payloads.NewEmployeeDTO;
import chunyin.ProgettoSettimanale5.payloads.NewEmployeeRespDTO;
import chunyin.ProgettoSettimanale5.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Employee> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size,
                                        @RequestParam(defaultValue = "id") String sortBy) {
        return this.employeeService.getEmployees(page, size, sortBy);
    }


    @GetMapping("/me")
    public Employee getProfile(@AuthenticationPrincipal Employee currentAuthenticatedUser){
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public Employee updateProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee, @RequestBody Employee updatedEmployee){
        return this.employeeService.update(currentAuthenticatedEmployee.getId(), updatedEmployee);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Employee currentAuthenticatedUser){
        this.employeeService.delete(currentAuthenticatedUser.getId());
    }
    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable UUID employeeId){
        return employeeService.findById(employeeId);
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee update(@PathVariable UUID employeeId, @RequestBody Employee body){
        return employeeService.update(employeeId, body);
    }

    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID employeeId){
        employeeService.delete(employeeId);
    }

    @PatchMapping("/{employeeId}/avatar")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable UUID employeeId) {
        try {
            return employeeService.uploadAvatar(employeeId, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
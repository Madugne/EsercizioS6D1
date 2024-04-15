package chunyin.ProgettoSettimanale5.services;

import chunyin.ProgettoSettimanale5.entities.Employee;
import chunyin.ProgettoSettimanale5.exceptions.BadRequestException;
import chunyin.ProgettoSettimanale5.exceptions.NotFoundException;
import chunyin.ProgettoSettimanale5.payloads.NewEmployeeDTO;
import chunyin.ProgettoSettimanale5.repositories.EmployeeRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Employee> getEmployees(int page, int size, String sortBy){
        if(size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return employeeRepository.findAll(pageable);
    }

    public Employee saveEmployee(NewEmployeeDTO body){
        employeeRepository.findByEmail(body.email()).ifPresent(
                employee -> {throw new BadRequestException("L'email " + employee.getEmail() + " è già in uso");
                }
        );
        Employee newEmployee = new Employee(body.name(), body.surname(), body.email(), body.password(),
                "https://ui-avatars.com/api/?name="+ body.name() + "+" + body.surname());
        return employeeRepository.save(newEmployee);
    }

    public Employee findById(UUID employeeId){
        return employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    public Employee update(UUID userId, Employee modifiedEmployee){
        Employee foundEmployee = this.findById(userId);
        foundEmployee.setName(modifiedEmployee.getName());
        foundEmployee.setSurname(modifiedEmployee.getSurname());
        foundEmployee.setEmail(modifiedEmployee.getEmail());
        foundEmployee.setAvatarURL(modifiedEmployee.getAvatarURL());
        return employeeRepository.save(foundEmployee);
    }

    public void delete(UUID employeeId){
        Employee found = this.findById(employeeId);
        employeeRepository.delete(found);
    }

    public Employee uploadAvatar(UUID id, MultipartFile file) throws IOException {
        Employee found = this.findById(id);
        String avatarUrl = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatarURL(avatarUrl);
        return employeeRepository.save(found);
    }

    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }
}

package chunyin.ProgettoSettimanale5.services;

import chunyin.ProgettoSettimanale5.entities.Device;
import chunyin.ProgettoSettimanale5.entities.Employee;
import chunyin.ProgettoSettimanale5.entities.Status;
import chunyin.ProgettoSettimanale5.exceptions.NotFoundException;
import chunyin.ProgettoSettimanale5.payloads.NewDeviceDTO;
import chunyin.ProgettoSettimanale5.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private EmployeeService employeeService;

    public Page<Device> getDevices(int page, int size, String sortBy){
        if(size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return deviceRepository.findAll(pageable);
    }

    public Device saveDevice(NewDeviceDTO body){
        Device newDevice = new Device(body.status(), body.type(), body.employee());
        return deviceRepository.save(newDevice);
    }

    public Device findById(UUID deviceId){
        return deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException(deviceId));
    }

    public Device update(UUID deviceId, Device modifiedDevice){
        Device foundDevice = this.findById(deviceId);
        foundDevice.setType(modifiedDevice.getType());
        foundDevice.setEmployee(modifiedDevice.getEmployee());
        foundDevice.setStatus(modifiedDevice.getStatus());
        return deviceRepository.save(foundDevice);
    }

    public void delete(UUID deviceId){
        Device found = this.findById(deviceId);
        deviceRepository.delete(found);
    }

    public List<Device> findByEmployee(UUID employeeId) {
        Employee employee = employeeService.findById(employeeId);
        return deviceRepository.findByEmployee(employee);
    }

    public Device deviceAssociation(UUID employeeId, UUID deviceId) {
        Employee employee = employeeService.findById(employeeId);
        Device foundDevice = this.findById(deviceId);
        if (foundDevice.getStatus().equals(Status.DISPONIBILE)) {
            foundDevice.setStatus(Status.ASSEGNATO);
            foundDevice.setEmployee(employee);
            return deviceRepository.save(foundDevice);
        } else {
            throw new RuntimeException("Dispositivo non assegnabile");
        }
    }
}
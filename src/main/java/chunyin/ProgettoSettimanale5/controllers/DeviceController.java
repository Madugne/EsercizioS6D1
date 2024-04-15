package chunyin.ProgettoSettimanale5.controllers;


import chunyin.ProgettoSettimanale5.entities.Device;
import chunyin.ProgettoSettimanale5.entities.Employee;
import chunyin.ProgettoSettimanale5.exceptions.BadRequestException;
import chunyin.ProgettoSettimanale5.payloads.NewDeviceDTO;
import chunyin.ProgettoSettimanale5.payloads.NewDeviceRespDTO;
import chunyin.ProgettoSettimanale5.payloads.NewEmployeeDTO;
import chunyin.ProgettoSettimanale5.payloads.NewEmployeeRespDTO;
import chunyin.ProgettoSettimanale5.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public Page<Device> getAllDevices(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return this.deviceService.getDevices(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewDeviceRespDTO saveDevice(@RequestBody NewDeviceDTO body){
        return new NewDeviceRespDTO(deviceService.saveDevice(body).getId());
    }

    @GetMapping("/{deviceId}")
    public Device findById(@PathVariable UUID deviceId){
        return deviceService.findById(deviceId);
    }

    @PutMapping("/{deviceId}")
    public Device update(@PathVariable UUID deviceId, @RequestBody Device body){
        return deviceService.update(deviceId, body);
    }

    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID deviceId){
        deviceService.delete(deviceId);
    }

    @PutMapping("/associate/{deviceId}/{employeeId}")
    public Device deviceAssociation(@PathVariable UUID employeeId, @PathVariable UUID deviceId) {
         return deviceService.deviceAssociation(employeeId, deviceId);
    }
}
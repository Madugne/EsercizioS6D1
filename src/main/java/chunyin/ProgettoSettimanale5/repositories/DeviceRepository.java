package chunyin.ProgettoSettimanale5.repositories;

import chunyin.ProgettoSettimanale5.entities.Device;
import chunyin.ProgettoSettimanale5.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository <Device, UUID> {
    List<Device> findByEmployee(Employee employee);
}

package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson11.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByAddress_Id(Integer address_id);
    boolean existsByAddress_IdAndIdNot(Integer address_id, Integer id);
}

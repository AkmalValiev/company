package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson11.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByCompName(String compName);
    boolean existsByAddress_Id(Integer address_id);
    boolean existsByCompNameAndIdNot(String compName, Integer id);
    boolean existsByAddress_IdAndIdNot(Integer address_id, Integer id);
}

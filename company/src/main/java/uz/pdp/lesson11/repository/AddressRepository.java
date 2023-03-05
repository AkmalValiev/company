package uz.pdp.lesson11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson11.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    boolean existsByStreetAndHomeNumber(String street, String homeNumber);
}

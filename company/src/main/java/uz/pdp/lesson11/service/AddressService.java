package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Address;
import uz.pdp.lesson11.payload.AddressDto;
import uz.pdp.lesson11.payload.ApiResponse;
import uz.pdp.lesson11.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAddresses(){
        return addressRepository.findAll();
    }

    public Address getAddress(Integer id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElseGet(Address::new);
    }

    public ApiResponse addAddress(AddressDto addressDto){
        boolean exists = addressRepository.existsByStreetAndHomeNumber(addressDto.getStreet(), addressDto.getHomeNumber());
        if (exists)
            return new ApiResponse("Kiritilgan nomli street va homeNumber mavjud!", false);
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address qo'shildi!", true);
    }

    public ApiResponse editAddress(Integer id, AddressDto addressDto){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha address topilmadi", false);
        boolean exists = addressRepository.existsByStreetAndHomeNumber(addressDto.getStreet(), addressDto.getHomeNumber());
        if (exists)
            return new ApiResponse("Kiritilgan nomli street va homeNumber mavjud!", false);
        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address taxrirlandi!", true);
    }

    public ApiResponse deleteAddress(Integer id){
        try {
            addressRepository.deleteById(id);
            return new ApiResponse("Address o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }

}

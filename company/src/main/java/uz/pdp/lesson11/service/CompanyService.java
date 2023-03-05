package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Address;
import uz.pdp.lesson11.entity.Company;
import uz.pdp.lesson11.payload.ApiResponse;
import uz.pdp.lesson11.payload.CompanyDto;
import uz.pdp.lesson11.repository.AddressRepository;
import uz.pdp.lesson11.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;

    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }

    public Company getCompany(Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElseGet(Company::new);
    }

    public ApiResponse addCompany(CompanyDto companyDto){

        boolean existsByCompName = companyRepository.existsByCompName(companyDto.getCompName());
        if (existsByCompName)
            return new ApiResponse("Kritilgan nomli company mavjud", false);

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha address topilmadi!", false);

        boolean existsByAddressId = companyRepository.existsByAddress_Id(companyDto.getAddressId());
        if (existsByAddressId)
            return new ApiResponse("Kiritilgan id li address mavjud!", false);

        Company company = new Company();
        company.setAddress(optionalAddress.get());
        company.setCompName(companyDto.getCompName());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new ApiResponse("Company qo'shildi!", true);
    }

    public ApiResponse editCompany(Integer id, CompanyDto companyDto){

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha company topilmadi!", false);

        boolean existsByCompName = companyRepository.existsByCompNameAndIdNot(companyDto.getCompName(), id);
        if (existsByCompName)
            return new ApiResponse("Bunaqa nomli kompaniya mavjud!", false);

        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha address topilmadi!", false);

        boolean existsByAddressIdNot = companyRepository.existsByAddress_IdAndIdNot(companyDto.getAddressId(), id);
        if (existsByAddressIdNot)
            return new ApiResponse("Bunaqa id li address mavjud!", false);

        Company company = optionalCompany.get();
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(optionalAddress.get());
        company.setCompName(companyDto.getCompName());
        companyRepository.save(company);
        return new ApiResponse("Company taxrirlandi!", true);
    }

    public ApiResponse deleteCompany(Integer id){
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Company o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }

}

package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Company;
import uz.pdp.lesson11.entity.Department;
import uz.pdp.lesson11.payload.ApiResponse;
import uz.pdp.lesson11.payload.DepartmentDto;
import uz.pdp.lesson11.repository.CompanyRepository;
import uz.pdp.lesson11.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }

    public Department getDepartment(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElseGet(Department::new);
    }

    public ApiResponse addDepartment(DepartmentDto departmentDto){

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha company topilmadi!", false);

        boolean existsByNameAndCompanyId = departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId());
        if (existsByNameAndCompanyId)
            return new ApiResponse("Bunaqa nomli department ko'rsatilgan company da mavjud!", false);

        Department department = new Department();
        department.setCompany(optionalCompany.get());
        department.setName(departmentDto.getName());
        departmentRepository.save(department);
        return new ApiResponse("Department qo'shildi!", true);
    }

    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto){

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha department topilmadi!", false);

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha company topilmadi!", false);

        boolean existsByNameAndCompanyIdAndIdNot = departmentRepository.existsByNameAndCompanyIdAndIdNot(departmentDto.getName(), departmentDto.getCompanyId(), id);
        if (existsByNameAndCompanyIdAndIdNot)
            return new ApiResponse("Bunaqa nomli department ko'rsatilgan companyda mavjud!", false);

        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Department taxrirlandi!", true);
    }

    public ApiResponse deleteDepartment(Integer id){
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }

    }


}

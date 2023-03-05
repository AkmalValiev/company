package uz.pdp.lesson11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson11.entity.Address;
import uz.pdp.lesson11.entity.Department;
import uz.pdp.lesson11.entity.Worker;
import uz.pdp.lesson11.payload.ApiResponse;
import uz.pdp.lesson11.payload.WorkerDto;
import uz.pdp.lesson11.repository.AddressRepository;
import uz.pdp.lesson11.repository.DepartmentRepository;
import uz.pdp.lesson11.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Worker> getWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorker(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElseGet(Worker::new);
    }

    public ApiResponse addWorker(WorkerDto workerDto) {

        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse("Bunday telefon raqamli worker mavjud!", false);

        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha address topilmadi!", false);

        boolean existsByAddressId = workerRepository.existsByAddress_Id(workerDto.getAddressId());
        if (existsByAddressId)
            return new ApiResponse("Bunaqa addressli worker mavjud!", false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha department topilmadi!", false);

        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new ApiResponse("Worker qo'shildi!", true);
    }

    public ApiResponse editWorker(Integer id, WorkerDto workerDto) {

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha worker topilmadi!", false);

        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse("Bunday telefon raqamli worker mavjud!", false);

        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha address topilmadi!", false);

        boolean existsByAddressIdAndIdNot = workerRepository.existsByAddress_IdAndIdNot(workerDto.getAddressId(), id);
        if (existsByAddressIdAndIdNot)
            return new ApiResponse("Kiritilgan address id li worker mavjud!", false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha department topilmadi!", false);

        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new ApiResponse("Worker taxrirlandi!", true);

    }

    public ApiResponse deleteWorker(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Worker o'chirildi!", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik!!!", false);
        }
    }
}

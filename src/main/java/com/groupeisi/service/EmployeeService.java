package com.groupeisi.service;

import com.groupeisi.entity.Employee;
import com.groupeisi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAllEmployees(){
        return repository.findAll();
    }
    public void addEmployer(Employee employee){
        repository.save(employee);
    }
    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return repository.findAll(pageable);
    }

    public Employee getEmployeeById(long id){
        Optional<Employee> employee = repository.findById(id);
        if(employee.isPresent())
            return employee.get();

        throw new RuntimeException("Employee with ID " + id + " not found !!!");
    }

    public void deleteEmployee(long id){
        if(repository.findById(id).isPresent())
            repository.deleteById(id);
        else
            throw new RuntimeException("Employee with ID " + id + " does not exist !!!");
    }
}

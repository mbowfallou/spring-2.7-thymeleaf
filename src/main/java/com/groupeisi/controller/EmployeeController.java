package com.groupeisi.controller;

import com.groupeisi.entity.Employee;
import com.groupeisi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @GetMapping
    public String getAllEmployees(Model model){
        //model.addAttribute("employeesList", service.getAllEmployees());
        //return "index";

        return getPaginatedEmployees(1, "id", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String getPaginatedEmployees(@PathVariable int pageNo,
                                        @RequestParam String sortField,
                                        @RequestParam String sortDir,
                                        Model model){
        int pageSize = 5;
        Page<Employee> employeesPage = service.findPaginated(pageNo, pageSize, sortField, sortDir);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("employeesList", employeesPage.getContent());
        model.addAttribute("totalItems", employeesPage.getTotalElements());
        model.addAttribute("totalPages", employeesPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "index";
    }

    @GetMapping("/add")
    public String addEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employeeForm";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        employee.getFirstName().trim();
        employee.getLastName().trim();
        employee.getEmail().trim();
        service.addEmployer(employee);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable long id, Model model){
        model.addAttribute("employee", service.getEmployeeById(id));
        return "updateEmployee";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable long id){
        service.deleteEmployee(id);
        return "redirect:/";
    }
}

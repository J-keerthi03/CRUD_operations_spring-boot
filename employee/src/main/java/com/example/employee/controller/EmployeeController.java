package com.example.employee.controller;


import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
    @RequestMapping("/api/employees")
    public class EmployeeController {
        @Autowired
        private EmployeeRepository employeeRepository;

        @GetMapping
        public List<Employee> getAllEmployees() {
            return employeeRepository.findAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
            return ResponseEntity.ok().body(employee);
        }

        @PostMapping
        public Employee createEmployee(@Valid @RequestBody Employee employee) {
            return employeeRepository.save(employee);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                       @Valid @RequestBody Employee employeeDetails) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

            employee.setName(employeeDetails.getName());
            employee.setEmail(employeeDetails.getEmail());

            final Employee updatedEmployee = employeeRepository.save(employee);
            return ResponseEntity.ok(updatedEmployee);
        }

        @DeleteMapping("/{id}")
        public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

            employeeRepository.delete(employee);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
        }
    }



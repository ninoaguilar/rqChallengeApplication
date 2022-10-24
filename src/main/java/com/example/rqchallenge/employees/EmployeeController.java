package com.example.rqchallenge.employees;

import com.example.rqchallenge.dummyRestApi.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController implements IEmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * Returns all employees.
     *
     * @return list of employees
     * @throws IOException
     *            if the input is not valid
     */
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        return Optional.ofNullable(employeeService.getAllEmployees())
                .map(employees -> new ResponseEntity<>(employees, HttpStatus.OK))
                .orElseThrow(RuntimeException::new);
    }

    /**
     * Return all employees whose name contains or matches the string input provided.
     *
     * @param searchString  name of the desired employee
     * @return
     */
    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return Optional.ofNullable(employeeService.getEmployeesByNameSearch(searchString))
                .map(employees -> new ResponseEntity<>(employees, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Returns a single employee by their ID
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return Optional.ofNullable(employeeService.getEmployeeById(id))
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElse( ResponseEntity.internalServerError().build());
    }

    /**
     * Returns a single integer indicating the highest salary of all employees.
     *
     * @return integer of the highest salary
     */
    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return Optional.ofNullable(employeeService.getHighestSalaryOfEmployees())
                .map(salary -> new ResponseEntity<>(salary, HttpStatus.OK))
                .orElse( ResponseEntity.internalServerError().build());
    }

    /**
     * Returns a list names of the top 10 employees based off of their salaries.
     *
     * @return list of employees
     */
    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return Optional.ofNullable(employeeService.getTop10HighestEarningEmployeeNames())
                .map(employeeNames -> new ResponseEntity<>(employeeNames, HttpStatus.OK))
                .orElse( ResponseEntity.internalServerError().build());
    }

    /**
     * Creates an employee from the employeeInput.
     *
     * @param employeeInput employee to create
     * @return string of the status (i.e. success)
     */
    @Override
    public ResponseEntity<String> createEmployee(Map<String, Object> employeeInput) {
        return Optional.ofNullable(employeeService.createEmployee(EmployeeMapper.toEmployee(employeeInput)))
                .map(status ->
                    status == "success"
                    ? new ResponseEntity<>(status, HttpStatus.OK)
                    : new ResponseEntity<>(status, HttpStatus.BAD_REQUEST))
                .orElse( ResponseEntity.internalServerError().build());
    }

    /**
     * Deletes the employee with the given id.
     *
     * @param id id of the employee to delete
     * @return the name of the employee that was deleted
     */
    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return Optional.ofNullable(employeeService.getEmployeeById(id)).map(foundEmployee -> {
            employeeService.deleteEmployee(foundEmployee.getId());
            return new ResponseEntity<>(foundEmployee.getName(), HttpStatus.OK);
        }).orElse( ResponseEntity.notFound().build());
    }
}

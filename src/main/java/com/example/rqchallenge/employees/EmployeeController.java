package com.example.rqchallenge.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
        try {
            return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * Return all employees whose name contains or matches the string input provided.
     *
     * @param searchString  name of the desired employee
     * @return
     */
    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return new ResponseEntity<>(employeeService.getEmployeesByNameSearch(searchString), HttpStatus.OK);
    }

    /**
     * Returns a single employee by their ID
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    /**
     * Returns a single integer indicating the highest salary of all employees.
     *
     * @return integer of the highest salary
     */
    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return new ResponseEntity<>(employeeService.getHighestSalaryOfEmployees(), HttpStatus.OK);
    }

    /**
     * Returns a list names of the top 10 employees based off of their salaries.
     *
     * @return list of employees
     */
    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return new ResponseEntity<>(employeeService.getTop10HighestEarningEmployeeNames(), HttpStatus.OK);
    }

    /**
     * Creates an employee from the employeeInput.
     *
     * @param employeeInput employee to create
     * @return string of the status (i.e. success)
     */
    @Override
    public ResponseEntity<String> createEmployee(Map<String, Object> employeeInput) {
        return new ResponseEntity<>(employeeService.createEmployee(
                employeeInput.get("name").toString(),
                employeeInput.get("salary").toString(),
                employeeInput.get("age").toString()), HttpStatus.OK);
    }

    /**
     * Deletes the employee with the given id.
     *
     * @param id id of the employee to delete
     * @return the name of the employee that was deleted
     */
    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.OK);
    }
}

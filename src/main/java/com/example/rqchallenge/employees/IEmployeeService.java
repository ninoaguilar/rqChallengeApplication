package com.example.rqchallenge.employees;

import java.io.IOException;
import java.util.List;

public interface IEmployeeService {
    public List<Employee> getAllEmployees() throws IOException;
    public List<Employee> getEmployeesByNameSearch(String searchString);
    public Employee getEmployeeById(String id);
    public Integer getHighestSalaryOfEmployees();
    public List<String> getTop10HighestEarningEmployeeNames();
    public String createEmployee(Employee employee);
    public String deleteEmployee(String id);
}

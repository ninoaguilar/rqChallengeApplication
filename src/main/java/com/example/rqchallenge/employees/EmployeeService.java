package com.example.rqchallenge.employees;

import com.example.rqchallenge.dummyRestApi.DummyRestApiService;
import com.example.rqchallenge.dummyRestApi.EmployeeMapper;
import com.example.rqchallenge.dummyRestApi.models.CreateResponse;
import com.example.rqchallenge.utils.Utils;
import com.google.common.collect.ImmutableList;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
    private DummyRestApiService dummyRestApiService;

    public EmployeeService(DummyRestApiService restApiService) {
        dummyRestApiService = restApiService;
    }

    public List<Employee> getAllEmployees() throws IOException {
            return Optional.ofNullable(dummyRestApiService.getEmployees().data)
                    .orElseThrow(IOException::new)
                    .stream()
                    .map(EmployeeMapper::toEmployee)
                    .toList();
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
            return dummyRestApiService.getEmployees().data
                    .stream()
                    .map(EmployeeMapper::toEmployee)
                    .filter(Utils.NameContains(searchString))
                    .toList();
    }

    public Employee getEmployeeById(String id) {
            return dummyRestApiService.getEmployees().data
                    .stream()
                    .map(EmployeeMapper::toEmployee)
                    .filter(Utils.IsId(id))
                    .findFirst()
                    .orElse(null);
    }

    public Integer getHighestSalaryOfEmployees() {
            return dummyRestApiService.getEmployees().data
                    .stream()
                    .map(EmployeeMapper::toEmployee)
                    .map(employee -> Integer.parseInt(employee.getSalary()))
                    .reduce(0, Integer::max);
    }

    public List<String> getTop10HighestEarningEmployeeNames() {
            return dummyRestApiService.getEmployees().data
                    .stream()
                    .map(EmployeeMapper::toEmployee)
                    .sorted(Comparator.comparing(Employee::getSalary).reversed())
                    .toList()
                    .subList(0, 10)
                    .stream()
                    .map(employee -> employee.getName())
                    .toList();
    }

    public String createEmployee(String name, String salary, String age) {
        return dummyRestApiService.createEmployee(Employee.of(name, salary, age)).status;
    }

    public String deleteEmployee(String id) {
        return Optional.ofNullable(getEmployeeById(id)).map(employee -> {
            dummyRestApiService.deleteEmployeeById(employee.getId());
            return employee.getName();
        }).orElse("User Not Found.");
    }
}

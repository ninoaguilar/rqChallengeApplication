package com.example.rqchallenge.employees;

import com.example.rqchallenge.dummyRestApi.EmployeeMapper;
import com.example.rqchallenge.dummyRestApi.models.CreateResponse;
import com.example.rqchallenge.dummyRestApi.models.EmployeesResponse;
import com.example.rqchallenge.utils.Utils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final String BASE_URL = "https://dummy.restapiexample.com/api";

    private enum EndpointLabels
    {
            EMPLOYEE,
            EMPLOYEE_BY_ID,
            CREATE_EMPLOYEE,
            DELETE_EMPLOYEE,
    }
    private final ImmutableMap<EndpointLabels, String> endpoints = ImmutableMap.of(
            EndpointLabels.EMPLOYEE, BASE_URL + "/v1/employees",
            EndpointLabels.EMPLOYEE_BY_ID, BASE_URL + "/v1/employees/id",
            EndpointLabels.CREATE_EMPLOYEE, BASE_URL + "/v1/create",
            EndpointLabels.DELETE_EMPLOYEE, BASE_URL + "/v1/delete"
    );
    private final RestTemplate restTemplate;


    public EmployeeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Employee> getAllEmployees() throws IOException {
        try {
            return Optional.ofNullable(restTemplate.getForObject(endpoints.get(EndpointLabels.EMPLOYEE), EmployeesResponse.class))
                    .map(EmployeeMapper::toEmployees)
                    .orElseThrow(() -> new Exception());
        } catch (Exception e) {
            throw new IOException(e);
        }

    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        try {
            return getAllEmployees()
                    .stream()
                    .filter(Utils.NameContains(searchString))
                    .toList();
        } catch (IOException e) {
            return ImmutableList.of();
        }
    }

    public Employee getEmployeeById(String id) {
        try {
            return getAllEmployees()
                    .stream()
                    .filter(Utils.IsId(id))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            return null;
        }
    }

    public Integer getHighestSalaryOfEmployees() {
        try {
            return getAllEmployees()
                    .stream()
                    .map(employee -> Integer.parseInt(employee.getSalary()))
                    .reduce(0, Integer::max);
        } catch (IOException e) {
            return 0;
        }
    }

    public List<String> getTop10HighestEarningEmployeeNames() {
        try {
            return getAllEmployees()
                    .stream()
                    .sorted(Comparator.comparing(Employee::getSalary).reversed())
                    .toList()
                    .subList(0, 10)
                    .stream()
                    .map(employee -> employee.getName())
                    .toList();
        } catch (IOException e) {
            return List.of();
        }
    }

    public String createEmployee(String name, String salary, String age) {
        CreateResponse response = restTemplate.postForObject(endpoints.get(EndpointLabels.CREATE_EMPLOYEE), Employee.of(name, salary, age), CreateResponse.class);
        return response.status;
    }

    public String deleteEmployee(String id) {
        Employee employee = getEmployeeById(id);
        restTemplate.delete(endpoints.get(EndpointLabels.DELETE_EMPLOYEE), id);
        return employee.getName();
    }
}

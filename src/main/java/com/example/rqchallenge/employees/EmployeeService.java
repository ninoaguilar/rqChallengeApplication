package com.example.rqchallenge.employees;

import com.example.rqchallenge.dummyRestApi.DummyRestApiService;
import com.example.rqchallenge.dummyRestApi.EmployeeMapper;
import com.example.rqchallenge.dummyRestApi.models.CreateResponse;
import com.example.rqchallenge.utils.CustomClientHttpRequestInterceptor;
import com.example.rqchallenge.utils.Utils;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {

    private static Logger LOGGER = LoggerFactory
            .getLogger(CustomClientHttpRequestInterceptor.class);
    private final DummyRestApiService dummyRestApiService;

    @Autowired
    public EmployeeService(DummyRestApiService restApiService) {

        dummyRestApiService = restApiService;
    }

    public List<Employee> getAllEmployees() throws IOException {
            return Optional.ofNullable(dummyRestApiService.getEmployees().data)
                    .orElseThrow(RuntimeException::new)
                    .stream()
                    .map(EmployeeMapper::toEmployee)
                    .toList();
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        try {
            return getAllEmployees()
                    .stream()
                    .filter(Utils.NameContains(searchString))
                    .toList();
        } catch (IOException e) {
            LOGGER.error("Failed to get response from dummyRestApiService getAllEmployees()");
            return ImmutableList.of();
        }
    }

    public Employee getEmployeeById(String id) {
        try {
            return getAllEmployees()
                    .stream()
                    .filter(Utils.IsId(id))
                    .findFirst()
                    .orElseGet(() -> {
                        LOGGER.info("User of id={} was not found.", id);
                        return null;
                    });
        } catch (IOException e) {
            return null;
        }
    }

    public Integer getHighestSalaryOfEmployees() {
        try {
            return dummyRestApiService.getEmployees().data
                    .stream()
                    .map(EmployeeMapper::toEmployee)
                    .map(employee -> Integer.parseInt(employee.getSalary()))
                    .reduce(0, Integer::max);
        } catch (IOException e) {
            return 0;
        }
    }

    public List<String> getTop10HighestEarningEmployeeNames() {
        try {
            return dummyRestApiService.getEmployees().data
                    .stream()
                    .map(EmployeeMapper::toEmployee)
                    .sorted(Comparator.comparing(Employee::getSalary).reversed())
                    .limit(10)
                    .map(employee -> employee.getName())
                    .toList();
        } catch (IOException e) {
            return ImmutableList.of();
        }
    }

    public String createEmployee(Employee employee) {
        return dummyRestApiService.createEmployee(employee).status;
    }

    public String deleteEmployee(String id) {
        return Optional.ofNullable(getEmployeeById(id)).map(employee -> {
            dummyRestApiService.deleteEmployeeById(employee.getId());
            return employee.getName();
        }).orElse("User Not Found.");
    }
}

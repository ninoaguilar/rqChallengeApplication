package com.example.rqchallenge.dummyRestApi;

import com.example.rqchallenge.dummyRestApi.models.CreateResponse;
import com.example.rqchallenge.dummyRestApi.models.EmployeesResponse;
import com.example.rqchallenge.employees.Employee;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class DummyRestApiService {
    private final String BASE_URL = "https://dummy.restapiexample.com/api";

    private enum EndpointLabels
    {
        EMPLOYEE,
        CREATE_EMPLOYEE,
        DELETE_EMPLOYEE,
    }
    private final ImmutableMap<EndpointLabels, String> endpoints = ImmutableMap.of(
            EndpointLabels.EMPLOYEE, BASE_URL + "/v1/employees",
            EndpointLabels.CREATE_EMPLOYEE, BASE_URL + "/v1/create",
            EndpointLabels.DELETE_EMPLOYEE, BASE_URL + "/v1/delete"
    );

    private RestTemplate restTemplate;

    @Autowired
    public DummyRestApiService (RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public EmployeesResponse getEmployees() throws IOException {
        return restTemplate.getForObject(endpoints.get(EndpointLabels.EMPLOYEE), EmployeesResponse.class);
    }

    public CreateResponse createEmployee(Employee employee) {
        return restTemplate.postForObject(endpoints.get(EndpointLabels.CREATE_EMPLOYEE), employee, CreateResponse.class);
    }

    public void deleteEmployeeById(String id) {
        restTemplate.delete(endpoints.get(EndpointLabels.DELETE_EMPLOYEE), id);
    }
}

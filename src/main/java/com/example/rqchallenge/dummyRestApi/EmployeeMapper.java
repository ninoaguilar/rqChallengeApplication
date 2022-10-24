package com.example.rqchallenge.dummyRestApi;

import com.example.rqchallenge.dummyRestApi.models.DummyEmployee;
import com.example.rqchallenge.dummyRestApi.models.EmployeesResponse;
import com.example.rqchallenge.employees.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class EmployeeMapper {
    static final private ObjectMapper mapper = new ObjectMapper();

    public static Employee toEmployee(Map<String, Object> employeeInput) {
        return mapper.convertValue(employeeInput, Employee.class);
    }
    public static Employee toEmployee(DummyEmployee dummyEmployee) {
        return Employee.of(
                dummyEmployee.id,
                dummyEmployee.employee_name,
                dummyEmployee.employee_salary,
                dummyEmployee.employee_age,
                dummyEmployee.profile_image);
    }
}

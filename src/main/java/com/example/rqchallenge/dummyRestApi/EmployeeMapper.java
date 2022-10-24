package com.example.rqchallenge.dummyRestApi;

import com.example.rqchallenge.dummyRestApi.models.DummyEmployee;
import com.example.rqchallenge.dummyRestApi.models.EmployeesResponse;
import com.example.rqchallenge.employees.Employee;

import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

public class EmployeeMapper {
    public static List<Employee> toEmployees(EmployeesResponse employeesResponse) {
        return employeesResponse.data.stream().map(EmployeeMapper::toEmployee).collect(toImmutableList());
    }

    public static Employee toEmployee(DummyEmployee dummyEmployee) {
        return Employee.of(dummyEmployee.employee_name, dummyEmployee.employee_salary, dummyEmployee.employee_age);
    }
}

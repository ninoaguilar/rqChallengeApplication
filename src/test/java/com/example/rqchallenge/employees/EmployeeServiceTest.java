package com.example.rqchallenge.employees;

import com.example.rqchallenge.dummyRestApi.DummyRestApiService;
import com.example.rqchallenge.dummyRestApi.models.DummyEmployee;
import com.example.rqchallenge.dummyRestApi.models.EmployeesResponse;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
@Import(EmployeeController.class)
public class EmployeeServiceTest {

    @MockBean
    @Autowired
    private DummyRestApiService dummyRestApiService;

    @MockBean
    private EmployeeService employeeService;

    private static EmployeesResponse employeesResponse;

    @BeforeAll
    public static void setup() {
        final DummyEmployee employee1 = new DummyEmployee();
        employee1.employee_name = "test";
        employee1.employee_age = "11";
        employee1.employee_salary = "11111";

        final DummyEmployee employee2 = new DummyEmployee();
        employee2.employee_name = "test2";
        employee2.employee_age = "22";
        employee2.employee_salary = "22222";

        final DummyEmployee employee3 = new DummyEmployee();
        employee3.employee_name = "test3";
        employee3.employee_age = "33";
        employee3.employee_salary = "333333";

        employeesResponse = new EmployeesResponse();
        employeesResponse.status = "success";
        employeesResponse.data = ImmutableList.of(employee1, employee2, employee3);
    }

    @Test
    void getAllEmployees_Successfully() throws Exception {
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);

        var employees = employeeService.getAllEmployees();

        Assertions.assertEquals(employees.stream().count(), 3);
    }
}

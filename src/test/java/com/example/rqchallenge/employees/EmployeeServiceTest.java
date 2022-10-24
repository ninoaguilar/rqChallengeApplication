package com.example.rqchallenge.employees;

import com.example.rqchallenge.dummyRestApi.DummyRestApiService;
import com.example.rqchallenge.dummyRestApi.models.CreateResponse;
import com.example.rqchallenge.dummyRestApi.models.DummyCreatedEmployee;
import com.example.rqchallenge.dummyRestApi.models.EmployeesResponse;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.io.IOException;
import java.util.List;

import static com.example.rqchallenge.employees.EmployeeTestData.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private RestTemplateBuilder builder;
    @Mock
    private DummyRestApiService dummyRestApiService;
    @InjectMocks
    private EmployeeService employeeService;

    private static EmployeesResponse employeesResponse;

    @BeforeAll
    public static void setup() {
        employeesResponse = new EmployeesResponse();
        employeesResponse.status = "success";
        employeesResponse.data = ImmutableList.of(dummyEmployee1, dummyEmployee2, dummyEmployee3, dummyEmployee4, dummyEmployee5, dummyEmployee6);
    }

    @Test
    void getAllEmployees_returnsListOfEmployees() throws IOException {
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);

        ImmutableList<Employee> employees = ImmutableList.copyOf(employeeService.getAllEmployees());

        Assertions.assertEquals((long) employees.size(), employeesResponse.data.size());
    }

    @Test
    void getAllEmployees_throwsIOException() throws IOException {
        Mockito.when(dummyRestApiService.getEmployees()).thenThrow(IOException.class);

        Assertions.assertThrows(IOException.class, () -> ImmutableList.copyOf(employeeService.getAllEmployees()));
    }

    @Test
    void getEmployeesByNameSearch_returnsFoundResults() throws IOException {
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);

        ImmutableList<Employee> employees = ImmutableList.copyOf(employeeService.getEmployeesByNameSearch("bruce"));

        Assertions.assertEquals((long) employees.size(), 2);
    }

    @Test
    void getEmployeesByNameSearch_returnsFoundResultsRegardlessOfCasing() throws IOException {
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);

        ImmutableList<Employee> employees = ImmutableList.copyOf(employeeService.getEmployeesByNameSearch("bRuCe"));

        Assertions.assertEquals((long) employees.size(), 2);
    }

    @Test
    void getEmployeesByNameSearch_returnsNoResults() throws IOException {
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);

        ImmutableList<Employee> employees = ImmutableList.copyOf(employeeService.getEmployeesByNameSearch("test"));

        Assertions.assertEquals((long) employees.size(), 0);
    }

    @Test
    void getEmployeeById_returnsFoundEmployee() throws IOException {
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);

        Employee actualEmployee = employeeService.getEmployeeById("3");

        Assertions.assertNotNull(actualEmployee);
        Assertions.assertEquals(actualEmployee.getId(), "3");
        Assertions.assertEquals(actualEmployee.getName(), employee3.getName());
        Assertions.assertEquals(actualEmployee.getSalary(), employee3.getSalary());
        Assertions.assertEquals(actualEmployee.getAge(), employee3.getAge());
        Assertions.assertEquals(actualEmployee.getProfilePicture(), "");
    }

    @Test
    void getEmployeeById_returnsNullForNotFoundEmployee() throws IOException {
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);

        Employee actualEmployee = employeeService.getEmployeeById("9999");

        Assertions.assertNull(actualEmployee);
    }

    @Test
    void getHighestSalaryOfEmployees_returnCorrectResult() throws IOException {
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);

        Integer actualHighestSalary = employeeService.getHighestSalaryOfEmployees();
        Integer expectedHighestSalary = 9876543;

        Assertions.assertEquals(actualHighestSalary, expectedHighestSalary);
    }

    @Test
    void getTop10HighestEarningEmployeeNames_returnCorrectResult() throws IOException {
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);

        List<String> actualEmployeeNames = employeeService.getTop10HighestEarningEmployeeNames();
        List<String> expectedEmployeeNames = ImmutableList.of("Eminem", "Steve Jobs", "Michelle Jordan", "Micheal Jordan", "Bruce Wayne", "Bruce Wine");

        Assertions.assertEquals(actualEmployeeNames, expectedEmployeeNames);
    }

    @Test
    void createEmployee_returnSuccessStatus() {
        DummyCreatedEmployee employee = new DummyCreatedEmployee();
        employee.name = "Nino Aguilar";
        employee.salary = "450000";
        employee.age = "35";

        CreateResponse response = new CreateResponse();
        response.status = "success";
        response.data = employee;

        Employee employee7 = Employee.of(employee.name, employee.salary, employee.age);

        Mockito.when(dummyRestApiService.createEmployee(employee7)).thenReturn(response);

        String actualEmployeeName = employeeService.createEmployee(employee7);
        String expectedEmployeeName = response.status;

        Assertions.assertEquals(actualEmployeeName, expectedEmployeeName);
    }

    @Test
    void deleteEmployee_returnDeletedEmployeesName() throws IOException {
        final String idToDelete = "4";
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);
        Mockito.doNothing().when(dummyRestApiService).deleteEmployeeById(idToDelete);

        String actualEmployeeName = employeeService.deleteEmployee(idToDelete);
        String expectedEmployeeName = employee4.getName();

        Assertions.assertEquals(actualEmployeeName, expectedEmployeeName);
    }

    @Test
    void deleteEmployee_returnNoUserFoundWhenNoUser() throws IOException {
        final String idToDelete = "99";
        Mockito.when(dummyRestApiService.getEmployees()).thenReturn(employeesResponse);

        String actualEmployeeName = employeeService.deleteEmployee(idToDelete);
        String expectedEmployeeName = "User Not Found.";

        Assertions.assertEquals(actualEmployeeName, expectedEmployeeName);
    }
}

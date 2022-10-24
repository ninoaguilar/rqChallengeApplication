package com.example.rqchallenge.employees;

import com.example.rqchallenge.dummyRestApi.EmployeeMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.rqchallenge.employees.EmployeeTestData.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
@Import(EmployeeController.class)
class EmployeeControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private static ImmutableList<Employee> employees;

    @BeforeAll
    public static void setup() {
        employees = ImmutableList.of(employee1, employee2, employee3, employee4, employee5, employee6);
    }

    @Test
    void GetAllEmployees_Successfully() throws Exception {
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/v1/employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(employee1.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(employee1.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employee2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(employee3.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].name").value(employee4.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].name").value(employee5.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[5].name").value(employee6.getName()));
    }

    @Test
    void getEmployeesByNameSearch_Successfully() throws Exception {
        Mockito.when(employeeService.getEmployeesByNameSearch("bruce")).thenReturn(employees);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/v1/employees/search/bruce")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(6)));
    }

    @Test
    void getEmployeesById_Successfully() throws Exception {
        Mockito.when(employeeService.getEmployeeById("1")).thenReturn(employees.get(0));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/v1/employees/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", aMapWithSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Steve Jobs"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("77777"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("56"));
    }

    @Test
    void getHighestSalaryOfEmployees_Successfully() throws Exception {
        Mockito.when(employeeService.getHighestSalaryOfEmployees()).thenReturn(9876543);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/v1/employees/highestSalary")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(9876543));
    }

    @Test
    void getTop10HighestEarningEmployeeNames_Successfully() throws Exception {
        Mockito.when(employeeService.getTop10HighestEarningEmployeeNames()).thenReturn(employees.stream().map(Employee::getName).toList());

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/v1/employees/topTenHighestEarningEmployeeNames")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(6)));
    }

    @Test
    void createEmployee_Successfully() throws Exception {
        Mockito.when(employeeService.createEmployee(Mockito.any())).thenReturn("success");

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/v1/employees")
                                .content("{ \"name\": \"test\", \"salary\": \"1000\", \"age\": \"25\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("success"));
    }

    @Test
    void deleteEmployee_Successfully() throws Exception {
        Mockito.when(employeeService.deleteEmployee("1")).thenReturn("name");
        Mockito.when(employeeService.getEmployeeById("1")).thenReturn(Employee.of("bob", "1", "1"));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/v1/employees/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("bob"));
    }
}

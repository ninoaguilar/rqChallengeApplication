package com.example.rqchallenge.employees;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class EmployeeControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private static ImmutableList<Employee> employees;

    @BeforeAll
    public static void setup() {
        final Employee employee1 = Employee.of("Steve Jobs", "77777", "56");
        final Employee employee2 = Employee.of("Bruce Wayne", "1234567", "32");
        final Employee employee3 = Employee.of("Bruce Wine", "1234567", "25");
        final Employee employee4 = Employee.of("Micheal Jordan", "232323", "65");
        final Employee employee5 = Employee.of("Michelle Jordan", "55555", "77");
        final Employee employee6 = Employee.of("Eminem", "9876543", "50");

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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Steve Jobs"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value("77777"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value("56"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Bruce Wayne"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Bruce Wine"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].name").value("Micheal Jordan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].name").value("Michelle Jordan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[5].name").value("Eminem"));
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
}

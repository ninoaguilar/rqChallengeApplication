package com.example.rqchallenge.employees;

import com.example.rqchallenge.dummyRestApi.models.DummyEmployee;

public class EmployeeTestData {
    public static final DummyEmployee dummyEmployee1 = DummyEmployee.of("1", "Steve Jobs", "77777", "56", "");
    public static final DummyEmployee dummyEmployee2 = DummyEmployee.of("2", "Bruce Wayne", "1234567", "32", "");
    public static final DummyEmployee dummyEmployee3 = DummyEmployee.of("3", "Bruce Wine", "1234567", "25", "");
    public static final DummyEmployee dummyEmployee4 = DummyEmployee.of("4", "Micheal Jordan", "232323", "65", "");
    public static final DummyEmployee dummyEmployee5 = DummyEmployee.of("5", "Michelle Jordan", "55555", "77", "");
    public static final DummyEmployee dummyEmployee6 = DummyEmployee.of("6", "Eminem", "9876543", "50", "");

    public static final Employee employee1 = Employee.of("Steve Jobs", "77777", "56");
    public static final Employee employee2 = Employee.of("Bruce Wayne", "1234567", "32");
    public static final Employee employee3 = Employee.of("Bruce Wine", "1234567", "25");
    public static final Employee employee4 = Employee.of("Micheal Jordan", "232323", "65");
    public static final Employee employee5 = Employee.of("Michelle Jordan", "55555", "77");
    public static final Employee employee6 = Employee.of("Eminem", "9876543", "50");

}

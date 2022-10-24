package com.example.rqchallenge.dummyRestApi.models;

public class DummyEmployee {
    public String id;
    public String employee_name;
    public String employee_salary;
    public String employee_age;
    public String profile_image;

    private DummyEmployee() {}

    private DummyEmployee(String id, String name, String salary, String age, String profile_image) {
        this.id = id;
        this.employee_name = name;
        this.employee_salary = salary;
        this.employee_age = age;
        this.profile_image = profile_image;
    }

    public static DummyEmployee of(String id, String name, String salary, String age, String profile_image) {
        return new DummyEmployee(id, name, salary, age, profile_image);
    }
}


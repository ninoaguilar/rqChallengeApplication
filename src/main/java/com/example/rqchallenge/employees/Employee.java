package com.example.rqchallenge.employees;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {
    private String id;
    private String name;
    private String salary;
    private String age;
    private String profilePicture;;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    private Employee() {}

    public static Employee of(String name, String salary, String age) {
        Employee employee = new Employee();

        employee.name = name;
        employee.salary = salary;
        employee.age = age;

        return employee;
    }

    public static Employee of(String id, String name, String salary, String age, String profilePicture) {
        Employee employee = new Employee();

        employee.id = id;
        employee.name = name;
        employee.salary = salary;
        employee.age = age;
        employee.profilePicture = profilePicture;

        return employee;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name=" + name +
                ", salary=" + salary +
                ", age=" + age +
                ", profilePicture=" + profilePicture + '\'' +
                '}';
    }
}

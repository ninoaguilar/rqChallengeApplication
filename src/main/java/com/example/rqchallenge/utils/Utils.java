package com.example.rqchallenge.utils;

import com.example.rqchallenge.employees.Employee;

import java.util.function.Predicate;

public class Utils
{
    public static Predicate<Employee> NameContains(String searchString) {
        return p -> p.getName().toLowerCase().contains(searchString.toLowerCase());
    }

    public static Predicate<Employee> IsId(String id) {
        return p -> p.getId() == id;
    }

    public static Predicate<Employee> HighestSalary(String searchString) {
        return p -> p.getName().toLowerCase() == searchString.toLowerCase();
    }
}

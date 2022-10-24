package com.example.rqchallenge.dummyRestApi;

import java.util.List;

public class EmployeesResponse {
    public String status;
    public List<DummyEmployee> data;
}

public class EmployeeResponse {
    public String status;
    public DummyEmployee data;
}

public class CreateResponse {
    public String status;
    public DummyCreatedEmployee data;
}

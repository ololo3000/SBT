package com.sbt.javaschool.common;

public class Person {
    private Company company;
    private String name;

    public Person(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public Company getCompany() {
        return company;
    }
}

package com.sbt.javaschool.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Company {
    private String name;
    private List<PersonKey> personsKeys = new ArrayList<>();

    public Company (String name) {
        this.name = name;
    }

    public void addPerson(PersonKey key) {
        personsKeys.add(key);
    }

    public List<PersonKey> getPersonKeys() {
        return Collections.unmodifiableList(personsKeys);
    }

    public String getName() {
        return name;
    }
}

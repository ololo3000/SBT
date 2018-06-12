package com.sbt.javaschool.common;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

public class PersonKey {

    private String personId;

    @AffinityKeyMapped
    private String companyId;

    public PersonKey(String personId, String companyId) {
        this.companyId = companyId;
        this.personId = personId;
    }

    public PersonKey(Person pearson) {
        personId = pearson.getName();
        companyId = pearson.getCompany().getName();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PersonKey)) {
            return false;
        }
        PersonKey personKey = (PersonKey)obj;
        return personId.equals(personKey.personId)
                && companyId.equals(personKey.companyId) ;
    }

    @Override
    public int hashCode() {
        return (companyId + personId).hashCode();
    }
}

package com.sbt.javaschool.benchmark;

import com.sbt.javaschool.common.Company;
import com.sbt.javaschool.common.Person;
import com.sbt.javaschool.common.PersonKey;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.yardstick.IgniteAbstractBenchmark;
import org.yardstickframework.BenchmarkConfiguration;

public abstract class GetAbstractBenchmark extends IgniteAbstractBenchmark {
    private IgniteCache<PersonKey, Person> personCache;
    private Company com;
    public final String PERSONS_CACHE_NAME ="PersonsCache";

    @Override
    public void setUp(BenchmarkConfiguration cfg) throws Exception {
        super.setUp(cfg);
        personCache = ignite().cache(PERSONS_CACHE_NAME);

        Company company = new Company("com");
        for (int i = 0; i < 100; i++) {
            Person person = new Person(Integer.toString(i), company);
            PersonKey personKey = new PersonKey(person);
            personCache.put(personKey, person);
            company.addPerson(personKey);
        }
        com = company;



    }

    public Company getCompany() {
        return com;
    }

    public IgniteCache<PersonKey, Person> getPersonCache() {
        return personCache;
    }
}

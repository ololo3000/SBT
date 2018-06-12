package com.sbt.javaschool.benchmark;

import com.sbt.javaschool.common.Person;
import com.sbt.javaschool.common.PersonKey;
import org.apache.ignite.lang.IgniteCallable;

import java.util.Map;

public class GetBenchmark extends GetAbstractBenchmark {

    @Override
    public boolean test(Map<Object, Object> ctx) throws Exception {
        Boolean result = ignite().compute().call(
                new IgniteCallable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        for(PersonKey key : getCompany().getPersonKeys()) {
                            Person person = getPersonCache().get(key);
                        }
                        return true;
                    }
                });

        return result;
    }
}

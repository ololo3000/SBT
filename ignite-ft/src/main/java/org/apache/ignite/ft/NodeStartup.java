package org.apache.ignite.ft;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import javax.cache.Cache;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

/**
 *
 */
public class NodeStartup {
    /** */
    public static void main(String[] args) {
        IgniteConfiguration cfg = new IgniteConfiguration();

        cfg.setIgniteInstanceName("test-node");

        Ignite ignite = Ignition.start(cfg);

        final String TEST_CACHE_NAME = "testCache";

        CacheConfiguration<TestClass, Object> cCfg = new CacheConfiguration<>(TEST_CACHE_NAME);

        ignite.destroyCache(TEST_CACHE_NAME);

        IgniteCache<TestClass, Object> cache = ignite.createCache(cCfg);

        assert cache.size() == 0;

        Map<Integer, Boolean> map = new HashMap<>();

        map.put(1, false);
        map.put(2, false);
        map.put(3, false);
        map.put(4, false);
        map.put(5, false);
        map.put(6, false);
        map.put(7, false);
        map.put(8, false);
        map.put(9, false);
        map.put(10, false);
        map.put(19, false);
        map.put(20, false);

        assert map.size() == 12;

        TestClass testCls = new TestClass(map);

        cache.put(testCls, new Object());

        assert cache.size() == 1;

        final Iterator<Cache.Entry<TestClass, Object>> iter = cache.iterator();

        TestClass k = iter.next().getKey();

        assert k != testCls;

        assert k.equals(testCls);

        assert Objects.equals(k.hashCode(), testCls.hashCode());

        assert cache.containsKey(new TestClass(map));

        assert cache.containsKey(testCls);

        assert cache.containsKey(k);

        assert cache.remove(k);

        assert !cache.containsKey(k);

        Ignition.stop("test-node", true);
    }

    /** */
    private static class TestClass {

        /** */
        public Map<Integer, Boolean> map;

        /** */
        public TestClass(Map<Integer, Boolean> map) {
            this.map = new HashMap<>(map);
        }

        /** {@inheritDoc} */
        @Override public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof TestClass))
                return false;

            TestClass cls = (TestClass)o;

            return Objects.equals(map, cls.map);
        }

        /** {@inheritDoc} */
        @Override public int hashCode() {
            return Objects.hash(map);
        }
    }
}

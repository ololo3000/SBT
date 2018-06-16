package org.apache.ignite;

import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.WALMode;
import org.apache.ignite.internal.IgniteEx;
import org.apache.ignite.testframework.junits.common.GridCommonAbstractTest;
import org.junit.Assert;

import java.util.HashSet;
import java.util.Set;

public class CheckNodesTest extends GridCommonAbstractTest {
    public final int TEST_NODE_CNT = 3;

    @Override
    protected void afterTest() throws Exception {
        stopAllGrids();
        cleanPersistenceDir();
    }

    public Set<String> startNodes(int cnt, ConfigurationGenerator generator) throws Exception {
        Set<String> nodeNames = new HashSet<>();
        for (int i = 0; i < cnt; ++i) {
            IgniteEx igniteEx = startGrid(generator.getConfiguration());
            nodeNames.add(igniteEx.name());
        }
        return nodeNames;
    }

    public void testInMemoryMode() throws Exception {
        Set<String> nodeNames = startNodes(TEST_NODE_CNT, new ConfigurationGenerator() {
            private int idx = 0;

            @Override
            public IgniteConfiguration getConfiguration() throws Exception {
                return CheckNodesTest.this
                    .getConfiguration(getTestIgniteInstanceName(idx++));
            }
        });

        for (String nodeName : nodeNames) {
            Assert.assertEquals(TEST_NODE_CNT, grid(nodeName).cluster().nodes().size());
        }
    }

    public void testPersistanceMode() throws Exception {
        Set<String> nodeNames = startNodes(TEST_NODE_CNT, new ConfigurationGenerator() {
            private int idx = 0;

            private DataStorageConfiguration memCfg = new DataStorageConfiguration()
                .setDefaultDataRegionConfiguration(
                    new DataRegionConfiguration().setMaxSize(50 * 1024 * 1024).setPersistenceEnabled(true))
                .setPageSize(1024);

            @Override
            public IgniteConfiguration getConfiguration() throws Exception {
                return CheckNodesTest.this
                    .getConfiguration(getTestIgniteInstanceName(idx++))
                    .setDataStorageConfiguration(memCfg);
            }
        });

        for (String nodeName : nodeNames) {
            Assert.assertEquals(TEST_NODE_CNT, grid(nodeName).cluster().nodes().size());
        }
    }

    @FunctionalInterface
    interface ConfigurationGenerator {
        IgniteConfiguration getConfiguration() throws Exception;
    }
}

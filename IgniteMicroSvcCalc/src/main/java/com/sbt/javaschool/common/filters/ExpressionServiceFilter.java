package com.sbt.javaschool.common.filters;

import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgnitePredicate;

public class ExpressionServiceFilter implements IgnitePredicate<ClusterNode> {
    @Override
    public boolean apply(ClusterNode clusterNode) {
        if (clusterNode.isClient()) {
            return false;
        }

        Boolean divisionSvcNode = clusterNode.attribute("expression.service.node");
        if (divisionSvcNode != null && divisionSvcNode) {
            return true;
        }

        return false;
    }
}

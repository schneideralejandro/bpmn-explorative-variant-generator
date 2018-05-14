package com.github.schneideralejandro.pattern;

import org.jbpt.pm.FlowNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Pattern {
  private FlowNode flowNode;
  private Map<FlowNode, Set<FlowNode>> controlFlows;

  Pattern(FlowNode flowNode, InsertionPoint insertionPoint) {
    this.flowNode = flowNode;
    this.controlFlows = new HashMap<>();
    // To flowNode.
    insertionPoint.getSources()
      .forEach(s -> controlFlows.put(s, Collections.singleton(flowNode)));
    // From flowNode.
    controlFlows.put(flowNode, insertionPoint.getTargets());
  }

  FlowNode getFlowNode() {
    return flowNode;
  }

  Map<FlowNode, Set<FlowNode>> getControlFlows() {
    return controlFlows;
  }
}
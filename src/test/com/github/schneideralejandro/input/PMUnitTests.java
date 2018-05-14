package com.github.schneideralejandro.input;

import org.jbpt.pm.ControlFlow;
import org.jbpt.pm.FlowNode;
import org.jbpt.pm.ProcessModel;
import org.jbpt.pm.bpmn.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.UnsupportedOperationException;
import java.util.Collection;
import java.util.Optional;

@ExtendWith(PMProvider.class)
class PMUnitTests {
  @Test
  void processModelIsWrapped() {
    ProcessModel before = new ProcessModel();
    PM pm = PM.getRoot(before);
    ProcessModel after = pm.getProcessModel();
    assertEquals(before, after);
  }

  @Test
  void flowNodesFieldIsImmutable(PM pm) {
    assertThrows(UnsupportedOperationException.class, 
      () -> pm.getFlowNodes().add(new Task()));
  }

  private <E> Optional<E> getRandom(Collection<E> c) {
    if (c.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(c.stream().findAny().get());
    }
  }

  @Test
  void flowNodesAreMutable(PM pm) {
    Optional<FlowNode> optional = getRandom(pm.getFlowNodes());
    assumeTrue(optional.isPresent(), "Aborted: No flow-nodes to mutate.");
    FlowNode flowNode = optional.get();
    String name = "Mutable";
    flowNode.setName(name);
    assertEquals(name, flowNode.getName());
  }

  @Test
  void controlFlowsFieldIsImmutable(PM pm) {
    Optional<ControlFlow<FlowNode>> optional = getRandom(pm.getControlFlows());
    assumeTrue(optional.isPresent(), "Aborted: No control-flows to get.");
    ControlFlow<FlowNode> controlFlow = optional.get();
    assertThrows(UnsupportedOperationException.class,
      () -> pm.getControlFlows().add(controlFlow));
  }

  @Test
  void controlFlowsAreMutable(PM pm) {
    Optional<ControlFlow<FlowNode>> optional = getRandom(pm.getControlFlows());
    assumeTrue(optional.isPresent(), "Aborted: No control-flow to mutate.");
    ControlFlow<FlowNode> controlFlow = optional.get();
    String name = "Mutable";
    controlFlow.setName(name);
    assertEquals(name, controlFlow.getName());
  }

  @Test
  void upstreamQueryResultIsImmutable(PM pm) {
    Optional<FlowNode> optional = getRandom(pm.getFlowNodes());
    assumeTrue(optional.isPresent(), "Aborted: No flow-nodes for query.");
    FlowNode flowNode = optional.get();
    assertThrows(UnsupportedOperationException.class,
      () -> pm.getUpstream(flowNode).add(flowNode));
  }

  @Test
  void downstreamQueryResultIsImmutable(PM pm) {
    Optional<FlowNode> optional = getRandom(pm.getFlowNodes());
    assumeTrue(optional.isPresent(), "Aborted: No flow-nodes for query.");
    FlowNode flowNode = optional.get();
    assertThrows(UnsupportedOperationException.class,
      () -> pm.getDownstream(flowNode).add(flowNode));
  }

  @Test
  void transferringQueryResultIsImmutable(PM pm) {
    Optional<FlowNode> optional = getRandom(pm.getFlowNodes());
    assumeTrue(optional.isPresent(), "Aborted: No flow-nodes for query.");
    FlowNode flowNode = optional.get();
    assertThrows(UnsupportedOperationException.class,
      () -> pm.getTransferring(flowNode).add(flowNode));
  }

  @Test
  void transferredQueryResultIsImmutable(PM pm) {
    Optional<FlowNode> optional = getRandom(pm.getFlowNodes());
    assumeTrue(optional.isPresent(), "Aborted: No flow-nodes for query.");
    FlowNode flowNode = optional.get();
    assertThrows(UnsupportedOperationException.class, 
      () -> pm.getTransferred(flowNode).add(flowNode));
  }

  @Test
  void usesProcessModelIdentity() {
    ProcessModel processModelA = new ProcessModel();
    ProcessModel processModelB = new ProcessModel();
    PM pmA = PM.getRoot(processModelA);
    PM pmB = PM.getRoot(processModelB);
    assertNotEquals(pmA, pmB);
    processModelB.setId(processModelA.getId());
    assertEquals(pmA, pmB);
  }

  @Test
  void usesProcessModelHashCode() {
    ProcessModel processModel = new ProcessModel();
    PM pm = PM.getRoot(processModel);
    assertEquals(processModel.hashCode(), pm.hashCode());
  }
}
package com.github.schneideralejandro.input;

import org.jbpt.pm.AndGateway;
import org.jbpt.pm.FlowNode;
import org.jbpt.pm.ProcessModel;
import org.jbpt.pm.XorGateway;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ProcessModelProvider.class)
class TransferabilityUnitTests {
  private static Transferability transferability;

  @BeforeAll
  static void getDependencies(ProcessModel processModel) {
    transferability = RTC.getTransferability(processModel);
  }

  private boolean isANDJoin(FlowNode flowNode, ProcessModel processModel) {
    return flowNode instanceof AndGateway
      && processModel.getIncomingControlFlow(flowNode).size() > 1;
  }

  private boolean isXORSplit(FlowNode flowNode, ProcessModel processModel) {
    return flowNode instanceof XorGateway
      && processModel.getOutgoingControlFlow(flowNode).size() > 1;
  }
  
  @Test
  void andJoinsNotTransferring(ProcessModel processModel) {
    assertTrue(processModel.getControlFlow().stream()
      .filter(cf -> isANDJoin(cf.getSource(), processModel))
      .noneMatch(cf -> transferability.existsRelation(cf.getSource(), 
        cf.getTarget())));
  }

  @Test
  void xorSplitsNotTransferring(ProcessModel processModel) {
    assertTrue(processModel.getControlFlow().stream()
      .filter(cf -> isXORSplit(cf.getSource(), processModel))
      .noneMatch(cf -> transferability.existsRelation(cf.getSource(), 
        cf.getTarget())));
  }
}
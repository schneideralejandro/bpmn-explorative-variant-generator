package com.github.schneideralejandro.input;

import org.jbpt.pm.AndGateway;
import org.jbpt.pm.ControlFlow;
import org.jbpt.pm.FlowNode;
import org.jbpt.pm.ProcessModel;
import org.jbpt.pm.XorGateway;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class Transferability extends RTC {
  @Override
  boolean isTransferring(FlowNode flowNode) {
    return isANDJoin(flowNode) && isXORSplit(flowNode);
  }

  private boolean isANDJoin(FlowNode flowNode) {
    return flowNode instanceof AndGateway
      && flowNode.getModel().getIncomingControlFlow(flowNode).size() > 1;
  }

  private boolean isXORSplit(FlowNode flowNode) {
    return flowNode instanceof XorGateway
      && flowNode.getModel().getOutgoingControlFlow(flowNode).size() > 1;
  }
}
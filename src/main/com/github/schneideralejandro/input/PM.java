package com.github.schneideralejandro.input;

import org.jbpt.pm.ControlFlow;
import org.jbpt.pm.FlowNode;
import org.jbpt.pm.ProcessModel;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class PM {
  private ProcessModel processModel;
  private Reachability reachability;
  private Transferability transferability;

  private PM() {
    // Use the static factories.
  }

  // For the root model.
  public static PM getRoot(ProcessModel processModel) {
    PM root = new PM();
    root.setProcessModel(processModel);
    Reachability reachability = RTC.getReachability(processModel);
    root.setReachability(reachability);
    Transferability transferability = RTC.getTransferability(processModel);
    root.setTransferability(transferability);
    return root;
  }

  // For the variants.
  public static PM getClone(PM root) {
    PM clone = new PM();
    ProcessModel processModel = root.getProcessModel().clone();
    clone.setProcessModel(processModel);
    Reachability reachability = RTC.getClone(root.getReachability());
    clone.setReachability(reachability);
    Transferability transferability = RTC.getClone(root.getTransferability());
    clone.setTransferability(transferability);
    return clone;
  }

  // For instantiation.
  private void setProcessModel(ProcessModel processModel) {
    this.processModel = processModel;
  }

  // For cloning/testing.
  ProcessModel getProcessModel() {
    return processModel;
  }

  public Collection<FlowNode> getFlowNodes() {
    return Collections.unmodifiableCollection(processModel.getFlowNodes());
  }

  public Collection<ControlFlow<FlowNode>> getControlFlows() {
    return Collections.unmodifiableCollection(processModel.getControlFlow());
  }

  // For instantiation.
  private void setReachability(Reachability reachability) {
    this.reachability = reachability;
  }

  // For cloning.
  private Reachability getReachability() {
    return reachability;
  }

  public Set<FlowNode> getUpstream(FlowNode flowNode) {
    return Collections.unmodifiableSet(reachability.getForeset(flowNode));
  }

  public Set<FlowNode> getDownstream(FlowNode flowNode) {
    return Collections.unmodifiableSet(reachability.getAfterset(flowNode));
  }

  public boolean existsPath(FlowNode source, FlowNode target) {
    return reachability.existsRelation(source, target);
  }

  // For instantiation.
  private void setTransferability(Transferability transferability) {
    this.transferability = transferability;
  }

  // For cloning.
  private Transferability getTransferability() {
    return transferability;
  }

  public Set<FlowNode> getTransferring(FlowNode flowNode) {
    return Collections.unmodifiableSet(transferability.getForeset(flowNode));
  }

  public Set<FlowNode> getTransferred(FlowNode flowNode) {
    return Collections.unmodifiableSet(transferability.getAfterset(flowNode));
  }

  public boolean existsTransfer(FlowNode source, FlowNode target) {
    return transferability.existsRelation(source, target);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PM)) return false;
    PM pm = (PM) o;
    return processModel.equals(pm.getProcessModel());
  }

  @Override
  public int hashCode() {
    return processModel.hashCode();
  }

  @Override
  public String toString() {
    return processModel.toString();
  }
}
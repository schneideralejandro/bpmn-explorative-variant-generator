package com.github.schneideralejandro.input;

import org.jbpt.pm.ControlFlow;
import org.jbpt.pm.FlowNode;
import org.jbpt.pm.ProcessModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

abstract class RTC {
  private Map<FlowNode, Set<FlowNode>> relations;

  RTC() {
    this.relations = new HashMap<>();
  }

  static Reachability getReachability(ProcessModel processModel) {
    Reachability reachability = new Reachability();
    reachability.addFlowNodes(processModel.getFlowNodes());
    reachability.addControlFlows(processModel.getControlFlow());
    return reachability;
  }

  static Reachability getClone(Reachability original) {
    Reachability clone = new Reachability();
    clone.addRTC(original);
    return clone;
  }

  static Transferability getTransferability(ProcessModel processModel) {
    Transferability transferability = new Transferability();
    transferability.addFlowNodes(processModel.getFlowNodes());
    transferability.addControlFlows(processModel.getControlFlow());
    return transferability;
  }

  static Transferability getClone(Transferability original) {
    Transferability clone = new Transferability();
    clone.addRTC(original);
    return clone;
  }

  private Map<FlowNode, Set<FlowNode>> getRelations() {
    return relations;
  }

  void addFlowNodes(Collection<FlowNode> flowNodes) {
    flowNodes.forEach(this::addFlowNode);
  }

  void addFlowNode(FlowNode flowNode) {
    Set<FlowNode> afterset = new HashSet<>();
    afterset.add(flowNode);
    relations.put(flowNode, afterset);
  }

  void addControlFlows(Collection<ControlFlow<FlowNode>> controlFlows) {
    controlFlows.forEach(cf -> addControlFlow(cf.getSource(), cf.getTarget()));
  }

  void addControlFlow(FlowNode source, FlowNode target) {
    if (isTransferring(source)) {
      getForeset(source)
        .forEach(i -> getAfterset(target)
          .forEach(j -> relations.get(i).add(j)));
    }
  }

  abstract boolean isTransferring(FlowNode flowNode);

  void addRTC(RTC rtc) {
    rtc.getRelations()
      .forEach((k, v) -> relations.put(k, new HashSet<>(v)));
  }

  void addRelation(FlowNode source, FlowNode target) {
    relations.get(source).add(target);
  }

  Set<FlowNode> getForeset(FlowNode flowNode) {
    return relations.keySet().stream()
      .filter(k -> relations.get(k).contains(flowNode))
      .collect(Collectors.toSet());
  }

  Set<FlowNode> getAfterset(FlowNode flowNode) {
    return relations.getOrDefault(flowNode, new HashSet<>());
  }

  boolean existsRelation(FlowNode source, FlowNode target) {
    return relations.containsKey(source)
      && relations.get(source).contains(target);
  }
}
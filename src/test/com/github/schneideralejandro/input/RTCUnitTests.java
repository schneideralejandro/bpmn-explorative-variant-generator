package com.github.schneideralejandro.input;

import org.jbpt.pm.FlowNode;
import org.jbpt.pm.ProcessModel;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.Set;
import java.util.stream.Collectors;

class RTCUnitTests {
  @ParameterizedTest
  @ArgumentsSource(RTCProvider.class)
  void isReflexive(RTC rtc, ProcessModel processModel) {
    assertTrue(processModel.getFlowNodes().stream()
      .allMatch(fn -> rtc.existsRelation(fn, fn)));
  }

  @ParameterizedTest
  @ArgumentsSource(RTCProvider.class)
  void isTransitive(RTC rtc, ProcessModel processModel) {
    assertTrue(processModel.getFlowNodes().stream()
      .flatMap(fn -> rtc.getForeset(fn).stream()
        .flatMap(i -> rtc.getAfterset(fn).stream()
          .map(j -> rtc.existsRelation(i, j))))
      .allMatch(b -> b));
  }

  private Set<FlowNode> getAfterset(FlowNode flowNode, RTC rtc, 
    ProcessModel processModel) {
    return processModel.getAdjacent(flowNode).stream()
      .flatMap(adj -> rtc.getAfterset(adj).stream())
      .collect(Collectors.toSet());
  }

  private Set<FlowNode> getNotInAfterset(FlowNode flowNode, RTC rtc, 
    ProcessModel processModel) {
    Set<FlowNode> afterset = getAfterset(flowNode, rtc, processModel);
    return processModel.getFlowNodes().stream()
      .filter(fn -> !afterset.contains(fn))
      .filter(fn -> !fn.equals(flowNode))
      .collect(Collectors.toSet());
  }

  @ParameterizedTest
  @ArgumentsSource(RTCProvider.class)
  void noIncorrectRelations(RTC rtc, ProcessModel processModel) {
    assertTrue(processModel.getFlowNodes().stream()
      .allMatch(i -> getNotInAfterset(i, rtc, processModel).stream()
        .noneMatch(j -> rtc.existsRelation(i, j))));
  }
}
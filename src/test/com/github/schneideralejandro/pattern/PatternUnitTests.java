package com.github.schneideralejandro.pattern;

import org.jbpt.pm.FlowNode;
import org.jbpt.pm.bpmn.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PatternUnitTests {
  private static InsertionPoint getInsertionPointD() {
    Set<FlowNode> sources = Stream.of(new Task(), new Task())
      .collect(Collectors.toSet());
    Set<FlowNode> targets = Stream.of(new Task(), new Task())
      .collect(Collectors.toSet());
    return new InsertionPoint(sources, targets);
  }

  private static InsertionPoint getInsertionPointC() {
    Set<FlowNode> targets = Stream.of(new Task(), new Task())
      .collect(Collectors.toSet());
    return new InsertionPoint(Collections.emptySet(), targets);
  }

  private static InsertionPoint getInsertionPointB() {
    Set<FlowNode> sources = Stream.of(new Task(), new Task())
      .collect(Collectors.toSet());
    return new InsertionPoint(sources, Collections.emptySet());
  }

  private static InsertionPoint getInsertionPointA() {
    return new InsertionPoint(Collections.emptySet(), Collections.emptySet());
  }

  static Stream<Arguments> getPatterns() {
    FlowNode flowNode = new Task();
    InsertionPoint insertionPointA = getInsertionPointA();
    InsertionPoint insertionPointB = getInsertionPointB();
    InsertionPoint insertionPointC = getInsertionPointC();
    InsertionPoint insertionPointD = getInsertionPointD();
    Pattern patternA = new Pattern(flowNode, insertionPointA);
    Pattern patternB = new Pattern(flowNode, insertionPointB);
    Pattern patternC = new Pattern(flowNode, insertionPointC);
    Pattern patternD = new Pattern(flowNode, insertionPointD);
    return Stream.of(
      Arguments.of(flowNode, insertionPointA, patternA),
      Arguments.of(flowNode, insertionPointB, patternB),
      Arguments.of(flowNode, insertionPointC, patternC),
      Arguments.of(flowNode, insertionPointD, patternD)
    );
  }

  @ParameterizedTest
  @MethodSource("getPatterns")
  void flowNodeIsWrapped(FlowNode flowNode, InsertionPoint insertionPoint, 
    Pattern pattern) {
    assertEquals(flowNode, pattern.getFlowNode());
  }

  @ParameterizedTest
  @MethodSource("getPatterns")
  void controlFlowsFromEachSourceToFlowNode(FlowNode flowNode, 
    InsertionPoint insertionPoint, Pattern pattern) {
    Map<FlowNode, Set<FlowNode>> controlFlows = pattern.getControlFlows();
    assertTrue(insertionPoint.getSources().stream()
      .allMatch(s -> controlFlows.containsKey(s)
        && controlFlows.get(s).contains(flowNode)));
  }
}
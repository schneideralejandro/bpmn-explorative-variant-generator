package com.github.schneideralejandro.pattern;

import org.jbpt.pm.FlowNode;
import org.jbpt.pm.bpmn.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class InsertionPointUnitTests {
  private InsertionPoint insertionPoint;

  @BeforeEach
  void getDependencies() {
    insertionPoint = new InsertionPoint(Collections.emptySet(), 
      Collections.emptySet());
  }

  @Test
  void sourcesIsImmutable() {
    assertThrows(UnsupportedOperationException.class, 
      () -> insertionPoint.getSources().add(new Task()));
  }

  @Test
  void targetsIsImmutable() {
    assertThrows(UnsupportedOperationException.class,
      () -> insertionPoint.getTargets().add(new Task()));
  }

  @Nested
  class DifferentSourceOrder {
    private InsertionPoint insertionPointA;
    private InsertionPoint insertionPointB;

    @BeforeEach
    void getDependencies() {
      Task taskA = new Task();
      Task taskB = new Task();
      List<FlowNode> aThenB = Stream.of(taskA, taskB)
        .collect(Collectors.toList());
      List<FlowNode> bThenA = Stream.of(taskB, taskA)
        .collect(Collectors.toList());
      insertionPointA = new InsertionPoint(aThenB, Collections.emptySet());
      insertionPointB = new InsertionPoint(bThenA, Collections.emptySet());
    }

    @Test
    void equalityUnaffectedBySourceOrder() {
      assertEquals(insertionPointA, insertionPointB);
    }

    @Test
    void hashCodeUnaffectedBySourceOrder() {
      assertEquals(insertionPointA.hashCode(), insertionPointB.hashCode());
    }
  }

  @Nested
  class DifferentTargetOrder {
    private InsertionPoint insertionPointA;
    private InsertionPoint insertionPointB;

    @BeforeEach
    void getDependencies() {
      Task taskA = new Task();
      Task taskB = new Task();
      List<FlowNode> aThenB = Stream.of(taskA, taskB)
        .collect(Collectors.toList());
      List<FlowNode> bThenA = Stream.of(taskB, taskA)
        .collect(Collectors.toList());
      insertionPointA = new InsertionPoint(Collections.emptySet(), aThenB);
      insertionPointB = new InsertionPoint(Collections.emptySet(), bThenA);
    }

    @Test
    void equalityUnaffectedByTargetOrder() {
      assertEquals(insertionPointA, insertionPointB);
    }

    @Test
    void hashCodeUnaffectedByTargetOrder() {
      assertEquals(insertionPointA.hashCode(), insertionPointB.hashCode());
    }
  }
}
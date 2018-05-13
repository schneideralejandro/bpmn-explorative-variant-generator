package com.github.schneideralejandro.input;

import org.jbpt.pm.ProcessModel;
import org.jbpt.pm.bpmn.EndEvent;
import org.jbpt.pm.bpmn.StartEvent;
import org.jbpt.pm.bpmn.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

@ExtendWith(ProcessModelProvider.class)
class ProcessModelUnitTests {
  @Test
  void activityTypeNotSerialized(ProcessModel processModel) {
    assertTrue(processModel.getActivities().stream()
      .noneMatch(a -> a instanceof Task));
  }

  @Test
  void eventTypeNotSerialized(ProcessModel processModel) {
    assertTrue(processModel.getEvents().stream()
      .noneMatch(e -> e instanceof StartEvent || e instanceof EndEvent));
  }

  @Test
  void identityBasedOnlyOnID() {
    Task t1 = new Task("t1");
    Task t2 = new Task("t2");
    ProcessModel pm1 = new ProcessModel();
    pm1.addControlFlow(t1, t2);
    ProcessModel pm2 = new ProcessModel();
    pm2.addControlFlow(t2, t1);
    assertNotEquals(pm1, pm2);
    pm2.setId(pm1.getId());
    assertEquals(pm1, pm2);
  }
}
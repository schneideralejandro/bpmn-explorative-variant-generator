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

class Reachability extends RTC {
  @Override
  void addControlFlow(FlowNode source, FlowNode target) {
    getForeset(source)
      .forEach(u -> getAfterset(target)
        .forEach(d -> addRelation(u, d)));
  }
}
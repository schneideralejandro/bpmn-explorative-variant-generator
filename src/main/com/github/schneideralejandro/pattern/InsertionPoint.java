package com.github.schneideralejandro.pattern;

import org.jbpt.pm.FlowNode;

import static java.lang.System.getProperty;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class InsertionPoint {
  private Set<FlowNode> sources;
  private Set<FlowNode> targets;

  InsertionPoint(Collection<FlowNode> sources, Collection<FlowNode> targets) {
    this.sources = new HashSet<>(sources);
    this.targets = new HashSet<>(targets);
  }

  Set<FlowNode> getSources() {
    return Collections.unmodifiableSet(sources);
  }

  Set<FlowNode> getTargets() {
    return Collections.unmodifiableSet(targets);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InsertionPoint)) return false;
    InsertionPoint insertionPoint = (InsertionPoint) o;
    return sources.equals(insertionPoint.getSources())
      && targets.equals(insertionPoint.getTargets());
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + sources.hashCode();
    result = 31 * result + targets.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Sources: " + sources.toString() 
      + System.getProperty("line.separator")
      + "Targets: " + targets.toString();
  }
}
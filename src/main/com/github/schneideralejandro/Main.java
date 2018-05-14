package com.github.schneideralejandro;

import com.github.schneideralejandro.input.PM;
import com.github.schneideralejandro.input.ProcessModelFactory;

import org.jbpt.pm.ProcessModel;
import org.jbpt.throwable.SerializationException;

import java.io.IOException;

public class Main {
  public static void main(String... args) {
    try {
      ProcessModel processModel = ProcessModelFactory.get(args[0]);
      PM root = PM.getRoot(processModel);
    } catch (IOException | SerializationException e) {
      e.printStackTrace();
    }
  }
}
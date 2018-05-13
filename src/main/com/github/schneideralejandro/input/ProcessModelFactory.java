package com.github.schneideralejandro.input;

import org.jbpt.pm.ProcessModel;
import org.jbpt.pm.io.JSON2Process;
import org.jbpt.throwable.SerializationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProcessModelFactory {
  public static ProcessModel get(String pathName) 
    throws IOException, SerializationException {
    Path path = Paths.get(pathName);
    byte[] encoded = Files.readAllBytes(path);
    String decoded = new String(encoded, StandardCharsets.UTF_8);
    return JSON2Process.convert(decoded);
  }
}
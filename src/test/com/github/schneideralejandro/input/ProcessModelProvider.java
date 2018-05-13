package com.github.schneideralejandro.input;

import org.jbpt.throwable.SerializationException;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.Objects;

class ProcessModelProvider implements ParameterResolver {
  @Override
  public boolean supportsParameter(ParameterContext parameterContext, 
    ExtensionContext extensionContext) throws ParameterResolutionException {
    Parameter parameter = parameterContext.getParameter();
    return Objects.equals(parameter.getParameterizedType().getTypeName(), 
      "org.jbpt.pm.ProcessModel");
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, 
    ExtensionContext extensionContext) throws ParameterResolutionException {
    ClassLoader classLoader = getClass().getClassLoader();
    URL url = classLoader.getResource("ps01.json");
    try {
      return ProcessModelFactory.get(url.getPath());
    } catch (IOException | SerializationException e) {
      throw new ParameterResolutionException(e.getMessage());
    }
  }
}
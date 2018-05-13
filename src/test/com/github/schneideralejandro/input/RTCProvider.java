package com.github.schneideralejandro.input;

import org.jbpt.pm.ProcessModel;
import org.jbpt.throwable.SerializationException;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;

class RTCProvider implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context)
    throws IOException, SerializationException {
    ClassLoader classLoader = getClass().getClassLoader();
    URL url = classLoader.getResource("ps01.json");
    ProcessModel processModel = ProcessModelFactory.get(url.getPath());
    Reachability reachability = RTC.getReachability(processModel);
    Transferability transferability = RTC.getTransferability(processModel);
    return Stream.of(
      Arguments.of(reachability, processModel),
      Arguments.of(transferability, processModel)
    );
  }
}
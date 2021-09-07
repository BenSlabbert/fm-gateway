package com.github.benslabbert.fm.gateway;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GatewayTest extends ApplicationTestServices {

  private static TestApplicationWrapper testApplicationWrapper;

  @BeforeAll
  static void beforeAll() {
    testApplicationWrapper = startApplication();
  }

  @AfterAll
  protected static void afterAll() {
    testApplicationWrapper.getApplicationContext().stop();
    stopContainers();
  }

  @Test
  void testItWorks() {
    assertTrue(testApplicationWrapper.getApplicationContext().isRunning());
  }
}

package com.github.benslabbert.fm.gateway;

import io.micronaut.runtime.Micronaut;

public class Application {

  public static void main(String[] args) {
    Micronaut.build(args).mainClass(Application.class).start();
  }
}

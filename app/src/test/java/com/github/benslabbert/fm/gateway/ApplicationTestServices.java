package com.github.benslabbert.fm.gateway;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class ApplicationTestServices {

  private static final GenericContainer<?> psqlContainer =
      new GenericContainer<>("postgres:13-alpine")
          .withExposedPorts(5432)
          .withEnv("POSTGRES_USER", "user")
          .withEnv("POSTGRES_PASSWORD", "password")
          .withEnv("POSTGRES_DB", "db")
          .waitingFor(Wait.forListeningPort());

  private static final GenericContainer<?> minioContainer =
      new GenericContainer<>("minio/minio")
          .withCommand("server /data")
          .withExposedPorts(9000)
          .withEnv("MINIO_ROOT_USER", "minio")
          .withEnv("MINIO_ROOT_PASSWORD", "minio123")
          .waitingFor(Wait.forListeningPort());

  protected static TestApplicationWrapper startApplication() {
    Stream.of(psqlContainer, minioContainer).parallel().forEach(GenericContainer::start);

    int grpcPort = ThreadLocalRandom.current().nextInt(50000, 55000);

    var embeddedServer =
        ApplicationContext.run(
            EmbeddedServer.class,
            Map.of(
                "datasources.default.url",
                "jdbc:postgresql://localhost:" + psqlContainer.getMappedPort(5432) + "/db",
                "minio.server.port",
                minioContainer.getMappedPort(9000)));

    var applicationContext = embeddedServer.getApplicationContext();
    return new TestApplicationWrapper(embeddedServer, applicationContext, grpcPort);
  }

  public static void stopContainers() {
    Stream.of(psqlContainer, minioContainer).parallel().forEach(GenericContainer::stop);
  }
}

package com.github.benslabbert.fm.gateway.dao.entity;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.annotation.Version;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@MappedEntity
@NoArgsConstructor
@AllArgsConstructor
public class Upload implements UserAware, Identifiable<UUID>, Versioned {

  @Id
  @GeneratedValue(value = GeneratedValue.Type.AUTO)
  private UUID id;

  @MappedProperty("user_id")
  private UUID userId;

  @MappedProperty("object_key")
  private String objectKey;

  @MappedProperty("etag")
  private String etag;

  @MappedProperty("content_length")
  private Long contentLength;

  @MappedProperty("content_type")
  private UploadContentType contentType;

  @Version
  @MappedProperty("version")
  private Integer version;

  @DateCreated
  @MappedProperty("created")
  private Instant created;

  @DateUpdated
  @MappedProperty("updated")
  private Instant updated;

  @Builder
  public Upload(
      UUID userId,
      String objectKey,
      long contentLength,
      UploadContentType contentType,
      String etag) {
    this.userId = userId;
    this.objectKey = objectKey;
    this.contentLength = contentLength;
    this.contentType = contentType;
    this.etag = etag;
  }

  @Override
  public UUID gerUserId() {
    return userId;
  }
}

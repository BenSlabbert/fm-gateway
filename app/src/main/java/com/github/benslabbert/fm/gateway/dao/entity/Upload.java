package com.github.benslabbert.fm.gateway.dao.entity;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedEntity
@NoArgsConstructor
@AllArgsConstructor
public class Upload extends BaseEntity<UUID> implements UserAware {

  @Id
  @GeneratedValue(value = GeneratedValue.Type.AUTO)
  private UUID id;

  @MappedProperty("user_id")
  private UUID userId;

  @MappedProperty("object_key")
  private String objectKey;

  @MappedProperty("content_length")
  private Long contentLength;

  @MappedProperty("content_type")
  private UploadContentType contentType;

  @Builder
  public Upload(UUID userId, String objectKey, long contentLength, UploadContentType contentType) {
    this.userId = userId;
    this.objectKey = objectKey;
    this.contentLength = contentLength;
    this.contentType = contentType;
  }

  @Override
  public UUID gerUserId() {
    return userId;
  }
}

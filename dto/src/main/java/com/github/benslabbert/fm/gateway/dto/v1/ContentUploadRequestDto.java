package com.github.benslabbert.fm.gateway.dto.v1;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class ContentUploadRequestDto {

  private String title;
  private String caption;
}

package com.github.benslabbert.fm.gateway.dto.v1;

import io.micronaut.core.annotation.Introspected;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class UploadPageResponseDto {

  private Integer page;
  private Integer size;
  private Integer total;
  private List<ContentUploadResponseDto> entries;
}

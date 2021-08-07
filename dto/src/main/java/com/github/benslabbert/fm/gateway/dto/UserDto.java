package com.github.benslabbert.fm.gateway.dto;

import io.micronaut.core.annotation.Introspected;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class UserDto {

  private UUID id;
  private String name;
}

package com.github.benslabbert.fm.gateway.dao.repo;

import com.github.benslabbert.fm.gateway.dao.entity.Upload;
import io.micronaut.context.annotation.Executable;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;
import java.util.Optional;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UploadRepo extends PageableRepository<Upload, UUID> {

  @Executable
  Optional<Upload> findByUserIdAndObjectKey(UUID userId, String objectKey);
}

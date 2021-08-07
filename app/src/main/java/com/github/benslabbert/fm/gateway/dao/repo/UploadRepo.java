package com.github.benslabbert.fm.gateway.dao.repo;

import com.github.benslabbert.fm.gateway.dao.entity.Upload;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UploadRepo extends CrudRepository<Upload, UUID> {
  // use on queries    @io.micronaut.context.annotation.Executable
}

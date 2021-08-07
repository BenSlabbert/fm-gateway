CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE upload (
    id uuid DEFAULT uuid_generate_v4 (),
    content_length bigint not null,
    version integer default 0 not null ,
    created timestamp default now() not null,
    updated timestamp default now() not null,
    PRIMARY KEY (id)
);

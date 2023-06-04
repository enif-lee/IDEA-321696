CREATE TABLE shards
(
  id         VARCHAR(255) NOT NULL PRIMARY KEY,
  created_at TIMESTAMPTZ default now()
);

CREATE TABLE shard_assignments
(
  id          VARCHAR(255) NOT NULL PRIMARY KEY,
  shard_id    VARCHAR(255) NOT NULL,
  created_at  TIMESTAMPTZ DEFAULT now(),
  FOREIGN KEY (shard_id) REFERENCES shards (id)
);

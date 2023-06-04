CREATE DOMAIN id as VARCHAR(127);
CREATE DOMAIN string AS VARCHAR(255);
CREATE DOMAIN memo AS VARCHAR(300);
CREATE DOMAIN audit_time AS TIMESTAMPTZ;
CREATE DOMAIN amount AS BIGINT;
CREATE DOMAIN rate AS INT;
CREATE TYPE payer AS ENUM ('partner', 'merchant');
CREATE TYPE round AS ENUM ('off', 'up', 'down');
CREATE TYPE currency AS ENUM ('krw', 'usd', 'jpy');

CREATE DOMAIN version_ref as VARCHAR(63) NOT NULL;

CREATE TABLE platforms
(
  id                     id          NOT NULL,
  ref                    version_ref PRIMARY KEY,
  is_hidden              boolean,
  applied_at             TIMESTAMPTZ NOT NULL
);
CREATE INDEX ON platforms (id, applied_at);

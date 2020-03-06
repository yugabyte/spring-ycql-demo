CREATE KEYSPACE IF NOT EXISTS demo;

CREATE TABLE demo.customer (
    id text PRIMARY KEY,
    name text,
    emailId text
) WITH default_time_to_live = 0
    AND transactions = {'enabled': 'true'};
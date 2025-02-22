#!/usr/bin/env bash

docker exec -i --user postgres "$1" createdb -p "$2" balancedb

docker exec -i --user postgres "$1" psql -p "$2" balancedb -a <<__END
create user clojure_ch password 'kafka-graphql-pw';
__END

docker exec -i "$1" psql -Uclojure_ch -p "$2" balancedb -a <<__END
drop table if exists balance;
drop table if exists cac;
drop table if exists cmt;

CREATE OR REPLACE FUNCTION maintain_updated_at()
RETURNS TRIGGER AS \$\$
BEGIN
   NEW.updated_at = now();
   RETURN NEW;
END;
\$\$ language 'plpgsql';

create table balance(
    balance_id int generated by default as identity primary key,
    username text not null,
    iban text not null,
    token text not null,
    amount bigint not null default 0,
    lmt bigint not null default -50000,
    created_at timestamp not null default current_timestamp,
    updated_at timestamp not null default current_timestamp);

create trigger balance_updated_at before update
on balance for each row execute procedure
maintain_updated_at();

insert into balance (balance_id, username, iban, token, amount, lmt) values
    (0, 'open_web', 'NL66OPEN0000000000', '00000000000000000000', 100000000000000000, -50000);

alter table balance alter column balance_id restart with 1;

create index balance_iban on balance using btree (iban);

create table cac(
    uuid UUID not null primary key,
    username text,
    iban text,
    token text,
    reason text,
    created_at timestamp not null default current_timestamp);

create table cmt(
    uuid UUID not null primary key,
    reason text,
    created_at timestamp not null default current_timestamp);
__END

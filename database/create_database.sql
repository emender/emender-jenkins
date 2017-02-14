create table product_names (
    id            integer primary key asc,
    name          text not null
);

create table product_versions (
    id            integer primary key asc,
    product_id    integer not null,
    version       text not null,
    foreign key   (product_id) references product_names(id)
);

create table guides (
    id            integer primary key asc,
    prod_ver_id   integer not null,
    name          text not null,
    foreign key   (prod_ver_id) references product_versions(id)
);

create table test_suites (
    id            integer primary key asc,
    name          text not null
);

create table tests (
    id            integer primary key asc,
    test_suite_id integer not null,
    name          text not null,
    foreign key   (test_suite_id) references test_suites(id)
);

create table waives (
    id            integer primary key asc,
    guide_id      integer not null,
    test_id       integer not null,
    file_name     text,
    cause         text,
    added         datetime,
    foreign key   (guide_id) references guides(id),
    foreign key   (test_id)  references tests(id)
);


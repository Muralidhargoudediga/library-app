create table if not exists author
(
    id    bigint auto_increment
        primary key,
    email varchar(255) null,
    name  varchar(255) null
);

create table if not exists category
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table if not exists publisher
(
    id          bigint auto_increment
        primary key,
    description varchar(255) null,
    email       varchar(255) null,
    name        varchar(255) null
);

create table if not exists shelf
(
    id           bigint auto_increment
        primary key,
    floor        int not null,
    shelf_number int not null
);

create table if not exists book
(
    id           bigint auto_increment
        primary key,
    description  varchar(255) null,
    is_available bit          null,
    isbn         varchar(255) null,
    language     varchar(255) null,
    name         varchar(255) null,
    release_date datetime(6)  null,
    publisher_id bigint       null,
    shelf_id     bigint       null,
    constraint FKgtvt7p649s4x80y6f4842pnfq
        foreign key (publisher_id) references publisher (id),
    constraint FKp7vf5b2vkphwbbfntbcoo7aby
        foreign key (shelf_id) references shelf (id)
);

create table if not exists book_author
(
    book_id   bigint not null,
    author_id bigint not null,
    constraint FKbjqhp85wjv8vpr0beygh6jsgo
        foreign key (author_id) references author (id),
    constraint FKhwgu59n9o80xv75plf9ggj7xn
        foreign key (book_id) references book (id)
);

create table if not exists book_category
(
    book_id     bigint not null,
    category_id bigint not null,
    constraint FKam8llderp40mvbbwceqpu6l2s
        foreign key (category_id) references category (id),
    constraint FKnyegcbpvce2mnmg26h0i856fd
        foreign key (book_id) references book (id)
);

create table if not exists user
(
    id            bigint auto_increment
        primary key,
    address       varchar(255) null,
    creation_time datetime(6)  null,
    email         varchar(255) null,
    name          varchar(255) null,
    password      varchar(255) null,
    phone         varchar(255) null,
    user_name     varchar(255) null
);

create table if not exists loaned_book
(
    id            bigint auto_increment
        primary key,
    due_date      datetime(6) null,
    late_fee      double      not null,
    loan_date     datetime(6) null,
    returned_date datetime(6) null,
    status        int         null,
    book_id       bigint      null,
    user_id       bigint      null,
    constraint FK573366r4wpphrs5pjglr8atlu
        foreign key (user_id) references user (id),
    constraint FKklm4g09k96j1s0a0qrinejneq
        foreign key (book_id) references book (id)
);

create table if not exists subscription
(
    id                bigint auto_increment
        primary key,
    subscription_time datetime(6) null,
    book_id           bigint      null,
    user_id           bigint      null,
    constraint FK8l1goo02px4ye49xd7wgogxg6
        foreign key (user_id) references user (id),
    constraint FKlosj9yawwlwevbcm3wx8hmt77
        foreign key (book_id) references book (id)
);



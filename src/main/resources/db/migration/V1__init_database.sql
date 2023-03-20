create table addresses
(
    id          binary(16)   not null
        primary key,
    city        varchar(15)  not null,
    country     varchar(15)  not null,
    postal_code varchar(7)   not null,
    street      varchar(100) not null
) engine = InnoDB;

create table authorities
(
    id   binary(16)   not null
        primary key,
    name varchar(255) null,
    constraint UK_nb3atvjf9ov5d0egnuk47o5e
        unique (name)
) engine = InnoDB;

create table brands
(
    id         binary(16)   not null
        primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    revision   bigint       null,
    logo       varchar(128) null,
    name       varchar(45)  not null,
    constraint UK_oce3937d2f4mpfqrycbr0l93m
        unique (name)
) engine = InnoDB;

create table brands_snapshots
(
    id           binary(16)   not null
        primary key,
    created_at   datetime(6)  null,
    updated_at   datetime(6)  null,
    max_revision bigint       null,
    logo         varchar(128) not null,
    name         varchar(45)  not null
) engine = InnoDB;

create table categories
(
    id         binary(16)   not null
        primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    revision   bigint       null,
    alias      varchar(64)  not null,
    enabled    bit          not null,
    image      varchar(128) null,
    name       varchar(128) not null,
    parent     binary(16)   null,
    constraint UK_jx1ptm0r46dop8v0o7nmgfbeq
        unique (alias),
    constraint UK_t8o6pivur7nn124jehx7cygw5
        unique (name),
    constraint FKqxcgflvs2beh702toak6bni11
        foreign key (parent) references categories (id)
) engine = InnoDB;

create table brands_categories
(
    brand    binary(16) not null,
    category binary(16) not null,
    primary key (brand, category),
    constraint FK7nqbfjlruh58n7vgdq42emkko
        foreign key (brand) references brands (id),
    constraint FKll6knp85g40hmi7duvjnh5v1j
        foreign key (category) references categories (id)
) engine = InnoDB;

create table categories_snapshots
(
    id           binary(16)   not null
        primary key,
    created_at   datetime(6)  null,
    updated_at   datetime(6)  null,
    max_revision bigint       null,
    alias        varchar(64)  not null,
    enabled      bit          not null,
    image        varchar(128) not null,
    name         varchar(128) not null
) engine = InnoDB;

create table credit_cards_snapshots
(
    id                 binary(16)   not null
        primary key,
    created_at         datetime(6)  null,
    updated_at         datetime(6)  null,
    max_revision       bigint       null,
    credit_card_number varchar(255) null,
    cvv                varchar(255) null,
    expiration_date    varchar(255) null
) engine = InnoDB;

create table orders
(
    id         binary(16)     not null
        primary key,
    created_at datetime(6)    null,
    updated_at datetime(6)    null,
    revision   bigint         null,
    products   varbinary(255) null
) engine = InnoDB;

create table product_details_snapshots
(
    id           binary(16)   not null
        primary key,
    created_at   datetime(6)  null,
    updated_at   datetime(6)  null,
    max_revision bigint       null,
    name         varchar(255) not null,
    value        varchar(255) not null
) engine = InnoDB;

create table products
(
    id                binary(16)    not null
        primary key,
    created_at        datetime(6)   null,
    updated_at        datetime(6)   null,
    revision          bigint        null,
    alias             varchar(255)  not null,
    average_rating    float         not null,
    cost              float         not null,
    discount_percent  float         null,
    enabled           bit           not null,
    full_description  varchar(4096) not null,
    height            float         not null,
    in_stock          bit           null,
    length            float         not null,
    main_image        varchar(255)  not null,
    name              varchar(255)  not null,
    price             float         not null,
    review_count      int           not null,
    short_description varchar(512)  not null,
    weight            float         not null,
    width             float         not null,
    brand             binary(16)    null,
    category          binary(16)    null,
    constraint UK_8qwq8q3hk7cxkp9gruxupnif5
        unique (alias),
    constraint UK_o61fmio5yukmmiqgnxf8pnavn
        unique (name),
    constraint FK2ntfj2kfvx19cldejusvdjrqe
        foreign key (brand) references brands (id),
    constraint FKtng6hvelpjyy7el0f5eq93nq4
        foreign key (category) references categories (id)
) engine = InnoDB;

create table product_details
(
    id         binary(16)   not null
        primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    revision   bigint       null,
    name       varchar(255) not null,
    value      varchar(255) not null,
    product    binary(16)   null,
    constraint FKmq859guik9c7y5ji8q356uwv8
        foreign key (product) references products (id)
) engine = InnoDB;

create table product_images
(
    id      binary(16)   not null
        primary key,
    name    varchar(255) not null,
    product binary(16)   null,
    constraint FKqq5ye5snxm5rosj9e23qmfa50
        foreign key (product) references products (id)
) engine = InnoDB;

create table products_snapshots
(
    id                binary(16)    not null
        primary key,
    created_at        datetime(6)   null,
    updated_at        datetime(6)   null,
    max_revision      bigint        null,
    alias             varchar(255)  not null,
    full_description  varchar(4096) not null,
    name              varchar(255)  not null,
    short_description varchar(512)  not null
) engine = InnoDB;

create table roles
(
    id   binary(16)   not null
        primary key,
    name varchar(255) null,
    constraint UK_ofx66keruapi6vyqpv6f2or37
        unique (name)
) engine = InnoDB;

create table roles_authorities
(
    role      binary(16) not null,
    authority binary(16) not null,
    primary key (role, authority),
    constraint FK6e032u9pfof27la3s53d55md2
        foreign key (role) references roles (id),
    constraint FK7vty7fbnyoqi3ikbby0upwxhc
        foreign key (authority) references authorities (id)
) engine = InnoDB;

create table users
(
    id                       binary(16)   not null
        primary key,
    created_at               datetime(6)  null,
    updated_at               datetime(6)  null,
    revision                 bigint       null,
    email                    varchar(120) not null,
    email_verification_token varchar(255) null,
    encrypted_password       varchar(255) not null,
    first_name               varchar(50)  not null,
    is_verified              bit          not null,
    last_name                varchar(50)  not null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email)
) engine = InnoDB;

create table credit_cards
(
    id                 binary(16)   not null
        primary key,
    created_at         datetime(6)  null,
    updated_at         datetime(6)  null,
    revision           bigint       null,
    credit_card_number varchar(255) not null,
    cvv                varchar(255) not null,
    expiration_date    varchar(255) not null,
    user               binary(16)   null,
    constraint FK1316uon7dnxmwwmxq4cvh5qbd
        foreign key (user) references users (id)
) engine = InnoDB;

create table password_reset_tokens
(
    id    binary(16)   not null
        primary key,
    token varchar(255) not null,
    user  binary(16)   null,
    constraint FK9cu5fq6ng9b0xhkicslddfoma
        foreign key (user) references users (id)
) engine = InnoDB;

create table users_addresses
(
    user    binary(16) not null,
    address binary(16) not null,
    primary key (user, address),
    constraint FK2xxalsf92dsomsnnqm1dm8q5c
        foreign key (address) references addresses (id),
    constraint FKi6r047dtpcftg3nyxeok3jrfw
        foreign key (user) references users (id)
) engine = InnoDB;

create table users_roles
(
    user binary(16) not null,
    role binary(16) not null,
    primary key (user, role),
    constraint FKbtpukn3y3g8meo2dmdrgryl91
        foreign key (role) references roles (id),
    constraint FKkjti9tmthqhpqdhugbhcngweo
        foreign key (user) references users (id)
) engine = InnoDB;

create table users_snapshots
(
    id                 binary(16)   not null
        primary key,
    created_at         datetime(6)  null,
    updated_at         datetime(6)  null,
    max_revision       bigint       null,
    email              varchar(120) not null,
    encrypted_password varchar(255) not null,
    first_name         varchar(50)  not null,
    last_name          varchar(50)  not null
) engine = InnoDB;
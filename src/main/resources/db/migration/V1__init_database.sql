create table shop_app_monolith.authorities
(
    id   binary(16)   not null
        primary key,
    name varchar(255) null,
    constraint UK_nb3atvjf9ov5d0egnuk47o5e
        unique (name)
);

create table shop_app_monolith.brands
(
    id         binary(16)   not null
        primary key,
    created_at datetime(6)  not null,
    updated_at datetime(6)  null,
    revision   bigint       null,
    logo       varchar(128) not null,
    name       varchar(45)  not null,
    constraint UK_oce3937d2f4mpfqrycbr0l93m
        unique (name)
);

create table shop_app_monolith.brands_snapshots
(
    id           binary(16)   not null
        primary key,
    created_at   datetime(6)  not null,
    updated_at   datetime(6)  null,
    max_revision bigint       null,
    logo         varchar(128) not null,
    name         varchar(45)  not null
);

create table shop_app_monolith.categories
(
    id             binary(16)   not null
        primary key,
    created_at     datetime(6)  not null,
    updated_at     datetime(6)  null,
    revision       bigint       null,
    alias          varchar(64)  not null,
    all_parent_ids varchar(256) null,
    enabled        bit          not null,
    image          varchar(128) not null,
    name           varchar(128) not null,
    parent_id      binary(16)   null,
    constraint UK_jx1ptm0r46dop8v0o7nmgfbeq
        unique (alias),
    constraint UK_t8o6pivur7nn124jehx7cygw5
        unique (name),
    constraint FKsaok720gsu4u2wrgbk10b5n8d
        foreign key (parent_id) references shop_app_monolith.categories (id)
);

create table shop_app_monolith.brands_categories
(
    brand_id    binary(16) not null,
    category_id binary(16) not null,
    primary key (brand_id, category_id),
    constraint FK58ksmicdguvu4d7b6yglgqvxo
        foreign key (brand_id) references shop_app_monolith.brands (id),
    constraint FK6x68tjj3eay19skqlhn7ls6ai
        foreign key (category_id) references shop_app_monolith.categories (id)
);

create table shop_app_monolith.categories_snapshots
(
    id           binary(16)   not null
        primary key,
    created_at   datetime(6)  not null,
    updated_at   datetime(6)  null,
    max_revision bigint       null,
    alias        varchar(64)  not null,
    enabled      bit          not null,
    image        varchar(128) not null,
    name         varchar(128) not null
);

create table shop_app_monolith.credit_cards_snapshots
(
    id                 binary(16)   not null
        primary key,
    created_at         datetime(6)  not null,
    updated_at         datetime(6)  null,
    max_revision       bigint       null,
    credit_card_number varchar(255) null,
    cvv                varchar(255) null,
    expiration_date    varchar(255) null
);

create table shop_app_monolith.currencies
(
    id     binary(16)  not null
        primary key,
    code   varchar(4)  not null,
    name   varchar(64) not null,
    symbol varchar(3)  not null
);

create table shop_app_monolith.product_details_snapshots
(
    id           binary(16)   not null
        primary key,
    created_at   datetime(6)  not null,
    updated_at   datetime(6)  null,
    max_revision bigint       null,
    name         varchar(255) not null,
    value        varchar(255) not null
);

create table shop_app_monolith.products
(
    id                binary(16)    not null
        primary key,
    created_at        datetime(6)   not null,
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
    brand_id          binary(16)    null,
    category_id       binary(16)    null,
    constraint UK_8qwq8q3hk7cxkp9gruxupnif5
        unique (alias),
    constraint UK_o61fmio5yukmmiqgnxf8pnavn
        unique (name),
    constraint FKa3a4mpsfdf4d2y6r8ra3sc8mv
        foreign key (brand_id) references shop_app_monolith.brands (id),
    constraint FKog2rp4qthbtt2lfyhfo32lsw9
        foreign key (category_id) references shop_app_monolith.categories (id)
);

create table shop_app_monolith.product_details
(
    id         binary(16)   not null
        primary key,
    created_at datetime(6)  not null,
    updated_at datetime(6)  null,
    revision   bigint       null,
    name       varchar(255) not null,
    value      varchar(255) not null,
    product    binary(16)   null,
    constraint FKmq859guik9c7y5ji8q356uwv8
        foreign key (product) references shop_app_monolith.products (id)
);

create table shop_app_monolith.product_images
(
    id      binary(16)   not null
        primary key,
    name    varchar(255) not null,
    product binary(16)   null,
    constraint FKqq5ye5snxm5rosj9e23qmfa50
        foreign key (product) references shop_app_monolith.products (id)
);

create table shop_app_monolith.products_snapshots
(
    id                binary(16)    not null
        primary key,
    created_at        datetime(6)   not null,
    updated_at        datetime(6)   null,
    max_revision      bigint        null,
    alias             varchar(255)  not null,
    full_description  varchar(4096) not null,
    name              varchar(255)  not null,
    short_description varchar(512)  not null
);

create table shop_app_monolith.roles
(
    id   binary(16)   not null
        primary key,
    name varchar(255) null,
    constraint UK_ofx66keruapi6vyqpv6f2or37
        unique (name)
);

create table shop_app_monolith.roles_authorities
(
    role      binary(16) not null,
    authority binary(16) not null,
    primary key (role, authority),
    constraint FK6e032u9pfof27la3s53d55md2
        foreign key (role) references shop_app_monolith.roles (id),
    constraint FK7vty7fbnyoqi3ikbby0upwxhc
        foreign key (authority) references shop_app_monolith.authorities (id)
);

create table shop_app_monolith.sections
(
    id            binary(16)    not null
        primary key,
    description   varchar(2048) not null,
    enabled       bit           not null,
    heading       varchar(256)  not null,
    section_order int           null,
    type          varchar(255)  null,
    constraint UK_i816mxaoddmtveofp1qjbnngf
        unique (heading)
);

create table shop_app_monolith.sections_brands
(
    id          binary(16) not null
        primary key,
    brand_order int        null,
    brand_id    binary(16) null,
    section_id  binary(16) null,
    constraint FK63hur81l4do6ck6x015gp3cuu
        foreign key (brand_id) references shop_app_monolith.brands (id),
    constraint FK68eeev3m314v82j0st116ftm9
        foreign key (section_id) references shop_app_monolith.sections (id)
);

create table shop_app_monolith.sections_categories
(
    id             binary(16) not null
        primary key,
    category_order int        null,
    category_id    binary(16) null,
    section_id     binary(16) null,
    constraint FK4oqn5st47mm34yvnt3criccsh
        foreign key (category_id) references shop_app_monolith.categories (id),
    constraint FK7ivge0x7bydfh10vw3d0n837j
        foreign key (section_id) references shop_app_monolith.sections (id)
);

create table shop_app_monolith.sections_products
(
    id            binary(16) not null
        primary key,
    product_order int        null,
    product_id    binary(16) null,
    section_id    binary(16) null,
    constraint FKjmh450o9k6x0hrmrumemucg1b
        foreign key (section_id) references shop_app_monolith.sections (id),
    constraint FKl5lf8i896cqdnc9shitywps2l
        foreign key (product_id) references shop_app_monolith.products (id)
);

create table shop_app_monolith.settings
(
    `key`    varchar(128)  not null
        primary key,
    category varchar(45)   not null,
    value    varchar(1024) not null
);

create table shop_app_monolith.shipping_rates
(
    id            binary(16)  not null
        primary key,
    cod_supported bit         null,
    country       varchar(45) null,
    days          int         not null,
    rate          float       not null,
    state         varchar(45) not null,
    constraint UK_p847mx3nf68mbgpsi6ndsuml1
        unique (country)
);

create table shop_app_monolith.users
(
    id                       binary(16)   not null
        primary key,
    created_at               datetime(6)  not null,
    updated_at               datetime(6)  null,
    revision                 bigint       null,
    email                    varchar(120) not null,
    email_verification_token varchar(255) null,
    encrypted_password       varchar(255) not null,
    is_verified              bit          not null,
    photos                   varchar(64)  null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email)
);

create table shop_app_monolith.addresses
(
    address_line_1  varchar(64) not null,
    address_line_2  varchar(64) null,
    city            varchar(45) not null,
    country         varchar(45) not null,
    first_name      varchar(45) not null,
    last_name       varchar(45) not null,
    phone_number    varchar(15) not null,
    postal_code     varchar(10) not null,
    default_address bit         null,
    user_id         binary(16)  not null
        primary key,
    constraint FK1fa36y2oqhao3wgg2rw1pi459
        foreign key (user_id) references shop_app_monolith.users (id)
);

create table shop_app_monolith.articles
(
    id           binary(16)   not null
        primary key,
    alias        varchar(500) not null,
    content      tinytext     not null,
    published    bit          not null,
    title        varchar(256) not null,
    type         smallint     null,
    updated_time datetime(6)  null,
    user_id      binary(16)   null,
    constraint FKlc3sm3utetrj1sx4v9ahwopnr
        foreign key (user_id) references shop_app_monolith.users (id)
);

create table shop_app_monolith.cart_items
(
    id         binary(16) not null
        primary key,
    quantity   int        not null,
    product_id binary(16) null,
    user_id    binary(16) null,
    constraint FK1re40cjegsfvw58xrkdp6bac6
        foreign key (product_id) references shop_app_monolith.products (id),
    constraint FK709eickf3kc0dujx3ub9i7btf
        foreign key (user_id) references shop_app_monolith.users (id)
);

create table shop_app_monolith.credit_cards
(
    id                 binary(16)   not null
        primary key,
    created_at         datetime(6)  not null,
    updated_at         datetime(6)  null,
    revision           bigint       null,
    credit_card_number varchar(255) not null,
    cvv                varchar(255) not null,
    expiration_date    varchar(255) not null,
    user               binary(16)   null,
    constraint FK1316uon7dnxmwwmxq4cvh5qbd
        foreign key (user) references shop_app_monolith.users (id)
);

create table shop_app_monolith.menus
(
    id         binary(16)   not null
        primary key,
    alias      varchar(256) not null,
    enabled    bit          not null,
    position   int          not null,
    title      varchar(128) not null,
    type       smallint     null,
    article_id binary(16)   null,
    constraint UK_752mo4d1g26knhksum5pek87
        unique (title),
    constraint UK_7fcv9rm1ecfeumh1lo4tuntgm
        unique (alias),
    constraint FKa5eajuapjflm0jk7xh5mangy5
        foreign key (article_id) references shop_app_monolith.articles (id)
);

create table shop_app_monolith.orders
(
    id             binary(16)   not null
        primary key,
    address_line_1 varchar(64)  not null,
    address_line_2 varchar(64)  null,
    city           varchar(45)  not null,
    country        varchar(45)  not null,
    first_name     varchar(45)  not null,
    last_name      varchar(45)  not null,
    phone_number   varchar(15)  not null,
    postal_code    varchar(10)  not null,
    deliver_date   datetime(6)  null,
    deliver_days   int          not null,
    order_time     datetime(6)  null,
    payment_method varchar(255) null,
    product_cost   float        not null,
    shipping_cost  float        not null,
    status         varchar(255) null,
    subtotal       float        not null,
    tax            float        not null,
    total          float        not null,
    user_id        binary(16)   null,
    constraint FK32ql8ubntj5uh44ph9659tiih
        foreign key (user_id) references shop_app_monolith.users (id)
);

create table shop_app_monolith.order_details
(
    id            binary(16) not null
        primary key,
    product_cost  float      not null,
    quantity      int        not null,
    shipping_cost float      not null,
    subtotal      float      not null,
    unit_price    float      not null,
    order_id      binary(16) null,
    product_id    binary(16) null,
    constraint FK4q98utpd73imf4yhttm3w0eax
        foreign key (product_id) references shop_app_monolith.products (id),
    constraint FKjyu2qbqt8gnvno9oe9j2s2ldk
        foreign key (order_id) references shop_app_monolith.orders (id)
);

create table shop_app_monolith.order_track
(
    id           binary(16)   not null
        primary key,
    notes        varchar(256) null,
    status       varchar(45)  not null,
    updated_time datetime(6)  null,
    order_id     binary(16)   null,
    constraint FK31jv1s212kajfn3kk1ksmnyfl
        foreign key (order_id) references shop_app_monolith.orders (id)
);

create table shop_app_monolith.password_reset_tokens
(
    id    binary(16)   not null
        primary key,
    token varchar(255) not null,
    user  binary(16)   null,
    constraint FK9cu5fq6ng9b0xhkicslddfoma
        foreign key (user) references shop_app_monolith.users (id)
);

create table shop_app_monolith.questions
(
    id          binary(16)   not null
        primary key,
    answer      varchar(255) null,
    answer_time datetime(6)  null,
    approved    bit          not null,
    ask_time    datetime(6)  null,
    question    varchar(255) null,
    votes       bigint       not null,
    answerer_id binary(16)   null,
    asker_id    binary(16)   null,
    product_id  binary(16)   null,
    constraint FKdnt39hlm1bcye9ivenccipd5s
        foreign key (product_id) references shop_app_monolith.products (id),
    constraint FKm993xs48yltdhr0qws3s1mpdn
        foreign key (answerer_id) references shop_app_monolith.users (id),
    constraint FKsat5fmui2uw8nnhan1dx817q1
        foreign key (asker_id) references shop_app_monolith.users (id)
);

create table shop_app_monolith.questions_votes
(
    id          binary(16) not null
        primary key,
    votes       bigint     not null,
    question_id binary(16) null,
    user_id     binary(16) null,
    constraint FK2xrl5kkgbdraix1mqr90elvdf
        foreign key (user_id) references shop_app_monolith.users (id),
    constraint FKehqtpwaad8w20qfxc8aqj0uiy
        foreign key (question_id) references shop_app_monolith.questions (id)
);

create table shop_app_monolith.reviews
(
    id          binary(16)   not null
        primary key,
    comment     varchar(300) not null,
    headline    varchar(128) not null,
    rating      bigint       not null,
    review_time datetime(6)  not null,
    votes       bigint       not null,
    product_id  binary(16)   null,
    user_id     binary(16)   null,
    constraint FKcgy7qjc1r99dp117y9en6lxye
        foreign key (user_id) references shop_app_monolith.users (id),
    constraint FKpl51cejpw4gy5swfar8br9ngi
        foreign key (product_id) references shop_app_monolith.products (id)
);

create table shop_app_monolith.reviews_votes
(
    id        binary(16) not null
        primary key,
    votes     bigint     not null,
    review_id binary(16) null,
    user_id   binary(16) null,
    constraint FK504qx90s9fupgf7982xnajla2
        foreign key (user_id) references shop_app_monolith.users (id),
    constraint FKosupda11xqkvo80r77evmwrey
        foreign key (review_id) references shop_app_monolith.reviews (id)
);

create table shop_app_monolith.sections_articles
(
    id            binary(16) not null
        primary key,
    article_order int        null,
    article_id    binary(16) null,
    section_id    binary(16) null,
    constraint FKguoivqg4rnmm8nilb12x40ygh
        foreign key (article_id) references shop_app_monolith.articles (id),
    constraint FKnbym8cke5rnxjm8bdi70dapl
        foreign key (section_id) references shop_app_monolith.sections (id)
);

create table shop_app_monolith.users_roles
(
    user binary(16) not null,
    role binary(16) not null,
    primary key (user, role),
    constraint FKbtpukn3y3g8meo2dmdrgryl91
        foreign key (role) references shop_app_monolith.roles (id),
    constraint FKkjti9tmthqhpqdhugbhcngweo
        foreign key (user) references shop_app_monolith.users (id)
);

create table shop_app_monolith.users_snapshots
(
    id                 binary(16)   not null
        primary key,
    created_at         datetime(6)  not null,
    updated_at         datetime(6)  null,
    max_revision       bigint       null,
    email              varchar(120) not null,
    encrypted_password varchar(255) not null,
    first_name         varchar(50)  not null,
    last_name          varchar(50)  not null
);
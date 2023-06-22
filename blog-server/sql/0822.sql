create table blog.article
(
    id          bigint auto_increment comment 'id'
        primary key,
    title       varchar(100)                        not null comment '标题',
    content     text                                null comment '内容',
    create_by   bigint                              not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_by   bigint                              not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted     tinyint   default 0                 not null
);

create table blog.article_comment
(
    article_id  bigint                              not null comment '文章id',
    comment_id  bigint                              not null comment '评论id',
    create_by   bigint                              not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_by   bigint                              not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted     tinyint   default 0                 not null,
    constraint article_comment_UN
        unique (article_id, comment_id)
);

create table blog.article_tag
(
    article_id  bigint                              not null comment '文章id',
    tag_id      bigint                              not null comment '标签id',
    create_by   bigint                              not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_by   bigint                              not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted     tinyint   default 0                 not null,
    constraint article_tag_UN
        unique (article_id, tag_id)
);

create table blog.comment
(
    id          bigint auto_increment
        primary key,
    content     text                                not null comment '内容',
    create_by   bigint                              not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_by   bigint                              not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted     tinyint   default 0                 not null
);

create table blog.message
(
    id          bigint auto_increment
        primary key,
    content     mediumtext                          not null comment '内容',
    create_by   bigint                              not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_by   bigint                              not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted     tinyint   default 0                 not null
);

create table blog.tag
(
    id          bigint auto_increment
        primary key,
    name        varchar(100)                        not null comment '名称',
    create_by   bigint                              not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_by   bigint                              not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted     tinyint   default 0                 not null
);

create table blog.user
(
    id          bigint auto_increment comment 'id'
        primary key,
    username    varchar(30)                         not null comment '用户名',
    password    varchar(500)                        null comment '密码',
    create_by   bigint                              not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_by   bigint                              not null,
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    deleted     tinyint   default 0                 not null
);


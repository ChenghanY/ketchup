create table file_output_record
(
    id           bigint auto_increment
        primary key,
    file_name    varchar(100)                           not null,
    file_ref     varchar(255)                           not null comment '文件关联信息，可以是文件物理地址oss的keyurl等等',
    output_type  varchar(16)                            not null comment '导出类型：异步或同步',
    output_state varchar(16)                            not null,
    created_at   datetime     default CURRENT_TIMESTAMP not null,
    created_by   varchar(100) default 'sys'             not null,
    updated_at   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    updated_by   varchar(100) default 'sys'             not null
)
    comment '文件记录';


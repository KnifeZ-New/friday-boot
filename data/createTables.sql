create table app_audit_log
(
    id             bigint auto_increment comment '主键id'
        primary key,
    user_id        varchar(50) null comment '用户id',
    service_name   varchar(500) null comment '服务名',
    action_name    varchar(500) null comment '方法名',
    parameters     varchar(500) null comment '请求参数',
    execution_time datetime null comment '执行时间',
    duration       bigint null comment '执行耗时',
    result_code    int null comment '状态码',
    result_message varchar(500) charset utf8mb3 null comment '执行消息',
    exception      text null comment '异常信息',
    ip             varchar(255) null,
    host_name      varchar(255) null
) comment '审计日志' charset = utf8mb4;

create table app_dictionary
(
    id          bigint auto_increment comment '主键id'
        primary key,
    name        varchar(255) null comment '字典名称',
    code        varchar(255) null comment '字典编码',
    description varchar(255) null comment '备注',
    is_enabled  tinyint(1) default 0 null comment '是否启用',
    create_by   varchar(255) not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_by   varchar(255) not null comment '修改人',
    update_time datetime     not null comment '更新时间'
) comment '字典' charset = utf8mb4;

create table app_dictionary_config
(
    id          bigint auto_increment comment '主键id'
        primary key,
    parent_id   bigint null comment '父级节点',
    dict_code   varchar(255) null comment '字典编码',
    name        varchar(255) charset utf8mb3 null comment '名字',
    value_type  varchar(255) null comment '数据类型',
    value       varchar(255) null comment '数据值',
    label_level varchar(255) null comment '标签等级',
    icon        varchar(255) null comment '图标',
    sort        int null comment '排序',
    description varchar(255) null comment '备注',
    is_enabled  tinyint(1) default 0 null comment '是否启用',
    create_by   varchar(255) not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_by   varchar(255) not null comment '修改人',
    update_time datetime     not null comment '更新时间'
) comment '字典配置' charset = utf8mb4;

create table app_menu
(
    id          int auto_increment comment '主键id'
        primary key,
    parent_id   int null comment '父级节点',
    name        varchar(255) null comment '菜单名称',
    route       varchar(255) null comment '菜单标识',
    type        int null comment '菜单类型',
    sort        int null comment '排序',
    icon        varchar(255) null comment '图标',
    transition  varchar(255) null comment '过场动画',
    badge       varchar(255) null comment '徽章',
    route_path  varchar(255) null comment '路由地址',
    component   varchar(255) null comment '组件路径',
    permission  varchar(255) null comment '权限标识',
    redirect    varchar(255) charset utf8mb3 null comment '重定向',
    keep_alive  tinyint(1) default 0 null comment '是否缓存',
    is_fixed    tinyint(1) default 0 null comment '是否固定',
    is_hot      tinyint(1) default 0 null comment '是否热点',
    is_visible  tinyint(1) default 1 null comment '是否显示',
    is_enabled  tinyint(1) default 1 null comment '是否启用',
    remark      varchar(500) null comment '备注',
    is_deleted  tinyint(1) default 0 null comment '是否删除',
    delete_by   varchar(255) charset utf8mb3 null comment '删除人',
    delete_time datetime null comment '删除时间',
    create_by   varchar(255) not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_by   varchar(255) not null comment '修改人',
    update_time datetime     not null comment '更新时间'
) comment '菜单' charset = utf8mb4;

create table app_organization_unit
(
    id          bigint auto_increment comment '主键id'
        primary key,
    unit_code   varchar(255) null comment '机构编码',
    parent_id   bigint null comment '父级节点',
    name        varchar(255) null comment '名称',
    description varchar(255) null comment '简介',
    create_by   varchar(255) not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_by   varchar(255) not null comment '修改人',
    update_time datetime     not null comment '更新时间'
) comment '组织机构' charset = utf8mb4;

create table app_organization_unit_role
(
    id                   bigint auto_increment comment '主键id'
        primary key,
    organization_unit_id varchar(255) null comment '组织机构id',
    role_id              varchar(255) null comment '角色id',
    create_by            varchar(255) not null comment '创建人',
    create_time          datetime     not null comment '创建时间',
    update_by            varchar(255) not null comment '修改人',
    update_time          datetime     not null comment '更新时间'
) comment '组织机构角色' charset = utf8mb4;

create table app_permission_grant
(
    id           bigint auto_increment comment '主键id'
        primary key,
    name         varchar(255) null comment '权限',
    provide_name varchar(255) null comment '授权类型',
    provide_for  varchar(255) null comment '授权对象'
) comment '授权记录' charset = utf8mb4;

create table app_role
(
    id           bigint auto_increment comment '主键id'
        primary key,
    name         varchar(255) null comment '角色',
    display_name varchar(255) null comment '显示名称',
    description  varchar(255) null comment '描述',
    is_enabled   tinyint(1) default 1 null comment '状态',
    create_by    varchar(255) not null comment '创建人',
    create_time  datetime     not null comment '创建时间',
    update_by    varchar(255) not null comment '修改人',
    update_time  datetime     not null comment '更新时间'
) comment '角色' charset = utf8mb4;

create table app_user
(
    id              bigint        not null
        primary key,
    account         varchar(255)  not null comment '帐号',
    username        varchar(255)  not null comment '用户名',
    password        varchar(255)  not null comment '密码',
    email           varchar(255) null comment '邮箱',
    phone           varchar(11) null comment '电话',
    avatar          varchar(255) null comment '头像',
    organization_id bigint null comment '所属部门id',
    is_locked       tinyint(1) default 0 null comment '是否锁定',
    create_by       varchar(255) null comment '创建人',
    create_time     datetime null comment '创建时间',
    update_by       varchar(255) null comment '修改人',
    update_time     datetime null comment '更新时间',
    is_deleted      int default 0 not null comment '是否删除',
    delete_by       varchar(255) null comment '删除人',
    delete_time     varchar(255) null comment '删除时间'
) comment '系统用户表' charset = utf8mb4;

create table app_user_role
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint null comment '用户id',
    role_id     bigint null comment '角色id',
    create_by   varchar(255) not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_by   varchar(255) not null comment '修改人',
    update_time datetime     not null comment '更新时间'
) comment '用户角色关联表' charset = utf8mb4;



INSERT INTO app_user (id, account, username, password, email, phone, avatar, organization_id, is_locked, create_by,
                      create_time, update_by, update_time, is_deleted, delete_by, delete_time)
VALUES (1, 'admin', '管理员', '$2a$10$RJzOtzT.fvCV6BQP/KBDiO3E3vAKzvcDB56xaUvE6ydmKto1AxLc6', '', null, null, 0, 0,
        'admin', NOW(), 'admin', NOW(), 0, null, null);

INSERT INTO app_role (id, name, display_name, description, is_enabled, create_by, create_time, update_by, update_time)
VALUES (1, 'SuperAdmin', '超级管理员', '拥有系统全部权限', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO app_user_role (id, user_id, role_id, create_by, create_time, update_by, update_time)
VALUES (1, 1, 1, 'admin', NOW(), 'admin', NOW());

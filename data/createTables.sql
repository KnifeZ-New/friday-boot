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

INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (2, 25, '部门管理', 'DepartmentManagement', 1, 2, 'ant-design:deployment-unit-outlined', 'fade', '',
        'organization-unit', '/admin/organization-unit/index.vue', '/api/organization-unit/tree-list:post', null, 1, 0,
        0, 1, 1, '12', 0, null, null, 'admin', '2022-10-13 13:07:02', 'admin', '2023-05-11 11:05:31');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (3, 2, '添加', 'Create', 2, 1, null, null, null, 'DepartmentManagement/Create', null,
        '/api/organization-unit:post', null, 0, 0, 0, 0, 1, null, 0, null, null, 'admin', '2022-10-13 13:07:29',
        'admin', '2022-10-18 17:35:48');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (4, 2, '修改', 'Update', 2, 2, null, null, null, 'DepartmentManagement/Update', null,
        '/api/organization-unit/{id}:post', null, 0, 0, 0, 0, 1, null, 0, null, null, 'admin', '2022-10-13 13:08:25',
        'admin', '2022-10-18 17:36:32');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (5, 2, '删除', 'Delete', 2, 3, null, null, null, 'DepartmentManagement/Delete', null,
        '/api/organization-unit/{id}:delete', null, 0, 0, 0, 0, 1, null, 0, null, null, 'admin', '2022-10-13 13:08:48',
        'admin', '2022-10-18 17:36:49');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (8, null, '配置', 'Settings', 0, 2, 'ant-design:appstore-twotone', 'fade', '', '/settings', 'Layout', '', null,
        0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2022-10-13 13:21:49', 'admin', '2023-05-04 15:38:37');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (9, 8, '字典管理', 'DictionaryManagement', 1, 1, 'ant-design:book-outlined', 'fade-left', '', 'dictionary',
        '/config/dict/index', '/api/dictionary/list:post', null, 1, 0, 0, 1, 1, '12', 0, null, null, 'admin',
        '2022-10-13 13:37:40', 'admin', '2023-05-04 15:45:46');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (12, 25, '角色管理', 'RoleManagement', 1, 4, 'ant-design:unlock-outlined', '', '', 'role',
        '/admin/role/index.vue', '/api/role/list:post', null, 1, 0, 0, 1, 1, '', 0, null, null, 'admin',
        '2022-10-27 07:21:14', 'admin', '2023-05-11 11:05:47');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (14, 25, '菜单管理', 'MenuManagement', 1, 1, 'ri:organization-chart', '', '', 'menu', '/admin/menu/index.vue',
        '/api/menu/tree-list:post', null, 1, 0, 0, 1, 1, '', 0, null, null, 'admin', '2022-10-27 07:24:21', 'admin',
        '2023-05-11 11:05:25');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (17, 25, '系统日志', 'SystemLog', 1, 8, 'ant-design:bug-outlined', '', '', '/sys/log',
        '/sys/error-log/index.vue', '', null, 1, 0, 0, 1, 1, '', 0, null, null, 'admin', '2022-10-27 07:26:50', 'admin',
        '2023-10-12 14:53:46');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (25, null, '系统管理', 'SystemManagement', 0, 1, 'ant-design:setting-outlined', null, null, '/admin', 'Layout',
        '', null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2022-12-08 09:44:01', 'admin', '2023-02-14 14:52:43');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (26, 25, '用户管理', 'UserManagement', 1, 3, 'ant-design:user-outlined', null, null, 'user',
        '/admin/user/index.vue', '/api/user/list:post', null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin',
        '2022-12-08 09:45:42', 'admin', '2023-05-11 11:05:38');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (27, null, '主页', 'dashboard', 0, 0, 'ant-design:home-outlined', null, null, '/dashboard', 'Layout', '', null,
        0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2022-12-08 09:49:09', 'admin', '2023-05-04 15:46:19');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (28, 27, '工作台', 'workbench', 1, 1, 'ant-design:coffee-outlined', null, null, 'workbench',
        '/dashboard/workbench/index', '', null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-02-16 09:58:50',
        'admin', '2023-02-16 09:58:50');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (29, 14, '新增', 'Create', 2, 2, null, null, null, 'MenuManagement/Create', null, '/api/menu:post', null, 0, 0,
        0, 1, 1, null, 0, null, null, 'admin', '2023-05-04 15:23:25', 'admin', '2023-05-05 16:20:35');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (30, 14, '编辑', 'Update', 2, 3, null, null, null, 'MenuManagement/Update', null, '/api/menu/{id}:post', null, 0,
        0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-05 16:02:14', 'admin', '2023-05-05 16:20:27');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (31, 14, '删除', 'Delete', 2, 3, null, null, null, 'MenuManagement/Delete', null, '/api/menu/{id}:delete', null,
        0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-05 16:19:32', 'admin', '2023-05-05 16:19:32');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (32, 14, '查询', 'Search', 2, 1, null, null, null, 'MenuManagement/Search', null, '/api/menu/tree-list:post',
        null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-05 16:20:11', 'admin', '2023-05-05 16:20:11');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (33, 8, '字典配置', 'DictionaryConfig', 1, 1, 'ant-design:appstore-outlined', null, null, 'dictionary-config',
        '/config/dict/DictionaryConfigDrawer', '/api/dictionary-config/{id}:get', null, 0, 0, 0, 0, 1, null, 1, null,
        null, 'admin', '2023-05-08 09:19:27', 'admin', '2023-05-08 09:48:56');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (34, 9, '查询', 'Search', 2, 1, null, null, null, 'DictionaryManagement/Search', null,
        '/api/dictionary/list:post', null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-08 09:19:55', 'admin',
        '2023-05-08 09:19:55');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (35, 9, '新增', 'Create', 2, 2, null, null, null, 'DictionaryManagement/Create', null, '/api/dictionary:post',
        null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-08 09:37:22', 'admin', '2023-05-08 09:37:22');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (36, 33, '新增', 'Create', 2, 2, null, null, null, 'DictionaryConfig/Create', null,
        '/api/dictionary-config:post', null, 0, 0, 0, 1, 1, null, 1, null, null, 'admin', '2023-05-08 09:38:45',
        'admin', '2023-05-08 09:48:49');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (37, 9, '字典配置', 'DictionaryConfig', 2, 5, null, null, null, 'DictionaryManagement/DictionaryConfig', null,
        '/api/dictionary-config/{id}:get', null, 0, 0, 0, 1, 1, null, 1, null, null, 'admin', '2023-05-08 09:47:16',
        'admin', '2023-05-08 15:07:12');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (38, 9, '删除', 'Delete', 2, 3, null, null, null, 'DictionaryManagement/Delete', null,
        '/api/dictionary/{id}:delete', null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-08 09:47:41',
        'admin', '2023-05-08 09:47:41');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (39, 9, '编辑', 'Update', 2, 4, null, null, null, 'DictionaryManagement/Update', null,
        '/api/dictionary/{id}:post', null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-08 09:49:25', 'admin',
        '2023-05-08 09:49:41');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (40, 9, '字典配置', 'DictionaryConfig', 2, 10, 'ant-design:book-outlined', null, null,
        'DictionaryManagement/DictionaryConfig', '/config/dict/DictionaryConfigDrawer',
        '/api/dictionary-config/tree-sets:post', null, 0, 0, 0, 0, 1, null, 0, null, null, 'admin',
        '2023-05-08 11:05:01', 'admin', '2023-10-14 16:33:30');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (41, 40, '新增', 'Create', 2, 1, null, null, null, 'DictionaryConfig/Create', null,
        '/api/dictionary-config:post', null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-08 11:06:00',
        'admin', '2023-05-08 11:06:00');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (42, 40, '删除', 'Delete', 2, 2, null, null, null, 'DictionaryConfig/Delete', null,
        '/api/dictionary-config/{id}:delete', null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-08 11:06:16',
        'admin', '2023-05-08 11:06:16');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (43, 40, '编辑', 'Update', 2, 3, null, null, null, 'DictionaryConfig/Update', null,
        '/api/dictionary-config/{id}:post', null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-08 11:06:28',
        'admin', '2023-05-08 11:06:28');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (44, 40, '查询', 'Search', 2, 4, null, null, null, 'DictionaryConfig/Search', null,
        '/api/dictionary-config/tree-sets:post', null, 0, 0, 0, 1, 1, null, 0, null, null, 'admin',
        '2023-05-08 11:07:07', 'admin', '2023-05-08 11:07:07');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (45, 26, '新增', 'Create', 2, 1, null, null, null, 'UserManagement/Create', null, '/api/user:post', null, 0, 0,
        0, 1, 1, null, 0, null, null, 'admin', '2023-05-11 11:06:15', 'admin', '2023-05-11 11:06:15');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (46, 26, '编辑', 'Update', 2, 2, null, null, null, 'UserManagement/Update', null, '/api/user/{id}:post', null, 0,
        0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-13 09:01:12', 'admin', '2023-05-13 09:01:12');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (47, 26, '删除', 'Delete', 2, 3, null, null, null, 'UserManagement/Delete', null, '/api/user/{id}:delete', null,
        0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-05-13 09:01:30', 'admin', '2023-05-13 09:01:30');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (48, 12, '编辑', 'Update', 2, 1, null, null, null, 'RoleManagement/Update', null, '/api/role/{id}:post', null, 0,
        0, 0, 1, 1, null, 0, null, null, 'admin', '2023-08-23 11:40:09', 'admin', '2023-08-23 11:40:09');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (49, 12, '删除', 'Delete', 2, 2, null, null, null, 'RoleManagement/Delete', null, '/api/role/{id}:delete', null,
        0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-08-23 14:14:56', 'admin', '2023-08-23 14:14:56');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (50, 12, '新增', 'Create', 2, 3, null, null, null, 'RoleManagement/Create', null, '/api/role:post', null, 0, 0,
        0, 1, 1, null, 0, null, null, 'admin', '2023-08-23 14:15:17', 'admin', '2023-08-23 14:15:17');
INSERT INTO app_menu (id, parent_id, name, route, type, sort, icon, transition, badge, route_path, component,
                      permission, redirect, keep_alive, is_fixed, is_hot, is_visible, is_enabled, remark, is_deleted,
                      delete_by, delete_time, create_by, create_time, update_by, update_time)
VALUES (51, 14, '按钮配置', 'MenuButtonConfig', 2, 5, null, null, null, null, null, '/api/menu/button-list:post', null,
        0, 0, 0, 1, 1, null, 0, null, null, 'admin', '2023-10-16 11:00:57', 'admin', '2023-10-16 13:39:33');

INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('2', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('3', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('4', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('5', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('27', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('28', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('26', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('14', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('32', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('29', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('30', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('31', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('8', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('9', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('40', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('34', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('35', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('38', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('39', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('41', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('42', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('43', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('44', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('17', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('45', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('46', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('47', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('12', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('48', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('49', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('50', 'ROLE', 'SuperAdmin');
INSERT INTO app_permission_grant (name, provide_name, provide_for)
VALUES ('25', 'ROLE', 'SuperAdmin');

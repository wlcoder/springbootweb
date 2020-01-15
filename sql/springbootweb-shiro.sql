
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱|登录帐号',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` bigint(1) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  `ip` varchar(20) DEFAULT NULL COMMENT 'ip',
  `image` varchar(200) DEFAULT NULL COMMENT '图像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `type` varchar(20) DEFAULT NULL COMMENT '角色类型',
  `description` varchar(50) DEFAULT NULL COMMENT '角色描述',
  `status` char(2) DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  `url` varchar(256) DEFAULT NULL COMMENT 'url地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL,
  `department_code` varchar(20) DEFAULT NULL,
  `department_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增列',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `gender` int(2) DEFAULT NULL COMMENT '性别 0：男 1：女',
  `birth` date DEFAULT NULL COMMENT '生日',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;


INSERT INTO `user` VALUES (33, 'wanglong', '系统管理员', 'long@qq.com', 'adf08ad3870aec8f7e8b767d3e72b8ef', '2019-12-14 11:23:18', '2020-1-15 20:57:31', 1, '127.0.0.1', 'springbootweb-img/809b3a9591ea4d1db36b2be9c770695c.png');
INSERT INTO `user` VALUES (39, 'we', '呵呵', 'haa@qq.com', 'd1681f4daf93caf0d34e39e45a3d5363', '2020-1-6 16:31:23', NULL, 1, '127.0.0.1', 'springbootweb-img/691d703b81a9431ea671a6521f327540.jpg');

INSERT INTO `role` VALUES (8, 'admin', 'W001', '管理员', '1');
INSERT INTO `role` VALUES (9, '董事长', 'W002', '公司NO1', '1');

INSERT INTO `permission` VALUES (8, '用户菜单', 'user_menu');
INSERT INTO `permission` VALUES (9, '角色菜单', 'role_menu');
INSERT INTO `permission` VALUES (10, '权限菜单', 'permission_menu');
INSERT INTO `permission` VALUES (11, '用户添加', '/user/addUser');
INSERT INTO `permission` VALUES (12, '密码重置', '/user/updatePwd');
INSERT INTO `permission` VALUES (13, '禁用/启用', '/user/updateStatus');
INSERT INTO `permission` VALUES (14, '用户添加角色', '/user/setUserRole');
INSERT INTO `permission` VALUES (15, '用户删除', '/user/deleteUser');
INSERT INTO `permission` VALUES (26, '用户编辑', '/user/updateUser');

INSERT INTO `user_role` VALUES (14, 39, 8);
INSERT INTO `user_role` VALUES (15, 39, 9);
INSERT INTO `user_role` VALUES (16, 33, 8);
INSERT INTO `user_role` VALUES (17, 33, 9);

INSERT INTO `role_permission` VALUES (68, 8, 8);
INSERT INTO `role_permission` VALUES (69, 8, 9);
INSERT INTO `role_permission` VALUES (70, 8, 10);
INSERT INTO `role_permission` VALUES (71, 8, 11);
INSERT INTO `role_permission` VALUES (72, 8, 12);
INSERT INTO `role_permission` VALUES (73, 9, 12);
INSERT INTO `role_permission` VALUES (74, 9, 13);
INSERT INTO `role_permission` VALUES (75, 9, 14);
INSERT INTO `role_permission` VALUES (76, 9, 15);
INSERT INTO `role_permission` VALUES (77, 9, 26);

INSERT INTO `department` VALUES (1, 'A001', '开发部');
INSERT INTO `department` VALUES (2, 'A002', '运维部');
INSERT INTO `department` VALUES (3, 'A003', '测试部');

INSERT INTO `employee` VALUES (1, 'wanglong', 'wanglong@qq.com', 1, '2019-11-24', 1);
INSERT INTO `employee` VALUES (8, 'long', 'long@qq.com', 1, '2019-11-27', 3);
INSERT INTO `employee` VALUES (9, 'haha', 'haa@qq.com', 0, '2019-11-24', 1);
INSERT INTO `employee` VALUES (10, 'hah', 'xuhui@qq.com', 0, '2019-11-27', 3);
INSERT INTO `employee` VALUES (11, 'hah', 'wanglong@qq.com', 0, '2019-11-24', 1);
INSERT INTO `employee` VALUES (12, 'ff', 'wanglong@qq.com', 1, '2019-11-27', 1);
INSERT INTO `employee` VALUES (13, 'hah', 'haa@qq.com', 1, '2019-11-24', 1);
INSERT INTO `employee` VALUES (14, 'hah', '22@qq', 1, '2019-11-14', 1);
INSERT INTO `employee` VALUES (15, 'long', 'wanglong@qq.com', 1, '2019-11-25', 1);











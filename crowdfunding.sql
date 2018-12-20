/*
Navicat MySQL Data Transfer

Source Server         : Cay
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : crowdfunding

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-12-20 22:21:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '系统菜单', '0', null, null);
INSERT INTO `t_permission` VALUES ('2', '控制面板', '1', '/main', 'glyphicon glyphicon-dashboard');
INSERT INTO `t_permission` VALUES ('3', '权限管理', '1', null, 'glyphicon glyphicon glyphicon-tasks');
INSERT INTO `t_permission` VALUES ('4', '用户维护', '3', '/user/', 'glyphicon glyphicon-user');
INSERT INTO `t_permission` VALUES ('5', '角色维护', '3', '/role/', 'glyphicon glyphicon-king');
INSERT INTO `t_permission` VALUES ('6', '许可维护', '3', '/permission/', 'glyphicon glyphicon-lock');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'PM-项目经理', '2018-12-11 21:21:15', '2018-12-11 21:21:15');
INSERT INTO `t_role` VALUES ('2', 'SE-软件工程师', '2018-12-11 21:21:15', '2018-12-11 21:21:15');
INSERT INTO `t_role` VALUES ('3', 'PG-程序员', '2018-12-11 21:21:16', '2018-12-11 21:21:16');
INSERT INTO `t_role` VALUES ('4', 'TL-组长', '2018-12-11 21:21:16', '2018-12-11 21:21:16');
INSERT INTO `t_role` VALUES ('5', 'GL-组长', '2018-12-11 21:21:16', '2018-12-11 21:21:16');
INSERT INTO `t_role` VALUES ('6', 'QC-品质控制', '2018-12-11 21:21:16', '2018-12-11 21:21:16');
INSERT INTO `t_role` VALUES ('7', 'SA-软件架构师', '2018-12-11 21:21:16', '2018-12-11 21:21:16');
INSERT INTO `t_role` VALUES ('8', 'CMS-配置管理', '2018-12-11 21:21:16', '2018-12-11 21:21:16');
INSERT INTO `t_role` VALUES ('9', 'SYS-系统管理', '2018-12-11 21:21:16', '2018-12-11 21:21:16');
INSERT INTO `t_role` VALUES ('10', 'TEST-测试', '2018-12-11 21:21:16', '2018-12-11 21:21:16');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL,
  `permissionid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('8', '1', '1');
INSERT INTO `t_role_permission` VALUES ('9', '1', '2');
INSERT INTO `t_role_permission` VALUES ('10', '1', '3');
INSERT INTO `t_role_permission` VALUES ('12', '1', '5');
INSERT INTO `t_role_permission` VALUES ('13', '1', '6');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'caychen', 'caychen', 'caychen', 'caychen@aliyun.com', '2018-12-03 10:45:00', null);
INSERT INTO `t_user` VALUES ('2', 'tom', 'tom', 'tom', 'tom@aliyun.com', '2018-12-04 21:58:36', null);
INSERT INTO `t_user` VALUES ('3', 'amy', 'amy', 'amy', 'amy@qq.com', '2018-12-04 14:46:41', null);
INSERT INTO `t_user` VALUES ('4', 'helen', 'helen', 'helen', 'helen@gmail.com', '2018-12-05 10:05:54', null);
INSERT INTO `t_user` VALUES ('5', 'kitty', 'kitty', 'kitty', 'kitty@aliyun.com', '2018-12-06 07:59:06', null);
INSERT INTO `t_user` VALUES ('6', 'jack', 'jack', 'jack', 'jack@163.com', '2018-12-06 12:05:20', null);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `roleid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '1');
INSERT INTO `t_user_role` VALUES ('4', '2', '2');
INSERT INTO `t_user_role` VALUES ('5', '2', '4');
INSERT INTO `t_user_role` VALUES ('6', '2', '6');
INSERT INTO `t_user_role` VALUES ('7', '3', '9');
INSERT INTO `t_user_role` VALUES ('8', '4', '6');
INSERT INTO `t_user_role` VALUES ('9', '4', '2');
INSERT INTO `t_user_role` VALUES ('10', '5', '7');
INSERT INTO `t_user_role` VALUES ('11', '5', '8');
INSERT INTO `t_user_role` VALUES ('12', '6', '5');
INSERT INTO `t_user_role` VALUES ('26', '6', '6');
SET FOREIGN_KEY_CHECKS=1;

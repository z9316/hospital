/*
Navicat MySQL Data Transfer

Source Server         : hospital
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : hospital

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2017-10-06 16:27:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for man
-- ----------------------------
DROP TABLE IF EXISTS `man`;
CREATE TABLE `man` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `isonline` int(1) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of man
-- ----------------------------
INSERT INTO `man` VALUES ('1', 'zhouzs', 'NDI5N2Y0NGIxMzk1NTIzNTI0NWIyNDk3Mzk5ZDdhOTM=', '0', '522723316@qq.com');

-- ----------------------------
-- Table structure for parameter
-- ----------------------------
DROP TABLE IF EXISTS `parameter`;
CREATE TABLE `parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parameter
-- ----------------------------
INSERT INTO `parameter` VALUES ('1', 'luencetext', 'F:/text/', null);
INSERT INTO `parameter` VALUES ('2', 'luencescan', 'F:/luence/', null);
INSERT INTO `parameter` VALUES ('3', 'host', 'smtp.qq.com', null);
INSERT INTO `parameter` VALUES ('4', 'port', '587', null);
INSERT INTO `parameter` VALUES ('5', 'name', '绿茶中药店管理员', null);
INSERT INTO `parameter` VALUES ('6', 'email', '2898115960@qq.com', null);
INSERT INTO `parameter` VALUES ('7', 'password', '', null);

-- ----------------------------
-- Table structure for searchcontent
-- ----------------------------
DROP TABLE IF EXISTS `searchcontent`;
CREATE TABLE `searchcontent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of searchcontent
-- ----------------------------
INSERT INTO `searchcontent` VALUES ('1', 'F:/text/', 'hello', 'qweqwe');

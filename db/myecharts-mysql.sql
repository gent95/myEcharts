/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50162
 Source Host           : localhost:3306
 Source Schema         : myecharts

 Target Server Type    : MySQL
 Target Server Version : 50162
 File Encoding         : 65001

 Date: 23/11/2018 15:15:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for echarts_option
-- ----------------------------
DROP TABLE IF EXISTS `echarts_option`;
CREATE TABLE `echarts_option`  (
  `NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `STATUS` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `OPTION_JSON` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `OPTION_SCRIPT` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `Y_TYPE` int(11) NULL DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of echarts_option
-- ----------------------------
INSERT INTO `echarts_option` VALUES ('测试', '测试', '1', '2018-11-23 14:33:27', '{\"yAxis\":[{\"type\":\"value\"}],\"xAxis\":[{\"data\":[1,2,3,4,5,6,7,8,9,10,11,13,14,15,16],\"show\":true,\"type\":\"category\"}],\"calculable\":true,\"legend\":{\"orient\":\"horizontal\",\"data\":[\"测试\",\"X\"],\"top\":30,\"left\":\"left\"},\"series\":[{\"data\":[1,2,3,4,5,6,7,8,9,10,11,13,14,15,16],\"name\":\"测试\",\"itemStyle\":{\"normal\":{\"areaStyle\":{\"type\":\"default\"}}},\"type\":\"line\",\"smooth\":true},null],\"toolbox\":{\"padding\":20,\"feature\":{\"saveAsImage\":{\"show\":true,\"title\":\"保存为图片\",\"type\":\"png\",\"lang\":[\"点击保存\"]},\"restore\":{\"show\":true,\"title\":\"还原\"},\"magicType\":{\"show\":true,\"title\":{\"bar\":\"柱形图切换\",\"stack\":\"堆积\",\"tiled\":\"平铺\",\"line\":\"折线图切换\"},\"type\":[\"line\",\"bar\",\"stack\",\"tiled\"]},\"dataView\":{\"show\":true,\"readOnly\":false,\"title\":\"数据视图\",\"lang\":[\"数据视图\",\"关闭\",\"刷新\"]},\"mark\":{\"lineStyle\":{\"color\":\"#1e90ff\",\"width\":2,\"type\":\"dashed\"},\"show\":true,\"title\":{\"markUndo\":\"删除辅助线\",\"markClear\":\"清空辅助线\",\"mark\":\"辅助线开关\"}}},\"show\":true},\"tooltip\":{\"trigger\":\"axis\"},\"title\":{\"subtext\":\"测试\",\"x\":\"center\",\"text\":\"测试\"}}', '', 0, 1);

-- ----------------------------
-- Table structure for echarts_sql
-- ----------------------------
DROP TABLE IF EXISTS `echarts_sql`;
CREATE TABLE `echarts_sql`  (
  `OPT_ID` int(11) NULL DEFAULT NULL,
  `NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SQL` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `STATUS` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_TIME` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SORT` int(11) NULL DEFAULT NULL,
  `RADIUS` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SQL_PARAM` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `Y_TYPE` int(11) NULL DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of echarts_sql
-- ----------------------------
INSERT INTO `echarts_sql` VALUES (1, '测试', 'line', '\n	select id as value from test', '1', '2018-11-23 14:36:41', 1, '20', '', 0, 1);
INSERT INTO `echarts_sql` VALUES (1, 'X', 'xAis', '	select id as value from test', '1', '2018-11-23 14:54:27', 2, '', '', 0, 2);
INSERT INTO `echarts_sql` VALUES (1, 'pie', 'pie', '	select id as value,name as name from test', '1', '2018-11-23 14:57:39', 3, '30', '', 0, 3);

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (1, 'a');
INSERT INTO `test` VALUES (2, 'b');
INSERT INTO `test` VALUES (3, 'c');
INSERT INTO `test` VALUES (4, 'd');
INSERT INTO `test` VALUES (5, 'e');
INSERT INTO `test` VALUES (6, 'f');
INSERT INTO `test` VALUES (7, 'g');
INSERT INTO `test` VALUES (8, 'h');
INSERT INTO `test` VALUES (9, 'i');
INSERT INTO `test` VALUES (10, 'j');
INSERT INTO `test` VALUES (11, 'k');
INSERT INTO `test` VALUES (13, 'l');
INSERT INTO `test` VALUES (14, 'm');
INSERT INTO `test` VALUES (15, 'n');
INSERT INTO `test` VALUES (16, 'o');

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : 01-本地数据
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : sany

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 01/07/2019 13:44:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_org
-- ----------------------------
DROP TABLE IF EXISTS `t_org`;
CREATE TABLE `t_org`  (
    `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构编码 代码生成',
    `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构名称',
    `parent_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父机构编码 顶级机构父机构代码为空',
    `parent_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父机构名称',
    `linkman` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
    `status` int(11) NULL DEFAULT NULL COMMENT '状态',
    `telephone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
    `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置',
    `sons` int(11) NULL DEFAULT NULL COMMENT '子机构的数量 用于生成机构代码',
    `sync_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '同步Id',
    `valid` int(11) NOT NULL COMMENT '有效值 0-有效;1-无效',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建用户ID',
    `create_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户名',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `update_id` bigint(20) NULL DEFAULT NULL COMMENT '更新用户ID',
    `update_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户名',
    PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '机构管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_org
-- ----------------------------
INSERT INTO `t_org` VALUES ('200', '系统机构', '0', NULL, NULL, NULL, NULL, NULL, 3, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_org` VALUES ('200001', 'string', '200', '系统机构', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_org` VALUES ('200002', 'string', '200', '系统机构', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_org` VALUES ('200003', 'string', '200', '系统机构', NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;

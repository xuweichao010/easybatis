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

 Date: 01/07/2019 13:44:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
    `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
    `gender` int(11) NULL DEFAULT NULL COMMENT '性别 0-男性;1-女性',
    `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
    `identity_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `job_type` int(11) NULL DEFAULT NULL COMMENT '职位类型',
    `job_level` int(11) NULL DEFAULT NULL COMMENT '职位级别',
    `org_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构名',
    `org_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构代码',
    `sync_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '同步标识',
    `type` int(11) NOT NULL DEFAULT 3 COMMENT '用户类型 1-超级管理员;2-管理员;3-普通用户',
    `valid` int(11) NOT NULL COMMENT '有效值 0-有效;1-无效',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建用户ID',
    `create_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户名',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `update_id` bigint(20) NULL DEFAULT NULL COMMENT '更新用户ID',
    `update_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新用户名',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'superAdmin', '$2a$10$5IP.TBsP2Bff9GPnDUq0SuM5NnmEAMknnAjYAGkK0tco9NIq9rrlS', '超级管理员', 1, 0, '42098219931111111X', 0, 0, '系统机构', '200', NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user` VALUES (2, 'admin', '$2a$10$5IP.TBsP2Bff9GPnDUq0SuM5NnmEAMknnAjYAGkK0tco9NIq9rrlS', '管理员', 1, 0, '42098219931111111X', 0, 0, '系统机构', '200', NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user` VALUES (3, 'user', '$2a$10$5IP.TBsP2Bff9GPnDUq0SuM5NnmEAMknnAjYAGkK0tco9NIq9rrlS', '用户', 1, 0, '42098219931111111X', 0, 0, '系统机构', '200', NULL, 3, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user` VALUES (4, 'string', '$2a$10$XCL5XP4fi6KSVinjsyiskuhyMb08nyd/pSz5/Q832.0LX7RFq0LiK', 'string', 0, 0, 'string', 0, 0, '系统机构', '200', NULL, 3, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user` VALUES (5, 'string1', '$2a$10$GHfLe.MfK2pWhxDsSald4uaZ/CqsK/Oyb8vQfheVwq2LrENlxLR22', 'string', 0, 0, 'string', 0, 0, '系统机构', '200', NULL, 3, 0, NULL, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;

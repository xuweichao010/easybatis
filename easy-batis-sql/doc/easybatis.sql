/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : 127.0.0.1:3306
 Source Schema         : easybatis

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 17/10/2024 08:24:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_org
-- ----------------------------
DROP TABLE IF EXISTS `t_org`;
CREATE TABLE `t_org`
(
    `code`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '机构编码',
    `name`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '机构名称',
    `parent_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上级机构编码',
    `parent_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上级机构名称',
    `address`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '机构地址',
    `employees_num` int                                                          NULL DEFAULT 0 COMMENT '组织下的员工人数',
    PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '组织表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_org
-- ----------------------------
INSERT INTO `t_org`
VALUES ('200', '总公司', NULL, 'EasyBatis', '中国区域', 100);
INSERT INTO `t_org`
VALUES ('200001', '北京分公司', '200', '总公司', '北京', 30);
INSERT INTO `t_org`
VALUES ('200002', '上海分公司', '200', '总公司', '上海', 50);
INSERT INTO `t_org`
VALUES ('200003', '广州分公司', '200', '总公司', '广州', 55);
INSERT INTO `t_org`
VALUES ('200004', '武汉分公司', '200', '总公司', '武汉', 40);
INSERT INTO `t_org`
VALUES ('200004001', '事业部', '200004', '武汉分公司', '武汉', 33);
INSERT INTO `t_org`
VALUES ('200004002', '研发部', '200004', '武汉分公司', '武汉', 10);
INSERT INTO `t_org`
VALUES ('200004003', '售后部', '200004', '武汉分公司', '武汉', 11);
INSERT INTO `t_org`
VALUES ('200004004', '商务部', '200004', '武汉分公司', '武汉', 15);
INSERT INTO `t_org`
VALUES ('200005', '武汉二公司', '200', '总公司', '武汉江汉区', 17);
INSERT INTO `t_org`
VALUES ('200006', '武汉三公司', '200', '总公司', '武汉江汉区', 22);
INSERT INTO `t_org`
VALUES ('200007', '武汉四公司', '200', '总公司', '武汉江汉区', 25);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
    `org_code`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '机构编码',
    `org_name`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '机构名称',
    `valid`       int                                                          NULL DEFAULT NULL COMMENT '是否有效 0:有效  1:无效',
    `create_time` date                                                         NULL DEFAULT NULL COMMENT '创建时间',
    `create_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户ID',
    `create_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名',
    `update_time` date                                                         NULL DEFAULT NULL COMMENT '创建时间',
    `update_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新用户ID',
    `update_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新用户名',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
    `org_code`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '机构编码',
    `org_name`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '机构名称',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
    `age`         int                                                          NULL DEFAULT NULL COMMENT '用户年龄',
    `job`         int                                                          NULL DEFAULT NULL COMMENT '职位 1-总监 2-经理 1-主管 3-销售 4-行政 5-技术员 6-财务',
    `valid`       int                                                          NULL DEFAULT NULL COMMENT '是否有效 0:有效  1:无效',
    `data_type`   int                                                          NULL DEFAULT NULL COMMENT '1-正式数据 2-测试过程数据',
    `create_time` date                                                         NULL DEFAULT NULL COMMENT '创建时间',
    `create_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户ID',
    `create_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户名',
    `update_time` date                                                         NULL DEFAULT NULL COMMENT '创建时间',
    `update_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新用户ID',
    `update_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新用户名',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8a001', '200', '东汉', '曹操', 50, 1, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8a002', '200', '东汉', '小乔', 25, 4, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8a003', '200', '东汉', '曹植', 27, 2, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8a004', '200', '东汉', '吕布', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8a005', '200', '东汉', '荀攸', 30, 3, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8b001', '200001', '东吴', '杨修', 45, 2, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8b002', '200001', '东吴', '陈琳', 25, 4, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8b003', '200001', '东吴', '毛玠', 27, 2, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8b004', '200001', '东吴', '桓玠', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8b005', '200001', '东吴', '郗虑', 30, 3, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8b006', '200001', '东吴', '郭嘉', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8b007', '200001', '东吴', '司马朗', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL,
        NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8b008', '200001', '东吴', '韩暨', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8b009', '200001', '东吴', '韦康', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8b010', '200001', '东吴', '邴原', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db745aac8dee8b001', '200001', '东吴', '赵俨', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db745aac8dee8b002', '200001', '东吴', '娄圭', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db745aac8dee8b003', '200001', '东吴', '蒯越', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db744aac8dee8b001', '200002', '蜀汉', '诸葛亮', 45, 2, 1, 1, NULL, NULL, NULL, NULL, NULL,
        NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db744aac8dee8b002', '200002', '蜀汉', '糜芳', 25, 4, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db744aac8dee8b003', '200002', '蜀汉', '庞统', 27, 2, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db744aac8dee8b004', '200002', '蜀汉', '法正', 30, 3, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db744aac8dee8b005', '200002', '蜀汉', '许靖', 30, 3, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db744aac8dee8b006', '200002', '蜀汉', '马良', 30, 3, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db744aac8dee8b007', '200002', '蜀汉', '关羽', 30, 3, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db744aac8dee8b008', '200002', '蜀汉', '张飞', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db744aac8dee8b009', '200002', '蜀汉', '马超', 30, 5, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db744aac8dee8b010', '200002', '蜀汉', '魏延', 30, 3, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db745aac8dee8b001', '200002', '蜀汉', '关平', 30, 3, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db745aac8dee8b002', '200002', '上海分公司', '周仓', 30, 3, 1, 1, NULL, NULL, NULL, NULL, NULL,
        NULL);
INSERT INTO `t_user`
VALUES ('47bd0225cc94400db745aac8dee8b003', '200002', '上海分公司', '李严', 30, 3, 1, 1, NULL, NULL, NULL, NULL, NULL,
        NULL);

SET FOREIGN_KEY_CHECKS = 1;



--  建表
/**
CREATE TABLE  IF NOT EXISTS  `t_user` (
  `id` BIGINT  NOT NULL COMMENT '用户id',
  `account` varchar(32) NOT NULL  COMMENT '登录账号',
  `password` VARCHAR(64) NOT NULL COMMENT '账号密码',
  `email` VARCHAR(64) COMMENT '邮箱',
  `phone` VARCHAR(11) COMMENT '电话',
  `name` VARCHAR(64) COMMENT '姓名',
  `sex` INT(1) COMMENT '性别 1：男 2：女',
  `identity_card_number` VARCHAR(64) COMMENT '身份证号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户表';
 */

-- 操作表
/**
SELECT      COUNT(*)
INTO        @index
FROM        information_schema.COLUMNS
WHERE       Table_SCHEMA = DATABASE()
            AND   TABLE_NAME = 'project_sn'
            AND   COLUMN_NAME = '';
SET         @SQL = IF(@index <= 0,
                      'ALTER TABLE `t_project_alarm` ADD COLUMN `project_sn` varchar(20) COMMENT \'项目标识 \'',
                      'SELECT \'project_sn has been added\';');
PREPARE     statement
FROM        @SQL;
EXECUTE     statement;

*/



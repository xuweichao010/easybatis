/**
管理用户信息
 */
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE  IF NOT EXISTS  `t_user` (
  `account` varchar(32) NOT NULL  COMMENT '登录账号',
  `password` VARCHAR(64) NOT NULL COMMENT '账号密码',
  `email` VARCHAR(64) COMMENT '邮箱',
  `phone` VARCHAR(11) COMMENT '电话',
  `name` VARCHAR(64) COMMENT '姓名',
  `sex` INT(1) COMMENT '性别 1：男 2：女',
  `identity_card_number` VARCHAR(64) COMMENT '身份证号',
  PRIMARY KEY (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户表';


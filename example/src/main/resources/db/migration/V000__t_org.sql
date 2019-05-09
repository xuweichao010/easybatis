/**
管理用户信息
 */
DROP TABLE IF EXISTS `t_org`;
CREATE TABLE  IF NOT EXISTS  `t_org` (
  `code` VARCHAR(16) NOT NULL COMMENT '机构编码',
  `name` VARCHAR(128) NOT NULL UNIQUE COMMENT '机构名称',
  `link_name` VARCHAR(64) COMMENT '联系人',
  `tel` VARCHAR(32) COMMENT '联系电话',
  `parent_name` VARCHAR (128) NOT NULL COMMENT '父机构ID',
  `parent_code` VARCHAR(16) NOT NULL COMMENT '父机构code',
  `address` VARCHAR(128)  COMMENT  '机构地址',
  `order` INT(11)  DEFAULT  100 COMMENT '排序',
  `number` INT(11) DEFAULT  0 COMMENT '用于生成子机构编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='机构表';

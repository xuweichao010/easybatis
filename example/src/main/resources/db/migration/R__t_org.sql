/**
机构管理
 */
CREATE TABLE  IF NOT EXISTS  `t_org` (
    `code`        VARCHAR(32)    NOT NULL   COMMENT '机构编码 代码生成' ,
    `name`        VARCHAR(32)    COMMENT '机构名称' ,
    `parent_code` VARCHAR(32)    COMMENT '父机构编码 顶级机构父机构代码为空' ,
    `parent_name` VARCHAR(32)    COMMENT '父机构名称' ,
    `sons` INT    COMMENT '子机构的数量 用于生成机构代码' ,
    PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构管理';


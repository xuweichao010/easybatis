/**
用户管理
 */
CREATE TABLE  IF NOT EXISTS  t_user(
    `id`        BIGINT NOT NULL AUTO_INCREMENT  COMMENT '用户ID' ,
    `account`   VARCHAR(32)    COMMENT '账号' ,
    `password`  VARCHAR(64)    COMMENT '密码' ,
    `name`      VARCHAR(32)    COMMENT '用户名' ,
    `org_name`  VARCHAR(32)    COMMENT '机构名' ,
    `org_code`  VARCHAR(32)    COMMENT '机构代码' ,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构管理';





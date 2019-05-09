DROP TABLE IF EXISTS `t_client`;
CREATE TABLE  IF NOT EXISTS  `t_client` (
  `client_id` VARCHAR(32) COMMENT '客户端ID',
  `client_secret` VARCHAR(64) NOT NULL COMMENT '客户端授权信息',
  `scope` VARCHAR(64) NOT NULL COMMENT  '客户端授权范围',
  `grant_type` VARCHAR(64) NOT NULL COMMENT  '客户端支持的授权类型',
  `access_token_valid_seconds` int(11) NOT NULL DEFAULT 86400 COMMENT '授权信息的有效时间 默认24小时',
  `refresh_token_valid_seconds` int(11) NOT NULL DEFAULT 86400 COMMENT '刷新授权的信息的有效期 默认 24小时',
  `description` VARCHAR(512) COMMENT '描述这个授权信息是谁在用',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户端授权信息';






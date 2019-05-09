
-- 创建表

DROP TABLE IF EXISTS `demo`;
CREATE TABLE  IF NOT EXISTS  `demo` (
 -- 需要的字段
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='demo';

-- 操做表检测语句
SELECT      COUNT(*)
INTO        @index
FROM        information_schema.COLUMNS
WHERE       Table_SCHEMA = DATABASE()
            AND   TABLE_NAME = 'demo'
            AND   COLUMN_NAME = 'flied';
SET         @SQL = IF(@index <= 0,
                      'ALTER TABLE `demo` ADD COLUMN `flied` varchar(20) COMMENT \'添加字段 \'',
                      'SELECT \'project_sn has been added\';');
PREPARE     statement
FROM        @SQL;
EXECUTE     statement;


-- 删除表
DROP TABLE IF EXISTS `demo`;





## Easybatis
作者： cc
邮箱： xuweichao010@163.com
微信公众号： onezerocc

### 简介
Easybatis 是一个Mybatis的增强工具,它在MyBatis的声明式的模式上增强，让我们操作数据层更加简单。
#### 特性

- 只做对Mybatis的增强,它的引入不会对原有的mybatis项目产生影响
- 该增强插件主要是针对Mybatis的Configuration做出改变注入sql，性能基本无损耗。
- 支持数据库审计和逻辑删除。
- 丰富的声明式注解。

#### 支持数据库
- MySQl 数据库

#### 代码托管

#### 参入


### 快速开始
我们将通过一个简单的 Demo 来阐述 Easybatis 的强大功能，在此之前，你需要具备以下功能
 
 拥有 Java 1.8及以上的 开发环境以及相应 IDE
 熟悉 Spring Boot
 熟悉 Maven
#### 构建环境

- 表脚本
```sql
CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL COMMENT '用户ID',
  `org_code` varchar(32) DEFAULT NULL COMMENT '机构编码',
  `org_name` varchar(32) DEFAULT NULL COMMENT '机构名称',
  `name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `age` int(3) DEFAULT NULL COMMENT '用户年龄',
  `job` int(1) DEFAULT NULL COMMENT '职位 1-总监 2-经理 1-主管 3-销售 4-行政 5-技术员 6-财务',
  `valid` int(1) DEFAULT NULL COMMENT '是否有效 0:有效  1:无效',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建用户ID',
  `create_name` varchar(32) DEFAULT NULL COMMENT '创建用户名',
  `update_time` date DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) DEFAULT NULL COMMENT '更新用户ID',
  `update_name` varchar(32) DEFAULT NULL COMMENT '更新用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```
- 数据脚本
```sql
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8a001', '200', '总公司', '曹操', '50', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8a002', '200', '总公司', '小乔', '25', '4', '1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8a003', '200', '总公司', '曹植', '27', '2', '1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_user`
VALUES ('37bd0225cc94400db744aac8dee8a004', '200', '总公司', '吕布', '30', '5', '1', NULL, NULL, NULL, NULL, NULL, NULL);
```

- 
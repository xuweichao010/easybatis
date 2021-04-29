## 
作者： cc
邮箱： xuweichao010@163.com
微信公众号： onezerocc

### 1.0 简介
Easybatis 是一个Mybatis的增强工具,它在MyBatis的声明式的模式上增强，让我们操作数据层更加简单。
#### 特性

- 只做对Mybatis的增强,它的引入不会对原有的mybatis项目产生影响
- 该增强插件主要是针对Mybatis的Configuration做出改变注入sql，性能基本无损耗。
- 支持数据库审计和逻辑删除。
- 丰富的声明式注解。

#### 支持数据库
- MySQl 数据库

#### 规划


### 2.0 快速开始
我们将通过一个简单的 Demo 来阐述 Easybatis 的强大功能，在此之前，你需要具备以下功能

 拥有 Java 1.8及以上的 开发环境以及相应 IDE
 熟悉 Spring Boot
 熟悉 Maven

#### 引入坐标

```xml

```
#### 创建表结构

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
####  初始化表数据

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

####  创建实体

```java
@Data
@Table("t_user")
public class TUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.CUSTOM)
    // 用户id 
    private String id;
    // 机构编码
    private String orgCode;
    // 机构名称
    private String orgName;
    // 用户名
    private String name;
}
```

####  创建Mapper
```java
@Mapper
public interface UserMapper extends BaseMapper<User, String> {
}
```
#### 创建启动类
``` java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(QuickStartApplication.class, args);
    }

}
```
####  添加日志配置
```yml
mybatis:
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  #打印SQL执行日志
  easybatis:
  		# 打印构建的SQL语句
```
####  根据主键查询数据

- 测试代码

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QuickStartTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        User user = userMapper.selectKey("37bd0225cc94400db744aac8dee8a001");
        System.out.println(user);
    }
}
```
- 查看日志我们发现在项目的启动过程中easybatis为UserMapper.selectKey方法构建的sql语句，并会打印该语句。
```xml
UserMapper.selectKey 	   <script> SELECT `id`, `org_code`, `org_name`, `name` FROM t_user <where> `id` = #{id} </where></script>
```
- 查看mybatis的日志我们可以看到
```mysql
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@36c281ed] will not be managed by Spring
==>  Preparing: SELECT `id`, `org_code`, `org_name`, `name` FROM t_user WHERE `id` = ?
==> Parameters: 37bd0225cc94400db744aac8dee8a001(String)
<==    Columns: id, org_code, org_name, name
<==        Row: 37bd0225cc94400db744aac8dee8a001, 200, 总公司, 曹操
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@257e0827]
```
- 最后我们可以看见自己打印的输出日志
```
User(id=37bd0225cc94400db744aac8dee8a001, orgCode=200, orgName=总公司, name=曹操)
```
- 总结:
    通过以上几个简单的步骤，我们就实现了User表的CRUD功能，甚至连XML文件都不用编写了,只需要简单的配置一下就可以了，Easybatis 帮我们做的远远不止这些，我们继续了解它的其他功能把

### 3.0 核心功能

#### 核心接口

##### EasyMapper

​	用于标识`easybatis` 需要增强的`mapper`,其中的 `E` 是实体对象的数据类型 ，`K`是主键的数据类型。这个接口是没有任何方法的。

```
public interface EasyMapper<E,K> {
}
```

##### BaseMapper

​	Easybatis 默认提供的CRUD的接口，如果是参数为K 标识这个参数为主键，如果参数为E 标识参数为数据实体。在整个工具内部是通过  **`@SelectSql`**、**`@UpdateSql`**、**`@InsertSql`**、**`@DeleteSql`**，来决定如何具体如何创建SQL的。**所以开发者可以根据自己的需求来构建自己的BaseMapper接口也是允许的，但你必须继承EasyMapper接口及相应的注解规范**。

```java
public interface BaseMapper<E, K> extends EasyMapper<E, K> {
    
    @SelectSql
    E selectKey(K id);

    @UpdateSql
    Integer update(E entity);

    @UpdateSql(dynamic = true)
    Integer updateActivate(E entity);

    @InsertSql
    Integer insert(E entity);

    @InsertSql
    Integer insertBatch(Collection<E> list);

    @DeleteSql
    Integer delete(K id);

}
```



#### BaseMapper

​	我们通过BaseMapper来了解一下我们框架是如何做增删改查的，我们继续使用 **`快速开始`** 定义的实体对象

##### selectKey

​	我们已经在快速开始演示了该方法的执行，这里就不在继续演示了。

##### insert 单条写入

- 测试代码

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QuickStartTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        User user = userMapper.selectKey("37bd0225cc94400db744aac8dee8a001");
        System.out.println(user);
    }
}
```

- 在控制台中easybatis 自动构建的SQL语句

```xml
UserMapper.insert 	   <script> INSERT INTO t_user (`id`, `org_code`, `org_name`, `name`) VALUES (#{id}, #{orgCode}, #{orgName}, #{name}) </script>
```

- mybatis 框架执行日志

```sql
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@6dab01d9] will not be managed by Spring
==>  Preparing: INSERT INTO t_user (`id`, `org_code`, `org_name`, `name`) VALUES (?, ?, ?, ?)
==> Parameters: 123456789(String), test(String), 测试组(String), 这是一个测试(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@257e0827]
```

##### insertBatch 批量写入

- 测试代码

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QuickStartTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertBatch() {
        User user1 = new User();
        user1.setId("123456789");
        user1.setName("这是一个测试1");
        user1.setOrgName("测试组");
        user1.setOrgCode("test");

        User user2 = new User();
        user2.setId("123456780");
        user2.setName("这是一个测试2");
        user2.setOrgName("测试组");
        user2.setOrgCode("test");
        userMapper.insertBatch(Arrays.asList(user1, user2));
    }
}
```

- 在控制台中easybatis 自动构建的SQL语句

```xml
UserMapper.insertBatch 	   <script> INSERT INTO t_user (`id`, `org_code`, `org_name`, `name`) VALUES  <foreach item= 'item'  collection='list' separator=', '> (#{item.id}, #{item.orgCode}, #{item.orgName}, #{item.name}) </foreach> </script>
```

- mybatis 框架执行日志

```
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@16a9eb2e] was not registered for synchronization because synchronization is not active
2021-04-12 16:25:44.080  INFO 3068 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@31e2232f] will not be managed by Spring
==>  Preparing: INSERT INTO t_user (`id`, `org_code`, `org_name`, `name`) VALUES (?, ?, ?, ?) , (?, ?, ?, ?)
==> Parameters: 123456781(String), test(String), 测试组(String), 这是一个测试1(String), 123456780(String), test(String), 测试组(String), 这是一个测试2(String)
<==    Updates: 2
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@16a9eb2e]
```

##### update 更新数据

- 测试代码

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QuickStartTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void update() {
        User user = userMapper.selectKey("123456789");
        user.setName("这是一个测试更新");
        userMapper.update(user);
    }
}
```

- 在控制台中easybatis 自动构建的SQL语句

```
UserMapper.update 	   <script> UPDATE t_user SET `org_code` = #{orgCode}, `org_name` = #{orgName}, `name` = #{name} <where> `id` = #{id} </where></script>
```

- mybatis 框架执行日志

```
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@554c4eaa] will not be managed by Spring
==>  Preparing: UPDATE t_user SET `org_code` = ?, `org_name` = ?, `name` = ? WHERE `id` = ?
==> Parameters: test(String), 测试组(String), 这是一个测试更新(String), 123456789(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@44af588b]
```

##### updateActivate 动态更新

- 测试代码

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QuickStartTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void update() {
        User user = userMapper.selectKey("123456789");
        user.setName("这是一个测试更新");
        userMapper.update(user);
    }
}
```

- 在控制台中easybatis 自动构建的SQL语句

```xml
serMapper.updateActivate 	   <script> UPDATE t_user <set> <if test='orgCode != null'> `org_code` = #{orgCode},</if> <if test='orgName != null'> `org_name` = #{orgName},</if> <if test='name != null'> `name` = #{name},</if> </set> <where> `id` = #{id} </where></script>
```

- mybatis 框架执行日志

```
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@33f2df51] will not be managed by Spring
==>  Preparing: UPDATE t_user SET `name` = ? WHERE `id` = ?
==> Parameters: 这是一个测试更新1(String), 123456789(String)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4784efd9]
```

**Tips:**  我们发现 这个只更新了实体有值的属性，无值的属性它没有更新，`@UpdateSql(dynamic = true) `这个主要通过  dynamic = true 来控制的，后面我们详细来介绍注解的功能。

##### delete 删除

- 测试代码

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QuickStartTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void del(){
        userMapper.delete("12345678");
    }
}
```

- 在控制台中easybatis 自动构建的SQL语句

```xml
UserMapper.delete 	   <script> DELETE FROM t_user <where> `id` = #{id} </where></script>
```

- mybatis 框架执行日志

```sql
JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@4b5a078a] will not be managed by Spring
==>  Preparing: DELETE FROM t_user WHERE `id` = ?
==> Parameters: 12345678(String)
<==    Updates: 0
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4784efd9]
```

##### 总结: 

​		通过上面的CRUD的操作，我们可以发现easybatis 主要是通过接口和注解来动态的构建SQL。来帮助我们在项目的运行时构建我们想要的SQL语句。

#### 实体注解

##### @Table

- 描述：表名注解
- 属性：

| 属性  | 类型   | 默认值 | 是否必填 | 描述                                                         |
| ----- | ------ | ------ | -------- | ------------------------------------------------------------ |
| value | String | ""     | 否       | 当没有开启自动映射表明的配置时，是必填字段<br>自动映射配置（参考） |

##### @Id

- 描述: 主键注解

| 属性  | 类型   | 默认值        | 是否必填 | 描述                                                         |
| ----- | ------ | ------------- | -------- | ------------------------------------------------------------ |
| value | String | ""            | 否       | 主键字段名                                                   |
| type  | Enum   | IdType.GLOBAL | 否       | 主键类型<br>GLOBAL:  全局配置默认为自增主键,全局主键配置（参考） |

##### @Column

- 描述：字段注解(非主键)

| 属性         | 类型    | 默认值 | 是否必填 | 描述                                                  |
| ------------ | ------- | ------ | -------- | ----------------------------------------------------- |
| value        | String  | ""     | 否       | 数据库字段名                                          |
| selectIgnore | boolean | false  | 否       | 查询是否查询该字段：<br>false: 查询 <br>true: 不查询  |
| updateIgnore | boolean | false  | 否       | 更新时是否跟心该字段<br> false: 更新<br> true: 不更新 |
| insertIgnore | boolean | false  | 否       | 插入时是否插入该字段<br> false: 插入<br> true: 不插入 |

#### CRUD注解

##### @SelectSql

- **描述：** 构建一个查询语句

| 属性       | 类型    | 默认值 | 是否必填 | 描述                                         |
| ---------- | ------- | ------ | -------- | -------------------------------------------- |
| value      | String  | ""     | 否       | 查询的数据库列字段，不填写会根据实体描述生成 |
| dynamic    | boolean | false  | 否       | 查询参数时是否开启动态查询。                 |
| databaseId | String  | false  | 否       | 预留属性                                     |

- **案例：**
  - **在UserMapper中添加查询方法**

  ```java
  @Mapper
  public interface UserMapper extends BaseMapper<User, String> {
   
      @SelectSql
      List<User> findBy(String name, String orgCode);
  
      @SelectSql(dynamic = true)
      List<User> findByDynamic(String name, String orgCode);
  
      @SelectSql(" id, org_name")
      List<User> findByColumn(String name, String orgCode);@SelectSql
  }
  ```

  - **创建单元测试**

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest(classes = Application.class)
  public class SelectSqlTest {
  
      @Autowired
      private UserMapper userMapper;
  
      @Test
      public void select() {
          System.out.println(">>>>>>>>>>>>>>    findBy      <<<<<<<<<<<<<<<<<<<<<");
          List<User> userList = userMapper.findBy("曹操", "200");
          System.out.println(">>>>>>>>>>>>>>    findByDynamic      <<<<<<<<<<<<<<<<<<<<<");
          List<User> userListDynamic = userMapper.findByDynamic(null, "200");
          System.out.println(">>>>>>>>>>>>>>    findByColumn      <<<<<<<<<<<<<<<<<<<<<");
          List<User> userListColumn = userMapper.findByColumn("曹操", "200");
      }
  }
  ```

  - **在控制台中easybatis 自动构建的SQL语句**

  ```xml
  UserMapper.findBy 	   <script> SELECT `id`, `org_code`, `org_name`, `name` FROM t_user <where> `name` = #{name} AND `org_code` = #{orgCode} </where></script>
  ```

  ```
  UserMapper.findByDynamic 	   <script> SELECT `id`, `org_code`, `org_name`, `name` FROM t_user <where> <if test='name != null'> AND `name` = #{name} </if> <if test='orgCode != null'> AND `org_code` = #{orgCode} </if> </where></script>
  ```

  ```
  UserMapper.findByColumn 	   <script> SELECT  id, org_name FROM t_user <where> `name` = #{name} AND `org_code` = #{orgCode} </where></script>
  ```

  - mybatis 框架执行日志

  ```sql
  >>>>>>>>>>>>>>    findBy      <<<<<<<<<<<<<<<<<<<<<
  Creating a new SqlSession
  SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3c3820bb] was not registered for synchronization because synchronization is not active
  2021-04-14 14:11:19.015  INFO 18868 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@63a28987] will not be managed by Spring
  ==>  Preparing: SELECT `id`, `org_code`, `org_name`, `name` FROM t_user WHERE `name` = ? AND `org_code` = ?
  ==> Parameters: 曹操(String), 200(String)
  <==    Columns: id, org_code, org_name, name
  <==        Row: 37bd0225cc94400db744aac8dee8a001, 200, 总公司, 曹操
  <==      Total: 1
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3c3820bb]
  
  >>>>>>>>>>>>>>    findByDynamic      <<<<<<<<<<<<<<<<<<<<<
  Creating a new SqlSession
  SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3a8d467e] was not registered for synchronization because synchronization is not active
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@63a28987] will not be managed by Spring
  ==>  Preparing: SELECT `id`, `org_code`, `org_name`, `name` FROM t_user WHERE `org_code` = ?
  ==> Parameters: 200(String)
  <==    Columns: id, org_code, org_name, name
  <==        Row: 37bd0225cc94400db744aac8dee8a001, 200, 总公司, 曹操
  <==        Row: 37bd0225cc94400db744aac8dee8a002, 200, 总公司, 小乔
  <==        Row: 37bd0225cc94400db744aac8dee8a003, 200, 总公司, 曹植
  <==        Row: 37bd0225cc94400db744aac8dee8a004, 200, 总公司, 吕布
  <==        Row: 37bd0225cc94400db744aac8dee8a005, 200, 总公司, 荀攸
  <==      Total: 5
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3a8d467e]
  
  >>>>>>>>>>>>>>    findByColumn      <<<<<<<<<<<<<<<<<<<<<
  Creating a new SqlSession
  SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@41ad373] was not registered for synchronization because synchronization is not active
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@63a28987] will not be managed by Spring
  ==>  Preparing: SELECT id, org_name FROM t_user WHERE `name` = ? AND `org_code` = ?
  ==> Parameters: 曹操(String), 200(String)
  <==    Columns: id, org_name
  <==        Row: 37bd0225cc94400db744aac8dee8a001, 总公司
  <==      Total: 1
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@41ad373]
  2021-04-14 14:11:19.330  INFO 18868 --- [extShutdownHook] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
  2021-04-14 14:11:19.340  INFO 18868 --- [extShutdownHook] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} closed
  ```

  - **总结**

  ```
  1. @SelectSql 是帮我构建查询语句的注解,他会根据方法的参数和注解来构建SQL
  2. 当开启了dynamic = true 方法的所有参数会变为动态查询条件
  3. 我们可以通过value 来指定我们想要的查询列
  ```

##### @UpdateSql

- 描述：
  构建一个更新语句

| 属性       | 类型    | 默认值 | 是否必填 | 描述               |
| ---------- | ------- | ------ | -------- | ------------------ |
| dynamic    | boolean | false  | 否       | 是否开启动态更新。 |
| databaseId | String  | false  | 否       | 预留属性           |

- 案例

  - **在UserMapper中添加更新方法**

    ```java
    @Mapper
    public interface UserMapper extends BaseMapper<User, String> {
        @UpdateSql
        Integer updateCode(@SetParam String orgCode);
    
        @UpdateSql
        void updateCodeByName(@SetParam String orgCode, String name);
    }
    ```
    
  - **创建单元测试**
  
    ```java
    @RunWith(SpringRunner.class)
    @SpringBootTest(classes = Application.class)
    public class UpdateSqlTest {
    
        @Autowired
        private UserMapper userMapper;
    
        @Test
        public void update() {
            System.out.println("\n>>>>>>>>>>>>>>>>>>   update    <<<<<<<<<<<<<<<<<<<<");
            User user = userMapper.selectKey("37bd0225cc94400db744aac8dee8a004");
            userMapper.update(user);
            System.out.println("\n>>>>>>>>>>>>>>>>>>   updateActivate   <<<<<<<<<<<<<<<<<<<<");
            user.setName("吕布1");
            User activateUser = new User();
            activateUser.setId("\n37bd0225cc94400db744aac8dee8a004");
            activateUser.setName("吕布");
            userMapper.updateActivate(activateUser);
            System.out.println("\n>>>>>>>>>>>>>>>>>>    updateCode   <<<<<<<<<<<<<<<<<<<<");
            userMapper.updateCode("200");
            System.out.println("\n>>>>>>>>>>>>>>>>>>    updateCodeByName   <<<<<<<<<<<<<<<<<<<<");
            userMapper.updateCodeByName("200", "曹操");
        }
    }
    ```
  
    
  
  - **在控制台中easybatis 自动构建的SQL语句**
  
    ```xml
    UserMapper.update 	   <script> UPDATE t_user SET `org_code` = #{orgCode}, `org_name` = #{orgName}, `name` = #{name} <where> `id` = #{id} </where></script>
    ```
  
    ```xml
    UserMapper.updateActivate 	   <script> UPDATE t_user <set> <if test='orgCode != null'> `org_code` = #{orgCode},</if> <if test='orgName != null'> `org_name` = #{orgName},</if> <if test='name != null'> `name` = #{name},</if> </set> <where> `id` = #{id} </where></script>
    ```
  
    ```xml
    UserMapper.updateCode 	   <script> UPDATE t_user SET `org_code` = #{orgCode}</script>
    ```
  
    ```xml
    UserMapper.updateCodeByName 	   <script> UPDATE t_user SET `org_code` = #{orgCode} <where> `name` = #{name} </where></script>
    ```
  
  - mybatis 框架执行日志
  
    ```sql
    >>>>>>>>>>>>>>>>>>   update    <<<<<<<<<<<<<<<<<<<<
    JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3751acd7] will not be managed by Spring
    ==>  Preparing: UPDATE t_user SET `org_code` = ?, `org_name` = ?, `name` = ? WHERE `id` = ?
    ==> Parameters: 200(String), 总公司(String), 吕布(String), 37bd0225cc94400db744aac8dee8a004(String)
    <==    Updates: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3719360c]
    
    >>>>>>>>>>>>>>>>>>   updateActivate   <<<<<<<<<<<<<<<<<<<<
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3855d9b2] was not registered for synchronization because synchronization is not active
    JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3751acd7] will not be managed by Spring
    ==>  Preparing: UPDATE t_user SET `name` = ? WHERE `id` = ?
    ==> Parameters: 吕布(String), 
    37bd0225cc94400db744aac8dee8a004(String)
    <==    Updates: 0
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3855d9b2]
    
    >>>>>>>>>>>>>>>>>>    updateCode   <<<<<<<<<<<<<<<<<<<<
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@730bea0] was not registered for synchronization because synchronization is not active
    JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3751acd7] will not be managed by Spring
    ==>  Preparing: UPDATE t_user SET `org_code` = ?
    ==> Parameters: 200(String)
    <==    Updates: 34
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@730bea0]
    
    >>>>>>>>>>>>>>>>>>    updateCodeByName   <<<<<<<<<<<<<<<<<<<<
    Creating a new SqlSession
    SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@677cb96e] was not registered for synchronization because synchronization is not active
    JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@3751acd7] will not be managed by Spring
    ==>  Preparing: UPDATE t_user SET `org_code` = ? WHERE `name` = ?
    ==> Parameters: 200(String), 曹操(String)
    <==    Updates: 1
    Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@677cb96e]
    ```
  
- Tips

  ```
  1. @UpdateSql 是帮助我们构建更新语句的
  2. 需要更新指定列的时候可以使用@SetParam, 没有添加该注解的都会以条件的形式参入到更新语句中。
  ```

  

##### @InsertSql

- 描述：

  构建一个插入语句

  | 属性       | 类型    | 默认值 | 是否必填 | 描述               |
  | ---------- | ------- | ------ | -------- | ------------------ |
  | databaseId | String  | false  | 否       | 预留属性           |

- 案例

   可以直接查看`BaseMapper` 案例中的`Insert单条写入` 和 `insertBatch 批量写入`  案例 easybatis 只支持了这两种写入情况。
   
- Tips

   ```
   1. @InsertSql 是帮助我们构建插入语句的注解。
   2. @InsertSql 注解只支持接口 E 泛型对象
   3. @InsertSql 可以支持集合，但是集合的泛型必须是 E
   ```

   

##### @DeleteSql
- 描述：

  构建一个删除语句

  | 属性       | 类型    | 默认值 | 是否必填 | 描述               |
  | ---------- | ------- | ------ | -------- | ------------------ |
  | databaseId | String  | false  | 否       | 预留属性           |

- 案例

  - **在UserMapper中添加删除方法**

  ```java
  @Mapper
  public interface UserMapper extends BaseMapper<User, String> {
      @DeleteSql
      Integer deleteByOrgName(String orgName);
  }
  ```

  - 编写单元测试

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest(classes = Application.class)
  public class DeleteSqlTest {
  
      @Autowired
      private UserMapper userMapper;
  
      @Test
      public void del() {
          System.out.println("\n>>>>>>>>>>>>>>>>>>   delete    <<<<<<<<<<<<<<<<<<<<");
          userMapper.delete("1234567889");
  
          System.out.println("\n>>>>>>>>>>>>>>>>>>   deleteByOrgName    <<<<<<<<<<<<<<<<<<<<");
          userMapper.deleteByOrgName("test");
      }
  }
  ```

  - **在控制台中easybatis 自动构建的SQL语句**

  ```xml
  UserMapper.deleteByOrgName 	   <script> DELETE FROM t_user <where> `org_name` = #{orgName} </where></script>
  ```

  ```xml
  UserMapper.deleteByOrgName 	   <script> DELETE FROM t_user <where> `org_name` = #{orgName} </where></script>
  ```

  - mybatis 框架执行日志

  ```sql
  >>>>>>>>>>>>>>>>>>   delete    <<<<<<<<<<<<<<<<<<<<
  Creating a new SqlSession
  SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@43a65cd8] was not registered for synchronization because synchronization is not active
  2021-04-14 17:54:05.039  INFO 18464 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@39909d1a] will not be managed by Spring
  ==>  Preparing: DELETE FROM t_user WHERE `id` = ?
  ==> Parameters: 1234567889(String)
  <==    Updates: 0
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@43a65cd8]
  
  >>>>>>>>>>>>>>>>>>   deleteByOrgName    <<<<<<<<<<<<<<<<<<<<
  Creating a new SqlSession
  SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@52d6d273] was not registered for synchronization because synchronization is not active
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@39909d1a] will not be managed by Spring
  ==>  Preparing: DELETE FROM t_user WHERE `org_name` = ?
  ==> Parameters: test(String)
  <==    Updates: 0
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@52d6d273]
  ```

- Tips


```
1. @DeleteSql 帮助我们构建一个删除语句
2. @DeteteSql 可以自定义删除条件。
```

#### 查询注解

​	这种注解主要应用在`@SelectSql`的查询方法上，是用来指定查询条件的，当方法的参数和查询对象中的属性没有查询注解时，默认为 `@Equal`  查询。

##### 案例演示

```java
// 1. 需要查询name字段和 orgCode字段 下面两个查询是等价的
@SelectSql
List<User> findBy(String name, String orgCode);
@SelectSql
List<User> findByCustom(@Equal("name") String customName,@Equal String orgCode);
    
//2. 一个查询可能需要name字段或者orgCode字段时可以这样写  下面两种动态查询也是一样的
@SelectSql(dynamic = true)
List<User> findByDynamic(String name, String orgCode);
@SelectSql
List<User> findByParamDynamic(@Equal(dynamic = true) String name, @Equal(dynamic = true)String orgCode);

//3. 如果一个查询有必传参数和非必传参数我们就可以这样写
@SelectSql
List<User> findByParamDynamic(String name, @Equal(dynamic = true)String orgCode);

//4. 在一个查询中有多种类型的查询条件，我们可以这样写
@SelectSql
List<User> findByMultiCondition(@Like(dynamic = true) String orgCode, @In List<String> id, String name);

// 5. 如果我们想进行排序 可以这样写.
@SelectSql
List<User> orderUser(@ASC Boolean id);

// 6. 如果我们想带条件的查询排序 可以这样写
@SelectSql
List<User> orderByCUser(String orgCode, @ASC Boolean id);

// 7. 如果我们想 动态的控制 id 排序条件可以这样写, 只有当id有值的时候才会参与排序
@SelectSql
List<User> orderByUser(String orgCode, @ASC(dynamic = true) Boolean id, @DESC Boolean name);

// 8. 我们想统计符合条件的记录可以这样写
@SelectSql
@Count
Integer count(String name, String orgCode);

// 9. 如果我们想去重 org_name 数据可以这样写
@SelectSql(" org_name ")
@Distinct
List<String> distinct(String name, String orgCode);

// 10. 如果我们想去重后统计数据
@SelectSql(" org_name ")
@Distinct
@Count
List<String> distinct(String name, String orgCode);

// 11. 如果我们想做分页查询
@SelectSql
List<User> limit(String name, String orgCode, @Start Integer start, @Offset Integer offset);

// 12.如果我们觉得 查询参数太多了 我们可以定义一个对象，在对象中去定义这些查询。
public class UserFilter {
    @RightLike
    private String orgCode;

    private String name;
    @ASC
    private Integer id;
    @Start
    private Integer start;
    @Offset
    private Integer offset;
}
@SelectSql
List<User> limit(UserFilter filter);
	
```

 上面我们做了一写基本演示，你也应该体会到了如何去使用我们的easybatis了吧，后面我们详细描述这些注解的是如何使用的。



##### @Equal

**描述：**等于查询 等价于`SQL`中的  `column = ?`

| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |



##### @NotEqual

描述：不等于查询 等价于`SQL`中的  `column != ?`

| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |



##### @ IsNull

**描述：**空查询  等价于`SQL`中的  `column IS NULL`


| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |



##### **@ IsNotNull** 

**描述：**查询非空 等价于`SQL`中的  `column IS NOT NULL`


| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |



##### @ In

**描述：** IN 查询 等价于`SQL`中的  `column IN (?, ?, ?)`


| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |


##### @ NotIn

**描述：** NOT IN 查询 等价于`SQL`中的  `column NOT IN (?, ?, ?)`


| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |


##### @ Like

**描述：** 全模糊查询 等价于`SQL`中的  `column LIKE '%?%'`


| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |


##### @ RightLike 

**描述:**  右模糊匹配查询 等价于`SQL`中的  `column LIKE '?%'`


| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |


##### @ LeftLike

**描述：**左模糊匹配查询 等价于`SQL`中的  `column LIKE '%?'`


| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |

##### @ NotLike

**描述：**查询不符合全模糊匹配条件 等价于`SQL`中的  `column NOT LIKE '%?%'`


| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |

##### @ NotRightLike 

**描述：** 查询不符合右模糊匹配条件 等价于`SQL`中的  `column NOT LIKE '?%'`


| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |

##### @ NotLeftLike

**描述：**查询不符合做模糊匹配条件 等价于SQL中的  `column NOT LIKE '%?'`


| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |

##### @Start  @Offset 分页注解

**描述：** 用来对查询结果分页的参数  等价于SQL中的  `LIMIT start, offset`

##### @ASC @DESC 排序注解

- @ASC

**描述：** 用来对查询结果分页的参数  等价于SQL中的  `ORDER BY column ASC`

| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |

- @DESC

**描述：** 用来对查询结果分页的参数  等价于SQL中的  `ORDER BY column DESC`

| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |
| alias   | String  | ""     | 否       | 条件别名，预留字段                                           |
| dynamic | boolean | false  | 否       | 查询条件是否开启动态查询，默认不开启。<br/>当参数为动态查询时，查询参数有值时才会参与查询否则不参与查询。 |

- Tips：

```
@ASC 和 @DESC 可以在一个方法中一起使用
```

####  更新注解

#####  案例演示

```java
// 1.如果我们想根据非主键外的条件更新数据(想根据组织编码批量更新组织名字)
	@UpdateSql
	Integer updateParam(String orgCode, @SetParam String orgName);
```

##### @SetParam

| 属性    | 类型    | 默认值 | 是否必填 | 描述                                                         |
| ------- | ------- | ------ | -------- | ------------------------------------------------------------ |
| value   | String  | ""     | 否       | 查询的数据库列，默认为属性名                                 |

#### 逻辑删除功能

##### @Logic

- 描述： 表字段逻辑处理注解（逻辑删除）

| 属性         | 类型    | 默认值 | 是否必填 | 描述                         |
| ------------ | ------- | ------ | -------- | ---------------------------- |
| value        | String  | ""     | 否       | 查询的数据库列，默认为属性名 |
| valid        | int     | -      | 是       | 有效值                       |
| invalid      | int     | -      | 是       | 无效值                       |
| selectIgnore | boolean | true   | 否       | 查询是否忽略该属性           |

##### 案例演示

- 创建实体

  ```java
  
  @Data
  @Table("t_user") // 我们继承快速开始中的User对象
  @ToString(callSuper = true)
  public class LogicUser extends User {
  
      @Logic(valid = 1, invalid = 0)
      private int valid;
  }
  ```

- 创建Mapper接口

  ```java
  @Mapper
  public interface LogicUserMapper extends BaseMapper<LogicUser, String> {
  }
  
  ```

- 逻辑查询功能演示

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest(classes = Application.class)
  public class LogicTest {
  
      @Autowired
      private LogicUserMapper logicUserMapper;
  
      @Test
      public void selectKey() {
          logicUserMapper.selectKey("37bd0225cc94400db744aac8dee8a004");
      }
  }
  ```

  我们可以通过日志看见 easybatis 在构建sql的时候帮我们自动加入了逻辑删除的过滤条件

  ```xml
  LogicUserMapper.selectKey 	   <script> SELECT `id`, `org_code`, `org_name`, `name` FROM t_user <where> `id` = #{id} AND `valid` = #{valid} </where></script>
  ```

  mybatis 执行日志

  ```sql
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@e146f93] will not be managed by Spring
  ==>  Preparing: SELECT `id`, `org_code`, `org_name`, `name` FROM t_user WHERE `id` = ? AND `valid` = ?
  ==> Parameters: 37bd0225cc94400db744aac8dee8a004(String), 1(Integer)
  <==    Columns: id, org_code, org_name, name
  <==        Row: 37bd0225cc94400db744aac8dee8a004, 200, 总公司, 吕布
  <==      Total: 1
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5d97caa4]
  ```

- insert 逻辑新增功能演示

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest(classes = Application.class)
  public class LogicTest {
  
      @Autowired
      private LogicUserMapper logicUserMapper;
  
      @Test
      public void insert() {
          LogicUser user = new LogicUser();
          user.setId("123456789");
          user.setName("这是一个测试");
          user.setOrgName("测试组");
          user.setOrgCode("test");
          logicUserMapper.insert(user);
          sout
      }
  }
  ```

  通过日志看见 easybatis 在构建insert 语句

  ```xml
  LogicUserMapper.insert 	   <script> INSERT INTO t_user (`id`, `org_code`, `org_name`, `name`, `valid`) VALUES (#{id}, #{orgCode}, #{orgName}, #{name}, #{valid}) </script>
  ```

  查看 mybatis 执行日志的时候 我们发现  valid属性被自动被维护了

  ```sql
  2021-04-21 14:23:49.516  INFO 13640 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@29fd8e67] will not be managed by Spring
  ==>  Preparing: INSERT INTO t_user (`id`, `org_code`, `org_name`, `name`, `valid`) VALUES (?, ?, ?, ?, ?)
  ==> Parameters: 123456781(String), test(String), 测试组(String), 这是一个测试(String), 1(Integer)
  <==    Updates: 1
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@47d023b7]
  LogicUser(super=User(id=123456781, orgCode=test, orgName=测试组, name=这是一个测试), valid=1)
  ```

- update 逻辑更新功能演示

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest(classes = Application.class)
  public class LogicTest {
  
      @Autowired
      private LogicUserMapper logicUserMapper;
  
      @Test
      public void update() {
          LogicUser user = logicUserMapper.selectKey("123456781");
          user.setName("这是一个测试更新");
          logicUserMapper.update(user);
      }
  
  }
  ```

  通过日志看见 easybatis 构建update语句

  ```xml
  LogicUserMapper.update 	   <script> UPDATE t_user SET `org_code` = #{orgCode}, `org_name` = #{orgName}, `name` = #{name} <where> `id` = #{id} AND `valid` = #{valid} </where></script>
  ```

  查看 mybatis 执行日志 发现更新语句没有维护 valid 属性了 而是在过滤条件用到了valid 属性

  ```sql
  Creating a new SqlSession
  SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5ceecfee] was not registered for synchronization because synchronization is not active
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@526b2f3e] will not be managed by Spring
  ==>  Preparing: UPDATE t_user SET `org_code` = ?, `org_name` = ?, `name` = ? WHERE `id` = ? AND `valid` = ?
  ==> Parameters: test(String), 测试组(String), 这是一个测试更新(String), 123456781(String), 1(Integer)
  <==    Updates: 1
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5ceecfee]
  ```

- delete逻辑删除功能演示

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest(classes = Application.class)
  public class LogicTest {
  
      @Autowired
      private LogicUserMapper logicUserMapper;
  
      @Test
      public void del(){
          logicUserMapper.delete("123456781");
      }
  }
  ```

  通过日志看见 easybatis 构建的delete语是一个update 语句 来修改逻辑删除的属性。

  ```xml
  LogicUserMapper.delete 	   <script> UPDATE t_user SET valid = #{invalid} <where> `id` = #{id} AND `valid` = #{valid} </where></script>
  ```

  查看 mybatis 执行日志

  ```java
  Creating a new SqlSession
  SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5d97caa4] was not registered for synchronization because synchronization is not active
  2021-04-21 14:43:13.051  INFO 12868 --- [           main] com.alibaba.druid.pool.DruidDataSource   : {dataSource-1} inited
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@6ed922e1] will not be managed by Spring
  ==>  Preparing: UPDATE t_user SET valid = ? WHERE `id` = ? AND `valid` = ?
  ==> Parameters: 0(Integer), 123456781(String), 1(Integer)
  <==    Updates: 1
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5d97caa4]
  ```

#### 审计功能

​		审计功能会帮我们去维护一些固定场景需要的字段。审计功能就是帮我们可以自动维护 创建数据的用户id、名字、时间 及修改数据的用户Id、名字、时间，但是需要你告诉easybatis框架如何获取这些数据，必须实现`AuditorContext` 接口当easybatis 需要数据时，他会主动调用这个接口实现。

​		审计功能就是帮助我们自动维护了一些信息，减少我们部分的开发工作量，他主要是通过mybatis的插件机制来完成的  可以参考 `ExecutorHandlerInterceptor`。

##### @CreateId

- 描述: 自动维护被标记了@CreateId的属性；

| 属性  | 类型         | 默认值 | 是否必填 | 描述                                 |
| ----- | ------------ | ------ | -------- | ------------------------------------ |
| value | String       | ""     | 否       | 查询的数据库列，默认为属性名         |
| alias | selectIgnore | true   | 否       | 查询是否忽略，默认是忽略该字段查询的 |


##### @CreateName

- 描述: 自动维护被标记了@CreateName的属性；

| 属性  | 类型         | 默认值 | 是否必填 | 描述                                 |
| ----- | ------------ | ------ | -------- | ------------------------------------ |
| value | String       | ""     | 否       | 查询的数据库列，默认为属性名         |
| alias | selectIgnore | true   | 否       | 查询是否忽略，默认是忽略该字段查询的 |

##### @CreateTime

- 描述: 自动维护被标记了@CreateTime的属性；

| 属性  | 类型         | 默认值 | 是否必填 | 描述                                 |
| ----- | ------------ | ------ | -------- | ------------------------------------ |
| value | String       | ""     | 否       | 查询的数据库列，默认为属性名         |
| alias | selectIgnore | true   | 否       | 查询是否忽略，默认是忽略该字段查询的 |

##### @UpdateId

- 描述: 自动维护被标记了@UpdateId的属性；

| 属性  | 类型         | 默认值 | 是否必填 | 描述                                 |
| ----- | ------------ | ------ | -------- | ------------------------------------ |
| value | String       | ""     | 否       | 查询的数据库列，默认为属性名         |
| alias | selectIgnore | true   | 否       | 查询是否忽略，默认是忽略该字段查询的 |

##### @UpdateName

- 描述: 自动维护被标记了@UpdateName的属性；

| 属性  | 类型         | 默认值 | 是否必填 | 描述                                 |
| ----- | ------------ | ------ | -------- | ------------------------------------ |
| value | String       | ""     | 否       | 查询的数据库列，默认为属性名         |
| alias | selectIgnore | true   | 否       | 查询是否忽略，默认是忽略该字段查询的 |

##### @UpdateTime

- 描述： 自动维护被标记了@UpdateTime的属性；

| 属性  | 类型         | 默认值 | 是否必填 | 描述                                 |
| ----- | ------------ | ------ | -------- | ------------------------------------ |
| value | String       | ""     | 否       | 查询的数据库列，默认为属性名         |
| alias | selectIgnore | true   | 否       | 查询是否忽略，默认是忽略该字段查询的 |

##### 案例演示：

- 创建实体类

  ```java
  @Data
  @Table("t_user")
  @ToString(callSuper = true)
  public class AuditorUser extends LogicUser{
  
      @CreateTime
      private Date createTime; //创建时间
  
      @CreateId
      private String createId; //创建用户id
  
      @CreateName
      private String createName; // 创建用户名
  
      @UpdateTime //创建时间
      private Date updateTime;
  
      @UpdateId //更新用户id
      private String updateId;
  
      @UpdateName //更新用户名
      private String updateName;
  }
  ```

- 创建Mapper 对象

  ```java
  @Mapper
  public interface LogicUserMapper extends BaseMapper<AuditorUser, String> {
  }
  ```

- 实现AuditorContext 接口 并交给spring管理

  ```java
  public class DefaultAuditorContext implements AuditorContext {
      @Override
      public Object id() {
          return "easyId";
      }
  
      @Override
      public Object name() {
          return "easy";
      }
  
      @Override
      public Object time() {
          return new Date();
      }
  	// 当你使用缓存对象时，在easybatis对属性增强完毕 会调用该方法，做结束的清理工作。
      @Override
      public void clear() {
      }
  }
  ```

- insert审计功能演示

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest(classes = Application.class)
  public class AuditorTest {
      @Autowired
      private AuditorUserMapper auditorUserMapper;
      @Test
      public void insert() {
          AuditorUser user = new AuditorUser();
          user.setId("123456789");
          user.setName("这是一个测试");
          user.setOrgName("测试组");
          user.setOrgCode("test");
          auditorUserMapper.insert(user);
      }
  }
  ```
  

通过日志看见 easybatis 在构建sql的时候帮我们自动加入了新增时审计相关的字段

  ```xml
  AuditorUserMapper.insert 	   <script> INSERT INTO t_user (`id`, `org_code`, `org_name`, `name`, `create_time`, `update_time`, `create_name`, `update_name`, `create_id`, `update_id`, `valid`) VALUES (#{id}, #{orgCode}, #{orgName}, #{name}, #{createTime}, #{updateTime}, #{createName}, #{updateName}, #{createId}, #{updateId}, #{valid}) </script>
  ```

  通过mybatis 执行日志我们可以看见，审计功能会自动维护相关的审计数据。

  ```sql
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@1455154c] will not be managed by Spring
  ==>  Preparing: INSERT INTO t_user (`id`, `org_code`, `org_name`, `name`, `create_time`, `update_time`, `create_name`, `update_name`, `create_id`, `update_id`, `valid`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
  ==> Parameters: 123456790(String), test(String), 测试组(String), 这是一个测试(String), 2021-04-25 16:17:54.757(Timestamp), 2021-04-25 16:17:54.757(Timestamp), easy(String), easy(String), easyId(String), easyId(String), 1(Integer)
  <==    Updates: 1
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6e6d4780]
  ```

- update 审计功能演示

  创建单元测试

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest(classes = Application.class)
  public class AuditorTest {
      @Autowired
      private AuditorUserMapper auditorUserMapper;
      @Test
      public void update(){
          AuditorUser auditorUser = auditorUserMapper.selectKey("123456790");
          auditorUser.setName("逻辑更新名字");
          auditorUserMapper.update(auditorUser);
      }
  }
  ```

  通过日志看见 easybatis 在构建sql的时候帮我们自动加入了修改时审计相关的字段

  ```
  AuditorUserMapper.update 	   <script> UPDATE t_user SET `org_code` = #{orgCode}, `org_name` = #{orgName}, `name` = #{name}, `update_time` = #{updateTime}, `update_name` = #{updateName}, `update_id` = #{updateId} <where> `id` = #{id} AND `valid` = #{valid} </where></script>
  ```

  过mybatis 执行日志我们可以看见，审计功能会自动维护相关的审计数据。

  ```sql
  SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6aa3bfc] was not registered for synchronization because synchronization is not active
  JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@788ba63e] will not be managed by Spring
  ==>  Preparing: UPDATE t_user SET `org_code` = ?, `org_name` = ?, `name` = ?, `update_time` = ?, `update_name` = ?, `update_id` = ? WHERE `id` = ? AND `valid` = ?
  ==> Parameters: test(String), 测试组(String), 逻辑更新名字(String), 2021-04-25 16:43:00.769(Timestamp), easy(String), easyId(String), 123456790(String), 1(Integer)
  <==    Updates: 1
  Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6aa3bfc]
  ```

  

#### 枚举处理



#### JOIN 查询 (规划中)








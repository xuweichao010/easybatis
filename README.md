# Easybatis 工具介绍
    Easybatis是一款基于JavaBean来快速生成增删改插的工具。它对原生mybatis任何入侵
## 1.0. 快速开始

1. 定义机构的JavaBean
```java
@Table("t_org")
@Data
public class Org  {
    @Id(type = IdType.CUSTOM)
    private String code;
    private String name;
    private String parentCode;
    private String parentName;
    private String address;
    private Integer employeesNum;
}

```

2. 声明Mapper

   BaseMapper接口是EasyBatis内置用于提供增删改插的接口。

```java
@Mapper
public interface OrgMapper extends BaseMapper<Org, String> {
    
}
```

3. 配置单元测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestCRUD {
    
}

@SpringBootApplication
@EnableEasyBatis
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

```



3. 根据主键查询

```java
@Test
public void testPrimaryKeySelect() {
    Org org = orgMapper.selectKey("200");
    System.out.println("EasyBatis查询结果: " + org.toString());
    Assert.assertNotEquals(org,null);
}
/**  SQL 
==>  Preparing: SELECT code, name, parent_code, parent_name, address, employees_num FROM t_org WHERE code = ? 
==> Parameters: 200(String)
<==    Columns: code, name, parent_code, parent_name, address, employees_num
<==        Row: 200, 2234234, null, EasyBatis, 中国区域, 100
<==      Total: 1
*/
```

4. 根据主键插入

```java
@Test
public void testInsertCutomeId() {
    Org org = new Org();
    org.setCode("200009");
    org.setName("武汉二公司");
    org.setParentName("总公司");
    org.setParentCode("200");
    org.setAddress("武汉江汉区");
    Long count = orgMapper.insert(org);
    /** SQL
    ==>  Preparing: INSERT INTO t_org ( code, name, parent_code, parent_name, address, employees_num) VALUES ( ?, ?, ?, ?, ?, ?) 
	==> Parameters: 200009(String), 武汉二公司(String), 200(String), 总公司(String), 武汉江汉区(String), null
	<==    Updates: 1
    */
}
```

5. 根据主键删除

```java
@Test
public void testDeleteByPrimaryKey() {
    Long delCount = orgMapper.delete("200009");
    /** SQL
    ==>  Preparing: DELETE FROM t_org WHERE code = ? 
	==> Parameters: 200009(String)
	<==    Updates: 1
    */
}
```

6. 根据主键修改

```java
@Test
public void testUpdateByPrimaryKey() {
    Org org = new Org();
    org.setCode("200009");
    org.setName("武汉二公司");
    org.setParentName("总公司");
    org.setParentCode("200");
    org.setAddress("武汉江汉区");
    orgMapper.insert(org);
    org.setName("武汉二公司修该");
    Long count = orgMapper.update(org);
    /**
    ==>  Preparing: UPDATE t_org SET code=?, name=?, parent_code=?, parent_name=?, address=?, employees_num=? WHERE code = ? 
	==> Parameters: 200009(String), 武汉二公司修该(String), 200(String), 总公司(String), 武汉江汉区(String), null, 200009(String)
	<==    Updates: 1
    */
}
```

7. 批量写入

```java
@Test
public void testInsertBatchCutomeId() {
    ArrayList<Org> list = new ArrayList<>();
    Org org = new Org();
    org.setCode("200006");
    org.setName("武汉三公司");
    org.setParentName("总公司");
    org.setParentCode("200");
    org.setAddress("武汉江汉区");
    list.add(org);

    org = new Org();
    org.setCode("200007");
    org.setName("武汉四公司");
    org.setParentName("总公司");
    org.setParentCode("200");
    org.setAddress("武汉江汉区");
    list.add(org);

    org = new Org();
    org.setCode("200008");
    org.setName("武汉五公司");
    org.setParentName("总公司");
    org.setParentCode("200");
    org.setAddress("武汉江汉区");
    list.add(org);
    Long count = orgMapper.insertBatch(list);
    /**
    ==>  Preparing: INSERT INTO t_org ( code, name, parent_code, parent_name, address, employees_num) VALUES ( ?, ?, ?, ?, ?, ?) , ( ?, ?, ?, ?, ?, ?) , ( ?, ?, ?, ?, ?, ?) 
	==> Parameters: 200006(String), 武汉三公司(String), 200(String), 总公司(String), 武汉江汉区(String), null, 200007(String), 武汉四公司(String), 200(String), 总公司(String), 武汉江汉区(String), null, 200008(String), 武汉五公司(String), 200(String), 总公司(String), 武汉江汉区(String), null
	<==    Updates: 3
    */
}
```



## 2.0.  注解介绍

### 2.1. 启用注解

- @EnableEasyBatis

​	Easybatis注解用于开启Easybatis插件自动生成配置功能.

### 2.2. 表注解

- @Table

​	标识JavaBean和数据库表之间的关系

| 参数  | 类型   | 描述                   |
| ----- | ------ | ---------------------- |
| value | String | 指定JavaBean对应的表名 |
| name  | String | 指定JavaBean对应的表名 |

- @Colum

  用于描述JavaBean和属性之间的关系

| 参数  | 类型   | 描述                   |
| ----- | ------ | ---------------------- |
| value | String | 指定JavaBean对应的字段名 |
| name  | String | 指定JavaBean对应的字段名 |

- @Id

​	标识数据库的主键标识用于根据主键的删改查操作

| 参数  | 类型   | 描述                                                         |
| ----- | ------ | ------------------------------------------------------------ |
| colum | String | 指定JavaBean的成员属性和表字段之间的关系(默认:驼峰转下划线)  |
| value | String | 指定JavaBean的成员属性和表字段之间的关系(默认:驼峰转下划线)  |
| type  | IdType | 指定ID的类型 默认：GLOBAL<br>GLOBAL：全局指定ID类型通过配置文件的mybatis.configuration.type来配置<br>AUTO:自动增长<br>UUID:使用UUID主键（系统自动生成）<br>CUSTOM:自定义主键 系统不干涉主键的生成 |

- @Ignore

  忽略JavaBean属性不与数据库字段之间的对应关系
  
- @Loglic

  用于数据的逻辑删除字段标识

| 参数    | 类型 | 描述         |
| ------- | ---- | ------------ |
| valid   | int  | 有效数据标识 |
| invalid | int  | 无效数据标识 |

### 2.3.  审计注解

- @CreateId，CreateName，CreateTime，UpdateId，UpdateName，UpdateTime




















































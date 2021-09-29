package com.xwc.open.easybatis.mysql.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mysql.mybatis.condition.ConditionTableUser;
import com.xwc.open.easybatis.mysql.mybatis.condition.ConditionTableUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.condition.ConditionUser;
import com.xwc.open.easybatis.mysql.mybatis.condition.ConditionUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/31
 * 描述：数据库操作测试
 */
public class ConditionMapperTest {


    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    ConditionTableUserMapper conditionTableUserMapper;
    ConditionUserMapper conditionUserMapper;
    SqlSession sqlSession;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(ConditionTableUserMapper.class);
        easybatisConfiguration.addMapper(ConditionUserMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        conditionTableUserMapper = sqlSession.getMapper(ConditionTableUserMapper.class);
        conditionUserMapper = sqlSession.getMapper(ConditionUserMapper.class);
    }

    @Test
    public void findAll() {
        List<ConditionUser> all = conditionUserMapper.findAll();
        Assert.assertFalse(all.isEmpty());
    }

    @Test
    public void equal() {

        List<ConditionUser> conditionUserList = conditionUserMapper.defaultEqual("曹操");
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.defaultEqual("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.defaultEqualDynamic("曹操");
        conditionTableUserList = conditionTableUserMapper.defaultEqualDynamic("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.defaultEqualDynamic(null);
        conditionTableUserList = conditionTableUserMapper.defaultEqualDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.equalAnnotation("曹操");
        conditionTableUserList = conditionTableUserMapper.equalAnnotation("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.equalAnnotationDynamic("曹操");
        conditionTableUserList = conditionTableUserMapper.equalAnnotationDynamic("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.equalAnnotationDynamic(null);
        conditionTableUserList = conditionTableUserMapper.equalAnnotationDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.equalAnnotationCustomColumn("曹操");
        conditionTableUserList = conditionTableUserMapper.equalAnnotationCustomColumn("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void notEqual() {
        List<ConditionUser> conditionUserList = conditionUserMapper.notEqualAnnotation("曹操");
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.notEqualAnnotation("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList, false);

        conditionUserList = conditionUserMapper.notEqualAnnotationDynamic("曹操");
        conditionTableUserList = conditionTableUserMapper.notEqualAnnotationDynamic("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.notEqualAnnotationDynamic(null);
        conditionTableUserList = conditionTableUserMapper.notEqualAnnotationDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.notEqualAnnotationCustom("曹操");
        conditionTableUserList = conditionTableUserMapper.notEqualAnnotationCustom("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList, false);
    }

    @Test
    public void greaterThan() {
        List<ConditionUser> conditionUserList = conditionUserMapper.greaterThanAnnotation(49);
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.greaterThanAnnotation("t_user", 49);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.greaterThanAnnotationDynamic(49);
        conditionTableUserList = conditionTableUserMapper.greaterThanAnnotationDynamic("t_user", 49);
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.greaterThanAnnotationDynamic(null);
        conditionTableUserList = conditionTableUserMapper.greaterThanAnnotationDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.greaterThanAnnotationCustom(49);
        conditionTableUserList = conditionTableUserMapper.greaterThanAnnotationCustom("t_user", 49);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void lessThan() {
        List<ConditionUser> conditionUserList = conditionUserMapper.lessThanAnnotation(51);
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.lessThanAnnotation("t_user", 51);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.lessThanAnnotationDynamic(51);
        conditionTableUserList = conditionTableUserMapper.lessThanAnnotationDynamic("t_user", 51);
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.lessThanAnnotationDynamic(null);
        conditionTableUserList = conditionTableUserMapper.lessThanAnnotationDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.lessThanAnnotationCustom(51);
        conditionTableUserList = conditionTableUserMapper.lessThanAnnotationCustom("t_user", 51);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void greaterThanEqual() {
        List<ConditionUser> conditionUserList = conditionUserMapper.greaterThanEqualAnnotation(50);
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.greaterThanEqualAnnotation("t_user", 50);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.greaterThanEqualAnnotationDynamic(50);
        conditionTableUserList = conditionTableUserMapper.greaterThanEqualAnnotationDynamic("t_user", 50);
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.greaterThanEqualAnnotationDynamic(null);
        conditionTableUserList = conditionTableUserMapper.greaterThanEqualAnnotationDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.greaterThanEqualAnnotationCustom(50);
        conditionTableUserList = conditionTableUserMapper.greaterThanEqualAnnotationCustom("t_user", 50);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void lessThanEqual() {
        List<ConditionUser> conditionUserList = conditionUserMapper.lessThanEqualAnnotation(50);
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.lessThanEqualAnnotation("t_user", 50);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.lessThanEqualAnnotationDynamic(50);
        conditionTableUserList = conditionTableUserMapper.lessThanEqualAnnotationDynamic("t_user", 50);
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.lessThanEqualAnnotationDynamic(null);
        conditionTableUserList = conditionTableUserMapper.lessThanEqualAnnotationDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.lessThanEqualAnnotationCustom(50);
        conditionTableUserList = conditionTableUserMapper.lessThanEqualAnnotationCustom("t_user", 50);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void isNull() {
        List<ConditionUser> conditionUserList = conditionUserMapper.isNullAnnotation(true);
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.isNullAnnotation("t_user", true);
        validate(conditionUserList, conditionTableUserList, false);

        conditionUserList = conditionUserMapper.isNullAnnotationDynamic(true);
        conditionTableUserList = conditionTableUserMapper.isNullAnnotationDynamic("t_user", true);
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.isNullAnnotationDynamic(null);
        conditionTableUserList = conditionTableUserMapper.isNullAnnotationDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.isNullAnnotationCustom(true);
        conditionTableUserList = conditionTableUserMapper.isNullAnnotationCustom("t_user", true);
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.isNullAnnotationCustom(null);
        conditionTableUserList = conditionTableUserMapper.isNullAnnotationCustom("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void isNotNull() {
        List<ConditionUser> conditionUserList = conditionUserMapper.isNotNullAnnotation(true);
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.isNotNullAnnotation("t_user", true);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.isNotNullAnnotationDynamic(true);
        conditionTableUserList = conditionTableUserMapper.isNotNullAnnotationDynamic("t_user", true);
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.isNotNullAnnotationDynamic(null);
        conditionTableUserList = conditionTableUserMapper.isNotNullAnnotationDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.isNotNullAnnotationCustom(true);
        conditionTableUserList = conditionTableUserMapper.isNotNullAnnotationCustom("t_user", true);
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.isNotNullAnnotationCustom(null);
        conditionTableUserList = conditionTableUserMapper.isNotNullAnnotationCustom("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void like() {
        List<ConditionUser> conditionUserList = conditionUserMapper.like("曹");
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.like("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.likeDynamic("曹");
        conditionTableUserList = conditionTableUserMapper.likeDynamic("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.likeDynamic(null);
        conditionTableUserList = conditionTableUserMapper.likeDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.likeCustom("曹");
        conditionTableUserList = conditionTableUserMapper.likeCustom("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.likeCustom(null);
        conditionTableUserList = conditionTableUserMapper.likeCustom("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void leftLike() {
        List<ConditionUser> conditionUserList = conditionUserMapper.leftLike("操");
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.leftLike("t_user", "操");
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.leftLikeDynamic("操");
        conditionTableUserList = conditionTableUserMapper.leftLikeDynamic("t_user", "操");
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.leftLikeDynamic(null);
        conditionTableUserList = conditionTableUserMapper.leftLikeDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.leftLikeCustom("操");
        conditionTableUserList = conditionTableUserMapper.leftLikeCustom("t_user", "操");
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.leftLikeCustom(null);
        conditionTableUserList = conditionTableUserMapper.leftLikeCustom("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);
    }


    @Test
    public void rightLike() {
        List<ConditionUser> conditionUserList = conditionUserMapper.rightLike("曹");
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.rightLike("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.rightLikeDynamic("曹");
        conditionTableUserList = conditionTableUserMapper.rightLikeDynamic("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.rightLikeDynamic(null);
        conditionTableUserList = conditionTableUserMapper.rightLikeDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.rightLikeCustom("曹");
        conditionTableUserList = conditionTableUserMapper.rightLikeCustom("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.rightLikeCustom(null);
        conditionTableUserList = conditionTableUserMapper.rightLikeCustom("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

    }

    @Test
    public void notLike() {
        List<ConditionUser> conditionUserList = conditionUserMapper.notLike("曹");
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.notLike("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, false);

        conditionUserList = conditionUserMapper.notLikeDynamic("曹");
        conditionTableUserList = conditionTableUserMapper.notLikeDynamic("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.likeDynamic(null);
        conditionTableUserList = conditionTableUserMapper.likeDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.notLikeCustom("曹");
        conditionTableUserList = conditionTableUserMapper.notLikeCustom("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.notLikeCustom(null);
        conditionTableUserList = conditionTableUserMapper.notLikeCustom("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void notLeftLike() {
        List<ConditionUser> conditionUserList = conditionUserMapper.notLeftLike("操");
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.notLeftLike("t_user", "操");
        validate(conditionUserList, conditionTableUserList, false);

        conditionUserList = conditionUserMapper.notLeftLikeDynamic("操");
        conditionTableUserList = conditionTableUserMapper.notLeftLikeDynamic("t_user", "操");
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.notLeftLikeDynamic(null);
        conditionTableUserList = conditionTableUserMapper.notLeftLikeDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.notLeftLikeCustom("操");
        conditionTableUserList = conditionTableUserMapper.notLeftLikeCustom("t_user", "操");
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.notLikeCustom(null);
        conditionTableUserList = conditionTableUserMapper.notLeftLikeCustom("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void notRightLike() {
        List<ConditionUser> conditionUserList = conditionUserMapper.notRightLike("曹");
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.notRightLike("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, false);

        conditionUserList = conditionUserMapper.notRightLikeDynamic("曹");
        conditionTableUserList = conditionTableUserMapper.notRightLikeDynamic("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.notRightLikeDynamic(null);
        conditionTableUserList = conditionTableUserMapper.notRightLikeDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.notRightLikeCustom("曹");
        conditionTableUserList = conditionTableUserMapper.notRightLikeCustom("t_user", "曹");
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.notRightLikeCustom(null);
        conditionTableUserList = conditionTableUserMapper.notRightLikeCustom("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void in() {
        List<String> nameList = Collections.singletonList("曹操");
        List<ConditionUser> conditionUserList = conditionUserMapper.in(nameList);
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.in("t_user", nameList);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.inDynamic(nameList);
        conditionTableUserList = conditionTableUserMapper.inDynamic("t_user", nameList);
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.inDynamic(null);
        conditionTableUserList = conditionTableUserMapper.inDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.inCustom(nameList);
        conditionTableUserList = conditionTableUserMapper.inCustom("t_user", nameList);
        validate(conditionUserList, conditionTableUserList, true);
        conditionUserList = conditionUserMapper.inCustom(null);
        conditionTableUserList = conditionTableUserMapper.inCustom("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);
    }

    @Test
    public void notIn() {
        List<String> nameList = Collections.singletonList("曹操");
        List<ConditionUser> conditionUserList = conditionUserMapper.notIn(nameList);
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.notIn("t_user", nameList);
        validate(conditionUserList, conditionTableUserList, false);

        conditionUserList = conditionUserMapper.notInDynamic(nameList);
        conditionTableUserList = conditionTableUserMapper.notInDynamic("t_user", nameList);
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.inDynamic(null);
        conditionTableUserList = conditionTableUserMapper.inDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);

        conditionUserList = conditionUserMapper.notInCustom(nameList);
        conditionTableUserList = conditionTableUserMapper.notInCustom("t_user", nameList);
        validate(conditionUserList, conditionTableUserList, false);
        conditionUserList = conditionUserMapper.inCustom(null);
        conditionTableUserList = conditionTableUserMapper.inCustom("t_user", null);
        validate(conditionUserList, conditionTableUserList, true);
    }


//    @Test
//    public void greaterThan() {
//        List<ConditionUser> conditionUserList = conditionUserMapper.greaterThanAnnotation(49);
//        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.greaterThanAnnotation("t_user", 49);
//        validate(conditionUserList, conditionTableUserList, true);
//
//        conditionUserList = conditionUserMapper.greaterThanAnnotationDynamic(49);
//        conditionTableUserList = conditionTableUserMapper.greaterThanAnnotationDynamic("t_user", 49);
//        validate(conditionUserList, conditionTableUserList, true);
//        conditionUserList = conditionUserMapper.greaterThanAnnotationDynamic(null);
//        conditionTableUserList = conditionTableUserMapper.greaterThanAnnotationDynamic("t_user", null);
//        validate(conditionUserList, conditionTableUserList, true);
//
//        conditionUserList = conditionUserMapper.greaterThanAnnotationCustom(49);
//        conditionTableUserList = conditionTableUserMapper.greaterThanAnnotationCustom("t_user", 49);
//        validate(conditionUserList, conditionTableUserList, true);
//    }


    private void validate(List<ConditionUser> userList, List<ConditionTableUser> tableUserList, boolean has) {
        if (has) {
            Assert.assertTrue(userList.stream().anyMatch(item -> "曹操".equals(item.getName())));
            Assert.assertTrue(tableUserList.stream().anyMatch(item -> "曹操".equals(item.getName())));
        } else {
            Assert.assertTrue(userList.stream().noneMatch(item -> "曹操".equals(item.getName())));
            Assert.assertTrue(tableUserList.stream().noneMatch(item -> "曹操".equals(item.getName())));
        }
    }
}

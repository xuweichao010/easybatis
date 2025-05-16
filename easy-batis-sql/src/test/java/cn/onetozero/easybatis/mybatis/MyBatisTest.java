package cn.onetozero.easybatis.mybatis;

import cn.onetozero.easybatis.entity.NormalUser;
import cn.onetozero.easybatis.mapper.MyBatisMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/12 17:28
 */
public class MyBatisTest {

    @Test
    public void init() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.getConfiguration().addMapper(MyBatisMapper.class);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            MyBatisMapper mapper = sqlSessionFactory.getConfiguration().getMapper(MyBatisMapper.class, sqlSession);
            List<NormalUser> normalUsers = mapper.listByUser();
            Assert.assertTrue(normalUsers.size() > 0);
        } finally {
            sqlSession.close();
        }
    }
}

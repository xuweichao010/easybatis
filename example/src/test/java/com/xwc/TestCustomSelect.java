package com.xwc;

import com.alibaba.fastjson.JSONObject;
import com.xwc.dao.OrgMapper;
import com.xwc.dao.UserMapper;
import com.xwc.dto.OrgQuery;
import com.xwc.entity.Org;
import com.xwc.esbatis.anno.condition.enhance.Like;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/10  14:48
 * 介绍：我们在业务中做的最多的就是查询操作，那么久来看一下除了主键以外的查询操作吧
 * 功能：
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestCustomSelect {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrgMapper orgMapper;

    //介绍对象查找
    @Test
    public void testQuerySelect() {
        OrgQuery quer = new OrgQuery();
        quer.setName("市场部");
        List<Org> list = orgMapper.list(quer);
        //通过debug日志  我们也看见了执行的sql SELECT code, name, parent_code, parent_name, sons FROM t_org WHERE name = ?
        //所以内部构建的是动态sql,如果查询字段为空，那么该条件不参与查询
        System.out.println(JSONObject.toJSONString(list));
        quer.setCode("201004");
        list = orgMapper.list(quer);
        //SELECT code, name, parent_code, parent_name, sons FROM t_org WHERE code LIKE CONCAT( ?, '%') AND name = ?
        System.out.println(JSONObject.toJSONString(list));
        quer = new OrgQuery();
        ArrayList<Integer> sonsCount = new ArrayList<>();
        sonsCount.add(1);
        sonsCount.add(3);
        quer.setSons(sonsCount);
        list = orgMapper.list(quer);
        //SELECT code, name, parent_code, parent_name, sons FROM t_org WHERE sons IN ( ? , ? )
        System.out.println(JSONObject.toJSONString(list));
        //对象查询就这样了，更多查询注解请查看帮助文档
    }

    // 介绍方法查找的使用
    @Test
    public void testMethodSelect() {

        ArrayList<Integer> sonsCount = new ArrayList<>();
        sonsCount.add(0);
        sonsCount.add(3);
        //参数千万不能为空咯
        List<Org> orgList = orgMapper.listByOrg("技术部", "201004", sonsCount);
        //SELECT code, name, parent_code, parent_name, sons FROM t_org WHERE name = ? AND code LIKE CONCAT( ?, '%') AND sons IN ( ? , ? )
        System.out.println(JSONObject.toJSONString(orgList));
        //然后我就想查询name字段，你返回这么多参数对我们来说是无用的,你可以指定GenerateSelectSql 注解的 colums属性就可以了，那我们就来改变一下吧
        orgList = orgMapper.listNameByOrg("技术部", "201004", sonsCount);
        //SELECT name FROM t_org WHERE name = ? AND code LIKE CONCAT( ?, '%') AND sons IN ( ? , ? )
        System.out.println(JSONObject.toJSONString(orgList));

    }

    @Test //汇总属性使用
    public void testCount() {
        OrgQuery quer = new OrgQuery();
        //我们查看该方法发现就是在对象查询条件上新增加了一个count注解
        Long count = orgMapper.count(quer);
        System.out.println(count);

    }

}

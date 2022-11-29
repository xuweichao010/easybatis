package com.xwc.easy.core;

import com.xwc.easy.core.method.BaseMapper;
import com.xwc.easy.core.method.TestBaseMapper;
import com.xwc.open.easy.parse.DefaultTableMetaAssistant;
import com.xwc.open.easy.parse.DefaultOperateMethodAssistant;
import com.xwc.open.easy.parse.EasyConfiguration;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 类描述：默认的属性类型
 * 作者：徐卫超 (cc)
 * 时间 2022/11/26 21:01
 */
public class DefaultOperateMethodAssistantTest {

    /**
     * 测试获取操作方法的元信息 是否能正常工作
     */
    @Test
    public void getOperateMethodMeta() {
        EasyConfiguration configuration = new EasyConfiguration();
        configuration.setTableMetaAssistant(new DefaultTableMetaAssistant(configuration));
        DefaultOperateMethodAssistant methodAssistant = new DefaultOperateMethodAssistant(configuration);
        OperateMethodMeta insert = methodAssistant.getOperateMethodMeta(TestBaseMapper.class, method(TestBaseMapper.class,
                "insert"));
        Assert.assertNotNull(insert);
    }


    public Method method(Class<?> clazz, String methodName) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        Assert.fail("无法找到对应的方法" + methodName);
        return null;
    }

}

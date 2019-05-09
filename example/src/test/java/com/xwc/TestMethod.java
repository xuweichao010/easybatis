package com.xwc;

import com.xwc.esbatis.anno.condition.Equal;
import com.xwc.esbatis.anno.condition.In;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/29  9:33
 * 业务：
 * 功能：
 */
public class TestMethod {
    //@RequestBody @Equal String name, @In List<String> ids
    public void getpa(String name){
        return;
    }

    public static void main(String[] args) {

        TestMethod testMethod = new TestMethod();
        Method[] methods = testMethod.getClass().getMethods();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        for(Method m:methods){
            String[] parameterNames = u.getParameterNames(m);
            Class<?>[] parameterTypes = m.getParameterTypes();
            AnnotatedType[] annotatedParameterTypes = m.getAnnotatedParameterTypes();
            Annotation[][] parameterAnnotations = m.getParameterAnnotations();
            System.out.println(parameterAnnotations);
        }
    }
}

package com.xwc.open.easybatis.ibatis;

import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.supports.SqlSourceGenerator;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/6 12:19
 */
public class MethodSignature {

    private final boolean returnsMany;
    private final boolean returnsMap;
    private final boolean returnsVoid;
    private final boolean returnsCursor;
    private final boolean returnsOptional;
    private final Class<?> returnType;
    private final String mapKey;
    private final Integer resultHandlerIndex;
    private final Integer rowBoundsIndex;
    private final EasyParamNameResolver paramNameResolver;
    private final boolean multi;
    private final OperateMethodMeta operateMethodMeta;
    private EasyBatisConfiguration easyBatisConfiguration;
    private SqlCommandType sqlCommandType;


    public MethodSignature(EasyBatisConfiguration easyBatisConfiguration, Class<?> mapperInterface, Method method
            , SqlCommandType sqlCommandType) {
        Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
        if (resolvedReturnType instanceof Class<?>) {
            this.returnType = (Class<?>) resolvedReturnType;
        } else if (resolvedReturnType instanceof ParameterizedType) {
            this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
        } else {
            this.returnType = method.getReturnType();
        }
        this.easyBatisConfiguration = easyBatisConfiguration;
        Configuration configuration = easyBatisConfiguration.getConfiguration();
        this.returnsVoid = void.class.equals(this.returnType);
        this.returnsMany = configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray();
        this.returnsCursor = Cursor.class.equals(this.returnType);
        this.returnsOptional = Optional.class.equals(this.returnType);
        this.mapKey = getMapKey(method);
        this.returnsMap = this.mapKey != null;
        this.rowBoundsIndex = getUniqueParamIndex(method, RowBounds.class);
        this.resultHandlerIndex = getUniqueParamIndex(method, ResultHandler.class);
        this.paramNameResolver = new EasyParamNameResolver(configuration, method);
        final String mappedStatementId = mapperInterface.getName() + "." + method.getName();
        this.operateMethodMeta = easyBatisConfiguration.getOperateMethodMeta(mappedStatementId);
        if (operateMethodMeta != null) {
            this.multi = SqlSourceGenerator.isMulti(operateMethodMeta, sqlCommandType);
        } else {
            this.multi = false;
        }
        this.sqlCommandType = sqlCommandType;
    }

    public Object convertArgsToSqlCommandParam(Object[] args) {
        Object namedParams = paramNameResolver.getNamedParams(multi, args);
        if (operateMethodMeta == null) {
            return namedParams;
        }
        Map<String, Object> namedParamMap = methodParams(namedParams);
        easyBatisConfiguration.getParamArgsResolver(operateMethodMeta.getDatabaseId())
                .methodParams(namedParamMap, operateMethodMeta, sqlCommandType);
        if (namedParamMap.size() > 1) {
            return namedParamMap;
        } else if (namedParamMap.size() == 1) {
            return namedParamMap.values().iterator().next();
        } else {
            return namedParams;
        }
    }


    @SuppressWarnings("unchecked")
    public Map<String, Object> methodParams(Object value) {
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        final Map<String, Object> params = new MapperMethod.ParamMap<>();
        if (!operateMethodMeta.getParameterAttributes().isEmpty()) {
            ParameterAttribute parameterAttribute = operateMethodMeta.getParameterAttributes().get(0);
            params.put(parameterAttribute.getParameterName(), value);
        }
        return params;
    }


    public boolean hasRowBounds() {
        return rowBoundsIndex != null;
    }

    public RowBounds extractRowBounds(Object[] args) {
        return hasRowBounds() ? (RowBounds) args[rowBoundsIndex] : null;
    }

    public boolean hasResultHandler() {
        return resultHandlerIndex != null;
    }

    public ResultHandler extractResultHandler(Object[] args) {
        return hasResultHandler() ? (ResultHandler) args[resultHandlerIndex] : null;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public boolean returnsMany() {
        return returnsMany;
    }

    public boolean returnsMap() {
        return returnsMap;
    }

    public boolean returnsVoid() {
        return returnsVoid;
    }

    public boolean returnsCursor() {
        return returnsCursor;
    }

    /**
     * return whether return type is {@code java.util.Optional}.
     *
     * @return return {@code true}, if return type is {@code java.util.Optional}
     * @since 3.5.0
     */
    public boolean returnsOptional() {
        return returnsOptional;
    }

    private Integer getUniqueParamIndex(Method method, Class<?> paramType) {
        Integer index = null;
        final Class<?>[] argTypes = method.getParameterTypes();
        for (int i = 0; i < argTypes.length; i++) {
            if (paramType.isAssignableFrom(argTypes[i])) {
                if (index == null) {
                    index = i;
                } else {
                    throw new BindingException(method.getName() + " cannot have multiple " + paramType.getSimpleName() + " parameters");
                }
            }
        }
        return index;
    }

    public String getMapKey() {
        return mapKey;
    }

    private String getMapKey(Method method) {
        String mapKey = null;
        if (Map.class.isAssignableFrom(method.getReturnType())) {
            final MapKey mapKeyAnnotation = method.getAnnotation(MapKey.class);
            if (mapKeyAnnotation != null) {
                mapKey = mapKeyAnnotation.value();
            }
        }
        return mapKey;
    }


}

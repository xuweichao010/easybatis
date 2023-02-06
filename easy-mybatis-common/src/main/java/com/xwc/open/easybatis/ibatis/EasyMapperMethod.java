package com.xwc.open.easybatis.ibatis;

import com.xwc.open.easy.parse.enums.FillType;
import com.xwc.open.easy.parse.model.FillAttribute;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easy.parse.model.TableMeta;
import com.xwc.open.easy.parse.model.parameter.EntityParameterAttribute;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.fill.FillAttributeHandler;
import com.xwc.open.easybatis.fill.FillWrapper;
import com.xwc.open.easybatis.fill.MapFillWrapper;
import com.xwc.open.easybatis.fill.ObjectFillWrapper;
import com.xwc.open.easybatis.supports.SqlSourceGenerator;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/4 15:49
 */
public class EasyMapperMethod {

    private final SqlCommand command;
    private final MethodSignature method;


    public EasyMapperMethod(Class<?> mapperInterface, Method method, EasyBatisConfiguration config) {
        this.command = new SqlCommand(config.getConfiguration(), mapperInterface, method);
        this.method = new MethodSignature(config, mapperInterface, method, command.getType());

    }


    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result;
        switch (command.getType()) {
            case INSERT: {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = rowCountResult(sqlSession.insert(command.getName(), param));
                break;
            }
            case UPDATE: {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = rowCountResult(sqlSession.update(command.getName(), param));
                break;
            }
            case DELETE: {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = rowCountResult(sqlSession.delete(command.getName(), param));
                break;
            }
            case SELECT:
                if (method.returnsVoid() && method.hasResultHandler()) {
                    executeWithResultHandler(sqlSession, args);
                    result = null;
                } else if (method.returnsMany()) {
                    result = executeForMany(sqlSession, args);
                } else if (method.returnsMap()) {
                    result = executeForMap(sqlSession, args);
                } else if (method.returnsCursor()) {
                    result = executeForCursor(sqlSession, args);
                } else {
                    Object param = method.convertArgsToSqlCommandParam(args);
                    result = sqlSession.selectOne(command.getName(), param);
                    if (method.returnsOptional()
                            && (result == null || !method.getReturnType().equals(result.getClass()))) {
                        result = Optional.ofNullable(result);
                    }
                }
                break;
            case FLUSH:
                result = sqlSession.flushStatements();
                break;
            default:
                throw new BindingException("Unknown execution method for: " + command.getName());
        }
        if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
            throw new BindingException("Mapper method '" + command.getName()
                    + "' attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
        }
        return result;
    }

    private Object rowCountResult(int rowCount) {
        final Object result;
        if (method.returnsVoid()) {
            result = null;
        } else if (Integer.class.equals(method.getReturnType()) || Integer.TYPE.equals(method.getReturnType())) {
            result = rowCount;
        } else if (Long.class.equals(method.getReturnType()) || Long.TYPE.equals(method.getReturnType())) {
            result = (long) rowCount;
        } else if (Boolean.class.equals(method.getReturnType()) || Boolean.TYPE.equals(method.getReturnType())) {
            result = rowCount > 0;
        } else {
            throw new BindingException("Mapper method '" + command.getName() + "' has an unsupported return type: " + method.getReturnType());
        }
        return result;
    }

    private void executeWithResultHandler(SqlSession sqlSession, Object[] args) {
        MappedStatement ms = sqlSession.getConfiguration().getMappedStatement(command.getName());
        if (!StatementType.CALLABLE.equals(ms.getStatementType())
                && void.class.equals(ms.getResultMaps().get(0).getType())) {
            throw new BindingException("method " + command.getName()
                    + " needs either a @ResultMap annotation, a @ResultType annotation,"
                    + " or a resultType attribute in XML so a ResultHandler can be used as a parameter.");
        }
        Object param = method.convertArgsToSqlCommandParam(args);
        if (method.hasRowBounds()) {
            RowBounds rowBounds = method.extractRowBounds(args);
            sqlSession.select(command.getName(), param, rowBounds, method.extractResultHandler(args));
        } else {
            sqlSession.select(command.getName(), param, method.extractResultHandler(args));
        }
    }

    private <E> Object executeForMany(SqlSession sqlSession, Object[] args) {
        List<E> result;
        Object param = method.convertArgsToSqlCommandParam(args);
        if (method.hasRowBounds()) {
            RowBounds rowBounds = method.extractRowBounds(args);
            result = sqlSession.selectList(command.getName(), param, rowBounds);
        } else {
            result = sqlSession.selectList(command.getName(), param);
        }
        // issue #510 Collections & arrays support
        if (!method.getReturnType().isAssignableFrom(result.getClass())) {
            if (method.getReturnType().isArray()) {
                return convertToArray(result);
            } else {
                return convertToDeclaredCollection(sqlSession.getConfiguration(), result);
            }
        }
        return result;
    }

    private <T> Cursor<T> executeForCursor(SqlSession sqlSession, Object[] args) {
        Cursor<T> result;
        Object param = method.convertArgsToSqlCommandParam(args);
        if (method.hasRowBounds()) {
            RowBounds rowBounds = method.extractRowBounds(args);
            result = sqlSession.selectCursor(command.getName(), param, rowBounds);
        } else {
            result = sqlSession.selectCursor(command.getName(), param);
        }
        return result;
    }

    private <E> Object convertToDeclaredCollection(Configuration config, List<E> list) {
        Object collection = config.getObjectFactory().create(method.getReturnType());
        MetaObject metaObject = config.newMetaObject(collection);
        metaObject.addAll(list);
        return collection;
    }

    @SuppressWarnings("unchecked")
    private <E> Object convertToArray(List<E> list) {
        Class<?> arrayComponentType = method.getReturnType().getComponentType();
        Object array = Array.newInstance(arrayComponentType, list.size());
        if (arrayComponentType.isPrimitive()) {
            for (int i = 0; i < list.size(); i++) {
                Array.set(array, i, list.get(i));
            }
            return array;
        } else {
            return list.toArray((E[]) array);
        }
    }

    private <K, V> Map<K, V> executeForMap(SqlSession sqlSession, Object[] args) {
        Map<K, V> result;
        Object param = method.convertArgsToSqlCommandParam(args);
        if (method.hasRowBounds()) {
            RowBounds rowBounds = method.extractRowBounds(args);
            result = sqlSession.selectMap(command.getName(), param, method.getMapKey(), rowBounds);
        } else {
            result = sqlSession.selectMap(command.getName(), param, method.getMapKey());
        }
        return result;
    }

    public static class ParamMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -2212268410512043556L;

        @Override
        public V get(Object key) {
            if (!super.containsKey(key)) {
                throw new BindingException("Parameter '" + key + "' not found. Available parameters are " + keySet());
            }
            return super.get(key);
        }

    }

    public static class SqlCommand {

        private final String name;
        private final SqlCommandType type;

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            final String methodName = method.getName();
            final Class<?> declaringClass = method.getDeclaringClass();
            MappedStatement ms = resolveMappedStatement(mapperInterface, methodName, declaringClass,
                    configuration);
            if (ms == null) {
                if (method.getAnnotation(Flush.class) != null) {
                    name = null;
                    type = SqlCommandType.FLUSH;
                } else {
                    throw new BindingException("Invalid bound statement (not found): "
                            + mapperInterface.getName() + "." + methodName);
                }
            } else {
                name = ms.getId();
                type = ms.getSqlCommandType();
                if (type == SqlCommandType.UNKNOWN) {
                    throw new BindingException("Unknown execution method for: " + name);
                }
            }
        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }

        private MappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName,
                                                       Class<?> declaringClass, Configuration configuration) {
            String statementId = mapperInterface.getName() + "." + methodName;
            if (configuration.hasStatement(statementId)) {
                return configuration.getMappedStatement(statementId);
            } else if (mapperInterface.equals(declaringClass)) {
                return null;
            }
            for (Class<?> superInterface : mapperInterface.getInterfaces()) {
                if (declaringClass.isAssignableFrom(superInterface)) {
                    MappedStatement ms = resolveMappedStatement(superInterface, methodName,
                            declaringClass, configuration);
                    if (ms != null) {
                        return ms;
                    }
                }
            }
            return null;
        }
    }

    public static class MethodSignature {

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
            fill(namedParamMap, operateMethodMeta, sqlCommandType);
            if (namedParamMap.size() > 1) {
                return namedParamMap;
            } else {
                return namedParams;
            }
        }

        /**
         * 尝试填充数据
         * 根据元数据和sql类型判断用户是否需要进行属性填充
         *
         * @param map               数据对象
         * @param operateMethodMeta 需要填充的方法
         * @param sqlCommandType    操作类型
         */
        private void fill(Map<String, Object> map, OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {
            List<FillAttribute> fillAttributes = null;
            TableMeta tableMeta = operateMethodMeta.getDatabaseMeta();
            if (sqlCommandType == SqlCommandType.INSERT) {
                fillAttributes = tableMeta.insertFillAttributes();
            } else if (sqlCommandType == SqlCommandType.UPDATE) {
                fillAttributes = tableMeta.updateFillAttributes();
            }
            if (fillAttributes == null || easyBatisConfiguration.getFillAttributeHandlers().isEmpty()) {
                return;
            }
            try {
                easyBatisConfiguration.getFillAttributeHandlers().forEach(FillAttributeHandler::fillBefore);
                doFill(map, operateMethodMeta, fillAttributes);
            } finally {
                easyBatisConfiguration.getFillAttributeHandlers().forEach(FillAttributeHandler::fillAfter);
            }
        }

        /**
         * 填充数据 根据参数来决定如何进行参数填充
         *
         * @param map
         * @param operateMethodMeta
         * @param fillAttributes
         */
        private void doFill(Map<String, Object> map, OperateMethodMeta operateMethodMeta,
                            List<FillAttribute> fillAttributes) {
            // 需要区分用对象填充还是参数填充 对象填充 参数是entity对象 参数填充 就是参数列表中没有entity对象
            ParameterAttribute entityParam = operateMethodMeta.getParameterAttributes().stream()
                    .filter(parameterAttribute -> parameterAttribute instanceof EntityParameterAttribute)
                    .findAny().orElse(null);
            if (entityParam != null) {
                Object data = map.get(entityParam.getParameterName());
                if (data instanceof List) {
                    ((List<?>) data).forEach(item -> {
                        ObjectFillWrapper objectFillWrapper = new ObjectFillWrapper(operateMethodMeta.getDatabaseMeta(), item);
                        fillAttributes.forEach(fillAttribute -> executorFill(fillAttribute, objectFillWrapper));
                    });
                } else {
                    ObjectFillWrapper objectFillWrapper = new ObjectFillWrapper(operateMethodMeta.getDatabaseMeta(), data);
                    fillAttributes.forEach(fillAttribute -> executorFill(fillAttribute, objectFillWrapper));
                }
            } else {
                MapFillWrapper mapFillWrapper = new MapFillWrapper(map);
                fillAttributes.forEach(fillAttribute -> executorFill(fillAttribute, mapFillWrapper));
            }
        }

        /**
         * 执行参数填充
         *
         * @param fillAttribute 需要进行填充的属性
         * @param wrapper       填充对象
         */
        private void executorFill(FillAttribute fillAttribute, FillWrapper wrapper) {
            if (fillAttribute.getType() == FillType.INSERT || fillAttribute.getType() == FillType.INSERT_UPDATE) {
                easyBatisConfiguration.getFillAttributeHandlers()
                        .forEach(fillAttributeHandler -> fillAttributeHandler.insertFill(fillAttribute.getIdentification(), fillAttribute.getField(),
                                wrapper));
            }
            if (fillAttribute.getType() == FillType.UPDATE || fillAttribute.getType() == FillType.INSERT_UPDATE) {
                easyBatisConfiguration.getFillAttributeHandlers()
                        .forEach(fillAttributeHandler -> fillAttributeHandler.updateFill(fillAttribute.getIdentification(), fillAttribute.getField(), wrapper));
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
}

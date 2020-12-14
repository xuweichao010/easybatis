package com.xwc.open.easybatis.core.handler.impl;

import com.xwc.open.easybatis.core.handler.EnumHandler;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 创建人：徐卫超
 * 时间：2019/12/20 16:51
 * 功能：处理枚举的实现
 * 备注：
 */
@MappedJdbcTypes({JdbcType.INTEGER})
@MappedTypes({EnumHandler.class})
public class EnumTypeHandler<E extends Enum<?> & EnumHandler> extends BaseTypeHandler<EnumHandler> {

    private Class<E> type;

    public EnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EnumHandler parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.value());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : enumOf(type,value);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : enumOf(type,code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : enumOf(type,code);
    }

    private <E extends Enum<?> & EnumHandler> E enumOf(Class<E> type, int code) {
        E[] enumConstants = type.getEnumConstants();
        for (E e : enumConstants) {
            if (e.value() == code)
                return e;
        }
        return null;
    }
}

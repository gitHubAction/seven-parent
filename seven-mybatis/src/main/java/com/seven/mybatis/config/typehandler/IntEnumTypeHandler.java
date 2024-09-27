package com.seven.mybatis.config.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntEnumTypeHandler<E extends Enum<?> & BaseIntEnum> extends BaseTypeHandler<BaseIntEnum> {

    private Class<E> type;

    public IntEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseIntEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, Integer.parseInt(parameter.getValue()));
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull() ? null : codeOf(code);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : codeOf(code);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : codeOf(code);
    }

    private E codeOf(int code) {
        try {
            return BaseEnumUtils.valueOf(type, code);
        } catch (Exception ex) {
            throw new IllegalArgumentException(String.format("Cannot convert %s to %s by code value.", code, type.getSimpleName()), ex);
        }
    }
}

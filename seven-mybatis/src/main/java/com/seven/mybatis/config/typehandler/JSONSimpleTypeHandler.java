package com.seven.mybatis.config.typehandler;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author shanhonghao
 * @date 2018-10-25 09:11
 */
public class JSONSimpleTypeHandler<E> extends BaseTypeHandler<E> {

    private Class<E> type;

    public JSONSimpleTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return rs.wasNull() ? null : toObject(json);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return rs.wasNull() ? null : toObject(json);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return cs.wasNull() ? null : toObject(json);
    }

    public String toJson(E parameter) {
        return JSON.toJSONString(parameter);
    }

    public E toObject(String json) {
        try {
            if (json == null) {
                return null;
            }
            return JSON.parseObject(json, type);
        } catch (Exception ex) {
            throw new IllegalArgumentException(String.format("Cannot convert %s to %s by json value.", json, type.getSimpleName()), ex);
        }
    }
}

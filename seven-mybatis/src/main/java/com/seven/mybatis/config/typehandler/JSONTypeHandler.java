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
 * @date 2018-10-25 09:29
 */
public abstract class JSONTypeHandler<E extends JSON> extends BaseTypeHandler<E> {

    private Class<E> type;

    public JSONTypeHandler(Class<E> type) {
        if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String jsonStr = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return toObject(jsonStr);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String jsonStr = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return toObject(jsonStr);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String jsonStr = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return toObject(jsonStr);
        }
    }

    public String toJson(E parameter) {
        return JSON.toJSONString(parameter);
    }

    public abstract E toObject(String json);

}

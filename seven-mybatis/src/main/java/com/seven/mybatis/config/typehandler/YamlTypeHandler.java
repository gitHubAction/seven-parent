package com.seven.mybatis.config.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shanhonghao
 * @date 2018-10-26 08:26
 */
public class YamlTypeHandler<E> extends BaseTypeHandler<E> {

    private Class<E> type;
    private Representer representer;
    private DumperOptions options;

    public YamlTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        this.options = options;

        Representer representer = new NonMetaClassRepresenter();
        representer.addClassTag(type, Tag.MAP);
        this.representer = representer;
    }

    class NonMetaClassRepresenter extends Representer {
        @Override
        protected Set<Property> getProperties(Class<? extends Object> type) {
            return super.getProperties(type).stream()
                .filter(property -> !"metaClass".equals(property.getName()))
                .collect(Collectors.toSet());
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        String yaml = toYaml(parameter);
        ps.setString(i, yaml);
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

    public String toYaml(E parameter) {
        Yaml parser = new Yaml(new Constructor(), representer, this.options);
        return parser.dump(parameter);
    }

    public E toObject(String yaml) {
        try {
            if (yaml == null) {
                return null;
            }
            Yaml parser = new Yaml();
            return parser.loadAs(yaml, type);
        } catch (Exception ex) {
            throw new IllegalArgumentException(String.format("Cannot convert %s to %s by yaml value.", yaml, type.getSimpleName()), ex);
        }
    }
}

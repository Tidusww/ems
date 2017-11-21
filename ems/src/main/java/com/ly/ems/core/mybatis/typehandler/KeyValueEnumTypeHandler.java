package com.ly.ems.core.mybatis.typehandler;

import com.ly.ems.common.utils.EnumUtil;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import com.ly.ems.common.utils.EnumUtil;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tidus on 2017/10/31.
 */
public class KeyValueEnumTypeHandler<E extends Enum<?> & BaseKeyValueEnum> extends BaseTypeHandler<BaseKeyValueEnum> {

    private Class<E> type;

    public KeyValueEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    /**
     * 用于定义设置参数时，该如何把Java类型的参数转换为对应的数据库类型
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseKeyValueEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getKey());
    }

    /**
     * 用于定义通过字段名称获取字段数据时，如何把数据库类型转换为对应的Java类型
     * @param rs
     * @param columnName
     * @return
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Integer key = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            try {
                return EnumUtil.getKeyValueEnumByKey(type, key);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + key + " to " + type.getSimpleName() + " by ordinal value.",
                        ex);
            }
        }
    }

    /**
     * 用于定义通过字段索引获取字段数据时，如何把数据库类型转换为对应的Java类型
     * @param rs
     * @param columnIndex
     * @return
     */
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Integer key = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            try {
                return EnumUtil.getKeyValueEnumByKey(type, key);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + key + " to " + type.getSimpleName() + " by ordinal value.",
                        ex);
            }
        }
    }


    /**
     * 用定义调用存储过程后，如何把数据库类型转换为对应的Java类型
     * @param cs
     * @param columnIndex
     * @return
     */
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Integer key = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            try {
                return EnumUtil.getKeyValueEnumByKey(type, key);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + key + " to " + type.getSimpleName() + " by ordinal value.",
                        ex);
            }
        }
    }
}

package com.liyuan.ems.core.mybatis.typehandler;

import com.liyuan.ems.common.utils.EnumUtil;
import com.liyuan.ems.core.mybatis.BaseCodeValueEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用于转换Code与Value都是String的Enum, 且数据库中保存的是Code
 * Created by tidus on 2017/10/31.
 */
public class CodeValueEnumTypeHandler<E extends Enum<?> & BaseCodeValueEnum> extends BaseTypeHandler<BaseCodeValueEnum> {

    private Class<E> type;

    public CodeValueEnumTypeHandler(Class<E> type) {
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
    public void setNonNullParameter(PreparedStatement ps, int i, BaseCodeValueEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    /**
     * 用于定义通过字段名称获取字段数据时，如何把数据库类型转换为对应的Java类型
     * @param rs
     * @param columnName
     * @return
     */
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            try {
                return EnumUtil.getCodeValueEnumByCode(type, code);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + code + " to " + type.getSimpleName() + " by ordinal value.",
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
        String code = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            try {
                return EnumUtil.getCodeValueEnumByCode(type, code);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + code + " to " + type.getSimpleName() + " by ordinal value.",
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
        String code = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            try {
                return EnumUtil.getCodeValueEnumByCode(type, code);
            } catch (Exception ex) {
                throw new IllegalArgumentException("Cannot convert " + code + " to " + type.getSimpleName() + " by ordinal value.",
                        ex);
            }
        }
    }

}

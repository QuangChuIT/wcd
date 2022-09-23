package com.aptech.utils;

import com.aptech.anotations.ColumnName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultToObject {
    public <T> List<T> getData(ResultSet rs, Class<T> clazz) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            T t = clazz.getConstructor().newInstance();
            this.loadDataToObject(rs, t);
            list.add(t);
        }
        return list;
    }


    public <T> void loadDataToObject(ResultSet rs, T obj) throws SQLException {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            ColumnName columnName = field.getAnnotation(ColumnName.class);
            Object o = rs.getObject(columnName.name());
        }
    }

}

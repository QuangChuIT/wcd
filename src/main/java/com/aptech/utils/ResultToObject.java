package com.aptech.utils;

import com.aptech.anotations.ColumnName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultToObject {

    private static ResultToObject instance;

    private ResultToObject() {

    }

    public static ResultToObject getInstance() {
        if (instance == null) {
            instance = new ResultToObject();
        }
        return instance;
    }

    public <T> List<T> getData(ResultSet rs, Class<T> clazz) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            T t = clazz.getConstructor().newInstance();
            this.loadDataToObject(rs, t);
            list.add(t);
        }
        return list;
    }


    private void loadDataToObject(ResultSet rs, Object obj) throws SQLException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            ColumnName columnName = field.getAnnotation(ColumnName.class);
            Object value = rs.getObject(columnName.name());
            Class<?> type = field.getType();
            if (isPrimitive(type)) {
                Class<?> boxed = boxPrimitiveClass(type);
                Object newValue = boxed.cast(value);
                field.set(obj, newValue);
            } else if (type == Date.class) {
                Timestamp timestamp = rs.getTimestamp(columnName.name());
                if (timestamp != null) {
                    Date date = new Date(timestamp.getTime());
                    field.set(obj, date);
                } else {
                    field.set(obj, null);
                }
            } else { // Default String
                field.set(obj, value);
            }
        }
    }

    private boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class
                || type == boolean.class || type == byte.class || type == char.class || type == short.class);
    }

    private Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            String string = "class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }
}

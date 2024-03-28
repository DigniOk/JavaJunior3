package model;

import annotation.Column;
import annotation.Id;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class DBTransit<T> {
    public JDBC connection;
    public DBTransit(JDBC connection) {
        this.connection = connection;
    }
    public boolean saveOrUpdate(T object) {
        Class<?> objectClass = object.getClass();
        int id = getId(objectClass, object);
        String firstName = getFirstName(objectClass, object);
        String lastName = getLastName(objectClass, object);
        int age = getAge(objectClass, object);
        if (id == -1 || firstName == null || lastName == null || age == -1) return false;
        else {
            connection.saveOrUpdateObject(id, firstName, lastName, age);
            return true;
        }
    }
    private int getId(Class<?> objectClass, T obj) {
        int id = -1;
        for (Method method : objectClass.getMethods()) {
            if (method.getAnnotation(Id.class) != null) {
                if (method.getAnnotation(Id.class).id() > 0) return method.getAnnotation(Id.class).id();
                else {
                    try {
                        id = (int) method.invoke(obj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return id;
    }
    private String getFirstName(Class<?> objectClass, T obj) {
        for (Method method : objectClass.getMethods()) {
            if (method.getAnnotation(Column.class) != null && method.getName().equals("getFirstName")) {
                if (!method.getAnnotation(Column.class).name().equals(""))
                    return method.getAnnotation(Column.class).name();
                else {
                    try {
                        return (String) method.invoke(obj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }
    /**
     * По аннотации ищет фамилию объекта (класса)
     * @param objectClass - класс объекта
     * @param obj - объект у которого мы вызываем метод
     * @return возвращает имя объекта, если у аннотации есть переданный параметр, возвращает его
     * иначе возвращает null
     */
    private String getLastName(Class<?> objectClass, T obj) {
        for (Method method : objectClass.getMethods()) {
            if (method.getAnnotation(Column.class) != null && method.getName().equals("getLastName")) {
                if (!method.getAnnotation(Column.class).name().equals(""))
                    return method.getAnnotation(Column.class).name();
                else {
                    try {
                        return (String) method.invoke(obj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }
    private int getAge(Class<?> objectClass, T obj) {
        int age = -1;
        for (Method method : objectClass.getMethods()) {
            if (method.getAnnotation(Column.class) != null && method.getName().equals("getAge")) {
                try {
                    age = (int) method.invoke(obj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return age;
    }
    public String simpleSelect() {
        return connection.simpleSelect();
    }
}

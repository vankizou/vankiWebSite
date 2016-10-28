package com.zoufanqi.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 基于entity中的对象进行操作
 *
 * @author vanki
 * @project_name lofficiel-cloud 2015年12月3日 下午3:47:41
 */
public class EntityUtil {

    /**
     * 复制属性值 <br />
     * ps: 两对象中属性(名称/值类型)一定是包含或包含于关系
     *
     * @param receiveCls         接受值Class对象
     * @param expendObj          输出值对象
     * @param receiveFieldIsMain 是否以receive的属性为主, false以send属性为主
     * @param <Entity>
     *
     * @return
     */
    public static <Entity> Entity copyObj(Class<Entity> receiveCls, Object expendObj, boolean... receiveFieldIsMain) {
        try {
            Entity receiveObj = receiveCls.newInstance();
            return EntityUtil.copyObj(receiveObj, expendObj, receiveFieldIsMain);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 复制属性值 <br />
     * ps: 两对象中属性(名称/值类型)一定是包含或包含于关系
     *
     * @param receiveObj    接受值对象
     * @param expendObj     输出值对象
     * @param receiveIsMain 是否以receive 的属性为主
     * @param <Entity>
     *
     * @return
     */
    public static <Entity> Entity copyObj(Entity receiveObj, Object expendObj, boolean... receiveIsMain) {
        if (receiveObj == null || expendObj == null) return null;
        try {
            Class<?> receiveCls = receiveObj.getClass();
            Class<?> expendCls = expendObj.getClass();
            Field[] fsReceive = receiveCls.getDeclaredFields();
            Field[] fsExpend = expendCls.getDeclaredFields();
            Field[] fs;

            if (receiveIsMain != null && receiveIsMain.length > 0) {
                boolean flag = receiveIsMain[0];
                if (flag) {
                    fs = receiveCls.getDeclaredFields();
                } else {
                    fs = expendCls.getDeclaredFields();
                }
            } else {
                if (fsReceive.length <= fsExpend.length) {
                    fs = receiveCls.getDeclaredFields();
                } else {
                    fs = expendCls.getDeclaredFields();
                }
            }
            for (Field f : fs) {
                Class<?> type = f.getType();
                String fName = f.getName();
                String setterName = EntityUtil.getSetter(fName);
                String getterName = EntityUtil.getGetter(fName);
                Object value = expendCls.getMethod(getterName).invoke(expendObj);
                if (value != null) {
                    receiveCls.getMethod(setterName, type).invoke(receiveObj, value);
                }
            }
            return receiveObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 给对象 e 中的属性 fName 赋值 fValue
     *
     * @param e      要赋值的对象
     * @param fName  对象中的属性名称
     * @param fValue 对应属性值
     * @return 成功true，失败false<br>
     * 2015年12月17日 下午4:16:36
     */
    @SuppressWarnings("unchecked")
    public static <E> boolean setValueToObj(E e, String fName, Object fValue) {
        Class<E> cls = (Class<E>) e.getClass();
        try {
            Field field = cls.getDeclaredField(fName);
            cls.getMethod(getSetter(fName), field.getType()).invoke(e, fValue);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 给对象 e 中的属性 fName 赋值 fValue
     *
     * @param e      要赋值的对象
     * @param fName  对象中的属性名称
     * @param fValue 对应属性值
     * @param fValue 对应方法参数类型
     *
     * @return 成功true，失败false<br>
     * 2015年12月17日 下午4:16:36
     */
    @SuppressWarnings("unchecked")
    public static <E> boolean setValueToObj(E e, String fName, Object fValue, Class<?> fType) {
        Class<E> cls = (Class<E>) e.getClass();
        try {
            cls.getMethod(getSetter(fName), fType).invoke(e, fValue);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 获取对象中的属性值
     *
     * @param obj
     * @param fName
     *
     * @return
     */
    public static Object getValueFromObj(Object obj, String fName) {
        try {
            Class<?> cls = obj.getClass();
            return cls.getMethod(getGetter(fName)).invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }

    private static String getMethodName_andxxEqualsTo(String fName) {
        return "and" + Character.toUpperCase(fName.charAt(0))
                + fName.substring(1) + "EqualTo";
    }

    private static String getGetter(String fName) {
        return "get" + Character.toUpperCase(fName.charAt(0))
                + fName.substring(1);
    }

    private static String getSetter(String fName) {
        return "set" + Character.toUpperCase(fName.charAt(0))
                + fName.substring(1);
    }
}
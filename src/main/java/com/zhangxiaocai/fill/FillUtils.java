package com.zhangxiaocai.fill;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @authors name:邓志强
 * @descriptions:
 * @date:2021/12/10 10:12
 * @version:1.0
 */
public class FillUtils {

        /**
         * 主方法
         * @param obj
         * @return
         */
        public static Object getFillObj(Object obj){
            if (obj == null) return null;
            Class<?> clazz = obj.getClass();
            Field[] fields = coverType(clazz.getDeclaredFields());
            if (fields == null) return null;
            for (Field field : fields) {
                if (field.isAnnotationPresent(Fill.class)) {
                    try {
                        if (isMatch(field,field.get(obj),true))isMatch(field, obj,false);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            return obj;
        }

        /**
         * 对变量进行赋值
         * @param item
         * @param obj
         */
        private static boolean isMatch(Field item,Object obj,boolean boo) throws Exception{
            try {
                Class<?> type = item.getType();
                Fill annotation = item.getAnnotation(Fill.class);
                switch (type.getSimpleName()){
                    case "int":
                        if (!boo)item.set(obj,annotation.intVal());
                        if (boo && (obj == null || (Integer) obj == 0)) return true;
                        break;
                    case "byte":
                        if (!boo)item.set(obj,annotation.byteVal());
                        if (boo && (obj == null || (Byte)obj == 0)) return true;
                        break;
                    case "char":
                        if (!boo)item.set(obj,annotation.charVal());
                        if (boo && (obj == null || (Character) obj == '\u0000')) return true;
                        break;
                    case "short":
                        if (!boo)item.set(obj,annotation.shortVal());
                        if (boo && (obj == null || (Short)obj == 0)) return true;
                        break;
                    case "long":
                        if (!boo)item.set(obj,annotation.longVal());
                        if (boo && (obj == null || (Long) obj == 0L)) return true;
                        break;
                    case "double":
                        if (!boo)item.set(obj,annotation.doubleVal());
                        if (boo && (obj == null || (Double) obj == 0.0d)) return true;
                        break;
                    case "float":
                        if (!boo)item.set(obj,annotation.floatVal());
                        if (boo && (obj == null || (Float) obj == 0.0f)) return true;
                        break;
                    case "boolean":
                        if (!boo)item.set(obj,annotation.booleanVal());
                        if (boo && (obj == null || (Boolean) obj == false)) return true;
                        break;
                    case "String":
                        if (!boo)item.set(obj,annotation.stringVal());
                        if (boo && (obj == null || obj.toString().equals(""))) return true;
                        break;
                    default:
                        if (!boo) item.set(obj,getFillObj(annotation.objectVal().newInstance()));
                        if (boo && (obj == null)) return true;
                        break;
                }
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return false;
        }


        /**
         * 将所有变量都设置为可修改
         * @param fields
         * @return
         */
        private static Field[] coverType(Field[] fields){
            if (fields == null || fields.length < 1) return null;
            for (int i = 0; i < fields.length; i++) {
                if (Modifier.isPrivate(fields[i].getModifiers()) || Modifier.isProtected(fields[i].getModifiers()))
                    fields[i].setAccessible(true);
            }
            return fields;
        }
}

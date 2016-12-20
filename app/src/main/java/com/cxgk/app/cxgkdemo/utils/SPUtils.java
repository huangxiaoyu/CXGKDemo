package com.cxgk.app.cxgkdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Huang on 2016/3/22.
 */
public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = Constants.SP_CONFIG;

    /**
     * {"code":"1","results":{"userid":"239","realname":"API\u6D4B\u8BD5\u7528\u6237","lastdate":"2016/8/9 14:26:56","lastip":"123.149.7.1"}}
     *
     * @param context
     * @param key
     * @param def
     * @return
     */
    public static Object get(Context context, String key, Object def) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (def instanceof String) {
            return sp.getString(key, String.valueOf(def));
        }
        if (def instanceof Integer) {
            return sp.getInt(key, (Integer) def);
        }
        if (def instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) def);
        }
        if (def instanceof Double) {
            return sp.getFloat(key, (Float) def);
        }
        if (def instanceof Long) {
            return sp.getLong(key, (Long) def);
        }
        return null;
    }

    public static void put(Context context, String key, Object def) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (def instanceof String) {
            editor.putString(key, String.valueOf(def));
        }
        if (def instanceof Integer) {
            editor.putInt(key, (Integer) def);
        }
        if (def instanceof Boolean) {
            editor.putBoolean(key, (Boolean) def);
        }
        if (def instanceof Double) {
            editor.putFloat(key, (Float) def);
        }
        if (def instanceof Long) {
            editor.putLong(key, (Long) def);
        }
        SharedPreferencesCompat.apply(editor);
    }

    public static Map<String, ?> getALL(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        SharedPreferencesCompat.apply(editor);
    }

    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }


    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    static class SharedPreferencesCompat {
        private static final Method applyMethod = findApplyMethod();

        private static Method findApplyMethod() {
            Class c = SharedPreferences.Editor.class;
            try {
                return c.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

        static void apply(SharedPreferences.Editor editor) {
            try {
                if (applyMethod != null) {
                    applyMethod.invoke(editor);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

}

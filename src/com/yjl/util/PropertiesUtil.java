package com.yjl.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties = new Properties();
    static {
        try {
//            File file = new File("resources/database.properties");
//            InputStream is = new FileInputStream(file);
            //通过类加载器，把配置文件datebase.properties转换为输入流
            InputStream is
                    = PropertiesUtil.class.getClassLoader().getResourceAsStream("database.properties");
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过key拿value
     * @param key
     * @return
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(PropertiesUtil.get("url"));
        System.out.println(PropertiesUtil.get("username"));
        System.out.println(PropertiesUtil.get("password"));
        System.out.println(PropertiesUtil.get("driver"));
    }
}

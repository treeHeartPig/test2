package org.smart4j.chapter2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 269871 on 2016/12/26.
 */
public class ClassUtil {
    private static final Logger LOGGER= LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public  static ClassLoader getClassLoader(){

        return Thread.currentThread().getContextClassLoader();
    }
    /**
     * 加载类
     */
    public static Class<?> loadClass(String className,boolean isInitialized){

        Class<?> cls=null;
        try {
            cls=Class.forName(className,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  cls;
    }
    /**
     * 获取指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName){

        Set<Class<?>> classSet=new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls=getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url=urls.nextElement();
                if(url!=null){
                    String protocol=url.getProtocol();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return classSet;
    }
}

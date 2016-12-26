package org.smart4j.chapter2.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.CollectionUtil;
import org.smart4j.chapter2.util.PropsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 269871 on 2016/12/14.
 */
public final class DatabaseHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER;

    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    private static final BasicDataSource DATA_SOURCE;

//    private static final String DRIVER;
//    private static final String URL;
//    private static final String USERNAME;
//    private static final String PASSWORD;

    static{
        CONNECTION_HOLDER=new ThreadLocal<Connection>();
        QUERY_RUNNER=new QueryRunner();

        Properties properties= PropsUtil.loadProps("config.properties");
        String driver=properties.getProperty("jdbc.driver");
        String url=properties.getProperty("jdbc.url");
        String username=properties.getProperty("jdbc.username");
        String password=properties.getProperty("jdbc.password");

        DATA_SOURCE=new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setPassword(password);
        DATA_SOURCE.setUsername(username);

    }
    /**
     * 查询实体列表
     */
    public static<T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){

        List<T> entityList;
        Connection conn=getConnection();
        try {

            entityList=QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        }catch (SQLException e){
            LOGGER.error("query entity list failure",e);
            throw new RuntimeException(e);
        }

        return entityList;
    }
    /**
     * 查询实体
     */
    public static<T>T queryEntity(Class<T> entityClass,String sql, Object... params){
        T entity;
        Connection conn=null;
        try {
            conn=getConnection();
            entity=QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(" query entity failure !",e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * 执行更新
     */
    public static int executeUpdate(String sql,Object... params) {
        int rows = 0;
        Connection conn = null;
        try {
            conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("update sql failure",e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return  rows;
    }
    /**
     * 插入实体
     */
    public static<T> boolean   insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("insert sql failure! fieldMap is null");
            return false;
        }
        String sql ="INSERT INTO "+getTableName(entityClass);
        StringBuffer columns=new StringBuffer("(");
        StringBuffer values=new StringBuffer("(");
        for (String fieldName:fieldMap.keySet()){
            columns.append(fieldName).append(",");
            values.append("?,");
        }
        columns.replace(columns.indexOf(","),columns.length(),")");
        values.replace(values.indexOf(","),values.length(),")");
        sql+=columns+"VALUES"+values;

        Object[] params=fieldMap.values().toArray();
        return executeUpdate(sql,params)==1;
    }

    /**
     * 更新实体
     */
    public static<T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("UPDATE sql failure! fieldMap is null");
            return false;
        }
        String sql="UPDATE "+getTableName(entityClass)+" SET ";
        StringBuilder fields=new StringBuilder();
        for (String fieldName:fieldMap.keySet()){
            fields.append(fieldName).append("=? ,");
        }
        sql+=fields.substring(0,fields.lastIndexOf(", "))+" WHERE id=?";
        Object[] params=fieldMap.values().toArray();
        return  executeUpdate(sql,params)==1;
    }

    /**
     * 删除操作
     */
    public static<T> boolean deleteEntity(Class<T> entityClass,long id){
       String sql="DELETE FROM "+getTableName(entityClass)+" WHERE id=?";
        return executeUpdate(sql,id)>=1;
    }


    private static String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName();
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn = null;
        conn = CONNECTION_HOLDER.get();
        if(conn==null){
            try{
                conn=DATA_SOURCE.getConnection();
            }catch (SQLException e){
                LOGGER.error("--get connection failure ",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }

        return conn;
    }

    /**
     * 执行SQL文件
     */
    public static void executeSqlFile(String filePath){
        InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader=new BufferedReader(new InputStreamReader(is));
        String sql;
        try {
            while ((sql=reader.readLine())!=null){
                executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("read sqlFile is failure",e);
            e.printStackTrace();
        }
    }
}

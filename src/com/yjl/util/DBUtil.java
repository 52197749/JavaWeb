package com.yjl.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBUtil {

    private static String url = PropertiesUtil.get("url");
    private static String user = PropertiesUtil.get("username");
    private static String pwd = PropertiesUtil.get("password");
    private static String driver = PropertiesUtil.get("driver");
    static {
        //1.加载驱动，即不同的数据库厂商提供的实现java连接数据库的实现方式。
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动类");
            e.printStackTrace();
        }
    }

    /**
     * 创建连接对象
     * @return
     */
    public static Connection getConnection(){
        //2.获取连接对象
        try {
            Connection conn = DriverManager.getConnection(url, user, pwd);
            return conn;
        } catch (SQLException e) {
            System.out.println("创建连接对象异常");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询的sql
     * @param conn
     * @param sql
     * @param params
     * @return
     */
    public static ResultSet executeQuery(Connection conn, String sql, Object[] params) throws SQLException {
        //3.创建PreparedStatement
        //String sql = "SELECT  * FROM petowner where id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        if(params != null){
            for(int i=0; i<params.length; i++){
                ps.setObject(i+1, params[i]);
            }
        }
        //4.执行查询的sql
        //执行查询sql，返回结果集
        ResultSet resultSet = ps.executeQuery();
        return resultSet;
    }

    /**
     * 更新操作
     * @param conn
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static int executeUpdate(Connection conn, String sql, Object[] params) throws SQLException{
        //创建预编译对象statement/PreparedStatement
        PreparedStatement ps = conn.prepareStatement(sql);
        //给占位符复制
        if(params != null){
            for(int i=0; i<params.length; i++){
                ps.setObject(i+1, params[i]);
            }
        }
        //4.执行添加的sql （添加、修改、删除 都是ps.executeUpdate()）
        //执行查询sql，返回整数
        return ps.executeUpdate();
    }


    /**
     * 释放资源
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, Statement st, Connection conn){
        if(rs!=null){
            try {
                rs.close();//关闭结果集
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st != null){
            try {
                st.close();//关闭状态对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn != null){
            try {
                conn.close();//关闭连接对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static<T> List<T> getListByResultSet(ResultSet rs, Class<T> clazz) throws SQLException {
        //获取元数据(包含列的相关信息)
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        //System.out.println(columnCount);
        String[] names = new String[columnCount];
        Object[] values = new Object[columnCount];
        List<T> list = new ArrayList<T>();
        while (rs.next()) {
            for (int i=0; i<columnCount; i++) {
                String columnName = metaData.getColumnName(i+1);
                Object value = rs.getObject(i+1);
                names[i] = columnName;
                values[i] = value;
            }
            T t = FansheUtil.getInstance(clazz, names, values);
            list.add(t);
        }
        return list;
    }

    public static<T> T getBeanByResultSet(ResultSet rs, Class<T> clazz) throws SQLException {
        //获取元数据(包含列的相关信息)
        List<T> list = getListByResultSet(rs, clazz);
        if(list != null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

}

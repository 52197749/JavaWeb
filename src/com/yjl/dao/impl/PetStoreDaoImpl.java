package com.yjl.dao.impl;

import com.yjl.dao.PetStoreDao;
import com.yjl.entity.PetStore;
import com.yjl.util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PetStoreDaoImpl implements PetStoreDao {

    private Connection conn;
    public PetStoreDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<PetStore> findAll() throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "select * from petStore";
        ResultSet rs = DBUtil.executeQuery(conn, sql, null);
        List<PetStore> list = DBUtil.getListByResultSet(rs, PetStore.class);
        return list;
    }

    @Override
    public PetStore findById(int id) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "select * from petStore where id=?";
        Object[] params = new Object[]{
                id
        };
        ResultSet rs = DBUtil.executeQuery(conn, sql, params);
        PetStore petStore = DBUtil.getBeanByResultSet(rs, PetStore.class);
        return petStore;
    }

    @Override
    public PetStore find(String name, String password) throws SQLException {
        String sql = "select * from petStore where name=? and password=?";
        Object[] params = new Object[]{
                name, password
        };
        ResultSet rs = DBUtil.executeQuery(conn, sql, params);
        PetStore petStore = DBUtil.getBeanByResultSet(rs, PetStore.class);
        return petStore;
    }

    @Override
    public int add(PetStore petStore) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "insert into petStore (name, password, balance) values(?,?,?)";
        Object[] params = new Object[]{
                petStore.getName(), petStore.getPassword(), petStore.getBalance()
        };
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }

    @Override
    public int modify(PetStore petStore) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "update petStore set name=?, password=?, balance=? where id=?";
        Object[] params = new Object[]{
                petStore.getName(), petStore.getPassword(),
                petStore.getBalance(), petStore.getId()
        };
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }

    @Override
    public int delete(PetStore petStore) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "delete from petStore where id=?";
        Object[] params = new Object[]{
                 petStore.getId()
        };
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }

    @Override
    public int deleteById(int id) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "delete from petStore where id=?";
        Object[] params = new Object[]{
                id
        };
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }
}

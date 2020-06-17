package com.yjl.dao.impl;

import com.yjl.dao.PetDao;
import com.yjl.entity.Pet;
import com.yjl.util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PetDaoImpl implements PetDao {

    private Connection conn;
    public PetDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Pet> findAll() throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "select * from pet";
        ResultSet rs = DBUtil.executeQuery(conn, sql, null);
        return DBUtil.getListByResultSet(rs, Pet.class);
    }

    @Override
    public List<Pet> findPetStorePets() throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "SELECT * FROM pet WHERE owner_id IS NULL AND store_id IS NOT NULL";
        ResultSet rs = DBUtil.executeQuery(conn, sql, null);
        return DBUtil.getListByResultSet(rs, Pet.class);
    }


    @Override
    public Pet findById(int id) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "select * from pet where id=?";
        ResultSet rs = DBUtil.executeQuery(conn, sql, new Object[]{id});
        return DBUtil.getBeanByResultSet(rs, Pet.class);
    }

    @Override
    public List<Pet> findByOwnerId(int ownerId) throws SQLException {
        //通过主人id获取主人的宠物列表
        String sql = "select * from pet where owner_id = ?";
        return DBUtil.getListByResultSet(DBUtil.executeQuery(conn, sql, new Object[]{ownerId}), Pet.class);
    }

    @Override
    public List<Pet> findByOwnerId(int ownerId, int pageNo, int pageSize) throws SQLException {
        String sql = "select * from pet where owner_id = ? limit ?,?";
        int startIndex = (pageNo-1)*pageSize;
        Object[] params = new Object[]{ownerId, startIndex, pageSize};

        return DBUtil.getListByResultSet(DBUtil.executeQuery(conn, sql, params), Pet.class);
    }


    @Override
    public int add(Pet pet) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "insert into pet (name, typeName, health, love,birthday, owner_id, store_id) values(?,?,?,?,?,?,?)";
        Object[] params = new Object[]{
                pet.getName(), pet.getTypeName(), pet.getHealth(), pet.getLove(),
                pet.getBirthday(), pet.getOwner_id(), pet.getStore_id()
        };
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }

    @Override
    public int modify(Pet pet) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "update pet set name=?, typeName=?, health=?, love=?, birthday=?, owner_id=?, store_id=? where id=?";
        Object[] params = new Object[]{
                pet.getName(), pet.getTypeName(), pet.getHealth(), pet.getLove(),
                pet.getBirthday(), pet.getOwner_id(), pet.getStore_id(), pet.getId()
        };
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }

    @Override
    public int delete(Pet pet) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "delete from pet where id=?";
        int count = DBUtil.executeUpdate(conn, sql, new Object[]{pet.getId()});
        return count;
    }

    @Override
    public int deleteById(int id) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "delete from pet where id=?";
        int count = DBUtil.executeUpdate(conn, sql, new Object[]{id});
        return count;
    }

    @Override
    public int getCount(int ownerId) throws SQLException {
        String sql = "select count(*) from pet where owner_id=?";
        ResultSet rs = DBUtil.executeQuery(conn, sql, new Object[]{ownerId});
        if(rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }
}
package com.yjl.dao.impl;

import com.yjl.dao.PetOwnerDao;
import com.yjl.entity.PetOwner;
import com.yjl.util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PetOwnerDaoImpl implements PetOwnerDao {

    private Connection conn;
    public PetOwnerDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<PetOwner> getAllPetOwner() throws SQLException {

        //4.执行查询的sql
        //执行查询sql，返回结果集
        String sql = "SELECT  * FROM petowner";
        ResultSet resultSet = DBUtil.executeQuery(conn, sql, null);
        //解析结果集，while循环取返回的记录
        /*List<PetOwner> list = new ArrayList<>();
        while(resultSet.next()){
            //遍历取记录
            PetOwner petOwner = createPetOwner(resultSet);;
            list.add(petOwner);
        }*/
        return DBUtil.getListByResultSet(resultSet, PetOwner.class);
    }

    @Override
    public PetOwner getOwnerById(int id) throws SQLException {
        //4.执行查询的sql
        //执行查询sql，返回结果集
        String sql = "SELECT  * FROM petowner where id=?";
        ResultSet resultSet = DBUtil.executeQuery(conn, sql, new Object[]{id});
        if(resultSet.next()){
            PetOwner petOwner = createPetOwner(resultSet);
            return petOwner;
        }
        return null;
    }

    private PetOwner createPetOwner(ResultSet resultSet) throws SQLException {
        int id_ = resultSet.getInt("id");
        String name = resultSet.getString(2);
        String password = resultSet.getString(3);
        int balance = resultSet.getInt(4);
        PetOwner petOwner = new PetOwner(id_, name, password, balance);
        return petOwner;
    }


    @Override
    public int addPetOwner(PetOwner petOwner) throws SQLException {
        //创建预编译对象statement/PreparedStatement
        String sql = "INSERT INTO petowner(name, password, money) VALUES (?, ?, ?)";

        //4.执行添加的sql （添加、修改、删除 都是ps.executeUpdate()）
        //执行查询sql，返回结果集
        Object[] params = new Object[]{petOwner.getName(), petOwner.getPassword(), petOwner.getMoney()};
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }

    @Override
    public PetOwner getPetOwner(String usernmae, String password) throws SQLException {
        String sql = "select * from PetOwner where name=? and password=?";
        Object[] params = new Object[]{usernmae, password};
        ResultSet resultSet = DBUtil.executeQuery(conn, sql, params);
        return DBUtil.getBeanByResultSet(resultSet, PetOwner.class);
    }

    @Override
    public int modify(PetOwner petOwner) throws SQLException {
        String sql = "UPDATE petowner SET NAME=?, PASSWORD=?, money=? WHERE id=?";
        Object[] params = new Object[]{petOwner.getName(), petOwner.getPassword(), petOwner.getMoney(), petOwner.getId()};
        return DBUtil.executeUpdate(conn, sql, params);
    }

}
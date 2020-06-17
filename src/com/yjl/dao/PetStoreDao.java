package com.yjl.dao;



import com.yjl.entity.PetStore;

import java.sql.SQLException;
import java.util.List;


public interface PetStoreDao {

    public List<PetStore> findAll() throws SQLException;
    public PetStore findById(int id) throws SQLException;
    //通过用户名和密码查找宠物商店
    public PetStore find(String name, String password) throws SQLException;

    public int add(PetStore petStore) throws SQLException;
    public int modify(PetStore petStore) throws SQLException;

    public int delete(PetStore petStore) throws SQLException;
    public int deleteById(int id) throws SQLException;
}

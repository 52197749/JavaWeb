package com.yjl.service.impl;

import com.yjl.dao.PetDao;
import com.yjl.dao.PetStoreDao;
import com.yjl.dao.impl.PetDaoImpl;
import com.yjl.dao.impl.PetStoreDaoImpl;
import com.yjl.entity.Pet;
import com.yjl.entity.PetStore;
import com.yjl.service.PetStoreService;
import com.yjl.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class PetStoreServiceImpl implements PetStoreService {

    @Override
    public List<PetStore> getAll() throws SQLException {
        Connection conn = DBUtil.getConnection();
        PetStoreDao petStoreDao = new PetStoreDaoImpl(conn);
        List<PetStore> list = petStoreDao.findAll();
        return list;
    }

    @Override
    public PetStore login(String name, String password) throws SQLException {
        Connection conn = DBUtil.getConnection();
        PetStoreDao petStoreDao = new PetStoreDaoImpl(conn);
        PetStore petStore = petStoreDao.find(name, password);
        return petStore;
    }

    @Override
    public int add(Pet pet) throws SQLException {
        Connection conn = DBUtil.getConnection();
        //PetStoreDao petStoreDao = new PetStoreDaoImpl(conn);
        PetDao petDao = new PetDaoImpl(conn);
        return petDao.add(pet);
    }
}

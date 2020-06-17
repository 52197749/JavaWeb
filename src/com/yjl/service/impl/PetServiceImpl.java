package com.yjl.service.impl;

import com.yjl.dao.PetDao;
import com.yjl.dao.impl.PetDaoImpl;
import com.yjl.entity.Pet;
import com.yjl.service.PetService;
import com.yjl.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class PetServiceImpl implements PetService {
    @Override
    public List<Pet> getAll() throws SQLException {
        Connection conn = DBUtil.getConnection();
        //调用dao层方法，获取宠物列表
        PetDao petDao = new PetDaoImpl(conn);
        List<Pet> list = petDao.findAll();
        return list;
    }

    @Override
    public List<Pet> getPetStorePets() throws SQLException {
        Connection conn = DBUtil.getConnection();
        //调用dao层方法，获取宠物列表
        PetDao petDao = new PetDaoImpl(conn);
        List<Pet> list = petDao.findPetStorePets();
        return list;
    }

    @Override
    public Pet getPetById(int xuhao) throws SQLException {
        Connection conn = DBUtil.getConnection();
        //调用dao层方法，获取宠物列表
        PetDao petDao = new PetDaoImpl(conn);
        return petDao.findById(xuhao);
    }
}

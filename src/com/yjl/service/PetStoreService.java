package com.yjl.service;

import com.yjl.entity.Pet;
import com.yjl.entity.PetStore;

import java.sql.SQLException;
import java.util.List;


public interface PetStoreService {

    //获取所有的宠物商店信息
    public List<PetStore> getAll() throws SQLException;

    public PetStore login(String name, String password) throws SQLException;

    int add(Pet pet) throws SQLException;
}

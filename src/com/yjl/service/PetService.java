package com.yjl.service;

import com.yjl.entity.Pet;

import java.sql.SQLException;
import java.util.List;


public interface PetService {

    //获取所有的宠物信息
    public List<Pet> getAll() throws SQLException;

    //获取所有的库存宠物
    public List<Pet> getPetStorePets() throws SQLException;

    Pet getPetById(int xuhao) throws SQLException;
}

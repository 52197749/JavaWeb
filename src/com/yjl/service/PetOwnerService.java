package com.yjl.service;

import com.yjl.entity.Pet;
import com.yjl.entity.PetOwner;
import com.yjl.util.PageUtil;

import java.sql.SQLException;
import java.util.List;


public interface PetOwnerService {

    //查询所有的宠物主人信息
    public List<PetOwner> getAll() throws SQLException;

    //登录
    public PetOwner login(String username, String password)throws SQLException;

    //购买宠物
    public int buyPet(PetOwner petOwner, int petId) throws SQLException;

    //获取我的宠物列表
    public List<Pet> getMyPets(Integer ownerId) throws SQLException;

    //出售宠物
    boolean sellPet(PetOwner petOwner, Pet pet, int petStoreId) throws SQLException;
    public  void getPageMyPets(Integer id, PageUtil pageUtil) throws SQLException;
    public Pet findById(int id) throws SQLException;
}

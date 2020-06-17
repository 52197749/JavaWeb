package com.yjl.dao;



import com.yjl.entity.PetOwner;

import java.sql.SQLException;
import java.util.List;


public interface PetOwnerDao {

    //获取宠物主人列表
    List<PetOwner> getAllPetOwner() throws SQLException;

    //获取宠物主人列表
    PetOwner getOwnerById(int id) throws SQLException;

    //添加宠物主人
    int addPetOwner(PetOwner petOwner) throws SQLException;

    //通过用户名和密码获取用户
    PetOwner getPetOwner(String usernmae, String password) throws SQLException;

    //更新宠物主人
    public int modify(PetOwner petOwner) throws SQLException;

}

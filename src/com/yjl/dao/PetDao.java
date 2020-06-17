package com.yjl.dao;




import com.yjl.entity.Pet;

import java.sql.SQLException;
import java.util.List;

public interface PetDao {

    public List<Pet> findAll() throws SQLException;
    public Pet findById(int id) throws SQLException;

    //通过主人id获取宠物列表
    public List<Pet> findByOwnerId(int ownerId) throws SQLException;
    //分页 通过主人id获取宠物列表
    public List<Pet> findByOwnerId(int ownerId, int pageNo, int pageSize) throws SQLException;

    //获取库存宠物
    public List<Pet> findPetStorePets() throws SQLException;

    public int add(Pet pet) throws SQLException;
    public int modify(Pet pet) throws SQLException;

    public int delete(Pet pet) throws SQLException;
    public int deleteById(int id) throws SQLException;
    //获取宠物主人的宠物条数
    public int getCount(int ownerId) throws SQLException;


}

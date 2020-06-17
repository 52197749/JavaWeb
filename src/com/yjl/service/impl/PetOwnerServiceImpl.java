package com.yjl.service.impl;


import com.yjl.dao.AccountDao;
import com.yjl.dao.PetDao;
import com.yjl.dao.PetOwnerDao;
import com.yjl.dao.PetStoreDao;
import com.yjl.dao.impl.AccountDaoImpl;
import com.yjl.dao.impl.PetDaoImpl;
import com.yjl.dao.impl.PetOwnerDaoImpl;
import com.yjl.dao.impl.PetStoreDaoImpl;
import com.yjl.entity.Account;
import com.yjl.entity.Pet;
import com.yjl.entity.PetOwner;
import com.yjl.entity.PetStore;
import com.yjl.service.PetOwnerService;
import com.yjl.util.DBUtil;
import com.yjl.util.PageUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class PetOwnerServiceImpl implements PetOwnerService {

    @Override
    public List<PetOwner> getAll() throws SQLException {
        Connection conn = DBUtil.getConnection();
        PetOwnerDao petOwnerDao = new PetOwnerDaoImpl(conn);
        List<PetOwner> list = petOwnerDao.getAllPetOwner();
        return list;
    }

    @Override
    public PetOwner login(String username, String password) throws SQLException {
        Connection conn = DBUtil.getConnection();
        PetOwnerDao petOwnerDao = new PetOwnerDaoImpl(conn);
        PetOwner petOwner = petOwnerDao.getPetOwner(username, password);
        return petOwner;
    }

    @Override
    public int buyPet(PetOwner petOwner, int petId) throws SQLException {

        Connection conn = DBUtil.getConnection();
        conn.setAutoCommit(false);
        PetDao petDao = new PetDaoImpl(conn);
        Pet pet = petDao.findById(petId);
        pet.setOwner_id(petOwner.getId());
        int count = petDao.modify(pet);
        if(petOwner.getMoney() - 5 < 0){
            throw new SQLException("元宝数不足。");
        }
        petOwner.setMoney(petOwner.getMoney() - 5);
        PetOwnerDao petOwnerDao = new PetOwnerDaoImpl(conn);
        petOwnerDao.modify(petOwner);
        PetStoreDao petStoreDao = new PetStoreDaoImpl(conn);
        Integer store_id = pet.getStore_id();
        PetStore petStore = petStoreDao.findById(store_id);
        petStore.setBalance(petStore.getBalance()+5);
        petStoreDao.modify(petStore);
        AccountDao accountDao = new AccountDaoImpl(conn);
        Account account = new Account();
        account.setDeal_type(1);
        account.setPet_id(petId);
        account.setSeller_id(petStore.getId());
        account.setBuyer_id(petOwner.getId());
        account.setPrice(5);
        account.setDeal_time(new Date());
        accountDao.add(account);
        conn.commit();
        return count;
    }

    @Override
    public List<Pet> getMyPets(Integer ownerId) throws SQLException {
        Connection conn = DBUtil.getConnection();
        PetDao petDao = new PetDaoImpl(conn);
        List<Pet> pets = petDao.findByOwnerId(ownerId);
        return pets;
    }

    @Override
    public boolean sellPet(PetOwner petOwner, Pet pet, int petStoreId) throws SQLException {

        try {
            Connection conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            PetDao petDao = new PetDaoImpl(conn);
            PetOwnerDao petOwnerDao = new PetOwnerDaoImpl(conn);
            PetStoreDao petStoreDao = new PetStoreDaoImpl(conn);
            AccountDao accountDao = new AccountDaoImpl(conn);
            pet.setOwner_id(null);
            pet.setStore_id(petStoreId);
            petDao.modify(pet);
            petOwner.setMoney(petOwner.getMoney()+5);
            petOwnerDao.modify(petOwner);
            PetStore petStore = petStoreDao.findById(petStoreId);
            petStore.setBalance(petStore.getBalance()-5);
            petStoreDao.modify(petStore);
            Account account = new Account();
            account.setDeal_type(2);
            account.setPet_id(pet.getId());
            account.setSeller_id(petOwner.getId());
            account.setBuyer_id(petStoreId);
            account.setPrice(5);
            account.setDeal_time(new Date());
            accountDao.add(account);
            conn.commit();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void getPageMyPets(Integer owner_id, PageUtil pageUtil) throws SQLException {
        Connection conn = DBUtil.getConnection();
        PetDao petDao = new PetDaoImpl(conn);
        int count = petDao.getCount(owner_id);

        int pageSize = pageUtil.getPageSize();
        int totalPageNo = count%pageSize==0?count/pageSize:count/pageSize+1;
        //对传进来的页码进行 兼容性处理
        int pageNo = pageUtil.getPageNo();
        if(pageNo<1){
            pageNo=1;
        }
        if(pageNo>totalPageNo){
            pageNo=totalPageNo;
        }
        pageUtil.setPageNo(pageNo);
        pageUtil.setTotalPageNo(totalPageNo);
        //获取当前页的宠物列表
        List<Pet> pets = petDao.findByOwnerId(owner_id, pageNo, pageSize);
        pageUtil.setPets(pets);
    }

    @Override
    public Pet findById(int id) throws SQLException {
        Connection conn = DBUtil.getConnection();
        PetDao petDao = new PetDaoImpl(conn);
        Pet byId = petDao.findById(id);
        return byId;
    }

}

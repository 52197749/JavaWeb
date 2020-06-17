package com.yjl.dao;




import com.yjl.entity.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {

    public List<Account> findAll() throws SQLException;
    public Account findById(int id) throws SQLException;

    public int add(Account account) throws SQLException;
    public int modify(Account account) throws SQLException;

    public int delete(Account account) throws SQLException;
    public int deleteById(int id) throws SQLException;
}

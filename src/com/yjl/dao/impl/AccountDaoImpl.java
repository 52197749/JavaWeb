package com.yjl.dao.impl;


import com.yjl.dao.AccountDao;
import com.yjl.entity.Account;
import com.yjl.util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class AccountDaoImpl implements AccountDao {

    private Connection conn;
    public AccountDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Account> findAll() throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "select * from account";
        ResultSet rs = DBUtil.executeQuery(conn, sql, null);
        List<Account> list = DBUtil.getListByResultSet(rs, Account.class);
        return list;
    }

    @Override
    public Account findById(int id) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "select * from account where id=?";
        Object[] params = new Object[]{id};
        ResultSet rs = DBUtil.executeQuery(conn, sql, params);
        Account account = DBUtil.getBeanByResultSet(rs, Account.class);
        return account;
    }

    @Override
    public int add(Account account) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "insert into account(deal_type, pet_id, seller_id, buyer_id, price, deal_time)" +
                " values (?,?,?,?,?,?)";
        Object[] params = new Object[]{
                account.getDeal_type(), account.getPet_id(), account.getSeller_id(), account.getBuyer_id(),
                account.getPrice(), account.getDeal_time()
        };
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }

    @Override
    public int modify(Account account) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "update account set deal_type=?, pet_id=?, seller_id=?, buyer_id=?, price=?, deal_time=?" +
                " where id=?";
        Object[] params = new Object[]{
                account.getDeal_type(), account.getPet_id(), account.getSeller_id(), account.getBuyer_id(),
                account.getPrice(), account.getDeal_time(), account.getId()
        };
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }

    @Override
    public int delete(Account account) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "delete from account where id=?";
        Object[] params = new Object[]{account.getId()};
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }

    @Override
    public int deleteById(int id) throws SQLException {
//        Connection conn = DBUtil.getConnection();
        String sql = "delete from account where id=?";
        Object[] params = new Object[]{id};
        int count = DBUtil.executeUpdate(conn, sql, params);
        return count;
    }
}

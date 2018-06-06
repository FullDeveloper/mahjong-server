package com.chess.mahjong.gameserver.service;

import com.chess.mahjong.persistent.daoimpl.AccountDaoImpl;
import com.chess.mahjong.persistent.entity.Account;
import com.chess.mahjong.persistent.entity.AccountExample;
import com.chess.mahjong.persistent.mapper.AccountMapper;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 16:47
 * Description:
 */
public class AccountService {

    private AccountMapper accMap;

    private static AccountService accountService = new AccountService();

    public static AccountService getInstance(){
        return accountService;
    }

    public void initSetSession(SqlSessionFactory sqlSessionFactory){
        accMap = new AccountDaoImpl(sqlSessionFactory);
    }

    public Account selectAccount(String openId) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andOpenIdEqualTo(openId);
        try {
            List<Account> accounts = accMap.selectByExample(accountExample);
            if(accounts!=null && accounts.size()>0){
                return accounts.get(0);
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建新用户
     * @param
     * @return 插入信息表中id
     * @throws SQLException
     */
    public int selectMaxId() throws SQLException{
        int index = accMap.selectMaxId();
        System.out.println("-account selectMaxId index->>" + index);
        return index;
    }

    /**
     * 创建新用户
     * @param account
     * @return 插入信息表中id
     * @throws SQLException
     */
    public int createAccount(Account account) {
        int index = accMap.insertSelective(account);
        //System.out.println("-account insert index->>" + index);
        return index;
    }
}

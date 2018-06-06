package com.chess.mahjong.persistent.daoimpl;

import com.chess.mahjong.persistent.entity.Account;
import com.chess.mahjong.persistent.entity.AccountExample;
import com.chess.mahjong.persistent.mapper.AccountMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 16:44
 * Description:
 */
public class AccountDaoImpl implements AccountMapper {

    private SqlSessionFactory sqlSessionFactory;

    public AccountDaoImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public int countByExample(AccountExample example) {
        return 0;
    }

    public int deleteByExample(AccountExample example) {
        return 0;
    }

    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    public int insert(Account record) {
        return 0;
    }

    public int insertSelective(Account record) {
        int flag = 0;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccountMapper mapper = null;
        try {
            mapper = sqlSession.getMapper(AccountMapper.class);
            flag = mapper.insertSelective(record);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //昵称出问题
            try {
                record.setNickname(filterNickName(record.getNickname()));
                flag = mapper.insertSelective(record);
                sqlSession.commit();
            } catch (Exception e2) {
                e.printStackTrace();
                record.setNickname("???????");
                flag = mapper.insertSelective(record);
                sqlSession.commit();
            }
        }finally {
            sqlSession.close();
        }
        return flag;
    }

    public List<Account> selectByExample(AccountExample example) {
        List<Account> result = null;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            AccountMapper mapper = sqlSession.getMapper(AccountMapper.class);
            result = mapper.selectByExample(example);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return result;
    }

    public Account selectByPrimaryKey(Integer id) {
        return null;
    }

    public int updateByExampleSelective(Account record, AccountExample example) {
        return 0;
    }

    public int updateByExample(Account record, AccountExample example) {
        return 0;
    }

    public int updateByPrimaryKeySelective(Account record) {
        return 0;
    }

    public int updateByPrimaryKey(Account record) {
        return 0;
    }

    public int selectMaxId() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccountMapper mapper = sqlSession.getMapper(AccountMapper.class);
        return mapper.selectMaxId();
    }

    /**
     *
     * @param nickname
     * @return String
     */
    public String filterNickName(String nickname){
        String reg = "[^\u4e00-\u9fa5]";
        nickname = nickname.replaceAll(reg, "?");
        return nickname;
    }

}

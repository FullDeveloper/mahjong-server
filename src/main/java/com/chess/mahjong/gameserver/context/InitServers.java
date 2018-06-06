package com.chess.mahjong.gameserver.context;

import com.chess.mahjong.gameserver.service.AccountService;
import com.chess.mahjong.gameserver.service.NoticeTableService;
import com.chess.mahjong.gameserver.service.RoomInfoService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/4 0004
 * Time: 16:49
 * Description:
 */
public class InitServers {

    public void initServersFun() throws IOException {
        Reader reader = Resources.getResourceAsReader("myBatisConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        AccountService.getInstance().initSetSession(sessionFactory);
        NoticeTableService.getInstance().initSetSession(sessionFactory);
        RoomInfoService.getInstance().initSetSession(sessionFactory);
    }

    private static InitServers initServers = new InitServers();

    public static InitServers getInstance(){
        return initServers;
    }

}

package com.chess.mahjong.gameserver.net;

import com.chess.mahjong.gameserver.net.codec.GameProtocolCodecFactory;
import org.apache.mina.core.filterchain.DefaultIoFilterChain;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @Author: 周润斌
 * @Date: create in 下午 6:04 2018-03-01
 * @Description:
 */
public class NetManager {

    private NioSocketAcceptor acceptor;


    /**
     * 开始监听端口 前端
     * @param ioHandler
     * @param listenerPort
     */
    public void startListener(IoHandler ioHandler,int listenerPort) throws IOException {
        acceptor = new NioSocketAcceptor();
        //最大连接数限制
        acceptor.setBacklog(1000);
        //重新使用地址
        acceptor.setReuseAddress(true);
        acceptor.setHandler(ioHandler);

        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        IoFilter protocol = new ProtocolCodecFilter(new GameProtocolCodecFactory());
        chain.addLast("codec",protocol);
        chain.addLast("ThreadPool",new ExecutorFilter(Executors.newCachedThreadPool()));
        int receiveSize = 1024*1024*2;  //接受大小
        int sendSize = 1024*1024*2; //发送大小
        int timeout = 10;
        SocketSessionConfig sessionConfig = acceptor.getSessionConfig();
        sessionConfig.setReuseAddress(true);// 设置每一个非主监听连接的端口可以重用
        sessionConfig.setReceiveBufferSize(receiveSize);// 设置输入缓冲区的大小
        sessionConfig.setSendBufferSize(sendSize);// 设置输出缓冲区的大小
        sessionConfig.setReadBufferSize(receiveSize);
        sessionConfig.setTcpNoDelay(true);// flush函数的调用 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
        sessionConfig.setSoLinger(0);
        //设置超时
        sessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, timeout);
        acceptor.bind(new InetSocketAddress(listenerPort));

    }

    /**
     * 开始监听端口 后台
     * @param ioHandler
     * @param listenerPort
     * @throws IOException
     */
    public void startHostListener(IoHandler ioHandler,int listenerPort) throws IOException {
        acceptor = new NioSocketAcceptor();
        //最大连接数限制
        acceptor.setBacklog(10);
        //重新使用地址
        acceptor.setReuseAddress(true);
        acceptor.setHandler(ioHandler);

        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        IoFilter protocol = new ProtocolCodecFilter(new GameProtocolCodecFactory());
        chain.addLast("codec", protocol);
        chain.addLast("ThreadPool",new ExecutorFilter(Executors.newCachedThreadPool()));

        int receiveSize = 1024*1024*2;  //接受大小
        int sendSize = 1024*1024*2; //发送大小
        int timeout = 10;
        SocketSessionConfig sessionConfig = acceptor.getSessionConfig();
        sessionConfig.setReuseAddress(true);// 设置每一个非主监听连接的端口可以重用
        sessionConfig.setReceiveBufferSize(receiveSize);// 设置输入缓冲区的大小
        sessionConfig.setSendBufferSize(sendSize);// 设置输出缓冲区的大小
        sessionConfig.setReadBufferSize(receiveSize);
        sessionConfig.setTcpNoDelay(true);// flush函数的调用 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
        sessionConfig.setSoLinger(0);
        //设置超时
        sessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, timeout);
        acceptor.bind(new InetSocketAddress(listenerPort));
    }

    public void stop(){
        acceptor.dispose(true);
    }

}

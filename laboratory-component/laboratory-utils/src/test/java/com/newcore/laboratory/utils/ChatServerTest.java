package com.newcore.laboratory.utils;

import org.junit.Test;

/**
 * 聊天服务端测试类
 */
public class ChatServerTest {

    /**
     * 启动聊天服务端
     */
    @Test
    public void test_start(){
        ChatServer server = ChatServer.getChatServerObject();
        server.start();
    }
}

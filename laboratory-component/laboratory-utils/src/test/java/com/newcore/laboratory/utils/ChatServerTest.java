package com.newcore.laboratory.utils;

import org.junit.Test;

public class ChatServerTest {

    /**
     * 启动聊天服务端
     */
    @Test
    public void test_start(){
        ChatServer chatServerObject = ChatServer.getChatServerObject();
        chatServerObject.start();
    }
}

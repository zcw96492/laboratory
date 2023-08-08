package com.newcore.laboratory.utils;

import org.junit.Test;

/**
 * 聊天客户端测试类
 * @author zhouchaowei
 */
public class ChatClientTest {

    /**
     * 启动聊天客户端
     */
    @Test
    public void test_start(){
        ChatClient client = ChatClient.getChatClientObject();
        client.start();
    }
}

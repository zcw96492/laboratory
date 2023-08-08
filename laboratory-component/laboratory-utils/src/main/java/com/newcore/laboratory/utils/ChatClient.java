package com.newcore.laboratory.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 实现简易聊天服务【客户端】
 * @author zhouchaowei
 */
public class ChatClient {

    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);

    private static ChatClient client = new ChatClient();

    /** 从服务器接收消息的缓冲字符输入流 */
    BufferedReader reader;

    /** 向服务器发送消息的打印输出流 */
    PrintWriter writer;

    /** 套接字连接 */
    Socket socket;

    /** 构造方法私有化 */
    private ChatClient(){}

    /**
     * 单例获取ChatClient对象
     * @return 聊天客户端ChatClient对象
     */
    public synchronized static ChatClient getChatClientObject(){
        if(client == null){
            client = new ChatClient();
        }
        return client;
    }

    /**
     * 启动聊天客户端方法
     */
    public void start() {
        try {
            /* 1.连接到服务器 */
            socket = new Socket("localhost", 5000);
            InputStreamReader isReader = new InputStreamReader(socket.getInputStream());

            /* 2.创建从服务器读取数据的缓冲字符输入流 */
            reader = new BufferedReader(isReader);

            /* 3.创建向服务器发送消息的打印输出流 */
            writer = new PrintWriter(socket.getOutputStream());

            /* 4.处理从服务器接收到的消息的线程 */
            Thread readerThread = new Thread(new ServerHandler());
            readerThread.start();

            /* 5.循环处理发送所有消息 */
            while (true) {
                /* 5.1.获取用户输入的消息 */
                String message = getUserInput();
                /* 5.2.发送消息给服务器 */
                sendMessageToServer(message);
            }
        } catch (IOException e) {
            logger.error("客户端连接失败!",e);
        }
    }

    /**
     * 从服务器接收到新消息时的处理
     * @param message 消息
     */
    public void handleMessage(String message) {
        logger.info(message); // 将消息输出到控制台
    }

    /**
     * 获取用户输入
     */
    public String getUserInput() {
        String inputLine = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            inputLine = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputLine;
    }

    /**
     * 将消息发送给服务器
     */
    public void sendMessageToServer(String message) {
        writer.println(message); // 发送消息给服务器
        writer.flush(); // 清空打印输出流
    }

    /**
     * 处理从服务器接收到的消息的线程
     */
    class ServerHandler implements Runnable {
        String message;

        @Override
        public void run() {
            try {
                while ((message = reader.readLine()) != null) { // 一直等待从服务器接收到消息
                    handleMessage(message); // 处理从服务器接收到的新消息
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

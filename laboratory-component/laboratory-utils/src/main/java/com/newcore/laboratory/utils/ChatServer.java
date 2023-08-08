package com.newcore.laboratory.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 实现简易聊天服务【服务端】
 * @author zhouchaowei
 */
public class ChatServer {

    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    /* 所有已连接客户端输出流的列表 */
    ArrayList<PrintWriter> clientOutputStreams;

    /** 单例对象 */
    private static ChatServer server = new ChatServer();

    /** 构造方法私有化 */
    private ChatServer(){}

    /**
     * 单例获取ChatServer对象
     * @return 聊天服务端ChatServer对象
     */
    public synchronized static ChatServer getChatServerObject(){
        if(server == null){
            server = new ChatServer();
        }
        return server;
    }

    /**
     * 启动聊天服务端方法
     */
    public void start() {
        clientOutputStreams = new ArrayList<>(); // 创建客户端输出流列表
        try {
            /* 1.创建服务器套接字并监听端口 */
            ServerSocket serverSocket = new ServerSocket(5000);
            /* 2.一直监听连接请求 */
            while (true) {
                /* 3.等待客户端连接 */
                Socket socket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                /* 4.添加客户端输出流到列表中 */
                clientOutputStreams.add(writer);
                /* 5.处理客户端线程 */
                Thread clientThread = new Thread(new ClientHandler(socket));
                clientThread.start();
                logger.info("获取客户端连接成功!");
            }
        } catch (IOException e) {
            logger.error("服务端启动失败!",e);
        }
    }

    /**
     * 处理从客户端接收到的消息
     */
    public void handleMessage(String message) {
        for (PrintWriter writer : clientOutputStreams) {
            /* 将消息发送给所有客户端 */
            writer.println(message);
            /* 发送完毕后刷新客户端 */
            writer.flush();
        }
    }

    /**
     * 内部类:用来处理客户端连接的线程
     * 【由于大多数情况下具有多个客户端,因此服务端需要多线程处理来自客户端的消息】
     */
    class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket socket;

        /**
         * 客户端代理
         * @param clientSocket 客户端套接字
         */
        public ClientHandler(Socket clientSocket) {
            try {
                socket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                /* 创建从客户端读取消息的缓冲字符输入流 */
                reader = new BufferedReader(isReader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            try {
                /* 一直接收从客户端的消息 */
                while ((message = reader.readLine()) != null) {
                    logger.info("收到消息:{}",message);
                    /* 将消息发送给所有客户端 */
                    handleMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

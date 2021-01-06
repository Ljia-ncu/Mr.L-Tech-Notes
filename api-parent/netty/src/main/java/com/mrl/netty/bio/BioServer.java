package com.mrl.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @ClassName: BioServer
 * @Description
 * @Author Mr.L
 * @Date 2021/1/6 20:14
 * @Version 1.0
 */
public class BioServer {

    private final static ThreadPoolExecutor service = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() + 1,
            5, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200), r -> new Thread(r,"server-pool-thread")
    );

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            Socket socket;
            while (true) {
                //ServerSocket开启后，阻塞在accept方法上
                //当客户端连接后，就开辟一个线程，然后通过套接口进行读写
                socket = serverSocket.accept();

                //BIO 单独开辟一条线程
                //new Thread(new ServerHeart(socket)).start();

                //伪异步IO 提交到线程池
                service.submit(new ServerHeart(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

    private static class ServerHeart implements Runnable {

        private final Socket socket;

        public ServerHeart(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
                String content;
                while (true) {
                    content = reader.readLine();
                    if (content == null) {
                        break;
                    }
                    System.out.println("server received:" + content);
                    writer.println("i'm ok!");
                }
            } catch (IOException e) {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}

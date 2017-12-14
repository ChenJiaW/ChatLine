package 仲恺聊天室;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        int clientNo = 0;  //服务器编号
        ServerSocket server = new ServerSocket(8888);
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<User> list = new ArrayList<>();

        try {
            System.out.println("服务器启动，开始监听客户的请求...");
            while(true) {
                Socket socket = server.accept();
                User user = new User("游客",clientNo,socket);
                list.add(user);
                System.out.println("接受客户" + clientNo+1 + ":" +
                        socket.getInetAddress() + "的连接请求.");
                exec.execute(new SingleServer(socket,clientNo,list));  //
                clientNo++;
            }
        } finally {
            server.close();
        }

    }


}





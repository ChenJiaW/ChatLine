package 仲恺聊天室;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/*
*   测试更新是否成功
*
*/

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8888);
        try {
            DataInputStream in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(
                    new DataOutputStream(socket.getOutputStream()));

            String ClientTest = "";
            do {
                System.out.println("请输入你要进行的操作：");
                System.out.println("-name 查询姓名");
                System.out.println("-pvp  其他玩家");
                System.out.println("-get  接收状态");
                System.out.println("-exit 退出聊天");
                Scanner sc = new Scanner(System.in);
                ClientTest = sc.next();
                out.writeUTF(ClientTest);
                out.flush();
                switch (ClientTest) {
                    case "-name":
                        getClientName(in);
                        out.writeInt(1);
                        out.flush();
                        break;
                    case "-pvp":
                        playWithOther(in,out);
                        out.writeInt(1);
                        out.flush();
                        break;
                    case "-get":
                        playWithMe(in,out);
                        out.writeInt(1);
                        out.flush();
                        break;
                    case "-exit":
                        out.writeInt(0);
                        out.flush();
                        break;
                }
            } while (!"-exit".equalsIgnoreCase(ClientTest));

        } finally {
            socket.close();
            System.out.println("退出通信！");
        }
    }

    public static void getClientName(DataInputStream in) throws IOException {
        int length,no;
        String ClientText;

        length = in.readInt();

        for (int i = 0; i < length; i++) {
            no = in.readInt();
            ClientText = in.readUTF();
            System.out.println(no + "..." + ClientText);
        }

    }

    public static void playWithMe(DataInputStream in,DataOutputStream out) throws IOException {
        System.out.println("当前处于接收状态：");
        String clientGetText;
        String clientSentText;
        Scanner sc = new Scanner(System.in);
        do {
            clientGetText = in.readUTF();
            System.out.println(clientGetText);
            clientSentText = sc.next();
            out.writeUTF(clientSentText);
            out.flush();
        } while(!"-exit".equalsIgnoreCase(clientGetText) || !"-exit".equalsIgnoreCase(clientSentText));
    }

    public static void playWithOther(DataInputStream in,DataOutputStream out) throws IOException {
        System.out.println("请输入你想要通信的人的编号：");
        Scanner sc = new Scanner(System.in);
        String clientGetTest,clientSentTest;
        int temp = sc.nextInt();
        out.writeInt(temp);
        out.flush();

        do {
            clientSentTest = sc.next();
            out.writeUTF(clientSentTest);
            out.flush();
            clientGetTest = in.readUTF();
            System.out.println(clientGetTest);
        } while(!"-exit".equalsIgnoreCase(clientGetTest) || !"-exit".equalsIgnoreCase(clientSentTest));
    }

}

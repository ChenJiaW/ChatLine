package 仲恺聊天室;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SingleServer implements Runnable{
    private Socket socket;
    private int clientNo;
    private ArrayList<User> list;

    public SingleServer(Socket socket,int clientNo,ArrayList<User> list) {
        this.socket = socket;
        this.clientNo = clientNo;
        this.list = list;
    }

    public void run() {
        try {
            DataInputStream in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(socket.getOutputStream()));
            User user = list.get(clientNo);
            user.setIn(in);
            user.setOut(out);

            do {
                String ServerText = in.readUTF();
                switch (ServerText) {
                    case "-name":
                        getClientName(list,out);
                        break;
                    case "-get":
                        break;
                    case "-pvp":
                        playWithPlayer(list,in,out);
                        break;
                    case "-exit":
                        break;

                }

            } while(in.readInt() != 0);
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            System.out.println("与客户" + clientNo+1 + "通信结束！");
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("与客户" + clientNo+1 + "通信的socket未能正确关闭！");
            }
        }
    }

    public void getClientName(ArrayList<User> list, DataOutputStream out) throws IOException {
        int length = list.size();
        out.writeInt(length);
        out.flush();
        for (User user : list) {
            int no = user.getNo();
            String name = user.getName();
            out.writeInt(no);
            out.flush();
            out.writeUTF(name);
            out.flush();
        }
    }

    public void playWithPlayer(ArrayList<User> list, DataInputStream in, DataOutputStream out) throws IOException {
        int clientNo = in.readInt();
        String getTest,sentTest;
        User user = list.get(clientNo);
        DataInputStream in1 = user.getIn();
        DataOutputStream out1 = user.getOut();

        do {
            getTest = in.readUTF();
            out1.writeUTF(getTest);
            out1.flush();
            sentTest = in1.readUTF();
            out.writeUTF(sentTest);
            out.flush();
        } while(true);
    }
}

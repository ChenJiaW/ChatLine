package 仲恺聊天室;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class User {
    private String name;    //游客名称
    private int no;         //游客编号
    private Socket socket;  //特定的socket
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public User() {}
    public User(String name,int no,Socket socket) {
        this.name = name;
        this.no = no;
        this.socket = socket;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public int getNo() {
        return no;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public Socket getSocket() {
        return socket;
    }
    public void setIn(DataInputStream in) {
        this.in = in;
    }
    public DataInputStream getIn() {
        return in;
    }
    public void setOut(DataOutputStream out) {
        this.out = out;
    }
    public DataOutputStream getOut() {
        return out;
    }
}

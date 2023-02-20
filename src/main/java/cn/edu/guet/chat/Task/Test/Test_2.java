package cn.edu.guet.chat.Task.Test;

import java.io.IOException;

public class Test_2 {
    public static void main(String[] args) throws IOException {
        Send send = new Send("localhost", 8887);
        Receive server = new Receive(8888);
        new Thread(server).start();
        new Thread(send).start();
    }
}
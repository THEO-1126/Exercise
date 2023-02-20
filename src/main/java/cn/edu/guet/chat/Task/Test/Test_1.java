package cn.edu.guet.chat.Task.Test;

import java.io.IOException;

public class Test_1 {
    public static void main(String[] args) throws IOException {
        Send send = new Send("localhost", 8888);
        Receive server = new Receive(8887);
        new Thread(server).start();
        new Thread(send).start();
    }
}
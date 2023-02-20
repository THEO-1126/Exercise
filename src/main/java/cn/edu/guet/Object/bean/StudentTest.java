package cn.edu.guet.Object.bean;

import java.io.*;

public class StudentTest {
    private static Object Student;

    public static void main(String[] args) {
        ObjectOutputStream oos=null;
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream("student");
            oos=new ObjectOutputStream(fos);
            oos.writeObject(Student);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

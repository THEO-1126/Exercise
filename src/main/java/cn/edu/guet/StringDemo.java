package cn.edu.guet;

public class StringDemo {
    public static void main(String[] args) {
        String s1="hello"; // 1个
        String s2=s1.substring(2,3); // 1个  返回一个新字符串
        String s3=s1.toString(); //不产生对象 toString返回对象本身 s3和s1为同一个对象
        String s4=new StringBuffer(s1).toString(); // 1个
    }
}

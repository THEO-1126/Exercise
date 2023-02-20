package cn.edu.guet;

import java.util.HashSet;
import java.util.Objects;

public class ColDemo {
    public static void main(String[] args) {
        HashSet<Student> set=new HashSet<Student>();
        Student stu1=new Student("1900710305","李冰冰");
        Student stu2=new Student("1900710305","李冰冰");
        set.add(stu1);
        set.add(stu2);
        System.out.println(set.size());
    }
}

class Student{
    String stuno;
    String name;
    Student(String stuno,String name){
        this.stuno=stuno;
        this.name=name;
    }

    public Student() {
        super();
    }

    public String getStuno() {
        return stuno;
    }

    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStuno(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getStuno().equals(student.getStuno()) && getName().equals(student.getName());
    }
}
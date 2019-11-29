package com.example.attendance;

public class Student {
    String id;
    String name;
    String dept;
    String section;

    public Student() {
    }

    public Student(String id, String name, String dept, String section) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.section = section;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getSection() {
        return section;
    }
}

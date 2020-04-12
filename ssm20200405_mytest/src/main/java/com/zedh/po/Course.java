package com.zedh.po;

public class Course {
    private int cid;
    private String cname;
    private String clocal;

    public Course() {
    }

    public Course(int cid, String cname, String clocal) {
        this.cid = cid;
        this.cname = cname;
        this.clocal = clocal;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getClocal() {
        return clocal;
    }

    public void setClocal(String clocal) {
        this.clocal = clocal;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", clocal='" + clocal + '\'' +
                '}';
    }
}

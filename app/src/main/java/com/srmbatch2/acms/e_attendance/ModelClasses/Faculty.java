package com.srmbatch2.acms.e_attendance.ModelClasses;

public class Faculty {

    public String name, subject, batch, count;


    public Faculty() {

    }

    public Faculty(String name, String subject, String batch) {
        this.name = name;
        this.subject = subject;
        this.batch = batch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
       return subject + "\nFaculty:" + name + "    Batch:" + batch;
    }

    public String toStringBatch() {
        return batch;
    }

    public String toStringSubject() {
        return subject;
    }

    public String toStringFaculty() {
        return name;
    }
}

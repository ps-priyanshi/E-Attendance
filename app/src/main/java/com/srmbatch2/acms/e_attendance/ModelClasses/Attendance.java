package com.srmbatch2.acms.e_attendance.ModelClasses;

public class Attendance {

    public String studentAtten, date, time, subjectAtten;

    public Attendance() {
    }

    public Attendance(String nameAtten, String date, String time, String subjectAtten) {
        this.studentAtten = nameAtten;
        this.date = date;
        this.time = time;
        this.subjectAtten = subjectAtten;
    }

    public String getStudentAtten() {
        return studentAtten;
    }

    public void setNameAtten(String nameAtten) {
        this.studentAtten = nameAtten;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubjectAtten() {
        return subjectAtten;
    }

    public void setSubjectAtten(String subjectAtten) {
        this.subjectAtten = subjectAtten;
    }
}

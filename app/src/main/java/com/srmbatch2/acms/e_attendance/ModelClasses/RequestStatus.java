package com.srmbatch2.acms.e_attendance.ModelClasses;

public class RequestStatus {

    public String studentName, studentRegNo, status, studentFaculty, studentSubject;

    public RequestStatus() {
    }

    public RequestStatus(String studentName, String studentRegNo, String status, String studentFaculty, String studentSubject) {
        this.studentName = studentName;
        this.studentRegNo = studentRegNo;
        this.status = status;
        this.studentFaculty = studentFaculty;
        this.studentSubject = studentSubject;
    }

    public String getStudentFaculty() {
        return studentFaculty;
    }

    public void setStudentFaculty(String studentFaculty) {
        this.studentFaculty = studentFaculty;
    }

    public String getStudentSubject() {
        return studentSubject;
    }

    public void setStudentSubject(String studentSubject) {
        this.studentSubject = studentSubject;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRegNo() {
        return studentRegNo;
    }

    public void setStudentRegNo(String studentRegNo) {
        this.studentRegNo = studentRegNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toStringStudentName() {return studentName; }

    public String toStringStudentRegNo() {return studentRegNo; }

    public String toStringStatus() {return status; }
}

package com.srmbatch2.acms.e_attendance.ModelClasses;

public class UserSubjects {

    public String subjectID, facultyUser, subjectUser,attendanceUser, typeUser;

    public UserSubjects() {

    }

    public UserSubjects(String subjectID, String facultyUser, String subjectUser, String attendanceUser, String typeUser) {
        this.subjectID = subjectID;
        this.facultyUser = facultyUser;
        this.subjectUser = subjectUser;
        this.attendanceUser = attendanceUser;
        this.typeUser = typeUser;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getFacultyUser() {
        return facultyUser;
    }

    public void setFacultyUser(String facultyUser) {
        this.facultyUser = facultyUser;
    }

    public String getSubjectUser() {
        return subjectUser;
    }

    public void setSubjectUser(String subjectUser) {
        this.subjectUser = subjectUser;
    }


    public String getAttendanceUser() {
        return attendanceUser;
    }

    public void setAttendanceUser(String attendanceUser) {
        this.attendanceUser = attendanceUser;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public String toStringAttendanceUser() {
        return attendanceUser;
    }

    public String toStringSubjectID() {
        return subjectID;
    }
}

package com.srmbatch2.acms.e_attendance.ModelClasses;

public class Student {

    public String keySubject, nameStudent, regNoStudent, facultyStudent, subjectStudent, attendedClass, type;


    public Student(){

    }

    public Student(String keySubject, String nameStudent, String regNoStudent, String facultyStudent, String subjectStudent, String attendedClass, String type) {
        this.keySubject = keySubject;
        this.nameStudent = nameStudent;
        this.regNoStudent = regNoStudent;
        this.facultyStudent = facultyStudent;
        this.subjectStudent = subjectStudent;
        this.attendedClass = attendedClass;
        this.type = type;
    }

    public String getKeySubject() {
        return keySubject;
    }

    public void setKeySubject(String keySubject) {
        this.keySubject = keySubject;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getRegNoStudent() {
        return regNoStudent;
    }

    public void setRegNoStudent(String regNoStudent) {
        this.regNoStudent = regNoStudent;
    }

    public String getFacultyStudent() {
        return facultyStudent;
    }

    public void setFacultyStudent(String facultyStudent) {
        this.facultyStudent = facultyStudent;
    }

    public String getSubjectStudent() {
        return subjectStudent;
    }

    public void setSubjectStudent(String subjectStudent) {
        this.subjectStudent = subjectStudent;
    }

    public String getAttendedClass() {
        return attendedClass;
    }

    public void setAttendedClass(String attendedClass) {
        this.attendedClass = attendedClass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return  nameStudent + ", " + regNoStudent + ", " + facultyStudent + ", " + subjectStudent +
                ", " + attendedClass;
    }

    public String toStringNameStudent() {
        return nameStudent;
    }

    public String toStringregNoStudent() {
        return regNoStudent;
    }

    public String toStringfacultyStudent() {
        return facultyStudent;
    }

    public String toStringsubjectStudent() {
        return subjectStudent;
    }

    public String toStringattendedClass() {
        return attendedClass;
    }

    public String toStringkeySubject() {
        return keySubject;
    }

    public String toStringData() { return subjectStudent + "\nFaculty : " + facultyStudent + "   Attendance : " +attendedClass + "%"; }
}

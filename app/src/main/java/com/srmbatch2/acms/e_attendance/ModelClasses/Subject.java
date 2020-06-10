package com.srmbatch2.acms.e_attendance.ModelClasses;

public class Subject {

    public String nameSub, facultySub, students;

    public Subject() {

    }

    public Subject(String nameSub, String facultySub, String students) {
        this.nameSub = nameSub;
        this.facultySub = facultySub;
        this.students = students;
    }

    public String getNameSub() {
        return nameSub;
    }

    public void setNameSub(String nameSub) {
        this.nameSub = nameSub;
    }

    public String getFacultySub() {
        return facultySub;
    }

    public void setFacultySub(String facultySub) {
        this.facultySub = facultySub;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return nameSub + ", " + facultySub + ", " + students;
    }

    public String toStringNameSub() {
        return nameSub;
    }

    public String toStringFacultySub() {
        return facultySub;
    }

    public String toStringStudents() {
        return students;
    }


}

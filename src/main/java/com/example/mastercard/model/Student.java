package com.example.mastercard;

public class Student {
    private String student_id;
    private String subject;
    private Double total_percentage;
    private String grade;
    private  String term;
    private String class_and_section;
    private String submitted_by;

    //Constructors
    public Student() {}
    public Student(String student_id, String subject, Double total_percentage, String grade, String term, String class_and_section, String submitted_by)
    {
        this.student_id = student_id;
        this.subject = subject;
        this.total_percentage = total_percentage;
        this.grade = grade;
        this.term = term;
        this.class_and_section = class_and_section;
        this.submitted_by = submitted_by;
    }

    //Getters and Setters
    public String getStudent_id() {return student_id; }
    public void setStudent_id(String student_id) {this.student_id = student_id;}

    public String getSubject() {return subject; }
    public void setSubject(String subject) {this.subject = subject;}

    public Double getTotal_percentage() {return total_percentage;}
    public void setTotal_percentage(Double total_percentage) {this.total_percentage = total_percentage;}

    public String getGrade() {return grade;}
    public void setGrade(String grade) {this.grade = grade;}

    public String getTerm() {return term;}
    public void setTerm(String term) {this.term = term;}

    public String getClass_and_section() {return class_and_section;}
    public void setClass_and_section(String class_and_section) {this.class_and_section = class_and_section;}

    public String getSubmitted_by() {return submitted_by;}
    public void setSubmitted_by(String submitted_by) {this.submitted_by = submitted_by;}

    @Override
public  String toString() {
        return "Student{" +
                "student_id='" + student_id + '\'' +
                "subject='" + subject + '\'' +
                "total_percentage='" + total_percentage + '\'' +
                "grade='" + grade + '\'' +
                "term='" + term + '\'' +
                "class_and_section'" + class_and_section + '\'' +
                "submitted_by'" + submitted_by + '\'' +
                '}';

    }
}

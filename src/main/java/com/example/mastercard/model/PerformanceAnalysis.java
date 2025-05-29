package com.example.mastercard.model;

import java.util.List;
import java.util.Map;

public class PerformanceAnalysis {
    private double overallAverage;
    private double highestScore;
    private double lowestScore;
    private int totalStudents;
    private Map<String, Long> gradeDistribution;
    private Map<String,Double> subjectAverages;
    private Map<String, Integer> subjectCounts;
    private String topPerformingSubject;
    private String lowestPerformingSubject;
    private List<Student> topPerformers;
    private List<Student> needsImprovement;
    private Map<String, List<Student>> studentByGrade;
    private String analysisDate;

    //Constructors

    public PerformanceAnalysis() {}

    //Getters and setters
    public double getOverallAverage(){return overallAverage;}
    public void setOverallAverage(double overallAverage) {this.overallAverage= overallAverage;}

    public double getHighestScore(){return highestScore;}
    public void setHighestScore(double highestScore){this.highestScore = highestScore;}

    public double getLowestScore(){return lowestScore;}
    public void setLowestScore(double lowestScore){this.lowestScore = lowestScore;}

    public int getTotalStudents() {return totalStudents;}
    public void setTotalStudents(int totalStudents){this.totalStudents = totalStudents;}

    public Map<String, Long> getGradeDistribution(){return gradeDistribution;}
    public void setGradeDistribution(Map<String, Long> gradeDistribution){this.gradeDistribution =  gradeDistribution;}

    public Map<String,Double> getSubjectAverages(){return subjectAverages;}
    public void setSubjectAverages(Map<String, Double> subjectAverages) {this.subjectAverages = subjectAverages;}

    public Map<String, Integer> getSubjectCounts(){return subjectCounts;}
    public void setSubjectCounts(Map<String,Integer> subjectCounts) {this.subjectCounts = subjectCounts;}

    public String getTopPerformingSubject() {return topPerformingSubject;}
    public void setTopPerformingSubject(String topPerformingSubject){this.topPerformingSubject = topPerformingSubject;}

    public String getLowestPerformingSubject(){return  lowestPerformingSubject;}
    public void setLowestPerformingSubject(String lowestPerformingSubject) {this.lowestPerformingSubject = lowestPerformingSubject;}

    public List<Student> getTopPerformers() {return topPerformers;}
    public void setTopPerformers(List<Student> topPerformers) {this.topPerformers = topPerformers;}

    public List<Student> getNeedsImprovement(){return needsImprovement;}
    public void setNeedsImprovement(List<Student> needsImprovement){this.needsImprovement = needsImprovement;}

    public Map<String,List<Student>> getStudentByGrade(){ return studentByGrade;}
    public void setStudentByGrade(Map<String,List<Student>> studentByGrade){this.studentByGrade = studentByGrade;}

    public String getAnalysisDate() {return analysisDate;}
    public void setAnalysisDate(String analysisDate){this.analysisDate = analysisDate;}

}

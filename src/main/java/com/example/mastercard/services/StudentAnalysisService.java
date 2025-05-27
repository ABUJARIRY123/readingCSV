package com.example.mastercard.services;

import com.example.mastercard.Student;
import com.example.mastercard.model.PerformanceAnalysis;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentAnalysisService {
    public PerformanceAnalysis analyzeStudentPerformance(List<Student> students) {
        PerformanceAnalysis analysis = new PerformanceAnalysis();
        if (students.isEmpty()) {
            return analysis;
        }
        //Basic statistics
        DoubleSummaryStatistics scoreStats=students.stream()
                .mapToDouble(Student::getTotal_percentage)
                .summaryStatistics();

        analysis.setOverallAverage(scoreStats.getAverage());
        analysis.setHighestScore(scoreStats.getMax());
        analysis.setLowestScore(scoreStats.getMin());
        analysis.setTotalStudents(students.size());

        //Grade distribution
        Map<String, Long> gradeDistribution = students.stream()
                .collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        analysis.setGradeDistribution((gradeDistribution));

        //Subject averages and counts

        Map<String, Double> subjectAverages = students.stream()
                .collect(Collectors.groupingBy(Student::getSubject,
                        Collectors.averagingDouble(Student::getTotal_percentage)));
        analysis.setSubjectAverages(subjectAverages);

        Map<String, Integer> subjectCounts =students.stream()
                .collect(Collectors.groupingBy(Student::getSubject,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        analysis.setSubjectCounts(subjectCounts);

        //Top and Lowest performing subject
        if (!subjectAverages.isEmpty()){
            String topSubject= subjectAverages.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("N/A");

            String lowestSubject = subjectAverages.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
                    .orElse("N/A");
            analysis.setTopPerformingSubject(topSubject);
            analysis.setLowestPerformingSubject(lowestSubject);
        }

        //Top peformers (top 10% or minimum 5 students)
        int topPerformerCount = Math.max(5, students.size() / 10);
        List<Student> topPerformers = students.stream()
                .sorted((s1, s2) -> Double.compare(s2.getTotal_percentage(), s1.getTotal_percentage()))
                .limit(topPerformerCount)
                .collect(Collectors.toList());
        analysis.setTopPerformers(topPerformers);

        //Students needing improvement (bottom 10% or students below 50)
        List<Student> needsImprovement = students.stream()
                .filter(s -> s.getTotal_percentage() < 50)
                .sorted(Comparator.comparing(Student::getTotal_percentage))
                .collect(Collectors.toList());
        analysis.setNeedsImprovement(needsImprovement);

        //Students grouped by grades
        Map<String, List<Student>> studentsByGrade = students.stream()
                .collect(Collectors.groupingBy(Student::getGrade));
        analysis.setStudentByGrade(studentsByGrade);

        //When was this analysis conducted
        analysis.setAnalysisDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return analysis;
    }
}

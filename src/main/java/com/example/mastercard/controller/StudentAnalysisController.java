package com.example.mastercard.controller;

import com.example.mastercard.services.StudentPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentAnalysisController {
    @Autowired
    private StudentPerformanceService studentPerformanceService;

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeStudentPerformance (
            @RequestParam String csvFilePath,
            @RequestParam(defaultValue = "C:\\Desktop\\performance") String outputDirectory){
        String result = studentPerformanceService.proccessStudentPerformance(csvFilePath, outputDirectory);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/results")
    public ResponseEntity<String> analyzedWithDefaults() {
    String defaultCsvPath = "C:\\dev\\results.csv";
    String defaultOutputPath = "C:\\Desktop\\performance";

    String result = studentPerformanceService.proccessStudentPerformance(defaultCsvPath, defaultOutputPath);
    return ResponseEntity.ok(result);
    }
}
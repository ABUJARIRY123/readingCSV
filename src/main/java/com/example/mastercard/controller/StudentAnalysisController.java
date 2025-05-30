package com.example.mastercard.controller;

import com.example.mastercard.DTO.StudentRequest;
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
    public ResponseEntity<String> analyzeStudentPerformance(@RequestBody StudentRequest request) {
        String result = studentPerformanceService.proccessStudentPerformance(
                request.getCsvFilePath(), request.getOutputDirectory());
        return ResponseEntity.ok(result);
    }


    @GetMapping("/results")
    public ResponseEntity<String> analyzedWithDefaults() {
    String defaultCsvPath = "C:\\Users\\BD\\OneDrive\\Desktop\\results_rows.csv";
    String defaultOutputPath = "C:\\Users\\BD\\OneDrive\\Desktop\\performance";

    String result = studentPerformanceService.proccessStudentPerformance(defaultCsvPath, defaultOutputPath);
    return ResponseEntity.ok(result);
    }
}
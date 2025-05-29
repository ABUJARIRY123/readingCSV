package com.example.mastercard.services;

import com.example.mastercard.model.Student;
import com.example.mastercard.model.PerformanceAnalysis;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StudentPerformanceService {
    @Autowired
    private CsvReaderService csvReaderService;

    @Autowired
    private StudentAnalysisService analysisService;

    @Autowired
    private  ExcelExportService excelExportService;

    public String proccessStudentPerformance(String csvFilePath, String outputDirectory) {
        try {
            //Step 1: Read csv file
            List<Student> students = csvReaderService.readStudentsFromCsv(csvFilePath);
            if (students.isEmpty()){
                return "No valid student records was found on the file.";
            }

            // Step 2: Analyze Performance
            PerformanceAnalysis analysis = analysisService.analyzeStudentPerformance(students);

            //Step 3: Export to Excel
            String excelFilePath = excelExportService.exportAnalysisToExcel(analysis, outputDirectory);

            return  String.format("Analysis completed successfullly!\n" +
                    "- Processed %d student records\n" +
                    "- Analysis exported to: %s\n" +
                    "- Overall average score: %.2f\n" +
                    "- Top performing subject: %s\n" +
                    "- Students needing improvement: %d",
            students.size(),
            excelFilePath,
            analysis.getOverallAverage(),
            analysis.getTopPerformingSubject(),
            analysis.getNeedsImprovement().size());
        }catch (IOException e) {
            return "Error reading the CSV file: " + e.getMessage();
        }catch (CsvException e) {
            return "Error parsing CSV file: " + e.getMessage();
        } catch (Exception e) {
            return  "Unexpected error during processing: " + e.getMessage();
        }

    }
}

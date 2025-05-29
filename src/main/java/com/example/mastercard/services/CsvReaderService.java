package com.example.mastercard.services;

import com.example.mastercard.model.Student;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {
    public List<Student> readStudentsFromCsv(String filePath) throws IOException, CsvException{
        List<Student> students =new ArrayList<>();
        Path path = Paths.get(filePath);
        if (!Files.exists(path)){
            throw new IOException("The csv file was not found at path: " + filePath);
        }
        try (CSVReader reader = new CSVReader(new FileReader(filePath))){
            List<String[]> records = reader.readAll();

            //Skip the header if it is there
            boolean isFirstRow =true;
            for (String[] record : records) {
                if (isFirstRow && isHeaderRow(record)) {
                    isFirstRow = false;
                    continue;
                }
                if (record.length >= 6) {
                    try {
                        Student student = new Student();
                        student.setStudent_id(record[0].trim());
                        student.setGrade(record[1].trim());
                        student.setSubject(record[2].trim());
                        student.setTerm(record[3].trim());
                        student.setSubmitted_by(record[4].trim());
                        student.setClass_and_section(record[5].trim());
                        student.setTotal_percentage(Double.parseDouble(record[6].trim()));
                        students.add(student);
                    } catch (NumberFormatException e) {
                        System.err.println("Am currently skipping invalid row: " + String.join(",", record));
                    }
                }
                isFirstRow = false;
            }
        }
        return students;
    }
    private boolean isHeaderRow(String[] record) {
        return record.length > 0 &&
                (record[0].toLowerCase().contains("student") ||
                 record[0].toLowerCase().contains("id") ||
                 record[0].toLowerCase().equals("student_id"));
    }
}

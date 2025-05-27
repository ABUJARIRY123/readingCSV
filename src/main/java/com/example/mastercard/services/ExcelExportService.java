package com.example.mastercard.services;

import com.example.mastercard.Student;
import com.example.mastercard.model.PerformanceAnalysis;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
public class ExcelExportService {
    public String exportAnalysisToExcel(PerformanceAnalysis analysis, String outputDirectory) throws IOException {
        //Creating output directory if it does not ecist
        Path outputPath = Paths.get(outputDirectory);
        if (!Files.exists(outputPath)){
            Files.createDirectories(outputPath);
        }

        //Generate filename with timestamp
        String fileName = "Students_Perfomance_Analysis_" +
                analysis.getAnalysisDate().replace(":", "-").replace("", "_") + ".xlsx";
        String fullPath = outputPath.resolve(fileName).toString();

        try(Workbook workbook = new XSSFWorkbook()){
            //Creating styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle numericStyle = createNumericStyle(workbook);

            //Creating summary sheet
            createdSummarySheet(workbook, analysis, headerStyle, dataStyle, numericStyle);

            //Creating grade distribution sheet
            createGradeDistributionSheet(workbook, analysis, headerStyle, dataStyle, numericStyle);

            //Creating subject analysis sheet

            createSubjectAnalysisSheet(workbook, analysis, headerStyle, dataStyle, numericStyle);

            //Creating top performers sheet
            createTopPerformersSheet(workbook, analysis, headerStyle, dataStyle, numericStyle);

            //Creating to students that needs improvement sheet

            if (!analysis.getNeedsImprovement().isEmpty()){
                createNeedsImprovementSheet(workbook, analysis, headerStyle, dataStyle, numericStyle);
            }

            try (FileOutputStream fileOut = new FileOutputStream(fullPath)){
                workbook.write(fileOut);
            }
        }
        return fullPath;
    }
    private void createdSummarySheet(Workbook workbook, PerformanceAnalysis analysis,
                                     CellStyle headerStyle, CellStyle dataStyle, CellStyle numericStyle) {
        Sheet sheet = workbook.createSheet("Summary");
        int rowNum = 0;

        //Title
        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Student Performance Analysis Summary");
        titleCell.setCellStyle(headerStyle);

        rowNum++; //Empty row

        //Analysis date
        createDataRow(sheet, rowNum++, "Analysis Date:", analysis.getAnalysisDate(), dataStyle);
        createDataRow(sheet, rowNum++, "Total Students:", String.valueOf(analysis.getTotalStudents()), dataStyle);

        rowNum++; //Empty Sheet

        //Score statistics
        createDataRow(sheet,rowNum++, "Overal Average Score:", String.format("%.2f", analysis.getOverallAverage()), numericStyle);
        createDataRow(sheet,rowNum++, "Highest Score:", String.format("%.2f", analysis.getHighestScore()), numericStyle);
        createDataRow(sheet, rowNum++, "Lowest Score:", String.format("%.2f", analysis.getLowestScore()), numericStyle);

        rowNum++;  //Empty row

        //Subject Performance
        createDataRow(sheet, rowNum++, "Top Performing Subject:", analysis.getTopPerformingSubject(), dataStyle);
        createDataRow(sheet, rowNum++, "Lowest Performing Subject:", analysis.getLowestPerformingSubject(), dataStyle);

        //Auto-size columns
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }
    private void createGradeDistributionSheet(Workbook workbook, PerformanceAnalysis analysis, CellStyle headerStyle, CellStyle dataStyle, CellStyle numericStyle) {
        Sheet sheet = workbook.createSheet("Grade Distribution");
        int rowNum = 0;

        //Headers
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Grade");
        headerRow.createCell(1).setCellValue("Count");
        headerRow.createCell(2).setCellValue("Percentage");

        for (Cell cell : headerRow) {
            cell.setCellStyle(headerStyle);
        }

        //Data
        for (Map.Entry<String, Double> entry : analysis.getSubjectAverages().entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(String.format("%.2f", entry.getValue()));
            row.createCell(2).setCellValue(analysis.getSubjectCounts().get(entry.getKey()));

            row.getCell(0).setCellStyle(dataStyle);
            row.getCell(1).setCellStyle(numericStyle);
            row.getCell(2).setCellStyle(numericStyle);
        }

        //Auto-size columns
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createSubjectAnalysisSheet(Workbook workbook, PerformanceAnalysis analysis,
                                            CellStyle headerStyle, CellStyle dataStyle, CellStyle numericStyle) {
        Sheet sheet = workbook.createSheet("Subject Analysis");

        int rowNum = 0;

        // Headers
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Subject");
        headerRow.createCell(1).setCellValue("Average Score");
        headerRow.createCell(2).setCellValue("Student Count");

        for (Cell cell : headerRow) {
            cell.setCellStyle(headerStyle);
        }

        // Data
        for (Map.Entry<String, Double> entry : analysis.getSubjectAverages().entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(String.format("%.2f", entry.getValue()));
            row.createCell(2).setCellValue(analysis.getSubjectCounts().get(entry.getKey()));

            row.getCell(0).setCellStyle(dataStyle);
            row.getCell(1).setCellStyle(numericStyle);
            row.getCell(2).setCellStyle(numericStyle);
        }

        // Auto-size columns
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createTopPerformersSheet(Workbook workbook, PerformanceAnalysis analysis, CellStyle headerStyle, CellStyle dataStyle, CellStyle numericStyle) {
        Sheet sheet = workbook.createSheet("Top Performers");
        createStudentListSheet(sheet, analysis.getTopPerformers(), headerStyle, dataStyle, numericStyle);
    }

    private void createNeedsImprovementSheet(Workbook workbook, PerformanceAnalysis analysis, CellStyle headerStyle, CellStyle dataStyle, CellStyle numericStyle) {
        Sheet sheet = workbook.createSheet("Needs Improvement");
        createStudentListSheet(sheet, analysis.getNeedsImprovement(), headerStyle, dataStyle, numericStyle);
    }
    private void createStudentListSheet(Sheet sheet, List<Student> students, CellStyle headerStyle, CellStyle dataStyle, CellStyle numericStyle) {
        int rowNum = 0;

        //Headers
        Row headerRow = sheet.createRow(rowNum++);
        String[] headers = {"Student ID", "Subject", "Score", "Grade", "Semester", "Teacher"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        //Data
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getStudent_id());
            row.createCell(1).setCellValue(student.getSubject());
            row.createCell(2).setCellValue(student.getTotal_percentage());
            row.createCell(3).setCellValue(student.getGrade());
            row.createCell(4).setCellValue(student.getTerm());
            row.createCell(5).setCellValue(student.getSubmitted_by());

            for (int i = 0; i < 6; i++) {
                if (i == 3) {//Score column
                    row.getCell(i).setCellStyle(numericStyle);
                }else {
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }
        }

        //Auto size columns
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createDataRow(Sheet sheet, int rowNum,String label, String value, CellStyle style) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(label);
        Cell valueCell = row.createCell(1);
        valueCell.setCellValue(value);
        valueCell.setCellStyle(style);
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createNumericStyle(Workbook workbook) {
        CellStyle style = createDataStyle(workbook);
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
        return style;
    }
}

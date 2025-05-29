package com.example.mastercard.DTO;

public class StudentRequest {
    private String csvFilePath;
    private String outputDirectory;

    // Constructors
    public StudentRequest() {}

    public StudentRequest(String csvFilePath, String outputDirectory) {
        this.csvFilePath = csvFilePath;
        this.outputDirectory = outputDirectory;
    }

    // Getters and Setters
    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
}

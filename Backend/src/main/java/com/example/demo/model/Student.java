package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Document(collection = "Students")
public class Student {
    @Id
    private String id;
    
    @Field("prnNo")
    @JsonProperty("PRNno")
    private String prnNo;
    private String name;
    private int semester;
    private List<Subject> subjects;

    public Student() {
    }

    public Student(String prnNo, String name, int semester, List<Subject> subjects) {
        this.prnNo = prnNo;
        this.name = name;
        this.semester = semester;
        this.subjects = subjects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrnNo() {
        return prnNo;
    }

    public void setPrnNo(String prnNo) {
        this.prnNo = prnNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public double getCGPA() {
        if (subjects == null || subjects.isEmpty()) {
            return 0.0;
        }
        double totalMarks = 0;
        for (Subject subject : subjects) {
            totalMarks += subject.getTotal();
        }
        // CGPA = total marks of all subjects / (number of subjects * 10)
        double cgpa = totalMarks / (subjects.size() * 10.0);
        return Math.round(cgpa * 100.0) / 100.0;
    }

    public String getOverallGrade() {
        double average = 0;
        if (subjects == null || subjects.isEmpty()) {
            return "N/A";
        }
        for (Subject subject : subjects) {
            average += subject.getTotal();
        }
        average = average / subjects.size();
        
        if (average >= 90) return "A+";
        if (average >= 80) return "A";
        if (average >= 70) return "B";
        if (average >= 60) return "C";
        if (average >= 50) return "D";
        return "F";
    }
}
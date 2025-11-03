package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "Students")
public class Student {
    @Id
    private String id;
    private String PRNno;
    private String name;
    private int semester;
    private List<Subject> subjects;

    public Student() {
    }

    public Student(String PRNno, String name, int semester, List<Subject> subjects) {
        this.PRNno = PRNno;
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

    public String getPRNno() {
        return PRNno;
    }

    public void setPRNno(String PRNno) {
        this.PRNno = PRNno;
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
        double averageMarks = totalMarks / subjects.size();
        return Math.round((averageMarks / 10) * 100.0) / 100.0;
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
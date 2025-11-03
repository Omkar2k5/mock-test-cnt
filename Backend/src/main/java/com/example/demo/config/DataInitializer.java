package com.example.demo.config;

import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if student already exists
        Optional<Student> existingStudent = studentRepository.findByPrnNo("12420262");
        
        if (existingStudent.isEmpty()) {
            System.out.println("Initializing Student Database...");
            
            // Create subjects
            Subject subject1 = new Subject("CC", "Cloud Computing", 30, 65);
            Subject subject2 = new Subject("CN", "Computer Network", 30, 70);
            Subject subject3 = new Subject("DAA", "Design & Analyze of Algorithm", 25, 68);
            Subject subject4 = new Subject("ANN", "Artificial Neural Network", 28, 68);

            // Create student
            Student student = new Student(
                    "12420262",
                    "Omkar Gondkar",
                    5,
                    Arrays.asList(subject1, subject2, subject3, subject4)
            );

            // Save student to MongoDB
            Student savedStudent = studentRepository.save(student);
            System.out.println("✓ Student data initialized successfully!");
            System.out.println("  PRN: " + savedStudent.getPrnNo());
            System.out.println("  Name: " + savedStudent.getName());
            System.out.println("  CGPA: " + savedStudent.getCGPA());
        } else {
            System.out.println("Student data already exists in database.");
        }
    }
}
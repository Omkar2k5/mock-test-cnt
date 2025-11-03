package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Get all students
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    /**
     * Get student by PRN number
     */
    @GetMapping("/prn/{prnNo}")
    public ResponseEntity<?> getStudentByPrnNo(@PathVariable String prnNo) {
        System.out.println("Searching for PRN: " + prnNo);
        Optional<Student> student = studentService.getStudentByPrnNo(prnNo);
        System.out.println("Student found: " + student.isPresent());
        if (student.isPresent()) {
            System.out.println("Returning student: " + student.get().getName());
            return ResponseEntity.ok(student.get());
        } else {
            System.out.println("Student not found in database");
            // Debug: Let's check all students
            List<Student> allStudents = studentService.getAllStudents();
            System.out.println("Total students in DB: " + allStudents.size());
            if (!allStudents.isEmpty()) {
                System.out.println("First student PRN: " + allStudents.get(0).getPrnNo());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"Student with PRN " + prnNo + " not found\"}");
        }
    }

    /**
     * Get student by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"Student with ID " + id + " not found\"}");
        }
    }

}
package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentByPrnNo(String prnNo) {
        return studentRepository.findByPRNno(prnNo);
    }

    public Optional<Student> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }

    public Student updateStudent(String id, Student student) {
        return studentRepository.findById(id).map(existingStudent -> {
            if (student.getName() != null) {
                existingStudent.setName(student.getName());
            }
            if (student.getSemester() > 0) {
                existingStudent.setSemester(student.getSemester());
            }
            if (student.getSubjects() != null) {
                existingStudent.setSubjects(student.getSubjects());
            }
            return studentRepository.save(existingStudent);
        }).orElse(null);
    }
}
package com.backend.simple.controller;

import com.backend.simple.exception.ResourceNotFoundException;
import com.backend.simple.model.Student;
import com.backend.simple.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/students")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student){
        return studentRepository.save(student);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){

        Student student = studentRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("The student not exist with id: " + id
                ));
        return ResponseEntity.ok(student);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,@RequestBody Student studentUp){

        Student student = studentRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("The student not exist with id: " + id
                ));
        student.setFirstName(studentUp.getFirstName());
        student.setLastName(studentUp.getLastName());
        student.setEmail(studentUp.getEmail());

        Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteStudent(@PathVariable Long id){
        Student student = studentRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("The student not exist with id: " + id
                ));
        studentRepository.delete(student);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

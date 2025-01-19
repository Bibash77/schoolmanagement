package com.example.usermgmntservice.controller;


import com.example.usermgmntservice.dto.GlobalAPIResponse;
import com.example.usermgmntservice.dto.StudentRequestDto;
import com.example.usermgmntservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController extends BaseController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<GlobalAPIResponse> getAllStudents() {
        return ResponseEntity.ok(successResponse("Fetched Student List !!", studentService.getAllStudents()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalAPIResponse> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(successResponse("Fetched student !!", studentService.getStudentById(id)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<GlobalAPIResponse> updateStudent(@RequestBody StudentRequestDto student, @PathVariable Long id) {
        return ResponseEntity.ok(successResponse("Student updated !!", studentService.updateStudent(student, id)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalAPIResponse> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);

        return ResponseEntity.ok(successResponse("Student Deleted !! ", id));
    }
}

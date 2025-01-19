package com.example.usermgmntservice.service;



import com.example.usermgmntservice.dao.StudentRepository;
import com.example.usermgmntservice.dto.StudentRequestDto;
import com.example.usermgmntservice.entity.Student;
import com.example.usermgmntservice.producer.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final KafkaProducerService kafkaProducerService;

    @Cacheable("students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // redis templates can be used for complex situation
    // for now implemented simple cacheable with eviction
    @Cacheable(value = "students", key = "#id")
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student updateStudent(StudentRequestDto studentRequest, Long id) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        BeanUtils.copyProperties(studentRequest, existingStudent);
        Student savedStudent = studentRepository.save(existingStudent);
        kafkaProducerService.sendStudentEvent(" ---- Student Update with success: " + savedStudent.getName());
        return savedStudent;
    }

    public void createStudent(Student student) {
        Student savedStudent = studentRepository.save(student);
        kafkaProducerService.sendStudentEvent("New Student Created: " + savedStudent.getName());
    }

    @CacheEvict(value = "students", key = "#id")
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
        kafkaProducerService.sendStudentEvent(" ---- Student deleted with id: " + id);

    }
}
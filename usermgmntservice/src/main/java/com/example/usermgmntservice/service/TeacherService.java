package com.example.usermgmntservice.service;


import com.example.usermgmntservice.dao.TeacherRepository;
import com.example.usermgmntservice.dto.TeacherRequestDto;
import com.example.usermgmntservice.entity.Teacher;
import com.example.usermgmntservice.producer.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final KafkaProducerService kafkaProducerService;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Cacheable(value = "teachers", key = "#id")
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    public void createTeacher(Teacher teacher) {
        Teacher savedTeacher = teacherRepository.save(teacher);
        kafkaProducerService.sendTeacherEvent("New Teacher Created: " + savedTeacher.getName());
    }

    public Teacher updateTeacher(TeacherRequestDto teacherRequest, Long id) {
        Teacher existingTeacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
        BeanUtils.copyProperties(teacherRequest, existingTeacher);
        Teacher savedTeacher = teacherRepository.save(existingTeacher);
        kafkaProducerService.sendStudentEvent(" ---- Teacher data Update with success: " + savedTeacher.getName());
        return savedTeacher;
    }

    @CacheEvict(value = "teachers", key = "#id")
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
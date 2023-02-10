package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        logger.info("Adding student: {}", student);
        return studentRepository.save(student);
    }

    public Student find(Long id) {
        logger.info("Finding student with id = {}", id);
        Student student = studentRepository.findById(id).orElseThrow();
        logger.info("Success: {}", student);
        return student;
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Finding student by age = {}", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Finding student by age from {} to {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Collection<Student> findStudentsByFaculty_Name(String faculty) {
        logger.info("Finding student by faculty name = {}", faculty);
        return studentRepository.findStudentsByFaculty_Name(faculty);
    }

    public Student edit(Student student) {
        logger.info("Was invoked method for edit student");
        return studentRepository.save(student);
    }

    public void delete(Long id) {
        logger.info("Removing student with id = {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public long getStudentsCount() {
        logger.info("Was invoked method to get count of students");
        return studentRepository.getStudentsCount();
    }

    public int getAverageAge() {
        logger.info("Was invoked method to get average age of student");
        return studentRepository.getAverageAge();
    }

    public Collection<Student> getLast(int n) {
        logger.info("Was invoked method to get {} last students", n);
        return studentRepository.getLast(n);
    }

}

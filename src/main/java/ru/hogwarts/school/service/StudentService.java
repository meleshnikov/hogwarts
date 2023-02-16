package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public double getAverageAge() {
        logger.info("Was invoked method to get average age of student");
        return studentRepository.getAverageAge();
    }

    public double getAverageAgeByStream() {
        logger.info("Was invoked method to get average age of student by stream");
        return getAll().stream()
                .parallel()
                .mapToInt(Student::getAge)
                .average().orElse(0);
    }

    public Collection<Student> getLast(int n) {
        logger.info("Was invoked method to get {} last students", n);
        return studentRepository.getLast(n);
    }

    public Collection<String> getNamesInUpperCaseSortedStartingWith(String letter) {
        logger.info("Finding names starting with: '{}'", letter);
        String l = letter.toUpperCase();
        return getAll()
                .stream()
                .parallel()
                .map(s -> s.getName().toUpperCase())
                .filter(n -> n.startsWith(l))
                .sorted()
                .collect(Collectors.toList());
    }

    public void printNames() {
        List<Student> students = new ArrayList<>(getAll());
        logger.info("Printing names...");
        Thread thread1 = new Thread(() -> {
            print(students, 2);
            print(students, 3);
        });
        Thread thread2 = new Thread(() -> {
            print(students, 4);
            print(students, 5);
        });
        thread1.start();
        thread2.start();
        print(students, 0);
        print(students, 1);
    }

    private final Object lock = new Object();

    public void printNamesSynchronized() {
        List<Student> students = new ArrayList<>(getAll());
        logger.info("Printing names synchronously...");
        Thread thread1 = new Thread(() -> {
            print(students, 2, lock);
            print(students, 3, lock);
        });
        Thread thread2 = new Thread(() -> {
            print(students, 4, lock);
            print(students, 5, lock);
        });
        print(students, 0, lock);
        print(students, 1, lock);
        thread1.start();
        thread2.start();
    }

    private void print(List<Student> students, int index) {
        logger.debug("{}: ", Thread.currentThread().getName());
        System.out.println(students.get(index).getName());
    }

    private void print(List<Student> students, int index, Object lock) {
        synchronized (lock) {
            print(students, index);
        }
    }

}

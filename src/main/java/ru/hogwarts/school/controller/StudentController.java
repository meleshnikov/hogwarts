package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService facultyService) {
        this.studentService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return student != null ?
                ResponseEntity.ok(studentService.create(student)) :
                ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> find(@PathVariable Long id) {
        Student student = studentService.find(id);
        return student != null ?
                ResponseEntity.ok(student) :
                ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<Collection<Student>> findByAge(@RequestParam int age) {
        return ResponseEntity.ok(studentService.findByAge(age));
    }

    @PutMapping
    public ResponseEntity<Student> edit(@RequestBody Student student) {
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        Student oldStudent = studentService.edit(student);
        return oldStudent != null ?
                ResponseEntity.ok(oldStudent) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Student> delete(@RequestBody Student student) {
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        Student oldStudent = studentService.delete(student);
        return oldStudent != null ?
                ResponseEntity.ok(oldStudent) :
                ResponseEntity.notFound().build();
    }

}

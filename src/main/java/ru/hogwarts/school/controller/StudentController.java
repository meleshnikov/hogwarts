package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
        return ResponseEntity.ok(studentService.find(id));
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<Collection<Student>> findByAge(@PathVariable int age) {
        return ResponseEntity.ok(studentService.findByAge(age));
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam int min,
                                                                @RequestParam int max) {
        return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
    }

    @GetMapping()
    public ResponseEntity<Collection<Student>> findStudentsByFaculty_Name(@RequestParam String faculty) {
        return ResponseEntity.ok(studentService.findStudentsByFaculty_Name(faculty));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @PutMapping
    public ResponseEntity<Student> edit(@RequestBody Student student) {
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.edit(student));
    }

    @DeleteMapping
    public ResponseEntity<Student> delete(@RequestBody Student student) {
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        studentService.delete(student.getId());
        return ResponseEntity.ok().build();
    }

}

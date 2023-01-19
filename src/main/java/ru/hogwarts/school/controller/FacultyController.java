package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        return faculty != null ?
                ResponseEntity.ok(facultyService.create(faculty)) :
                ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> find(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.find(id));
    }

    @GetMapping("/by/color")
    public ResponseEntity<Collection<Faculty>> findByColor(@RequestParam String color) {
        return color != null ?
                ResponseEntity.ok(facultyService.findByColor(color)) :
                ResponseEntity.badRequest().build();
    }

    @GetMapping("/by")
    public ResponseEntity<Collection<Faculty>> findByNameIgnoreCaseOrColorIgnoreCase(@RequestParam String name,
                                                                                     @RequestParam String color) {
        return ResponseEntity.ok(facultyService.findByNameIgnoreCaseOrColorIgnoreCase(name, color));
    }

    @GetMapping("/by/student")
    public ResponseEntity<Collection<Faculty>> findFacultyByStudentId(@RequestParam Long id) {
        return ResponseEntity.ok(facultyService.findFacultyByStudentId(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Faculty>> getAll() {
        return ResponseEntity.ok(facultyService.getAll());
    }

    @PutMapping
    public ResponseEntity<Faculty> edit(@RequestBody Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.edit(faculty));
    }

    @DeleteMapping
    public ResponseEntity<Faculty> delete(@RequestBody Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.badRequest().build();
        }
        facultyService.delete(faculty.getId());
        return ResponseEntity.ok().build();
    }

}

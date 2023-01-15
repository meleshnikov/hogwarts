package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
        Faculty faculty = facultyService.find(id);
        return faculty != null ?
                ResponseEntity.ok(faculty) :
                ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<Collection<Faculty>> findByColor(@RequestParam String color) {
        return color != null ?
                ResponseEntity.ok(facultyService.findByColor(color)) :
                ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<Faculty> edit(@RequestBody Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.badRequest().build();
        }
        Faculty oldFaculty = facultyService.edit(faculty);
        return oldFaculty != null ?
                ResponseEntity.ok(oldFaculty) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Faculty> delete(@RequestBody Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.badRequest().build();
        }
        Faculty oldFaculty = facultyService.delete(faculty);
        return oldFaculty != null ?
                ResponseEntity.ok(oldFaculty) :
                ResponseEntity.notFound().build();
    }

}

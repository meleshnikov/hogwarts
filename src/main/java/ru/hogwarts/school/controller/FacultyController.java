package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;
    private final FacultyRepository facultyRepository;

    public FacultyController(FacultyService facultyService,
                             FacultyRepository facultyRepository) {
        this.facultyService = facultyService;
        this.facultyRepository = facultyRepository;
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
        return ResponseEntity.ok(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color));
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

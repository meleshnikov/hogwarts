package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty create(Faculty faculty) {
        logger.info("Adding faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty find(Long id) {
        logger.info("Finding faculty by id = {}", id);
        return facultyRepository.findById(id).orElseThrow();
    }

    public Faculty findFacultyByStudentsId(long id) {
        logger.info("Finding faculty by student's id = {}", id);
        return facultyRepository.findFacultyByStudentsId(id);
    }

    public Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Finding faculty by name = {} or color = {}", name, color);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Finding faculty by color = {}", color);
        return facultyRepository.findByColor(color);
    }

    public Faculty edit(Faculty faculty) {
        logger.info("Editing faculty to {}", faculty);
        return facultyRepository.save(faculty);
    }

    public void delete(Long id) {
        logger.info("Removing faculty with id = {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAll() {
        logger.info("Was invoked method to get all faculties");
        return facultyRepository.findAll();
    }

    public String getLongestName() {
        return getAll().stream()
                .max(Comparator.comparing(f -> f.getName().length()))
                .orElseThrow()
                .getName();
    }
}

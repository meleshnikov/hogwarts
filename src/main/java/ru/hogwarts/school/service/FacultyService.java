package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();

    private long id = 0;

    public Faculty create(Faculty faculty) {
        Objects.requireNonNull(faculty);
        faculty.setId(++id);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty find(Long id) {
        return faculties.get(id);
    }

    public Collection<Faculty> findByColor(String color) {
        Objects.requireNonNull(color);
        return faculties.values().stream().filter(f -> f.getColor().equals(color)).toList();
    }

    // возвращает старое значение или null, если отсутствует
    public Faculty edit(Faculty faculty) {
        Objects.requireNonNull(faculty);
        Long id = faculty.getId();
        return faculties.containsKey(id) ? faculties.put(id, faculty) : null;
    }

    public Faculty delete(Faculty faculty) {
        Objects.requireNonNull(faculty);
        return faculties.remove(faculty.getId());
    }
}

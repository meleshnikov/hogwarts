package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();

    private long id = 0;

    public Student create(Student student) {
        Objects.requireNonNull(student);
        student.setId(++id);
        students.put(student.getId(), student);
        return student;
    }

    public Student find(Long id) {
        return students.get(id);
    }

    public Collection<Student> findByAge(int age) {
        return students.values().stream().filter(s -> s.getAge() == age).toList();
    }

    // возвращает старое значение или null, если отсутствует
    public Student edit(Student student) {
        Objects.requireNonNull(student);
        Long id = student.getId();
        return students.containsKey(id) ? students.put(id, student) : null;
    }

    public Student delete(Student student) {
        Objects.requireNonNull(student);
        return students.remove(student.getId());
    }

}

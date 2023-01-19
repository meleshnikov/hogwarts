package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByColor(String color);

    Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    @Query(value = "select f " +
            "from faculty as f, student as s " +
            "where s.faculty_id = f.id " +
            "and s.id = :studentId",
            nativeQuery = true)
    Collection<Faculty> findFacultyByStudentId(Long studentId);


}

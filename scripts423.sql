SELECT student.name, student.age, faculty.name
FROM student
         JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name, student.age
FROM student
         JOIN avatar ON avatar.student_id = student.id;
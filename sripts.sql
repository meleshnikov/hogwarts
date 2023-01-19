SELECT *
FROM student
WHERE age > 10
  AND age < 15;

SELECT name
FROM student;

SELECT *
FROM student
WHERE name LIKE '%O%';

SELECT *
FROM student
WHERE age < student.id;

SELECT *
FROM student
ORDER BY age;
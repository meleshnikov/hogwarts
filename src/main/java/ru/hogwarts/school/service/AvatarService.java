package ru.hogwarts.school.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${avatars.dir.path}")
    private String dir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void upload(long id, MultipartFile file) throws IOException {
        Student student = studentService.find(id);
        Path path = Path.of(dir,
                student.getId().toString() + "."
                        + FilenameUtils.getExtension(file.getOriginalFilename()));

        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = find(id);
        avatar.setStudent(student);
        avatar.setPath(path.toString());
        avatar.setSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
    }

    public Avatar find(long id) {
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }


    public void download(long id, HttpServletResponse response) throws IOException {
        Avatar avatar = find(id);
        Path path = Path.of(avatar.getPath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();
        ) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getSize());
            is.transferTo(os);
        }

    }


}

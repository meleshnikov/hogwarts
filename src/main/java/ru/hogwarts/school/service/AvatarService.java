package ru.hogwarts.school.service;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
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
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${avatars.dir.path}")
    private String dir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void upload(long id, MultipartFile file) throws IOException {
        logger.info("Uploading avatar for student with id = {}", id);
        Student student = studentService.find(id);
        Path path = Path.of(dir,
                student.getId().toString() + "."
                        + FilenameUtils.getExtension(file.getOriginalFilename()));
        Files.createDirectories(path.getParent());
        logger.debug("avatar's path: {}", path);
        Files.deleteIfExists(path);

        logger.debug("Opening file");
        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            logger.debug("Writing file to: {}", path);
            bis.transferTo(bos);
        }

        logger.debug("Adding file to db");
        Avatar avatar = find(id);
        avatar.setStudent(student);
        avatar.setPath(path.toString());
        avatar.setSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
    }

    public Avatar find(long id) {
        logger.info("Finding avatar by student's id = {}", id);
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }


    public void download(long id, HttpServletResponse response) throws IOException {
        logger.info("Downloading file for student with id = {}", id);
        Avatar avatar = find(id);
        Path path = Path.of(avatar.getPath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()
        ) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getSize());
            is.transferTo(os);
        }

    }

    public Collection<Avatar> getAvatars(Integer page, Integer size) {
        logger.info("Was invoked method to get avatars in page = {}, with page size = {}", page, size);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }

}

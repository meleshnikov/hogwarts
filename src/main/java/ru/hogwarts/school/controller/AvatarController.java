package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> upload(@PathVariable long id,
                                  @RequestParam MultipartFile file) throws IOException {
        avatarService.upload(id, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/from-db")
    public ResponseEntity<byte[]> download(@PathVariable long id) {
        Avatar avatar = avatarService.find(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("{id}/from-file")
    public void download(@PathVariable long id,
                         HttpServletResponse response) throws IOException {
        avatarService.download(id, response);
    }

    @GetMapping
    public ResponseEntity<Collection<Avatar>> getAvatars(@RequestParam Integer page,
                                                         @RequestParam Integer size) {
        Collection<Avatar> avatars = avatarService.getAvatars(page, size);
        return ResponseEntity.ok(avatars);
    }

}

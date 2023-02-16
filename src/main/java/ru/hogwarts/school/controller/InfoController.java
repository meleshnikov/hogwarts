package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/get-port")
    public int getPort() {
        return infoService.getPort();
    }

    @GetMapping("/foo1")
    public long foo1() {
        return infoService.foo1();
    }

    @GetMapping("/foo2")
    public long foo2() {
        return infoService.foo2();
    }

    @GetMapping("/foo3")
    public long foo3() {
        return infoService.foo3();
    }

    @GetMapping("/foo4")
    public long foo4() {
        return infoService.foo4();
    }

}

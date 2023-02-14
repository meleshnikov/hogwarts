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
    public int foo1() {
        return infoService.foo1();
    }

    @GetMapping("/foo2")
    public int foo2() {
        return infoService.foo2();
    }

    @GetMapping("/foo3")
    public int foo3() {
        return infoService.foo3();
    }

}

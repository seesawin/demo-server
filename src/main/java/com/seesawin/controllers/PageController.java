package com.seesawin.controllers;

import com.github.pagehelper.PageInfo;
import com.seesawin.models.Person;
import com.seesawin.services.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/page")
public class PageController {
    @Autowired
    PageService pageService;

    @GetMapping("/all/{page}/{pageSize}")
    public PageInfo<Person> allAccess(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize) {
        PageInfo<Person> person = pageService.getPerson(page, pageSize);
        return person;
    }
}

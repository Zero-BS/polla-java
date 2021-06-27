package org.zerobs.polla.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zerobs.polla.entities.db.Tag;
import org.zerobs.polla.services.TagManager;

import java.util.List;

@RestController
@RequestMapping("/v1/tags")
public class TagController {
    @Autowired
    private TagManager tagManager;

    @GetMapping("search")
    public List<Tag> search(@RequestParam("name_initials") String nameInitials) {
        return tagManager.search(nameInitials);
    }
}

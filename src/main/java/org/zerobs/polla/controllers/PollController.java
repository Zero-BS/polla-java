package org.zerobs.polla.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.zerobs.polla.entities.db.Poll;
import org.zerobs.polla.exception.CustomException;
import org.zerobs.polla.services.PollManager;

@RestController
@RequestMapping("/v1/polls")
public class PollController {
    @Autowired
    private PollManager pollManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody(required = false) Poll poll) throws CustomException {
        pollManager.add(poll);
    }
}

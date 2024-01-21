package com.example.demo.controller;

import com.example.demo.dto.DoctorOutputDto;
import com.example.demo.service.CommentService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.DoctoretoService;
import com.example.demo.service.DrDrService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/Comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;



    @PostMapping(value = "/extract-comments")
    public @ResponseBody
    DoctorOutputDto EctractComments() throws JsonProcessingException, InterruptedException {
        return commentService.ExtractComments();
    }

}

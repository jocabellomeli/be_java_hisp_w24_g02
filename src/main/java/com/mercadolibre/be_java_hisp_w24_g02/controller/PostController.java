package com.mercadolibre.be_java_hisp_w24_g02.controller;

import com.mercadolibre.be_java_hisp_w24_g02.dao.CreatePostDAO;
import com.mercadolibre.be_java_hisp_w24_g02.service.interfaces.IPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class PostController {
    @Autowired
    private IPostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> addNewProductPost(@Valid @RequestBody CreatePostDAO createPostDAO){
        this.postService.createProductPost(createPostDAO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

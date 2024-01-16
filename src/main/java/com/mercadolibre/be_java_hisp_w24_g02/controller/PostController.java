package com.mercadolibre.be_java_hisp_w24_g02.controller;

import com.mercadolibre.be_java_hisp_w24_g02.entity.Post;
import com.mercadolibre.be_java_hisp_w24_g02.repository.implementations.PostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostRepositoryImpl postRepositoryImpl;
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPost() {
        List<Post> post = postRepositoryImpl.findAll();
        return ResponseEntity.ok(post);
    }
}

package com.mercadolibre.be_java_hisp_w24_g02.controller;

import com.mercadolibre.be_java_hisp_w24_g02.dao.UserRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/users/{userId}/followers/list")
    public ResponseEntity<UserRelationshipsDTO> getUserFollowers(@PathVariable Integer userId, @RequestParam(required = false, defaultValue = "none") String order) {
        UserRelationshipsDTO userRelationshipsDTO = userService.getUserFollowers(userId, order);
        return ResponseEntity.ok(userRelationshipsDTO);
    }

    @GetMapping("/users/{userId}/followed/list")
    public ResponseEntity<UserRelationshipsDTO> getUserFollowed(@PathVariable Integer userId, @RequestParam(required = false, defaultValue = "none") String order) {
        UserRelationshipsDTO userRelationshipsDTO = userService.getUserFollowed(userId, order);
        return ResponseEntity.ok(userRelationshipsDTO);
    }

}

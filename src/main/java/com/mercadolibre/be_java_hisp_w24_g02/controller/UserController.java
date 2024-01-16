package com.mercadolibre.be_java_hisp_w24_g02.controller;

import com.mercadolibre.be_java_hisp_w24_g02.dao.UserFollowedsPostsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dao.UserRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    @GetMapping("/users/{userId}/followers/list")
    public ResponseEntity<UserRelationshipsDTO> getUserFollowers(@PathVariable Integer userId) {
        UserRelationshipsDTO userRelationshipsDTO = userService.getUserFollowers(userId);
        return ResponseEntity.ok(userRelationshipsDTO);
    }

    @GetMapping("products/followed/{userId}/list")
    public ResponseEntity<UserFollowedsPostsDTO> getUserFollowed(@PathVariable Integer userId) {
        UserFollowedsPostsDTO userServiceUserFollowed = userService.getFollowedPost(userId);
        return ResponseEntity.ok(userServiceUserFollowed);
    }

}

package com.mercadolibre.be_java_hisp_w24_g02.controller;

import com.mercadolibre.be_java_hisp_w24_g02.dto.FollowUserDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<String> unFollowUser(@PathVariable Integer userId, @PathVariable Integer userIdToUnfollow) {
        FollowUserDTO followUserDTO = new FollowUserDTO(userId, userIdToUnfollow);
        this.userService.unfollowUser(followUserDTO);
        return ResponseEntity.ok("Usuario seguido exitosamente");
    }

    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<String> followUser(@PathVariable Integer userId, @PathVariable Integer userIdToFollow) {
        FollowUserDTO followUserDTO = new FollowUserDTO(userId, userIdToFollow);
        userService.followUser(followUserDTO);
        return ResponseEntity.ok("Usuario seguido exitosamente");
    }


    @GetMapping("/users/{userId}/followed/list")
    public ResponseEntity<UserRelationshipsDTO> getUserFollowed(@PathVariable Integer userId) {
        UserRelationshipsDTO userRelationshipsDTO = userService.getUserFollowed(userId);
        return ResponseEntity.ok(userRelationshipsDTO);
    }

}

package com.mercadolibre.be_java_hisp_w24_g02.controller;

import com.mercadolibre.be_java_hisp_w24_g02.dto.UserFollowedsPostsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.FollowUserDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.service.interfaces.IUserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<UserRelationshipsDTO> getUserFollowers(@PathVariable Integer userId, @RequestParam(required = false, defaultValue = "none") String order) throws BadRequestException {
        UserRelationshipsDTO userRelationshipsDTO = userService.getUserFollowers(userId, order);
        return ResponseEntity.ok(userRelationshipsDTO);
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<String> unFollowUser(@PathVariable Integer userId, @PathVariable Integer userIdToUnfollow) {
        FollowUserDTO followUserDTO = new FollowUserDTO(userId, userIdToUnfollow);
        this.userService.unfollowUser(followUserDTO);
        return ResponseEntity.ok("Usuario seguido exitosamente");
    }
    
    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<UserRelationshipsDTO> getUserFollowed(@PathVariable Integer userId, @RequestParam(required = false, defaultValue = "none") String order) throws BadRequestException {
        UserRelationshipsDTO userRelationshipsDTO = userService.getUserFollowed(userId, order);
        return ResponseEntity.ok(userRelationshipsDTO);
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<String> followUser(@PathVariable Integer userId, @PathVariable Integer userIdToFollow) {
        FollowUserDTO followUserDTO = new FollowUserDTO(userId, userIdToFollow);
        userService.followUser(followUserDTO);
        return ResponseEntity.ok("Usuario seguido exitosamente");
    }


    @GetMapping("products/followed/{userId}/list")
    public ResponseEntity<UserFollowedsPostsDTO> getUserFollowed(@PathVariable Integer userId) {
        UserFollowedsPostsDTO userServiceUserFollowed = userService.getFollowedPost(userId);
        return ResponseEntity.ok(userServiceUserFollowed);
    }

}

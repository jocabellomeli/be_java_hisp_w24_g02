package com.mercadolibre.be_java_hisp_w24_g02.controller;

import com.mercadolibre.be_java_hisp_w24_g02.dto.UserRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<UserRelationshipsDTO> getUserFollowed(@PathVariable Integer userId, @RequestParam(required = false, defaultValue = "none") String order) throws BadRequestException {
        UserRelationshipsDTO userRelationshipsDTO = userService.getUserFollowed(userId, order);
        return ResponseEntity.ok(userRelationshipsDTO);
    }

}

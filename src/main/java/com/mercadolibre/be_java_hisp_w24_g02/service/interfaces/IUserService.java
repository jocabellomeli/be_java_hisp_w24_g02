package com.mercadolibre.be_java_hisp_w24_g02.service.interfaces;

import com.mercadolibre.be_java_hisp_w24_g02.dto.FollowUserDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserRelationshipsDTO;
import org.apache.coyote.BadRequestException;

public interface IUserService {
    void unfollowUser(FollowUserDTO followUserDTO);
    void followUser(FollowUserDTO followUserDTO);
    UserRelationshipsDTO getUserFollowers(Integer userId, String order);
    UserRelationshipsDTO getUserFollowed(Integer userId, String order);
}

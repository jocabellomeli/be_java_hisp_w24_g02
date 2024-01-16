package com.mercadolibre.be_java_hisp_w24_g02.service.interfaces;

import com.mercadolibre.be_java_hisp_w24_g02.dto.FollowUserDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserRelationshipsDTO;

public interface IUserService {

    UserRelationshipsDTO getUserFollowers(Integer userId);
    void unfollowUser(FollowUserDTO followUserDTO);
    UserRelationshipsDTO followUser(FollowUserDTO followUserDTO);
    UserRelationshipsDTO getUserFollowed(Integer userId);


}

package com.mercadolibre.be_java_hisp_w24_g02.service.interfaces;

import com.mercadolibre.be_java_hisp_w24_g02.dto.UserFollowedsPostsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserRelationshipsDTO;
import org.apache.coyote.BadRequestException;

public interface IUserService {

    UserRelationshipsDTO getUserFollowers(Integer userId, String order);

    UserRelationshipsDTO getUserFollowed(Integer userId, String order);

    void followUser(Integer userId, Integer userIdToFollow);

    UserFollowedsPostsDTO getFollowedPost(Integer userId);

}

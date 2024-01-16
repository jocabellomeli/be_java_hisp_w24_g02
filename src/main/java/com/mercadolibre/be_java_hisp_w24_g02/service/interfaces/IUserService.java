package com.mercadolibre.be_java_hisp_w24_g02.service.interfaces;

import com.mercadolibre.be_java_hisp_w24_g02.dao.UserFollowedsPostsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dao.UserRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;

import java.util.List;

public interface IUserService {

    UserRelationshipsDTO getUserFollowers(Integer userId);
    UserFollowedsPostsDTO getFollowedPost(Integer userId);
}

package com.mercadolibre.be_java_hisp_w24_g02.service.interfaces;

import com.mercadolibre.be_java_hisp_w24_g02.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserRelationshipsDTO;

public interface IUserService {

    UserRelationshipsDTO getUserFollowers(Integer userId);

    UserRelationshipsDTO getUserFollowed(Integer userId);

    UserFollowersCountDTO getUserFollowersCount(Integer userId);

}

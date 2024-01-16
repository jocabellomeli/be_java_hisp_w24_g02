package com.mercadolibre.be_java_hisp_w24_g02.service.interfaces;

import com.mercadolibre.be_java_hisp_w24_g02.dao.FollowUserDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dao.UserRelationshipsDTO;

public interface IUserService {

    UserRelationshipsDTO getUserFollowers(Integer userId);
    public Boolean unfollowUser(FollowUserDTO followUserDTO);

}

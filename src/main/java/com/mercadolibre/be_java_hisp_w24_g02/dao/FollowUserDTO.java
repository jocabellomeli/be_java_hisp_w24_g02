package com.mercadolibre.be_java_hisp_w24_g02.dao;

public record FollowUserDTO(
        Integer userId,
        Integer userIdToUnfollow
) {
}

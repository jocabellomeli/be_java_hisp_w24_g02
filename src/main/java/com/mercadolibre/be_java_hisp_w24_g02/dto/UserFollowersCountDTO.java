package com.mercadolibre.be_java_hisp_w24_g02.dto;

public record UserFollowersCountDTO(
        Integer user_id,String user_name, Integer followers_count)
{

}

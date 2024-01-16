package com.mercadolibre.be_java_hisp_w24_g02.service.interfaces;

import com.mercadolibre.be_java_hisp_w24_g02.dao.CreatePostDTO;

public interface IPostService {
    public Boolean createProductPost(CreatePostDTO createPostDTO);
}

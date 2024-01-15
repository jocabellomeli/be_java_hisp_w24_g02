package com.mercadolibre.be_java_hisp_w24_g02.repository;

import com.mercadolibre.be_java_hisp_w24_g02.entity.Post;

import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements IPostRepository {
    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public Optional<Post> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}

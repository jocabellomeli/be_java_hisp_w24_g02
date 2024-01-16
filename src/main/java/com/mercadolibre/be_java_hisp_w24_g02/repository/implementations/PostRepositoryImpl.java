package com.mercadolibre.be_java_hisp_w24_g02.repository.implementations;

import com.mercadolibre.be_java_hisp_w24_g02.entity.Post;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IPostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    private List<Post> posts;

    public PostRepositoryImpl() {
        this.posts = new ArrayList<>();
    }

    @Override
    public Post save(Post post) {
        this.posts.add(post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return this.posts;
    }

    @Override
    public Optional<Post> findById(Integer id) {
        return this.posts.stream().filter(post -> Objects.equals(post.getId(), id)).findFirst();
    }

    @Override
    public Post update(Post post) {
        this.posts = this.posts.stream().map(currentPost -> {
            if(Objects.equals(post.getId(), currentPost.getId())){
                return post;
            }

            return currentPost;
        }).toList();

        return post;
    }

    @Override
    public void delete(Integer id) {
        this.posts = this.posts.stream().filter(post -> !Objects.equals(post.getId(), id)).toList();
    }
}

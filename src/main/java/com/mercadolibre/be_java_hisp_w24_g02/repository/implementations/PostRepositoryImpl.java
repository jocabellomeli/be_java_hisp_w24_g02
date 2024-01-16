package com.mercadolibre.be_java_hisp_w24_g02.repository.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.be_java_hisp_w24_g02.entity.Post;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IPostRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements IPostRepository {

    List<Post> posts;

    public PostRepositoryImpl() {
        System.out.println(loadData());
        this.posts = loadData();
    }
    @Override
    public Post save(Post post) {
        this.posts.add(post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return posts;
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

    private List<Post> loadData(){
        List<Post> data = null;
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        TypeReference<List<Post>> typeRef = new TypeReference<>() {};
        try {
            file = ResourceUtils.getFile("classpath:json/posts.json");

            data = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    @Override
    public List<Post> getPostOfFollowedList(List<Integer> usersIds) {
        return posts.stream().filter(post -> usersIds.contains(post.getUserId())
        ).collect(Collectors.toList());
    }

}

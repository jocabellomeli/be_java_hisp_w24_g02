package com.mercadolibre.be_java_hisp_w24_g02.repository.implementations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private List<User> users;

    public UserRepositoryImpl() {
        this.users = loadData();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    private List<User> loadData(){
        ArrayList<User> data = null;
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<ArrayList<User>> typeRef = new TypeReference<>() {};
        try {
            file = ResourceUtils.getFile("classpath:json/users.json");
            data = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}

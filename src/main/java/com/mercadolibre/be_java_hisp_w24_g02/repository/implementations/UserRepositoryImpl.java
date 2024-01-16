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
        return this.users;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
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
            randomizeUsers(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void randomizeUsers(ArrayList<User> data) {
        for (User user : data) {
            user.setFollowers(randomizeRelationships(data, user.getId()));
            user.setFollowed(randomizeRelationships(data, user.getId()));
        }
    }

    private List<User> randomizeRelationships(ArrayList<User> data, Integer... excludeIds) {
        List<User> randomUsers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User randomUser = data.get((int) (Math.random() * data.size()));
            //Que toque 2 veces el mismo usuario y que no sea el mismo usuario que el que se esta creando
            if (!randomUsers.contains(randomUser) && !randomUser.getId().equals(excludeIds[0])) {
                randomUsers.add(randomUser);
            }
        }
        return randomUsers;
    }

}

package com.mercadolibre.be_java_hisp_w24_g02.service.implementations;

import com.mercadolibre.be_java_hisp_w24_g02.dao.UserBasicInfoDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dao.UserRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;
import com.mercadolibre.be_java_hisp_w24_g02.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IUserRepository;
import com.mercadolibre.be_java_hisp_w24_g02.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public UserRelationshipsDTO getUserFollowers(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );

        List<UserBasicInfoDTO> followers = user.getFollowers()
                .stream()
                .map(follower -> new UserBasicInfoDTO(follower.getId(), follower.getName()))
                .toList();

        return new UserRelationshipsDTO(
                user.getId(),
                user.getName(),
                followers,
                true
        );
    }

    @Override
    public UserRelationshipsDTO followUser(Integer userId, Integer userIdToFollow) {

        User follower = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId));

        User userToFollow = userRepository.findById(userIdToFollow)
                .orElseThrow(() -> new NotFoundException("User to follow not found: " + userIdToFollow));


        if (follower.getFollowed().contains(userToFollow)  && userToFollow.getFollowers().contains(follower)) {
            throw new IllegalArgumentException("Ya estás siguiendo a este usuario: " + userIdToFollow);
        }

        follower.getFollowed().add(userToFollow);
        userToFollow.getFollowers().add(follower);

        List<UserBasicInfoDTO> followeds = follower.getFollowed()
                        .stream()
                        .map(followed -> new UserBasicInfoDTO(followed.getId(), followed.getName()))
                        .toList();

        userRepository.update(follower);
        userRepository.update(userToFollow);



        return new UserRelationshipsDTO(
                follower.getId(),
                follower.getName(),
                followeds,
                false

        );
    }
}

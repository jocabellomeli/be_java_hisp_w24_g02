package com.mercadolibre.be_java_hisp_w24_g02.service.implementations;

import com.mercadolibre.be_java_hisp_w24_g02.dao.FollowUserDTO;
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
    public Boolean unfollowUser(FollowUserDTO followUserDTO) {
        User user = this.userRepository.findById(followUserDTO.userId())
                .orElseThrow(() -> new NotFoundException("User " + followUserDTO.userId() + " not found"));
        User userToUnfollow = this.userRepository.findById(followUserDTO.userIdToUnfollow())
                .orElseThrow(() -> new NotFoundException("User to unfollow "+ followUserDTO.userIdToUnfollow() + " not found"));

        List<User> usersNewFollowers = user.getFollowers().stream().filter(
                userFollower -> !userFollower.getId().equals(userToUnfollow.getId())
        ).toList();
        user.setFollowers(usersNewFollowers);

        this.userRepository.update(user);

        return Boolean.TRUE;
    }
}

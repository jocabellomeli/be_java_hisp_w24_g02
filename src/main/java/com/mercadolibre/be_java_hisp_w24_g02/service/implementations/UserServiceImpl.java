package com.mercadolibre.be_java_hisp_w24_g02.service.implementations;

import com.mercadolibre.be_java_hisp_w24_g02.dto.FollowUserDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserBasicInfoDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserRelationshipsDTO;
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

        User user = getUser(userId);

        List<UserBasicInfoDTO> followers = user.getFollowers()
                .stream()
                .map(follower -> new UserBasicInfoDTO(follower.getId(), follower.getName()))
                .toList();

        return getUserRelationshipsDTO(user, followers, true);
    }

    @Override
    public UserRelationshipsDTO getUserFollowed(Integer userId) {

        User user = getUser(userId);

        List<UserBasicInfoDTO> followed = user.getFollowed()
                .stream()
                .map(follower -> new UserBasicInfoDTO(follower.getId(), follower.getName()))
                .toList();

        return getUserRelationshipsDTO(user, followed, false);

    }

    private UserRelationshipsDTO getUserRelationshipsDTO(User user, List<UserBasicInfoDTO> relationShipList, boolean isFollowers) {
        return new UserRelationshipsDTO(
                user.getId(),
                user.getName(),
                relationShipList,
                isFollowers
        );
    }

    @Override
    public Boolean unfollowUser(FollowUserDTO followUserDTO) {
        User user = this.userRepository.findById(followUserDTO.userId())
                .orElseThrow(() -> new NotFoundException("User " + followUserDTO.userId() + " not found"));
        User userToUnfollow = this.userRepository.findById(followUserDTO.userIdToUnfollow())
                .orElseThrow(() -> new NotFoundException("User to unfollow " + followUserDTO.userIdToUnfollow() + " not found"));

        List<User> usersNewFollowers = user.getFollowers().stream().filter(
                userFollower -> !userFollower.getId().equals(userToUnfollow.getId())
        ).toList();
        user.setFollowers(usersNewFollowers);

        this.userRepository.update(user);

        return Boolean.TRUE;
    }

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

    private User getUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );
    }
}

package com.mercadolibre.be_java_hisp_w24_g02.service.implementations;

import com.mercadolibre.be_java_hisp_w24_g02.dto.UserBasicInfoDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserFollowersCountDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;
import com.mercadolibre.be_java_hisp_w24_g02.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IUserRepository;
import com.mercadolibre.be_java_hisp_w24_g02.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public UserFollowersCountDTO getUserFollowersCount(Integer userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if (user.isEmpty()){
            throw new NotFoundException("user id"+ userId + "not fount");
        }
        Integer followersCount = user.get().getFollowers().size();
        return new UserFollowersCountDTO(user.get().getId(),user.get().getName(),followersCount);
    }

    private UserRelationshipsDTO getUserRelationshipsDTO(User user, List<UserBasicInfoDTO> relationShipList, boolean isFollowers) {
        return new UserRelationshipsDTO(
                user.getId(),
                user.getName(),
                relationShipList,
                isFollowers
        );
    }

    private User getUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );
    }
}

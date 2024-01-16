package com.mercadolibre.be_java_hisp_w24_g02.service.implementations;

import com.mercadolibre.be_java_hisp_w24_g02.dto.UserBasicInfoDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.UserFollowedsPostsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.entity.Post;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;
import com.mercadolibre.be_java_hisp_w24_g02.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w24_g02.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IPostRepository;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IUserRepository;
import com.mercadolibre.be_java_hisp_w24_g02.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPostRepository postRepository;

    @Override
    public UserRelationshipsDTO getUserFollowers(Integer userId, String order){

        User user = getUser(userId);

        List<UserBasicInfoDTO> followers = user.getFollowers()
                .stream()
                .map(follower -> new UserBasicInfoDTO(follower.getId(), follower.getName()))
                .toList();

        followers = orderList(followers, order);

        return getUserRelationshipsDTO(user, followers, true);
    }

    @Override
    public UserRelationshipsDTO getUserFollowed(Integer userId, String order){

        User user = getUser(userId);

        List<UserBasicInfoDTO> followed = user.getFollowed()
                .stream()
                .map(follower -> new UserBasicInfoDTO(follower.getId(), follower.getName()))
                .toList();

        followed = orderList(followed, order);
        return getUserRelationshipsDTO(user, followed, false);

    }

    private List<UserBasicInfoDTO> orderList (List<UserBasicInfoDTO> list, String order) {
        if (order.equals("none")) {
            return list;
        }
        if (order.equals("name_asc")) {
            return list.stream().sorted(Comparator.comparing(UserBasicInfoDTO::userName)).toList();
        }
        if (order.equals("name_desc")) {
            return list.stream().sorted(Comparator.comparing(UserBasicInfoDTO::userName).reversed()).toList();
        }
        throw new BadRequestException("Invalid order");
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
    public void followUser(Integer userId, Integer userIdToFollow) {

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
    }

    private User getUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );
    }
    
    public UserFollowedsPostsDTO getFollowedPost(Integer userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            List<Integer> getIdsFollowed = getIdsFollowed(user.get());
            List<Post> posts = postRepository.getPostOfFollowedList(getIdsFollowed);
            return new UserFollowedsPostsDTO(
                    user.get().getId(),
                    posts
            );
        }
        return null;
    }

    public List<Integer> getIdsFollowed(User user) {
        return user.getFollowed().stream().map(User::getId).toList();
    }


}

package com.mercadolibre.be_java_hisp_w24_g02.service.implementations;

import com.mercadolibre.be_java_hisp_w24_g02.dao.UserBasicInfoDTO;
import com.mercadolibre.be_java_hisp_w24_g02.dao.UserRelationshipsDTO;
import com.mercadolibre.be_java_hisp_w24_g02.entity.User;
import com.mercadolibre.be_java_hisp_w24_g02.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w24_g02.repository.interfaces.IUserRepository;
import com.mercadolibre.be_java_hisp_w24_g02.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public UserRelationshipsDTO getUserFollowers(Integer userId, String order) {

        User user = getUser(userId);

        List<UserBasicInfoDTO> followers = user.getFollowers()
                .stream()
                .map(follower -> new UserBasicInfoDTO(follower.getId(), follower.getName()))
                .toList();

        return getUserRelationshipsDTO(user, followers, true);
    }

    @Override
    public UserRelationshipsDTO getUserFollowed(Integer userId, String order) {

        User user = getUser(userId);

        List<UserBasicInfoDTO> followed = user.getFollowed()
                .stream()
                .map(follower -> new UserBasicInfoDTO(follower.getId(), follower.getName()))
                .toList();

        return getUserRelationshipsDTO(user, followed, false);

    }

    private List<UserBasicInfoDTO> orderList (List<UserBasicInfoDTO> list, String order){
        if (order.equals("none")) {
            return list;
        }
        if (order.equals("name_asc")) {
            return list.stream().sorted(Comparator.comparing(UserBasicInfoDTO::userName)).toList();
        }
        if (order.equals("name_desc")) {
            return list.stream().sorted(Comparator.comparing(UserBasicInfoDTO::userName).reversed()).toList();
        }
        throw new NotFoundException("Order not found");
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
